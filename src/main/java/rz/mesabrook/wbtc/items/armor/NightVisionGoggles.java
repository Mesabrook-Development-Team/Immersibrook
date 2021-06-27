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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
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
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		if(playerIn instanceof EntityPlayer)
		{
			Triggers.trigger(Triggers.WEAR_NV, playerIn);
		}
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		NBTTagCompound tag = itemStack.getTagCompound();
		boolean nightvision = true;

		if (tag != null)
		{
			nightvision = tag.getBoolean("nightvision");
		}

		if(player instanceof EntityPlayer && nightvision)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 210, 1, true, false));
		}
		else if(player instanceof EntityPlayer && !nightvision)
		{
			player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
		}
	}
}
