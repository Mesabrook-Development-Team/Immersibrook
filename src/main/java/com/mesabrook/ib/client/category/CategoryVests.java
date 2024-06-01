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
public class CategoryVests extends AbstractCategory 
{
	
	public CategoryVests()
	{
		super("im.filter.vests", new ItemStack(ModItems.HARDHAT_BLUE));
	}
	
	@Override
	public void init() 
	{
		add(ModItems.SAFETY_VEST_ORANGE);
		add(ModItems.SAFETY_VEST_ORANGE_NO_X);
		add(ModItems.SAFETY_VEST_YELLOW);
		add(ModItems.SAFETY_VEST_YELLOW_X);
		add(ModItems.SAFETY_VEST_BLUE);
		add(ModItems.SAFETY_VEST_TGES);
		add(ModItems.SAFETY_VEST_RED);
		add(ModItems.SAFETY_VEST_EMS);
		add(ModItems.LBLUE_VEST);
		add(ModItems.LBLUE_VEST_X);
		add(ModItems.NC_VEST);
		add(ModItems.IRW_VEST);
		add(ModItems.SAFETY_VEST_WHITE);
		add(ModItems.SAFETY_VEST_MAGENTA);
		add(ModItems.SAFETY_VEST_LIME);
		add(ModItems.SAFETY_VEST_PINK);
		add(ModItems.SAFETY_VEST_GRAY);
		add(ModItems.SAFETY_VEST_SILVER);
		add(ModItems.SAFETY_VEST_CYAN);
		add(ModItems.SAFETY_VEST_PURPLE);
		add(ModItems.SAFETY_VEST_BROWN);
		add(ModItems.SAFETY_VEST_GREEN);
		add(ModItems.SAFETY_VEST_BLACK);
		add(ModItems.POLICE_VEST);
		add(ModItems.COOLING_VEST);

		// Hard Hats
		add(ModItems.HARDHAT_YELLOW);
		add(ModItems.HARDHAT_WHITE);
		add(ModItems.HARDHAT_ORANGE);
		add(ModItems.HARDHAT_MAGENTA);
		add(ModItems.HARDHAT_LBLUE);
		add(ModItems.HARDHAT_LIME);
		add(ModItems.HARDHAT_PINK);
		add(ModItems.HARDHAT_GRAY);
		add(ModItems.HARDHAT_SILVER);
		add(ModItems.HARDHAT_CYAN);
		add(ModItems.HARDHAT_PURPLE);
		add(ModItems.HARDHAT_BLUE);
		add(ModItems.HARDHAT_BROWN);
		add(ModItems.HARDHAT_GREEN);
		add(ModItems.HARDHAT_RED);
		add(ModItems.HARDHAT_BLACK);
		add(ModItems.POLICE_HELMET);
		add(ModItems.FIRE_HELMET_BLACK);
		add(ModItems.FIRE_HELMET_WHITE);
		add(ModItems.FIRE_HELMET_YELLOW);
		add(ModItems.FIRE_HELMET_RED);
		add(ModItems.FDRC_CHEST);
		add(ModItems.IRFD_CHEST);
		add(ModItems.CBFD_CHEST);
		add(ModItems.AVFD_CHEST);
		add(ModItems.SCFD_CHEST);
		add(ModItems.NPFD_CHEST);
		add(ModItems.FIRE_BOOTS);
		add(ModItems.FF_PANTS_BLACK);
		add(ModItems.FF_PANTS_BLACK_WS);
		add(ModItems.FF_PANTS_DT);
		add(ModItems.FF_PANTS_LT);
		add(ModItems.BLACK_GENERIC);
		add(ModItems.LT_GENERIC);
		add(ModItems.DT_GENERIC);
		add(ModItems.RESPIRATOR);

		// Conductor's Uniforms
		add(ModItems.CU_BL_HAT);
		add(ModItems.CU_BL_VEST);
		add(ModItems.CU_BL_PANTS);
		add(ModItems.CU_BLC_HAT);
		add(ModItems.CU_BLC_VEST);
		add(ModItems.CU_BLC_PANTS);

		// Engineer's Uniform
		add(ModItems.E_HAT);
		add(ModItems.ENGINEERS_OVERALLS);
		
		// More Wearable Items
		add(ModItems.ARMY_CAP);
		add(ModItems.NAVY_CAP);
		add(ModItems.AF_CAP);
		add(ModItems.MC_CAP);
		add(ModItems.SF_CAP);
		add(ModItems.MARSHALS_CAP);
	}
}
