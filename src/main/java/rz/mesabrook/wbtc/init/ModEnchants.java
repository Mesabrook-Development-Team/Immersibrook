package rz.mesabrook.wbtc.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rz.mesabrook.wbtc.enchant.EnchantmentThunder;
import rz.mesabrook.wbtc.items.tools.ToolPickaxeBase;
import rz.mesabrook.wbtc.items.weapons.ItemCane;
import rz.mesabrook.wbtc.items.weapons.ItemWeapon;
import rz.mesabrook.wbtc.util.Reference;

@Mod.EventBusSubscriber(modid=Reference.MODID)
public class ModEnchants 
{
	public static final EnumEnchantmentType WEAPONS = EnumHelper.addEnchantmentType("weapons", (item)->(item instanceof ItemWeapon || item instanceof ToolPickaxeBase));

	public static final Enchantment THUNDER = new EnchantmentThunder();
	
	@SubscribeEvent
	public static void registerEnchantments(Register<Enchantment> event)
	{
		event.getRegistry().registerAll(THUNDER);
	}
}
