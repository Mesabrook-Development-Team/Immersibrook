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
public class CategoryBlocks extends AbstractCategory {

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
		
		// Misc Blocks
		add(ModBlocks.WHITE_STONE);
		add(ModBlocks.WHITE_COBBLE);
		add(ModBlocks.WHITE_STONE_BRICKS);
		add(ModBlocks.SMOOTHED_STONE);
		add(ModBlocks.ASTRO_TURF);
		
		// Commercial Kitchen Blocks
		add(ModBlocks.FLOOR_KITCHEN_1);
		add(ModBlocks.FLOOR_KITCHEN_2);
		add(ModBlocks.FLOOR_DRAIN_1);
		add(ModBlocks.FLOOR_DRAIN_2);
		
		// Industrial Blocks
		add(ModBlocks.INDUSTRIAL_TILE_1);
		add(ModBlocks.INDUSTRIAL_TILE_2);
		add(ModBlocks.INDUSTRIAL_TILE_3);
		add(ModBlocks.INDUSTRIAL_TILE_4);
		
		// Pillars
		add(ModBlocks.PILLAR_BASE);
		add(ModBlocks.PILLAR_POST);
		add(ModBlocks.PILLAR_TOP);
	}

}
