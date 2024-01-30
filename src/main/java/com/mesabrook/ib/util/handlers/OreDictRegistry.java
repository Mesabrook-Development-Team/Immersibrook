package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.Reference;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictRegistry
{
	public static void init()
	{
		try
		{
			// Metals
			OreDictionary.registerOre("stickIron", ModItems.IRON_ROD);
			OreDictionary.registerOre("stickConstantan", ModItems.ROD_CONSTANTAN);
			OreDictionary.registerOre("stickNickel", ModItems.ROD_NICKEL);

			// Pleather
			OreDictionary.registerOre("leather", ModItems.PLEATHER);

			// Plastics
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_WHITE);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_ORANGE);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_MAGENTA);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_LBLUE);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_YELLOW);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_LIME);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_PINK);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_GRAY);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_SILVER);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_CYAN);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_PURPLE);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_BLUE);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_BROWN);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_GREEN);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_RED);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_BLACK);
			OreDictionary.registerOre("listAllRawPlastic", ModItems.RAW_PLASTIC_GLOWING);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_WHITE);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_ORANGE);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_MAGENTA);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_LBLUE);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_YELLOW);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_LIME);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_PINK);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_GRAY);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_SILVER);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_CYAN);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_PURPLE);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_BLUE);
			OreDictionary.registerOre("listAllPlastic", ModItems.BRICK_O_SHIT);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_GREEN);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_RED);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_BLACK);
			OreDictionary.registerOre("listAllPlastic", ModItems.PLASTIC_GLOWING);
			OreDictionary.registerOre("platePlastic", ModItems.PLASTIC_PLATE);
			OreDictionary.registerOre("platePlastiglass", ModItems.PLASTIGLASS_SHEET);
			OreDictionary.registerOre("platePlexiglass", ModItems.PLASTIGLASS_SHEET);
			OreDictionary.registerOre("platePlexiglas", ModItems.PLASTIGLASS_SHEET);

			// Color-coded Plastic Ingot Entries
			OreDictionary.registerOre("itemWhitePlastic", ModItems.PLASTIC_WHITE);
			OreDictionary.registerOre("itemOrangePlastic", ModItems.PLASTIC_ORANGE);
			OreDictionary.registerOre("itemMagentaPlastic", ModItems.PLASTIC_MAGENTA);
			OreDictionary.registerOre("itemLightBluePlastic", ModItems.PLASTIC_LBLUE);
			OreDictionary.registerOre("itemYellowPlastic", ModItems.PLASTIC_YELLOW);
			OreDictionary.registerOre("itemLimePlastic", ModItems.PLASTIC_LIME);
			OreDictionary.registerOre("itemPinkPlastic", ModItems.PLASTIC_PINK);
			OreDictionary.registerOre("itemGrayPlastic", ModItems.PLASTIC_GRAY);
			OreDictionary.registerOre("itemLightGrayPlastic", ModItems.PLASTIC_SILVER);
			OreDictionary.registerOre("itemSilverPlastic", ModItems.PLASTIC_SILVER);
			OreDictionary.registerOre("itemCyanPlastic", ModItems.PLASTIC_CYAN);
			OreDictionary.registerOre("itemPurplePlastic", ModItems.PLASTIC_PURPLE);
			OreDictionary.registerOre("itemBluePlastic", ModItems.PLASTIC_BLUE);
			OreDictionary.registerOre("itemBrownPlastic", ModItems.BRICK_O_SHIT);
			OreDictionary.registerOre("itemGreenPlastic", ModItems.PLASTIC_GREEN);
			OreDictionary.registerOre("itemRedPlastic", ModItems.PLASTIC_RED);
			OreDictionary.registerOre("itemBlackPlastic", ModItems.PLASTIC_BLACK);
			OreDictionary.registerOre("itemGlowstonePlastic", ModItems.PLASTIC_GLOWING);

			// Color-coded Raw Plastic Entries
			OreDictionary.registerOre("itemRawWhitePlastic", ModItems.RAW_PLASTIC_WHITE);
			OreDictionary.registerOre("itemRawOrangePlastic", ModItems.RAW_PLASTIC_ORANGE);
			OreDictionary.registerOre("itemRawMagentaPlastic", ModItems.RAW_PLASTIC_MAGENTA);
			OreDictionary.registerOre("itemRawLightBluePlastic", ModItems.RAW_PLASTIC_LBLUE);
			OreDictionary.registerOre("itemRawYellowPlastic", ModItems.RAW_PLASTIC_YELLOW);
			OreDictionary.registerOre("itemRawLimePlastic", ModItems.RAW_PLASTIC_LIME);
			OreDictionary.registerOre("itemRawPinkPlastic", ModItems.RAW_PLASTIC_PINK);
			OreDictionary.registerOre("itemRawGrayPlastic", ModItems.RAW_PLASTIC_GRAY);
			OreDictionary.registerOre("itemRawLightGrayPlastic", ModItems.RAW_PLASTIC_SILVER);
			OreDictionary.registerOre("itemRawSilverPlastic", ModItems.RAW_PLASTIC_SILVER);
			OreDictionary.registerOre("itemRawCyanPlastic", ModItems.RAW_PLASTIC_CYAN);
			OreDictionary.registerOre("itemRawPurplePlastic", ModItems.RAW_PLASTIC_PURPLE);
			OreDictionary.registerOre("itemRawBluePlastic", ModItems.RAW_PLASTIC_BLUE);
			OreDictionary.registerOre("itemRawBrownPlastic", ModItems.RAW_PLASTIC_BROWN);
			OreDictionary.registerOre("itemRawGreenPlastic", ModItems.RAW_PLASTIC_GREEN);
			OreDictionary.registerOre("itemRawRedPlastic", ModItems.RAW_PLASTIC_RED);
			OreDictionary.registerOre("itemRawBlackPlastic", ModItems.RAW_PLASTIC_BLACK);
			OreDictionary.registerOre("itemRawGlowstonePlastic", ModItems.RAW_PLASTIC_GLOWING);
			OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC);

			// Food Items/Blocks
			OreDictionary.registerOre("blockCheese", ModBlocks.CUBE_CHEESE);
			OreDictionary.registerOre("foodPinkLemonade", ModItems.PINK_LEMONADE_DRINK);
			OreDictionary.registerOre("foodSparklingPinkLemonade", ModItems.SPARKLING_PINK_LEMONADE);
			OreDictionary.registerOre("foodLemonaide", ModItems.PINK_LEMONADE_DRINK);
			OreDictionary.registerOre("foodChocolatetruffle", ModItems.MILK_TRUFFLE);
			OreDictionary.registerOre("foodWhitechocolatetruffle", ModItems.WHITE_TRUFFLE);
			OreDictionary.registerOre("foodWhitechocolatebar", ModItems.WHITE_CHOCOLATE);
			OreDictionary.registerOre("foodWhitechocolate", ModItems.WHITE_CHOCOLATE);
			OreDictionary.registerOre("foodCaramelchocolatetruffle", ModItems.TRUFFLE_CARAMEL);
			OreDictionary.registerOre("foodPeanutbutterchocolatetruffle", ModItems.TRUFFLE_PB);
			OreDictionary.registerOre("foodStrawberrychocolatetruffle", ModItems.TRUFFLE_STRAWBERRY);
			OreDictionary.registerOre("foodBlueberrychocolatetruffle", ModItems.TRUFFLE_WHITE_BB);
			OreDictionary.registerOre("foodGrapechocolatetruffle", ModItems.TRUFFLE_WHITE_GRAPE);

			// Food Dyes
			OreDictionary.registerOre("dyeWhite", ModItems.DYE_WHITE);
			OreDictionary.registerOre("dyeRed", ModItems.DYE_RED);
			OreDictionary.registerOre("dyeGreen", ModItems.DYE_GREEN);
			OreDictionary.registerOre("dyeBlue", ModItems.DYE_BLUE);
			OreDictionary.registerOre("dyeYellow", ModItems.DYE_YELLOW);
			OreDictionary.registerOre("dyeWhite", ModItems.DUST_WHITE);

			// Sugars
			OreDictionary.registerOre("sugar", ModItems.SUGAR_RED);
			OreDictionary.registerOre("sugar", ModItems.SUGAR_GREEN);
			OreDictionary.registerOre("sugar", ModItems.SUGAR_BLUE);
			OreDictionary.registerOre("sugar", ModItems.SUGAR_BROWN);
			OreDictionary.registerOre("sugar", ModItems.SUGAR_LIME);
			OreDictionary.registerOre("sugar", ModItems.SUGAR_PINK);
			OreDictionary.registerOre("sugar", ModItems.SUGAR_PURPLE);
			OreDictionary.registerOre("sugar", ModItems.SUGAR_ORANGE);
			OreDictionary.registerOre("highfructosecornsyrup", ModItems.HFCS);
			OreDictionary.registerOre("cornsyrup", ModItems.HFCS);
			OreDictionary.registerOre("foodCornsyrup", ModItems.HFCS);
			OreDictionary.registerOre("hfcs", ModItems.HFCS);
			OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_RED);
			OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_GREEN);
			OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_BLUE);
			OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_BROWN);
			OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_LIME);
			OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_PINK);
			OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_PURPLE);
			OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_ORANGE);
			OreDictionary.registerOre("listAllsugar", ModItems.HFCS);

			Main.logger.info("[" + Reference.MODNAME + "] Ore Dictionary Entries Registered.");
		}
		catch(Exception ex)
		{
			Main.logger.error("[" + Reference.MODNAME + " ERROR] Ore Dictionary Registration Failed!");
			ex.printStackTrace();
		}
	}
}
