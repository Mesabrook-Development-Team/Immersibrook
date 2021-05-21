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
	}

}
