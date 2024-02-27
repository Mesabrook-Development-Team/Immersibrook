package com.mesabrook.ib.util.recipe;

import blusunrize.immersiveengineering.api.crafting.*;
import blusunrize.immersiveengineering.common.IEContent;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import com.pam.harvestcraft.item.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesHandler
{
	public static void registerCraftingRecipes() {}

	public static void registerMachineRecipes()
	{
		try
		{
			int outputAmount = 1;

			// White Mushroom > White Dye Dust
			CrusherRecipe.addRecipe(new ItemStack(ModItems.DUST_WHITE, 2), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("harvestcraft", "whitemushroomitem"))), 20);

			// Emergency Water Sachet Bottling Machine Recipe Thing
			FluidStack water = new FluidStack(FluidRegistry.WATER, 1000);
			BottlingMachineRecipe.addRecipe(new ItemStack(ModItems.WATER_SACHET, 1), new ItemStack(ModItems.PLASTIC_SILVER), water);

			// SIM Card crafting
			BlueprintCraftingRecipe.addRecipe("components", new ItemStack(ModItems.SIM_CARD), ModItems.PLASTIC_WHITE, new ItemStack(IEContent.itemMaterial, 1, 27), new ItemStack(IEContent.itemMaterial, 3, 20));

			// Pleather crafting
			BlueprintCraftingRecipe.addRecipe("components", new ItemStack(ModItems.PLEATHER), ModItems.PLASTIC_PLATE, new ItemStack(Items.STRING, 3), new ItemStack(Items.DYE, 1, 8));

			// <color> Plastic Ingot > <color> Raw Plastic Dust.
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_WHITE, outputAmount), new ItemStack(ModItems.PLASTIC_WHITE), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_ORANGE, outputAmount), new ItemStack(ModItems.PLASTIC_ORANGE), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_MAGENTA, outputAmount), new ItemStack(ModItems.PLASTIC_MAGENTA), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_LBLUE, outputAmount), new ItemStack(ModItems.PLASTIC_LBLUE), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_LIME, outputAmount), new ItemStack(ModItems.PLASTIC_LIME), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_PINK, outputAmount), new ItemStack(ModItems.PLASTIC_PINK), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_YELLOW, outputAmount), new ItemStack(ModItems.PLASTIC_YELLOW), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_GRAY, outputAmount), new ItemStack(ModItems.PLASTIC_GRAY), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_SILVER, outputAmount), new ItemStack(ModItems.PLASTIC_SILVER), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_CYAN, outputAmount), new ItemStack(ModItems.PLASTIC_CYAN), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_PURPLE, outputAmount), new ItemStack(ModItems.PLASTIC_PURPLE), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BLUE, outputAmount), new ItemStack(ModItems.PLASTIC_BLUE), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_GREEN, outputAmount), new ItemStack(ModItems.PLASTIC_GREEN), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BROWN, outputAmount), new ItemStack(ModItems.BRICK_O_SHIT), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_RED, outputAmount), new ItemStack(ModItems.PLASTIC_RED), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BLACK, outputAmount), new ItemStack(ModItems.PLASTIC_BLACK), 10);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_GLOWING, outputAmount), new ItemStack(ModItems.PLASTIC_GLOWING), 10);

			// <color> Plastic Block > <color> Raw Plastic Dust.
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_WHITE, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_WHITE), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_ORANGE, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_ORANGE), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_MAGENTA, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_MAGENTA), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_LBLUE, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_LBLUE), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_LIME, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_LIME), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_PINK, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_PINK), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_YELLOW, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_YELLOW), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_GRAY, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_GRAY), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_SILVER, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_SILVER), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_CYAN, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_CYAN), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_PURPLE, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_PURPLE), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BLUE, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_BLUE), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_GREEN, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_GREEN), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BROWN, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_BROWN), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_RED, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_RED), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BLACK, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_BLACK), 20);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_GLOWING, 7), new ItemStack(ModBlocks.PLASTIC_CUBE_GLOWING), 20);
			
			// <color> Plastic Bricks > <color> Raw Plastic Dusts.28
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_WHITE, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 0), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_ORANGE, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 1), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_MAGENTA, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 2), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_LBLUE, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 3), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_LIME, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 5), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_PINK, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 6), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_YELLOW, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 4), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_GRAY, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 7), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_SILVER, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 8), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_CYAN, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 9), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_PURPLE, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 10), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BLUE, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 11), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_GREEN, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 13), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BROWN, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 12), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_RED, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 14), 60);
			CrusherRecipe.addRecipe(new ItemStack(ModItems.RAW_PLASTIC_BLACK, 28), new ItemStack(ModBlocks.PLASTIC_BRICKS, 1, 15), 60);

			// Food Blocks
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.groundbeefItem, 16), new ItemStack(ModBlocks.CUBE_BEEF), 20);
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.groundchickenItem, 16), new ItemStack(ModBlocks.CUBE_CHICKEN), 20);
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.groundporkItem, 16), new ItemStack(ModBlocks.CUBE_PORK), 20);
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.groundfishItem, 16), new ItemStack(ModBlocks.CUBE_FISH), 20);
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.applesauceItem, 7), new ItemStack(ModBlocks.CUBE_APPLES), 20);
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.applesauceItem, 20), new ItemStack(ModBlocks.CUBE_GAPPLE), 20);

			// Food Items
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.groundbeefItem, 2), new ItemStack(Items.BEEF), 10);
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.groundchickenItem, 2), new ItemStack(Items.CHICKEN), 10);
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.groundporkItem, 2), new ItemStack(Items.PORKCHOP), 10);
			CrusherRecipe.addRecipe(new ItemStack(ItemRegistry.groundfishItem, 2), new ItemStack(Items.FISH), 10);
		}
		catch(Exception ex)
		{
			Main.logger.error("[" + Reference.MODNAME + "] ERROR!!! Unable to register one or more Crusher recipes!" + ex);
		}
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

			// Player Steak
			GameRegistry.addSmelting(ModItems.PLAYER_MEAT, new ItemStack(ModItems.PLAYER_MEAT_COOKED, 1), 20F);

			// Bread
			GameRegistry.addSmelting(ItemRegistry.doughItem, new ItemStack(Items.BREAD, 1), 5F);

			// Food
			GameRegistry.addSmelting(ModItems.RAW_CHICKEN_NUGGET, new ItemStack(ModItems.CHICKEN_NUGGET, 1), 5F);
			GameRegistry.addSmelting(Items.BREAD, new ItemStack(ItemRegistry.toastItem, 1), 5F);
			Main.logger.info("[" + Reference.MODNAME + "] Smelting Recipes Registered.");
		}
		catch(Exception e)
		{
			Main.logger.error("[" + Reference.MODNAME + "] [ERROR] Something went wrong!" + e.getMessage());
			e.printStackTrace();
			Main.logger.error("Ah fuck, I can't believe you've done this.");
			Main.logger.error("Report this bug, please.");

		}
	}
}
