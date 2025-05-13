package com.mesabrook.ib.blocks.container;

import com.mesabrook.ib.blocks.te.TileEntityAutomatedTaggingStation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAutomatedTaggingStation extends Container {

	public final TileEntityAutomatedTaggingStation taggingStation;
	public ContainerAutomatedTaggingStation(EntityPlayer player, TileEntityAutomatedTaggingStation taggingStation) {
		this.taggingStation = taggingStation;
		
		// Hot bar
        for(int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 154));
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

            addSlotToContainer(new Slot(player.inventory, i, 8 + column * 18, 96 + 18 * row));
        }
		
		// Security tags
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new SlotItemHandler(taggingStation.getInventory(), i, i + 8 + 17 * i, 18));
		}
		
		// Items to tag
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new SlotItemHandler(taggingStation.getInventory(), i + 9, 8 + 18 * i, 50));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistance(taggingStation.getPos().getX(), taggingStation.getPos().getY(), taggingStation.getPos().getZ()) < 5.5D;
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
