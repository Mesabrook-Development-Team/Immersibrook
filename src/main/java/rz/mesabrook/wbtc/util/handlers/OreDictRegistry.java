package rz.mesabrook.wbtc.util.handlers;

import net.minecraftforge.oredict.OreDictionary;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;

public class OreDictRegistry 
{
	public static void addToOD()
	{
		// Metals
		OreDictionary.registerOre("stickIron", ModItems.IRON_ROD);
		OreDictionary.registerOre("stickConstantan", ModItems.ROD_CONSTANTAN);
		OreDictionary.registerOre("stickNickel", ModItems.ROD_NICKEL);

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

		// Food Dyes
		OreDictionary.registerOre("dyeWhite", ModItems.DYE_WHITE);
		OreDictionary.registerOre("dyeRed", ModItems.DYE_RED);
		OreDictionary.registerOre("dyeGreen", ModItems.DYE_GREEN);
		OreDictionary.registerOre("dyeBlue", ModItems.DYE_BLUE);
		OreDictionary.registerOre("dyeYellow", ModItems.DYE_YELLOW);

		// Colored Sugars
		OreDictionary.registerOre("sugar", ModItems.SUGAR_RED);
		OreDictionary.registerOre("sugar", ModItems.SUGAR_GREEN);
		OreDictionary.registerOre("sugar", ModItems.SUGAR_BLUE);
		OreDictionary.registerOre("sugar", ModItems.SUGAR_BROWN);
		OreDictionary.registerOre("sugar", ModItems.SUGAR_LIME);
		OreDictionary.registerOre("sugar", ModItems.SUGAR_PINK);
		OreDictionary.registerOre("sugar", ModItems.SUGAR_PURPLE);
		OreDictionary.registerOre("sugar", ModItems.SUGAR_ORANGE);
		OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_RED);
		OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_GREEN);
		OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_BLUE);
		OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_BROWN);
		OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_LIME);
		OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_PINK);
		OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_PURPLE);
		OreDictionary.registerOre("listAllsugar", ModItems.SUGAR_ORANGE);

		// It's logging time.
		Main.logger.info(ModItems.IRON_ROD.getUnlocalizedName() + " has been added to the Ore Dictionary under stickIron");
		Main.logger.info(ModItems.ROD_CONSTANTAN.getUnlocalizedName() + " has been added to the Ore Dictionary under stickConstantan");
		Main.logger.info(ModItems.ROD_NICKEL.getUnlocalizedName() + " has been added to the Ore Dictionary under stickNickel");
		Main.logger.info(ModItems.RAW_PLASTIC.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_WHITE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawWhitePlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_ORANGE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawOrangePlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_MAGENTA.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawMagentaPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_LBLUE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawLightBluePlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_YELLOW.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawYellowPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_LIME.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawLimePlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_PINK.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawPinkPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_GRAY.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawGrayPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_SILVER.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawSilverPlastic, itemRawLightGrayPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_CYAN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawCyanPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_PURPLE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawPurplePlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_BLUE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawBluePlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_BROWN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawBrownPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_GREEN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawGreenPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_RED.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawRedPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_BLACK.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawBlackPlastic, listAllRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_GLOWING.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic, itemRawGlowstonePlastic, listAllRawPlastic");
		Main.logger.info(ModItems.PLASTIC_WHITE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemWhitePlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_ORANGE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemOrangePlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_MAGENTA.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemMagentaPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_LBLUE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemLightBluePlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_YELLOW.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemYellowPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_LIME.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemLimePlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_PINK.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemPinkPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_GRAY.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemGrayPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_SILVER.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemSilverPlastic, itemLightGrayPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_CYAN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemCyanPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_PURPLE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemPurplePlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_BLUE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemBluePlastic, listAllPlastic");
		Main.logger.info(ModItems.BRICK_O_SHIT.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemBrownPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_GREEN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemGreenPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_RED.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemRedPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_BLACK.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemBlackPlastic, listAllPlastic");
		Main.logger.info(ModItems.PLASTIC_GLOWING.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic, itemGlowstonePlastic, listAllPlastic");
		Main.logger.info(ModBlocks.CUBE_CHEESE.getUnlocalizedName() + " has been added to the Ore Dictionary under blockCheese");
		Main.logger.info(ModItems.DYE_RED.getUnlocalizedName() + "has been added to the Ore Dictionary under dyeRed");
		Main.logger.info(ModItems.DYE_GREEN.getUnlocalizedName() + "has been added to the Ore Dictionary under dyeGreen");
		Main.logger.info(ModItems.DYE_BLUE.getUnlocalizedName() + "has been added to the Ore Dictionary under dyeBlue");
		Main.logger.info(ModItems.DYE_YELLOW.getUnlocalizedName() + "has been added to the Ore Dictionary under dyeYellow");
		Main.logger.info(ModItems.DYE_WHITE.getUnlocalizedName() + "has been added to the Ore Dictionary under dyeWhite");
		Main.logger.info(ModItems.SUGAR_RED.getUnlocalizedName() + "has been added to the Ore Dictionary under sugar, listAllsugar");
		Main.logger.info(ModItems.SUGAR_GREEN.getUnlocalizedName() + "has been added to the Ore Dictionary under sugar, listAllsugar");
		Main.logger.info(ModItems.SUGAR_BLUE.getUnlocalizedName() + "has been added to the Ore Dictionary under sugar, listAllsugar");
		Main.logger.info(ModItems.SUGAR_PURPLE.getUnlocalizedName() + "has been added to the Ore Dictionary under sugar, listAllsugar");
		Main.logger.info(ModItems.SUGAR_PINK.getUnlocalizedName() + "has been added to the Ore Dictionary under sugar, listAllsugar");
		Main.logger.info(ModItems.SUGAR_BROWN.getUnlocalizedName() + "has been added to the Ore Dictionary under sugar, listAllsugar");
		Main.logger.info(ModItems.SUGAR_LIME.getUnlocalizedName() + "has been added to the Ore Dictionary under sugar, listAllsugar");
		Main.logger.info(ModItems.SUGAR_ORANGE.getUnlocalizedName() + "has been added to the Ore Dictionary under sugar, listAllsugar");
		Main.logger.info(ModItems.PINK_LEMONADE_DRINK.getUnlocalizedName() + " has been added to the Ore Dictionary under foodPinkLemonade, foodLemonaide");
		Main.logger.info(ModItems.SPARKLING_PINK_LEMONADE.getUnlocalizedName() + " has been added to the Ore Dictionary under foodSparklingPinkLemonade");
	}
}
