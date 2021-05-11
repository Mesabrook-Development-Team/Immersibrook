package rz.mesabrook.wbtc.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.inventory.EntityEquipmentSlot;
import rz.mesabrook.wbtc.init.ModEnchants;

public class EnchantmentThunder extends Enchantment
{
	public EnchantmentThunder()
	{
		super(Rarity.UNCOMMON, ModEnchants.WEAPONS, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		setRegistryName("thunder");
		setName("thunder");
	}
	
	@Override
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
	{
		user.world.addWeatherEffect(new EntityLightningBolt(user.world, target.posX, target.posY, target.posZ, false));
	}
}
