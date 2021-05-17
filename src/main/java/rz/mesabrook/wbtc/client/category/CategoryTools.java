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
public class CategoryTools extends AbstractCategory {

	public CategoryTools()
	{
		super("im.filter.tools", new ItemStack(ModItems.DIAMOND_SOD));
	}
	
	@Override
	public void init() 
	{
		// Sods
		add(ModItems.WOOD_SOD);
		add(ModItems.STONE_SOD);
		add(ModItems.IRON_SOD);
		add(ModItems.GOLD_SOD);
		add(ModItems.DIAMOND_SOD);
		
		// Cane
		add(ModItems.ZOE_CANE);
		
		// First Aid Kit
		add(ModItems.FIRST_AID_KIT);
		
		// Safety Vests
		add(ModItems.SAFETY_VEST_ORANGE);
	}

}
