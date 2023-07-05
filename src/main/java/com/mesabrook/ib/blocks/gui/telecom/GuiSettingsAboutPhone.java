package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.telecom.*;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class GuiSettingsAboutPhone extends GuiPhoneBase {

	LabelButton back;
	LabelButton number;
	MinedroidButton factoryReset;
	ImageButton mux;
	int clicksToDebug = 0;
	private boolean factoryResetPressed;

	public GuiSettingsAboutPhone(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}
	
	@Override
	protected String getInnerTextureFileName() {
		return "system/app_screen.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int stringWidth = fontRenderer.getStringWidth(new TextComponentTranslation("im.contacts.phone").getFormattedText());

		back = new LabelButton(4, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);
		number = new LabelButton(5, INNER_X + 3 + stringWidth + 3, INNER_Y + 118, getFormattedPhoneNumber(phoneStackData.getPhoneNumberString()), 0xFFFFFF);

		factoryReset = new MinedroidButton(1, INNER_X + 3, INNER_Y + 160, INNER_TEX_WIDTH - 6, "Factory Reset", 0xFF5733);

		mux = new ImageButton(100, INNER_X + 67, INNER_Y + 45, 28, 28, "icn_mux.png", 32, 32);

		buttonList.add(back);
		buttonList.add(number);
		buttonList.add(mux);
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		fontRenderer.drawString("About Phone", INNER_X + 15, INNER_Y + 20, 0xFFFFFF);

		drawCenteredString(fontRenderer, new TextComponentTranslation("im.minedroid").getFormattedText() + " " + Reference.MINEDROID_VERSION, INNER_X + 80, INNER_Y + 80, 3395327);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.phoneinfo").getFormattedText(), INNER_X + 3, INNER_Y + 106, 0xFFFFFF);
		fontRenderer.drawString(new TextComponentTranslation("im.contacts.phone").getFormattedText(), INNER_X + 3, INNER_Y + 118, 0x5179bd);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.strategy").getFormattedText(), INNER_X + 3, INNER_Y + 131, 0x5179bd);
		int stratStringWidth = fontRenderer.getStringWidth(new TextComponentTranslation("im.settings.strategy").getFormattedText());
		fontRenderer.drawString(phoneStackData.getSecurityStrategy().toString(), INNER_X + 3 + stratStringWidth + 3, INNER_Y + 131, 0xFFFFFF);

		GlStateManager.color(1, 1, 1);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == back)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettings(phoneStack, hand));
		}

		if(button == number)
		{
			StringSelection stringSelection = new StringSelection(getFormattedPhoneNumber(phoneStackData.getPhoneNumberString()));
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "Phone Number Copied", 0xFFFFFF));
		}
		
		if (button == factoryReset)
		{
			if (!factoryResetPressed)
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.settings.confirm").getFormattedText()));
				factoryResetPressed = true;
				return;
			}
			
			FactoryResetPacket reset = new FactoryResetPacket();
			reset.hand = hand.ordinal();
			reset.phoneActivateGuiClassName = GuiSettingsAboutPhone.class.getName();
			PacketHandler.INSTANCE.sendToServer(reset);
			
			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.settings.resetdone").getFormattedText()));
		}

		if(button == mux)
		{
			clicksToDebug++;
			if(clicksToDebug == 4)
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "One More Click", 0xFFFFFF));
			}
			if(clicksToDebug >= 5)
			{
				if(!phoneStackData.getIsDebugModeEnabled())
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "Debug Mode Enabled", 0xFFFFFF));

					PhoneWallpaperPacket packet = new PhoneWallpaperPacket();
					packet.hand = hand.ordinal();
					packet.lockBackground = phoneStackData.getLockBackground();
					packet.homeBackground = phoneStackData.getHomeBackground();
					packet.setShowIRLTime = phoneStackData.getShowIRLTime();
					packet.useMilitaryTime = phoneStackData.getShowingMilitaryIRLTime();
					packet.toggleDebugMode = true;
					packet.guiClassName = GuiSettingsAboutPhone.class.getName();
					PacketHandler.INSTANCE.sendToServer(packet);
				}
				else
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "Debug Mode Disabled", 0xFFFFFF));

					PhoneWallpaperPacket packet = new PhoneWallpaperPacket();
					packet.hand = hand.ordinal();
					packet.lockBackground = phoneStackData.getLockBackground();
					packet.homeBackground = phoneStackData.getHomeBackground();
					packet.setShowIRLTime = phoneStackData.getShowIRLTime();
					packet.useMilitaryTime = phoneStackData.getShowingMilitaryIRLTime();
					packet.toggleDebugMode = false;
					packet.guiClassName = GuiSettingsAboutPhone.class.getName();
					PacketHandler.INSTANCE.sendToServer(packet);
				}
			}
		}
	}
}
