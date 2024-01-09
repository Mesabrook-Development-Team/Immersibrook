package com.mesabrook.ib.items.commerce;

import com.mesabrook.ib.init.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemRegisterFluidWrapper extends Item {
	
	public ItemRegisterFluidWrapper()
	{
		super();
		setUnlocalizedName("register_fluid_wrapper");
        setRegistryName("register_fluid_wrapper");
        
        ModItems.ITEMS.add(this);
	}

	@Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
    	if (!stack.hasCapability(CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null))
    	{
    		return super.getNBTShareTag(stack);
    	}
    	
    	NBTTagCompound stackTag = super.getNBTShareTag(stack);
    	
    	if (stackTag == null)
    	{
    		stackTag = new NBTTagCompound();
    	}
    	
    	IRegisterFluidWrapper fluidWrapperHandler = stack.getCapability(CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null);
    	stackTag.setTag("fluidWrapper", CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY.writeNBT(fluidWrapperHandler, null));
    	return stackTag;
    }
    
    @Override
    public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
    	super.readNBTShareTag(stack, nbt);
    	if (!nbt.hasKey("fluidWrapper") || !stack.hasCapability(CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null))
    	{
    		return;
    	}
    	
    	IRegisterFluidWrapper handler = stack.getCapability(CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null);
    	CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY.readNBT(handler, null, nbt.getTag("fluidWrapper"));
    }
	
	public static interface IRegisterFluidWrapper
	{		
		FluidStack getFluidStack();
		void setFluidStack(FluidStack stack);
		BlockPos getMeterPosition();
		void setMeterPosition(BlockPos pos);
	}
	
	public static class CapabilityRegisterFluidWrapper
	{
		@CapabilityInject(IRegisterFluidWrapper.class)
		public static Capability<IRegisterFluidWrapper> REGISTER_FLUID_WRAPPER_CAPABILITY;
		
		public static void init()
		{
			CapabilityManager.INSTANCE.register(IRegisterFluidWrapper.class, new IStorage<IRegisterFluidWrapper>() {
				@Override
				public void readNBT(Capability<IRegisterFluidWrapper> capability, IRegisterFluidWrapper instance,
						EnumFacing side, NBTBase nbt) {
					if (!(nbt instanceof NBTTagCompound))
					{
						return;
					}
					
					NBTTagCompound tag = (NBTTagCompound)nbt;
					if (tag.hasKey("stack"))
					{
						FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("stack"));
						instance.setFluidStack(fluidStack);
					}
					
					if (tag.hasKey("meterPos"))
					{
						BlockPos meterPos = BlockPos.fromLong(tag.getLong("meterPos"));
						instance.setMeterPosition(meterPos);
					}
				}
				
				@Override
				public NBTBase writeNBT(Capability<IRegisterFluidWrapper> capability, IRegisterFluidWrapper instance,
						EnumFacing side) {
					NBTTagCompound tag = new NBTTagCompound();
					if (instance.getFluidStack() != null)
					{
						NBTTagCompound stackTag = new NBTTagCompound();
						tag.setTag("stack", instance.getFluidStack().writeToNBT(stackTag));
					}
					
					tag.setLong("meterPos", instance.getMeterPosition().toLong());
					return tag;
				}
			}, RegisterFluidWrapperCapability::new);
		}
	}
	
	public static class RegisterFluidWrapperCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
	{		
		RegisterFluidWrapperCapability handler = new RegisterFluidWrapperCapability();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (capability == CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY)
			{
				return CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY.cast(handler);
			}
			
			return null;
		}
		
		@Override
		public NBTTagCompound serializeNBT() {
			return (NBTTagCompound)CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY.getStorage().writeNBT(CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, handler, null);
		}
		
		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY.getStorage().readNBT(CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, handler, null, nbt);
		}
	}
	
	public static class RegisterFluidWrapperCapability implements IRegisterFluidWrapper
	{
		private FluidStack stack = null;
		private BlockPos meterPos = new BlockPos(0, -1, 0);
		
		public RegisterFluidWrapperCapability() {}
		
		public RegisterFluidWrapperCapability(FluidStack stack, BlockPos meterPos)
		{
			this.stack = stack;
			this.meterPos = meterPos;
			if (this.meterPos == null)
			{
				this.meterPos = new BlockPos(0, -1, 0);
			}
		}

		@Override
		public FluidStack getFluidStack() {
			return stack;
		}

		@Override
		public void setFluidStack(FluidStack stack)
		{
			this.stack = stack;
		}
		
		@Override
		public BlockPos getMeterPosition() {
			if (meterPos == null)
			{
				return new BlockPos(0, -1, 0);
			}
			
			return meterPos;
		}

		@Override
		public void setMeterPosition(BlockPos meterPos)
		{
			if (meterPos == null)
			{
				this.meterPos = new BlockPos(0, -1, 0);
			}
			
			this.meterPos = meterPos;
		}
	}
}
