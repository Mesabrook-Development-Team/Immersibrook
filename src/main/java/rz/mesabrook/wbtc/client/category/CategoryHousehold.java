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

		// Ducks
		add(ModItems.RUBBER_DUCK);
		add(ModItems.RUBBER_DUCK_CYAN);
		add(ModItems.RUBBER_DUCK_LIME);
		add(ModItems.RUBBER_DUCK_BLACK);
		add(ModItems.RUBBER_DUCK_BLUE);
		add(ModItems.RUBBER_DUCK_GREEN);
		add(ModItems.RUBBER_DUCK_EVIL);

		// Boxes
		add(ModBlocks.SHIPPING_BOX_SMALL);
		add(ModBlocks.SHIPPING_BOX_MEDIUM);
		add(ModBlocks.SHIPPING_BOX_LARGE);
		add(ModBlocks.SHIPPING_BOX_CHUNGUS);

		// Collectable Stamps
		add(ModItems.IR_STAMP_1);
		add(ModItems.IR_STAMP_2);
		add(ModItems.IR_STAMP_3);
		add(ModItems.RC_STAMP_1);
		add(ModItems.RC_STAMP_2);
		add(ModItems.RC_STAMP_3);
		add(ModItems.RC_STAMP_4);
		add(ModItems.RC_STAMP_5);
		add(ModItems.RC_STAMP_6);
		add(ModItems.SC_STAMP_1);
		add(ModItems.CB_STAMP_1);
		add(ModItems.CB_STAMP_2);
		add(ModItems.CB_STAMP_3);
		add(ModItems.CB_STAMP_4);
		add(ModItems.AV_STAMP_1);
		add(ModItems.AV_STAMP_2);
		add(ModItems.AV_STAMP_3);
		add(ModItems.AV_STAMP_4);
		add(ModItems.AV_STAMP_5);
		add(ModItems.CL_STAMP_1);
		add(ModItems.CL_STAMP_2);
	}

}
