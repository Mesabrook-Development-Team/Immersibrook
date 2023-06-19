package com.mesabrook.ib.init;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.*;
import com.mesabrook.ib.blocks.food.FoodBlock;
import com.mesabrook.ib.blocks.metro.TicketMachine;
import com.mesabrook.ib.blocks.stairs.MiscStairs;
import com.mesabrook.ib.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	// Fake Light Block
	public static final Block FAKE_LIGHT_SOURCE = new BlockFakeLight("wbtc_fake_light");

	// Ceiling Lights
	public static final Block CEILING_LIGHT_SPOTLIGHT = new BlockCeilingLight("ceiling_light_spot");
	public static final Block CEILING_LIGHT_PANEL = new BlockCeilingLight("panel_light");
	public static final Block CEILING_LIGHT_LARGE = new BlockCeilingLight("warehouse_light");
	public static final Block CEILING_LIGHT_SLIM = new BlockCeilingLight("ceiling_light_slim");

	// Ceiling Block
	public static final Block CEILING = new Immersiblock("panel_ceiling", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Ceiling Blocks
	public static final Block PANEL_OAK_L = new Immersiblock("panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_SPRUCE_L = new Immersiblock("panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_BIRCH_L = new Immersiblock("panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_FLOOR_JUNGLE_L = new Immersiblock("panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_ACACIA_L = new Immersiblock("panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DARK_OAK_L = new Immersiblock("panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_CHECKERBOARD = new Immersiblock("panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_CHECKERBOARD_OLD = new Immersiblock("panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_1 = new Immersiblock("panel_floor_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_2 = new Immersiblock("panel_floor_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_3 = new Immersiblock("panel_floor_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_4 = new Immersiblock("panel_floor_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_5 = new Immersiblock("panel_floor_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_6 = new Immersiblock("panel_floor_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_7 = new Immersiblock("panel_floor_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_8 = new Immersiblock("panel_floor_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_9 = new Immersiblock("panel_floor_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_10 = new Immersiblock("panel_floor_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_11 = new Immersiblock("panel_floor_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_12 = new Immersiblock("panel_floor_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_13 = new Immersiblock("panel_floor_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_14 = new Immersiblock("panel_floor_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_15 = new Immersiblock("panel_floor_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_16 = new Immersiblock("panel_floor_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_17 = new Immersiblock("panel_floor_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_18 = new Immersiblock("panel_floor_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_19 = new Immersiblock("panel_floor_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_20 = new Immersiblock("panel_floor_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_21 = new Immersiblock("panel_floor_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_22 = new Immersiblock("panel_floor_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_23 = new Immersiblock("panel_floor_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_24 = new Immersiblock("panel_floor_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_25 = new Immersiblock("panel_floor_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_26 = new Immersiblock("panel_floor_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_27 = new Immersiblock("panel_floor_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_28 = new Immersiblock("panel_floor_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_29 = new Immersiblock("panel_floor_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_30 = new Immersiblock("panel_floor_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_31 = new Immersiblock("panel_floor_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_32 = new Immersiblock("panel_floor_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_33 = new Immersiblock("panel_floor_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_34 = new Immersiblock("panel_floor_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_35 = new Immersiblock("panel_floor_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_36 = new Immersiblock("panel_floor_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_TILE_37 = new Immersiblock("panel_floor_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DANGER_1 = new Immersiblock("panel_floor_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DANGER_2 = new Immersiblock("panel_floor_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DANGER_3 = new Immersiblock("panel_floor_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DANGER_4 = new Immersiblock("panel_floor_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_KITCHEN_1 = new Immersiblock("panel_floor_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_KITCHEN_2 = new Immersiblock("panel_floor_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_KITCHEN_3 = new Immersiblock("panel_floor_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DRAIN_1 = new Immersiblock("panel_floor_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DRAIN_2 = new Immersiblock("panel_floor_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_DRAIN_3 = new Immersiblock("panel_floor_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_WHITE = new Immersiblock("panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_ORANGE = new Immersiblock("panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_MAGENTA = new Immersiblock("panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_LBLUE = new Immersiblock("panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_YELLOW = new Immersiblock("panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_LIME = new Immersiblock("panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_PINK = new Immersiblock("panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_GRAY = new Immersiblock("panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_SILVER = new Immersiblock("panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_CYAN = new Immersiblock("panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_PURPLE = new Immersiblock("panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_BLUE = new Immersiblock("panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_BROWN = new Immersiblock("panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_GREEN = new Immersiblock("panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_RED = new Immersiblock("panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PANEL_WOOL_BLACK = new Immersiblock("panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Laminate Wood Floor Blocks
	public static final Block FLOOR_OAK = new Immersiblock("wbtc_oak", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_SPRUCE = new Immersiblock("wbtc_spruce", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_BIRCH = new Immersiblock("wbtc_birch", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_JUNGLE = new Immersiblock("wbtc_jungle", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_ACACIA = new Immersiblock("wbtc_acacia", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_DARK_OAK = new Immersiblock("wbtc_dark_oak", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	
	// Smooth Wood Blocks
	public static final Block SMOOTH_OAK = new Immersiblock("smooth_oak", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_SPRUCE = new Immersiblock("smooth_spruce", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_BIRCH = new Immersiblock("smooth_birch", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_JUNGLE = new Immersiblock("smooth_jungle", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_ACACIA = new Immersiblock("smooth_acacia", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTH_DARK_OAK = new Immersiblock("smooth_dark_oak", Material.WOOD, SoundType.WOOD, Main.IMMERSIBROOK_MAIN, 0F);

	// Floor Blocks
	public static final Block FLOOR_CHECKERBOARD = new Immersiblock("wbtc_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_OLD_CHECKERBOARD = new Immersiblock("tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_1 = new Immersiblock("wbtc_floor_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_2 = new Immersiblock("wbtc_floor_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_3 = new Immersiblock("wbtc_floor_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_4 = new Immersiblock("wbtc_floor_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_5 = new Immersiblock("wbtc_floor_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_6 = new Immersiblock("wbtc_floor_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_7 = new Immersiblock("wbtc_floor_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_8 = new Immersiblock("wbtc_floor_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_9 = new Immersiblock("wbtc_floor_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_10 = new Immersiblock("wbtc_floor_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_11 = new Immersiblock("wbtc_floor_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_12 = new Immersiblock("wbtc_floor_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_13 = new Immersiblock("wbtc_floor_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_14 = new Immersiblock("wbtc_floor_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_15 = new Immersiblock("wbtc_floor_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_16 = new Immersiblock("wbtc_floor_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_17 = new Immersiblock("wbtc_floor_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_18 = new Immersiblock("wbtc_floor_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_19 = new Immersiblock("wbtc_floor_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_20 = new Immersiblock("wbtc_floor_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_21 = new Immersiblock("wbtc_floor_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_22 = new Immersiblock("wbtc_floor_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_23 = new Immersiblock("wbtc_floor_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_24 = new Immersiblock("wbtc_floor_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_25 = new Immersiblock("wbtc_floor_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_26 = new Immersiblock("wbtc_floor_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_27 = new Immersiblock("wbtc_floor_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_28 = new Immersiblock("wbtc_floor_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_29 = new Immersiblock("wbtc_floor_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_30 = new Immersiblock("wbtc_floor_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_31 = new Immersiblock("wbtc_floor_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_32 = new Immersiblock("wbtc_floor_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_33 = new Immersiblock("wbtc_floor_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_34 = new Immersiblock("wbtc_floor_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_35 = new Immersiblock("wbtc_floor_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_36 = new Immersiblock("wbtc_floor_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_TILE_37 = new Immersiblock("wbtc_floor_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);

	// Misc Blocks
	public static final Block WHITE_STONE = new Immersiblock("white_stone", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SMOOTHED_STONE = new Immersiblock("smoothed_stone", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block ASTRO_TURF = new Immersiblock("synthetic_turf", Material.PLANTS, SoundType.PLANT, Main.IMMERSIBROOK_MAIN, 0.7F);
	public static final Block WHITE_COBBLE = new Immersiblock("white_cobblestone", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block WHITE_STONE_BRICKS = new Immersiblock("white_stone_bricks", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);

	// Dynamic Pillars - Credit to CSX8600
	public static final Block PILLAR_POST = new Pillar("wbtc_pillar", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block PILLAR_STONE = new Pillar("wbtc_pillar_stone", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block PILLAR_OAK = new Pillar("wbtc_pillar_oak_log", Material.WOOD, SoundType.WOOD, "axe", 0);
	public static final Block PILLAR_BIRCH = new Pillar("wbtc_pillar_birch", Material.WOOD, SoundType.WOOD, "axe", 0);
	public static final Block PILLAR_SPRUCE = new Pillar("wbtc_pillar_spruce", Material.WOOD, SoundType.WOOD, "axe", 0);
	public static final Block PILLAR_JUNGLE = new Pillar("wbtc_pillar_jungle", Material.WOOD, SoundType.WOOD, "axe", 0);
	public static final Block PILLAR_ACACIA = new Pillar("wbtc_pillar_acacia", Material.WOOD, SoundType.WOOD, "axe", 0);
	public static final Block PILLAR_DARK_OAK = new Pillar("wbtc_pillar_dark_oak", Material.WOOD, SoundType.WOOD, "axe", 0);
	public static final Block PILLAR_COBBLESTONE = new Pillar("wbtc_pillar_cobble", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block PILLAR_STONEBRICKS = new Pillar("wbtc_pillar_stonebricks", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block PILLAR_WHITE_STONEBRICKS = new Pillar("wbtc_pillar_white_stonebricks", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block PILLAR_WHITE_STONE = new Pillar("wbtc_pillar_white_stone", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block PILLAR_WHITE_COBBLESTONE = new Pillar("wbtc_pillar_white_cobble", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block PILLAR_SMOOTHED_STONE = new Pillar("wbtc_pillar_smoothed_stone", Material.ROCK, SoundType.STONE, "pickaxe", 1);
	public static final Block CELL_TOWER = new Pillar("cell_tower", Material.IRON, SoundType.METAL, "pickaxe", 1);
	public static final Block CELL_TOWER_RED = new Pillar("cell_tower_red", Material.IRON, SoundType.METAL, "pickaxe", 1);

	// Commercial Kitchen Floor Blocks
	public static final Block FLOOR_KITCHEN_1 = new Immersiblock("floor_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_DRAIN_1 = new Immersiblock("floor_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_KITCHEN_2 = new Immersiblock("floor_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_DRAIN_2 = new Immersiblock("floor_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_KITCHEN_3 = new Immersiblock("floor_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block FLOOR_DRAIN_3 = new Immersiblock("floor_drain_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);

	// Industrial Floor Blocks
	public static final Block INDUSTRIAL_TILE_1 = new Immersiblock("floor_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block INDUSTRIAL_TILE_2 = new Immersiblock("floor_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block INDUSTRIAL_TILE_3 = new Immersiblock("floor_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block INDUSTRIAL_TILE_4 = new Immersiblock("floor_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);

	// Handrails
	public static final Block IRON_HANDRAIL = new BlockHandrail("iron_handrail", Material.IRON, SoundType.METAL, "pickaxe");
	public static final Block WOOD_HANDRAIL = new BlockHandrail("wood_handrail", Material.WOOD, SoundType.WOOD, "axe");

	// Chroma
	public static final Block CHROMA_GREEN = new ChromaScreen("chroma_green", Material.IRON, SoundType.METAL, "pickaxe", ModUtils.getPixelatedAABB(0,0,0, 16.1,16.1,1.8));
	public static final Block CHROMA_BLUE = new ChromaScreen("chroma_blue", Material.IRON, SoundType.METAL, "pickaxe", ModUtils.getPixelatedAABB(3,0,0, 13,12,1.5));
	public static final Block CHROMA_BLOCK_BLUE = new Immersiblock("chroma_blue_block", Material.IRON, SoundType.METAL, Main.IMMERSIBROOK_MAIN, 0.7F);
	public static final Block CHROMA_BLOCK_GREEN = new Immersiblock("chroma_green_block", Material.IRON, SoundType.METAL, Main.IMMERSIBROOK_MAIN, 0.7F);

	// Food Cubes
	public static final Block CUBE_PORK = new FoodBlock("cube_pork", MapColor.PINK, SoundType.SLIME,Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_BEEF = new FoodBlock("cube_beef", MapColor.RED, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_CHICKEN = new FoodBlock("cube_chicken", MapColor.SNOW,SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_MUTTON = new FoodBlock("cube_mutton", MapColor.RED, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_RABBIT = new FoodBlock("cube_rabbit", MapColor.PINK, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_APPLES = new FoodBlock("cube_apples", MapColor.RED, SoundType.WOOD, Main.IMMERSIBROOK_MAIN);
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
	public static final Block CUBE_CHEESE = new FoodBlock("cube_cheese", MapColor.YELLOW, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_APPLE_PIE = new FoodBlock("cube_apple_pie", MapColor.ORANGE_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_STRAWBERRY_PIE = new FoodBlock("cube_strawberry_pie", MapColor.RED_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_BLUEBERRY_PIE = new FoodBlock("cube_blueberry_pie", MapColor.BLUE_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_CHERRY_PIE = new FoodBlock("cube_cherry_pie", MapColor.RED, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_SWEETPOTATO_PIE = new FoodBlock("cube_sweetpotato_pie", MapColor.ORANGE_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_KEYLIME_PIE = new FoodBlock("cube_keylime_pie", MapColor.LIME, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_RASPBERRY_PIE = new FoodBlock("cube_raspberry_pie", MapColor.PINK, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_PECAN_PIE = new FoodBlock("cube_pecan_pie", MapColor.BROWN, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_GOOSEBERRY_PIE = new FoodBlock("cube_gooseberry_pie", MapColor.BROWN, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_SPIDER_PIE = new FoodBlock("cube_spider_pie", MapColor.RED, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_RHUBARB_PIE = new FoodBlock("cube_rhubarb_pie", MapColor.RED_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_PATREON_PIE = new FoodBlock("cube_patreon_pie", MapColor.GOLD, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_SLIME_PIE = new FoodBlock("cube_slime_pie", MapColor.GREEN, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_MEAT_PIE = new FoodBlock("cube_meat_pie", MapColor.RED, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_SPINACH_PIE = new FoodBlock("cube_spinach_pie", MapColor.GREEN, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_MINCE_PIE = new FoodBlock("cube_mince_pie", MapColor.BROWN, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_COTTAGE_PIE = new FoodBlock("cube_cottage_pie", MapColor.WHITE_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_SHEPHERD_PIE = new FoodBlock("cube_shepherd_pie", MapColor.WHITE_STAINED_HARDENED_CLAY, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);
	public static final Block CUBE_POT_PIE = new FoodBlock("cube_pot_pie", MapColor.BROWN, SoundType.SLIME, Main.IMMERSIBROOK_MAIN);

	// Plastic Blocks
	public static final Block PLASTIC_CUBE_WHITE = new Immersiblock("plastic_cube_white", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_ORANGE = new Immersiblock("plastic_cube_orange", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_MAGENTA = new Immersiblock("plastic_cube_magenta", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_LBLUE = new Immersiblock("plastic_cube_lblue", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_YELLOW = new Immersiblock("plastic_cube_yellow", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_LIME = new Immersiblock("plastic_cube_lime", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_PINK = new Immersiblock("plastic_cube_pink", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_GRAY = new Immersiblock("plastic_cube_gray", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_SILVER = new Immersiblock("plastic_cube_silver", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_CYAN = new Immersiblock("plastic_cube_cyan", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_PURPLE = new Immersiblock("plastic_cube_purple", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_BLUE = new Immersiblock("plastic_cube_blue", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_BROWN = new Immersiblock("plastic_cube_brown", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_GREEN = new Immersiblock("plastic_cube_green", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_RED = new Immersiblock("plastic_cube_red", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_BLACK = new Immersiblock("plastic_cube_black", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block PLASTIC_CUBE_GLOWING = new Immersiblock("plastic_cube_glowing", Material.ROCK, SoundTypeInit.PLASTIC, Main.IMMERSIBROOK_MAIN, 1F);

	// Raw Plastic Blocks
	public static final Block RAW_PLASTIC_CUBE_WHITE = new BlockRawPlastic("raw_plastic_block_white", 0F);
	public static final Block RAW_PLASTIC_CUBE_ORANGE = new BlockRawPlastic("raw_plastic_block_orange", 0F);
	public static final Block RAW_PLASTIC_CUBE_MAGENTA = new BlockRawPlastic("raw_plastic_block_magenta", 0F);
	public static final Block RAW_PLASTIC_CUBE_LBLUE = new BlockRawPlastic("raw_plastic_block_lblue", 0F);
	public static final Block RAW_PLASTIC_CUBE_YELLOW = new BlockRawPlastic("raw_plastic_block_yellow", 0F);
	public static final Block RAW_PLASTIC_CUBE_LIME = new BlockRawPlastic("raw_plastic_block_lime", 0F);
	public static final Block RAW_PLASTIC_CUBE_PINK = new BlockRawPlastic("raw_plastic_block_pink", 0F);
	public static final Block RAW_PLASTIC_CUBE_GRAY = new BlockRawPlastic("raw_plastic_block_gray", 0F);
	public static final Block RAW_PLASTIC_CUBE_SILVER = new BlockRawPlastic("raw_plastic_block_silver", 0F);
	public static final Block RAW_PLASTIC_CUBE_CYAN = new BlockRawPlastic("raw_plastic_block_cyan", 0F);
	public static final Block RAW_PLASTIC_CUBE_PURPLE = new BlockRawPlastic("raw_plastic_block_purple", 0F);
	public static final Block RAW_PLASTIC_CUBE_BLUE = new BlockRawPlastic("raw_plastic_block_blue", 0F);
	public static final Block RAW_PLASTIC_CUBE_BROWN = new BlockRawPlastic("raw_plastic_block_brown", 0F);
	public static final Block RAW_PLASTIC_CUBE_GREEN = new BlockRawPlastic("raw_plastic_block_green", 0F);
	public static final Block RAW_PLASTIC_CUBE_RED = new BlockRawPlastic("raw_plastic_block_red", 0F);
	public static final Block RAW_PLASTIC_CUBE_BLACK = new BlockRawPlastic("raw_plastic_block_black", 0F);
	public static final Block RAW_PLASTIC_CUBE_GLOWING = new BlockRawPlastic("raw_plastic_block_glowing", 0.5F);
	
	// Sign stand
	public static final Block SIGN_STAND = new SignStand("sign_stand", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, Main.IMMERSIBROOK_MAIN);
	
	// Signs
	public static final Block EXIT_SIGN = new SignBlock("exit_sign", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,5,0, 13,12,1), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block EXIT_SIGN_GREEN = new SignBlock("exit_sign_green", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,5,0, 13,12,1), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block DIAG_SIGN_RED = new SignBlock("diagonal_exit_red", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(1,7,0, 15,13.5,1.2), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block DIAG_SIGN_GREEN = new SignBlock("diagonal_exit_green", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(1,7,0, 15,13.5,1.2), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block EXIT_SIGN_LEFT = new SignBlock("exit_sign_red_left", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,5,0, 13,12,1), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block EXIT_SIGN_RIGHT = new SignBlock("exit_sign_red_right", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,5,0, 13,12,1), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block EXIT_SIGN_GREEN_LEFT = new SignBlock("exit_sign_green_left", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,5,0, 13,12,1), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block EXIT_SIGN_GREEN_RIGHT = new SignBlock("exit_sign_green_right", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,5,0, 13,12,1), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block CEILING_EXIT_SIGN_GREEN = new SignBlock("ceiling_exit_green", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,9,7, 13,16,9), 0.4F, Main.IMMERSIBROOK_MAIN);
	public static final Block CEILING_EXIT_SIGN_RED = new SignBlock("ceiling_exit_red", Material.IRON, SoundType.METAL, 1.0F, 1.0F, "pickaxe", 0, ModUtils.getPixelatedAABB(3,9,7, 13,16,9), 0.4F, Main.IMMERSIBROOK_MAIN);

	// Decor
	public static final Block PC_MOUSE = new DecorPCMouse("pc_mouse", Material.IRON, SoundType.METAL, 0.2F, 1.0F, ModUtils.getPixelatedAABB(7,0,6, 9,1,10), 0.4F, Main.IMMERSIBROOK_MAIN);

	// Trophies
	public static final Block STATUE_OWO = new BlockStatue("statue_owo", MapColor.BLUE, ModUtils.getPixelatedAABB(3,0,5, 13,25,11)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_RZ = new BlockStatue("statue_rz", MapColor.GRAY, ModUtils.getPixelatedAABB(3,0,5, 13,30,11)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_CSX = new BlockStatue("statue_csx", MapColor.GRAY, ModUtils.getPixelatedAABB(3,0,5, 13,30,11)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_TLZ = new BlockStatue("statue_tlz", MapColor.GRAY, ModUtils.getPixelatedAABB(3,0,5, 13,30,11)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_MD = new BlockStatue("statue_md", MapColor.GRAY, ModUtils.getPixelatedAABB(3,0,5, 13,30,11)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_LW = new BlockStatue("statue_sloose", MapColor.GRAY, ModUtils.getPixelatedAABB(3,0,5, 13,30,11)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_SVV = new BlockStatue("statue_svv", MapColor.GRAY, ModUtils.getPixelatedAABB(3,0,5, 13,30,11)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_TWO = new BlockStatue("trophy_two_years", MapColor.GRAY, ModUtils.getPixelatedAABB(3,0,5, 13,30,11)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block STATUE_MONTY = new BlockStatue("statue_monty", MapColor.BLACK, ModUtils.getPixelatedAABB(0,0,0, 16,16,16)).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// Plaques
	public static final Block PLAQUE_DEV = new BlockPlaque("plaque_dev", MapColor.GOLD, ModUtils.getPixelatedAABB(3,0,0, 13,12,1.5)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block PLAQUE_SUPPORTER = new BlockPlaque("plaque_supporter", MapColor.GOLD, ModUtils.getPixelatedAABB(3,0,0, 13,12,1.5)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block PLAQUE_PLAYTEST = new BlockPlaque("plaque_playtest", MapColor.GOLD, ModUtils.getPixelatedAABB(3,0,0, 13,12,1.5)).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// Big Plaques
	public static final Block BIG_PLAQUE_PLAYTEST = new BlockLargePlaque("big_plaque_playtest", MapColor.GOLD, ModUtils.getPixelatedAABB(-16,0,0, 16,32,1.5)).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// Trash Can
	public static final Block TRASH_BIN = new BlockBin("rubbish_bin").setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block TRASH_BIN_GREEN = new BlockBin("rubbish_bin_green").setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block TRASH_BIN_BLUE = new BlockBin("rubbish_bin_blue").setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block TRASH_BIN_RED = new BlockBin("rubbish_bin_red").setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block TRASH_BIN_PUSH = new BlockBin("push_trashcan").setCreativeTab(Main.IMMERSIBROOK_MAIN);

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
	public static final Block SMOOTHED_STONE_STAIRS = new MiscStairs("smoothed_stone_stair", ModBlocks.SMOOTHED_STONE.getDefaultState(), SoundType.STONE, "pickaxe", 0).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// Smooth Oak Wood Ceiling Blocks
	public static final Block SO_PANEL_OAK_L = new Immersiblock("so_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_SPRUCE_L = new Immersiblock("so_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_BIRCH_L = new Immersiblock("so_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_FLOOR_JUNGLE_L = new Immersiblock("so_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_ACACIA_L = new Immersiblock("so_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DARK_OAK_L = new Immersiblock("so_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_CHECKERBOARD = new Immersiblock("so_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_CHECKERBOARD_OLD = new Immersiblock("so_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_1 = new Immersiblock("so_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_2 = new Immersiblock("so_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_3 = new Immersiblock("so_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_4 = new Immersiblock("so_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_5 = new Immersiblock("so_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_6 = new Immersiblock("so_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_7 = new Immersiblock("so_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_8 = new Immersiblock("so_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_9 = new Immersiblock("so_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_10 = new Immersiblock("so_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_11 = new Immersiblock("so_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_12 = new Immersiblock("so_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_13 = new Immersiblock("so_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_14 = new Immersiblock("so_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_15 = new Immersiblock("so_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_16 = new Immersiblock("so_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_17 = new Immersiblock("so_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_18 = new Immersiblock("so_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_19 = new Immersiblock("so_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_20 = new Immersiblock("so_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_21 = new Immersiblock("so_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_22 = new Immersiblock("so_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_23 = new Immersiblock("so_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_24 = new Immersiblock("so_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_25 = new Immersiblock("so_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_26 = new Immersiblock("so_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_27 = new Immersiblock("so_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_28 = new Immersiblock("so_panel_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_29 = new Immersiblock("so_panel_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_30 = new Immersiblock("so_panel_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_31 = new Immersiblock("so_panel_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_32 = new Immersiblock("so_panel_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_33 = new Immersiblock("so_panel_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_34 = new Immersiblock("so_panel_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_35 = new Immersiblock("so_panel_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_36 = new Immersiblock("so_panel_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_TILE_37 = new Immersiblock("so_panel_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DANGER_1 = new Immersiblock("so_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DANGER_2 = new Immersiblock("so_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DANGER_3 = new Immersiblock("so_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DANGER_4 = new Immersiblock("so_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_KITCHEN_1 = new Immersiblock("so_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_KITCHEN_2 = new Immersiblock("so_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_KITCHEN_3 = new Immersiblock("so_panel_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DRAIN_1 = new Immersiblock("so_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DRAIN_2 = new Immersiblock("so_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_DRAIN_3 = new Immersiblock("so_panel_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_WHITE = new Immersiblock("so_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_ORANGE = new Immersiblock("so_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_MAGENTA = new Immersiblock("so_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_LBLUE = new Immersiblock("so_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_YELLOW = new Immersiblock("so_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_LIME = new Immersiblock("so_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_PINK = new Immersiblock("so_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_GRAY = new Immersiblock("so_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_SILVER = new Immersiblock("so_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_CYAN = new Immersiblock("so_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_PURPLE = new Immersiblock("so_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_BLUE = new Immersiblock("so_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_BROWN = new Immersiblock("so_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_GREEN = new Immersiblock("so_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_RED = new Immersiblock("so_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SO_PANEL_WOOL_BLACK = new Immersiblock("so_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Birch Wood Ceiling Blocks
	public static final Block SB_PANEL_OAK_L = new Immersiblock("sb_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_SPRUCE_L = new Immersiblock("sb_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_BIRCH_L = new Immersiblock("sb_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_FLOOR_JUNGLE_L = new Immersiblock("sb_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_ACACIA_L = new Immersiblock("sb_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DARK_OAK_L = new Immersiblock("sb_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_CHECKERBOARD = new Immersiblock("sb_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_CHECKERBOARD_OLD = new Immersiblock("sb_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_1 = new Immersiblock("sb_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_2 = new Immersiblock("sb_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_3 = new Immersiblock("sb_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_4 = new Immersiblock("sb_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_5 = new Immersiblock("sb_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_6 = new Immersiblock("sb_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_7 = new Immersiblock("sb_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_8 = new Immersiblock("sb_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_9 = new Immersiblock("sb_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_10 = new Immersiblock("sb_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_11 = new Immersiblock("sb_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_12 = new Immersiblock("sb_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_13 = new Immersiblock("sb_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_14 = new Immersiblock("sb_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_15 = new Immersiblock("sb_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_16 = new Immersiblock("sb_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_17 = new Immersiblock("sb_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_18 = new Immersiblock("sb_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_19 = new Immersiblock("sb_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_20 = new Immersiblock("sb_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_21 = new Immersiblock("sb_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_22 = new Immersiblock("sb_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_23 = new Immersiblock("sb_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_24 = new Immersiblock("sb_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_25 = new Immersiblock("sb_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_26 = new Immersiblock("sb_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_27 = new Immersiblock("sb_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_28 = new Immersiblock("sb_panel_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_29 = new Immersiblock("sb_panel_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_30 = new Immersiblock("sb_panel_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_31 = new Immersiblock("sb_panel_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_32 = new Immersiblock("sb_panel_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_33 = new Immersiblock("sb_panel_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_34 = new Immersiblock("sb_panel_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_35 = new Immersiblock("sb_panel_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_36 = new Immersiblock("sb_panel_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_TILE_37 = new Immersiblock("sb_panel_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DANGER_1 = new Immersiblock("sb_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DANGER_2 = new Immersiblock("sb_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DANGER_3 = new Immersiblock("sb_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DANGER_4 = new Immersiblock("sb_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_KITCHEN_1 = new Immersiblock("sb_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_KITCHEN_2 = new Immersiblock("sb_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_KITCHEN_3 = new Immersiblock("sb_panel_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DRAIN_1 = new Immersiblock("sb_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DRAIN_2 = new Immersiblock("sb_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_DRAIN_3 = new Immersiblock("sb_panel_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_WHITE = new Immersiblock("sb_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_ORANGE = new Immersiblock("sb_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_MAGENTA = new Immersiblock("sb_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_LBLUE = new Immersiblock("sb_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_YELLOW = new Immersiblock("sb_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_LIME = new Immersiblock("sb_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_PINK = new Immersiblock("sb_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_GRAY = new Immersiblock("sb_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_SILVER = new Immersiblock("sb_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_CYAN = new Immersiblock("sb_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_PURPLE = new Immersiblock("sb_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_BLUE = new Immersiblock("sb_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_BROWN = new Immersiblock("sb_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_GREEN = new Immersiblock("sb_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_RED = new Immersiblock("sb_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SB_PANEL_WOOL_BLACK = new Immersiblock("sb_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Spruce Wood Ceiling Blocks
	public static final Block SS_PANEL_OAK_L = new Immersiblock("ss_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_SPRUCE_L = new Immersiblock("ss_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_BIRCH_L = new Immersiblock("ss_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_FLOOR_JUNGLE_L = new Immersiblock("ss_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_ACACIA_L = new Immersiblock("ss_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DARK_OAK_L = new Immersiblock("ss_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_CHECKERBOARD = new Immersiblock("ss_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_CHECKERBOARD_OLD = new Immersiblock("ss_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_1 = new Immersiblock("ss_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_2 = new Immersiblock("ss_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_3 = new Immersiblock("ss_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_4 = new Immersiblock("ss_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_5 = new Immersiblock("ss_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_6 = new Immersiblock("ss_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_7 = new Immersiblock("ss_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_8 = new Immersiblock("ss_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_9 = new Immersiblock("ss_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_10 = new Immersiblock("ss_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_11 = new Immersiblock("ss_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_12 = new Immersiblock("ss_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_13 = new Immersiblock("ss_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_14 = new Immersiblock("ss_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_15 = new Immersiblock("ss_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_16 = new Immersiblock("ss_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_17 = new Immersiblock("ss_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_18 = new Immersiblock("ss_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_19 = new Immersiblock("ss_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_20 = new Immersiblock("ss_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_21 = new Immersiblock("ss_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_22 = new Immersiblock("ss_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_23 = new Immersiblock("ss_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_24 = new Immersiblock("ss_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_25 = new Immersiblock("ss_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_26 = new Immersiblock("ss_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_27 = new Immersiblock("ss_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_28 = new Immersiblock("ss_panel_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_29 = new Immersiblock("ss_panel_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_30 = new Immersiblock("ss_panel_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_31 = new Immersiblock("ss_panel_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_32 = new Immersiblock("ss_panel_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_33 = new Immersiblock("ss_panel_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_34 = new Immersiblock("ss_panel_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_35 = new Immersiblock("ss_panel_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_36 = new Immersiblock("ss_panel_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_TILE_37 = new Immersiblock("ss_panel_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DANGER_1 = new Immersiblock("ss_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DANGER_2 = new Immersiblock("ss_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DANGER_3 = new Immersiblock("ss_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DANGER_4 = new Immersiblock("ss_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_KITCHEN_1 = new Immersiblock("ss_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_KITCHEN_2 = new Immersiblock("ss_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_KITCHEN_3 = new Immersiblock("ss_panel_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DRAIN_1 = new Immersiblock("ss_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DRAIN_2 = new Immersiblock("ss_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_DRAIN_3 = new Immersiblock("ss_panel_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_WHITE = new Immersiblock("ss_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_ORANGE = new Immersiblock("ss_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_MAGENTA = new Immersiblock("ss_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_LBLUE = new Immersiblock("ss_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_YELLOW = new Immersiblock("ss_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_LIME = new Immersiblock("ss_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_PINK = new Immersiblock("ss_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_GRAY = new Immersiblock("ss_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_SILVER = new Immersiblock("ss_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_CYAN = new Immersiblock("ss_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_PURPLE = new Immersiblock("ss_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_BLUE = new Immersiblock("ss_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_BROWN = new Immersiblock("ss_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_GREEN = new Immersiblock("ss_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_RED = new Immersiblock("ss_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SS_PANEL_WOOL_BLACK = new Immersiblock("ss_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Jungle Wood Ceiling Blocks
	public static final Block SJ_PANEL_OAK_L = new Immersiblock("sj_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_SPRUCE_L = new Immersiblock("sj_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_BIRCH_L = new Immersiblock("sj_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_FLOOR_JUNGLE_L = new Immersiblock("sj_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_ACACIA_L = new Immersiblock("sj_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DARK_OAK_L = new Immersiblock("sj_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_CHECKERBOARD = new Immersiblock("sj_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_CHECKERBOARD_OLD = new Immersiblock("sj_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_1 = new Immersiblock("sj_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_2 = new Immersiblock("sj_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_3 = new Immersiblock("sj_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_4 = new Immersiblock("sj_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_5 = new Immersiblock("sj_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_6 = new Immersiblock("sj_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_7 = new Immersiblock("sj_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_8 = new Immersiblock("sj_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_9 = new Immersiblock("sj_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_10 = new Immersiblock("sj_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_11 = new Immersiblock("sj_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_12 = new Immersiblock("sj_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_13 = new Immersiblock("sj_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_14 = new Immersiblock("sj_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_15 = new Immersiblock("sj_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_16 = new Immersiblock("sj_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_17 = new Immersiblock("sj_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_18 = new Immersiblock("sj_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_19 = new Immersiblock("sj_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_20 = new Immersiblock("sj_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_21 = new Immersiblock("sj_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_22 = new Immersiblock("sj_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_23 = new Immersiblock("sj_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_24 = new Immersiblock("sj_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_25 = new Immersiblock("sj_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_26 = new Immersiblock("sj_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_27 = new Immersiblock("sj_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_28 = new Immersiblock("sj_panel_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_29 = new Immersiblock("sj_panel_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_30 = new Immersiblock("sj_panel_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_31 = new Immersiblock("sj_panel_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_32 = new Immersiblock("sj_panel_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_33 = new Immersiblock("sj_panel_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_34 = new Immersiblock("sj_panel_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_35 = new Immersiblock("sj_panel_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_36 = new Immersiblock("sj_panel_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_TILE_37 = new Immersiblock("sj_panel_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DANGER_1 = new Immersiblock("sj_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DANGER_2 = new Immersiblock("sj_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DANGER_3 = new Immersiblock("sj_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DANGER_4 = new Immersiblock("sj_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_KITCHEN_1 = new Immersiblock("sj_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_KITCHEN_2 = new Immersiblock("sj_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_KITCHEN_3 = new Immersiblock("sj_panel_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DRAIN_1 = new Immersiblock("sj_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DRAIN_2 = new Immersiblock("sj_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_DRAIN_3 = new Immersiblock("sj_panel_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_WHITE = new Immersiblock("sj_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_ORANGE = new Immersiblock("sj_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_MAGENTA = new Immersiblock("sj_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_LBLUE = new Immersiblock("sj_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_YELLOW = new Immersiblock("sj_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_LIME = new Immersiblock("sj_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_PINK = new Immersiblock("sj_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_GRAY = new Immersiblock("sj_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_SILVER = new Immersiblock("sj_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_CYAN = new Immersiblock("sj_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_PURPLE = new Immersiblock("sj_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_BLUE = new Immersiblock("sj_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_BROWN = new Immersiblock("sj_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_GREEN = new Immersiblock("sj_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_RED = new Immersiblock("sj_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SJ_PANEL_WOOL_BLACK = new Immersiblock("sj_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Acacia Wood Ceiling Blocks
	public static final Block SA_PANEL_OAK_L = new Immersiblock("sa_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_SPRUCE_L = new Immersiblock("sa_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_BIRCH_L = new Immersiblock("sa_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_FLOOR_JUNGLE_L = new Immersiblock("sa_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_ACACIA_L = new Immersiblock("sa_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DARK_OAK_L = new Immersiblock("sa_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_CHECKERBOARD = new Immersiblock("sa_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_CHECKERBOARD_OLD = new Immersiblock("sa_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_1 = new Immersiblock("sa_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_2 = new Immersiblock("sa_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_3 = new Immersiblock("sa_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_4 = new Immersiblock("sa_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_5 = new Immersiblock("sa_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_6 = new Immersiblock("sa_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_7 = new Immersiblock("sa_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_8 = new Immersiblock("sa_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_9 = new Immersiblock("sa_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_10 = new Immersiblock("sa_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_11 = new Immersiblock("sa_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_12 = new Immersiblock("sa_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_13 = new Immersiblock("sa_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_14 = new Immersiblock("sa_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_15 = new Immersiblock("sa_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_16 = new Immersiblock("sa_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_17 = new Immersiblock("sa_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_18 = new Immersiblock("sa_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_19 = new Immersiblock("sa_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_20 = new Immersiblock("sa_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_21 = new Immersiblock("sa_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_22 = new Immersiblock("sa_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_23 = new Immersiblock("sa_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_24 = new Immersiblock("sa_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_25 = new Immersiblock("sa_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_26 = new Immersiblock("sa_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_27 = new Immersiblock("sa_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_28 = new Immersiblock("sa_panel_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_29 = new Immersiblock("sa_panel_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_30 = new Immersiblock("sa_panel_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_31 = new Immersiblock("sa_panel_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_32 = new Immersiblock("sa_panel_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_33 = new Immersiblock("sa_panel_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_34 = new Immersiblock("sa_panel_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_35 = new Immersiblock("sa_panel_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_36 = new Immersiblock("sa_panel_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_TILE_37 = new Immersiblock("sa_panel_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DANGER_1 = new Immersiblock("sa_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DANGER_2 = new Immersiblock("sa_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DANGER_3 = new Immersiblock("sa_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DANGER_4 = new Immersiblock("sa_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_KITCHEN_1 = new Immersiblock("sa_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_KITCHEN_2 = new Immersiblock("sa_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_KITCHEN_3 = new Immersiblock("sa_panel_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DRAIN_1 = new Immersiblock("sa_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DRAIN_2 = new Immersiblock("sa_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_DRAIN_3 = new Immersiblock("sa_panel_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_WHITE = new Immersiblock("sa_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_ORANGE = new Immersiblock("sa_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_MAGENTA = new Immersiblock("sa_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_LBLUE = new Immersiblock("sa_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_YELLOW = new Immersiblock("sa_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_LIME = new Immersiblock("sa_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_PINK = new Immersiblock("sa_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_GRAY = new Immersiblock("sa_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_SILVER = new Immersiblock("sa_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_CYAN = new Immersiblock("sa_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_PURPLE = new Immersiblock("sa_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_BLUE = new Immersiblock("sa_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_BROWN = new Immersiblock("sa_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_GREEN = new Immersiblock("sa_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_RED = new Immersiblock("sa_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SA_PANEL_WOOL_BLACK = new Immersiblock("sa_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Smooth Dark Oak Wood Ceiling Blocks
	public static final Block SDO_PANEL_OAK_L = new Immersiblock("sdo_panel_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_SPRUCE_L = new Immersiblock("sdo_panel_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_BIRCH_L = new Immersiblock("sdo_panel_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_FLOOR_JUNGLE_L = new Immersiblock("sdo_panel_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_ACACIA_L = new Immersiblock("sdo_panel_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_DARK_OAK_L = new Immersiblock("sdo_panel_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_CHECKERBOARD = new Immersiblock("sdo_panel_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_CHECKERBOARD_OLD = new Immersiblock("sdo_panel_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_1 = new Immersiblock("sdo_panel_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_2 = new Immersiblock("sdo_panel_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_3 = new Immersiblock("sdo_panel_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_4 = new Immersiblock("sdo_panel_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_5 = new Immersiblock("sdo_panel_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_6 = new Immersiblock("sdo_panel_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_7 = new Immersiblock("sdo_panel_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_8 = new Immersiblock("sdo_panel_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_9 = new Immersiblock("sdo_panel_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_10 = new Immersiblock("sdo_panel_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_11 = new Immersiblock("sdo_panel_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_12 = new Immersiblock("sdo_panel_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_13 = new Immersiblock("sdo_panel_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_14 = new Immersiblock("sdo_panel_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_15 = new Immersiblock("sdo_panel_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_16 = new Immersiblock("sdo_panel_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_17 = new Immersiblock("sdo_panel_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_18 = new Immersiblock("sdo_panel_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_19 = new Immersiblock("sdo_panel_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_20 = new Immersiblock("sdo_panel_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_21 = new Immersiblock("sdo_panel_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_22 = new Immersiblock("sdo_panel_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_23 = new Immersiblock("sdo_panel_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_24 = new Immersiblock("sdo_panel_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_25 = new Immersiblock("sdo_panel_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_26 = new Immersiblock("sdo_panel_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_27 = new Immersiblock("sdo_panel_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_28 = new Immersiblock("sdo_panel_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_29 = new Immersiblock("sdo_panel_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_30 = new Immersiblock("sdo_panel_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_31 = new Immersiblock("sdo_panel_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_32 = new Immersiblock("sdo_panel_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_33 = new Immersiblock("sdo_panel_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_34 = new Immersiblock("sdo_panel_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_35 = new Immersiblock("sdo_panel_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_36 = new Immersiblock("sdo_panel_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_TILE_37 = new Immersiblock("sdo_panel_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_DANGER_1 = new Immersiblock("sdo_panel_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_DANGER_2 = new Immersiblock("sdo_panel_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_DANGER_3 = new Immersiblock("sdo_panel_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_DANGER_4 = new Immersiblock("sdo_panel_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_KITCHEN_1 = new Immersiblock("sdo_panel_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_KITCHEN_2 = new Immersiblock("sdo_panel_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_KITCHEN_3 = new Immersiblock("sdo_panel_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_DRAIN_1 = new Immersiblock("sdo_panel_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_DRAIN_2 = new Immersiblock("sdo_panel_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_DRAIN_3 = new Immersiblock("sdo_panel_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_WHITE = new Immersiblock("sdo_panel_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_ORANGE = new Immersiblock("sdo_panel_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_MAGENTA = new Immersiblock("sdo_panel_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_LBLUE = new Immersiblock("sdo_panel_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_YELLOW = new Immersiblock("sdo_panel_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_LIME = new Immersiblock("sdo_panel_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_PINK = new Immersiblock("sdo_panel_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_GRAY = new Immersiblock("sdo_panel_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_SILVER = new Immersiblock("sdo_panel_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_CYAN = new Immersiblock("sdo_panel_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_PURPLE = new Immersiblock("sdo_panel_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_BLUE = new Immersiblock("sdo_panel_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_BROWN = new Immersiblock("sdo_panel_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_GREEN = new Immersiblock("sdo_panel_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_RED = new Immersiblock("sdo_panel_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block SDO_PANEL_WOOL_BLACK = new Immersiblock("sdo_panel_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// White Concrete Ceiling Panels
	public static final Block CW_OAK_L = new Immersiblock("white_concrete_tile_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_SPRUCE_L = new Immersiblock("white_concrete_tile_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_BIRCH_L = new Immersiblock("white_concrete_tile_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_FLOOR_JUNGLE_L = new Immersiblock("white_concrete_tile_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_ACACIA_L = new Immersiblock("white_concrete_tile_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_DARK_OAK_L = new Immersiblock("white_concrete_tile_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_CHECKERBOARD = new Immersiblock("white_concrete_tile_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_CHECKERBOARD_OLD = new Immersiblock("white_concrete_tile_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_1 = new Immersiblock("white_concrete_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_2 = new Immersiblock("white_concrete_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_3 = new Immersiblock("white_concrete_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_4 = new Immersiblock("white_concrete_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_5 = new Immersiblock("white_concrete_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_6 = new Immersiblock("white_concrete_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_7 = new Immersiblock("white_concrete_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_8 = new Immersiblock("white_concrete_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_9 = new Immersiblock("white_concrete_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_10 = new Immersiblock("white_concrete_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_11 = new Immersiblock("white_concrete_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_12 = new Immersiblock("white_concrete_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_13 = new Immersiblock("white_concrete_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_14 = new Immersiblock("white_concrete_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_15 = new Immersiblock("white_concrete_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_16 = new Immersiblock("white_concrete_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_17 = new Immersiblock("white_concrete_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_18 = new Immersiblock("white_concrete_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_19 = new Immersiblock("white_concrete_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_20 = new Immersiblock("white_concrete_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_21 = new Immersiblock("white_concrete_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_22 = new Immersiblock("white_concrete_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_23 = new Immersiblock("white_concrete_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_24 = new Immersiblock("white_concrete_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_25 = new Immersiblock("white_concrete_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_26 = new Immersiblock("white_concrete_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_27 = new Immersiblock("white_concrete_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_28 = new Immersiblock("white_concrete_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_29 = new Immersiblock("white_concrete_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_30 = new Immersiblock("white_concrete_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_31 = new Immersiblock("white_concrete_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_32 = new Immersiblock("white_concrete_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_33 = new Immersiblock("white_concrete_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_34 = new Immersiblock("white_concrete_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_35 = new Immersiblock("white_concrete_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_36 = new Immersiblock("white_concrete_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_TILE_37 = new Immersiblock("white_concrete_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_DANGER_1 = new Immersiblock("white_concrete_tile_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_DANGER_2 = new Immersiblock("white_concrete_tile_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_DANGER_3 = new Immersiblock("white_concrete_tile_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_DANGER_4 = new Immersiblock("white_concrete_tile_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_KITCHEN_1 = new Immersiblock("white_concrete_tile_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_KITCHEN_2 = new Immersiblock("white_concrete_tile_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_KITCHEN_3 = new Immersiblock("white_concrete_tile_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_DRAIN_1 = new Immersiblock("white_concrete_tile_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_DRAIN_2 = new Immersiblock("white_concrete_tile_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_DRAIN_3 = new Immersiblock("white_concrete_tile_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_WHITE = new Immersiblock("white_concrete_tile_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_ORANGE = new Immersiblock("white_concrete_tile_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_MAGENTA = new Immersiblock("white_concrete_tile_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_LBLUE = new Immersiblock("white_concrete_tile_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_YELLOW = new Immersiblock("white_concrete_tile_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_LIME = new Immersiblock("white_concrete_tile_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_PINK = new Immersiblock("white_concrete_tile_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_GRAY = new Immersiblock("white_concrete_tile_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_SILVER = new Immersiblock("white_concrete_tile_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_CYAN = new Immersiblock("white_concrete_tile_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_PURPLE = new Immersiblock("white_concrete_tile_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_BLUE = new Immersiblock("white_concrete_tile_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_BROWN = new Immersiblock("white_concrete_tile_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_GREEN = new Immersiblock("white_concrete_tile_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_RED = new Immersiblock("white_concrete_tile_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CW_WOOL_BLACK = new Immersiblock("white_concrete_tile_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Gray Concrete Ceiling Panels
	public static final Block CG_OAK_L = new Immersiblock("gray_concrete_tile_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_SPRUCE_L = new Immersiblock("gray_concrete_tile_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_BIRCH_L = new Immersiblock("gray_concrete_tile_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_FLOOR_JUNGLE_L = new Immersiblock("gray_concrete_tile_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_ACACIA_L = new Immersiblock("gray_concrete_tile_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_DARK_OAK_L = new Immersiblock("gray_concrete_tile_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_CHECKERBOARD = new Immersiblock("gray_concrete_tile_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_CHECKERBOARD_OLD = new Immersiblock("gray_concrete_tile_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_1 = new Immersiblock("gray_concrete_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_2 = new Immersiblock("gray_concrete_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_3 = new Immersiblock("gray_concrete_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_4 = new Immersiblock("gray_concrete_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_5 = new Immersiblock("gray_concrete_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_6 = new Immersiblock("gray_concrete_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_7 = new Immersiblock("gray_concrete_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_8 = new Immersiblock("gray_concrete_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_9 = new Immersiblock("gray_concrete_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_10 = new Immersiblock("gray_concrete_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_11 = new Immersiblock("gray_concrete_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_12 = new Immersiblock("gray_concrete_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_13 = new Immersiblock("gray_concrete_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_14 = new Immersiblock("gray_concrete_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_15 = new Immersiblock("gray_concrete_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_16 = new Immersiblock("gray_concrete_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_17 = new Immersiblock("gray_concrete_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_18 = new Immersiblock("gray_concrete_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_19 = new Immersiblock("gray_concrete_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_20 = new Immersiblock("gray_concrete_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_21 = new Immersiblock("gray_concrete_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_22 = new Immersiblock("gray_concrete_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_23 = new Immersiblock("gray_concrete_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_24 = new Immersiblock("gray_concrete_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_25 = new Immersiblock("gray_concrete_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_26 = new Immersiblock("gray_concrete_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_27 = new Immersiblock("gray_concrete_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_28 = new Immersiblock("gray_concrete_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_29 = new Immersiblock("gray_concrete_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_30 = new Immersiblock("gray_concrete_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_31 = new Immersiblock("gray_concrete_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_32 = new Immersiblock("gray_concrete_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_33 = new Immersiblock("gray_concrete_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_34 = new Immersiblock("gray_concrete_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_35 = new Immersiblock("gray_concrete_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_36 = new Immersiblock("gray_concrete_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_TILE_37 = new Immersiblock("gray_concrete_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_DANGER_1 = new Immersiblock("gray_concrete_tile_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_DANGER_2 = new Immersiblock("gray_concrete_tile_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_DANGER_3 = new Immersiblock("gray_concrete_tile_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_DANGER_4 = new Immersiblock("gray_concrete_tile_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_KITCHEN_1 = new Immersiblock("gray_concrete_tile_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_KITCHEN_2 = new Immersiblock("gray_concrete_tile_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_KITCHEN_3 = new Immersiblock("gray_concrete_tile_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_DRAIN_1 = new Immersiblock("gray_concrete_tile_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_DRAIN_2 = new Immersiblock("gray_concrete_tile_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_DRAIN_3 = new Immersiblock("gray_concrete_tile_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_WHITE = new Immersiblock("gray_concrete_tile_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_ORANGE = new Immersiblock("gray_concrete_tile_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_MAGENTA = new Immersiblock("gray_concrete_tile_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_LBLUE = new Immersiblock("gray_concrete_tile_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_YELLOW = new Immersiblock("gray_concrete_tile_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_LIME = new Immersiblock("gray_concrete_tile_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_PINK = new Immersiblock("gray_concrete_tile_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_GRAY = new Immersiblock("gray_concrete_tile_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_SILVER = new Immersiblock("gray_concrete_tile_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_CYAN = new Immersiblock("gray_concrete_tile_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_PURPLE = new Immersiblock("gray_concrete_tile_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_BLUE = new Immersiblock("gray_concrete_tile_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_BROWN = new Immersiblock("gray_concrete_tile_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_GREEN = new Immersiblock("gray_concrete_tile_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_RED = new Immersiblock("gray_concrete_tile_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CG_WOOL_BLACK = new Immersiblock("gray_concrete_tile_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Silver Concrete Ceiling Panels
	public static final Block CS_OAK_L = new Immersiblock("silver_concrete_tile_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_SPRUCE_L = new Immersiblock("silver_concrete_tile_spruce_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_BIRCH_L = new Immersiblock("silver_concrete_tile_birch_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_FLOOR_JUNGLE_L = new Immersiblock("silver_concrete_tile_jungle_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_ACACIA_L = new Immersiblock("silver_concrete_tile_acacia_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_DARK_OAK_L = new Immersiblock("silver_concrete_tile_dark_oak_l", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_CHECKERBOARD = new Immersiblock("silver_concrete_tile_checkerboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_CHECKERBOARD_OLD = new Immersiblock("silver_concrete_tile_tileboard", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_1 = new Immersiblock("silver_concrete_tile_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_2 = new Immersiblock("silver_concrete_tile_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_3 = new Immersiblock("silver_concrete_tile_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_4 = new Immersiblock("silver_concrete_tile_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_5 = new Immersiblock("silver_concrete_tile_5", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_6 = new Immersiblock("silver_concrete_tile_6", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_7 = new Immersiblock("silver_concrete_tile_7", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_8 = new Immersiblock("silver_concrete_tile_8", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_9 = new Immersiblock("silver_concrete_tile_9", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_10 = new Immersiblock("silver_concrete_tile_10", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_11 = new Immersiblock("silver_concrete_tile_11", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_12 = new Immersiblock("silver_concrete_tile_12", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_13 = new Immersiblock("silver_concrete_tile_13", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_14 = new Immersiblock("silver_concrete_tile_14", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_15 = new Immersiblock("silver_concrete_tile_15", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_16 = new Immersiblock("silver_concrete_tile_16", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_17 = new Immersiblock("silver_concrete_tile_17", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_18 = new Immersiblock("silver_concrete_tile_18", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_19 = new Immersiblock("silver_concrete_tile_19", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_20 = new Immersiblock("silver_concrete_tile_20", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_21 = new Immersiblock("silver_concrete_tile_21", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_22 = new Immersiblock("silver_concrete_tile_22", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_23 = new Immersiblock("silver_concrete_tile_23", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_24 = new Immersiblock("silver_concrete_tile_24", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_25 = new Immersiblock("silver_concrete_tile_25", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_26 = new Immersiblock("silver_concrete_tile_26", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_27 = new Immersiblock("silver_concrete_tile_27", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_28 = new Immersiblock("silver_concrete_tile_28", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_29 = new Immersiblock("silver_concrete_tile_29", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_30 = new Immersiblock("silver_concrete_tile_30", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_31 = new Immersiblock("silver_concrete_tile_31", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_32 = new Immersiblock("silver_concrete_tile_32", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_33 = new Immersiblock("silver_concrete_tile_33", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_34 = new Immersiblock("silver_concrete_tile_34", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_35 = new Immersiblock("silver_concrete_tile_35", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_36 = new Immersiblock("silver_concrete_tile_36", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_TILE_37 = new Immersiblock("silver_concrete_tile_37", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_DANGER_1 = new Immersiblock("silver_concrete_tile_danger_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_DANGER_2 = new Immersiblock("silver_concrete_tile_danger_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_DANGER_3 = new Immersiblock("silver_concrete_tile_danger_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_DANGER_4 = new Immersiblock("silver_concrete_tile_danger_4", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_KITCHEN_1 = new Immersiblock("silver_concrete_tile_kitchen_1", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_KITCHEN_2 = new Immersiblock("silver_concrete_tile_kitchen_2", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_KITCHEN_3 = new Immersiblock("silver_concrete_tile_kitchen_3", Material.ROCK, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_DRAIN_1 = new Immersiblock("silver_concrete_tile_drain_1", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_DRAIN_2 = new Immersiblock("silver_concrete_tile_drain_2", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_DRAIN_3 = new Immersiblock("silver_concrete_tile_drain_3", Material.IRON, SoundType.STONE, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_WHITE = new Immersiblock("silver_concrete_tile_wool_white", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_ORANGE = new Immersiblock("silver_concrete_tile_wool_orange", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_MAGENTA = new Immersiblock("silver_concrete_tile_wool_magenta", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_LBLUE = new Immersiblock("silver_concrete_tile_wool_lblue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_YELLOW = new Immersiblock("silver_concrete_tile_wool_yellow", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_LIME = new Immersiblock("silver_concrete_tile_wool_lime", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_PINK = new Immersiblock("silver_concrete_tile_wool_pink", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_GRAY = new Immersiblock("silver_concrete_tile_wool_gray", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_SILVER = new Immersiblock("silver_concrete_tile_wool_silver", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_CYAN = new Immersiblock("silver_concrete_tile_wool_cyan", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_PURPLE = new Immersiblock("silver_concrete_tile_wool_purple", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_BLUE = new Immersiblock("silver_concrete_tile_wool_blue", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_BROWN = new Immersiblock("silver_concrete_tile_wool_brown", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_GREEN = new Immersiblock("silver_concrete_tile_wool_green", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_RED = new Immersiblock("silver_concrete_tile_wool_red", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);
	public static final Block CS_WOOL_BLACK = new Immersiblock("silver_concrete_tile_wool_black", Material.CLOTH, SoundType.CLOTH, Main.IMMERSIBROOK_MAIN, 0F);

	// Food Boxes
	public static final Block SUGAR_BOX = new FoodBox("sugar_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(12,0,6, 4,9,10));
	public static final Block FLOUR_BOX = new FoodBox("flour_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(12,0,6, 4,9,10));
	public static final Block CORNMEAL_BOX = new FoodBox("cornmeal_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(12,0,6, 4,9,10));
	public static final Block COFFEE_BOX = new FoodBox("coffee_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(12,0,6, 4,9,10));
	public static final Block MILK_CHOC_TRUFFLE_BOX = new FoodBox("milk_chocolate_truffles_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(6,0,7, 10,5,9));
	public static final Block WHITE_CHOC_TRUFFLE_BOX = new FoodBox("white_chocolate_truffles_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(6,0,7, 10,5,9));
	public static final Block CHOC_CARAMEL_TRUFFLE_BOX = new FoodBox("choc_caramel_truffle_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(6,0,7, 10,5,9));
	public static final Block CHOC_PB_TRUFFLE_BOX = new FoodBox("choc_pb_truffle_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(6,0,7, 10,5,9));
	public static final Block CHOC_STRAWBERRY_TRUFFLE_BOX = new FoodBox("choc_strawberry_truffle_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(6,0,7, 10,5,9));
	public static final Block CHOC_GRAPE_TRUFFLE_BOX = new FoodBox("choc_grape_truffle_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(6,0,7, 10,5,9));
	public static final Block CHOC_BLUEBERRY_TRUFFLE_BOX = new FoodBox("choc_blueberry_truffle_box", Material.WOOD, SoundType.SAND, 64, "pickaxe", 1, 1.0F, 1.0F, ModUtils.getPixelatedAABB(6,0,7, 10,5,9));

	// Trusses
	public static final Block STEEL_TRUSS = new Truss("steel_truss", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block STEEL_TRUSS_HOWE = new Truss("steel_truss_howe", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block STEEL_TRUSS_PRATT = new Truss("steel_truss_pratt", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block IRON_TRUSS = new Truss("iron_truss", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block IRON_TRUSS_HOWE = new Truss("iron_truss_howe", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block IRON_TRUSS_PRATT = new Truss("iron_truss_pratt", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block NICKEL_TRUSS = new Truss("nickel_truss", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block NICKEL_TRUSS_HOWE = new Truss("nickel_truss_howe", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block NICKEL_TRUSS_PRATT = new Truss("nickel_truss_pratt", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block CONSTANTAN_TRUSS = new Truss("constantan_truss", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block CONSTANTAN_TRUSS_HOWE = new Truss("constantan_truss_howe", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block CONSTANTAN_TRUSS_PRATT = new Truss("constantan_truss_pratt", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.METAL);
	public static final Block CHEESE_TRUSS = new Truss("cheese_truss", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.SLIME);
	public static final Block CHEESE_TRUSS_HOWE = new Truss("cheese_truss_howe", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.SLIME);
	public static final Block CHEESE_TRUSS_PRATT = new Truss("cheese_truss_pratt", ModUtils.getPixelatedAABB(16,0,6, 0,16,10), SoundType.SLIME);
	
	// Telecom
	public static final Block CELL_ANTENNA = new BlockCellAntenna("cell_antenna");

	// Shipping Boxes
	public static final Block SHIPPING_BOX_SMALL = new BlockShippingBox("box_small", ModUtils.getPixelatedAABB(5.5,0,5.5, 10.5,5,10.5));
	public static final Block SHIPPING_BOX_MEDIUM = new BlockShippingBox("box_medium", ModUtils.getPixelatedAABB(2,0,2, 14,9,14));
	public static final Block SHIPPING_BOX_LARGE = new BlockShippingBox("box_large", ModUtils.getPixelatedAABB(0,0,0, 16,12,16));
	public static final Block SHIPPING_BOX_CHUNGUS = new BlockShippingBox("box_xl", ModUtils.getPixelatedAABB(0,0,0, 16,16,16));

	// Columns, not to be confused with Pillars.
	public static final Block COLUMN_WHITE_CONCRETE = new BlockColumn("column_concrete_white", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_ORANGE_CONCRETE = new BlockColumn("column_concrete_orange", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_MAGENTA_CONCRETE = new BlockColumn("column_concrete_magenta", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_LBLUE_CONCRETE = new BlockColumn("column_concrete_lblue", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_YELLOW_CONCRETE = new BlockColumn("column_concrete_yellow", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_LIME_CONCRETE = new BlockColumn("column_concrete_lime", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_PINK_CONCRETE = new BlockColumn("column_concrete_pink", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_GRAY_CONCRETE = new BlockColumn("column_concrete_gray", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_SILVER_CONCRETE = new BlockColumn("column_concrete_silver", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_CYAN_CONCRETE = new BlockColumn("column_concrete_cyan", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_PURPLE_CONCRETE = new BlockColumn("column_concrete_purple", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_BLUE_CONCRETE = new BlockColumn("column_concrete_blue", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_BROWN_CONCRETE = new BlockColumn("column_concrete_brown", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_GREEN_CONCRETE = new BlockColumn("column_concrete_green", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_RED_CONCRETE = new BlockColumn("column_concrete_red", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_BLACK_CONCRETE = new BlockColumn("column_concrete_black", Material.ROCK, SoundType.STONE, "pickaxe", 1, 0.0F);
	public static final Block COLUMN_GLOWING = new BlockColumn("column_glowing_plastic", Material.ROCK, SoundType.STONE, "pickaxe", 1, 1.0F);

	// Plexiglass
	public static final Block PLEXIGLASS_0 = new BlockPlexiglass("plexiglass_0");
	public static final Block PLEXIGLASS_1 = new BlockPlexiglass("plexiglass_1");
	public static final Block PLEXIGLASS_2 = new BlockPlexiglass("plexiglass_2");
	public static final Block PLEXIGLASS_3 = new BlockPlexiglass("plexiglass_3");
	public static final Block PLEXIGLASS_4 = new BlockPlexiglass("plexiglass_4");
	public static final Block PLEXIGLASS_5 = new BlockPlexiglass("plexiglass_5");
	public static final Block PLEXIGLASS_6 = new BlockPlexiglass("plexiglass_6");
	public static final Block PLEXIGLASS_7 = new BlockPlexiglass("plexiglass_7");
	public static final Block PLEXIGLASS_8 = new BlockPlexiglass("plexiglass_8");
	public static final Block PLEXIGLASS_9 = new BlockPlexiglass("plexiglass_9");
	public static final Block PLEXIGLASS_10 = new BlockPlexiglass("plexiglass_10");
	public static final Block PLEXIGLASS_PANE_0 = new BlockPlexiglassPane("plexiglass_pane_0");
	public static final Block PLEXIGLASS_PANE_1 = new BlockPlexiglassPane("plexiglass_pane_1");
	public static final Block PLEXIGLASS_PANE_2 = new BlockPlexiglassPane("plexiglass_pane_2");
	public static final Block PLEXIGLASS_PANE_3 = new BlockPlexiglassPane("plexiglass_pane_3");
	public static final Block PLEXIGLASS_PANE_4 = new BlockPlexiglassPane("plexiglass_pane_4");
	public static final Block PLEXIGLASS_PANE_5 = new BlockPlexiglassPane("plexiglass_pane_5");
	public static final Block PLEXIGLASS_PANE_6 = new BlockPlexiglassPane("plexiglass_pane_6");
	public static final Block PLEXIGLASS_PANE_7 = new BlockPlexiglassPane("plexiglass_pane_7");
	public static final Block PLEXIGLASS_PANE_8 = new BlockPlexiglassPane("plexiglass_pane_8");
	public static final Block PLEXIGLASS_PANE_9 = new BlockPlexiglassPane("plexiglass_pane_9");
	public static final Block PLEXIGLASS_PANE_10 = new BlockPlexiglassPane("plexiglass_pane_10");

	// Transit
	public static final Block TICKET_MACHINE = new TicketMachine("ticket_machine");

	// Underground Utilities Marker Poles
	public static final Block MARKER_POLE_ORANGE = new ImmersiblockRotational("marker_pole", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 2.5F, 3.0F, ModUtils.getPixelatedAABB(7,0,7, 9,26,9));
	public static final Block MARKER_POLE_RED = new ImmersiblockRotational("marker_pole_red", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 2.5F, 3.0F, ModUtils.getPixelatedAABB(7,0,7, 9,26,9));
	public static final Block MARKER_POLE_BLUE = new ImmersiblockRotational("marker_pole_blue", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 2.5F, 3.0F, ModUtils.getPixelatedAABB(7,0,7, 9,26,9));
	public static final Block MARKER_POLE_WOOD_ORANGE = new ImmersiblockRotational("marker_pole_wood", Material.ROCK, SoundType.WOOD, "axe", 1, 2.5F, 3.0F, ModUtils.getPixelatedAABB(7,0,7, 9,26,9));
	public static final Block MARKER_POLE_WOOD_RED = new ImmersiblockRotational("marker_pole_wood_red", Material.ROCK, SoundType.WOOD, "axe", 1, 2.5F, 3.0F, ModUtils.getPixelatedAABB(7,0,7, 9,26,9));
	public static final Block MARKER_POLE_WOOD_BLUE = new ImmersiblockRotational("marker_pole_wood_blue", Material.ROCK, SoundType.WOOD, "axe", 1, 2.5F, 3.0F, ModUtils.getPixelatedAABB(7,0,7, 9,26,9));

	// Manhole Covers
	public static final Block RELAY_MANHOLE = new ImmersiblockRotational("relay_manhole", Material.IRON, SoundTypeInit.MANHOLE, "pickaxe", 1, 1.0F, 2.0F, ModUtils.getPixelatedAABB(0,0,0, 16,1,16));
	public static final Block LVN_MANHOLE = new ImmersiblockRotational("lvn_manhole", Material.IRON, SoundTypeInit.MANHOLE, "pickaxe", 1, 1.0F, 2.0F, ModUtils.getPixelatedAABB(0,0,0, 16,1,16));
	public static final Block UTIL_MANHOLE = new ImmersiblockRotational("util_manhole", Material.IRON, SoundTypeInit.MANHOLE, "pickaxe", 1, 1.0F, 2.0F, ModUtils.getPixelatedAABB(0,0,0, 16,1,16));
	public static final Block BLANK_MANHOLE = new ImmersiblockRotational("manhole", Material.IRON, SoundTypeInit.MANHOLE, "pickaxe", 1, 1.0F, 2.0F, ModUtils.getPixelatedAABB(0,0,0, 16,1,16));

	// Manhole Blocks
	public static final Block MANHOLE_CLOSED = new BlockManhole("relay_manhole_closed", ModUtils.getPixelatedAABB(0,0,0, 16,16,16)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block UTIL_MANHOLE_CLOSED = new BlockManhole("util_manhole_closed", ModUtils.getPixelatedAABB(0,0,0, 16,16,16)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block BLANK_MANHOLE_CLOSED = new BlockManhole("manhole_closed", ModUtils.getPixelatedAABB(0,0,0, 16,16,16)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block LVN_MANHOLE_CLOSED = new BlockManhole("lvn_manhole_closed", ModUtils.getPixelatedAABB(0,0,0, 16,16,16)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block MANHOLE_LADDER = new BlockManhole("manhole_ladder", ModUtils.getPixelatedAABB(0,0,0, 16,32,3)).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// Manhole Technical Blocks
	public static final Block MANHOLE_OPEN = new BlockManhole("relay_manhole_open", ModUtils.getPixelatedAABB(0,0,0, 16,32,3));
	public static final Block UTIL_MANHOLE_OPEN = new BlockManhole("util_manhole_open", ModUtils.getPixelatedAABB(0,0,0, 16,32,3));
	public static final Block BLANK_MANHOLE_OPEN = new BlockManhole("manhole_open", ModUtils.getPixelatedAABB(0,0,0, 16,32,3));
	public static final Block LVN_MANHOLE_OPEN = new BlockManhole("lvn_manhole_open", ModUtils.getPixelatedAABB(0,0,0, 16,32,3));

	// Steel Ladder
	public static final Block METAL_LADDER = new BlockImmersiLadder("metal_ladder");

	// Vinyl Siding Panels
	public static final Block SIDING_WHITE = new BlockSiding("siding_white", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_ORANGE = new BlockSiding("siding_orange", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_MAGENTA = new BlockSiding("siding_magenta", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_LBLUE = new BlockSiding("siding_lblue", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_YELLOW = new BlockSiding("siding_yellow", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_LIME = new BlockSiding("siding_lime", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_PINK = new BlockSiding("siding_pink", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_GRAY = new BlockSiding("siding_gray", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_SILVER = new BlockSiding("siding_silver", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_CYAN = new BlockSiding("siding_cyan", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_PURPLE = new BlockSiding("siding_purple", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_BROWN = new BlockSiding("siding_brown", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_BLUE = new BlockSiding("siding_blue", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_RED = new BlockSiding("siding_red", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_GREEN = new BlockSiding("siding_green", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_BLACK = new BlockSiding("siding_black", Material.ROCK, SoundTypeInit.PLASTIC, "pickaxe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_OAK = new BlockSiding("siding_oak", Material.WOOD, SoundType.WOOD, "axe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_SPRUCE = new BlockSiding("siding_spruce", Material.WOOD, SoundType.WOOD, "axe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_BIRCH = new BlockSiding("siding_birch", Material.WOOD, SoundType.WOOD, "axe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_JUNGLE = new BlockSiding("siding_jungle", Material.WOOD, SoundType.WOOD, "axe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_ACACIA = new BlockSiding("siding_acacia", Material.WOOD, SoundType.WOOD, "axe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));
	public static final Block SIDING_DARK_OAK = new BlockSiding("siding_dark_oak", Material.WOOD, SoundType.WOOD, "axe", 1, 0.5F, 1.0F, ModUtils.getPixelatedAABB(0,0,0, 16,16,1));

	// OBJ Test Block
	public static final Block OBJ_EYE = new BlockTestEye("eye");

	// Colored Blocks
	public static final Block COLORED_STONE = new BlockColoredStone();
	public static final Block COLORED_COBBLESTONE = new BlockColoredCobblestone();
	public static final Block COLORED_STONE_BRICKS = new BlockColoredStoneBricks();
	public static final Block COLORED_BRICKS = new BlockColoredBricks();

	// Doors
	public static final Block OAK_PUSH_DOOR = new BlockDoorBase("oak_push_door", Material.WOOD);
	public static final Block SPRUCE_PUSH_DOOR = new BlockDoorBase("spruce_push_door", Material.WOOD);
	public static final Block BIRCH_PUSH_DOOR = new BlockDoorBase("birch_push_door", Material.WOOD);
	public static final Block JUNGLE_PUSH_DOOR = new BlockDoorBase("jungle_push_door", Material.WOOD);
	public static final Block ACACIA_PUSH_DOOR = new BlockDoorBase("acacia_push_door", Material.WOOD);
	public static final Block DARK_OAK_PUSH_DOOR = new BlockDoorBase("dark_oak_push_door", Material.WOOD);

	// Wall Signs
	public static final Block WALL_SIGN_BLACK = new BlockWallSign("wall_sign_black", MapColor.BLACK, ModUtils.getPixelatedAABB(5,5,0, 11,11,0.8)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block WALL_SIGN_RED = new BlockWallSign("wall_sign_red", MapColor.RED, ModUtils.getPixelatedAABB(5,5,0, 11,11,0.8)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block WALL_SIGN_GREEN = new BlockWallSign("wall_sign_green", MapColor.GREEN, ModUtils.getPixelatedAABB(5,5,0, 11,11,0.8)).setCreativeTab(Main.IMMERSIBROOK_MAIN);
	public static final Block WALL_SIGN_BLUE = new BlockWallSign("wall_sign_blue", MapColor.BLUE, ModUtils.getPixelatedAABB(5,5,0, 11,11,0.8)).setCreativeTab(Main.IMMERSIBROOK_MAIN);

	// In-Street Crosswalk Sign
	public static final Block IN_STREET_CROSSWALK_SIGN = new ImmersiblockRotational("in_street_crosswalk_sign", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.getPixelatedAABB(3,0,5, 13,21,11));

	// Self-Checkout Blocks
	public static final Block SCO_POS = new ImmersiblockRotational("sco_pos", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);
	public static final Block SCO_SCANNER = new ImmersiblockRotational("sco_scanner", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);
	public static final Block SCO_BAGGING = new ImmersiblockRotational("sco_bagging", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);

	// Retail Shelving Blocks
	public static final Block SHELF_ONE_LEVEL_TWO_PEGHOOKS = new ImmersiblockRotational("shelf_one_level_two_peghooks", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);
	public static final Block SHELF_FOUR_PEGHOOKS = new ImmersiblockRotational("shelf_four_peghooks", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);
	public static final Block SHELF_TWO_LEVELS_NO_PEGHOOKS = new ImmersiblockRotational("shelf_two_levels_no_peghooks", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);

	// Security Station
	public static final Block SECURITY_STATION = new ImmersiblockRotational("security_station", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);
}