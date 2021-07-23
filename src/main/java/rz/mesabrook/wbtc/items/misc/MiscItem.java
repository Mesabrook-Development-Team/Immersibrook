package rz.mesabrook.wbtc.items.misc;

import java.util.List;
import java.util.Locale;

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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

public class MiscItem extends Item implements IHasModel
{
	private final TextComponentTranslation color = new TextComponentTranslation("im.color");
	public MiscItem(String name, int stack)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(stack);
		
		ModItems.ITEMS.add(this);

		color.getStyle().setBold(true);
		color.getStyle().setColor(TextFormatting.YELLOW);
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(this.getUnlocalizedName().contains("nv_lens") || this.getUnlocalizedName().contains("nv_goggle_body") || this.getUnlocalizedName().contains("nv_goggle_circuits") || this.getUnlocalizedName().contains("nv_goggle_strap"))
		{
			tooltip.add(TextFormatting.GOLD + "Crafting Ingredient");
		}

		if(this.getUnlocalizedName().contains("dye_red") || this.getUnlocalizedName().contains("sugar_red"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.RED + "RED");
		}
		if(this.getUnlocalizedName().contains("dye_green") || this.getUnlocalizedName().contains("sugar_green"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.DARK_GREEN + "GREEN");
		}
		if(this.getUnlocalizedName().contains("dye_blue") || this.getUnlocalizedName().contains("sugar_blue"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.BLUE + "BLUE");
		}
		if(this.getUnlocalizedName().contains("dye_white"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.WHITE + "WHITE");
		}
		if(this.getUnlocalizedName().contains("dye_yellow"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.YELLOW + "YELLOW");
		}
		if(this.getUnlocalizedName().contains("sugar_orange"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.GOLD + "ORANGE");
		}
		if(this.getUnlocalizedName().contains("sugar_purple"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.DARK_PURPLE + "PURPLE");
		}
		if(this.getUnlocalizedName().contains("sugar_brown"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.DARK_RED + "BROWN");
		}
		if(this.getUnlocalizedName().contains("sugar_lime"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.GREEN + "LIME");
		}
		if(this.getUnlocalizedName().contains("sugar_pink"))
		{
			tooltip.add(TextFormatting.GOLD + "Color: " + TextFormatting.LIGHT_PURPLE + "PINK");
		}

		if(this.getUnlocalizedName().contains("raw_candy_red"))
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.LIGHT_PURPLE + "Strawberry");
		}

		if(this.getUnlocalizedName().contains("raw_candy_lime"))
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.GREEN + "Lime");
		}

		if(this.getUnlocalizedName().contains("raw_candy_blue"))
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.BLUE + "Blueberry");
		}

		if(this.getUnlocalizedName().contains("raw_candy_orange"))
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.GOLD + "Orange");
		}

		if(this.getUnlocalizedName().contains("raw_candy_grape"))
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.DARK_PURPLE + "Grape");
		}

		if(this.getUnlocalizedName().contains("raw_candy_rb"))
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.DARK_RED + "Root Beer");
		}

		if(this.getUnlocalizedName().contains("raw_candy_choc"))
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.DARK_RED + "Chocolate");
		}

		if(this.getUnlocalizedName().contains("raw_candy_pl"))
		{
			tooltip.add(TextFormatting.GOLD + "Flavor: " + TextFormatting.LIGHT_PURPLE + "Pink Lemonade");
		}
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{

	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
