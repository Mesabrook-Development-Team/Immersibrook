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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerTaggingStation extends Container {

	private final InventoryPlayer playerInventory;
	private final BlockPos taggingPos;
	private final InventoryBasic craftingInventory;
	private final IItemHandler taggingStationInventory;
	public final InventoryCraftResult craftResult;
	public double resetDistance;
	public ContainerTaggingStation(InventoryPlayer playerInventory, IItemHandler taggingStationInventory, BlockPos pos)
	{
		this.playerInventory = playerInventory;
		this.taggingStationInventory = taggingStationInventory;
		this.taggingPos = pos;
		this.craftingInventory = new InventoryBasic("Tagging Station", true, 1)
		{
			@Override
			public void markDirty() {
				super.markDirty();
				ContainerTaggingStation.this.onCraftMatrixChanged(this);
			}
		};
		this.craftResult = new InventoryCraftResult();
		
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
		addSlotToContainer(new SlotItemHandler(taggingStationInventory, 0, 30, 42)
				{			
					@Override
					public void onSlotChanged() {
						super.onSlotChanged();
						
						onCraftMatrixChanged(craftingInventory);
					}
				});
		addSlotToContainer(new Slot(craftingInventory, 0, 66, 42));
		
		addSlotToContainer(new Slot(craftResult, 0, 124, 42)
				{					
					@Override
					public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
						taggingStationInventory.getStackInSlot(0).shrink(1);
						craftingInventory.removeStackFromSlot(0);
						return super.onTake(thePlayer, stack);
					}
					
					@Override
					public boolean isItemValid(ItemStack stack) {
						return false;
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
            
            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		
		ItemStack stack = craftingInventory.getStackInSlot(0);
		if (!stack.isEmpty())
		{
			playerIn.dropItem(stack, false);
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
		if (taggingStationInventory.getStackInSlot(0).getItem() != ModItems.SECURITY_BOX || inventoryIn.getStackInSlot(0).isEmpty() || getResetDistance() <= 0)
		{
			craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
			super.onCraftMatrixChanged(inventoryIn);
			return;
		}
		
		ItemStack stackToWrap = inventoryIn.getStackInSlot(0);
		ItemStack securedItem = new ItemStack(ModItems.SECURITY_BOX);
		ISecuredItem item = securedItem.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
		IEmployeeCapability empCap = playerInventory.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		item.setInnerStack(stackToWrap);
		item.setLocation(empCap.getLocationEmployee().Location);
		item.setResetDistance(resetDistance);
		craftResult.setInventorySlotContents(0, securedItem);
		super.onCraftMatrixChanged(inventoryIn);
	}
	
	public IItemHandler getTaggingStationInventory()
	{
		return taggingStationInventory;
	}
	
	public double getResetDistance()
	{
		return resetDistance;
	}
	
	public void setResetDistance(double resetDistance)
	{
		this.resetDistance = resetDistance;
		
		onCraftMatrixChanged(craftingInventory);
		
		if (!craftResult.getStackInSlot(0).isEmpty() && craftResult.getStackInSlot(0).hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
		{
			craftResult.getStackInSlot(0).getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).setResetDistance(resetDistance);
		}
		
		detectAndSendChanges();
	}
}
