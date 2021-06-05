package rz.mesabrook.wbtc.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSourceHammer extends DamageSource
{
    public DamageSourceHammer(String damageSourceIn)
    {
        super(damageSourceIn);
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        return new TextComponentTranslation("im.death.bonk", entityLivingBaseIn.getDisplayName());
    }
}
