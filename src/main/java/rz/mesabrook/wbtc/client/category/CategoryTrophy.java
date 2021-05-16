package rz.mesabrook.wbtc.client.category;

import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

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
		
		// Plaques
		add(ModBlocks.PLAQUE_DEV);
		add(ModBlocks.PLAQUE_SUPPORTER);
	}

}
