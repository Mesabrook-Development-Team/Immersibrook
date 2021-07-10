package rz.mesabrook.wbtc.items.armor;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

public class SafetyVest extends ItemArmor implements IHasModel
{
	public SafetyVest(String name, CreativeTabs tab, ArmorMaterial mat, EntityEquipmentSlot equipSlot)
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
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		if(playerIn instanceof EntityPlayer)
		{
			Triggers.trigger(Triggers.WEAR_VEST, playerIn);
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		NBTTagCompound tag = itemStack.getTagCompound();
		boolean glowing = true;
		
		if (tag != null)
		{
			glowing = tag.getBoolean("glowing");
		}
		
		if(player instanceof EntityPlayer && glowing)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 10, true, false));
		}
		else if(player instanceof EntityPlayer && !glowing)
		{
			player.removeActivePotionEffect(MobEffects.GLOWING);
		}
	}
}
