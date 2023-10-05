package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.blocks.BlockSoundEmitter;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEntitySoundEmitter extends TileEntity implements ITickable
{
    private int range = 15;
    private String modID = "";
    private String soundID = "";

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        modID = compound.getString("modID");
        soundID = compound.getString("soundID");
        range = compound.getInteger("range");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setString("modID", modID);
        compound.setString("soundID", soundID);
        compound.setInteger("range", range);
        return super.writeToNBT(compound);
    }

    public int getRange()
    {
        return range;
    }

    public int setRange(int rangeIn)
    {
        markDirty();
        return range = rangeIn;
    }

    public String getModID()
    {
        return modID;
    }

    public String setModID(String modIDIn)
    {
        markDirty();
        return modID = modIDIn;
    }

    public String getSoundID()
    {
        return soundID;
    }

    public String setSoundID(String soundIDIn)
    {
        markDirty();
        return soundID = soundIDIn;
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setString("modID", getModID());
        tag.setString("soundID", getSoundID());
        tag.setInteger("range", getRange());
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        super.handleUpdateTag(tag);
        this.modID = tag.getString("modID");
        this.soundID = tag.getString("soundID");
        this.range = tag.getInteger("range");
    }

    @Override
    public void update()
    {

    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("modID", getModID());
        tag.setString("soundID", getSoundID());
        tag.setInteger("range", getRange());
        return new SPacketUpdateTileEntity(getPos(), 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.modID = pkt.getNbtCompound().getString("modID");
        this.soundID = pkt.getNbtCompound().getString("soundID");
        this.range = pkt.getNbtCompound().getInteger("range");
        super.onDataPacket(net, pkt);
    }
}
