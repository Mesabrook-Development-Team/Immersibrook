package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.util.TileEntityHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySyncClient extends TileEntity
{
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
    	handleUpdateTag(pkt.getNbtCompound());
    }
    
    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
    	readFromNBT(tag);
    	
    	if (world.isRemote)
    	{
    		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    	}
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    public void sync()
    {
        TileEntityHelper.syncToClient(this);
    }
}
