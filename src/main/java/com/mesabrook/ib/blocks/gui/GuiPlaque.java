package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.net.EngravePacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.io.IOException;

public class GuiPlaque extends GuiScreen {
	
	GuiTextField awardedToBox;
	GuiTextField awardedForBox;
	GuiButtonExt engrave;
	private EnumHand hand;
	public GuiPlaque(EnumHand hand)
	{
		this.hand = hand;
	}
	
	@Override
	public void initGui() {
		int horizontalCenter = width / 2;
		int verticalCenter = height / 2;
		
		awardedToBox = new GuiTextField(1, fontRenderer, horizontalCenter - 90, verticalCenter - 30, 200, 20);
		awardedToBox.setFocused(true);
		awardedForBox = new GuiTextField(2, fontRenderer, awardedToBox.x, awardedToBox.y + awardedToBox.height + 4, 200, 20);
		
		engrave = new GuiButtonExt(1, awardedForBox.x, awardedForBox.y + 24, awardedForBox.width, 20, "Engrave");
		
		buttonList.add(engrave);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		
		awardedToBox.drawTextBox();
		awardedForBox.drawTextBox();
		int stringWidth = fontRenderer.getStringWidth("Awarded To:");
		fontRenderer.drawString("Awarded To:", awardedToBox.x - stringWidth - 4, awardedToBox.y + ((awardedToBox.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);
		
		stringWidth = fontRenderer.getStringWidth("Awarded For:");
		fontRenderer.drawString("Awarded For:", awardedForBox.x - stringWidth - 4, awardedForBox.y + ((awardedForBox.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		awardedToBox.mouseClicked(mouseX, mouseY, mouseButton);
		awardedForBox.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		awardedToBox.textboxKeyTyped(typedChar, keyCode);
		awardedForBox.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == engrave && awardedToBox.getText() != null && !awardedToBox.getText().equals(""))
		{
			EngravePacket packet = new EngravePacket();
			packet.awardedTo = awardedToBox.getText();
			packet.awardedFor = awardedForBox.getText();
			packet.hand = hand;
			PacketHandler.INSTANCE.sendToServer(packet);
			
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}
}
