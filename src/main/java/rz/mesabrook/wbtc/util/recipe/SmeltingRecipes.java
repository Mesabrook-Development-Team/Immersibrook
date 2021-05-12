package rz.mesabrook.wbtc.util.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.config.ModConfig;

public class SmeltingRecipes 
{
	public static void registerSmeltingRecipes()
	{
		try
		{
			// Aluminum Ore
			GameRegistry.addSmelting(ModBlocks.ALUMINUM_ORE, new ItemStack(ModItems.ALUMINUM_INGOT), 69);
			
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
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_BROWN, new ItemStack(ModItems.PLASTIC_BROWN), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_RED, new ItemStack(ModItems.PLASTIC_RED), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_GREEN, new ItemStack(ModItems.PLASTIC_GREEN), 10);
			GameRegistry.addSmelting(ModItems.RAW_PLASTIC_BLACK, new ItemStack(ModItems.PLASTIC_BLACK), 10);
			
			// Food Cubes
			GameRegistry.addSmelting(ModBlocks.CUBE_BEEF, new ItemStack(Items.COOKED_BEEF, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_CHICKEN, new ItemStack(Items.COOKED_CHICKEN, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_PORK, new ItemStack(Items.COOKED_PORKCHOP, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_MUTTON, new ItemStack(Items.COOKED_MUTTON, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_RABBIT, new ItemStack(Items.COOKED_RABBIT, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_FISH, new ItemStack(Items.COOKED_FISH, 9), 10);
			GameRegistry.addSmelting(ModBlocks.CUBE_SALMON, new ItemStack(Items.COOKED_FISH, 9, 1), 10);
			
			// Discs
			GameRegistry.addSmelting(ModItems.DISC_USSR2, new ItemStack(ModItems.DISC_USSR1, 1), 420F);
			GameRegistry.addSmelting(ModItems.DISC_BOOEY, new ItemStack(ModItems.DISC_FISH, 1), 420F);
			
			if(ModConfig.smeltingLeatherForASaddle)
			{
				GameRegistry.addSmelting(Items.LEATHER, new ItemStack(Items.SADDLE, 1), 69F);
				Main.logger.info("[" + Reference.MODNAME + "] Leather to Saddle Smelting Recipe Registered.");
			}
			else
			{
				Main.logger.info("[" + Reference.MODNAME + "] Leather to Saddle Smelting Recipe Disasbled.");
			}
			
			Main.logger.info("Smelting Recipes Registered.");
		}
		catch(Exception e)
		{
			Main.logger.error("[ERROR] Something went wrong while registering recipes! " + e.getMessage());
			Main.logger.error(e.getMessage());
			Main.logger.error("Ah fuck, I can't believe you've done this.");
			Main.logger.error("Report this bug, please.");

		}
	}
}
