package rz.mesabrook.wbtc.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

public class WBTCTabCeiling extends CreativeTabs
{
	public WBTCTabCeiling(String label)
	{
		super("wbtc_tab_ceiling");
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModBlocks.PANEL_CHECKERBOARD);
	}
}
