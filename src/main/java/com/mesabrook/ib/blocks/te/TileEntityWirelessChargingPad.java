package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.util.ISimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class TileEntityWirelessChargingPad extends TileEntitySyncClient implements ISimpleInventory
{
    private ItemStack phoneItem = ItemStack.EMPTY;
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

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.setPhone(ItemStack.EMPTY);
        if(tagCompound.hasKey("Items", Constants.NBT.TAG_LIST))
        {
            NBTTagList tagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < tagList.tagCount(); ++i)
            {
                NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
                ItemStack stack = new ItemStack(itemTag);
                this.setPhone(stack);
            }
        }
        else if(tagCompound.hasKey("Item", Constants.NBT.TAG_COMPOUND))
        {
            this.setPhone(new ItemStack(tagCompound.getCompoundTag("Item")));
        }
        this.rotation = tagCompound.getInteger("Rotation");
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
    }
}
