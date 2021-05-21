package rz.mesabrook.wbtc.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.items.armor.NightVisionGoggles;
import rz.mesabrook.wbtc.items.armor.SafetyVestModel;
import rz.mesabrook.wbtc.items.misc.ItemFirstAidKit;
import rz.mesabrook.wbtc.items.misc.MiscItem;
import rz.mesabrook.wbtc.items.record.MemeRecord;
import rz.mesabrook.wbtc.items.weapons.ItemCane;
import rz.mesabrook.wbtc.items.weapons.ItemSod;
import rz.mesabrook.wbtc.util.Reference;

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
	
	// Special Cane
	public static final Item ZOE_CANE = new ItemCane("zoe_cane", ToolMaterial.DIAMOND);
	
	// Immersibrook Creative Tab Item
	public static final Item IMMERSIBROOK_ICON = new MiscItem("icon_immersibrook", 1);
	
	// Metals
	public static final Item IRON_ROD = new MiscItem("iron_rod", 64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item ALUMINUM_ROD = new MiscItem("aluminum_rod", 64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item ALUMINUM_INGOT = new MiscItem("ingot_aluminum", 64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item ALUMINUM_NUGGET = new MiscItem("nugget_aluminum", 64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item ALUMINUM_DUST = new MiscItem("aluminum_dust", 64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	
	// Raw Plastic
	public static final Item RAW_PLASTIC = new MiscItem("raw_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	
	// Colored Raw Plastic
	public static final Item RAW_PLASTIC_WHITE = new MiscItem("raw_white_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_ORANGE = new MiscItem("raw_orange_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_MAGENTA = new MiscItem("raw_magenta_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_LBLUE = new MiscItem("raw_lblue_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_YELLOW = new MiscItem("raw_yellow_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_LIME = new MiscItem("raw_lime_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_PINK = new MiscItem("raw_pink_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_GRAY = new MiscItem("raw_gray_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_SILVER = new MiscItem("raw_silver_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_CYAN = new MiscItem("raw_cyan_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_PURPLE = new MiscItem("raw_purple_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_BLUE = new MiscItem("raw_blue_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_BROWN = new MiscItem("raw_brown_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_GREEN = new MiscItem("raw_green_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_RED = new MiscItem("raw_red_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_BLACK = new MiscItem("raw_black_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item RAW_PLASTIC_GLOWING = new MiscItem("raw_glowing_plastic", 64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	
	// Finalized Colored Plastic Ingots
	public static final Item PLASTIC_WHITE = new MiscItem("white_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_ORANGE = new MiscItem("orange_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_MAGENTA = new MiscItem("magenta_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_LBLUE = new MiscItem("lblue_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_YELLOW = new MiscItem("yellow_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_LIME = new MiscItem("lime_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_PINK = new MiscItem("pink_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_GRAY = new MiscItem("gray_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_SILVER = new MiscItem("silver_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_CYAN = new MiscItem("cyan_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_PURPLE = new MiscItem("purple_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_BLUE = new MiscItem("blue_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_BROWN = new MiscItem("brown_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_GREEN = new MiscItem("green_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_RED = new MiscItem("red_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_BLACK = new MiscItem("black_plastic",  64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item PLASTIC_GLOWING = new MiscItem("glowing_plastic", 64).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	
	// Meme Records
	public static final Item BLANK_DISC = new MiscItem("blank_vinyl",  16).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item DISC_AMALTHEA = new MemeRecord("amalthea", SoundInit.AMALTHEA);
	public static final Item DISC_NYAN = new MemeRecord("nyan", SoundInit.NYAN);
	public static final Item DISC_USSR1 = new MemeRecord("ussr1", SoundInit.USSR1);
	public static final Item DISC_USSR2 = new MemeRecord("ussr2", SoundInit.USSR2);
	public static final Item DISC_BOOEY = new MemeRecord("baba_booey", SoundInit.BOOEY);
	public static final Item DISC_DOLAN = new MemeRecord("dolan", SoundInit.DOLAN);
	public static final Item DISC_MURICA = new MemeRecord("murica", SoundInit.MURICA);
	public static final Item DISC_PIGSTEP = new MemeRecord("pigstep", SoundInit.PIGSTEP);
	public static final Item DISC_KRAB_BORG = new MemeRecord("krab_borg", SoundInit.KRAB_BORG);
	public static final Item DISC_KRAB_BORG_FULL = new MemeRecord("krab_borg_full", SoundInit.KRAB_BORG_FULL);
	public static final Item DISC_FISH = new MemeRecord("fish_full", SoundInit.FISH_FULL);
	
	// First Aid Kit
	public static final Item FIRST_AID_KIT = new ItemFirstAidKit("first_aid", Main.IMMERSIBROOK_MAIN);
	
	// Safety Vest Armor Materials
	public static final ArmorMaterial SAFETY_VEST_MATERIAL = EnumHelper.addArmorMaterial("safety_vest", Reference.MODID + ":safety_vest", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial SAFETY_VEST_YELLOW_MAT = EnumHelper.addArmorMaterial("yellow_vest", Reference.MODID + ":yellow_vest", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial SAFETY_VEST_ORANGE_NON_X_MAT = EnumHelper.addArmorMaterial("orange_vest_no_x", Reference.MODID + ":orange_vest_no_x", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial SAFETY_VEST_BLUE_MAT = EnumHelper.addArmorMaterial("blue_vest", Reference.MODID + ":blue_vest", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial SAFETY_VEST_YELLOW_WITH_X_MAT = EnumHelper.addArmorMaterial("yellow_vest_x", Reference.MODID + ":yellow_vest_x", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial LVN_SAFETY_VEST_NO_X = EnumHelper.addArmorMaterial("lvn_vest", Reference.MODID + ":lvn_vest", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial LVN_SAFETY_VEST_X = EnumHelper.addArmorMaterial("lvn_vest_x", Reference.MODID + ":lvn_vest_x", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial LVN_SAFETY_VEST_TXT = EnumHelper.addArmorMaterial("lvn_vest_txt", Reference.MODID + ":lvn_vest_txt", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial IRW_SAFETY_VEST_MAT = EnumHelper.addArmorMaterial("irw_vest", Reference.MODID + ":irw_vest", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial TGES_VEST = EnumHelper.addArmorMaterial("tges_vest", Reference.MODID + ":tges_vest", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial RED_VEST = EnumHelper.addArmorMaterial("red_vest", Reference.MODID + ":red_vest", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial EMS_VEST = EnumHelper.addArmorMaterial("ems_vest", Reference.MODID + ":ems_vest", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.VEST, 2.0F);
	public static final ArmorMaterial NIGHT_VISION_HELMET = EnumHelper.addArmorMaterial("nv_goggles", Reference.MODID + ":nv_goggles", 20, new int[] {7, 8, 8, 9}, 0, SoundInit.NV, 2.0F);
	
	// Generic Safety Vests
	public static final Item SAFETY_VEST_ORANGE = new SafetyVestModel("safety_vest_orange", Main.IMMERSIBROOK_MAIN, SAFETY_VEST_MATERIAL, EntityEquipmentSlot.CHEST);
	public static final Item SAFETY_VEST_YELLOW = new SafetyVestModel("safety_vest_yellow", Main.IMMERSIBROOK_MAIN, SAFETY_VEST_YELLOW_MAT, EntityEquipmentSlot.CHEST);
	public static final Item SAFETY_VEST_ORANGE_NO_X = new SafetyVestModel("safety_vest_orange_no_x", Main.IMMERSIBROOK_MAIN, SAFETY_VEST_ORANGE_NON_X_MAT, EntityEquipmentSlot.CHEST);
	public static final Item SAFETY_VEST_BLUE = new SafetyVestModel("safety_vest_blue", Main.IMMERSIBROOK_MAIN, SAFETY_VEST_BLUE_MAT, EntityEquipmentSlot.CHEST);
	public static final Item SAFETY_VEST_YELLOW_X = new SafetyVestModel("safety_vest_yellow_x", Main.IMMERSIBROOK_MAIN, SAFETY_VEST_YELLOW_WITH_X_MAT, EntityEquipmentSlot.CHEST);
	public static final Item SAFETY_VEST_TGES = new SafetyVestModel("tges_vest", Main.IMMERSIBROOK_MAIN, TGES_VEST, EntityEquipmentSlot.CHEST);
	public static final Item SAFETY_VEST_RED = new SafetyVestModel("red_vest", Main.IMMERSIBROOK_MAIN, RED_VEST, EntityEquipmentSlot.CHEST);
	public static final Item SAFETY_VEST_EMS = new SafetyVestModel("ems_vest", Main.IMMERSIBROOK_MAIN, EMS_VEST, EntityEquipmentSlot.CHEST);
	
	// IRW Vest
	public static final Item IRW_VEST = new SafetyVestModel("irw_vest", Main.IMMERSIBROOK_MAIN, IRW_SAFETY_VEST_MAT, EntityEquipmentSlot.CHEST);
	
	// LVN Safety Vests
	public static final Item LVN_VEST = new SafetyVestModel("lvn_vest", Main.IMMERSIBROOK_MAIN, LVN_SAFETY_VEST_NO_X, EntityEquipmentSlot.CHEST);
	public static final Item LVN_VEST_X = new SafetyVestModel("lvn_vest_x", Main.IMMERSIBROOK_MAIN, LVN_SAFETY_VEST_X, EntityEquipmentSlot.CHEST);
	public static final Item LVN_VEST_TXT = new SafetyVestModel("lvn_vest_txt", Main.IMMERSIBROOK_MAIN, LVN_SAFETY_VEST_TXT, EntityEquipmentSlot.CHEST);
	
	// Night Vision Goggles
	public static final Item NV_GOGGLES = new NightVisionGoggles("nv_goggles", Main.IMMERSIBROOK_MAIN, NIGHT_VISION_HELMET, EntityEquipmentSlot.HEAD);

	// Night vision goggles crafting ingredients.
	public static final Item NV_LENS = new MiscItem("nv_lens", 16).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item NV_BODY = new MiscItem("nv_goggle_body", 16).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item NV_INTERNALS = new MiscItem("nv_goggle_circuits", 16).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Item NV_STRAPS = new MiscItem("nv_goggle_strap", 16).setCreativeTab(Main.IMMERSIBROOK_MAIN);
}
