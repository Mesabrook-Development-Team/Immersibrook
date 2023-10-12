package com.mesabrook.ib.client.category;

import com.mesabrook.ib.init.ModItems;
import net.minecraft.item.ItemStack;

/**
 * Original Author: MrCrayfish
 * Adapted by RavenholmZombie for use in Immersibrook.
 * 
 * https://github.com/MrCrayfish
 * https://github.com/RavenholmZombie
 */
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
		add(ModItems.DISC_XP);
		add(ModItems.DISC_SPOOKY);
		add(ModItems.DISC_RITZ);
		add(ModItems.DISC_HL3);
		add(ModItems.DISC_COOKINg);
		add(ModItems.DISC_MEMORY);
		add(ModItems.WIDE_DISC);
	}

}
