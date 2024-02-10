package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiEmptyPhone extends GuiPhoneBase {

	public GuiEmptyPhone(ItemStack phoneStack, EnumHand hand)
	{
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "system/app_screen.png";
	}
}
