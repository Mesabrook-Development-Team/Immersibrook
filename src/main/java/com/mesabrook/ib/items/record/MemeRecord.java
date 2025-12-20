package com.mesabrook.ib.items.record;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
		if(stack.getItem() == ModItems.DISC_O_COME_DIVINE_MESSIAH) return true;
		if(stack.getItem() == ModItems.DISC_O_COME_O_COME_EMMANUEL) return true;
		if(stack.getItem() == ModItems.DISC_ALL_CREATURES_OF_OUR_GOD_AND_KING) return true;
		if(stack.getItem() == ModItems.DISC_GLORY_AND_PRAISE_TO_OUR_GOD) return true;
		if(stack.getItem() == ModItems.DISC_HAIL_HOLY_QUEEN) return true;
		if(stack.getItem() == ModItems.DISC_HARK_THE_HERALD_ANGLE) return true;
		if(stack.getItem() == ModItems.DISC_HOLY_GOD_WE_PRAISE_THY_NAME) return true;
		if(stack.getItem() == ModItems.DISC_IMMACULATE_MARY) return true;
		if(stack.getItem() == ModItems.DISC_JESUS_CHRIST_IS_RISEN_TODAY) return true;
		if(stack.getItem() == ModItems.DISC_JOY_TO_THE_WORLD) return true;
		if(stack.getItem() == ModItems.DISC_LIFT_HIGH_THE_CROSS) return true;
		if(stack.getItem() == ModItems.DISC_LORD_WHO_THROUGHOUT_THESE_FORTY_DAYS) return true;
		else return false;
    }
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0);	
	}
}
