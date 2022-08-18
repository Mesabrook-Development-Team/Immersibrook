package com.mesabrook.ib.blocks.te;

import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.tileentity.*;

public class TileEntityWallSign extends TileEntity
{
    private String signId = "";
    private String lineOne = "";
    private String lineTwo = "";

    public TileEntityWallSign() {super();}

    public TileEntityWallSign(String signID)
    {
        super();
        this.signId = signID;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        signId = compound.getString("signId");
        lineOne = compound.getString("line1");
        lineTwo = compound.getString("line2");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setString("signId", signId);
        compound.setString("line1", lineOne);
        compound.setString("line2", lineTwo);
        return super.writeToNBT(compound);
    }

    public String getLineOne() {return lineOne;}
    public String getLineTwo() {return lineTwo;}

    public void setLineOne(String lineOne)
    {
        this.lineOne = lineOne;
        markDirty();
    }
    public void setLineTwo(String lineTwo)
    {
        this.lineTwo = lineTwo;
        markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setString("line1", getLineOne());
        tag.setString("line2", getLineTwo());
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        super.handleUpdateTag(tag);

        this.lineOne = tag.getString("line1");
        this.lineTwo = tag.getString("line2");
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("line1", getLineOne());
        tag.setString("line2", getLineTwo());
        return new SPacketUpdateTileEntity(getPos(), 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.lineOne = pkt.getNbtCompound().getString("line1");
        this.lineTwo = pkt.getNbtCompound().getString("line2");
        super.onDataPacket(net, pkt);
    }
}
