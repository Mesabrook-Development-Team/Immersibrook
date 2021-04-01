package rz.mesabrook.wbtc.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import rz.mesabrook.wbtc.items.drinks.WBTC_Beverage;
import rz.mesabrook.wbtc.items.misc.MiscItem;
import rz.mesabrook.wbtc.items.record.MemeRecord;
import rz.mesabrook.wbtc.items.weapons.ItemSod;

public class ModItems 
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	// Sod Materials
	public static final ToolMaterial SOD_WOOD = EnumHelper.addToolMaterial("wbtc_wood", 0, 30, 1.0F, -2.0F, 8);
	public static final ToolMaterial SOD_STONE = EnumHelper.addToolMaterial("wbtc_stone", 1, 66, 2.0F, -0.5F, 5);
	public static final ToolMaterial SOD_IRON = EnumHelper.addToolMaterial("wbtc_iron", 2, 125, 3.0F, -1.0F, 8);
	public static final ToolMaterial SOD_GOLD = EnumHelper.addToolMaterial("wbtc_gold", 0, 16, 1.0F, -2.0F, 11);
	public static final ToolMaterial SOD_DIAMOND = EnumHelper.addToolMaterial("wbtc_diamond", 3, 780, 4.0F, -0.5F, 5);
	
	// Sods
	public static final Item WOOD_SOD = new ItemSod("wood_sod", SOD_WOOD);
	public static final Item STONE_SOD = new ItemSod("stone_sod", SOD_STONE);
	public static final Item IRON_SOD = new ItemSod("iron_sod", SOD_IRON);
	public static final Item GOLD_SOD = new ItemSod("gold_sod", SOD_GOLD);
	public static final Item DIAMOND_SOD = new ItemSod("diamond_sod", SOD_DIAMOND);
	
	// Metals
	public static final Item IRON_ROD = new MiscItem("iron_rod", CreativeTabs.MISC, 64);
	public static final Item ALUMINUM_ROD = new MiscItem("aluminum_rod", CreativeTabs.MISC, 64);
	public static final Item ALUMINUM_INGOT = new MiscItem("ingot_aluminum", CreativeTabs.MATERIALS, 64);
	public static final Item ALUMINUM_NUGGET = new MiscItem("nugget_aluminum", CreativeTabs.MATERIALS, 64);
	public static final Item ALUMINUM_DUST = new MiscItem("aluminum_dust", CreativeTabs.MATERIALS, 64);
	
	// Raw Plastic
	public static final Item RAW_PLASTIC = new MiscItem("raw_plastic", CreativeTabs.MATERIALS, 64);
	
	// Colored Raw Plastic
	public static final Item RAW_PLASTIC_WHITE = new MiscItem("raw_white_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_ORANGE = new MiscItem("raw_orange_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_MAGENTA = new MiscItem("raw_magenta_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_LBLUE = new MiscItem("raw_lblue_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_YELLOW = new MiscItem("raw_yellow_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_LIME = new MiscItem("raw_lime_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_PINK = new MiscItem("raw_pink_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_GRAY = new MiscItem("raw_gray_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_SILVER = new MiscItem("raw_silver_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_CYAN = new MiscItem("raw_cyan_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_PURPLE = new MiscItem("raw_purple_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_BLUE = new MiscItem("raw_blue_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_BROWN = new MiscItem("raw_brown_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_GREEN = new MiscItem("raw_green_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_RED = new MiscItem("raw_red_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item RAW_PLASTIC_BLACK = new MiscItem("raw_black_plastic", CreativeTabs.MATERIALS, 64);
	
	// Finalized Colored Plastic Ingots
	public static final Item PLASTIC_WHITE = new MiscItem("white_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_ORANGE = new MiscItem("orange_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_MAGENTA = new MiscItem("magenta_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_LBLUE = new MiscItem("lblue_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_YELLOW = new MiscItem("yellow_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_LIME = new MiscItem("lime_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_PINK = new MiscItem("pink_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_GRAY = new MiscItem("gray_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_SILVER = new MiscItem("silver_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_CYAN = new MiscItem("cyan_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_PURPLE = new MiscItem("purple_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_BLUE = new MiscItem("blue_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_BROWN = new MiscItem("brown_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_GREEN = new MiscItem("green_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_RED = new MiscItem("red_plastic", CreativeTabs.MATERIALS, 64);
	public static final Item PLASTIC_BLACK = new MiscItem("black_plastic", CreativeTabs.MATERIALS, 64);
	
	// Meme Records
	public static final Item BLANK_DISC = new MiscItem("blank_vinyl", CreativeTabs.MISC, 16);
	public static final Item DISC_AMALTHEA = new MemeRecord("amalthea", SoundInit.AMALTHEA);
	public static final Item DISC_NYAN = new MemeRecord("nyan", SoundInit.NYAN);
	public static final Item DISC_USSR1 = new MemeRecord("ussr1", SoundInit.USSR1);
	public static final Item DISC_USSR2 = new MemeRecord("ussr2", SoundInit.USSR2);
	public static final Item DISC_BOOEY = new MemeRecord("baba_booey", SoundInit.BOOEY);
	public static final Item DISC_DOLAN = new MemeRecord("dolan", SoundInit.DOLAN);
	public static final Item DISC_MURICA = new MemeRecord("murica", SoundInit.MURICA);
	public static final Item DISC_PIGSTEP = new MemeRecord("pigstep", SoundInit.PIGSTEP);
	
}
