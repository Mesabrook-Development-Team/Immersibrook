package rz.mesabrook.wbtc.client.category;

import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 * 
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */
public class CategoryVests extends AbstractCategory 
{
	
	public CategoryVests()
	{
		super("im.filter.vests", new ItemStack(ModItems.SAFETY_VEST_ORANGE));
	}
	
	@Override
	public void init() 
	{
		add(ModItems.SAFETY_VEST_ORANGE);
		add(ModItems.SAFETY_VEST_ORANGE_NO_X);
		add(ModItems.SAFETY_VEST_YELLOW);
		add(ModItems.SAFETY_VEST_YELLOW_X);
		add(ModItems.SAFETY_VEST_BLUE);
		add(ModItems.SAFETY_VEST_TGES);
		add(ModItems.SAFETY_VEST_RED);
		add(ModItems.SAFETY_VEST_EMS);
		add(ModItems.LVN_VEST);
		add(ModItems.LVN_VEST_X);
		add(ModItems.LVN_VEST_TXT);
		add(ModItems.IRW_VEST);
		add(ModItems.SAFETY_VEST_WHITE);
		add(ModItems.SAFETY_VEST_MAGENTA);
		add(ModItems.SAFETY_VEST_LIME);
		add(ModItems.SAFETY_VEST_PINK);
		add(ModItems.SAFETY_VEST_GRAY);
		add(ModItems.SAFETY_VEST_SILVER);
		add(ModItems.SAFETY_VEST_CYAN);
		add(ModItems.SAFETY_VEST_PURPLE);
		add(ModItems.SAFETY_VEST_BROWN);
		add(ModItems.SAFETY_VEST_GREEN);
		add(ModItems.SAFETY_VEST_BLACK);
	}

}
