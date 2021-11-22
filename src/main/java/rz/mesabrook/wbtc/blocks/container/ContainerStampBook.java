package rz.mesabrook.wbtc.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;
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
			addSlotToContainer(new Slot(inventory, i, 46 + i * 36, 449));
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
			
			addSlotToContainer(new Slot(inventory, i, 46 + column * 36, 333 + 36 * row));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getHeldItem(hand).getItem() == ModItems.STAMP_BOOK;
	}
	
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
