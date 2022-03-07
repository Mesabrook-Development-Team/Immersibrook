package com.mesabrook.ib.items.record;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public class MemeRecord extends ItemRecord implements IHasModel
{
	public MemeRecord(String name, SoundEvent soundIn) 
	{
		super(name, soundIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.IMMERSIBROOK_MAIN);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);	
	}
}
