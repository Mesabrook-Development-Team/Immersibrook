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
		
		// Plastics
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_WHITE);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_ORANGE);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_MAGENTA);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_LBLUE);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_YELLOW);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_LIME);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_PINK);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_GRAY);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_SILVER);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_CYAN);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_PURPLE);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_BLUE);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_BROWN);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_GREEN);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_RED);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_BLACK);
		OreDictionary.registerOre("itemRawPlastic", ModItems.RAW_PLASTIC_GLOWING);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_WHITE);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_ORANGE);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_MAGENTA);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_LBLUE);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_YELLOW);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_LIME);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_PINK);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_GRAY);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_SILVER);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_CYAN);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_PURPLE);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_BLUE);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_BROWN);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_GREEN);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_RED);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_BLACK);
		OreDictionary.registerOre("itemPlastic", ModItems.PLASTIC_GLOWING);
		
		// Food Items/Blocks
		OreDictionary.registerOre("blockCheese", ModBlocks.CUBE_CHEESE);
		OreDictionary.registerOre("foodPinkLemonade", ModItems.PINK_LEMONADE_DRINK);
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
		Main.logger.info(ModItems.RAW_PLASTIC.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_WHITE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_ORANGE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_MAGENTA.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_LBLUE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_YELLOW.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_LIME.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_PINK.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_GRAY.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_SILVER.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_CYAN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_PURPLE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_BLUE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_BROWN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_GREEN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_RED.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.RAW_PLASTIC_BLACK.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic"); 
		Main.logger.info(ModItems.RAW_PLASTIC_GLOWING.getUnlocalizedName() + " has been added to the Ore Dictionary under itemRawPlastic");
		Main.logger.info(ModItems.PLASTIC_WHITE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_ORANGE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_MAGENTA.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_LBLUE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_YELLOW.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_LIME.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_PINK.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_GRAY.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_SILVER.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_CYAN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_PURPLE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_BLUE.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_BROWN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_GREEN.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_RED.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_BLACK.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
		Main.logger.info(ModItems.PLASTIC_GLOWING.getUnlocalizedName() + " has been added to the Ore Dictionary under itemPlastic");
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
	}
}
