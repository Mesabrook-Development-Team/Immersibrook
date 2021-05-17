package rz.mesabrook.wbtc.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

public class SafetyVestBase extends ItemArmor implements IHasModel
{
	public SafetyVestBase(String name, ArmorMaterial mat, int renderIndex, EntityEquipmentSlot equipSlot)
	{
		super(mat, renderIndex, equipSlot);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if(!player.isPotionActive(MobEffects.GLOWING))
		{
			player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100, 10, true, false));
		}
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
