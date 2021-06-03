package rz.mesabrook.wbtc.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.blocks.*;
import rz.mesabrook.wbtc.blocks.food.FoodBlock;
import rz.mesabrook.wbtc.blocks.stairs.MiscStairs;
import rz.mesabrook.wbtc.util.ModUtils;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	// Fake Light Block
	public static final Block FAKE_LIGHT_SOURCE = new BlockFakeLight("wbtc_fake_light");

	// Ceiling Lights
	public static final Block CEILING_LIGHT_SPOTLIGHT = new BlockCeilingLight("ceiling_light_spot");
	public static final Block CEILING_LIGHT_PANEL = new BlockCeilingLight("panel_light");

	// Ceiling Block
	public static final Block CEILING = new MiscBlock("panel_ceiling", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Ceiling Blocks
	public static final Block PANEL_OAK_L = new MiscBlock("panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_SPRUCE_L = new MiscBlock("panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_BIRCH_L = new MiscBlock("panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_FLOOR_JUNGLE_L = new MiscBlock("panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_ACACIA_L = new MiscBlock("panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DARK_OAK_L = new MiscBlock("panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_CHECKERBOARD = new MiscBlock("panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_CHECKERBOARD_OLD = new MiscBlock("panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_1 = new MiscBlock("panel_floor_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_2 = new MiscBlock("panel_floor_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_3 = new MiscBlock("panel_floor_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_4 = new MiscBlock("panel_floor_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_5 = new MiscBlock("panel_floor_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_6 = new MiscBlock("panel_floor_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_7 = new MiscBlock("panel_floor_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_8 = new MiscBlock("panel_floor_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_9 = new MiscBlock("panel_floor_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_10 = new MiscBlock("panel_floor_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_11 = new MiscBlock("panel_floor_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_12 = new MiscBlock("panel_floor_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_13 = new MiscBlock("panel_floor_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_14 = new MiscBlock("panel_floor_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_15 = new MiscBlock("panel_floor_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_16 = new MiscBlock("panel_floor_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_17 = new MiscBlock("panel_floor_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_18 = new MiscBlock("panel_floor_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_19 = new MiscBlock("panel_floor_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_20 = new MiscBlock("panel_floor_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_21 = new MiscBlock("panel_floor_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_22 = new MiscBlock("panel_floor_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_23 = new MiscBlock("panel_floor_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_24 = new MiscBlock("panel_floor_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_25 = new MiscBlock("panel_floor_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_26 = new MiscBlock("panel_floor_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_27 = new MiscBlock("panel_floor_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);


	public static final Block PANEL_DANGER_1 = new MiscBlock("panel_floor_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DANGER_2 = new MiscBlock("panel_floor_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DANGER_3 = new MiscBlock("panel_floor_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DANGER_4 = new MiscBlock("panel_floor_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_KITCHEN_1 = new MiscBlock("panel_floor_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_KITCHEN_2 = new MiscBlock("panel_floor_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DRAIN_1 = new MiscBlock("panel_floor_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DRAIN_2 = new MiscBlock("panel_floor_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_WHITE = new MiscBlock("panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_ORANGE = new MiscBlock("panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_MAGENTA = new MiscBlock("panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_LBLUE = new MiscBlock("panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_YELLOW = new MiscBlock("panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_LIME = new MiscBlock("panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_PINK = new MiscBlock("panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_GRAY = new MiscBlock("panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_SILVER = new MiscBlock("panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_CYAN = new MiscBlock("panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_PURPLE = new MiscBlock("panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_BLUE = new MiscBlock("panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_BROWN = new MiscBlock("panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_GREEN = new MiscBlock("panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_RED = new MiscBlock("panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_BLACK = new MiscBlock("panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Laminate Wood Floor Blocks
	public static final Block FLOOR_OAK = new MiscBlock("wbtc_oak", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_SPRUCE = new MiscBlock("wbtc_spruce", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_BIRCH = new MiscBlock("wbtc_birch", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_JUNGLE = new MiscBlock("wbtc_jungle", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_ACACIA = new MiscBlock("wbtc_acacia", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_DARK_OAK = new MiscBlock("wbtc_dark_oak", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Smooth Wood Blocks
	public static final Block SMOOTH_OAK = new MiscBlock("smooth_oak", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_SPRUCE = new MiscBlock("smooth_spruce", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_BIRCH = new MiscBlock("smooth_birch", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_JUNGLE = new MiscBlock("smooth_jungle", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_ACACIA = new MiscBlock("smooth_acacia", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_DARK_OAK = new MiscBlock("smooth_dark_oak", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);

	// Floor Blocks
	public static final Block FLOOR_CHECKERBOARD = new MiscBlock("wbtc_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_OLD_CHECKERBOARD = new MiscBlock("tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_1 = new MiscBlock("wbtc_floor_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_2 = new MiscBlock("wbtc_floor_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_3 = new MiscBlock("wbtc_floor_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_4 = new MiscBlock("wbtc_floor_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_5 = new MiscBlock("wbtc_floor_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_6 = new MiscBlock("wbtc_floor_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_7 = new MiscBlock("wbtc_floor_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_8 = new MiscBlock("wbtc_floor_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_9 = new MiscBlock("wbtc_floor_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_10 = new MiscBlock("wbtc_floor_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_11 = new MiscBlock("wbtc_floor_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_12 = new MiscBlock("wbtc_floor_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_13 = new MiscBlock("wbtc_floor_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_14 = new MiscBlock("wbtc_floor_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_15 = new MiscBlock("wbtc_floor_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_16 = new MiscBlock("wbtc_floor_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_17 = new MiscBlock("wbtc_floor_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_18 = new MiscBlock("wbtc_floor_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_19 = new MiscBlock("wbtc_floor_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_20 = new MiscBlock("wbtc_floor_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_21 = new MiscBlock("wbtc_floor_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_22 = new MiscBlock("wbtc_floor_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_23 = new MiscBlock("wbtc_floor_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_24 = new MiscBlock("wbtc_floor_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_25 = new MiscBlock("wbtc_floor_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_26 = new MiscBlock("wbtc_floor_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_27 = new MiscBlock("wbtc_floor_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);

	// Misc Blocks
	public static final Block WHITE_STONE = new MiscBlock("white_stone", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTHED_STONE = new MiscBlock("smoothed_stone", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block ASTRO_TURF = new MiscBlock("synthetic_turf", Material.PLANTS, SoundType.PLANT, Main.IMMERSIBROOK_MAIN, 0.7F);
	public static final Block WHITE_COBBLE = new MiscBlock("white_cobblestone", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block WHITE_STONE_BRICKS = new MiscBlock("white_stone_bricks", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CAT_BLOCK = new MiscBlock("cat_block", Material.ROCK, SoundType.METAL, Main.IMMERSIBROOK_MAIN, 0F);

	// Commercial Kitchen Floor Blocks
	public static final Block FLOOR_KITCHEN_1 = new MiscBlock("floor_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_DRAIN_1 = new MiscBlock("floor_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_KITCHEN_2 = new MiscBlock("floor_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_DRAIN_2 = new MiscBlock("floor_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Industrial Floor Blocks
	public static final Block INDUSTRIAL_TILE_1 = new MiscBlock("floor_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block INDUSTRIAL_TILE_2 = new MiscBlock("floor_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block INDUSTRIAL_TILE_3 = new MiscBlock("floor_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block INDUSTRIAL_TILE_4 = new MiscBlock("floor_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Quartz Pillar and Base. May not make it into next version.
	public static final Block PILLAR_BASE = new PillarBase("pillar_base");
	public static final Block PILLAR_TOP = new PillarBase("pillar_top");
	public static final Block PILLAR_POST = new Pillar("wbtc_pillar", 0);
	
	// Handrails
	public static final Block IRON_HANDRAIL = new BlockHandrail("iron_handrail", Material.IRON, SoundType.METAL, "pickaxe");
	public static final Block WOOD_HANDRAIL = new BlockHandrail("wood_handrail", Material.WOOD, SoundType.WOOD, "axe");

	// Chroma Screen Panels
	public static final Block CHROMA_GREEN = new ChromaScreen("chroma_green", Material.IRON, SoundType.METAL, "pickaxe");
	public static final Block CHROMA_BLUE = new ChromaScreen("chroma_blue", Material.IRON, SoundType.METAL, "pickaxe");
	
	// Food Cubes
	public static final Block CUBE_PORK = new FoodBlock("cube_pork", MapColor.PINK, SoundType.SLIME,Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_BEEF = new FoodBlock("cube_beef", MapColor.RED, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_CHICKEN = new FoodBlock("cube_chicken", MapColor.SNOW,SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_MUTTON = new FoodBlock("cube_mutton", MapColor.RED, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_RABBIT = new FoodBlock("cube_rabbit", MapColor.PINK, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_APPLES = new FoodBlock("cube_apples", MapColor.RED, SoundType.WOOD, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_CHEESE = new FoodBlock("cube_cheese", MapColor.YELLOW, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_CARROT = new FoodBlock("cube_carrot", MapColor.ORANGE_STAINED_HARDENED_CLAY, SoundType.WOOD, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_POTATO = new FoodBlock("cube_potato", MapColor.BROWN, SoundType.WOOD, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_PUMPKIN_PIE = new FoodBlock("cube_pumpkin_pie", MapColor.ORANGE_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_BEET = new FoodBlock("cube_beetroot", MapColor.RED, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_COOKIE = new FoodBlock("cube_cookie", MapColor.BROWN, SoundType.WOOD, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_GAPPLE = new FoodBlock("cube_gapple", MapColor.GOLD, SoundType.ANVIL, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_BREAD = new FoodBlock("cube_bread", MapColor.BROWN, SoundType.WOOD, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_FISH = new FoodBlock("cube_fish", MapColor.GRAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_SALMON = new FoodBlock("cube_salmon", MapColor.PINK, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_PUFF = new FoodBlock("cube_pufferfish", MapColor.YELLOW, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_CLOWN = new FoodBlock("cube_nemo", MapColor.ORANGE_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	
	// Plastic Blocks
	public static final Block PLASTIC_CUBE_WHITE = new MiscBlock("plastic_cube_white", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_ORANGE = new MiscBlock("plastic_cube_orange", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_MAGENTA = new MiscBlock("plastic_cube_magenta", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_LBLUE = new MiscBlock("plastic_cube_lblue", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_YELLOW = new MiscBlock("plastic_cube_yellow", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_LIME = new MiscBlock("plastic_cube_lime", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_PINK = new MiscBlock("plastic_cube_pink", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_GRAY = new MiscBlock("plastic_cube_gray", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_SILVER = new MiscBlock("plastic_cube_silver", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_CYAN = new MiscBlock("plastic_cube_cyan", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_PURPLE = new MiscBlock("plastic_cube_purple", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_BLUE = new MiscBlock("plastic_cube_blue", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_BROWN = new MiscBlock("plastic_cube_brown", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_GREEN = new MiscBlock("plastic_cube_green", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_RED = new MiscBlock("plastic_cube_red", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_BLACK = new MiscBlock("plastic_cube_black", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_GLOWING = new MiscBlock("plastic_cube_glowing", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 1F);
	
	// Aluminum
	public static final Block CUBE_ALUMINUM = new MiscBlock("aluminum_block", Material.ROCK, SoundType.METAL, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block ALUMINUM_ORE = new MiscBlock("wbtc_aluminum_ore", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Sign stand
	public static final Block SIGN_STAND = new SignStand("sign_stand", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, Main.IMMERSIBROOK_MAIN);
	
	// Signs
	public static final Block EXIT_SIGN = new SignBlock("exit_sign", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,5,0, 13,12,1), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block EXIT_SIGN_GREEN = new SignBlock("exit_sign_green", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,5,0, 13,12,1), 0.4F, Main.IMMERSIBROOK_MAIN);
	
	// Decor
	public static final Block PC_MOUSE = new DecorPCMouse("pc_mouse", Material.IRON, SoundType.METAL, 0.2F, 1.0F, ModUtils.getPixelatedAABB(7,0,6, 9,1,10), 0.4F, Main.IMMERSIBROOK_MAIN);
	
	// Trophies
	public static final Block STATUE_OWO = new BlockStatue("statue_owo", MapColor.BLUE).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_RZ = new BlockStatue("statue_rz", MapColor.GRAY).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_CSX = new BlockStatue("statue_csx", MapColor.GRAY).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_TD = new BlockStatue("statue_td", MapColor.GRAY).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_TLZ = new BlockStatue("statue_tlz", MapColor.GRAY).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_MD = new BlockStatue("statue_md", MapColor.GRAY).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_LW = new BlockStatue("statue_lw", MapColor.GRAY).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_SVV = new BlockStatue("statue_svv", MapColor.GRAY).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	
	// Plaques
	public static final Block PLAQUE_DEV = new BlockPlaque("plaque_dev", MapColor.GOLD, ModUtils.getPixelatedAABB(3,0,0, 13,12,1.5)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block PLAQUE_SUPPORTER = new BlockPlaque("plaque_supporter", MapColor.GOLD, ModUtils.getPixelatedAABB(3,0,0, 13,12,1.5)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	
	// Trash Can
	public static final Block TRASH_BIN = new BlockBin("rubbish_bin").setCreativeTab(Main.IMMERSIBROOK_MAIN);
	
	// Laminate Stairs
	public static final Block STAIRS_OAK_L = new MiscStairs("oak_laminate_stairs", ModBlocks.FLOOR_OAK.getDefaultState(), SoundType.STONE, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_BIRCH_L = new MiscStairs("birch_laminate_stairs", ModBlocks.FLOOR_BIRCH.getDefaultState(), SoundType.STONE, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_SPRUCE_L = new MiscStairs("spruce_laminate_stairs", ModBlocks.FLOOR_SPRUCE.getDefaultState(), SoundType.STONE, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_JUNGLE_L = new MiscStairs("jungle_laminate_stairs", ModBlocks.FLOOR_JUNGLE.getDefaultState(), SoundType.STONE, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_ACACIA_L = new MiscStairs("acacia_laminate_stairs", ModBlocks.FLOOR_ACACIA.getDefaultState(), SoundType.STONE, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_DARK_OAK_L = new MiscStairs("dark_oak_laminate_stairs", ModBlocks.FLOOR_DARK_OAK.getDefaultState(), SoundType.STONE, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// Smooth Wood Stairs
	public static final Block STAIRS_SMOOTH_OAK = new MiscStairs("smooth_oak_stairs", ModBlocks.SMOOTH_OAK.getDefaultState(), SoundType.WOOD, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_SMOOTH_BIRCH = new MiscStairs("smooth_birch_stairs", ModBlocks.SMOOTH_BIRCH.getDefaultState(), SoundType.WOOD, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_SMOOTH_SPRUCE = new MiscStairs("smooth_spruce_stairs", ModBlocks.SMOOTH_SPRUCE.getDefaultState(), SoundType.WOOD, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_SMOOTH_JUNGLE = new MiscStairs("smooth_jungle_stairs", ModBlocks.SMOOTH_JUNGLE.getDefaultState(), SoundType.WOOD, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_SMOOTH_ACACIA = new MiscStairs("smooth_acacia_stairs", ModBlocks.SMOOTH_ACACIA.getDefaultState(), SoundType.WOOD, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STAIRS_SMOOTH_DARK_OAK = new MiscStairs("smooth_dark_oak_stairs", ModBlocks.SMOOTH_OAK.getDefaultState(), SoundType.WOOD, "axe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// Chalked Stone Stairs
	public static final Block CHALKED_STONE_STAIRS = new MiscStairs("white_stone_stairs", ModBlocks.WHITE_STONE.getDefaultState(), SoundType.STONE, "pickaxe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block CHALKED_COBBLESTONE_STAIRS = new MiscStairs("white_cobblestone_stairs", ModBlocks.WHITE_COBBLE.getDefaultState(), SoundType.STONE, "pickaxe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block CHALKED_STONE_BRICK_STAIRS = new MiscStairs("white_stone_brick_stairs", ModBlocks.WHITE_STONE_BRICKS.getDefaultState(), SoundType.STONE, "pickaxe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// Smooth Oak Wood Ceiling Blocks
	public static final Block SO_PANEL_OAK_L = new MiscBlock("so_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_SPRUCE_L = new MiscBlock("so_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_BIRCH_L = new MiscBlock("so_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_FLOOR_JUNGLE_L = new MiscBlock("so_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_ACACIA_L = new MiscBlock("so_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DARK_OAK_L = new MiscBlock("so_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_CHECKERBOARD = new MiscBlock("so_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_CHECKERBOARD_OLD = new MiscBlock("so_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_1 = new MiscBlock("so_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_2 = new MiscBlock("so_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_3 = new MiscBlock("so_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_4 = new MiscBlock("so_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_5 = new MiscBlock("so_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_6 = new MiscBlock("so_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_7 = new MiscBlock("so_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_8 = new MiscBlock("so_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_9 = new MiscBlock("so_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_10 = new MiscBlock("so_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_11 = new MiscBlock("so_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_12 = new MiscBlock("so_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_13 = new MiscBlock("so_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_14 = new MiscBlock("so_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_15 = new MiscBlock("so_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_16 = new MiscBlock("so_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_17 = new MiscBlock("so_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_18 = new MiscBlock("so_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_19 = new MiscBlock("so_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_20 = new MiscBlock("so_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_21 = new MiscBlock("so_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_22 = new MiscBlock("so_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_23 = new MiscBlock("so_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_24 = new MiscBlock("so_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_25 = new MiscBlock("so_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_26 = new MiscBlock("so_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_27 = new MiscBlock("so_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DANGER_1 = new MiscBlock("so_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DANGER_2 = new MiscBlock("so_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DANGER_3 = new MiscBlock("so_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DANGER_4 = new MiscBlock("so_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_KITCHEN_1 = new MiscBlock("so_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_KITCHEN_2 = new MiscBlock("so_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DRAIN_1 = new MiscBlock("so_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DRAIN_2 = new MiscBlock("so_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_WHITE = new MiscBlock("so_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_ORANGE = new MiscBlock("so_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_MAGENTA = new MiscBlock("so_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_LBLUE = new MiscBlock("so_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_YELLOW = new MiscBlock("so_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_LIME = new MiscBlock("so_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_PINK = new MiscBlock("so_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_GRAY = new MiscBlock("so_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_SILVER = new MiscBlock("so_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_CYAN = new MiscBlock("so_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_PURPLE = new MiscBlock("so_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_BLUE = new MiscBlock("so_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_BROWN = new MiscBlock("so_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_GREEN = new MiscBlock("so_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_RED = new MiscBlock("so_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_BLACK = new MiscBlock("so_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Birch Wood Ceiling Blocks
	public static final Block SB_PANEL_OAK_L = new MiscBlock("sb_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_SPRUCE_L = new MiscBlock("sb_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_BIRCH_L = new MiscBlock("sb_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_FLOOR_JUNGLE_L = new MiscBlock("sb_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_ACACIA_L = new MiscBlock("sb_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DARK_OAK_L = new MiscBlock("sb_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_CHECKERBOARD = new MiscBlock("sb_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_CHECKERBOARD_OLD = new MiscBlock("sb_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_1 = new MiscBlock("sb_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_2 = new MiscBlock("sb_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_3 = new MiscBlock("sb_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_4 = new MiscBlock("sb_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_5 = new MiscBlock("sb_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_6 = new MiscBlock("sb_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_7 = new MiscBlock("sb_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_8 = new MiscBlock("sb_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_9 = new MiscBlock("sb_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_10 = new MiscBlock("sb_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_11 = new MiscBlock("sb_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_12 = new MiscBlock("sb_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_13 = new MiscBlock("sb_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_14 = new MiscBlock("sb_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_15 = new MiscBlock("sb_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_16 = new MiscBlock("sb_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_17 = new MiscBlock("sb_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_18 = new MiscBlock("sb_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_19 = new MiscBlock("sb_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_20 = new MiscBlock("sb_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_21 = new MiscBlock("sb_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_22 = new MiscBlock("sb_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_23 = new MiscBlock("sb_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_24 = new MiscBlock("sb_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_25 = new MiscBlock("sb_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_26 = new MiscBlock("sb_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_27 = new MiscBlock("sb_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DANGER_1 = new MiscBlock("sb_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DANGER_2 = new MiscBlock("sb_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DANGER_3 = new MiscBlock("sb_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DANGER_4 = new MiscBlock("sb_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_KITCHEN_1 = new MiscBlock("sb_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_KITCHEN_2 = new MiscBlock("sb_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DRAIN_1 = new MiscBlock("sb_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DRAIN_2 = new MiscBlock("sb_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_WHITE = new MiscBlock("sb_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_ORANGE = new MiscBlock("sb_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_MAGENTA = new MiscBlock("sb_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_LBLUE = new MiscBlock("sb_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_YELLOW = new MiscBlock("sb_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_LIME = new MiscBlock("sb_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_PINK = new MiscBlock("sb_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_GRAY = new MiscBlock("sb_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_SILVER = new MiscBlock("sb_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_CYAN = new MiscBlock("sb_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_PURPLE = new MiscBlock("sb_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_BLUE = new MiscBlock("sb_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_BROWN = new MiscBlock("sb_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_GREEN = new MiscBlock("sb_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_RED = new MiscBlock("sb_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_BLACK = new MiscBlock("sb_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Spruce Wood Ceiling Blocks
	public static final Block SS_PANEL_OAK_L = new MiscBlock("ss_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_SPRUCE_L = new MiscBlock("ss_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_BIRCH_L = new MiscBlock("ss_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_FLOOR_JUNGLE_L = new MiscBlock("ss_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_ACACIA_L = new MiscBlock("ss_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DARK_OAK_L = new MiscBlock("ss_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_CHECKERBOARD = new MiscBlock("ss_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_CHECKERBOARD_OLD = new MiscBlock("ss_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_1 = new MiscBlock("ss_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_2 = new MiscBlock("ss_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_3 = new MiscBlock("ss_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_4 = new MiscBlock("ss_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_5 = new MiscBlock("ss_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_6 = new MiscBlock("ss_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_7 = new MiscBlock("ss_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_8 = new MiscBlock("ss_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_9 = new MiscBlock("ss_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_10 = new MiscBlock("ss_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_11 = new MiscBlock("ss_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_12 = new MiscBlock("ss_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_13 = new MiscBlock("ss_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_14 = new MiscBlock("ss_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_15 = new MiscBlock("ss_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_16 = new MiscBlock("ss_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_17 = new MiscBlock("ss_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_18 = new MiscBlock("ss_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_19 = new MiscBlock("ss_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_20 = new MiscBlock("ss_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_21 = new MiscBlock("ss_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_22 = new MiscBlock("ss_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_23 = new MiscBlock("ss_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_24 = new MiscBlock("ss_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_25 = new MiscBlock("ss_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_26 = new MiscBlock("ss_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_27 = new MiscBlock("ss_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DANGER_1 = new MiscBlock("ss_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DANGER_2 = new MiscBlock("ss_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DANGER_3 = new MiscBlock("ss_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DANGER_4 = new MiscBlock("ss_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_KITCHEN_1 = new MiscBlock("ss_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_KITCHEN_2 = new MiscBlock("ss_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DRAIN_1 = new MiscBlock("ss_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DRAIN_2 = new MiscBlock("ss_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_WHITE = new MiscBlock("ss_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_ORANGE = new MiscBlock("ss_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_MAGENTA = new MiscBlock("ss_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_LBLUE = new MiscBlock("ss_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_YELLOW = new MiscBlock("ss_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_LIME = new MiscBlock("ss_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_PINK = new MiscBlock("ss_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_GRAY = new MiscBlock("ss_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_SILVER = new MiscBlock("ss_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_CYAN = new MiscBlock("ss_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_PURPLE = new MiscBlock("ss_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_BLUE = new MiscBlock("ss_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_BROWN = new MiscBlock("ss_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_GREEN = new MiscBlock("ss_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_RED = new MiscBlock("ss_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_BLACK = new MiscBlock("ss_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Jungle Wood Ceiling Blocks
	public static final Block SJ_PANEL_OAK_L = new MiscBlock("sj_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_SPRUCE_L = new MiscBlock("sj_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_BIRCH_L = new MiscBlock("sj_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_FLOOR_JUNGLE_L = new MiscBlock("sj_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_ACACIA_L = new MiscBlock("sj_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DARK_OAK_L = new MiscBlock("sj_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_CHECKERBOARD = new MiscBlock("sj_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_CHECKERBOARD_OLD = new MiscBlock("sj_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_1 = new MiscBlock("sj_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_2 = new MiscBlock("sj_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_3 = new MiscBlock("sj_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_4 = new MiscBlock("sj_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_5 = new MiscBlock("sj_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_6 = new MiscBlock("sj_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_7 = new MiscBlock("sj_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_8 = new MiscBlock("sj_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_9 = new MiscBlock("sj_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_10 = new MiscBlock("sj_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_11 = new MiscBlock("sj_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_12 = new MiscBlock("sj_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_13 = new MiscBlock("sj_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_14 = new MiscBlock("sj_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_15 = new MiscBlock("sj_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_16 = new MiscBlock("sj_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_17 = new MiscBlock("sj_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_18 = new MiscBlock("sj_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_19 = new MiscBlock("sj_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_20 = new MiscBlock("sj_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_21 = new MiscBlock("sj_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_22 = new MiscBlock("sj_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_23 = new MiscBlock("sj_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_24 = new MiscBlock("sj_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_25 = new MiscBlock("sj_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_26 = new MiscBlock("sj_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_27 = new MiscBlock("sj_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DANGER_1 = new MiscBlock("sj_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DANGER_2 = new MiscBlock("sj_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DANGER_3 = new MiscBlock("sj_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DANGER_4 = new MiscBlock("sj_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_KITCHEN_1 = new MiscBlock("sj_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_KITCHEN_2 = new MiscBlock("sj_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DRAIN_1 = new MiscBlock("sj_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DRAIN_2 = new MiscBlock("sj_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_WHITE = new MiscBlock("sj_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_ORANGE = new MiscBlock("sj_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_MAGENTA = new MiscBlock("sj_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_LBLUE = new MiscBlock("sj_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_YELLOW = new MiscBlock("sj_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_LIME = new MiscBlock("sj_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_PINK = new MiscBlock("sj_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_GRAY = new MiscBlock("sj_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_SILVER = new MiscBlock("sj_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_CYAN = new MiscBlock("sj_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_PURPLE = new MiscBlock("sj_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_BLUE = new MiscBlock("sj_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_BROWN = new MiscBlock("sj_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_GREEN = new MiscBlock("sj_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_RED = new MiscBlock("sj_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_BLACK = new MiscBlock("sj_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Acacia Wood Ceiling Blocks
	public static final Block SA_PANEL_OAK_L = new MiscBlock("sa_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_SPRUCE_L = new MiscBlock("sa_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_BIRCH_L = new MiscBlock("sa_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_FLOOR_JUNGLE_L = new MiscBlock("sa_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_ACACIA_L = new MiscBlock("sa_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DARK_OAK_L = new MiscBlock("sa_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_CHECKERBOARD = new MiscBlock("sa_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_CHECKERBOARD_OLD = new MiscBlock("sa_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_1 = new MiscBlock("sa_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_2 = new MiscBlock("sa_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_3 = new MiscBlock("sa_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_4 = new MiscBlock("sa_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_5 = new MiscBlock("sa_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_6 = new MiscBlock("sa_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_7 = new MiscBlock("sa_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_8 = new MiscBlock("sa_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_9 = new MiscBlock("sa_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_10 = new MiscBlock("sa_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_11 = new MiscBlock("sa_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_12 = new MiscBlock("sa_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_13 = new MiscBlock("sa_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_14 = new MiscBlock("sa_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_15 = new MiscBlock("sa_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_16 = new MiscBlock("sa_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_17 = new MiscBlock("sa_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_18 = new MiscBlock("sa_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_19 = new MiscBlock("sa_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_20 = new MiscBlock("sa_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_21 = new MiscBlock("sa_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_22 = new MiscBlock("sa_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_23 = new MiscBlock("sa_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_24 = new MiscBlock("sa_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_25 = new MiscBlock("sa_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_26 = new MiscBlock("sa_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_27 = new MiscBlock("sa_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DANGER_1 = new MiscBlock("sa_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DANGER_2 = new MiscBlock("sa_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DANGER_3 = new MiscBlock("sa_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DANGER_4 = new MiscBlock("sa_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_KITCHEN_1 = new MiscBlock("sa_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_KITCHEN_2 = new MiscBlock("sa_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DRAIN_1 = new MiscBlock("sa_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DRAIN_2 = new MiscBlock("sa_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_WHITE = new MiscBlock("sa_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_ORANGE = new MiscBlock("sa_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_MAGENTA = new MiscBlock("sa_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_LBLUE = new MiscBlock("sa_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_YELLOW = new MiscBlock("sa_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_LIME = new MiscBlock("sa_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_PINK = new MiscBlock("sa_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_GRAY = new MiscBlock("sa_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_SILVER = new MiscBlock("sa_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_CYAN = new MiscBlock("sa_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_PURPLE = new MiscBlock("sa_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_BLUE = new MiscBlock("sa_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_BROWN = new MiscBlock("sa_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_GREEN = new MiscBlock("sa_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_RED = new MiscBlock("sa_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_BLACK = new MiscBlock("sa_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Dark Oak Wood Ceiling Blocks
}