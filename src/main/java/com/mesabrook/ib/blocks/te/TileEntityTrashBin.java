package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.blocks.container.ContainerTrashBin;
import com.mesabrook.ib.init.ModSounds;
import com.mesabrook.ib.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityTrashBin extends TileEntityLockableLoot implements ITickable
{
	private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(72, ItemStack.EMPTY);
	public int numPlayersUsing, ticksSinceSync;
	public float lidAngle, prevLidAngle;
	
	@Override
	public int getSizeInventory()
	{
		return 72;
	}
	
	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}
	
	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : this.chestContents)
		{
			if(!stack.isEmpty()) return false;
		}
		
		return true;
	}
	
	@Override
	public String getName()
	{
		return "Trash";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.chestContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		
		if(!this.checkLootAndRead(compound)) ItemStackHelper.loadAllItems(compound, chestContents);
		if(compound.hasKey("CustomName", 8)) this.customName = compound.getString("CustomName");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		if(!this.checkLootAndWrite(compound)) ItemStackHelper.saveAllItems(compound, chestContents);
		if(compound.hasKey("CustomName", 8)) compound.setString("CustomName", this.customName);
		
		return compound;
	}
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) 
	{
		return new ContainerTrashBin(playerInventory, this, playerIn);
	}
	
	@Override
	public String getGuiID() 
	{
		return Reference.MODID + ":trash_bin";
	}
	
	@Override
	protected NonNullList<ItemStack> getItems() 
	{
		return this.chestContents;
	}
	
	@Override
	public void update()
	{
		if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + pos.getX() + pos.getY() + pos.getZ()) % 200 == 0)
        {
            this.numPlayersUsing = 0;
            float f = 5.0F;

            for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)pos.getX() - 5.0F), (double)((float)pos.getY() - 5.0F), (double)((float)pos.getZ() - 5.0F), (double)((float)(pos.getX() + 1) + 5.0F), (double)((float)(pos.getY() + 1) + 5.0F), (double)((float)(pos.getZ() + 1) + 5.0F))))
            {
                if (entityplayer.openContainer instanceof ContainerTrashBin)
                {
                    if (((ContainerTrashBin)entityplayer.openContainer).getChestInventory() == this)
                    {
                        ++this.numPlayersUsing;
                    }
                }
            }
        }
		
        this.prevLidAngle = this.lidAngle;
        float f1 = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
        {
            //double d1 = (double)pos.getX() + 0.5D;
            //double d2 = (double)pos.getZ() + 0.5D;
            //this.world.playSound((EntityPlayer)null, d1, (double)pos.getY() + 0.5D, d2, SoundInit.CAN_OPEN, SoundCategory.AMBIENT, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
        {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0)
            {
                this.lidAngle += 0.1F;
            }
            else
            {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f3 = 0.5F;

            if (this.lidAngle < 0.5F && f2 >= 0.5F)
            {
                //double d3 = (double)pos.getX() + 0.5D;
                //double d0 = (double)pos.getZ() + 0.5D;
                //this.world.playSound((EntityPlayer)null, d3, (double)pos.getY() + 0.5D, d0, SoundInit.CAN_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }		
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		if(!player.isSpectator())
		{
			++this.numPlayersUsing;
			this.world.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
			
			double d1 = (double)pos.getX() + 0.5D;
	        double d2 = (double)pos.getZ() + 0.5D;
	        this.world.playSound((EntityPlayer)null, d1, (double)pos.getY() + 0.5D, d2, ModSounds.CAN_OPEN, SoundCategory.AMBIENT, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
		}
	}
	
	@Override
	public void closeInventory(EntityPlayer player) 
	{
		--this.numPlayersUsing;
		this.world.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
		this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
		
        double d3 = (double)pos.getX() + 0.5D;
        double d0 = (double)pos.getZ() + 0.5D;
        this.world.playSound((EntityPlayer)null, d3, (double)pos.getY() + 0.5D, d0, ModSounds.CAN_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
	}	
}
