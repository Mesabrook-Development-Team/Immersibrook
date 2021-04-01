package rz.mesabrook.wbtc.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

public class WBTCTrophyTab extends CreativeTabs
{
	public WBTCTrophyTab(String label)
	{
		super("wbtc_tab_trophy");
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModBlocks.STATUE_OWO);
	}
}
