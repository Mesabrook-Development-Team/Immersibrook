package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.util.ISimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class TileEntityPhoneStand extends TileEntitySyncClient implements ISimpleInventory
{
    private ItemStack phoneItem = ItemStack.EMPTY;
    private long locationIDOwner;
    private int rotation = 0;

    public void setPhone(ItemStack phoneIn)
    {
        this.phoneItem = phoneIn;
        markDirty();
    }

    public ItemStack getPhoneItem()
    {
        return phoneItem;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
        markDirty();
    }

    public int getRotation()
    {
        return rotation;
    }

    public long getLocationIDOwner() {
		return locationIDOwner;
	}

	public void setLocationIDOwner(long locationIDOwner) {
		this.locationIDOwner = locationIDOwner;
		markDirty();
	}

	@Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.setPhone(ItemStack.EMPTY);
        if(tagCompound.hasKey("Item", Constants.NBT.TAG_COMPOUND))
        {
            this.setPhone(new ItemStack(tagCompound.getCompoundTag("Item")));
        }
        this.rotation = tagCompound.getInteger("Rotation");
        this.locationIDOwner = tagCompound.getLong("locationIDOwner");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        if(this.phoneItem != null)
        {
            tagCompound.setTag("Item", this.phoneItem.writeToNBT(new NBTTagCompound()));
        }
        tagCompound.setInteger("Rotation", this.rotation);
        tagCompound.setLong("locationIDOwner", this.locationIDOwner);
        return tagCompound;
    }

    @Override
    public int getSize()
    {
        return 1;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return getPhoneItem();
    }

    @Override
    public void clear()
    {
        phoneItem = null;
        markDirty();
    }
}
