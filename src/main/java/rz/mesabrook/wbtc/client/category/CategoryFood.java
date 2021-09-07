package rz.mesabrook.wbtc.client.category;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;

public class CategoryFood extends AbstractCategory 
{
	
	public CategoryFood()
	{
		super("im.filter.food", new ItemStack(ModBlocks.CUBE_APPLES));
	}

	@Override
	public void init() 
	{
		add(ModBlocks.CUBE_PORK);
		add(ModBlocks.CUBE_BEEF);
		add(ModBlocks.CUBE_CHICKEN);
		add(ModBlocks.CUBE_MUTTON);
		add(ModBlocks.CUBE_RABBIT);
		add(ModBlocks.CUBE_APPLES);
		add(ModBlocks.CUBE_CARROT);
		add(ModBlocks.CUBE_POTATO);
		add(ModBlocks.CUBE_PUMPKIN_PIE);
		add(ModBlocks.CUBE_BEET);
		add(ModBlocks.CUBE_COOKIE);
		add(ModBlocks.CUBE_GAPPLE);
		add(ModBlocks.CUBE_BREAD);
		add(ModBlocks.CUBE_FISH);
		add(ModBlocks.CUBE_SALMON);
		add(ModBlocks.CUBE_PUFF);
		add(ModBlocks.CUBE_CLOWN);
		add(ModBlocks.CUBE_CHEESE);
		add(ModBlocks.CUBE_APPLE_PIE);
		add(ModBlocks.CUBE_STRAWBERRY_PIE);
		add(ModBlocks.CUBE_BLUEBERRY_PIE);
		add(ModBlocks.CUBE_CHERRY_PIE);
		add(ModBlocks.CUBE_SWEETPOTATO_PIE);
		add(ModBlocks.CUBE_KEYLIME_PIE);
		add(ModBlocks.CUBE_RASPBERRY_PIE);
		add(ModBlocks.CUBE_PECAN_PIE);
		add(ModBlocks.CUBE_GOOSEBERRY_PIE);
		add(ModBlocks.CUBE_SPIDER_PIE);
		add(ModBlocks.CUBE_RHUBARB_PIE);
		add(ModBlocks.CUBE_PATREON_PIE);
		add(ModBlocks.CUBE_SLIME_PIE);
		add(ModBlocks.CUBE_MEAT_PIE);
		add(ModBlocks.CUBE_SPINACH_PIE);
		add(ModBlocks.CUBE_MINCE_PIE);
		add(ModBlocks.CUBE_COTTAGE_PIE);
		add(ModBlocks.CUBE_SHEPHERD_PIE);
		add(ModBlocks.CUBE_POT_PIE);
		add(ModItems.CANDY_RUBY);
		add(ModItems.CANDY_LIME);
		add(ModItems.CANDY_BLUE);
		add(ModItems.CANDY_ORANGE);
		add(ModItems.CANDY_GRAPE);
		add(ModItems.CANDY_ROOT_BEER);
		add(ModItems.CANDY_CHOCOLATE);
		add(ModItems.CANDY_PINK_LEMONADE);
		add(ModItems.PINK_LEMONADE_DRINK);
		add(ModItems.SPARKLING_PINK_LEMONADE);
		add(ModItems.LOLIPOP_RED);
		add(ModItems.LOLIPOP_GREEN);
		add(ModItems.LOLIPOP_BLUE);
		add(ModItems.LOLIPOP_ORANGE);
		add(ModItems.LOLIPOP_RB);
		add(ModItems.LOLIPOP_CHOC);
		add(ModItems.LOLIPOP_PL);
		add(ModItems.LOLIPOP_GRAPE);
		add(ModItems.CANDY_CORN);
		add(ModItems.RAVEN_BAR);
		add(ModItems.KLUSS_BAR);
		add(ModItems.SERPENT_BAR);
		add(ModItems.STRAWBERRY_BAR);
		add(ModItems.NUT_BAR);
		add(ModItems.KRISP_BAR);

		// Food Crafting Items
		add(ModItems.DYE_RED);
		add(ModItems.DYE_BLUE);
		add(ModItems.DYE_GREEN);
		add(ModItems.DYE_WHITE);
		add(ModItems.DYE_YELLOW);
		add(ModItems.SUGAR_ORANGE);
		add(ModItems.SUGAR_RED);
		add(ModItems.SUGAR_GREEN);
		add(ModItems.SUGAR_BLUE);
		add(ModItems.SUGAR_PURPLE);
		add(ModItems.SUGAR_BROWN);
		add(ModItems.SUGAR_LIME);
		add(ModItems.SUGAR_PINK);
		add(ModItems.SUGAR_YELLOW);
		add(Items.SUGAR);
		add(ModItems.RAW_CANDY_RED);
		add(ModItems.RAW_CANDY_LIME);
		add(ModItems.RAW_CANDY_BLUE);
		add(ModItems.RAW_CANDY_ORANGE);
		add(ModItems.RAW_CANDY_GRAPE);
		add(ModItems.RAW_CANDY_RB);
		add(ModItems.RAW_CANDY_CHOC);
		add(ModItems.RAW_CANDY_PL);
	}
}
