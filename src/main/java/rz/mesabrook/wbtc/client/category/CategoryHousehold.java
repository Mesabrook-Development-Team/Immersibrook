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
		
		// Decorative
		add(ModBlocks.PC_MOUSE);
		
		// Signs
		add(ModBlocks.EXIT_SIGN);
		add(ModBlocks.EXIT_SIGN_GREEN);
		
		// Sign Stand
		add(ModBlocks.SIGN_STAND);
		
		// Bin
		add(ModBlocks.TRASH_BIN);

		// Lights
		add(ModBlocks.CEILING_LIGHT_SPOTLIGHT);
		add(ModBlocks.CEILING_LIGHT_PANEL);
	}

}
