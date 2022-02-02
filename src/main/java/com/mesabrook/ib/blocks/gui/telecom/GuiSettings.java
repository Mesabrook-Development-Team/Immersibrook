package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiSettings extends GuiPhoneBase {

	public GuiSettings(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		ImageButton lockScreenButton = new ImageButton(0, INNER_X, INNER_Y + 33, INNER_TEX_WIDTH, 26, "btn_lock_screen.png", 324, 52);
		ImageButton personalizeButton = new ImageButton(1, INNER_X, INNER_Y + 59, INNER_TEX_WIDTH, 26, "btn_personalize.png", 324, 52);
		ImageButton aboutButton = new ImageButton(2, INNER_X, INNER_Y + 85, INNER_TEX_WIDTH, 26, "btn_about.png", 324, 52);
		buttonList.addAll(ImmutableList.of(lockScreenButton, personalizeButton, aboutButton));
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		fontRenderer.drawString("Settings", INNER_X + 3, INNER_Y + 20, 0xFFFFFF);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 0)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsLockScreen(phoneStack, hand));
		}
		
		if (button.id == 1)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsPersonalize(phoneStack, hand));
		}
		
		if (button.id == 2)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsAboutPhone(phoneStack, hand));
		}
	}
}
