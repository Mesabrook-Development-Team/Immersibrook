package rz.mesabrook.wbtc.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

public class WBTCTab extends CreativeTabs
{
	public WBTCTab(String label)
	{
		super("wbtc_tab");
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModBlocks.FLOOR_CHECKERBOARD);
	}
}
