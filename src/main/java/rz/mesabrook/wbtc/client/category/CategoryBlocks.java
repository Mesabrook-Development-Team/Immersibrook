package rz.mesabrook.wbtc.client.category;

import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

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
		
		// Aluminum Block
		add(ModBlocks.CUBE_ALUMINUM);
	}

}
