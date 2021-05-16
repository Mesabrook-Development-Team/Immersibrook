package rz.mesabrook.wbtc.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModItems;

public class TabImmersibrook extends CreativeTabs
{
	private String title = "";
	private boolean hoveringButton = false;
	
	public TabImmersibrook(String label)
	{
		super(label);
	}
	
    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(ModItems.IMMERSIBROOK_ICON);
    }
    
    @Override
    public String getTranslatedTabLabel()
    {
        return hoveringButton ? title : "ctab." + this.getTabLabel();
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setHoveringButton(boolean hoveringButton)
    {
        this.hoveringButton = hoveringButton;
    }
}
