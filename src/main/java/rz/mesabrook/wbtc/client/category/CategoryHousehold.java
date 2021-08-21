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
		add(ModBlocks.TRASH_BIN_GREEN);
		add(ModBlocks.TRASH_BIN_BLUE);
		add(ModBlocks.TRASH_BIN_RED);

		// Lights
		add(ModBlocks.CEILING_LIGHT_SPOTLIGHT);
		add(ModBlocks.CEILING_LIGHT_PANEL);
		add(ModBlocks.CEILING_LIGHT_LARGE);
		add(ModBlocks.CEILING_LIGHT_SLIM);

		// Toys
		add(ModItems.POPPER_RED);
		add(ModItems.POPPER_GREEN);
		add(ModItems.POPPER_BLUE);
		add(ModItems.IMMERSIBROOK_ICON);
		add(ModItems.DOOTER);
		add(ModItems.MASK_JASON);
		add(ModItems.MASK_SKELETON);
		add(ModItems.MASK_MEDICAL);
		add(ModItems.MASK_SLIME);

		// Warp Metro
		add(ModBlocks.TICKET_MACHINE);

		// Comms
		add(ModItems.RADIO);
		add(ModItems.PHONE_WHITE);
		add(ModItems.PHONE_ORANGE);
		add(ModItems.PHONE_MAGENTA);
		add(ModItems.PHONE_LBLUE);
		add(ModItems.PHONE_YELLOW);
		add(ModItems.PHONE_LIME);
		add(ModItems.PHONE_PINK);
		add(ModItems.PHONE_GRAY);
		add(ModItems.PHONE_SILVER);
		add(ModItems.PHONE_CYAN);
		add(ModItems.PHONE_BLUE);
		add(ModItems.PHONE_BROWN);
		add(ModItems.PHONE_GREEN);
		add(ModItems.PHONE_PURPLE);
		add(ModItems.PHONE_RED);
		add(ModItems.PHONE_BLACK);
		add(ModItems.PHONE_SPECIAL);
		add(ModItems.RUBBER_DUCK);
		add(ModItems.RUBBER_DUCK_CYAN);
		add(ModItems.RUBBER_DUCK_LIME);
		add(ModItems.RUBBER_DUCK_BLACK);
		add(ModItems.RUBBER_DUCK_BLUE);
		add(ModItems.RUBBER_DUCK_GREEN);
		add(ModItems.RUBBER_DUCK_EVIL);
	}

}
