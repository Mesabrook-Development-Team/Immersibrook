package com.mesabrook.ib.blocks.te;

import java.util.LinkedHashSet;

import com.mesabrook.ib.apimodels.company.Company;
import com.mesabrook.ib.apimodels.company.Location;
import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.blocks.BlockAutomatedTaggingStation;
import com.mesabrook.ib.blocks.ImmersiblockRotational;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.net.sco.AutomatedTaggingStationSetStackSizePacket;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.DataRequestTaskStatus;
import com.mesabrook.ib.util.apiaccess.GetData;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityAutomatedTaggingStation extends TileEntity implements ITickable {

	private ItemStackHandler securityBoxInventory;
	private int tagStackSize;
	private EnergyStorage energyStorage;
	private double resetDistance;
	private Location locationOwner;
	private boolean active;
	private LightStates lightState = LightStates.Dark;
	public static final int MAX_ENERGY = 1000;
	
	public final EnumFacing INPUT_SIDE = EnumFacing.WEST;
	
	public TileEntityAutomatedTaggingStation()
	{
		securityBoxInventory = new AutomatedTaggingStationItemStackHandler();
		energyStorage = new EnergyStorage(MAX_ENERGY, 10, 0)
		{
			@Override
			public int receiveEnergy(int maxReceive, boolean simulate) {
				int retVal = super.receiveEnergy(maxReceive, simulate);
				
				if (!simulate)
				{
					markDirty();
					world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
				}
				
				return retVal;
			}
		};
		tagStackSize = 1;
	}
	
	public class AutomatedTaggingStationItemStackHandler extends ItemStackHandler
	{
		public AutomatedTaggingStationItemStackHandler() {
			super(18);
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			if (slot < 9)
			{
				if (!stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
				{
					return false;
				}
				
				return stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack().isEmpty();
			}
			else
			{
				return !stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
			}
		}
		
		@Override
		public int getSlotLimit(int slot) {
			if (slot >= 9)
			{
				return tagStackSize;
			}
			
			return super.getSlotLimit(slot);
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
			markDirty();
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (!isItemValid(slot, stack))
			{
				return stack;
			}
			
			return super.insertItem(slot, stack, simulate);
		}
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		
		wasActiveLastTick = active;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("securityBoxInv"))
		{
			securityBoxInventory.deserializeNBT(compound.getCompoundTag("securityBoxInv"));
		}
		
		if (compound.hasKey("tagStackSize"))
		{
			tagStackSize = compound.getInteger("tagStackSize");
		}
		
		if (compound.hasKey("energyStorage"))
		{
			CapabilityEnergy.ENERGY.readNBT(energyStorage, null, compound.getTag("energyStorage"));
		}
		
		if (compound.hasKey("resetDistance"))
		{
			resetDistance = compound.getDouble("resetDistance");
		}
		
		if (compound.hasKey("locationOwner"))
		{
			NBTTagCompound locationOwnerTag = compound.getCompoundTag("locationOwner");
			locationOwner = new Location();
			locationOwner.Company = new Company();
			locationOwner.Company.CompanyID = locationOwnerTag.getLong("CompanyID");
			locationOwner.Company.Name = locationOwnerTag.getString("CompanyName");
			locationOwner.LocationID = locationOwnerTag.getLong("LocationID");
			locationOwner.Name = locationOwnerTag.getString("LocationName");
		}
		
		if (compound.hasKey("active"))
		{
			active = compound.getBoolean("active");
		}
		
		if (compound.hasKey("lightState"))
		{
			lightState = LightStates.values()[compound.getInteger("lightState")];
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("securityBoxInv", securityBoxInventory.serializeNBT());
		compound.setInteger("tagStackSize", tagStackSize);
		compound.setTag("energyStorage", CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
		compound.setDouble("resetDistance", resetDistance);
		compound.setBoolean("active", active);
		
		if (locationOwner != null)
		{
			NBTTagCompound locationOwnerTag = new NBTTagCompound();
			locationOwnerTag.setLong("CompanyID", locationOwner.Company.CompanyID);
			locationOwnerTag.setString("CompanyName", locationOwner.Company.Name == null ? "" : locationOwner.Company.Name);
			locationOwnerTag.setLong("LocationID", locationOwner.LocationID);
			locationOwnerTag.setString("LocationName", locationOwner.Name == null ? "" : locationOwner.Name);
			compound.setTag("locationOwner", locationOwnerTag);
		}
		
		compound.setInteger("lightState", lightState.ordinal());
		return super.writeToNBT(compound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		writeToNBT(tag);
		return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		readFromNBT(tag);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		IBlockState currentState = world.getBlockState(pos);
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && currentState.getValue(BlockAutomatedTaggingStation.FACING).rotateY() == facing)
		{
			return true;
		}
		
		if (capability == CapabilityEnergy.ENERGY && facing == EnumFacing.UP)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		IBlockState currentState = world.getBlockState(pos);
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && currentState.getValue(BlockAutomatedTaggingStation.FACING).rotateY() == facing)
		{
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(securityBoxInventory);
		}
		
		if (capability == CapabilityEnergy.ENERGY && facing == EnumFacing.UP)
		{
			return CapabilityEnergy.ENERGY.cast(energyStorage);
		}
		
		return super.getCapability(capability, facing);
	}
	
	public int getTagStackSize() {
		return tagStackSize;
	}
	
	public void setTagStackSize(int tagStackSize) {
		this.tagStackSize = tagStackSize;
		
		if (!world.isRemote)
		{
			IBlockState currentState = world.getBlockState(getPos());
			for(int i = 9; i < securityBoxInventory.getSlots(); i++)
			{
				ItemStack stackToBox = securityBoxInventory.getStackInSlot(i);
				if (!stackToBox.isEmpty() && stackToBox.getCount() > tagStackSize)
				{
					int amountOver = stackToBox.getCount() - tagStackSize;
					stackToBox.shrink(amountOver);
					
					for(int j = i + 1; j < securityBoxInventory.getSlots(); j++)
					{
						ItemStack potentialOverflowStack = securityBoxInventory.getStackInSlot(j);
						if (potentialOverflowStack.isEmpty() || (ItemStack.areItemsEqual(stackToBox, potentialOverflowStack) && ItemStack.areItemStackTagsEqual(stackToBox, potentialOverflowStack)))
						{
							int amountToGrow = Math.min(amountOver, potentialOverflowStack.getMaxStackSize());
							if (potentialOverflowStack.isEmpty())
							{
								potentialOverflowStack = stackToBox.copy();
								potentialOverflowStack.setCount(amountToGrow);
								securityBoxInventory.setStackInSlot(j, potentialOverflowStack);
							}
							else
							{
								potentialOverflowStack.grow(amountToGrow);							
							}
							amountOver -= amountToGrow;
							if (amountOver <= 0)
							{
								break;
							}
						}
					}
					
					if (amountOver > 0)
					{
						ItemStack stackToSpawn = stackToBox.copy();
						stackToSpawn.setCount(amountOver);
						EnumFacing facingToSpawn = currentState.getValue(ImmersiblockRotational.FACING).getOpposite();
						BlockPos spawnPos = getPos().offset(facingToSpawn);
						InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), stackToSpawn);
					}
				}
			}
			
			world.notifyBlockUpdate(getPos(), currentState, currentState, 3);
		}
		markDirty();
	}
	
	public ItemStackHandler getInventory()
	{
		return securityBoxInventory;
	}
	
	public int getEnergyStored()
	{
		return energyStorage.getEnergyStored();
	}
	
	public void syncClientToServer()
	{
		AutomatedTaggingStationSetStackSizePacket syncPacket = new AutomatedTaggingStationSetStackSizePacket();
		syncPacket.pos = getPos();
		syncPacket.stackSize = tagStackSize;
		syncPacket.resetDistance = resetDistance;
		syncPacket.active = active;
		PacketHandler.INSTANCE.sendToServer(syncPacket);
	}
	
	private class ItemStackKey {
	    private final ItemStack stack;

	    public ItemStackKey(ItemStack stack) {
	        this.stack = stack.copy();
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (!(obj instanceof ItemStackKey)) return false;
	        ItemStack other = ((ItemStackKey) obj).stack;
	        return stack.getCount() == other.getCount() &&
	        	   ItemStack.areItemsEqual(stack, other) &&
	               ItemStack.areItemStackTagsEqual(stack, other);
	    }

	    @Override
	    public int hashCode() {
	        int result = stack.getItem().hashCode();
	        result = 31 * result + stack.getItemDamage();
	        result = 37 * result + stack.getCount();
	        if (stack.hasTagCompound()) {
	            // Serialize tag compound for consistent hash
	            result = 31 * result + stack.getTagCompound().toString().hashCode();
	        }
	        return result;
	    }
	}


	private DataRequestTask priceLookupTask = null;
	private LinkedHashSet<ItemStackKey> approvedItemStacks = new LinkedHashSet<>(50);
	
	private boolean wasActiveLastTick = false;
	private int cooldown = 0;
	@Override
	public void update() {
		if (world.isRemote || cooldown-- > 0)
		{
			return;
		}
		
		cooldown = 0;
		setLightState(LightStates.Dark);
		
		if (!active)
		{
			if (wasActiveLastTick)
			{
				wasActiveLastTick = false;
				ServerSoundBroadcastPacket.playIBSound(world, getSoundNameShutdown(), pos, true);
				
				cooldown = 360; // ~18 seconds
			}
			
			return;
		}
		
		if (!wasActiveLastTick)
		{
			wasActiveLastTick = true;
			ServerSoundBroadcastPacket.playIBSound(world, getSoundNameStartup(), pos, true);
			cooldown = 460; // ~23 seconds
			
			return;
		}
		
		if (priceLookupTask != null)
		{
			if (priceLookupTask.getStatus() != DataRequestTaskStatus.Complete)
			{
				return;
			}
			
			handlePriceLookupTaskComplete();			
			return;
		}
		else
		{
			checkAndProcessNextItem();
		}
	}
	
	private void handlePriceLookupTaskComplete()
	{
		ItemStack firstSecurityBoxStack = null;
		int firstSecurityBoxStackIndex = -1;
		for(int i = 0; i < 9; i++)
		{
			ItemStack boxStack = securityBoxInventory.getStackInSlot(i);
			if (boxStack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				firstSecurityBoxStack = boxStack;
				firstSecurityBoxStackIndex = i;
				break;
			}
		}
		
		ItemStack firstStackToBox = null;
		int firstStackToBoxIndex = -1;
		for(int i = 9; i < 18; i++)
		{
			ItemStack stackToBox = securityBoxInventory.getStackInSlot(i);
			if (!stackToBox.isEmpty())
			{
				firstStackToBox = stackToBox;
				firstStackToBoxIndex = i;
				break;
			}
		}
		
		if (firstStackToBox == null || firstSecurityBoxStack == null)
		{
			priceLookupTask = null;
			return;
		}
		
		LocationItem locationItem = priceLookupTask.getTask().getResult(LocationItem.class);
		ItemStack stack = (ItemStack)priceLookupTask.getData().get("stack");
		priceLookupTask = null;
		
		if (!ItemStack.areItemsEqual(firstStackToBox, stack) || !ItemStack.areItemStackTagsEqual(firstStackToBox, stack) || firstStackToBox.getCount() != stack.getCount()) // Skip this iteration
		{
			return;
		}
		
		// At this point, we know the item stack from the data request and the next item stack to box are the same.
		// Accepting or rejecting will cost energy, so check and subtract first.
		
		if (energyStorage.getEnergyStored() <= 0 || energyStorage.receiveEnergy(-1, false) >= 0) // lol hax - receiving -1 is the same as extracting 1 but it isn't caught by the validation check
		{
			cooldown = 40;
			setLightState(LightStates.Red);
			return; // No energy
		}
		
		IBlockState currentState = world.getBlockState(getPos());
		if (locationItem == null) // Reject - no price found
		{
			EnumFacing facingToSpawn = currentState.getValue(ImmersiblockRotational.FACING).getOpposite();
			BlockPos spawnPos = getPos().offset(facingToSpawn);
			ItemStack stackToSpawn = firstStackToBox.copy();
			insertOrSpawnAt(spawnPos, stackToSpawn);
			
			securityBoxInventory.setStackInSlot(firstStackToBoxIndex, ItemStack.EMPTY);
			
			ServerSoundBroadcastPacket.playIBSound(world, getSoundNameReject(), pos, true);
			cooldown = 40;
			setLightState(LightStates.Red);
			return;
		}
		else // Accept - we found a price
		{
			while(approvedItemStacks.size() >= 50)
			{
				approvedItemStacks.remove(approvedItemStacks.toArray()[0]);
			}
			
			EnumFacing facingToSpawn = currentState.getValue(ImmersiblockRotational.FACING).rotateYCCW();
			BlockPos spawnPos = getPos().offset(facingToSpawn);
			
			firstStackToBox = firstStackToBox.copy();
			approvedItemStacks.add(new ItemStackKey(firstStackToBox));
			
			ItemStack modifiedBoxStack = firstSecurityBoxStack.copy();
			modifiedBoxStack.shrink(1);
			securityBoxInventory.setStackInSlot(firstSecurityBoxStackIndex, modifiedBoxStack);
			
			securityBoxInventory.setStackInSlot(firstStackToBoxIndex, ItemStack.EMPTY);
			
			ItemStack stackToSpawn = firstSecurityBoxStack.copy();
			stackToSpawn.setCount(1);
			ISecuredItem securedItem = stackToSpawn.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
			securedItem.setInnerStack(firstStackToBox);
			securedItem.setResetDistance(resetDistance);
			securedItem.setLocation(locationOwner);
			insertOrSpawnAt(spawnPos, stackToSpawn);
			
			ServerSoundBroadcastPacket.playIBSound(world, getSoundNamePack(), pos, true);
			cooldown = 40;
			setLightState(LightStates.Green);
		}
	}
	
	private void checkAndProcessNextItem()
	{
		ItemStack firstSecurityBoxStack = null;
		int firstSecurityBoxStackIndex = -1;
		for(int i = 0; i < 9; i++)
		{
			ItemStack boxStack = securityBoxInventory.getStackInSlot(i);
			if (boxStack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				firstSecurityBoxStack = boxStack;
				firstSecurityBoxStackIndex = i;
				break;
			}
		}
		
		ItemStack firstStackToBox = null;
		int firstStackToBoxIndex = -1;
		for(int i = 9; i < 18; i++)
		{
			ItemStack stackToBox = securityBoxInventory.getStackInSlot(i);
			if (!stackToBox.isEmpty() && stackToBox.getCount() == tagStackSize)
			{
				firstStackToBox = stackToBox;
				firstStackToBoxIndex = i;
				break;
			}
		}
		
		if (firstStackToBox == null || firstSecurityBoxStack == null || firstStackToBox.getCount() != tagStackSize)
		{
			return;
		}
		
		ItemStackKey key = new ItemStackKey(firstStackToBox);
		if (approvedItemStacks.contains(key))
		{
			if (energyStorage.getEnergyStored() <= 0 || energyStorage.receiveEnergy(-1, false) >= 0) // lol hax - receiving -1 is the same as extracting 1 but it isn't caught by the validation check
			{
				cooldown = 40;
				setLightState(LightStates.Red);
				return; // No energy
			}
			
			IBlockState currentState = world.getBlockState(getPos());
			EnumFacing facingToSpawn = currentState.getValue(ImmersiblockRotational.FACING).rotateYCCW();
			BlockPos spawnPos = getPos().offset(facingToSpawn);
			
			ItemStack modifiedBoxStack = firstSecurityBoxStack.copy();
			modifiedBoxStack.shrink(1);
			securityBoxInventory.setStackInSlot(firstSecurityBoxStackIndex, modifiedBoxStack);
			
			securityBoxInventory.setStackInSlot(firstStackToBoxIndex, ItemStack.EMPTY);
			
			ItemStack stackToSpawn = firstSecurityBoxStack.copy();
			stackToSpawn.setCount(1);
			ISecuredItem securedItem = stackToSpawn.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
			securedItem.setInnerStack(firstStackToBox.copy());
			securedItem.setResetDistance(resetDistance);
			securedItem.setLocation(locationOwner);
			insertOrSpawnAt(spawnPos, stackToSpawn);

			ServerSoundBroadcastPacket.playIBSound(world, getSoundNamePack(), pos, true);
			cooldown = 40;
			setLightState(LightStates.Green);
		}
		else // Need to query
		{
			GetData get = new GetData(API.Company, "PriceCheck/GetItem", LocationItem.class);
			get.addQueryString("itemName", firstStackToBox.getDisplayName());
			get.addQueryString("quantity", Integer.toString(firstStackToBox.getCount()));
			get.addQueryString("locationID", Long.toString(locationOwner.LocationID));
			
			priceLookupTask = new DataRequestTask(get);
			priceLookupTask.getData().put("stack", firstStackToBox.copy());
			DataRequestQueue.INSTANCE.addTask(priceLookupTask);
		}
	}
	
	private static EnumFacing getFacingBetween(BlockPos from, BlockPos to) {
	    for (EnumFacing facing : EnumFacing.values()) {
	        if (from.offset(facing).equals(to)) {
	            return facing;
	        }
	    }
	    return null; // Not adjacent
	}
	
	private void insertOrSpawnAt(BlockPos pos, ItemStack stack)
	{
		EnumFacing insertFacing = getFacingBetween(pos, getPos());
		
		TileEntity te = world.getTileEntity(pos);
		
		ItemStack stackToInsert = stack.copy();
		if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, insertFacing))
		{
			IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, insertFacing);
			for(int i = 0; i < handler.getSlots(); i++)
			{
				if (handler.isItemValid(i, stackToInsert))
				{
					ItemStack remainder = handler.insertItem(i, stack.copy(), true);
					if (remainder.isEmpty() || remainder.getCount() < stack.getCount())
					{
						stackToInsert = handler.insertItem(i, stackToInsert.copy(), false);
					}
					
					if (stackToInsert.isEmpty())
					{
						return;
					}
				}
			}
		}
		InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stackToInsert);
	}

	public void setLocationOwner(Location location) {
		this.locationOwner = location;
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	public void setResetDistance(double distance) {
		this.resetDistance = distance;
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	public double getResetDistance()
	{
		return resetDistance;
	}

	public long getLocationIDOwner() {
		if (locationOwner == null)
		{
			return 0;
		}
		
		return locationOwner.LocationID;
	}
	
	public void dumpInventory()
	{
		for(int i = 0; i < securityBoxInventory.getSlots(); i++)
		{
			ItemStack stack = securityBoxInventory.getStackInSlot(i);
			if (stack.isEmpty())
			{
				continue;
			}
			
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			securityBoxInventory.setStackInSlot(i, ItemStack.EMPTY);
		}
		
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
		markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	}
	
	public LightStates getLightState()
	{
		return lightState;
	}
	
	private void setLightState(LightStates state) {
		boolean didChange = !lightState.equals(state);
		
		if (didChange)
		{
			this.lightState = state;
			markDirty();
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	private String getSoundNameStartup() { return "apm_startup"; }
	private String getSoundNameShutdown() { return "apm_shutdown"; }
	private String getSoundNamePack() { return "apm_pack_success"; }
	private String getSoundNameReject() { return "apm_pack_fail"; }
	
	public enum LightStates implements IStringSerializable
	{
		Dark,
		Red,
		Green;

		@Override
		public String getName() {
			return toString().toLowerCase();
		}		
	}
}
