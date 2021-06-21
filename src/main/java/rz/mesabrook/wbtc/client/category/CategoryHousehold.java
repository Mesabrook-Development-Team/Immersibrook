package rz.mesabrook.wbtc.client.category;

import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 * 
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */
public class CategoryHousehold extends AbstractCategory 
{	
	public CategoryHousehold()
	{
		super("im.filter.household", new ItemStack(ModBlocks.TRASH_BIN));
	}

	@Override
	public void init() 
	{
		// Handrails
		add(ModBlocks.IRON_HANDRAIL);
		add(ModBlocks.WOOD_HANDRAIL);
		
		// Chroma Screens
		add(ModBlocks.CHROMA_BLUE);
		add(ModBlocks.CHROMA_GREEN);
		add(ModBlocks.CHROMA_BLOCK_BLUE);
		add(ModBlocks.CHROMA_BLOCK_GREEN);
		
		// Decorative
		add(ModBlocks.PC_MOUSE);
		add(ModBlocks.PIXEL_BLOCK);
		
		// Signs
		add(ModBlocks.EXIT_SIGN);
		add(ModBlocks.EXIT_SIGN_GREEN);
		add(ModBlocks.DIAG_SIGN_RED);
		add(ModBlocks.DIAG_SIGN_GREEN);
		add(ModBlocks.EXIT_SIGN_LEFT);
		add(ModBlocks.EXIT_SIGN_RIGHT);
		add(ModBlocks.EXIT_SIGN_GREEN_LEFT);
		add(ModBlocks.EXIT_SIGN_GREEN_RIGHT);
		
		// Sign Stand
		add(ModBlocks.SIGN_STAND);
		
		// Bin
		add(ModBlocks.TRASH_BIN);

		// Lights
		add(ModBlocks.CEILING_LIGHT_SPOTLIGHT);
		add(ModBlocks.CEILING_LIGHT_PANEL);

		// Poppers
		add(ModItems.POPPER_RED);
		add(ModItems.POPPER_GREEN);
		add(ModItems.POPPER_BLUE);
	}

}
