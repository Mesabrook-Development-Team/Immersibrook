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
			addSlotToContainer(new Slot(inventory, i, 120 + i * 18, 383));
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
			
			addSlotToContainer(new Slot(inventory, i, 120 + column * 18, 325 + 18 * row));
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
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 128, 43));
						break;
					case 1: // Iron River
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 280, 60));
						break;
					case 2: // Ravenholm City
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 294, 131));
						break;
					case 3: // Sodor City
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 228, 256));
						break;
					case 4: // Crystal Beach
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 109, 162));
						break;
					case 5: // Clayton
						addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 22, 266));
						break;
				}
			}
			// Extra stamps
			else
			{
				int row = (i - 6) / 3;
				int column = (i - 6) % 3;
				
				addSlotToContainer(new SlotItemHandler(stampBookHandler, i, 399 + column * 18, 20 + row * 17));
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

            if (index < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
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
	
	// We'll use the below code later when we are more specific about what types of stamps go where
//	@Override
//	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
//		Slot slot = inventorySlots.get(index);
//		ItemStack originStack = null;
//		ItemStack destinationStack = ItemStack.EMPTY;
//		
//		if (slot.getHasStack())
//		{
//			originStack = slot.getStack();
//			destinationStack = originStack.copy();
//			
//			if (index > 35)
//			{
//				if (!mergeItemStack(originStack, 0, 35, false))
//				{
//					return ItemStack.EMPTY;
//				}
//			}
//			else
//			{
//				BaseItemTrafficLightFrame heldFrame = (BaseItemTrafficLightFrame)playerIn.getHeldItemMainhand().getItem();
//				
//				boolean didMerge = false;
//				for(int i = 0; i < heldFrame.getBulbCount(); i++)
//				{
//					Slot frameSlot = inventorySlots.get(i + 36);
//					
//					if (!frameSlot.getHasStack() && frameSlot.isItemValid(originStack))
//					{
//						mergeItemStack(originStack, i + 36, i + 37, false);
//						didMerge = true;
//						break;
//					}
//				}
//				
//				if (!didMerge)
//				{
//					return ItemStack.EMPTY;
//				}
//			}
//		}
//		else
//		{
//			return ItemStack.EMPTY;
//		}
//		
//		if (originStack.isEmpty())
//		{
//			slot.putStack(ItemStack.EMPTY);
//		}
//		else
//		{
//			slot.onSlotChanged();
//		}
//		
//		return destinationStack;
//	}
}
