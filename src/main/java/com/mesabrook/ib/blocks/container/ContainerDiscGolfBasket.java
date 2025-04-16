package com.mesabrook.ib.blocks.container;

import com.mesabrook.ib.blocks.te.TileEntityDiscGolfBasket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDiscGolfBasket extends Container {

	private TileEntityDiscGolfBasket basket;
	public ContainerDiscGolfBasket(InventoryPlayer playerInv, TileEntityDiscGolfBasket basket)
	{
		super();
		
		this.basket = basket;
		
		// Hot bar
        for(int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 108));
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

            addSlotToContainer(new Slot(playerInv, i, 8 + column * 18, 50 + 18 * row));
        }

        // Ration Inventory
        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new SlotItemHandler(basket.getDiscInventory(), i, 8 + i * 18, 18));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistance(basket.getPos().getX(), basket.getPos().getY(), basket.getPos().getZ()) < 5.5D;
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
