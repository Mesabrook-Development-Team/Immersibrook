package com.mesabrook.ib.tab;

import com.mesabrook.ib.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

import java.time.LocalDate;

public class TabImmersibrook extends CreativeTabs
{
	private String title = "";
	private boolean hoveringButton = false;
	
	public TabImmersibrook(String label)
	{
		super(label);
        setBackgroundImageName("item_search.png");
	}
	
    @Override
    public ItemStack getTabIconItem()
    {
        if(LocalDate.now().getMonthValue() == 4 && LocalDate.now().getDayOfMonth() == 1)
        {
            return new ItemStack(ModItems.KEKW);
        }
        else if(LocalDate.now().getMonthValue() == 6 && LocalDate.now().getDayOfMonth() == 21)
        {
            return new ItemStack(ModItems.IB_ICON_SUMMER);
        }
        else if(LocalDate.now().getMonthValue() == 10)
        {
            return new ItemStack(ModItems.IB_ICON_OCT);
        }
        else if(LocalDate.now().getMonthValue() == 12)
        {
            return new ItemStack(ModItems.IB_ICON_XMAS);
        }
        else
        {
            return new ItemStack(ModItems.IB_ICON_NEW);
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
