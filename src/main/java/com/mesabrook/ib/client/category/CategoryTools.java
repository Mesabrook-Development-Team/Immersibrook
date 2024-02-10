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
public class CategoryTools extends AbstractCategory {

	public CategoryTools()
	{
		super("im.filter.tools", new ItemStack(ModItems.DIAMOND_SOD));
	}
	
	@Override
	public void init() 
	{
		// Sods and Swords
		add(ModItems.WOOD_SOD);
		add(ModItems.STONE_SOD);
		add(ModItems.IRON_SOD);
		add(ModItems.GOLD_SOD);
		add(ModItems.DIAMOND_SOD);
		add(ModItems.ALUMINUM_SOD);
		add(ModItems.ALUMINUM_SWORD);
		add(ModItems.EMERALD_SWORD);
		
		// Specials
		add(ModItems.ZOE_CANE);
		add(ModItems.LEVI_HAMMER);
		add(ModItems.GMOD_HAMMER);
		add(ModItems.GAVEL);
		add(ModItems.GAVEL_SILVER);
		add(ModItems.GAVEL_QUARTZ);

		// First Aid Kit
		add(ModItems.FIRST_AID_KIT);
		
		// Night Vision Goggles
		add(ModItems.NV_GOGGLES);

		// Manhole Hook
		add(ModItems.MANHOLE_HOOK);

		// Tools
		add(ModItems.ICE_CHISEL);
		add(ModItems.FLAGGER_STOP);
		add(ModItems.TAPE_MEASURE);
		add(ModItems.PORTABLE_ENDER_CHEST);

		// Rations
		add(ModItems.ES_RATION);
		add(ModItems.RS_RATION);

		add(ModItems.ITEM_LAUNCHER);
		add(ModItems.PIANO);
	}

}
