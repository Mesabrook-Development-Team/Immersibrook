package com.mesabrook.ib.client.category;

import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import net.minecraft.item.ItemStack;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 * 
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */
public class CategoryResources extends AbstractCategory 
{
	public CategoryResources()
	{
		super("im.filter.resources", new ItemStack(ModItems.PLASTIC_ORANGE));
	}
	
	@Override
	public void init() 
	{
		// Raw Plastics
		add(ModItems.RAW_PLASTIC);
		add(ModItems.RAW_PLASTIC_WHITE);
		add(ModItems.RAW_PLASTIC_ORANGE);
		add(ModItems.RAW_PLASTIC_MAGENTA);
		add(ModItems.RAW_PLASTIC_LBLUE);
		add(ModItems.RAW_PLASTIC_YELLOW);
		add(ModItems.RAW_PLASTIC_LIME);
		add(ModItems.RAW_PLASTIC_PINK);
		add(ModItems.RAW_PLASTIC_GRAY);
		add(ModItems.RAW_PLASTIC_SILVER);
		add(ModItems.RAW_PLASTIC_CYAN);
		add(ModItems.RAW_PLASTIC_PURPLE);
		add(ModItems.RAW_PLASTIC_BLUE);
		add(ModItems.RAW_PLASTIC_BROWN);
		add(ModItems.RAW_PLASTIC_GREEN);
		add(ModItems.RAW_PLASTIC_RED);
		add(ModItems.RAW_PLASTIC_BLACK);
		add(ModItems.RAW_PLASTIC_GLOWING);
		
		// Plastic Ingots
		add(ModItems.PLASTIC_WHITE);
		add(ModItems.PLASTIC_ORANGE);
		add(ModItems.PLASTIC_MAGENTA);
		add(ModItems.PLASTIC_LBLUE);
		add(ModItems.PLASTIC_YELLOW);
		add(ModItems.PLASTIC_LIME);
		add(ModItems.PLASTIC_PINK);
		add(ModItems.PLASTIC_GRAY);
		add(ModItems.PLASTIC_SILVER);
		add(ModItems.PLASTIC_CYAN);
		add(ModItems.PLASTIC_PURPLE);
		add(ModItems.PLASTIC_BLUE);
		add(ModItems.BRICK_O_SHIT);
		add(ModItems.PLASTIC_GREEN);
		add(ModItems.PLASTIC_RED);
		add(ModItems.PLASTIC_BLACK);
		add(ModItems.PLASTIC_GLOWING);
		
		// Night Vision Goggle Components
		add(ModItems.NV_BODY);
		add(ModItems.NV_INTERNALS);
		add(ModItems.NV_LENS);
		add(ModItems.NV_STRAPS);

		// Levi Hammer Components
		add(ModItems.LEVI_HAMMER_HEAD);
		add(ModItems.LEVI_HAMMER_STICK);

		// Raw Plastic Blocks
		add(ModBlocks.RAW_PLASTIC_CUBE_WHITE);
		add(ModBlocks.RAW_PLASTIC_CUBE_ORANGE);
		add(ModBlocks.RAW_PLASTIC_CUBE_MAGENTA);
		add(ModBlocks.RAW_PLASTIC_CUBE_LBLUE);
		add(ModBlocks.RAW_PLASTIC_CUBE_YELLOW);
		add(ModBlocks.RAW_PLASTIC_CUBE_LIME);
		add(ModBlocks.RAW_PLASTIC_CUBE_PINK);
		add(ModBlocks.RAW_PLASTIC_CUBE_GRAY);
		add(ModBlocks.RAW_PLASTIC_CUBE_SILVER);
		add(ModBlocks.RAW_PLASTIC_CUBE_CYAN);
		add(ModBlocks.RAW_PLASTIC_CUBE_PURPLE);
		add(ModBlocks.RAW_PLASTIC_CUBE_BLUE);
		add(ModBlocks.RAW_PLASTIC_CUBE_BROWN);
		add(ModBlocks.RAW_PLASTIC_CUBE_GREEN);
		add(ModBlocks.RAW_PLASTIC_CUBE_RED);
		add(ModBlocks.RAW_PLASTIC_CUBE_BLACK);
		add(ModBlocks.RAW_PLASTIC_CUBE_GLOWING);

		// Phone Crafting Components
		add(ModItems.PHONE_SCREEN);
		add(ModItems.PHONE_SHELL_WHITE);
		add(ModItems.PHONE_SHELL_ORANGE);
		add(ModItems.PHONE_SHELL_MAGENTA);
		add(ModItems.PHONE_SHELL_LBLUE);
		add(ModItems.PHONE_SHELL_YELLOW);
		add(ModItems.PHONE_SHELL_LIME);
		add(ModItems.PHONE_SHELL_PINK);
		add(ModItems.PHONE_SHELL_GRAY);
		add(ModItems.PHONE_SHELL_SILVER);
		add(ModItems.PHONE_SHELL_CYAN);
		add(ModItems.PHONE_SHELL_PURPLE);
		add(ModItems.PHONE_SHELL_BLUE);
		add(ModItems.PHONE_SHELL_BROWN);
		add(ModItems.PHONE_SHELL_GREEN);
		add(ModItems.PHONE_SHELL_RED);
		add(ModItems.PHONE_SHELL_BLACK);
		add(ModItems.PHONE_SHELL_SPECIAL);

		// Misc resource Items
		add(ModItems.PAPER_STICK);
		add(ModItems.WINE_BOTTLE);
		add(ModItems.CARDBOARD_PIECE);
		add(ModItems.ROD_CONSTANTAN);
		add(ModItems.ROD_NICKEL);
		add(ModItems.FIRECLOTH_1);
		add(ModItems.FIRECLOTH_2);
		add(ModItems.FIRECLOTH_3);
		add(ModItems.FIRECLOTH_4);
		add(ModItems.REFLECTIVE_WHITE);
		add(ModItems.REFLECTIVE_PINK);
		add(ModItems.REFLECTIVE_GREEN);
		add(ModItems.REFLECTIVE_YELLOW);
		add(ModItems.PLASTIC_PLATE);
		add(ModItems.PLASTIGLASS_SHEET);
		add(ModItems.TAPE_CRAFTING);
		add(ModItems.DENIM);
		add(ModItems.PEGHOOK);
		add(ModItems.ASBESTOS);
		add(ModBlocks.CHRYSOTILE);
		add(ModBlocks.CHRYSOTILE_SMOOTHED);
	}
}
