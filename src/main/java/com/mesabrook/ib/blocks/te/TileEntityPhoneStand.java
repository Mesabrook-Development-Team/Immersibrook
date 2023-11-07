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
    private UUID playerUUID = new UUID(0, 0);
    private int rotation = 0;

    public void setPhone(ItemStack phoneIn)
    {
        this.phoneItem = phoneIn;
    }

    public ItemStack getPhoneItem()
    {
        return phoneItem;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
    }

    public int getRotation()
    {
        return rotation;
    }

    public void setOwnerUUID(UUID ownerUUIDIn)
    {
        this.playerUUID = ownerUUIDIn;
    }

    public UUID getOwnerUUID()
    {
        return this.playerUUID;
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
        this.playerUUID = tagCompound.getUniqueId("Owner");
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
        tagCompound.setUniqueId("Owner", this.playerUUID);
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
