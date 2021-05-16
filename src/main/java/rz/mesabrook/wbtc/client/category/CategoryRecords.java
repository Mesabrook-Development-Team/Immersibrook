package rz.mesabrook.wbtc.client.category;

import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModItems;

public class CategoryRecords extends AbstractCategory 
{
	public CategoryRecords()
	{
		super("im.filter.records", new ItemStack(ModItems.DISC_KRAB_BORG));
	}
	
	@Override
	public void init() 
	{
		// Music Discs
		add(ModItems.BLANK_DISC);
		add(ModItems.DISC_AMALTHEA);
		add(ModItems.DISC_NYAN);
		add(ModItems.DISC_USSR1);
		add(ModItems.DISC_USSR2);
		add(ModItems.DISC_BOOEY);
		add(ModItems.DISC_DOLAN);
		add(ModItems.DISC_MURICA);
		add(ModItems.DISC_PIGSTEP);
		add(ModItems.DISC_KRAB_BORG);
		add(ModItems.DISC_KRAB_BORG_FULL);
		add(ModItems.DISC_FISH);
	}

}
