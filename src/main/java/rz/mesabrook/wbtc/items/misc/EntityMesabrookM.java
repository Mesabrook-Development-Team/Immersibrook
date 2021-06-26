package rz.mesabrook.wbtc.items.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import rz.mesabrook.wbtc.init.ModItems;

import java.util.UUID;

public class EntityMesabrookM extends EntityThrowable
{
    public static final UUID RZ_UUID = UUID.fromString("c2907bdd-9aba-4c20-b83b-ddb41c004e78");
    public static final UUID CSX_UUID = UUID.fromString("717bb4e7-c701-42a6-b06f-bbe17e0518ae");
    private int damage;

    public EntityMesabrookM(World worldIn)
    {
        super(worldIn);
    }

    public EntityMesabrookM(World worldIn, EntityLivingBase throwerIn, int damage)
    {
        super(worldIn, throwerIn);
        this.damage = damage;
    }

    public EntityMesabrookM(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("mesarangDamage", damage);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        damage = compound.getInteger("mesarangDamage");
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if(result.entityHit != null)
        {
            boolean hit = false;
            damage++;

            if(!world.isRemote)
            {
                if(result.entityHit instanceof EntityPlayerMP)
                {
                    GameProfile profile = ((EntityPlayerMP) result.entityHit).getGameProfile();
                    if(profile != null && RZ_UUID.equals(profile.getId()) || CSX_UUID.equals(profile.getId()))
                    {
                        hit = true;
                        damage = Integer.MAX_VALUE;
                        EntityLightningBolt lightningBolt = new EntityLightningBolt(world, getThrower().posX, getThrower().posY, getThrower().posZ, true);
                        world.addWeatherEffect(lightningBolt);
                        world.spawnEntity(lightningBolt);
                    }
                }
            }

            if(!hit)
            {
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5f);
            }

        }
        if(!this.world.isRemote)
        {
            if(damage < ModItems.IMMERSIBROOK_ICON.getMaxDamage())
            {
                this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(ModItems.IMMERSIBROOK_ICON, 1, damage)));
            }
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}