package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;

import net.minecraft.item.Item;

public class ItemSecurityBoxedItem extends Item {

	public ItemSecurityBoxedItem()
	{
		setUnlocalizedName("security_box");
        setRegistryName("security_box");
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
	}
}
