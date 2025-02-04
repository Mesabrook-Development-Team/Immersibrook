package com.mesabrook.ib.init;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.items.potions.PotionAsbestos;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionInit 
{
	public static final Potion ASBESTOS_EFFECT = new PotionAsbestos("asbestos", true, 7763574, 0, 0);
	public static final PotionType ASBESTOS = new PotionType("asbestos", new PotionEffect[] {new PotionEffect(ASBESTOS_EFFECT, 3600)}).setRegistryName("asbestos");
	public static final PotionType ASBESTOS_LONG = new PotionType("asbestos", new PotionEffect[] {new PotionEffect(ASBESTOS_EFFECT, 6800)}).setRegistryName("long_asbestos");

	
	public static void registerPotions()
	{
		registerPotion(ASBESTOS, ASBESTOS_LONG, ASBESTOS_EFFECT);
		registerPotionMixes();
	}
	private static void registerPotion(PotionType defaultPotion, PotionType longPotion, Potion effect)
	{
		try
		{
			ForgeRegistries.POTIONS.register(effect);
			ForgeRegistries.POTION_TYPES.register(defaultPotion);
			ForgeRegistries.POTION_TYPES.register(longPotion);
		}
		catch(Exception ex)
		{
			Main.logger.error("ERROR IN POTION REGISTRATION!");
			ex.printStackTrace();
		}
	}
	
	private static void registerPotionMixes()
	{
		PotionHelper.addMix(ASBESTOS, Items.REDSTONE, ASBESTOS_LONG);
		PotionHelper.addMix(PotionTypes.AWKWARD, Items.DIAMOND, ASBESTOS);
	}
}
