package rz.mesabrook.wbtc.client.category;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

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
		add(ModBlocks.CUBE_CHEESE);
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
	}

}
