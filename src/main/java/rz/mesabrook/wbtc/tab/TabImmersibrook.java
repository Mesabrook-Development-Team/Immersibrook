package rz.mesabrook.wbtc.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModItems;

import java.time.LocalDate;

public class TabImmersibrook extends CreativeTabs
{
	private String title = "";
	private boolean hoveringButton = false;
    int day = 1;
	int month = 4;
	
	public TabImmersibrook(String label)
	{
		super(label);
        setBackgroundImageName("item_search.png");
	}
	
    @Override
    public ItemStack getTabIconItem()
    {
        if(LocalDate.now().getMonthValue() == month && LocalDate.now().getDayOfMonth() == day)
        {
            return new ItemStack(ModItems.KEKW);
        }
        else
        {
            return new ItemStack(ModItems.IMMERSIBROOK_ICON);
        }
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

    @Override
    public boolean hasSearchBar() {return true;}
}
