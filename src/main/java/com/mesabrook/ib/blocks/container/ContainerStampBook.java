package com.mesabrook.ib.blocks.container;

import com.mesabrook.ib.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerStampBook extends Container {

	IItemHandler stampBookHandler;
	ItemStack stampBookStack;
	EnumHand hand;
	
	public ContainerStampBook(InventoryPlayer inventory, ItemStack stampBookStack, EnumHand hand)
	{
		this.stampBookStack = stampBookStack;
		this.hand = hand;
		
		// Hot bar
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventory, i, 30 + i * 18, 212));
		}
		
		// Player inventory
		for(int i = 9; i < 36; i++)
		{
			int rowWork = i;
			int row = 0;
			while (rowWork - 9 >= 9)
			{
				row++;
				rowWork -= 9;
			}
			
			int column = i % 9;
			
			addSlotToContainer(new Slot(inventory, i, 30 + column * 18, 154 + 18 * row));
		}
		
		// Stamp book inventory
		stampBookHandler = stampBookStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i = 0; i < stampBookHandler.getSlots(); i++)
		{
			// Cities
			if (i < 6)
			{
				switch(i)
				{
					case 0: // Autumn Valley
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 22, 22));
						break;
					case 1: // Iron River
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 89, 53));
						break;
					case 2: // Ravenholm City
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 83, 83));
						break;
					case 3: // Sodor City
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 69, 109));
						break;
					case 4: // Crystal Beach
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 37, 100));
						break;
					case 5: // Clayton
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 19, 117));
						break;
				}
			}
			// Extra stamps
			else
			{
				int row = (i - 6) / 3;
				int column = (i - 6) % 3;
				
				addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 138 + column * 18, 20 + row * 17));
			}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getHeldItem(hand).getItem() == ModItems.STAMP_BOOK;
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
