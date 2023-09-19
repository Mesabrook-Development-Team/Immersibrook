package com.mesabrook.ib.blocks.te;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

import com.mesabrook.ib.apimodels.company.Register;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.net.sco.POSInitializeRegisterResponsePacket;
import com.mesabrook.ib.util.apiaccess.DataAccess;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataAccess.AuthenticationStatus;
import com.mesabrook.ib.util.apiaccess.DataAccess.GenericErrorResponse;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.DataRequestTaskStatus;
import com.mesabrook.ib.util.apiaccess.GetData;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;
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

	// Runtime data
	DataRequestTask statusUpdateTask = null;
	DataRequestTask initializeTask = null;
	Calendar nextUpdateTime = Calendar.getInstance();
	
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
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(itemHandler, null, compound.getTag("inventory"));
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
		compound.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(itemHandler, null));
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
		compound.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(itemHandler, null));
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
			CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(itemHandler, null, tag.getTag("inventory"));
		}
		registerStatus = RegisterStatuses.values()[tag.getInteger("registerStatus")];
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
		PaymentCard(true);
		
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
		public RegisterItemHandler(TileEntityRegister register)
		{
			super(100);
			this.register = register;
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (!register.getRegisterStatus().isOperationalState || register.getLocationIDOwner() == 0 ||
					(stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) && stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getLocationIDOwner() != register.getLocationIDOwner()) ||
					!getStackInSlot(slot).isEmpty())
			{
				return stack;
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
		}
	}
}
