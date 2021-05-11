package rz.mesabrook.wbtc.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rz.mesabrook.wbtc.enchant.EnchantmentThunder;
import rz.mesabrook.wbtc.items.weapons.ItemCane;
import rz.mesabrook.wbtc.items.weapons.ItemSod;
import rz.mesabrook.wbtc.util.Reference;

@Mod.EventBusSubscriber(modid=Reference.MODID)
public class ModEnchants 
{
	public static final EnumEnchantmentType WEAPONS = EnumHelper.addEnchantmentType("weapons", (item)->(item instanceof ItemSod || item instanceof ItemCane));

	public static final Enchantment THUNDER = new EnchantmentThunder();
	
	@SubscribeEvent
	public static void registerEnchantments(Register<Enchantment> event)
	{
		event.getRegistry().registerAll(THUNDER);
	}
}
