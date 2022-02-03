package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.mesabrook.ib.net.telecom.FactoryResetPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiSettingsAboutPhone extends GuiPhoneBase {

	LabelButton back;
	MinedroidButton factoryReset;
	private boolean factoryResetPressed;
	
	public GuiSettingsAboutPhone(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}
	
	@Override
	protected String getInnerTextureFileName() {
		return "app_screen_about.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();

		back = new LabelButton(4, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);
		factoryReset = new MinedroidButton(1, INNER_X + 3, INNER_Y + 160, INNER_TEX_WIDTH - 6, "Factory Reset", 0xFF5733);
		
		buttonList.add(back);
		buttonList.add(factoryReset);
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		fontRenderer.drawString("About Phone", INNER_X + 15, INNER_Y + 20, 0xFFFFFF);

		drawCenteredString(fontRenderer, "Minedroid " + Reference.MINEDROID_VERSION, INNER_X + 80, INNER_Y + 80, 3395327);
		
		fontRenderer.drawString("Phone Information", INNER_X + 3, INNER_Y + 106, 0xFFFFFF);

		fontRenderer.drawString("Phone Number:", INNER_X + 3, INNER_Y + 118, 0x5179bd);
		int stringWidth = fontRenderer.getStringWidth("Phone Number:");
		fontRenderer.drawString(getFormattedPhoneNumber(phoneStackData.getPhoneNumberString()), INNER_X + 3 + stringWidth + 3, INNER_Y + 118, 0xFFFFFF);

		fontRenderer.drawString("Security Strategy:", INNER_X + 3, INNER_Y + 131, 0x5179bd);
		int stratStringWidth = fontRenderer.getStringWidth("Security Strategy:");
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
		
		if (button == factoryReset)
		{
			if (!factoryResetPressed)
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Press again to confirm"));
				factoryResetPressed = true;
				return;
			}
			
			FactoryResetPacket reset = new FactoryResetPacket();
			reset.hand = hand.ordinal();
			reset.phoneActivateGuiClassName = GuiSettingsAboutPhone.class.getName();
			PacketHandler.INSTANCE.sendToServer(reset);
			
			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Phone Reset"));
		}
	}
}
