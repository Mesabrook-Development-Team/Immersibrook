package com.mesabrook.ib.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerWallet extends Container {

	InventoryPlayer playerInventory;
	IItemHandler walletInventory;
	EnumHand hand;
	public ContainerWallet(InventoryPlayer playerInventory, ItemStack wallet, EnumHand hand)
	{
		walletInventory = new ItemStackHandler(12);
		if (wallet.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
		{
			walletInventory = wallet.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		}
		this.playerInventory = playerInventory;
		this.hand = hand;
		
		// Wallet slots - cards
		addSlotToContainer(new SlotItemHandler(walletInventory, 0, 8, 18));
		addSlotToContainer(new SlotItemHandler(walletInventory, 1, 28, 18));
		addSlotToContainer(new SlotItemHandler(walletInventory, 2, 46, 18));
		
		// Wallet slots - money
		for(int i = 3; i < walletInventory.getSlots(); i++)
		{
			addSlotToContainer(new SlotItemHandler(walletInventory, i, 8 + 18 * (i - 3), 39));
		}
		
		// Player inventory
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				this.addSlotToContainer(new Slot(playerInventory, x + 9 + 9 * y, 8 + x*18, 71 + y*18));
			}
		}
		
		// Player hotbar
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(playerInventory, x, 8 + x*18, 129));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getHeldItem(hand).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) && 
				playerIn.getHeldItem(hand).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) == walletInventory;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 36)
            {
                if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
	}

}
