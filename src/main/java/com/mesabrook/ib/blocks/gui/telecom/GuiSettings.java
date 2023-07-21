package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.util.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;
import java.io.*;

public class GuiSettings extends GuiPhoneBase {

	int aboutBtnX;
	int aboutBtnY;
	public GuiSettings(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "system/app_screen_no_bar.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();

		if(phoneStackData.getIsDebugModeEnabled())
		{
			aboutBtnX = INNER_X + 20;
			aboutBtnY = INNER_Y + 120;
		}
		else
		{
			aboutBtnX = INNER_X + 57;
			aboutBtnY = INNER_Y + 120;
		}

		ImageButton lockScreenButton = new ImageButton(0, INNER_X + 20, INNER_Y + 55, 48, 48, phoneStackData.getIconTheme() + "/btn_lock_screen.png", 32, 32);
		ImageButton personalizeButton = new ImageButton(1, INNER_X + 90, INNER_Y + 55, 48, 48, phoneStackData.getIconTheme() + "/btn_personalize.png", 32, 32);
		ImageButton aboutButton = new ImageButton(2, aboutBtnX, aboutBtnY, 48, 48, phoneStackData.getIconTheme() + "/btn_about.png", 32, 32);
		ImageButton debugButton = new ImageButton(3, INNER_X + 90, aboutBtnY, 48, 48, phoneStackData.getIconTheme() + "/btn_debug.png", 32, 32);

		if(phoneStackData.getIsDebugModeEnabled())
		{
			buttonList.addAll(ImmutableList.of(lockScreenButton, personalizeButton, aboutButton, debugButton));
		}
		else
		{
			buttonList.addAll(ImmutableList.of(lockScreenButton, personalizeButton, aboutButton));
		}
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		drawCenteredString(fontRenderer, new TextComponentTranslation("im.settings.title").getFormattedText(), INNER_X + 80, INNER_Y + 35, 0xFFFFFF);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.securityicon").getFormattedText(), INNER_X + 25, INNER_Y + 103, 0xFFFFFF);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.personalizeicon").getFormattedText(), INNER_X + 85, INNER_Y + 103, 0xFFFFFF);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.abouticon").getFormattedText(), aboutBtnX - 6, INNER_Y + 170, 0xFFFFFF);

		if(phoneStackData.getIsDebugModeEnabled())
		{
			fontRenderer.drawString(new TextComponentTranslation("im.settings.debug").getFormattedText(), INNER_X + 100, INNER_Y + 170, 0xFFFFFF);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		try
		{
			super.actionPerformed(button);
			if (button.id == 0)
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsSecurity(phoneStack, hand));
			}

			if (button.id == 1)
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsPersonalization(phoneStack, hand));
			}

			if (button.id == 2)
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsAboutPhone(phoneStack, hand));
			}

			if(button.id == 3)
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiDebugMenu(phoneStack, hand));
			}
		}
		catch (Exception ex)
		{
			GuiPhoneCrashed crashGui = new GuiPhoneCrashed(phoneStack, hand);

			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter( writer );
			ex.printStackTrace(printWriter);
			printWriter.flush();

			crashGui.setErrorTitle(ex.getMessage());
			crashGui.setErrorStackTrace(writer.toString());

			Minecraft.getMinecraft().displayGuiScreen(crashGui);
		}
	}
}
