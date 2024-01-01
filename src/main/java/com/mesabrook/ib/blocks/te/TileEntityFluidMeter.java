package com.mesabrook.ib.blocks.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityFluidMeter extends TileEntity {
	private FluidMeterFluidHandler upwardFluidHandler;
	private FluidMeterFluidHandler downwardFluidHandler;
	private FlowDirection flowDirection = FlowDirection.Up;
	public TileEntityFluidMeter() {
		upwardFluidHandler = new FluidMeterFluidHandler(FlowDirection.Up);
		downwardFluidHandler = new FluidMeterFluidHandler(FlowDirection.Down);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		flowDirection = FlowDirection.values()[compound.getInteger("flowDirection")];
		
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("flowDirection", flowDirection.ordinal());
		return super.writeToNBT(compound);
	}
	
	public FlowDirection getFlowDirection() {
		return flowDirection;
	}

	public void setFlowDirection(FlowDirection flowDirection) {
		this.flowDirection = flowDirection;
		markDirty();
		
		if (world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		tag.setInteger("flowDirection", flowDirection.ordinal());
		return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		
		flowDirection = FlowDirection.values()[tag.getInteger("flowDirection")];
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
			return fluidHandler.fill(resource, doFill);
		}

		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain) {
			FluidStack noneDrained = resource.copy();
			noneDrained.amount = 0;
			if (!flowDirection.equals(handlerDirection))
			{
				return noneDrained;
			}
			
			TileEntity nextContainer = world.getTileEntity(pos.offset(nextContainerDirection));
			if (nextContainer == null || !nextContainer.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection.getOpposite()))
			{
				return noneDrained;
			}
			
			IFluidHandler fluidHandler = nextContainer.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection.getOpposite());
			return fluidHandler.drain(resource, doDrain);
		}

		@Override
		public FluidStack drain(int maxDrain, boolean doDrain) {
			if (!flowDirection.equals(handlerDirection))
			{
				return null;
			}
			
			TileEntity nextContainer = world.getTileEntity(pos.offset(nextContainerDirection));
			if (nextContainer == null || !nextContainer.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection.getOpposite()))
			{
				return null;
			}
			
			IFluidHandler fluidHandler = nextContainer.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, nextContainerDirection.getOpposite());
			return fluidHandler.drain(maxDrain, doDrain);
		}
	}
}
