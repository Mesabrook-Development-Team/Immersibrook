package com.mesabrook.ib.blocks.te;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntitySmoker extends TileEntity implements ITickable
{
    public boolean isPowered;
    public TileEntitySmoker()
    {

    }

    public void emitSmoke()
    {
        World world = getWorld();
        if (world != null)
        {
            // Emit continuous smoke particles logic goes here
            for (int i = 0; i < 10; i++)
            {
                double offsetX = world.rand.nextGaussian() * 0.02D;
                double offsetY = world.rand.nextGaussian() * 0.02D;
                double offsetZ = world.rand.nextGaussian() * 0.02D;

                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        pos.getX() + 0.5,
                        pos.getY() + 1.0,
                        pos.getZ() + 0.5,
                        offsetX, offsetY + 0.4, offsetZ);
            }
        }
    }

    @Override
    public void update()
    {
        isPowered = world.isBlockPowered(pos);
        if(isPowered)
        {
            emitSmoke();
        }
    }
}
