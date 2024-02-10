package com.mesabrook.ib.blocks.container;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRegisterSecurityBoxInventory extends Container {

	InventoryPlayer playerInventory;
	TileEntityRegister register;
	public ContainerRegisterSecurityBoxInventory(InventoryPlayer playerInventory, TileEntityRegister register)
	{
		super();
		this.playerInventory = playerInventory;
		this.register = register;
		
		// Player Inventory
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				this.addSlotToContainer(new Slot(playerInventory, x + 9 + 9 * y, 70 + x*18, 218 + y*18));
			}
		}
		
		// Player hotbar
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(playerInventory, x, 70 + x*18, 276));
		}
		
		// Register inventory
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				this.addSlotToContainer(new SlotItemHandler(register.getSecurityBoxInventory(), x + 9 * y, 107 + x*18, 44 + y*18));
			}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistance(register.getPos().getX(), register.getPos().getY(), register.getPos().getZ()) < 5.5D;
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
