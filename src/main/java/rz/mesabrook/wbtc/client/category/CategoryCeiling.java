package rz.mesabrook.wbtc.client.category;

import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 * 
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */
public class CategoryCeiling extends AbstractCategory 
{
	public CategoryCeiling()
	{
		super("im.filter.ceiling", new ItemStack(ModBlocks.PANEL_CHECKERBOARD_OLD));
	}
	
	@Override
	public void init() 
	{
		// Laminate Wood Panels
		add(ModBlocks.CEILING);
		add(ModBlocks.PANEL_OAK_L);
		add(ModBlocks.PANEL_SPRUCE_L);
		add(ModBlocks.PANEL_BIRCH_L);
		add(ModBlocks.PANEL_FLOOR_JUNGLE_L);
		add(ModBlocks.PANEL_ACACIA_L);
		add(ModBlocks.PANEL_DARK_OAK_L);
		
		// Checkerboard Panels
		add(ModBlocks.PANEL_CHECKERBOARD);
		add(ModBlocks.PANEL_CHECKERBOARD_OLD);
		
		// Floor Tile Panels
		add(ModBlocks.PANEL_TILE_1);
		add(ModBlocks.PANEL_TILE_2);
		add(ModBlocks.PANEL_TILE_3);
		add(ModBlocks.PANEL_TILE_4);
		add(ModBlocks.PANEL_TILE_5);
		add(ModBlocks.PANEL_TILE_6);
		add(ModBlocks.PANEL_TILE_7);
		add(ModBlocks.PANEL_TILE_8);
		add(ModBlocks.PANEL_TILE_9);
		add(ModBlocks.PANEL_TILE_10);
		add(ModBlocks.PANEL_TILE_11);
		add(ModBlocks.PANEL_TILE_12);
		add(ModBlocks.PANEL_TILE_13);
		add(ModBlocks.PANEL_TILE_14);
		add(ModBlocks.PANEL_TILE_15);
		add(ModBlocks.PANEL_TILE_16);
		add(ModBlocks.PANEL_TILE_17);
		add(ModBlocks.PANEL_TILE_18);
		add(ModBlocks.PANEL_TILE_19);
		add(ModBlocks.PANEL_TILE_20);
		add(ModBlocks.PANEL_TILE_21);
		add(ModBlocks.PANEL_TILE_22);
		add(ModBlocks.PANEL_TILE_23);
		add(ModBlocks.PANEL_TILE_24);
		add(ModBlocks.PANEL_TILE_25);
		add(ModBlocks.PANEL_TILE_26);
		add(ModBlocks.PANEL_TILE_27);
		add(ModBlocks.PANEL_TILE_28);
		add(ModBlocks.PANEL_TILE_29);
		add(ModBlocks.PANEL_TILE_30);
		add(ModBlocks.PANEL_TILE_31);
		add(ModBlocks.PANEL_TILE_32);
		
		// Commercial Kitchen Tile Panels
		add(ModBlocks.PANEL_KITCHEN_1);
		add(ModBlocks.PANEL_KITCHEN_2);
		add(ModBlocks.PANEL_KITCHEN_3);
		add(ModBlocks.PANEL_DRAIN_1);
		add(ModBlocks.PANEL_DRAIN_2);
		add(ModBlocks.PANEL_DRAIN_3);
		
		// Industrial Tile Panels
		add(ModBlocks.PANEL_DANGER_1);
		add(ModBlocks.PANEL_DANGER_2);
		add(ModBlocks.PANEL_DANGER_3);
		add(ModBlocks.PANEL_DANGER_4);
		
		// Wool Panels
		add(ModBlocks.PANEL_WOOL_WHITE);
		add(ModBlocks.PANEL_WOOL_ORANGE);
		add(ModBlocks.PANEL_WOOL_MAGENTA);
		add(ModBlocks.PANEL_WOOL_LBLUE);
		add(ModBlocks.PANEL_WOOL_YELLOW);
		add(ModBlocks.PANEL_WOOL_LIME);
		add(ModBlocks.PANEL_WOOL_PINK);
		add(ModBlocks.PANEL_WOOL_GRAY);
		add(ModBlocks.PANEL_WOOL_SILVER);
		add(ModBlocks.PANEL_WOOL_CYAN);
		add(ModBlocks.PANEL_WOOL_PURPLE);
		add(ModBlocks.PANEL_WOOL_BLUE);
		add(ModBlocks.PANEL_WOOL_BROWN);
		add(ModBlocks.PANEL_WOOL_GREEN);
		add(ModBlocks.PANEL_WOOL_RED);
		add(ModBlocks.PANEL_WOOL_BLACK);

		/**
		 * Smooth Oak Wood Ceiling Panel Blocks
		 */
		// Laminate Wood Panels
		add(ModBlocks.SO_PANEL_OAK_L);
		add(ModBlocks.SO_PANEL_SPRUCE_L);
		add(ModBlocks.SO_PANEL_BIRCH_L);
		add(ModBlocks.SO_PANEL_FLOOR_JUNGLE_L);
		add(ModBlocks.SO_PANEL_ACACIA_L);
		add(ModBlocks.SO_PANEL_DARK_OAK_L);

		// Checkerboard Panels
		add(ModBlocks.SO_PANEL_CHECKERBOARD);
		add(ModBlocks.SO_PANEL_CHECKERBOARD_OLD);

		// Floor Tile Panels
		add(ModBlocks.SO_PANEL_TILE_1);
		add(ModBlocks.SO_PANEL_TILE_2);
		add(ModBlocks.SO_PANEL_TILE_3);
		add(ModBlocks.SO_PANEL_TILE_4);
		add(ModBlocks.SO_PANEL_TILE_5);
		add(ModBlocks.SO_PANEL_TILE_6);
		add(ModBlocks.SO_PANEL_TILE_7);
		add(ModBlocks.SO_PANEL_TILE_8);
		add(ModBlocks.SO_PANEL_TILE_9);
		add(ModBlocks.SO_PANEL_TILE_10);
		add(ModBlocks.SO_PANEL_TILE_11);
		add(ModBlocks.SO_PANEL_TILE_12);
		add(ModBlocks.SO_PANEL_TILE_13);
		add(ModBlocks.SO_PANEL_TILE_14);
		add(ModBlocks.SO_PANEL_TILE_15);
		add(ModBlocks.SO_PANEL_TILE_16);
		add(ModBlocks.SO_PANEL_TILE_17);
		add(ModBlocks.SO_PANEL_TILE_18);
		add(ModBlocks.SO_PANEL_TILE_19);
		add(ModBlocks.SO_PANEL_TILE_20);
		add(ModBlocks.SO_PANEL_TILE_21);
		add(ModBlocks.SO_PANEL_TILE_22);
		add(ModBlocks.SO_PANEL_TILE_23);
		add(ModBlocks.SO_PANEL_TILE_24);
		add(ModBlocks.SO_PANEL_TILE_25);
		add(ModBlocks.SO_PANEL_TILE_26);
		add(ModBlocks.SO_PANEL_TILE_27);
		add(ModBlocks.SO_PANEL_TILE_28);
		add(ModBlocks.SO_PANEL_TILE_29);
		add(ModBlocks.SO_PANEL_TILE_30);
		add(ModBlocks.SO_PANEL_TILE_31);
		add(ModBlocks.SO_PANEL_TILE_32);

		// Commercial Kitchen Tile Panels
		add(ModBlocks.SO_PANEL_KITCHEN_1);
		add(ModBlocks.SO_PANEL_KITCHEN_2);
		add(ModBlocks.SO_PANEL_KITCHEN_3);
		add(ModBlocks.SO_PANEL_DRAIN_1);
		add(ModBlocks.SO_PANEL_DRAIN_2);
		add(ModBlocks.SO_PANEL_DRAIN_3);

		// Industrial Tile Panels
		add(ModBlocks.SO_PANEL_DANGER_1);
		add(ModBlocks.SO_PANEL_DANGER_2);
		add(ModBlocks.SO_PANEL_DANGER_3);
		add(ModBlocks.SO_PANEL_DANGER_4);

		// Wool Panels
		add(ModBlocks.SO_PANEL_WOOL_WHITE);
		add(ModBlocks.SO_PANEL_WOOL_ORANGE);
		add(ModBlocks.SO_PANEL_WOOL_MAGENTA);
		add(ModBlocks.SO_PANEL_WOOL_LBLUE);
		add(ModBlocks.SO_PANEL_WOOL_YELLOW);
		add(ModBlocks.SO_PANEL_WOOL_LIME);
		add(ModBlocks.SO_PANEL_WOOL_PINK);
		add(ModBlocks.SO_PANEL_WOOL_GRAY);
		add(ModBlocks.SO_PANEL_WOOL_SILVER);
		add(ModBlocks.SO_PANEL_WOOL_CYAN);
		add(ModBlocks.SO_PANEL_WOOL_PURPLE);
		add(ModBlocks.SO_PANEL_WOOL_BLUE);
		add(ModBlocks.SO_PANEL_WOOL_BROWN);
		add(ModBlocks.SO_PANEL_WOOL_GREEN);
		add(ModBlocks.SO_PANEL_WOOL_RED);
		add(ModBlocks.SO_PANEL_WOOL_BLACK);

		/**
		 * Smooth Birch Wood Ceiling Panel Blocks
		 */
		// Laminate Wood Panels
		add(ModBlocks.SB_PANEL_OAK_L);
		add(ModBlocks.SB_PANEL_SPRUCE_L);
		add(ModBlocks.SB_PANEL_BIRCH_L);
		add(ModBlocks.SB_PANEL_FLOOR_JUNGLE_L);
		add(ModBlocks.SB_PANEL_ACACIA_L);
		add(ModBlocks.SB_PANEL_DARK_OAK_L);

		// Checkerboard Panels
		add(ModBlocks.SB_PANEL_CHECKERBOARD);
		add(ModBlocks.SB_PANEL_CHECKERBOARD_OLD);

		// Floor Tile Panels
		add(ModBlocks.SB_PANEL_TILE_1);
		add(ModBlocks.SB_PANEL_TILE_2);
		add(ModBlocks.SB_PANEL_TILE_3);
		add(ModBlocks.SB_PANEL_TILE_4);
		add(ModBlocks.SB_PANEL_TILE_5);
		add(ModBlocks.SB_PANEL_TILE_6);
		add(ModBlocks.SB_PANEL_TILE_7);
		add(ModBlocks.SB_PANEL_TILE_8);
		add(ModBlocks.SB_PANEL_TILE_9);
		add(ModBlocks.SB_PANEL_TILE_10);
		add(ModBlocks.SB_PANEL_TILE_11);
		add(ModBlocks.SB_PANEL_TILE_12);
		add(ModBlocks.SB_PANEL_TILE_13);
		add(ModBlocks.SB_PANEL_TILE_14);
		add(ModBlocks.SB_PANEL_TILE_15);
		add(ModBlocks.SB_PANEL_TILE_16);
		add(ModBlocks.SB_PANEL_TILE_17);
		add(ModBlocks.SB_PANEL_TILE_18);
		add(ModBlocks.SB_PANEL_TILE_19);
		add(ModBlocks.SB_PANEL_TILE_20);
		add(ModBlocks.SB_PANEL_TILE_21);
		add(ModBlocks.SB_PANEL_TILE_22);
		add(ModBlocks.SB_PANEL_TILE_23);
		add(ModBlocks.SB_PANEL_TILE_24);
		add(ModBlocks.SB_PANEL_TILE_25);
		add(ModBlocks.SB_PANEL_TILE_26);
		add(ModBlocks.SB_PANEL_TILE_27);
		add(ModBlocks.SB_PANEL_TILE_28);
		add(ModBlocks.SB_PANEL_TILE_29);
		add(ModBlocks.SB_PANEL_TILE_30);
		add(ModBlocks.SB_PANEL_TILE_31);
		add(ModBlocks.SB_PANEL_TILE_32);

		// Commercial Kitchen Tile Panels
		add(ModBlocks.SB_PANEL_KITCHEN_1);
		add(ModBlocks.SB_PANEL_KITCHEN_2);
		add(ModBlocks.SB_PANEL_KITCHEN_3);
		add(ModBlocks.SB_PANEL_DRAIN_1);
		add(ModBlocks.SB_PANEL_DRAIN_2);
		add(ModBlocks.SB_PANEL_DRAIN_3);

		// Industrial Tile Panels
		add(ModBlocks.SB_PANEL_DANGER_1);
		add(ModBlocks.SB_PANEL_DANGER_2);
		add(ModBlocks.SB_PANEL_DANGER_3);
		add(ModBlocks.SB_PANEL_DANGER_4);

		// Wool Panels
		add(ModBlocks.SB_PANEL_WOOL_WHITE);
		add(ModBlocks.SB_PANEL_WOOL_ORANGE);
		add(ModBlocks.SB_PANEL_WOOL_MAGENTA);
		add(ModBlocks.SB_PANEL_WOOL_LBLUE);
		add(ModBlocks.SB_PANEL_WOOL_YELLOW);
		add(ModBlocks.SB_PANEL_WOOL_LIME);
		add(ModBlocks.SB_PANEL_WOOL_PINK);
		add(ModBlocks.SB_PANEL_WOOL_GRAY);
		add(ModBlocks.SB_PANEL_WOOL_SILVER);
		add(ModBlocks.SB_PANEL_WOOL_CYAN);
		add(ModBlocks.SB_PANEL_WOOL_PURPLE);
		add(ModBlocks.SB_PANEL_WOOL_BLUE);
		add(ModBlocks.SB_PANEL_WOOL_BROWN);
		add(ModBlocks.SB_PANEL_WOOL_GREEN);
		add(ModBlocks.SB_PANEL_WOOL_RED);
		add(ModBlocks.SB_PANEL_WOOL_BLACK);

		/**
		 * Smooth Spruce Wood Ceiling Panel Blocks
		 **/
		// Laminate Wood Panels
		add(ModBlocks.SS_PANEL_OAK_L);
		add(ModBlocks.SS_PANEL_SPRUCE_L);
		add(ModBlocks.SS_PANEL_BIRCH_L);
		add(ModBlocks.SS_PANEL_FLOOR_JUNGLE_L);
		add(ModBlocks.SS_PANEL_ACACIA_L);
		add(ModBlocks.SS_PANEL_DARK_OAK_L);

		// Checkerboard Panels
		add(ModBlocks.SS_PANEL_CHECKERBOARD);
		add(ModBlocks.SS_PANEL_CHECKERBOARD_OLD);

		// Floor Tile Panels
		add(ModBlocks.SS_PANEL_TILE_1);
		add(ModBlocks.SS_PANEL_TILE_2);
		add(ModBlocks.SS_PANEL_TILE_3);
		add(ModBlocks.SS_PANEL_TILE_4);
		add(ModBlocks.SS_PANEL_TILE_5);
		add(ModBlocks.SS_PANEL_TILE_6);
		add(ModBlocks.SS_PANEL_TILE_7);
		add(ModBlocks.SS_PANEL_TILE_8);
		add(ModBlocks.SS_PANEL_TILE_9);
		add(ModBlocks.SS_PANEL_TILE_10);
		add(ModBlocks.SS_PANEL_TILE_11);
		add(ModBlocks.SS_PANEL_TILE_12);
		add(ModBlocks.SS_PANEL_TILE_13);
		add(ModBlocks.SS_PANEL_TILE_14);
		add(ModBlocks.SS_PANEL_TILE_15);
		add(ModBlocks.SS_PANEL_TILE_16);
		add(ModBlocks.SS_PANEL_TILE_17);
		add(ModBlocks.SS_PANEL_TILE_18);
		add(ModBlocks.SS_PANEL_TILE_19);
		add(ModBlocks.SS_PANEL_TILE_20);
		add(ModBlocks.SS_PANEL_TILE_21);
		add(ModBlocks.SS_PANEL_TILE_22);
		add(ModBlocks.SS_PANEL_TILE_23);
		add(ModBlocks.SS_PANEL_TILE_24);
		add(ModBlocks.SS_PANEL_TILE_25);
		add(ModBlocks.SS_PANEL_TILE_26);
		add(ModBlocks.SS_PANEL_TILE_27);
		add(ModBlocks.SS_PANEL_TILE_28);
		add(ModBlocks.SS_PANEL_TILE_29);
		add(ModBlocks.SS_PANEL_TILE_30);
		add(ModBlocks.SS_PANEL_TILE_31);
		add(ModBlocks.SS_PANEL_TILE_32);

		// Commercial Kitchen Tile Panels
		add(ModBlocks.SS_PANEL_KITCHEN_1);
		add(ModBlocks.SS_PANEL_KITCHEN_2);
		add(ModBlocks.SS_PANEL_KITCHEN_3);
		add(ModBlocks.SS_PANEL_DRAIN_1);
		add(ModBlocks.SS_PANEL_DRAIN_2);
		add(ModBlocks.SS_PANEL_DRAIN_3);

		// Industrial Tile Panels
		add(ModBlocks.SS_PANEL_DANGER_1);
		add(ModBlocks.SS_PANEL_DANGER_2);
		add(ModBlocks.SS_PANEL_DANGER_3);
		add(ModBlocks.SS_PANEL_DANGER_4);

		// Wool Panels
		add(ModBlocks.SS_PANEL_WOOL_WHITE);
		add(ModBlocks.SS_PANEL_WOOL_ORANGE);
		add(ModBlocks.SS_PANEL_WOOL_MAGENTA);
		add(ModBlocks.SS_PANEL_WOOL_LBLUE);
		add(ModBlocks.SS_PANEL_WOOL_YELLOW);
		add(ModBlocks.SS_PANEL_WOOL_LIME);
		add(ModBlocks.SS_PANEL_WOOL_PINK);
		add(ModBlocks.SS_PANEL_WOOL_GRAY);
		add(ModBlocks.SS_PANEL_WOOL_SILVER);
		add(ModBlocks.SS_PANEL_WOOL_CYAN);
		add(ModBlocks.SS_PANEL_WOOL_PURPLE);
		add(ModBlocks.SS_PANEL_WOOL_BLUE);
		add(ModBlocks.SS_PANEL_WOOL_BROWN);
		add(ModBlocks.SS_PANEL_WOOL_GREEN);
		add(ModBlocks.SS_PANEL_WOOL_RED);
		add(ModBlocks.SS_PANEL_WOOL_BLACK);

		/**
		 * Smooth Jungle Wood Ceiling Panel Blocks
		 */
		// Laminate Wood Panels
		add(ModBlocks.SJ_PANEL_OAK_L);
		add(ModBlocks.SJ_PANEL_SPRUCE_L);
		add(ModBlocks.SJ_PANEL_BIRCH_L);
		add(ModBlocks.SJ_PANEL_FLOOR_JUNGLE_L);
		add(ModBlocks.SJ_PANEL_ACACIA_L);
		add(ModBlocks.SJ_PANEL_DARK_OAK_L);

		// Checkerboard Panels
		add(ModBlocks.SJ_PANEL_CHECKERBOARD);
		add(ModBlocks.SJ_PANEL_CHECKERBOARD_OLD);

		// Floor Tile Panels
		add(ModBlocks.SJ_PANEL_TILE_1);
		add(ModBlocks.SJ_PANEL_TILE_2);
		add(ModBlocks.SJ_PANEL_TILE_3);
		add(ModBlocks.SJ_PANEL_TILE_4);
		add(ModBlocks.SJ_PANEL_TILE_5);
		add(ModBlocks.SJ_PANEL_TILE_6);
		add(ModBlocks.SJ_PANEL_TILE_7);
		add(ModBlocks.SJ_PANEL_TILE_8);
		add(ModBlocks.SJ_PANEL_TILE_9);
		add(ModBlocks.SJ_PANEL_TILE_10);
		add(ModBlocks.SJ_PANEL_TILE_11);
		add(ModBlocks.SJ_PANEL_TILE_12);
		add(ModBlocks.SJ_PANEL_TILE_13);
		add(ModBlocks.SJ_PANEL_TILE_14);
		add(ModBlocks.SJ_PANEL_TILE_15);
		add(ModBlocks.SJ_PANEL_TILE_16);
		add(ModBlocks.SJ_PANEL_TILE_17);
		add(ModBlocks.SJ_PANEL_TILE_18);
		add(ModBlocks.SJ_PANEL_TILE_19);
		add(ModBlocks.SJ_PANEL_TILE_20);
		add(ModBlocks.SJ_PANEL_TILE_21);
		add(ModBlocks.SJ_PANEL_TILE_22);
		add(ModBlocks.SJ_PANEL_TILE_23);
		add(ModBlocks.SJ_PANEL_TILE_24);
		add(ModBlocks.SJ_PANEL_TILE_25);
		add(ModBlocks.SJ_PANEL_TILE_26);
		add(ModBlocks.SJ_PANEL_TILE_27);
		add(ModBlocks.SJ_PANEL_TILE_28);
		add(ModBlocks.SJ_PANEL_TILE_29);
		add(ModBlocks.SJ_PANEL_TILE_30);
		add(ModBlocks.SJ_PANEL_TILE_31);
		add(ModBlocks.SJ_PANEL_TILE_32);

		// Commercial Kitchen Tile Panels
		add(ModBlocks.SJ_PANEL_KITCHEN_1);
		add(ModBlocks.SJ_PANEL_KITCHEN_2);
		add(ModBlocks.SJ_PANEL_KITCHEN_3);
		add(ModBlocks.SJ_PANEL_DRAIN_1);
		add(ModBlocks.SJ_PANEL_DRAIN_2);
		add(ModBlocks.SJ_PANEL_DRAIN_3);

		// Industrial Tile Panels
		add(ModBlocks.SJ_PANEL_DANGER_1);
		add(ModBlocks.SJ_PANEL_DANGER_2);
		add(ModBlocks.SJ_PANEL_DANGER_3);
		add(ModBlocks.SJ_PANEL_DANGER_4);

		// Wool Panels
		add(ModBlocks.SJ_PANEL_WOOL_WHITE);
		add(ModBlocks.SJ_PANEL_WOOL_ORANGE);
		add(ModBlocks.SJ_PANEL_WOOL_MAGENTA);
		add(ModBlocks.SJ_PANEL_WOOL_LBLUE);
		add(ModBlocks.SJ_PANEL_WOOL_YELLOW);
		add(ModBlocks.SJ_PANEL_WOOL_LIME);
		add(ModBlocks.SJ_PANEL_WOOL_PINK);
		add(ModBlocks.SJ_PANEL_WOOL_GRAY);
		add(ModBlocks.SJ_PANEL_WOOL_SILVER);
		add(ModBlocks.SJ_PANEL_WOOL_CYAN);
		add(ModBlocks.SJ_PANEL_WOOL_PURPLE);
		add(ModBlocks.SJ_PANEL_WOOL_BLUE);
		add(ModBlocks.SJ_PANEL_WOOL_BROWN);
		add(ModBlocks.SJ_PANEL_WOOL_GREEN);
		add(ModBlocks.SJ_PANEL_WOOL_RED);
		add(ModBlocks.SJ_PANEL_WOOL_BLACK);

		/**
		 * Smooth Acacia Wood Ceiling Panel Blocks
		 */
		// Laminate Wood Panels
		add(ModBlocks.SA_PANEL_OAK_L);
		add(ModBlocks.SA_PANEL_SPRUCE_L);
		add(ModBlocks.SA_PANEL_BIRCH_L);
		add(ModBlocks.SA_PANEL_FLOOR_JUNGLE_L);
		add(ModBlocks.SA_PANEL_ACACIA_L);
		add(ModBlocks.SA_PANEL_DARK_OAK_L);

		// Checkerboard Panels
		add(ModBlocks.SA_PANEL_CHECKERBOARD);
		add(ModBlocks.SA_PANEL_CHECKERBOARD_OLD);

		// Floor Tile Panels
		add(ModBlocks.SA_PANEL_TILE_1);
		add(ModBlocks.SA_PANEL_TILE_2);
		add(ModBlocks.SA_PANEL_TILE_3);
		add(ModBlocks.SA_PANEL_TILE_4);
		add(ModBlocks.SA_PANEL_TILE_5);
		add(ModBlocks.SA_PANEL_TILE_6);
		add(ModBlocks.SA_PANEL_TILE_7);
		add(ModBlocks.SA_PANEL_TILE_8);
		add(ModBlocks.SA_PANEL_TILE_9);
		add(ModBlocks.SA_PANEL_TILE_10);
		add(ModBlocks.SA_PANEL_TILE_11);
		add(ModBlocks.SA_PANEL_TILE_12);
		add(ModBlocks.SA_PANEL_TILE_13);
		add(ModBlocks.SA_PANEL_TILE_14);
		add(ModBlocks.SA_PANEL_TILE_15);
		add(ModBlocks.SA_PANEL_TILE_16);
		add(ModBlocks.SA_PANEL_TILE_17);
		add(ModBlocks.SA_PANEL_TILE_18);
		add(ModBlocks.SA_PANEL_TILE_19);
		add(ModBlocks.SA_PANEL_TILE_20);
		add(ModBlocks.SA_PANEL_TILE_21);
		add(ModBlocks.SA_PANEL_TILE_22);
		add(ModBlocks.SA_PANEL_TILE_23);
		add(ModBlocks.SA_PANEL_TILE_24);
		add(ModBlocks.SA_PANEL_TILE_25);
		add(ModBlocks.SA_PANEL_TILE_26);
		add(ModBlocks.SA_PANEL_TILE_27);
		add(ModBlocks.SA_PANEL_TILE_28);
		add(ModBlocks.SA_PANEL_TILE_29);
		add(ModBlocks.SA_PANEL_TILE_30);
		add(ModBlocks.SA_PANEL_TILE_31);
		add(ModBlocks.SA_PANEL_TILE_32);

		// Commercial Kitchen Tile Panels
		add(ModBlocks.SA_PANEL_KITCHEN_1);
		add(ModBlocks.SA_PANEL_KITCHEN_2);
		add(ModBlocks.SA_PANEL_KITCHEN_3);
		add(ModBlocks.SA_PANEL_DRAIN_1);
		add(ModBlocks.SA_PANEL_DRAIN_2);
		add(ModBlocks.SA_PANEL_DRAIN_3);

		// Industrial Tile Panels
		add(ModBlocks.SA_PANEL_DANGER_1);
		add(ModBlocks.SA_PANEL_DANGER_2);
		add(ModBlocks.SA_PANEL_DANGER_3);
		add(ModBlocks.SA_PANEL_DANGER_4);

		// Wool Panels
		add(ModBlocks.SA_PANEL_WOOL_WHITE);
		add(ModBlocks.SA_PANEL_WOOL_ORANGE);
		add(ModBlocks.SA_PANEL_WOOL_MAGENTA);
		add(ModBlocks.SA_PANEL_WOOL_LBLUE);
		add(ModBlocks.SA_PANEL_WOOL_YELLOW);
		add(ModBlocks.SA_PANEL_WOOL_LIME);
		add(ModBlocks.SA_PANEL_WOOL_PINK);
		add(ModBlocks.SA_PANEL_WOOL_GRAY);
		add(ModBlocks.SA_PANEL_WOOL_SILVER);
		add(ModBlocks.SA_PANEL_WOOL_CYAN);
		add(ModBlocks.SA_PANEL_WOOL_PURPLE);
		add(ModBlocks.SA_PANEL_WOOL_BLUE);
		add(ModBlocks.SA_PANEL_WOOL_BROWN);
		add(ModBlocks.SA_PANEL_WOOL_GREEN);
		add(ModBlocks.SA_PANEL_WOOL_RED);
		add(ModBlocks.SA_PANEL_WOOL_BLACK);

		/**
		 * Smooth Dark Oak Wood Ceiling Panel Blocks
		 */
		// Laminate Wood Panels
		add(ModBlocks.SDO_PANEL_OAK_L);
		add(ModBlocks.SDO_PANEL_SPRUCE_L);
		add(ModBlocks.SDO_PANEL_BIRCH_L);
		add(ModBlocks.SDO_PANEL_FLOOR_JUNGLE_L);
		add(ModBlocks.SDO_PANEL_ACACIA_L);
		add(ModBlocks.SDO_PANEL_DARK_OAK_L);

		// Checkerboard Panels
		add(ModBlocks.SDO_PANEL_CHECKERBOARD);
		add(ModBlocks.SDO_PANEL_CHECKERBOARD_OLD);

		// Floor Tile Panels
		add(ModBlocks.SDO_PANEL_TILE_1);
		add(ModBlocks.SDO_PANEL_TILE_2);
		add(ModBlocks.SDO_PANEL_TILE_3);
		add(ModBlocks.SDO_PANEL_TILE_4);
		add(ModBlocks.SDO_PANEL_TILE_5);
		add(ModBlocks.SDO_PANEL_TILE_6);
		add(ModBlocks.SDO_PANEL_TILE_7);
		add(ModBlocks.SDO_PANEL_TILE_8);
		add(ModBlocks.SDO_PANEL_TILE_9);
		add(ModBlocks.SDO_PANEL_TILE_10);
		add(ModBlocks.SDO_PANEL_TILE_11);
		add(ModBlocks.SDO_PANEL_TILE_12);
		add(ModBlocks.SDO_PANEL_TILE_13);
		add(ModBlocks.SDO_PANEL_TILE_14);
		add(ModBlocks.SDO_PANEL_TILE_15);
		add(ModBlocks.SDO_PANEL_TILE_16);
		add(ModBlocks.SDO_PANEL_TILE_17);
		add(ModBlocks.SDO_PANEL_TILE_18);
		add(ModBlocks.SDO_PANEL_TILE_19);
		add(ModBlocks.SDO_PANEL_TILE_20);
		add(ModBlocks.SDO_PANEL_TILE_21);
		add(ModBlocks.SDO_PANEL_TILE_22);
		add(ModBlocks.SDO_PANEL_TILE_23);
		add(ModBlocks.SDO_PANEL_TILE_24);
		add(ModBlocks.SDO_PANEL_TILE_25);
		add(ModBlocks.SDO_PANEL_TILE_26);
		add(ModBlocks.SDO_PANEL_TILE_27);
		add(ModBlocks.SDO_PANEL_TILE_28);
		add(ModBlocks.SDO_PANEL_TILE_29);
		add(ModBlocks.SDO_PANEL_TILE_30);
		add(ModBlocks.SDO_PANEL_TILE_31);
		add(ModBlocks.SDO_PANEL_TILE_32);

		// Commercial Kitchen Tile Panels
		add(ModBlocks.SDO_PANEL_KITCHEN_1);
		add(ModBlocks.SDO_PANEL_KITCHEN_2);
		add(ModBlocks.SDO_PANEL_KITCHEN_3);
		add(ModBlocks.SDO_PANEL_DRAIN_1);
		add(ModBlocks.SDO_PANEL_DRAIN_2);
		add(ModBlocks.SDO_PANEL_DRAIN_3);

		// Industrial Tile Panels
		add(ModBlocks.SDO_PANEL_DANGER_1);
		add(ModBlocks.SDO_PANEL_DANGER_2);
		add(ModBlocks.SDO_PANEL_DANGER_3);
		add(ModBlocks.SDO_PANEL_DANGER_4);

		// Wool Panels
		add(ModBlocks.SDO_PANEL_WOOL_WHITE);
		add(ModBlocks.SDO_PANEL_WOOL_ORANGE);
		add(ModBlocks.SDO_PANEL_WOOL_MAGENTA);
		add(ModBlocks.SDO_PANEL_WOOL_LBLUE);
		add(ModBlocks.SDO_PANEL_WOOL_YELLOW);
		add(ModBlocks.SDO_PANEL_WOOL_LIME);
		add(ModBlocks.SDO_PANEL_WOOL_PINK);
		add(ModBlocks.SDO_PANEL_WOOL_GRAY);
		add(ModBlocks.SDO_PANEL_WOOL_SILVER);
		add(ModBlocks.SDO_PANEL_WOOL_CYAN);
		add(ModBlocks.SDO_PANEL_WOOL_PURPLE);
		add(ModBlocks.SDO_PANEL_WOOL_BLUE);
		add(ModBlocks.SDO_PANEL_WOOL_BROWN);
		add(ModBlocks.SDO_PANEL_WOOL_GREEN);
		add(ModBlocks.SDO_PANEL_WOOL_RED);
		add(ModBlocks.SDO_PANEL_WOOL_BLACK);
	}

}
