package rz.mesabrook.wbtc.client.category;

import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 * 
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */
public class CategoryTrophy extends AbstractCategory 
{
	public CategoryTrophy()
	{
		super("im.filter.awards", new ItemStack(ModBlocks.STATUE_OWO));
	}
	
	@Override
	public void init() 
	{
		// Trophies
		add(ModBlocks.STATUE_OWO);
		add(ModBlocks.STATUE_RZ);
		add(ModBlocks.STATUE_CSX);
		add(ModBlocks.STATUE_TD);
		add(ModBlocks.STATUE_TLZ);
		add(ModBlocks.STATUE_MD);
		add(ModBlocks.STATUE_LW);
		add(ModBlocks.STATUE_SVV);
		add(ModBlocks.STATUE_TWO);
		
		// Plaques
		add(ModBlocks.PLAQUE_DEV);
		add(ModBlocks.PLAQUE_SUPPORTER);
	}

}
