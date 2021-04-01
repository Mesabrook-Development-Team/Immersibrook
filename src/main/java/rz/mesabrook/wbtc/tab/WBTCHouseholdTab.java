package rz.mesabrook.wbtc.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

public class WBTCHouseholdTab extends CreativeTabs
{
	public WBTCHouseholdTab(String label)
	{
		super("wbtc_tab_household");
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModBlocks.TRASH_BIN);
	}
}
