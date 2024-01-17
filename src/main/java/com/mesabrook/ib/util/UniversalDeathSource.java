package com.mesabrook.ib.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class UniversalDeathSource extends DamageSource
{
    private String deathMessage = "";

    /**
     * A simple generic DeathSource for Immersibrook.
     *
     * HOW TO USE:
     * Pass a damage source string in and then an unlocalized death message string.
     * Example: "im.death.kekw"
     *
     * @param damageSourceIn String
     * @param deathMessageString String
     */
    public UniversalDeathSource(String damageSourceIn, String deathMessageString)
    {
        super(damageSourceIn);
        this.deathMessage = deathMessageString;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        return new TextComponentTranslation(deathMessage, entityLivingBaseIn.getDisplayName());
    }
}
