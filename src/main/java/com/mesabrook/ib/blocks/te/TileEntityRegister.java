package com.mesabrook.ib.blocks.te;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.apimodels.company.Register;
import com.mesabrook.ib.blocks.BlockRegister;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.net.sco.POSInitializeRegisterResponsePacket;
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

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
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
	
	private BigDecimal getDueAmount()
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
		ArrayList<StoreSaleSubParameter> parameters = new ArrayList<>();
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack.isEmpty())
			{
				continue;
			}
			
			if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				ISecuredItem securedItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
				stack = securedItem.getInnerStack();
			}
			
			StoreSaleSubParameter param = new StoreSaleSubParameter();
			param.Name = stack.getDisplayName();
			param.Amount = stack.getCount();
			param.SaleAmount = itemHandler.getPrice(i);
			parameters.add(param);
		}
		
		setRegisterStatus(RegisterStatuses.TransactionProcessing);
		
		PostData post = new PostData(API.Company, "StoreSaleItemIBAccess/Post", parameters, new Class<?>[0]);
		post.getHeaderOverrides().put("RegisterIdentifier", identifier.toString());
		
		paymentTask = new DataRequestTask(post);
		DataRequestQueue.INSTANCE.addTask(paymentTask);
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
								setRegisterStatus(RegisterStatuses.Online);
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
			}
			else
			{
				setRegisterStatus(RegisterStatuses.WaitingForNetwork);
			}
			statusUpdateTask = null;
		}
		
		if (paymentTask != null && paymentTask.getStatus() == DataRequestTaskStatus.Complete)
		{
			if (paymentTask.getTask().getRequestSuccessful())
			{
				BlockPos spawnPos = getPos().offset(world.getBlockState(getPos()).getValue(BlockRegister.FACING));
				
				for(int i = 0; i < itemHandler.getSlots(); i++)
				{
					ItemStack stack = itemHandler.getStackInSlot(i);
					if (stack.isEmpty())
					{
						continue;
					}
					
					if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
					{
						ISecuredItem securedItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
						stack = securedItem.getInnerStack();
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
			}
			else
			{
				setRegisterStatus(RegisterStatuses.PaymentSelect);
				
				GenericErrorResponse errorMessage = paymentTask.getTask().getResult(GenericErrorResponse.class);
				if (errorMessage != null)
				{
					setTenderFailureMessage(errorMessage.message);
				}
				
				BlockPos spawnPos = getPos().offset(world.getBlockState(getPos()).getValue(BlockRegister.FACING));
				List<ItemStack> tenderedStacks = ItemMoney.getMoneyStackForAmount(tenderedAmount);
				for(ItemStack stack : tenderedStacks)
				{
					InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), stack);
				}
				
				setTenderedAmount(new BigDecimal(0));
			}
			
			paymentTask = null;
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
		IItemHandler itemHandler = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			ItemStack stackInSlot = itemHandler.getStackInSlot(i);
			if (stackInSlot.isEmpty())
			{
				itemHandler.insertItem(i, stack, false);
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
	}

	public static class StoreSaleSubParameter
	{
		public String Name;
		public int Amount;
		public BigDecimal SaleAmount;
	}
}
