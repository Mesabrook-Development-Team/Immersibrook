package com.mesabrook.ib.capability.wallet;

import com.mesabrook.ib.items.commerce.ItemDebitCard;
import com.mesabrook.ib.items.commerce.ItemMoney;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemWalletCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {

	IItemHandler walletInv;
	
	public ItemWalletCapabilityProvider()
	{
		walletInv = new WalletItemHandler();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(walletInv);
		}
		return null;
	}

	private static class WalletItemHandler extends ItemStackHandler
	{
		public WalletItemHandler()
		{
			super(12);
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return (stack.getItem() instanceof ItemMoney && slot >= 3 && slot <= 11) || (stack.getItem() instanceof ItemDebitCard && slot >= 0 && slot <= 2);
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(walletInv, null));
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(walletInv, null, nbt.getTag("inventory"));
	}
}
