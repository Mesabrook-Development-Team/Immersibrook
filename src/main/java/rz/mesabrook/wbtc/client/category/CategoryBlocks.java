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
		add(ModBlocks.CAT_BLOCK);
		
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
		
		// Pillars
		add(ModBlocks.PILLAR_POST);
		add(ModBlocks.PILLAR_STONE);
		add(ModBlocks.PILLAR_OAK);
		add(ModBlocks.PILLAR_BIRCH);
		add(ModBlocks.PILLAR_SPRUCE);
		add(ModBlocks.PILLAR_JUNGLE);
		add(ModBlocks.PILLAR_ACACIA);
		add(ModBlocks.PILLAR_DARK_OAK);
	}

}
