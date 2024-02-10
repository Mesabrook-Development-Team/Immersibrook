package com.mesabrook.ib.blocks.container;

import com.mesabrook.ib.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerShoppingBasket extends Container {
	
	InventoryPlayer playerInventory;
	IItemHandler shoppingBasketInventory;
	EnumHand hand;
	ItemStack shoppingBasketStack;
	
	public ContainerShoppingBasket(InventoryPlayer playerInventory, ItemStack shoppingBasketStack, EnumHand hand)
	{
		this.playerInventory = playerInventory;
		this.shoppingBasketInventory = shoppingBasketStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		this.hand = hand;
		this.shoppingBasketStack = shoppingBasketStack;
		
		// Player hotbar
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(playerInventory, x, 8 + x*18, 162));
		}
		
		// Player inventory
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				this.addSlotToContainer(new Slot(playerInventory, x + 9 + 9 * y, 8 + x*18, 104 + y*18));
			}
		}
		
		// Basket
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				this.addSlotToContainer(new SlotItemHandler(shoppingBasketInventory, x + 9 * y, 8 + x*18, 18 + y*18));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getHeldItem(hand).getItem() == ModItems.SHOPPING_BASKET;
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (slotId < 0 || slotId >= inventorySlots.size())
		{
			return ItemStack.EMPTY;
		}
		
		Slot slot = getSlot(slotId);
		if (slot != null && slot.getStack() == shoppingBasketStack)
		{
			return clickTypeIn == ClickType.QUICK_MOVE ? slot.getStack() : ItemStack.EMPTY;
		}
		
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            
            if (itemstack1 == shoppingBasketStack)
            {
            	return itemstack;
            }
            
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
