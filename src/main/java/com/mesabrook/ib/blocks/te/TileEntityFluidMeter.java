package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.apimodels.company.Location;
import com.mesabrook.ib.util.IndependentTimer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityFluidMeter extends TileEntity implements ITickable {
	private FluidMeterFluidHandler upwardFluidHandler;
	private FluidMeterFluidHandler downwardFluidHandler;
	private FlowDirection flowDirection = FlowDirection.Up;
	
	private long locationIDOwner = 0;
	private String locationOwnerName = "";
	private int fluidCounter = 0;
	private int lifetimeFluidCounter = 0;
	private String lastFluid = "";
	
	private int lastSync = 0;
	private boolean shouldSync = false;
	
	private int levelScroll = 0;
	private int lifetimeFluidScroll = 0;
	private int fluidScroll = 0;
	private int ownerScroll = 0;
	private IndependentTimer scrollTimer = new IndependentTimer();
	
	public TileEntityFluidMeter() {
		super();
		upwardFluidHandler = new FluidMeterFluidHandler(FlowDirection.Up);
		downwardFluidHandler = new FluidMeterFluidHandler(FlowDirection.Down);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		flowDirection = FlowDirection.values()[compound.getInteger("flowDirection")];
		locationIDOwner = compound.getLong("locationIDOwner");
		locationOwnerName = compound.getString("locationOwnerName");
		fluidCounter = compound.getInteger("fluidCounter");
		lastFluid = compound.getString("lastFluid");
		lifetimeFluidCounter = compound.getInteger("lifetimeFluidCounter");
		
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("flowDirection", flowDirection.ordinal());
		compound.setLong("locationIDOwner", locationIDOwner);
		compound.setString("locationOwnerName", locationOwnerName);
		compound.setInteger("fluidCounter", fluidCounter);
		compound.setString("lastFluid", lastFluid);
		compound.setInteger("lifetimeFluidCounter", lifetimeFluidCounter);
		return super.writeToNBT(compound);
	}
	
	public FlowDirection getFlowDirection() {
		return flowDirection;
	}

	public void setFlowDirection(FlowDirection flowDirection) {
		this.flowDirection = flowDirection;
		markDirty();
	}
	
	public long getLocationIDOwner()
	{
		return locationIDOwner;
	}
	
	public String getLocationOwnerName()
	{
		return locationOwnerName;
	}
	
	public void setLocationOwner(Location location)
	{
		if (location == null)
		{
			location = new Location();
		}
		
		locationIDOwner = location.LocationID;
		if (location.Name == null)
		{
			locationOwnerName = "";
		}
		else
		{
			boolean hasCompanyName = location.Company != null;
			if (hasCompanyName)
			{
				locationOwnerName = location.Company.Name + " (";
			}
			
			locationOwnerName += location.Name;
			
			if (hasCompanyName)
			{
				locationOwnerName += ")";
			}
		}
		
		markDirty();
	}
	
	public int getFluidCounter()
	{
		return fluidCounter;
	}
	
	public void setFluidCounter(int fluidCounter)
	{
		this.fluidCounter = Math.max(fluidCounter, 0);
		markDirty();
	}
	
	public int getLifetimeFluidCounter() {
		return lifetimeFluidCounter;
	}

	public void setLifetimeFluidCounter(int lifetimeFluidCounter) {
		this.lifetimeFluidCounter = lifetimeFluidCounter;
		markDirty();
	}

	public void increaseFluidCounter(int amount)
	{
		this.fluidCounter += amount;
		markDirty();
	}
	
	public void increaseLifetimeFluidCounter(int amount)
	{
		this.lifetimeFluidCounter += amount;
		markDirty();
	}
	
	public String getLastFluid()
	{
		return lastFluid;
	}
	
	public void setLastFluid(String lastUnlocalizedFluid)
	{
		this.lastFluid = lastUnlocalizedFluid;
		markDirty();
	}
	
	public int getLevelScroll() {
		return levelScroll;
	}

	public void setLevelScroll(int levelScroll) {
		this.levelScroll = levelScroll;
	}

	public int getLifetimeFluidScroll() {
		return lifetimeFluidScroll;
	}

	public void setLifetimeFluidScroll(int lifetimeFluidScroll) {
		this.lifetimeFluidScroll = lifetimeFluidScroll;
	}

	public int getFluidScroll() {
		return fluidScroll;
	}

	public void setFluidScroll(int fluidScroll) {
		this.fluidScroll = fluidScroll;
	}

	public int getOwnerScroll() {
		return ownerScroll;
	}

	public void setOwnerScroll(int ownerScroll) {
		this.ownerScroll = ownerScroll;
	}

	public IndependentTimer getScrollTimer() {
		return scrollTimer;
	}

	@Override
	public void markDirty() {
		super.markDirty();

		shouldSync = true;
	}
	
	@Override
	public void update() {
		if (world.isRemote || ++lastSync <= 20)
		{
			return;
		}
		
		if (shouldSync && world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		tag.setInteger("flowDirection", flowDirection.ordinal());
		tag.setLong("locationIDOwner", locationIDOwner);
		tag.setString("locationOwnerName", locationOwnerName);
		tag.setInteger("fluidCounter", fluidCounter);
		tag.setString("lastFluid", lastFluid);
		tag.setInteger("lifetimeFluidCounter", lifetimeFluidCounter);
		return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		
		flowDirection = FlowDirection.values()[tag.getInteger("flowDirection")];
		locationIDOwner = tag.getLong("locationIDOwner");
		locationOwnerName = tag.getString("locationOwnerName");
		fluidCounter = tag.getInteger("fluidCounter");
		lastFluid = tag.getString("lastFluid");
		lifetimeFluidCounter = tag.getInteger("lifetimeFluidCounter");
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		
		handleUpdateTag(pkt.getNbtCompound());
		
		if (world != null && world.isRemote)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (facing == EnumFacing.UP || facing == EnumFacing.DOWN) && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			if (facing == EnumFacing.UP)
			{
				return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(downwardFluidHandler);
			}
			else if (facing == EnumFacing.DOWN)
			{
				return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(upwardFluidHandler);
			}
		}
		
		return null;
	}
	
	public enum FlowDirection
	{
		Up,
		Down
	}
	
	public class FluidMeterFluidHandler implements IFluidHandler
	{
		final IFluidTankProperties[] fluidTankProperties;
		final FlowDirection handlerDirection;
		final EnumFacing nextContainerDirection;
		public FluidMeterFluidHandler(FlowDirection handlerDirection)
		{
			fluidTankProperties = new IFluidTankProperties[] { new FluidTankProperties(null, 0, true, true) };
			this.handlerDirection = handlerDirection;
			
			if (this.handlerDirection == FlowDirection.Up)
			{
				nextContainerDirection = EnumFacing.UP;
			}
			else
			{
				nextContainerDirection = EnumFacing.DOWN;
			}
		}
		
		@Override
		public IFluidTankProperties[] getTankProperties() {
			return fluidTankProperties;
		}

		@Override
		public int fill(FluidStack resource, boolean doFill) {
			if (!flowDirection.equals(handlerDirection))
			{
				return 0;
			}
			
			TileEntity nextContainer = world.getTileEntity(pos.offset(nextContainerDirection));
			if (nextContainer == null || !nextContainer.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection.getOpposite()))
			{
				return 0;
			}
			
			IFluidHandler fluidHandler = nextContainer.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection.getOpposite());
			int fluidConsumed = fluidHandler.fill(resource, doFill);
			if (doFill)
			{
				if (resource != null && resource.getFluid().getName().equalsIgnoreCase(getLastFluid()))
				{
					increaseFluidCounter(fluidConsumed);
					increaseLifetimeFluidCounter(fluidConsumed);
				}
				else
				{
					setFluidCounter(fluidConsumed);
					increaseLifetimeFluidCounter(fluidConsumed);
					setLastFluid(resource == null ? "" : resource.getFluid().getName());
				}
			}
			
			return fluidConsumed;
		}

		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain) {
			if (resource == null)
			{
				return null;
			}
			
			FluidStack noneDrained = resource.copy();
			noneDrained.amount = 0;
			if (!flowDirection.equals(handlerDirection))
			{
				return noneDrained;
			}
			
			TileEntity nextContainer = world.getTileEntity(pos.offset(nextContainerDirection.getOpposite()));
			if (nextContainer == null || !nextContainer.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection))
			{
				return noneDrained;
			}
			
			IFluidHandler fluidHandler = nextContainer.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection);
			FluidStack drainedStack = fluidHandler.drain(resource, doDrain);
			if (doDrain)
			{
				if (drainedStack != null && drainedStack.getFluid().getName().equalsIgnoreCase(getLastFluid()))
				{
					increaseFluidCounter(drainedStack.amount);
					increaseLifetimeFluidCounter(drainedStack.amount);
				}
				else
				{
					setFluidCounter(0);
					increaseLifetimeFluidCounter(drainedStack.amount);
					setLastFluid(drainedStack == null ? "" : drainedStack.getFluid().getName());
				}
			}
			return drainedStack;
		}

		@Override
		public FluidStack drain(int maxDrain, boolean doDrain) {
			if (!flowDirection.equals(handlerDirection))
			{
				return null;
			}
			
			TileEntity nextContainer = world.getTileEntity(pos.offset(nextContainerDirection.getOpposite()));
			if (nextContainer == null || !nextContainer.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection))
			{
				return null;
			}
			
			IFluidHandler fluidHandler = nextContainer.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection);
			FluidStack drainedStack = fluidHandler.drain(maxDrain, doDrain);
			if (doDrain)
			{
				if (drainedStack != null && drainedStack.getFluid().getName().equalsIgnoreCase(getLastFluid()))
				{
					increaseFluidCounter(drainedStack.amount);
					increaseLifetimeFluidCounter(drainedStack.amount);
				}
				else
				{
					setFluidCounter(0);
					increaseLifetimeFluidCounter(drainedStack.amount);
					setLastFluid(drainedStack == null ? "" : drainedStack.getFluid().getName());
				}
			}
			return drainedStack;
		}
	}
}
