package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.google.common.collect.ImmutableList;

import com.mesabrook.ib.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiSettings extends GuiPhoneBase {

	public GuiSettings(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen_no_bar.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		ImageButton lockScreenButton = new ImageButton(0, INNER_X + 20, INNER_Y + 55, 48, 48, "btn_lock_screen.png", 32, 32);
		ImageButton personalizeButton = new ImageButton(1, INNER_X + 90, INNER_Y + 55, 48, 48, "btn_personalize.png", 32, 32);
		ImageButton aboutButton = new ImageButton(2, INNER_X + 57, INNER_Y + 120, 48, 48, "btn_about.png", 32, 32);
		buttonList.addAll(ImmutableList.of(lockScreenButton, personalizeButton, aboutButton));
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		//fontRenderer.drawString(new TextComponentTranslation("im.settings.appname").getFormattedText(), INNER_X + 3, INNER_Y + 20, 0xFFFFFF);

		drawCenteredString(fontRenderer, new TextComponentTranslation("im.settings.title").getFormattedText(), INNER_X + 80, INNER_Y + 35, 0xFFFFFF);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.securityicon").getFormattedText(), INNER_X + 25, INNER_Y + 103, 0xFFFFFF);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.personalizeicon").getFormattedText(), INNER_X + 85, INNER_Y + 103, 0xFFFFFF);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.abouticon").getFormattedText(), INNER_X + 50, INNER_Y + 170, 0xFFFFFF);

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
