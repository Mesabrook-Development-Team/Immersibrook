package com.mesabrook.ib.client.category;

import com.mesabrook.ib.init.ModBlocks;
import net.minecraft.item.ItemStack;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 * 
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */
public class CategoryBlocks extends AbstractCategory
{

	public CategoryBlocks()
	{
		super("im.filter.building", new ItemStack(ModBlocks.FLOOR_OLD_CHECKERBOARD));
	}
	
	@Override
	public void init() 
	{
		// Laminate Wood
		add(ModBlocks.FLOOR_OAK);
		add(ModBlocks.FLOOR_SPRUCE);
		add(ModBlocks.FLOOR_BIRCH);
		add(ModBlocks.FLOOR_JUNGLE);
		add(ModBlocks.FLOOR_ACACIA);
		add(ModBlocks.FLOOR_DARK_OAK);
		
		// Smooth Wood
		add(ModBlocks.SMOOTH_OAK);
		add(ModBlocks.SMOOTH_SPRUCE);
		add(ModBlocks.SMOOTH_BIRCH);
		add(ModBlocks.SMOOTH_JUNGLE);
		add(ModBlocks.SMOOTH_ACACIA);
		add(ModBlocks.SMOOTH_DARK_OAK);
		
		// Laminate Stairs
		add(ModBlocks.STAIRS_OAK_L);
		add(ModBlocks.STAIRS_BIRCH_L);
		add(ModBlocks.STAIRS_SPRUCE_L);
		add(ModBlocks.STAIRS_JUNGLE_L);
		add(ModBlocks.STAIRS_ACACIA_L);
		add(ModBlocks.STAIRS_DARK_OAK_L);
		
		// Smooth Wood Stairs
		add(ModBlocks.STAIRS_SMOOTH_OAK);
		add(ModBlocks.STAIRS_SMOOTH_BIRCH);
		add(ModBlocks.STAIRS_SMOOTH_SPRUCE);
		add(ModBlocks.STAIRS_SMOOTH_JUNGLE);
		add(ModBlocks.STAIRS_SMOOTH_ACACIA);
		add(ModBlocks.STAIRS_SMOOTH_DARK_OAK);
		
		// Chalked Stone Stairs
		add(ModBlocks.CHALKED_STONE_STAIRS);
		add(ModBlocks.CHALKED_COBBLESTONE_STAIRS);
		add(ModBlocks.CHALKED_STONE_BRICK_STAIRS);
		add(ModBlocks.SMOOTHED_STONE_STAIRS);
		
		// Floor Tiles
		add(ModBlocks.FLOOR_CHECKERBOARD);
		add(ModBlocks.FLOOR_OLD_CHECKERBOARD);
		add(ModBlocks.FLOOR_TILE_1);
		add(ModBlocks.FLOOR_TILE_2);
		add(ModBlocks.FLOOR_TILE_3);
		add(ModBlocks.FLOOR_TILE_4);
		add(ModBlocks.FLOOR_TILE_5);
		add(ModBlocks.FLOOR_TILE_6);
		add(ModBlocks.FLOOR_TILE_7);
		add(ModBlocks.FLOOR_TILE_8);
		add(ModBlocks.FLOOR_TILE_9);
		add(ModBlocks.FLOOR_TILE_10);
		add(ModBlocks.FLOOR_TILE_11);
		add(ModBlocks.FLOOR_TILE_12);
		add(ModBlocks.FLOOR_TILE_13);
		add(ModBlocks.FLOOR_TILE_14);
		add(ModBlocks.FLOOR_TILE_15);
		add(ModBlocks.FLOOR_TILE_16);
		add(ModBlocks.FLOOR_TILE_17);
		add(ModBlocks.FLOOR_TILE_18);
		add(ModBlocks.FLOOR_TILE_19);
		add(ModBlocks.FLOOR_TILE_20);
		add(ModBlocks.FLOOR_TILE_21);
		add(ModBlocks.FLOOR_TILE_22);
		add(ModBlocks.FLOOR_TILE_23);
		add(ModBlocks.FLOOR_TILE_24);
		add(ModBlocks.FLOOR_TILE_25);
		add(ModBlocks.FLOOR_TILE_26);
		add(ModBlocks.FLOOR_TILE_27);
		add(ModBlocks.FLOOR_TILE_28);
		add(ModBlocks.FLOOR_TILE_29);
		add(ModBlocks.FLOOR_TILE_30);
		add(ModBlocks.FLOOR_TILE_31);
		add(ModBlocks.FLOOR_TILE_32);
		add(ModBlocks.FLOOR_TILE_33);
		add(ModBlocks.FLOOR_TILE_34);
		add(ModBlocks.FLOOR_TILE_35);
		add(ModBlocks.FLOOR_TILE_36);
		add(ModBlocks.FLOOR_TILE_37);

		// Plastic Blocks
		add(ModBlocks.PLASTIC_CUBE_WHITE);
		add(ModBlocks.PLASTIC_CUBE_ORANGE);
		add(ModBlocks.PLASTIC_CUBE_MAGENTA);
		add(ModBlocks.PLASTIC_CUBE_LBLUE);
		add(ModBlocks.PLASTIC_CUBE_YELLOW);
		add(ModBlocks.PLASTIC_CUBE_LIME);
		add(ModBlocks.PLASTIC_CUBE_PINK);
		add(ModBlocks.PLASTIC_CUBE_GRAY);
		add(ModBlocks.PLASTIC_CUBE_SILVER);
		add(ModBlocks.PLASTIC_CUBE_CYAN);
		add(ModBlocks.PLASTIC_CUBE_PURPLE);
		add(ModBlocks.PLASTIC_CUBE_BLUE);
		add(ModBlocks.PLASTIC_CUBE_BROWN);
		add(ModBlocks.PLASTIC_CUBE_GREEN);
		add(ModBlocks.PLASTIC_CUBE_RED);
		add(ModBlocks.PLASTIC_CUBE_BLACK);
		add(ModBlocks.PLASTIC_CUBE_GLOWING);

		// Misc Blocks
		add(ModBlocks.WHITE_STONE);
		add(ModBlocks.WHITE_COBBLE);
		add(ModBlocks.WHITE_STONE_BRICKS);
		add(ModBlocks.SMOOTHED_STONE);
		add(ModBlocks.ASTRO_TURF);
		
		// Commercial Kitchen Blocks
		add(ModBlocks.FLOOR_KITCHEN_1);
		add(ModBlocks.FLOOR_KITCHEN_2);
		add(ModBlocks.FLOOR_KITCHEN_3);
		add(ModBlocks.FLOOR_DRAIN_1);
		add(ModBlocks.FLOOR_DRAIN_2);
		add(ModBlocks.FLOOR_DRAIN_3);
		
		// Industrial Blocks
		add(ModBlocks.INDUSTRIAL_TILE_1);
		add(ModBlocks.INDUSTRIAL_TILE_2);
		add(ModBlocks.INDUSTRIAL_TILE_3);
		add(ModBlocks.INDUSTRIAL_TILE_4);
		add(ModBlocks.RELAY_MANHOLE);
		add(ModBlocks.LVN_MANHOLE);
		add(ModBlocks.UTIL_MANHOLE);
		add(ModBlocks.BLANK_MANHOLE);
		add(ModBlocks.MANHOLE_CLOSED);
		add(ModBlocks.UTIL_MANHOLE_CLOSED);
		add(ModBlocks.BLANK_MANHOLE_CLOSED);
		add(ModBlocks.LVN_MANHOLE_CLOSED);
		add(ModBlocks.MANHOLE_LADDER);
		add(ModBlocks.METAL_LADDER);

		// Pillars
		add(ModBlocks.PILLAR_POST);
		add(ModBlocks.PILLAR_STONE);
		add(ModBlocks.PILLAR_OAK);
		add(ModBlocks.PILLAR_BIRCH);
		add(ModBlocks.PILLAR_SPRUCE);
		add(ModBlocks.PILLAR_JUNGLE);
		add(ModBlocks.PILLAR_ACACIA);
		add(ModBlocks.PILLAR_DARK_OAK);
		add(ModBlocks.PILLAR_COBBLESTONE);
		add(ModBlocks.PILLAR_STONEBRICKS);
		add(ModBlocks.PILLAR_WHITE_STONEBRICKS);
		add(ModBlocks.PILLAR_WHITE_STONE);
		add(ModBlocks.PILLAR_WHITE_COBBLESTONE);
		add(ModBlocks.PILLAR_SMOOTHED_STONE);

		// Trusses
		add(ModBlocks.STEEL_TRUSS);
		add(ModBlocks.STEEL_TRUSS_HOWE);
		add(ModBlocks.STEEL_TRUSS_PRATT);
		add(ModBlocks.IRON_TRUSS);
		add(ModBlocks.IRON_TRUSS_PRATT);
		add(ModBlocks.IRON_TRUSS_HOWE);
		add(ModBlocks.NICKEL_TRUSS);
		add(ModBlocks.NICKEL_TRUSS_PRATT);
		add(ModBlocks.NICKEL_TRUSS_HOWE);
		add(ModBlocks.CONSTANTAN_TRUSS);
		add(ModBlocks.CONSTANTAN_TRUSS_PRATT);
		add(ModBlocks.CONSTANTAN_TRUSS_HOWE);

		// Columns. Again, not to be confused with Pillars.
		add(ModBlocks.COLUMN_WHITE_CONCRETE);
		add(ModBlocks.COLUMN_ORANGE_CONCRETE);
		add(ModBlocks.COLUMN_MAGENTA_CONCRETE);
		add(ModBlocks.COLUMN_LBLUE_CONCRETE);
		add(ModBlocks.COLUMN_LIME_CONCRETE);
		add(ModBlocks.COLUMN_PINK_CONCRETE);
		add(ModBlocks.COLUMN_YELLOW_CONCRETE);
		add(ModBlocks.COLUMN_GRAY_CONCRETE);
		add(ModBlocks.COLUMN_SILVER_CONCRETE);
		add(ModBlocks.COLUMN_CYAN_CONCRETE);
		add(ModBlocks.COLUMN_PURPLE_CONCRETE);
		add(ModBlocks.COLUMN_BLUE_CONCRETE);
		add(ModBlocks.COLUMN_BROWN_CONCRETE);
		add(ModBlocks.COLUMN_GREEN_CONCRETE);
		add(ModBlocks.COLUMN_RED_CONCRETE);
		add(ModBlocks.COLUMN_BLACK_CONCRETE);
		add(ModBlocks.COLUMN_GLOWING);

		// Plexiglass
		add(ModBlocks.PLEXIGLASS_0);
		add(ModBlocks.PLEXIGLASS_1);
		add(ModBlocks.PLEXIGLASS_2);
		add(ModBlocks.PLEXIGLASS_3);
		add(ModBlocks.PLEXIGLASS_4);
		add(ModBlocks.PLEXIGLASS_5);
		add(ModBlocks.PLEXIGLASS_6);
		add(ModBlocks.PLEXIGLASS_7);
		add(ModBlocks.PLEXIGLASS_8);
		add(ModBlocks.PLEXIGLASS_9);
		add(ModBlocks.PLEXIGLASS_10);
		add(ModBlocks.PLEXIGLASS_PANE_0);
		add(ModBlocks.PLEXIGLASS_PANE_1);
		add(ModBlocks.PLEXIGLASS_PANE_2);
		add(ModBlocks.PLEXIGLASS_PANE_3);
		add(ModBlocks.PLEXIGLASS_PANE_4);
		add(ModBlocks.PLEXIGLASS_PANE_5);
		add(ModBlocks.PLEXIGLASS_PANE_6);
		add(ModBlocks.PLEXIGLASS_PANE_7);
		add(ModBlocks.PLEXIGLASS_PANE_8);
		add(ModBlocks.PLEXIGLASS_PANE_9);
		add(ModBlocks.PLEXIGLASS_PANE_10);

		// Marker Poles
		add(ModBlocks.MARKER_POLE_ORANGE);
		add(ModBlocks.MARKER_POLE_RED);
		add(ModBlocks.MARKER_POLE_BLUE);
		add(ModBlocks.MARKER_POLE_WOOD_ORANGE);
		add(ModBlocks.MARKER_POLE_WOOD_RED);
		add(ModBlocks.MARKER_POLE_WOOD_BLUE);

		// Vinyl Siding
		add(ModBlocks.SIDING_WHITE);
		add(ModBlocks.SIDING_ORANGE);
		add(ModBlocks.SIDING_MAGENTA);
		add(ModBlocks.SIDING_LBLUE);
		add(ModBlocks.SIDING_YELLOW);
		add(ModBlocks.SIDING_PINK);
		add(ModBlocks.SIDING_LIME);
		add(ModBlocks.SIDING_GRAY);
		add(ModBlocks.SIDING_SILVER);
		add(ModBlocks.SIDING_CYAN);
		add(ModBlocks.SIDING_PURPLE);
		add(ModBlocks.SIDING_BLUE);
		add(ModBlocks.SIDING_BROWN);
		add(ModBlocks.SIDING_GREEN);
		add(ModBlocks.SIDING_RED);
		add(ModBlocks.SIDING_BLACK);
		add(ModBlocks.SIDING_OAK);
		add(ModBlocks.SIDING_SPRUCE);
		add(ModBlocks.SIDING_BIRCH);
		add(ModBlocks.SIDING_JUNGLE);
		add(ModBlocks.SIDING_ACACIA);
		add(ModBlocks.SIDING_DARK_OAK);

		// Colored Blocks
		add(ModBlocks.COLORED_STONE);
		add(ModBlocks.COLORED_COBBLESTONE);

		// Doors
		add(ModBlocks.OAK_PUSH_DOOR);
		add(ModBlocks.SPRUCE_PUSH_DOOR);
		add(ModBlocks.BIRCH_PUSH_DOOR);
		add(ModBlocks.JUNGLE_PUSH_DOOR);
		add(ModBlocks.ACACIA_PUSH_DOOR);
		add(ModBlocks.DARK_OAK_PUSH_DOOR);
	}
}