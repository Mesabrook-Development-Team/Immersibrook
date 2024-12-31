package com.mesabrook.ib.util.handlers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.apimodels.account.Account;
import com.mesabrook.ib.apimodels.account.DebitCard;
import com.mesabrook.ib.apimodels.company.EmployeeToDoItem;
import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.capability.debitcard.CapabilityDebitCard;
import com.mesabrook.ib.capability.debitcard.IDebitCard;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.items.commerce.ItemDebitCard;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.items.commerce.ItemRegisterFluidWrapper;
import com.mesabrook.ib.net.FetchCSNotificationPacket;
import com.mesabrook.ib.net.FetchCSNotificationPacket.FetchTypes;
import com.mesabrook.ib.net.FetchCSNotificationResponsePacket;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.net.atm.CreateNewDebitCardATMResponsePacket;
import com.mesabrook.ib.net.atm.DepositATMResponsePacket;
import com.mesabrook.ib.net.atm.FetchAccountsResponsePacket;
import com.mesabrook.ib.net.atm.WithdrawATMResponsePacket;
import com.mesabrook.ib.net.sco.QueryPriceResponsePacket;
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
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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
		handleShelfPriceLookupTasks();
		handlePriceSetTasks();
		handleCompanyToDoQueryTasks();
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
							ItemStack stack = itemHandler.getStackInSlot(slot);
							LocationItem locationItem = access.getResult(LocationItem.class);
							if (locationItem.Item != null && locationItem.Item.IsFluid && stack.hasCapability(ItemRegisterFluidWrapper.CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null)) // Additional math required
							{
								ItemRegisterFluidWrapper.IRegisterFluidWrapper wrapper = stack.getCapability(ItemRegisterFluidWrapper.CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null);
								BigDecimal pricePerMB = locationItem.BasePrice.divide(new BigDecimal(locationItem.Quantity));
								itemHandler.setRegularPrice(slot, pricePerMB.multiply(new BigDecimal(wrapper.getFluidStack().amount)).setScale(2, RoundingMode.HALF_UP));
								
								if (locationItem.CurrentPromotionLocationItem != null && locationItem.CurrentPromotionLocationItem.PromotionPrice != null && pricePerMB.compareTo(locationItem.CurrentPromotionLocationItem.PromotionPrice) != 0)
								{
									BigDecimal promotionPricePerMB = locationItem.CurrentPromotionLocationItem.PromotionPrice.divide(new BigDecimal(locationItem.Quantity));
									itemHandler.setPrice(slot, promotionPricePerMB.multiply(new BigDecimal(wrapper.getFluidStack().amount)).setScale(2, RoundingMode.HALF_UP));
								}
								else
								{
									itemHandler.setPrice(slot, itemHandler.getRegularPrice(slot));
								}
							}
							else
							{
								itemHandler.setRegularPrice(slot, locationItem.BasePrice);
								if (locationItem.CurrentPromotionLocationItem != null && locationItem.CurrentPromotionLocationItem.PromotionPrice != null && locationItem.BasePrice.compareTo(locationItem.CurrentPromotionLocationItem.PromotionPrice) != 0)
								{
									itemHandler.setPrice(slot, locationItem.CurrentPromotionLocationItem.PromotionPrice);
								}
								else
								{
									itemHandler.setPrice(slot, locationItem.BasePrice);
								}
							}
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
						
						ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
						packet.pos = player.getPosition();
						packet.modID = "wbtc";
						packet.soundName = "card_out";
						PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
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
	
	public static ArrayList<DataRequestTask> shelfPriceLookupTasks = new ArrayList<>();
	private static void handleShelfPriceLookupTasks()
	{
		if (checkerCounter != 7 || shelfPriceLookupTasks.size() <= 0)
		{
			return;
		}
		
		ArrayList<DataRequestTask> tasksToRemove = new ArrayList<>();
		for(DataRequestTask task : shelfPriceLookupTasks)
		{
			if (task.getStatus() != DataRequestTaskStatus.Complete)
			{
				continue;
			}
			
			QueryPriceResponsePacket response = new QueryPriceResponsePacket();
			
			GetData get = (GetData)task.getTask();
			if (!get.getRequestSuccessful())
			{
				String consoleMessage = "An error occurred while fetching price";
				GenericErrorResponse error = get.getResult(GenericErrorResponse.class);
				if (error != null)
				{
					consoleMessage += ": " + error.message;
				}
				
				Main.logger.error(consoleMessage);
			}
			
			response.pos = (BlockPos)task.getData().get("shelfPos");
			response.placementID = (int)task.getData().get("placementID");
			response.locationItem = get.getResult(LocationItem.class);
			UUID playerID = (UUID)task.getData().get("playerID");
			
			EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
			PacketHandler.INSTANCE.sendTo(response, player);
			
			tasksToRemove.add(task);
		}
		
		shelfPriceLookupTasks.removeAll(tasksToRemove);
	}

	public static ArrayList<DataRequestTask> priceSetTasks = new ArrayList<>();
	private static void handlePriceSetTasks()
	{
		if (checkerCounter != 3 || priceSetTasks.size() <= 0)
		{
			return;
		}
		
		ArrayList<DataRequestTask> tasksToRemove = new ArrayList<>();
		for(DataRequestTask task : priceSetTasks)
		{
			if (task.getStatus() != DataRequestTaskStatus.Complete)
			{
				continue;
			}
			
			DataAccess access = task.getTask();
			if (!access.getRequestSuccessful())
			{
				String errorLog = "An error occurred while trying to set prices";
				GenericErrorResponse errorResponse = access.getResult(GenericErrorResponse.class);
				if (errorResponse != null)
				{
					errorLog += ": " + errorResponse.message;
				}
				
				Main.logger.error(errorLog);
				tasksToRemove.add(task);
				continue;
			}
			
			LocationItem item = access.getResult(LocationItem.class);
			QueryPriceResponsePacket responsePacket = new QueryPriceResponsePacket();
			responsePacket.locationItem = item;
			responsePacket.pos = (BlockPos)task.getData().get("tagStationPos");
			responsePacket.placementID = 0;
			
			UUID playerID = (UUID)task.getData().get("playerID");
			EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
			PacketHandler.INSTANCE.sendTo(responsePacket, player);
			tasksToRemove.add(task);
		}
		
		priceSetTasks.removeAll(tasksToRemove);
	}

	public static ArrayList<DataRequestTask> companyToDoQueryTasks = new ArrayList<>();
	private static void handleCompanyToDoQueryTasks()
	{
		if (checkerCounter != 19 || companyToDoQueryTasks.size() <= 0)
		{
			return;
		}
		
		ArrayList<DataRequestTask> tasksToRemove = new ArrayList<>();
		for(DataRequestTask task : companyToDoQueryTasks)
		{
			if (task.getStatus() != DataRequestTaskStatus.Complete.Complete)
			{
				continue;
			}
			
			tasksToRemove.add(task);
			
			if (!task.getTask().getRequestSuccessful())
			{
				continue;
			}
			
			UUID playerID = (UUID)task.getData().get("playerID");
			EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);			
			EmployeeToDoItem[] toDoItems = task.getTask().getResult(EmployeeToDoItem[].class);
			
			if (player == null || toDoItems == null)
			{
				continue;
			}
			
			FetchCSNotificationPacket.FetchTypes fetchType = (FetchCSNotificationPacket.FetchTypes)task.getData().get("fetchType");
			if (fetchType == FetchTypes.InitialLogin)
			{				
				Stream<EmployeeToDoItem> itemsStream = Arrays.stream(toDoItems);
				long urgentCount = itemsStream.filter(e -> e.getTextFormat() == TextFormatting.RED).collect(Collectors.counting());
				itemsStream = Arrays.stream(toDoItems);
				long importantCount = itemsStream.filter(e -> e.getTextFormat() == TextFormatting.YELLOW).collect(Collectors.counting());
				itemsStream = Arrays.stream(toDoItems);
				long infoCount = itemsStream.filter(e -> e.getTextFormat() == TextFormatting.BLUE).collect(Collectors.counting());
				
				if (urgentCount > 0 || importantCount > 0 || infoCount > 0)
				{
					String message = "";
					if (urgentCount > 0)
					{
						message = "" + TextFormatting.RED + urgentCount + " urgent task(s)";
						
						if (importantCount > 0 || infoCount > 0)
						{
							message += ", ";
						}
					}
					
					if (importantCount > 0)
					{
						message += "" + TextFormatting.YELLOW + importantCount + " important task(s)";
						
						if (infoCount > 0)
						{
							message += ", ";
						}
					}
					
					if (infoCount > 0)
					{
						message += "" + TextFormatting.BLUE + infoCount + " informational task(s)";
					}
					
					TextComponentString textToPlayer = new TextComponentString("You have " + message + TextFormatting.RESET + ". Click to view.");
					textToPlayer.setStyle(new Style()
							.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to view details")))
							.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ib csnotifications")));
					
					player.sendMessage(textToPlayer);
				}
			}
			else
			{
				FetchCSNotificationResponsePacket response = new FetchCSNotificationResponsePacket();
				response.employeeToDoItems.addAll(Arrays.stream(toDoItems).collect(Collectors.toList()));
				response.fetchType = fetchType;
				PacketHandler.INSTANCE.sendTo(response, player);
			}
		}
		
		for(DataRequestTask taskToRemove : tasksToRemove)
		{
			companyToDoQueryTasks.remove(taskToRemove);
		}
	}
}
