package com.mesabrook.ib.capability.debitcard;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityDebitCardItemProvider implements ICapabilitySerializable<NBTTagCompound> {

	IDebitCard debitCardInfo;
	public CapabilityDebitCardItemProvider()
	{
		debitCardInfo = new IDebitCard.Impl();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityDebitCard.DEBIT_CARD_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityDebitCard.DEBIT_CARD_CAPABILITY)
		{
			return CapabilityDebitCard.DEBIT_CARD_CAPABILITY.cast(debitCardInfo);
		}
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound)new CapabilityDebitCard.Storage().writeNBT(CapabilityDebitCard.DEBIT_CARD_CAPABILITY, debitCardInfo, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		new CapabilityDebitCard.Storage().readNBT(CapabilityDebitCard.DEBIT_CARD_CAPABILITY, debitCardInfo, null, nbt);
	}

}
