package rz.mesabrook.wbtc.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityWineBottle extends EntityThrowable
{
    public EntityWineBottle(World worldIn) {super(worldIn);}
    public EntityWineBottle(World worldIn, EntityLivingBase throwerIn) {super(worldIn, throwerIn);}
    public EntityWineBottle(World worldIn, double x, double y, double z) {super(worldIn, x, y, z);}

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(!this.world.isRemote)
        {
            this.world.playEvent(2002, new BlockPos(this), PotionUtils.getPotionColor(PotionTypes.HARMING));
            this.setDead();
        }
    }

}
