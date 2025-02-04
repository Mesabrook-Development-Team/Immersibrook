package com.mesabrook.ib.items.potions;

import com.mesabrook.ib.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionAsbestos extends Potion
{
	public PotionAsbestos(String name, boolean isBadPotion, int color, int iconIndexX, int iconIndexY)
	{
		super(isBadPotion, color);
		setPotionName("effect." + name);
		setIconIndex(iconIndexX, iconIndexY);
		setRegistryName(new ResourceLocation(Reference.MODID + ":" + name));
	}
	
	@Override
	public boolean hasStatusIcon()
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":" + "textures/gui/potion_effects.png"));
		return true;
	}
}
