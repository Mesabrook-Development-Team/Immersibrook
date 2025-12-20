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
		add(ModItems.DISC_O_COME_DIVINE_MESSIAH);
		add(ModItems.DISC_O_COME_O_COME_EMMANUEL);
		add(ModItems.DISC_ALL_CREATURES_OF_OUR_GOD_AND_KING);
		add(ModItems.DISC_GLORY_AND_PRAISE_TO_OUR_GOD);
		add(ModItems.DISC_HAIL_HOLY_QUEEN);
		add(ModItems.DISC_HARK_THE_HERALD_ANGLE);
		add(ModItems.DISC_HOLY_GOD_WE_PRAISE_THY_NAME);
		add(ModItems.DISC_IMMACULATE_MARY);
		add(ModItems.DISC_JESUS_CHRIST_IS_RISEN_TODAY);
		add(ModItems.DISC_JOY_TO_THE_WORLD);
		add(ModItems.DISC_LIFT_HIGH_THE_CROSS);
		add(ModItems.DISC_LORD_WHO_THROUGHOUT_THESE_FORTY_DAYS);
		add(ModItems.DISC_LOTION_IN_THE_GOON_CAVE);
	}

}
