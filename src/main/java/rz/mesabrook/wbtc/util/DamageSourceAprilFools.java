package rz.mesabrook.wbtc.util;

import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSourceAprilFools extends DamageSource
{
    public DamageSourceAprilFools(String damageSourceIn)
    {
        super(damageSourceIn);
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        return new TextComponentTranslation("im.death.kekw", entityLivingBaseIn.getDisplayName());
    }
}
