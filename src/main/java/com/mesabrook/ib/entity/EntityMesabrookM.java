package com.mesabrook.ib.entity;

import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.Reference;
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

@SuppressWarnings("deprecated")
public class EntityMesabrookM extends EntityThrowable
{
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
    @SuppressWarnings("deprecated")
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
                    if(profile != null && Reference.RZ_UUID.equals(profile.getId()) || Reference.CSX_UUID.equals(profile.getId()) || Reference.SLOOSE_UUID.equals(profile.getId()) || Reference.ZOE_UUID.equals(profile.getId()))
                    {
                        hit = true;
                        EntityLightningBolt lightningBolt = new EntityLightningBolt(world, getThrower().posX, getThrower().posY, getThrower().posZ, true);
                        world.addWeatherEffect(lightningBolt);
                        world.spawnEntity(lightningBolt);
                    }
                }
            }

            if(!hit)
            {
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.1f);
            }

        }
        if(!this.world.isRemote)
        {

            if(damage < ModItems.IMMERSIBROOK_ICON.getMaxDamage())
            {
                damage++;
                this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(ModItems.IMMERSIBROOK_ICON, 1, damage)));
            }
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}