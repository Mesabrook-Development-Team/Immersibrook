package com.mesabrook.ib.blocks.container;

import com.mesabrook.ib.items.misc.ItemPhone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerSmartphone extends Container
{
    IItemHandler itemHandler;
    ItemStack phoneStack;
    EnumHand hand;

    public ContainerSmartphone(InventoryPlayer inventory, ItemStack stack, EnumHand hand)
    {
        this.phoneStack = stack;
        this.hand = hand;

        // Hot bar
        for(int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 108));
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

            addSlotToContainer(new Slot(inventory, i, 8 + column * 18, 50 + 18 * row));
        }

        // Storage
        itemHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return playerIn.getHeldItem(hand).getItem() instanceof ItemPhone;
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
