package rz.mesabrook.wbtc.items.misc;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

public class MiscItem extends Item implements IHasModel
{
	
	public MiscItem(String name, int stack)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(stack);
		
		ModItems.ITEMS.add(this);
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(this.getUnlocalizedName().contains("raw"))
		{
			tooltip.add(TextFormatting.GOLD + "Raw Material");
		}
		
		if(this.getUnlocalizedName().contains("nv_lens") || this.getUnlocalizedName().contains("nv_goggle_body") || this.getUnlocalizedName().contains("nv_goggle_circuits") || this.getUnlocalizedName().contains("nv_goggle_strap"))
		{
			tooltip.add(TextFormatting.GOLD + "Crafting Ingredient");
		}
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
