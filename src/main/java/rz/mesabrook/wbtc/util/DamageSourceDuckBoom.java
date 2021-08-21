package rz.mesabrook.wbtc.util;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import rz.mesabrook.wbtc.advancements.Triggers;

public class DamageSourceDuckBoom extends DamageSource
{
    public DamageSourceDuckBoom(String damageSourceIn)
    {
        super(damageSourceIn);
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        return new TextComponentTranslation("im.death.rubberduck", entityLivingBaseIn.getDisplayName());
    }
}
