package com.mesabrook.ib.items.weapons;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.item.ItemSword;

public class ItemCane extends ItemSword implements IHasModel
{
	public ItemCane(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
