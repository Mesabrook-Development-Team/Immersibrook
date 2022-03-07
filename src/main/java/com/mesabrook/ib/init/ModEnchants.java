package com.mesabrook.ib.init;

import com.mesabrook.ib.enchant.EnchantmentThunder;
import com.mesabrook.ib.items.tools.ItemBanHammer;
import com.mesabrook.ib.items.weapons.ItemWeapon;
import com.mesabrook.ib.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=Reference.MODID)
public class ModEnchants 
{
	public static final EnumEnchantmentType WEAPONS = EnumHelper.addEnchantmentType("weapons", (item)->(item instanceof ItemWeapon || item instanceof ItemBanHammer));

	public static final Enchantment THUNDER = new EnchantmentThunder();
	
	@SubscribeEvent
	public static void registerEnchantments(Register<Enchantment> event)
	{
		event.getRegistry().registerAll(THUNDER);
	}
}
