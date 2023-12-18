package com.mesabrook.ib.util.handlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.apimodels.account.Account;
import com.mesabrook.ib.apimodels.account.DebitCard;
import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.capability.debitcard.CapabilityDebitCard;
import com.mesabrook.ib.capability.debitcard.IDebitCard;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.items.commerce.ItemDebitCard;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.net.atm.CreateNewDebitCardATMResponsePacket;
import com.mesabrook.ib.net.atm.DepositATMResponsePacket;
import com.mesabrook.ib.net.atm.FetchAccountsResponsePacket;
import com.mesabrook.ib.net.atm.WithdrawATMResponsePacket;
import com.mesabrook.ib.net.sco.StoreModeGuiResponse;
import com.mesabrook.ib.util.apiaccess.DataAccess;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataAccess.GenericErrorResponse;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.DataRequestTaskStatus;
import com.mesabrook.ib.util.apiaccess.GetData;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.items.CapabilityItemHandler;

@EventBusSubscriber
public class ServerTickHandler {

	// Used to stagger checks. Methods using this should try to avoid too much happening on any given tick. Values 0-20
	private static byte checkerCounter = 0;
	@SubscribeEvent
	public static void serverTick(ServerTickEvent e)
	{
		if (e.phase != Phase.END)
		{
			return;
		}
		
		checkerCounter++;
		if (checkerCounter > 20)
		{
			checkerCounter = 0;
		}
		checkStoreModeRequests();
		updateEmployeeStoreModes();
		handlePriceLookups();
		handleFetchATMAccounts();
		handleATMWithdrawRequests();
		handleATMDepositRequests();
		handleNewCardRequests();
	}
	
	public static HashMap<UUID, DataRequestTask> storeModeRequestsByUser = new HashMap<UUID, DataRequestTask>();
	private static void checkStoreModeRequests()
	{
		if (checkerCounter != 0 || storeModeRequestsByUser.size() == 0)
		{
			return;
		}
		
		HashSet<UUID> uuidsToRemove = new HashSet<>();
		for(Map.Entry<UUID, DataRequestTask> entry : storeModeRequestsByUser.entrySet())
		{
			DataRequestTask task = entry.getValue();
			if (task.getStatus() == DataRequestTaskStatus.Complete)
			{
				DataAccess access = task.getTask();
				if (access.getRequestSuccessful())
				{
					LocationEmployee[] locationEmployees = access.getResult(LocationEmployee[].class);
					EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(entry.getKey());
					
					StoreModeGuiResponse response = new StoreModeGuiResponse();
					response.locationEmployees = locationEmployees;
					PacketHandler.INSTANCE.sendTo(response, player);
				}
				
				uuidsToRemove.add(entry.getKey());
			}
		}
		
		for(UUID idToRemove : uuidsToRemove)
		{
			storeModeRequestsByUser.remove(idToRemove);
		}
	}
	
