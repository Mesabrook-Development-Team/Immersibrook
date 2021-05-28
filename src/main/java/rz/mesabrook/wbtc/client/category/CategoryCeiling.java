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
		
		// Commercial Kitchen Tile Panels
		add(ModBlocks.PANEL_KITCHEN_1);
		add(ModBlocks.PANEL_KITCHEN_2);
		add(ModBlocks.PANEL_DRAIN_1);
		add(ModBlocks.PANEL_DRAIN_2);
		
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
	}

}
