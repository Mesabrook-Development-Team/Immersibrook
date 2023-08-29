package com.mesabrook.ib.blocks.container;

import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
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
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ContainerTaggingStation extends Container {

	private final InventoryPlayer playerInventory;
	private final BlockPos taggingPos;
	private final InventoryCrafting craftingInventory;
	public final InventoryCraftResult craftResult;
	public double resetDistance;
	public ContainerTaggingStation(InventoryPlayer playerInventory, BlockPos pos)
	{
		this.playerInventory = playerInventory;
		this.taggingPos = pos;
		this.craftingInventory = new InventoryCrafting(this, 2, 1);
		this.craftResult = new InventoryCraftResult();
		
		// Hot bar
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 117));
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
			
			addSlotToContainer(new Slot(playerInventory, i, 8 + column * 18, 59 + 18 * row));
		}
		
		// Crafting grid
		addSlotToContainer(new Slot(craftingInventory, 0, 30, 14)
				{
					@Override
					public boolean isItemValid(ItemStack stack) {
						return stack.getItem() == ModItems.SECURITY_BOX;
					}
				});
		addSlotToContainer(new Slot(craftingInventory, 1, 66, 14));
		
		addSlotToContainer(new Slot(craftResult, 0, 124, 14)
				{					
					@Override
					public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
						craftingInventory.decrStackSize(0, 1);
						craftingInventory.removeStackFromSlot(1);
						return super.onTake(thePlayer, stack);
					}
				});
		
		TileEntity te = playerInventory.player.world.getTileEntity(pos);
		if (te instanceof TileEntityTaggingStation)
		{
			TileEntityTaggingStation tagStation = (TileEntityTaggingStation)te;
			resetDistance = tagStation.getDistanceDefault();
		}
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
        }

        return itemstack;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		
		for(int i = 0; i < 2; i++)
		{
			ItemStack stack = craftingInventory.getStackInSlot(i);
			if (!stack.isEmpty())
			{
				playerIn.dropItem(stack, false);
			}
		}
		
		if (!playerIn.world.isRemote)
		{
			TileEntity te = playerIn.world.getTileEntity(taggingPos);
			if (te != null && te instanceof TileEntityTaggingStation)
			{
				TileEntityTaggingStation tagStation = (TileEntityTaggingStation)te;
				tagStation.setDistanceDefault(resetDistance);
				playerIn.world.notifyBlockUpdate(taggingPos, playerIn.world.getBlockState(taggingPos), playerIn.world.getBlockState(taggingPos), 3);
			}
		}
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		if (inventoryIn.getStackInSlot(0).getItem() != ModItems.SECURITY_BOX || inventoryIn.getStackInSlot(1).isEmpty() || getResetDistance() <= 0)
		{
			craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
			super.onCraftMatrixChanged(inventoryIn);
			return;
		}
		
		ItemStack stackToWrap = inventoryIn.getStackInSlot(1);
		ItemStack securedItem = new ItemStack(ModItems.SECURITY_BOX);
		ISecuredItem item = securedItem.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
		IEmployeeCapability empCap = playerInventory.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		item.setInnerStack(stackToWrap);
		item.setLocationIDOwner(empCap.getLocationID());
		item.setResetDistance(resetDistance);
		craftResult.setInventorySlotContents(0, securedItem);
		super.onCraftMatrixChanged(inventoryIn);
	}
	
	public double getResetDistance()
	{
		return resetDistance;
	}
	
	public void setResetDistance(double resetDistance)
	{
		this.resetDistance = resetDistance;
		
		onCraftMatrixChanged(craftingInventory);
		detectAndSendChanges();
	}
}
