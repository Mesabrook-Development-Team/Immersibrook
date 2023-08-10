package com.mesabrook.ib.items.commerce;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.item.Item;

public class ItemSecurityBox extends Item implements IHasModel {
	
	public ItemSecurityBox()
	{
		super();
		setUnlocalizedName("security_box");
        setRegistryName("security_box");
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0);
	}

}
