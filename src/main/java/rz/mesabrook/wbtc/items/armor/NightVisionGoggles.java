package rz.mesabrook.wbtc.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.rendering.ModelCustomArmor;
import rz.mesabrook.wbtc.rendering.ModelNVGoggles;
import rz.mesabrook.wbtc.util.IHasModel;

public class NightVisionGoggles extends ItemArmor implements IHasModel
{
	public NightVisionGoggles(String name, CreativeTabs tab, ArmorMaterial mat, EntityEquipmentSlot equipSlot)
	{
		super(mat, 1, equipSlot);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(1);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 210, 1, true, false));
	}
}
