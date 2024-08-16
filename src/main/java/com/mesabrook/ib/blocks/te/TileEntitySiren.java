package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.init.ModSounds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class TileEntitySiren extends TileEntity implements ITickable
{
    private boolean isPowered = false;
    private boolean isPlaying = false;
    private int cooldown = 0;
    private int delay = 20; // Default delay between checks (ticks)
    private int radius = 64; // Default radius for sound
    private int soundDuration = 120; // Duration of the siren sound in ticks
    private SoundEvent sirenSound = null;
    
    @Override
    public void update() 
    {
        if (isPowered) 
        {
            if (!isPlaying) 
            {
                playSiren();
                isPlaying = true;
                cooldown = soundDuration; // Set cooldown to sound duration
            } 
            else 
            {
                cooldown--;
                if (cooldown <= 0) 
                {
                    isPlaying = false; // Reset the flag to allow the sound to be played again
                }
            }
        } 
        else 
        {
            if (isPlaying) 
            {
                stopSiren();
            }
        }
    }
    
    public SoundEvent setSirenSound(SoundEvent sirenSoundIn)
    {
    	return sirenSound = sirenSoundIn;
    }
    
    public SoundEvent getSirenSound()
    {
    	return sirenSound;
    }
    
    public String getSirenSoundString()
    {
    	return sirenSound.getRegistryName().toString();
    }
    
    public int setDuration(int durationIn)
    {
    	return soundDuration = durationIn;
    }
    
    public int getDuration()
    {
    	return soundDuration;
    }
    
    public int setRadius(int radiusIn)
    {
    	return radius = radiusIn;
    }
    
    public int getRadius()
    {
    	return radius;
    }
    
    public void setPowered(boolean powered) 
    {
        this.isPowered = powered;
        if (!powered && isPlaying) 
        {
            stopSiren();
        }
    }

    private void playSiren() 
    {
        if (!world.isRemote)
        {
            world.playSound(null, pos, sirenSound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            sendSirenPacket();
        }
    }
    
    private void stopSiren()
    {
        if (isPlaying) 
        {
            // Reset the playing flag and cooldown
            isPlaying = false;
            cooldown = 0;
        }
    }

    private void sendSirenPacket() 
    {
        if (!world.isRemote) 
        {
            SPacketCustomSound sirenPacket = new SPacketCustomSound(
                sirenSound.getRegistryName().toString(), 
                SoundCategory.BLOCKS, 
                pos.getX(), pos.getY(), pos.getZ(), 
                1.0F, 1.0F
            );
            for (EntityPlayer player : world.playerEntities) 
            {
                if (player.getDistanceSq(pos) <= radius * radius) 
                {
                    ((EntityPlayerMP) player).connection.sendPacket(sirenPacket);
                }
            }
        }
    }
}
