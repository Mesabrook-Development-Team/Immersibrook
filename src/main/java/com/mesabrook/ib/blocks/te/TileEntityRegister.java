package com.mesabrook.ib.blocks.te;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.apimodels.company.Register;
import com.mesabrook.ib.apimodels.company.RegisterStatus;
import com.mesabrook.ib.apimodels.company.RegisterStatus.Statuses;
import com.mesabrook.ib.blocks.BlockRegister;
import com.mesabrook.ib.capability.debitcard.CapabilityDebitCard;
import com.mesabrook.ib.capability.debitcard.IDebitCard;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.commerce.ItemDebitCard;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.items.commerce.ItemWallet;
import com.mesabrook.ib.net.sco.POSCardShowMessagePacket;
import com.mesabrook.ib.net.sco.POSInitializeRegisterResponsePacket;
import com.mesabrook.ib.net.sco.POSOpenCardReaderGUIPacket;
import com.mesabrook.ib.util.apiaccess.DataAccess;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataAccess.AuthenticationStatus;
import com.mesabrook.ib.util.apiaccess.DataAccess.GenericErrorResponse;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.DataRequestTaskStatus;
import com.mesabrook.ib.util.apiaccess.GetData;
import com.mesabrook.ib.util.apiaccess.PostData;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mesabrook.ib.util.handlers.ServerTickHandler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityRegister extends TileEntity implements ITickable {
	private String name = "";
	private UUID identifier = new UUID(0L, 0L);
	private long locationIDOwner = 0;
	private BigDecimal currentTaxRate = new BigDecimal(0);
	private BigDecimal tenderedAmount = new BigDecimal(0);
	private final RegisterItemHandler itemHandler = new RegisterItemHandler(this);
	private final SecurityBoxHandler securityBoxHandler = new SecurityBoxHandler(this);
	private ItemStack insertedCardStack;
	private ArrayList<TrackedFluidData> fluidData = new ArrayList<>();
	
	RegisterStatuses registerStatus = RegisterStatuses.Uninitialized;
	private String tenderFailureMessage = "";

	// Runtime data
	DataRequestTask statusUpdateTask = null;
	DataRequestTask initializeTask = null;
	DataRequestTask paymentTask = null;
	Calendar nextUpdateTime = Calendar.getInstance();
	int transactionCompleteCounter = 0;
	
	// Data methods
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		identifier = compound.getUniqueId("identifier");
		name = compound.getString("name");
		registerStatus = RegisterStatuses.values()[compound.getInteger("registerStatus")];
		locationIDOwner = compound.getLong("locationIDOwner");
		if (compound.hasKey("currentTaxRate"))
		{
			currentTaxRate = new BigDecimal(compound.getString("currentTaxRate"));
		}
		if (compound.hasKey("tenderedAmount"))
		{
			tenderedAmount = new BigDecimal(compound.getString("tenderedAmount"));
		}
		if (compound.hasKey("inventory"))
		{
			itemHandler.deserializeNBT(compound.getCompoundTag("inventory"));
		}
		if (compound.hasKey("tenderFailureMessage"))
		{
			tenderFailureMessage = compound.getString("tenderFailureMessage");
		}
		insertedCardStack = null;
		if (compound.hasKey("insertedCardStack"))
		{
			insertedCardStack = new ItemStack(compound.getCompoundTag("insertedCardStack"));
		}
		if (compound.hasKey("securityBoxInventory"))
		{
			securityBoxHandler.deserializeNBT(compound.getCompoundTag("securityBoxInventory"));
		}
		
		fluidData.clear();
		NBTTagList trackedFluidData = compound.getTagList("trackedFluidData", NBT.TAG_COMPOUND);
		for(NBTBase nbt : trackedFluidData)
		{
			if (nbt instanceof NBTTagCompound)
			{
				TrackedFluidData newTrackedData = new TrackedFluidData();
				newTrackedData.deserializeNBT((NBTTagCompound)nbt);
				fluidData.add(newTrackedData);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setUniqueId("identifier", identifier);
		compound.setString("name", name);
		compound.setInteger("registerStatus", registerStatus.ordinal());
		compound.setLong("locationIDOwner", locationIDOwner);
		compound.setString("currentTaxRate", currentTaxRate.toPlainString());
		compound.setString("tenderedAmount", tenderedAmount.toPlainString());
		compound.setTag("inventory", itemHandler.serializeNBT());
		compound.setString("tenderFailureMessage", tenderFailureMessage);
		if (insertedCardStack != null)
		{
			compound.setTag("insertedCardStack", insertedCardStack.serializeNBT());
		}
		compound.setTag("securityBoxInventory", securityBoxHandler.serializeNBT());
		NBTTagList fluidDataList = new NBTTagList();
		for(TrackedFluidData data : fluidData)
		{
			fluidDataList.appendTag(data.serializeNBT());
		}
		compound.setTag("trackedFluidData", fluidDataList);
		return super.writeToNBT(compound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		compound.setInteger("registerStatus", registerStatus.ordinal());
		compound.setString("name", name);
		compound.setLong("locationIDOwner", locationIDOwner);
		compound.setString("currentTaxRate", currentTaxRate.toPlainString());
		compound.setString("tenderedAmount", tenderedAmount.toPlainString());
		compound.setTag("inventory", itemHandler.serializeNBT());
		compound.setString("tenderFailureMessage", tenderFailureMessage);
		if (insertedCardStack != null)
		{
			compound.setTag("insertedCardStack", insertedCardStack.serializeNBT());
		}
		compound.setTag("securityBoxInventory", securityBoxHandler.serializeNBT());
		NBTTagList fluidDataList = new NBTTagList();
		for(TrackedFluidData data : fluidData)
		{
			fluidDataList.appendTag(data.serializeNBT());
		}
		compound.setTag("trackedFluidData", fluidDataList);
		return compound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		name = tag.getString("name");
		locationIDOwner = tag.getLong("locationIDOwner");
		currentTaxRate = new BigDecimal(tag.getString("currentTaxRate"));
		tenderedAmount = new BigDecimal(tag.getString("tenderedAmount"));
		itemHandler.dumpInventory();
		if (tag.hasKey("inventory"))
		{
			itemHandler.deserializeNBT(tag.getCompoundTag("inventory"));
		}
		registerStatus = RegisterStatuses.values()[tag.getInteger("registerStatus")];
		tenderFailureMessage = tag.getString("tenderFailureMessage");
		insertedCardStack = null;
		if (tag.hasKey("insertedCardStack"))
		{
			insertedCardStack = new ItemStack(tag.getCompoundTag("insertedCardStack"));
		}
		
		if (tag.hasKey("securityBoxInventory"))
		{
			securityBoxHandler.deserializeNBT(tag.getCompoundTag("securityBoxInventory"));
		}

		fluidData.clear();
		NBTTagList trackedFluidData = tag.getTagList("trackedFluidData", NBT.TAG_COMPOUND);
		for(NBTBase nbt : trackedFluidData)
		{
			if (nbt instanceof NBTTagCompound)
			{
				TrackedFluidData newTrackedData = new TrackedFluidData();
				newTrackedData.deserializeNBT((NBTTagCompound)nbt);
				fluidData.add(newTrackedData);
			}
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		}
		
		return null;
	}
	
	// Getters/setters
	public RegisterStatuses getRegisterStatus() { return registerStatus; }
	
	public void setRegisterStatus(RegisterStatuses registerStatus)
	{
		if (this.registerStatus == registerStatus)
		{
			return;
		}
		
		this.registerStatus = registerStatus;
		if (this.registerStatus != RegisterStatuses.PaymentSelect)
		{
			setTenderFailureMessage("");
		}
		
		markDirty();
		
		if (world != null)
		{
			getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
		}
	}

	public long getLocationIDOwner() {
		return locationIDOwner;
	}

	public void setLocationIDOwner(long locationIDOwner) {
		if (this.locationIDOwner == locationIDOwner)
		{
			return;
		}
		
		this.locationIDOwner = locationIDOwner;
		
		markDirty();
		
		if (world != null)
		{
			getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
		}
	}
	
	public void setCurrentTaxRate(BigDecimal currentTaxRate)
	{
		if (this.currentTaxRate.equals(currentTaxRate))
		{
			return;
		}
		
		this.currentTaxRate = currentTaxRate;
		markDirty();
		
		if (world != null)
		{
			getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
		}
	}
	
	public BigDecimal getCurrentTaxRate()
	{
		return currentTaxRate;
	}
	
	public void setTenderedAmount(BigDecimal tenderedAmount)
	{
		if (this.tenderedAmount.equals(tenderedAmount))
		{
			return;
		}
		
		this.tenderedAmount = tenderedAmount;
		markDirty();
		
		if (world != null)
		{
			getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
		}
	}
	
	public BigDecimal getTenderedAmount()
	{
		return tenderedAmount;
	}

	public String getTenderFailureMessage() {
		return tenderFailureMessage;
	}

	public void setTenderFailureMessage(String tenderFailureMessage) {
		if (this.tenderFailureMessage.equalsIgnoreCase(tenderFailureMessage))
		{
			return;
		}
		
		this.tenderFailureMessage = tenderFailureMessage;
		markDirty();
		
		if (world != null)
		{
			getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
		}
	}
	
	public ItemStack getInsertedCardStack()
	{
		return insertedCardStack;
	}

	public void clearInsertedCardStack()
	{
		insertedCardStack = null;
		markDirty();
		
		if (world != null)
		{
			getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
		}
	}
	
	public SecurityBoxHandler getSecurityBoxInventory()
	{
		return securityBoxHandler;
	}
	
	public BigDecimal getCurrentTotal()
	{
		TileEntityRegister.RegisterItemHandler handler = (TileEntityRegister.RegisterItemHandler)getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		BigDecimal runningTotal = new BigDecimal(0);
		for(int i = 0; i < handler.getSlots(); i++)
		{
			BigDecimal price = handler.getPrice(i);
			if (price != null)
			{
				runningTotal = price.add(runningTotal);
			}
		}
		
		return runningTotal.setScale(2, RoundingMode.HALF_UP);
	}
	
	public void applyCashTender(BigDecimal amount)
	{
		tenderedAmount = tenderedAmount.add(amount);
		BigDecimal amountDue = getDueAmount();
		if (amountDue.compareTo(new BigDecimal(0)) <= 0)
		{
			onPaid();
		}
		else
		{
			markDirty();
			
			if (world != null)
			{
				getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
			}
		}
	}
	
	public BigDecimal getDueAmount()
	{
		BigDecimal runningTotal = new BigDecimal(0);
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			BigDecimal itemAmount = itemHandler.getPrice(i);
			if (itemAmount == null)
			{
				continue;
			}
			
			runningTotal = runningTotal.add(itemAmount);
		}
		
		runningTotal = runningTotal.multiply(currentTaxRate.divide(new BigDecimal(100)).add(new BigDecimal(1))).setScale(2, RoundingMode.HALF_UP);
		runningTotal = runningTotal.subtract(tenderedAmount);
		return runningTotal;
	}
	
	private void onPaid()
	{
		onPaid(null, null);
	}
	
	private void onPaid(DebitCardPaymentParameter debitCardInfo, UUID playerIDActor)
	{
		ArrayList<StoreSaleSubParameter> storeSales = new ArrayList<>();
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack.isEmpty())
			{
				continue;
			}
			
			StoreSaleSubParameter param = new StoreSaleSubParameter();
			param.Name = stack.getDisplayName();
			param.Amount = stack.getCount();
			param.SaleAmount = itemHandler.getPrice(i);
			storeSales.add(param);
		}
		
		setRegisterStatus(RegisterStatuses.TransactionProcessing);
		
		StoreSaleParameter saleParam = new StoreSaleParameter();
		saleParam.StoreItems = storeSales;
		saleParam.DebitCardInformation = debitCardInfo;
		
		PostData post = new PostData(API.Company, "StoreSaleItemIBAccess/Post", saleParam, new Class<?>[0]);
		post.getHeaderOverrides().put("RegisterIdentifier", identifier.toString());
		
		paymentTask = new DataRequestTask(post);
		if (playerIDActor != null)
		{
			paymentTask.getData().put("playerIDActor", playerIDActor);
		}
		if (debitCardInfo != null)
		{
			paymentTask.getData().put("cashBack", debitCardInfo.AuthorizedAmount.subtract(debitCardInfo.PaymentAmount));
			paymentTask.getData().put("cardPaymentAmount", debitCardInfo.PaymentAmount);
		}
		DataRequestQueue.INSTANCE.addTask(paymentTask);
	}
	
	public void onCardReaderUse(ItemStack stack, EntityPlayerMP player)
	{
		if (registerStatus == RegisterStatuses.PaymentSelect || registerStatus == RegisterStatuses.PaymentCard)
		{
			if (stack.getItem() instanceof ItemDebitCard)
			{
				insertedCardStack = stack.copy();
				stack.shrink(stack.getCount());
				setRegisterStatus(RegisterStatuses.PaymentCardInUse);
			}
			
			if (stack.getItem() instanceof ItemWallet && stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
			{
				IItemHandler walletInventory = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				ItemStack firstCardSlotStack = walletInventory.getStackInSlot(0);
				if (firstCardSlotStack.hasCapability(CapabilityDebitCard.DEBIT_CARD_CAPABILITY, null))
				{
					insertedCardStack = firstCardSlotStack.copy();
					walletInventory.extractItem(0, insertedCardStack.getCount(), false);
					setRegisterStatus(RegisterStatuses.PaymentCardInUse);
				}
			}
		}
		
		POSOpenCardReaderGUIPacket openCardReaderGUI = new POSOpenCardReaderGUIPacket();
		openCardReaderGUI.atmPos = getPos();
		PacketHandler.INSTANCE.sendTo(openCardReaderGUI, player);
	}
	
	public void performDebitCardProcessing(String enteredPIN, BigDecimal authorizedAmount, BigDecimal cashBack, UUID playerIDActor)
	{
		if (authorizedAmount.subtract(cashBack).compareTo(getDueAmount()) != 0)
		{
			// TODO: Going to need to display an error here
			return;
		}
		
		ItemStack cardStack = getInsertedCardStack();
		if (!cardStack.hasCapability(CapabilityDebitCard.DEBIT_CARD_CAPABILITY, null))
		{
			return;
		}
		
		tenderedAmount = getTenderedAmount().add(authorizedAmount); // Skipping setter because onPaid will do the syncing
		
		IDebitCard debitCard = cardStack.getCapability(CapabilityDebitCard.DEBIT_CARD_CAPABILITY, null);
		
		DebitCardPaymentParameter param = new DebitCardPaymentParameter();
		param.RegisterIdentifier = identifier;
		param.CardNumber = debitCard.getCardNumber();
		param.PIN = enteredPIN;
		param.AuthorizedAmount = authorizedAmount;
		param.PaymentAmount = authorizedAmount.subtract(cashBack);
		
		onPaid(param, playerIDActor);
	}
	
	// Operational methods
	@Override
	public void update() {	
		if (getWorld().isRemote)
		{
			return;
		}
		
		if (DataAccess.getAuthenticationStatus() == AuthenticationStatus.LoggedOut)
		{
			if (registerStatus != RegisterStatuses.WaitingForNetwork)
			{
				setRegisterStatus(RegisterStatuses.WaitingForNetwork);
			}
			return;
		}
		
		if (registerStatus == RegisterStatuses.Uninitialized)
		{
			if (initializeTask != null && initializeTask.getStatus() == DataRequestTaskStatus.Complete)
			{
				POSInitializeRegisterResponsePacket response = new POSInitializeRegisterResponsePacket();
				response.pos = getPos();
				
				UUID playerID = (UUID)initializeTask.getData().get("initializer");
				Register successResult = initializeTask.getTask().getResult(Register.class);
				GenericErrorResponse failedResult = initializeTask.getTask().getResult(GenericErrorResponse.class);
				if (successResult != null)
				{
					identifier = successResult.Identifier;
					setRegisterStatus(RegisterStatuses.WaitingForNetwork); // marks dirty automatically
					
					response.wasSuccessful = true;
				}
				else if (failedResult != null)
				{
					setRegisterStatus(RegisterStatuses.Uninitialized);
					
					response.wasSuccessful = false;
					response.error = failedResult.message;
				}
				else
				{
					setRegisterStatus(RegisterStatuses.Uninitialized);
					
					response.wasSuccessful = false;
					response.error = "An unknown error occurred";
				}
				
				initializeTask = null;
				PacketHandler.INSTANCE.sendTo(response, FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID));
			}
			
			return;
		}
		
		if ((identifier == null || identifier.equals(new UUID(0L, 0L))) && registerStatus != RegisterStatuses.Uninitialized)
		{
			setRegisterStatus(RegisterStatuses.Uninitialized);
			return;
		}
		
		if (nextUpdateTime.before(Calendar.getInstance()) && statusUpdateTask == null)
		{
			createStatusUpdateTask();
		}
		
		if (statusUpdateTask != null && statusUpdateTask.getStatus() == DataRequestTaskStatus.Complete)
		{
			nextUpdateTime = Calendar.getInstance();
			nextUpdateTime.add(Calendar.SECOND, 10);
			
			if (!statusUpdateTask.getTask().getRequestSuccessful())
			{
				setRegisterStatus(RegisterStatuses.WaitingForNetwork);
				statusUpdateTask = null;
				return;
			}
			
			Register result = statusUpdateTask.getTask().getResult(Register.class);
			if (result != null)
			{
				if (result.CurrentStatus != null && result.CurrentStatus.Status != null)
				{
					switch(result.CurrentStatus.Status)
					{
						case Offline:
							setRegisterStatus(RegisterStatuses.Offline);
							break;
						case InternalStorageFull:
							setRegisterStatus(RegisterStatuses.InternalStorageFull);
							break;
						case Online:
							RegisterStatuses currentStatus = getRegisterStatus();
							if (!currentStatus.isOperationalState())
							{
								if (hasItemsForSession())
								{
									setRegisterStatus(RegisterStatuses.InSession);
								}
								else
								{
									setRegisterStatus(RegisterStatuses.Online);
								}
							}
							break;
					}
				}
				
				if (result.LocationID != null)
				{
					setLocationIDOwner(result.LocationID);
				}
				
				if (result.CurrentTaxRate == null)
				{
					setCurrentTaxRate(new BigDecimal(0));
				}
				else
				{
					setCurrentTaxRate(result.CurrentTaxRate);
				}
				
				// Make sure the security box inventory is not full
				int currentlyStoredCount = 0;
				for(int i = 0; i < securityBoxHandler.getSlots(); i++)
				{
					ItemStack boxStack = securityBoxHandler.getStackInSlot(i);
					if (boxStack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
					{
						currentlyStoredCount += boxStack.getCount();
					}
				}
				
				if (currentlyStoredCount >= securityBoxHandler.getSlots() * ModItems.SECURITY_BOX.getItemStackLimit(new ItemStack(ModItems.SECURITY_BOX)) && getRegisterStatus() != RegisterStatuses.InternalStorageFull)
				{
					setRegisterStatus(RegisterStatuses.InternalStorageFull);
					notifyMesaSuiteOfStatusChange(Statuses.InternalStorageFull, "Automatic");
				}
			}
			else
			{
				setRegisterStatus(RegisterStatuses.WaitingForNetwork);
			}
			statusUpdateTask = null;
		}
		
		if (paymentTask != null && paymentTask.getStatus() == DataRequestTaskStatus.Complete)
		{
			onProcessingTaskCompleted();
			return;
		}
		
		if (registerStatus == RegisterStatuses.TransactionComplete)
		{
			if (transactionCompleteCounter++ >= 60)
			{
				transactionCompleteCounter = 0;
				setRegisterStatus(RegisterStatuses.Online);
			}
		}
	}

	private void onProcessingTaskCompleted() {
		BlockPos spawnPos = getPos().offset(world.getBlockState(getPos()).getValue(BlockRegister.FACING));
		
		if (paymentTask.getTask().getRequestSuccessful())
		{				
			for(int i = 0; i < itemHandler.getSlots(); i++)
			{
				ItemStack stack = itemHandler.getStackInSlot(i).copy();
				if (stack.isEmpty())
				{
					continue;
				}
				
				if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
				{
					ISecuredItem securedItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
					ItemStack securedItemStack = stack.copy();
					stack = securedItem.getInnerStack();
					
					securedItem = securedItemStack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
					securedItem.setHomeLocation(null);
					securedItem.setHomeSpot(-1);
					securedItem.setInnerStack(null);
					securedItem.setLocation(null);
					securedItem.setResetDistance(0);
					
					securityBoxHandler.insertItem(securedItemStack);
				}
				
				InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), stack);
			}
			
			BigDecimal change = getDueAmount().abs();
			if(change.compareTo(new BigDecimal(0)) > 0)
			{
				for(ItemStack stack : ItemMoney.getMoneyStackForAmount(change))
				{
					InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), stack);
				}
			}
			
			itemHandler.dumpInventory();				
			
			setTenderedAmount(new BigDecimal(0));
			setRegisterStatus(RegisterStatuses.TransactionComplete);
					
			if (paymentTask.getData().containsKey("playerIDActor"))
			{
				UUID playerID = (UUID)paymentTask.getData().get("playerIDActor");
				EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
				
				POSCardShowMessagePacket showMessage = new POSCardShowMessagePacket();
				showMessage.message = "Accepted";
				PacketHandler.INSTANCE.sendTo(showMessage, player);
			}
		}
		else
		{
			BigDecimal cashBack = new BigDecimal(0);
			if (paymentTask.getData().containsKey("cashBack"))
			{
				cashBack = (BigDecimal)paymentTask.getData().get("cashBack");
			}
			
			BigDecimal cardPaymentAmount = new BigDecimal(0);
			if (paymentTask.getData().containsKey("cardPaymentAmount"))
			{
				cardPaymentAmount = (BigDecimal)paymentTask.getData().get("cardPaymentAmount");
			}
			
			setRegisterStatus(RegisterStatuses.PaymentSelect);
			
			GenericErrorResponse errorMessage = paymentTask.getTask().getResult(GenericErrorResponse.class);
			if (errorMessage != null)
			{
				setTenderFailureMessage(errorMessage.message);
				
				if (paymentTask.getData().containsKey("playerIDActor"))
				{
					UUID playerID = (UUID)paymentTask.getData().get("playerIDActor");
					EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
					
					POSCardShowMessagePacket showMessage = new POSCardShowMessagePacket();
					showMessage.message = errorMessage.message;
					PacketHandler.INSTANCE.sendTo(showMessage, player);
				}
			}
			
			List<ItemStack> tenderedStacks = ItemMoney.getMoneyStackForAmount(tenderedAmount.subtract(cashBack).subtract(cardPaymentAmount));
			for(ItemStack stack : tenderedStacks)
			{
				InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), stack);
			}
			
			setTenderedAmount(new BigDecimal(0));
		}
		
		paymentTask = null;
		
		if (getInsertedCardStack() != null)
		{
			InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), insertedCardStack);
			clearInsertedCardStack();
		}
	}
	
	private void createStatusUpdateTask()
	{
		GetData get = new GetData(API.Company, "RegisterAccess/Get", Register.class);
		get.getHeaderOverrides().put("RegisterIdentifier", identifier.toString());
		statusUpdateTask = new DataRequestTask(get);
		DataRequestQueue.INSTANCE.addTask(statusUpdateTask);
	}
	
	public void initialize(String identifier, UUID initializer, String playerName)
	{
		setRegisterStatus(RegisterStatuses.Initializing);
		
		GetData get = new GetData(API.Company, "RegisterAccess/GetByIdentifier/" + identifier, Register.class);
		get.getHeaderOverrides().put("PlayerName", playerName);
		initializeTask = new DataRequestTask(get);
		initializeTask.getData().put("initializer", initializer);
		DataRequestQueue.INSTANCE.addTask(initializeTask);
	}
	
	public boolean insertItemInFirstAvailableSlot(ItemStack stack)
	{
		return insertItemInFirstAvailableSlot(stack, false);
	}
	
	public boolean insertItemInFirstAvailableSlot(ItemStack stack, boolean simulate)
	{
		IItemHandler itemHandler = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			ItemStack stackInSlot = itemHandler.getStackInSlot(i);
			if (stackInSlot.isEmpty())
			{
				itemHandler.insertItem(i, stack, simulate);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasItemsForSession()
	{
		IItemHandler itemHandler = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			ItemStack stackInSlot = itemHandler.getStackInSlot(i);
			if (!stackInSlot.isEmpty())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSecurityBoxCapacityForStack(ItemStack securedItemStack)
	{
		if (!securedItemStack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
		{
			return true;
		}
		
		int totalSecurityBoxCount = 0;
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			if (itemHandler.getStackInSlot(i).hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				totalSecurityBoxCount++;
			}
		}
		
		for(int i = 0; i < securityBoxHandler.getSlots(); i++)
		{
			ItemStack boxStack = securityBoxHandler.getStackInSlot(i);
			if (boxStack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				totalSecurityBoxCount += boxStack.getCount();
			}
		}
		
		return totalSecurityBoxCount < securityBoxHandler.getSlots() * ModItems.SECURITY_BOX.getItemStackLimit(new ItemStack(ModItems.SECURITY_BOX));
	}
	
	public void notifyMesaSuiteOfStatusChange(RegisterStatus.Statuses status, String performer)
	{
		// Cancel status update
		statusUpdateTask = null;
		nextUpdateTime = Calendar.getInstance();
		nextUpdateTime.add(Calendar.SECOND, 10);
		
		RegisterStatus registerStatus = new RegisterStatus();
		registerStatus.Status = status;
		registerStatus.Initiator = performer;
		
		PostData post = new PostData(API.Company, "RegisterAccess/SetStatus", registerStatus, RegisterStatus.class);
		post.getHeaderOverrides().put("RegisterIdentifier", identifier.toString());
		DataRequestTask postTask = new DataRequestTask(post);
		DataRequestQueue.INSTANCE.addTask(postTask);
	}
	
	public enum RegisterStatuses
	{
		WaitingForNetwork(false),
		Uninitialized(false),
		Initializing(false),
		Offline(false),
		InternalStorageFull(false),
		Online(true),
		InSession(true),
		PaymentSelect(true),
		PaymentCash(true),
		PaymentCard(true),
		PaymentCardInUse(true),
		TransactionProcessing(true),
		TransactionComplete(true);
		
		private boolean isOperationalState;
		private RegisterStatuses(boolean isOperationalState)
		{
			this.isOperationalState = isOperationalState;
		}
		
		public boolean isOperationalState()
		{
			return isOperationalState;
		}
	}
	
	public static class TrackedFluidData implements INBTSerializable<NBTTagCompound>
	{
		private BlockPos fluidMeterPos = new BlockPos(0, -1, 0);
		private int lastReading = 0;
		
		public BlockPos getFluidMeterPos() {
			return fluidMeterPos;
		}
		public void setFluidMeterPos(BlockPos fluidMeterPos) {
			this.fluidMeterPos = fluidMeterPos;
		}
		public int getLastReading() {
			return lastReading;
		}
		public void setLastReading(int lastReading) {
			this.lastReading = lastReading;
		}
		
		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setLong("meterPos", getFluidMeterPos().toLong());
			tag.setInteger("lastReading", lastReading);
			return tag;
		}
		
		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			fluidMeterPos = BlockPos.fromLong(nbt.getLong("meterPos"));
			lastReading = nbt.getInteger("lastReading");
		}
	}

	public static class RegisterItemHandler extends ItemStackHandler
	{
		TileEntityRegister register;
		private ArrayList<BigDecimal> prices;
		public RegisterItemHandler(TileEntityRegister register)
		{
			super(100);
			this.register = register;
			prices = new ArrayList<>(100);
			for(int i = 0; i < 100; i++)
			{
				prices.add(null);
			}
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (!register.getRegisterStatus().isOperationalState || register.getLocationIDOwner() == 0 ||
					(stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) && stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getLocationIDOwner() != register.getLocationIDOwner()) ||
					!getStackInSlot(slot).isEmpty())
			{
				return stack;
			}
			
			if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) && !register.hasSecurityBoxCapacityForStack(stack))
			{
				return stack;
			}
			
			if (!simulate && register.getWorld() != null)
			{
				prices.set(slot, null);
				
				if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
				{
					GetData get = new GetData(API.Company, "LocationItemIBAccess/Get", LocationItem.class);
					get.addQueryString("locationID", Long.toString(register.getLocationIDOwner()));
					ItemStack checkStack = stack;
					if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
					{
						ISecuredItem securedItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
						checkStack = securedItem.getInnerStack();
					}
					get.addQueryString("name", checkStack.getDisplayName());
					get.addQueryString("quantity", Integer.toString(checkStack.getCount()));
					
					DataRequestTask task = new DataRequestTask(get);
					task.getData().put("pos", register.getPos().toLong());
					task.getData().put("slot", slot);
					task.getData().put("dimId", register.getWorld().provider.getDimension());
					ServerTickHandler.priceLookupTasks.add(task);
					DataRequestQueue.INSTANCE.addTask(task);
				}
			}
			return super.insertItem(slot, stack, simulate);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return ItemStack.EMPTY;
		}
		
		public ItemStack extractItemInternalOnly(int slot)
		{
			int maxAmountForSlot = getSlotLimit(slot);
			ItemStack retVal = super.extractItem(slot, maxAmountForSlot, false);
			prices.remove(slot);
			prices.add(null);
			for(int i = slot + 1; i < getSlots(); i++)
			{
				ItemStack nextStack = getStackInSlot(i);
				if (!nextStack.isEmpty())
				{
					nextStack = super.extractItem(i, nextStack.getCount(), false);
					super.insertItem(i - 1, nextStack, false);
				}
			}
			
			return retVal;
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			register.setRegisterStatus(RegisterStatuses.InSession);
			register.markDirty();
			
			if (register.world != null)
			{
				register.world.notifyBlockUpdate(register.pos, register.world.getBlockState(register.pos), register.world.getBlockState(register.pos), 3);
			}
		}
		
		public void dumpInventory()
		{
			for(ItemStack stack : stacks)
			{
				stack.shrink(stack.getCount());
			}
			
			for(int i = 0; i < getSlots(); i++)
			{
				prices.set(i, null);
			}
		}
		
		public BigDecimal getPrice(int slot)
		{
			try
			{
				return prices.get(slot);
			}
			catch (Exception ex)
			{
				return null;
			}
		}
		
		public void setPrice(int slot, BigDecimal price)
		{
			prices.set(slot, price);
			onContentsChanged(slot);
		}
	
		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound tag = super.serializeNBT();
			for(int i = 0; i < getSlots(); i++)
			{
				if (prices.size() <= i || prices.get(i) == null)
				{
					continue;
				}
				
				tag.setString("Price" + i, prices.get(i).toPlainString());
			}
			
			return tag;
		}
		
		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			super.deserializeNBT(nbt);
			
			prices = new ArrayList<>(getSlots());
			for(int i = 0; i < getSlots(); i++)
			{
				prices.add(null);
			}
			for(int i = 0; i < getSlots(); i++)
			{
				if (!nbt.hasKey("Price" + i))
				{
					continue;
				}
				
				BigDecimal price = new BigDecimal(nbt.getString("Price" + i));
				prices.set(i, price);
			}
		}
	
		@Override
		protected int getStackLimit(int slot, ItemStack stack) {
			if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				return 1;
			}
			
			return super.getStackLimit(slot, stack);
		}
	}

	public static class SecurityBoxHandler extends ItemStackHandler
	{
		private TileEntityRegister register;
		public SecurityBoxHandler(TileEntityRegister register)
		{
			super(27);
			this.register = register;
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return stack.isEmpty() || stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			
			register.markDirty();
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return stack;
		}
		
		protected ItemStack insertItemInternal(int slot, ItemStack stack, boolean simulate)
		{
			return super.insertItem(slot, stack, simulate);
		}
		
		public ItemStack insertItem(ItemStack stack)
		{
			return insertItem(stack, false);
		}
	
		public ItemStack insertItem(ItemStack stack, boolean simulate)
		{
			for(int i = 0; i < getSlots(); i++)
			{
				stack = insertItemInternal(i, stack, simulate);
				if (stack.isEmpty())
				{
					return stack;
				}
			}
			
			return stack;
		}
	}
	
	public static class StoreSaleSubParameter
	{
		public String Name;
		public int Amount;
		public BigDecimal SaleAmount;
	}
	
	private static class DebitCardPaymentParameter
	{
		public UUID RegisterIdentifier;
		public String CardNumber;
		public String PIN;
		public BigDecimal AuthorizedAmount;
		public BigDecimal PaymentAmount;
	}
	
	public static class StoreSaleParameter
	{
		public ArrayList<StoreSaleSubParameter> StoreItems = new ArrayList<>();
		public DebitCardPaymentParameter DebitCardInformation;
	}
}