	private static ConcurrentHashMap<UUID, DataRequestTask> employeeStoreModeRequests = new ConcurrentHashMap<>();
	private static int updateEmployeeStoreModesChecker = 0;
	private static void updateEmployeeStoreModes()
	{
		if (++updateEmployeeStoreModesChecker < 100)
		{
			return;
		}
		
		updateEmployeeStoreModesChecker = 0;
		
		for(EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
		{
			if (employeeStoreModeRequests.containsKey(player.getUniqueID()))
			{
				handleEmployeeStoreModeRequest(player);
			}
			else
			{
				IEmployeeCapability employeeCap = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
				if (employeeCap.getLocationID() == 0)
				{
					employeeCap.serverToClientSync();
					return;
				}
				
				enqueueStoreModeUpdateNow(player);
			}
			
		}
	}
	
	private static void handleEmployeeStoreModeRequest(EntityPlayerMP player)
	{
		DataRequestTask task = employeeStoreModeRequests.get(player.getUniqueID());
		if (task.getStatus() != DataRequestTaskStatus.Complete)
		{
			return;
		}
		employeeStoreModeRequests.remove(player.getUniqueID());
		
		if (!task.getTask().getRequestSuccessful() && task.getTask().getResponseCode() != 404)
		{
			GenericErrorResponse errorResponse = task.getTask().getResult(GenericErrorResponse.class);
			String logError = "An unknown error occurred while handling employee store mode request";
			if (errorResponse != null)
			{
				logError = errorResponse.message;
			}
			
			Main.logger.error(logError);
			return;
		}
		
		LocationEmployee locationEmployee = new LocationEmployee();
		locationEmployee.LocationID = 0;
		if (task.getTask().getResponseCode() != 404)
		{				
			locationEmployee = task.getTask().getResult(LocationEmployee.class);
		}
		IEmployeeCapability employeeCap = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		employeeCap.setLocationEmployee(locationEmployee);
		employeeCap.serverToClientSync();
	}
	
	public static void enqueueStoreModeUpdateNow(EntityPlayerMP player)
	{
		IEmployeeCapability employeeCap = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		
		GetData get = new GetData(API.Company, "LocationEmployeeIBAccess/GetLocationForPlayerLocation", LocationEmployee.class);
		get.addQueryString("player", player.getName());
		get.addQueryString("locationID", Long.toString(employeeCap.getLocationID()));
		
		DataRequestTask task = new DataRequestTask(get);
		employeeStoreModeRequests.put(player.getUniqueID(), task);
		DataRequestQueue.INSTANCE.addTask(task);
	}
	
	public static void dequeueStoreModeUpdate(EntityPlayerMP player)
	{
		employeeStoreModeRequests.remove(player.getUniqueID());
	}
	
	public static ArrayList<DataRequestTask> priceLookupTasks = new ArrayList<>();
	private static void handlePriceLookups()
	{
		ArrayList<DataRequestTask> tasksToRemove = new ArrayList<>();
		for(DataRequestTask task : priceLookupTasks)
		{
			if (task.getStatus() == DataRequestTaskStatus.Complete)
			{				
				DataAccess access = task.getTask();
				if (access.getRequestSuccessful())
				{
					BlockPos registerPos = BlockPos.fromLong((long)task.getData().get("pos"));
					int slot = (int)task.getData().get("slot");
					int dimensionID = (int)task.getData().get("dimId");
					
					World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dimensionID);
					if (world != null)
					{
						TileEntity te = world.getTileEntity(registerPos);
						if (te instanceof TileEntityRegister)
						{
							TileEntityRegister register = (TileEntityRegister)te;
							TileEntityRegister.RegisterItemHandler itemHandler = (TileEntityRegister.RegisterItemHandler)register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
							LocationItem locationItem = access.getResult(LocationItem.class);
							itemHandler.setPrice(slot, locationItem.BasePrice);
						}
					}
				}
				tasksToRemove.add(task);
			}
		}
		
		for(DataRequestTask taskToRemove : tasksToRemove)
		{
			priceLookupTasks.remove(taskToRemove);
		}
	}
	
	public static ArrayList<DataRequestTask> fetchATMAccountsRequests = new ArrayList<>();
	private static void handleFetchATMAccounts()
	{
		if (checkerCounter != 10 || fetchATMAccountsRequests.size() <= 0)
		{
			return;
		}
		
		ArrayList<DataRequestTask> tasksToRemove = new ArrayList<>();
		for(DataRequestTask task : fetchATMAccountsRequests)
		{
			if (task.getStatus() == DataRequestTaskStatus.Complete)
			{
				ArrayList<Account> accounts = new ArrayList<>();
				String error = "";
				
				UUID playerID = (UUID)task.getData().get("playerID");
				DataAccess access = task.getTask();
				if (access.getRequestSuccessful())
				{
					Account[] accountArray = access.getResult(Account[].class);
					if (accountArray != null)
					{
						accounts = new ArrayList<Account>(Arrays.asList(accountArray));
					}
				}
				else
				{
					GenericErrorResponse errorResponse = access.getResult(GenericErrorResponse.class);
					if (errorResponse != null)
					{
						error = errorResponse.message;
					}
					
					if (error == "" || error == null)
					{
						error = "An unknown error occurred";
					}
				}
				
				FetchAccountsResponsePacket response = new FetchAccountsResponsePacket();
				response.accounts = accounts;
				response.error = error;
				
				EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
				
				PacketHandler.INSTANCE.sendTo(response, player);
				tasksToRemove.add(task);
			}
		}
		
		for(DataRequestTask task : tasksToRemove)
		{
			fetchATMAccountsRequests.remove(task);
		}
	}

