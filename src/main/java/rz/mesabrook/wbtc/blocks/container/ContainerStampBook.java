package rz.mesabrook.wbtc.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import rz.mesabrook.wbtc.init.ModItems;

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
			addSlotToContainer(new Slot(inventory, i, 200 + i * 18, 250));
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
			
			addSlotToContainer(new Slot(inventory, i, 200 + column * 18, 193 + 18 * row));
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
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 20, 75));
						break;
					case 1: // Iron River
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 20, 95));
						break;
					case 2: // Ravenholm City
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 20, 115));
						break;
					case 3: // Sodor City
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 20, 135));
						break;
					case 4: // Crystal Beach
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 20, 155));
						break;
					case 5: // Clayton
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 20, 175));
						break;
				}
			}
			// Extra stamps
			else
			{
				int row = (i - 6) / 3;
				int column = (i - 6) % 3;
				
				addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 255 + column * 18, 83 + row * 17));
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
