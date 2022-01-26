package com.mesabrook.ib.util.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;

public class RecipesHandler
{
	public static void registerCraftingRecipes()
	{

	}

	public static void registerSmeltingRecipes()
	{
		try
		{
			// Plastic Ingots
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_WHITE, new ItemStack(ModItems.PLASTIC_WHITE), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_ORANGE, new ItemStack(ModItems.PLASTIC_ORANGE), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_MAGENTA, new ItemStack(ModItems.PLASTIC_MAGENTA), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_LBLUE, new ItemStack(ModItems.PLASTIC_LBLUE), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_YELLOW, new ItemStack(ModItems.PLASTIC_YELLOW), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_LIME, new ItemStack(ModItems.PLASTIC_LIME), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_PINK, new ItemStack(ModItems.PLASTIC_PINK), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_GRAY, new ItemStack(ModItems.PLASTIC_GRAY), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_SILVER, new ItemStack(ModItems.PLASTIC_SILVER), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_CYAN, new ItemStack(ModItems.PLASTIC_CYAN), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_PURPLE, new ItemStack(ModItems.PLASTIC_PURPLE), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_BLUE, new ItemStack(ModItems.PLASTIC_BLUE), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_BROWN, new ItemStack(ModItems.BRICK_O_SHIT), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_RED, new ItemStack(ModItems.PLASTIC_RED), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_GREEN, new ItemStack(ModItems.PLASTIC_GREEN), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_BLACK, new ItemStack(ModItems.PLASTIC_BLACK), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_GLOWING, new ItemStack(ModItems.PLASTIC_GLOWING), 10);

			// Raw Plastic Blocks
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_WHITE, new ItemStack(ModItems.PLASTIC_WHITE, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_ORANGE, new ItemStack(ModItems.PLASTIC_ORANGE, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_MAGENTA, new ItemStack(ModItems.PLASTIC_MAGENTA, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_LBLUE, new ItemStack(ModItems.PLASTIC_LBLUE, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_YELLOW, new ItemStack(ModItems.PLASTIC_YELLOW, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_LIME, new ItemStack(ModItems.PLASTIC_LIME, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_PINK, new ItemStack(ModItems.PLASTIC_PINK, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_GRAY, new ItemStack(ModItems.PLASTIC_GRAY, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_SILVER, new ItemStack(ModItems.PLASTIC_SILVER, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_CYAN, new ItemStack(ModItems.PLASTIC_CYAN, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_PURPLE, new ItemStack(ModItems.PLASTIC_PURPLE, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_BLUE, new ItemStack(ModItems.PLASTIC_BLUE, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_BROWN, new ItemStack(ModItems.BRICK_O_SHIT, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_GREEN, new ItemStack(ModItems.PLASTIC_GREEN, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_RED, new ItemStack(ModItems.PLASTIC_RED, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_BLACK, new ItemStack(ModItems.PLASTIC_BLACK, 9), 10);
			GameRegistry.addSmelting(ModBlocks.RAW_PLASTIC_CUBE_GLOWING, new ItemStack(ModItems.PLASTIC_GLOWING, 9), 10);

			// Food Cubes
			GameRegistry.addSmelting(ModBlocks.CUBE_BEEF, new ItemStack(Items.COOKED_BEEF, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_CHICKEN, new ItemStack(Items.COOKED_CHICKEN, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_PORK, new ItemStack(Items.COOKED_PORKCHOP, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_MUTTON, new ItemStack(Items.COOKED_MUTTON, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_RABBIT, new ItemStack(Items.COOKED_RABBIT, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_FISH, new ItemStack(Items.COOKED_FISH, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_SALMON, new ItemStack(Items.COOKED_FISH, 9, 1), 10);
			GameRegistry.addSmelting(ModBlocks.CHEESE_TRUSS, new ItemStack(ModItems.FOOD_TRUSS, 1, 0), 69);

			// Discs
			GameRegistry.addSmelting(ModItems.DISC_USSR2, new ItemStack(ModItems.DISC_USSR1, 1), 420F);
			GameRegistry.addSmelting(ModItems.DISC_BOOEY, new ItemStack(ModItems.DISC_FISH, 1), 420F);

			// Candies
			GameRegistry.addSmelting(ModItems.RAW_CANDY_RED, new ItemStack(ModItems.CANDY_RUBY, 1), 69F);
			GameRegistry.addSmelting(ModItems.RAW_CANDY_LIME, new ItemStack(ModItems.CANDY_LIME, 1), 69F);
			GameRegistry.addSmelting(ModItems.RAW_CANDY_BLUE, new ItemStack(ModItems.CANDY_BLUE, 1), 69F);
			GameRegistry.addSmelting(ModItems.RAW_CANDY_ORANGE, new ItemStack(ModItems.CANDY_ORANGE, 1), 69F);
			GameRegistry.addSmelting(ModItems.RAW_CANDY_GRAPE, new ItemStack(ModItems.CANDY_GRAPE, 1), 69F);
			GameRegistry.addSmelting(ModItems.RAW_CANDY_PL, new ItemStack(ModItems.CANDY_PINK_LEMONADE, 1), 69F);
			GameRegistry.addSmelting(ModItems.RAW_CANDY_CHOC, new ItemStack(ModItems.CANDY_CHOCOLATE, 1), 69F);
			GameRegistry.addSmelting(ModItems.RAW_CANDY_RB, new ItemStack(ModItems.CANDY_ROOT_BEER, 1), 69F);

			// Firecloth
			GameRegistry.addSmelting(ModItems.FIRECLOTH_1, new ItemStack(ModItems.FIRECLOTH_2, 1), 10F);
			GameRegistry.addSmelting(ModItems.FIRECLOTH_2, new ItemStack(ModItems.FIRECLOTH_3, 1), 10F);
			GameRegistry.addSmelting(ModItems.FIRECLOTH_3, new ItemStack(ModItems.FIRECLOTH_4, 1), 10F);
			GameRegistry.addSmelting(ModItems.FIRECLOTH_4, new ItemStack(Items.COAL, 2, 1), 0F);

			// Plastiglass Sheet
			GameRegistry.addSmelting(ModItems.PLASTIC_PLATE, new ItemStack(ModItems.PLASTIGLASS_SHEET, 1), 69F);

			if(ModConfig.smeltingLeatherForASaddle)
			{
				GameRegistry.addSmelting(Items.LEATHER, new ItemStack(Items.SADDLE, 1), 24F);
				Main.logger.info("[" + Reference.MODNAME + "] Leather to Saddle Smelting Recipe Registered.");
			}
			else
			{
				Main.logger.info("[" + Reference.MODNAME + "] Leather to Saddle Smelting Recipe Disasbled.");
			}
			
			Main.logger.info("[" + Reference.MODNAME + "] Smelting Recipes Registered.");
		}
		catch(Exception e)
		{
			Main.logger.error("[" + Reference.MODNAME + "] [ERROR] Something went wrong!" + e.getMessage());
			Main.logger.error(e.getMessage());
			Main.logger.error("Ah fuck, I can't believe you've done this.");
			Main.logger.error("Report this bug, please.");

		}
	}
}