	public static ArrayList<DataRequestTask> atmWithdrawRequests = new ArrayList<>();
	private static void handleATMWithdrawRequests()
	{
		if (checkerCounter != 3 || atmWithdrawRequests.size() <= 0)
		{
			return;
		}
		
		ArrayList<DataRequestTask> tasksToRemove = new ArrayList<>();
		for(DataRequestTask task : atmWithdrawRequests)
		{
			if (task.getStatus() != DataRequestTaskStatus.Complete)
			{
				continue;
			}
			
			UUID playerID = (UUID)task.getData().get("playerID");
			EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
			
			WithdrawATMResponsePacket response = new WithdrawATMResponsePacket();
			GenericErrorResponse error = task.getTask().getResult(GenericErrorResponse.class);
			
			if (error != null)
			{
				response.error = error.message;
			}
			else if (task.getTask().getRequestSuccessful())
			{
				BlockPos dispensePos = (BlockPos)task.getData().get("dispensePos");
				BigDecimal amount = (BigDecimal)task.getData().get("amount");
				List<ItemStack> moneyItems = ItemMoney.getMoneyStackForAmount(amount);
				
				for(ItemStack money : moneyItems)
				{
					InventoryHelper.spawnItemStack(player.world, dispensePos.getX(), dispensePos.getY(), dispensePos.getZ(), money);
				}
			}
			
			PacketHandler.INSTANCE.sendTo(response, player);
			
			tasksToRemove.add(task);
		}
		
		for(DataRequestTask task : tasksToRemove)
		{
			atmWithdrawRequests.remove(task);
		}
	}

	public static ArrayList<DataRequestTask> atmDepositRequests = new ArrayList<>();
	private static void handleATMDepositRequests()
	{
		if (checkerCounter != 13 || atmDepositRequests.size() <= 0)
		{
			return;
		}
		
		ArrayList<DataRequestTask> tasksToRemove = new ArrayList<>();
		for(DataRequestTask task : atmDepositRequests)
		{
			if (task.getStatus() != DataRequestTaskStatus.Complete)
			{
				continue;
			}
			
			UUID playerID = (UUID)task.getData().get("playerID");
			EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
			
			DepositATMResponsePacket response = new DepositATMResponsePacket();
			GenericErrorResponse error = task.getTask().getResult(GenericErrorResponse.class);
			
			if (error != null)
			{
				response.error = error.message;
				
				BigDecimal amount = (BigDecimal)task.getData().get("amount");
				List<ItemStack> moneyStacks = ItemMoney.getMoneyStackForAmount(amount);
				for(ItemStack money : moneyStacks)
				{
					InventoryHelper.spawnItemStack(player.world, player.posX, player.posY, player.posZ, money);
				}
			}
			
			PacketHandler.INSTANCE.sendTo(response, player);
			
			tasksToRemove.add(task);
		}
		
		for(DataRequestTask task : tasksToRemove)
		{
			atmDepositRequests.remove(task);
		}
	}

	public static ArrayList<DataRequestTask> newCardRequests = new ArrayList<>();
	public static void handleNewCardRequests()
	{
		if (checkerCounter != 15 || newCardRequests.size() <= 0)
		{
			return;
		}
		
		ArrayList<DataRequestTask> tasksToRemove = new ArrayList<>();
		for(DataRequestTask task : newCardRequests)
		{
			if (task.getStatus() != DataRequestTaskStatus.Complete)
			{
				continue;
			}
			
			UUID playerID = (UUID)task.getData().get("playerID");
			EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
			
			CreateNewDebitCardATMResponsePacket response = new CreateNewDebitCardATMResponsePacket();
			DebitCard debitCardResponse = task.getTask().getResult(DebitCard.class);
			GenericErrorResponse error = task.getTask().getResult(GenericErrorResponse.class);
			
			if (debitCardResponse.DebitCardID != 0)
			{
				if (debitCardResponse.Account == null)
				{
					response.error = "No Account was associated with card";
				}
				else
				{
					ItemDebitCard.EnumDebitCardType type = null;
					
					if (debitCardResponse.Account.CompanyID != 0) 
					{
						type = ItemDebitCard.EnumDebitCardType.Business;
					}
					else if (debitCardResponse.Account.GovernmentID != 0)
					{
						type = ItemDebitCard.EnumDebitCardType.Government;
					}
					
					if (type == null)
					{
						response.error = "No Card found for this type of Account";
					}
					else
					{
						ItemStack cardStack = new ItemStack(ItemDebitCard.debitCardsByType.get(type), 1);
						IDebitCard debitCardCap = cardStack.getCapability(CapabilityDebitCard.DEBIT_CARD_CAPABILITY, null);
						debitCardCap.setCardNumber(debitCardResponse.CardNumber);
						
						BlockPos spawnPos = (BlockPos)task.getData().get("spawnPos");
						InventoryHelper.spawnItemStack(player.world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), cardStack);
					}
				}
			}
			else if (error != null)
			{
				response.error = error.message;
			}
			
			PacketHandler.INSTANCE.sendTo(response, player);
			
			tasksToRemove.add(task);
		}
		
		for(DataRequestTask task : tasksToRemove)
		{
			newCardRequests.remove(task);
		}
	}
}
