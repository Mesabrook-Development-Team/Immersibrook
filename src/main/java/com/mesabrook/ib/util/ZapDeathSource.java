package com.mesabrook.ib.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class ZapDeathSource extends DamageSource
{
	public ZapDeathSource(String damageSourceIn)
	{
		super(damageSourceIn);
	}
	
    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
    	return new TextComponentTranslation("im.death.zap", entityLivingBaseIn.getDisplayName());
    }
}
