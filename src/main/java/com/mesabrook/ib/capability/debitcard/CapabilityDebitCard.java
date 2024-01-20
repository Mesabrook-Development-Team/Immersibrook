package com.mesabrook.ib.capability.debitcard;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityDebitCard {
	@CapabilityInject(IDebitCard.class)
	public static Capability<IDebitCard> DEBIT_CARD_CAPABILITY;
	
	public static void init()
	{
		CapabilityManager.INSTANCE.register(IDebitCard.class, new Storage(), IDebitCard.Impl::new);
	}
	
	public static class Storage implements IStorage<IDebitCard>
	{
		@Override
		public NBTBase writeNBT(Capability<IDebitCard> capability, IDebitCard instance, EnumFacing side) {
			if (capability != DEBIT_CARD_CAPABILITY)
			{
				return null;
			}
			
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("cardNumber", instance.getCardNumber());
			return tag;
		}

		@Override
		public void readNBT(Capability<IDebitCard> capability, IDebitCard instance, EnumFacing side, NBTBase nbt) {
			if (capability != DEBIT_CARD_CAPABILITY)
			{
				return;
			}
			
			NBTTagCompound compound = (NBTTagCompound)nbt;
			instance.setCardNumber(compound.getString("cardNumber"));
		}
		
	}
}
