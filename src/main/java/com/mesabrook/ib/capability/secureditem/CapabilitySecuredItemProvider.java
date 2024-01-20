package com.mesabrook.ib.capability.secureditem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilitySecuredItemProvider implements ICapabilitySerializable<NBTTagCompound> {

	ISecuredItem securedItemInfo;
	public CapabilitySecuredItemProvider()
	{
		securedItemInfo = new ISecuredItem.Impl();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilitySecuredItem.SECURED_ITEM_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilitySecuredItem.SECURED_ITEM_CAPABILITY)
		{
			return CapabilitySecuredItem.SECURED_ITEM_CAPABILITY.cast(securedItemInfo);
		}
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound)new CapabilitySecuredItem.Storage().writeNBT(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, securedItemInfo, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		new CapabilitySecuredItem.Storage().readNBT(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, securedItemInfo, null, nbt);
	}

}
