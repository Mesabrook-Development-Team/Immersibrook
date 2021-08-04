package rz.mesabrook.wbtc.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import rz.mesabrook.wbtc.advancements.Triggers;

public class DamageSourceHammer extends DamageSource
{
    public DamageSourceHammer(String damageSourceIn)
    {
        super(damageSourceIn);
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        if(SoundRandomizer.hammerResult == "beaned")
        {
            if(entityLivingBaseIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                Triggers.trigger(Triggers.BONKED, player);
            }
            return new TextComponentTranslation("im.death.beaned", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "bong" || SoundRandomizer.hammerResult == "dove")
        {
            if(entityLivingBaseIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                Triggers.trigger(Triggers.BONKED, player);
            }
            return new TextComponentTranslation("im.death.bong", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "bonk")
        {
            if(entityLivingBaseIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                Triggers.trigger(Triggers.BONKED, player);
            }
            return new TextComponentTranslation("im.death.bonk", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "owie")
        {
            if(entityLivingBaseIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                Triggers.trigger(Triggers.BONKED, player);
            }
            return new TextComponentTranslation("im.death.bones", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "cpw")
        {
            if(entityLivingBaseIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                Triggers.trigger(Triggers.BONKED, player);
            }
            return new TextComponentTranslation("im.death.cpw", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "sploot")
        {
            if(entityLivingBaseIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                Triggers.trigger(Triggers.BONKED, player);
            }
            return new TextComponentTranslation("im.death.sploot", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "squidward")
        {
            if(entityLivingBaseIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                Triggers.trigger(Triggers.BONKED, player);
            }
            return new TextComponentTranslation("im.death.krabs", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "reverb")
        {
            if(entityLivingBaseIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
                Triggers.trigger(Triggers.BONKED, player);
            }
            return new TextComponentTranslation("im.death.reverb", entityLivingBaseIn.getDisplayName());
        }
        if(entityLivingBaseIn instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
            Triggers.trigger(Triggers.BONKED, player);
        }

        return new TextComponentTranslation("im.death.default", entityLivingBaseIn.getDisplayName());
    }
}
