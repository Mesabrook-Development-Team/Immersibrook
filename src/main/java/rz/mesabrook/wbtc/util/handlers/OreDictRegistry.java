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
		OreDictionary.registerOre("stickAluminum", ModItems.ALUMINUM_ROD);
		OreDictionary.registerOre("ingotAluminum", ModItems.ALUMINUM_INGOT);
		OreDictionary.registerOre("nuggetAluminum", ModItems.ALUMINUM_NUGGET);
		OreDictionary.registerOre("blockAluminum", ModBlocks.CUBE_ALUMINUM);
		OreDictionary.registerOre("oreAluminum", ModBlocks.ALUMINUM_ORE);
		OreDictionary.registerOre("dustAluminum", ModItems.ALUMINUM_DUST);
		
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
		
		// It's logging time.
		Main.logger.info(ModItems.IRON_ROD.getUnlocalizedName() + " has been added to the Ore Dictionary under stickIron");
		Main.logger.info(ModItems.ALUMINUM_ROD.getUnlocalizedName() + " has been added to the Ore Dictionary under stickAluminum");
		Main.logger.info(ModItems.ALUMINUM_INGOT.getUnlocalizedName() + " has been added to the Ore Dictionary under ingotAluminum");
		Main.logger.info(ModItems.ALUMINUM_NUGGET.getUnlocalizedName() + " has been added to the Ore Dictionary under nuggetAluminum");
		Main.logger.info(ModBlocks.CUBE_ALUMINUM.getUnlocalizedName() + " has been added to the Ore Dictionary under blockAluminum");
		Main.logger.info(ModBlocks.ALUMINUM_ORE.getUnlocalizedName() + " has been added to the Ore Dictionary under oreAluminum");
		Main.logger.info(ModItems.ALUMINUM_DUST.getUnlocalizedName() + " has been added to the Ore Dictionary under dustAluminum");
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
	}
}
