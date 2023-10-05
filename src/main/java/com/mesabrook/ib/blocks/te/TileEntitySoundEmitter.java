package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEntitySoundEmitter extends TileEntity implements ITickable
{
    private int range;
    private String modID;
    private String soundID;

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
    public void update()
    {
        if (world.isBlockPowered(pos))
        {
            try
            {
                EntityPlayer player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), getRange(), false);
                player.sendMessage(new TextComponentString("Active"));

                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = pos;
                packet.modID = getModID();
                packet.soundName = getSoundID();
                packet.rapidSounds = false;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, getRange()));
            }
            catch(Exception ex)
            {
                return;
            }
        }
        else
        {
            // Block is not powered, do something else here
        }
    }
}
