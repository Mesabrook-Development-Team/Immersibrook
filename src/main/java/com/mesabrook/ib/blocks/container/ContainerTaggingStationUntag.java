package com.mesabrook.ib.blocks.container;

import com.mesabrook.ib.blocks.te.TileEntityTaggingStation;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ContainerTaggingStationUntag extends Container {

	private final BlockPos taggingPos;
	private final InventoryPlayer playerInventory;
	private final InventoryBasic craftingInventory;
	private final InventoryCraftResult securityBoxResult;
	private final InventoryCraftResult itemResult;
	
	public ContainerTaggingStationUntag(InventoryPlayer playerInventory, BlockPos taggingPos)
	{
		this.taggingPos = taggingPos;
		this.playerInventory = playerInventory;
		
		craftingInventory = new InventoryBasic("Secured Item", true, 1)
		{
			@Override
			public boolean isItemValidForSlot(int index, ItemStack stack) {
				return stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) &&
						!stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack().isEmpty() &&
						securityBoxResult.getStackInSlot(0).isEmpty() &&
						itemResult.getStackInSlot(0).isEmpty();
			}
			
			@Override
			public void markDirty() {
				super.markDirty();
				ContainerTaggingStationUntag.this.onCraftMatrixChanged(playerInventory);
			}
			
			@Override
			public int getInventoryStackLimit() {
				return 1;
			}
		};
		
		securityBoxResult = new InventoryCraftResult();
		itemResult = new InventoryCraftResult();
		
		// Hot bar
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 145));
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
			
			addSlotToContainer(new Slot(playerInventory, i, 8 + column * 18, 87 + 18 * row));
		}
		
		// Crafting grid
		addSlotToContainer(new Slot(craftingInventory, 0, 30, 42)
		{
			@Override
			public boolean isItemValid(ItemStack stack) {
				if (!stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
				{
					return false;
				}
				
				ISecuredItem securedItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
				IEmployeeCapability emp = ContainerTaggingStationUntag.this.playerInventory.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
				
				return  !securedItem.getInnerStack().isEmpty() &&
						securedItem.getLocationIDOwner() == emp.getLocationID() &&
						securityBoxResult.getStackInSlot(0).isEmpty() &&
						itemResult.getStackInSlot(0).isEmpty();
						
			}
		});
		
		addSlotToContainer(new Slot(securityBoxResult, 0, 82, 42)
		{
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
			
			@Override
			public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
				craftingInventory.removeStackFromSlot(0);
				return super.onTake(thePlayer, stack);
			}
		});
		addSlotToContainer(new Slot(itemResult, 0, 124, 42)
		{
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
			
			@Override
			public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
				craftingInventory.removeStackFromSlot(0);
				return super.onTake(thePlayer, stack);
			}
		});
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistance(taggingPos.getX(), taggingPos.getY(), taggingPos.getZ()) <= 8.0D;
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
            
            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		
		ItemStack stackToDrop = craftingInventory.getStackInSlot(0);
		if (!stackToDrop.isEmpty())
		{
			playerIn.dropItem(stackToDrop, false);
		}
		if (stackToDrop.isEmpty())
		{
			stackToDrop = securityBoxResult.getStackInSlot(0);
			if (!stackToDrop.isEmpty())
			{
				playerIn.dropItem(stackToDrop, false);
			}
			
			stackToDrop = itemResult.getStackInSlot(0);
			if (!stackToDrop.isEmpty())
			{
				playerIn.dropItem(stackToDrop, false);
			}
		}
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		
		securityBoxResult.removeStackFromSlot(0);
		itemResult.removeStackFromSlot(0);
		
		ItemStack newCraftingStack = craftingInventory.getStackInSlot(0);
		if (!newCraftingStack.isEmpty() && newCraftingStack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) && !newCraftingStack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack().isEmpty())
		{
			ItemStack blankSecurityBox = new ItemStack(ModItems.SECURITY_BOX, 1);
			ItemStack item = newCraftingStack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack().copy();
			
			securityBoxResult.setInventorySlotContents(0, blankSecurityBox);
			itemResult.setInventorySlotContents(0, item);
		}
		
		super.onCraftMatrixChanged(inventoryIn);
	}
}
