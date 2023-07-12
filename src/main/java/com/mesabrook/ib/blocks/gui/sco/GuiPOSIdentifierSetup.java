package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.net.sco.POSInitializeRegisterPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiPOSIdentifierSetup extends GuiPOSBase {

	GuiTextField identifierField;
	GuiButton submit;
	
	final String title = TextFormatting.BOLD + "* REGISTER UNINITIALIZED *";
	final String identifierFieldLabel = "Identifier:";
	String error;
	
	public GuiPOSIdentifierSetup(TileEntityRegister register) {
		super(register);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		int fieldLabelWidth = fontRenderer.getStringWidth(identifierFieldLabel);
		identifierField = new GuiTextField(0, fontRenderer, innerLeft + fieldLabelWidth + 4, height / 2 - 10, innerWidth - fieldLabelWidth - 8, 20);
		identifierField.setMaxStringLength(36);
		submit = new GuiButtonExt(0, identifierField.x, identifierField.y + identifierField.height + 2, identifierField.width, 20, "Submit");
		
		if (register.getRegisterStatus() == RegisterStatuses.Initializing)
		{
			submit.enabled = false;
			identifierField.setEnabled(false);
			identifierField.setText("Initializing...");
		}
		
		buttonList.add(submit);
	}

	@Override
	public void doDraw(int mouseX, int mouseY, float partialTicks) {		
		drawCenteredString(fontRenderer, title, width / 2, innerTop + 2, 0xFFFFFF);
		fontRenderer.drawString(identifierFieldLabel, innerLeft + 2, height / 2 - (fontRenderer.FONT_HEIGHT / 2), 0xFFFFFF);
		identifierField.drawTextBox();
		
		if (error != null)
		{
			String truncatedError = fontRenderer.trimStringToWidth(error, submit.width);
			fontRenderer.drawString(truncatedError, submit.x, submit.y + submit.height + 2, 0xFF0000);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		identifierField.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		identifierField.textboxKeyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == submit)
		{
			submit.enabled = false;
			String identifier = identifierField.getText();
			identifierField.setText("Initializing...");
			identifierField.setCursorPositionZero();
			identifierField.setEnabled(false);
			
			POSInitializeRegisterPacket initPacket = new POSInitializeRegisterPacket();
			initPacket.identifier = identifier;
			initPacket.pos = register.getPos();
			PacketHandler.INSTANCE.sendToServer(initPacket);
		}
	}
	
	public void displayError(String error)
	{
		this.error = error;
		submit.enabled = true;
		identifierField.setText("");
		identifierField.setEnabled(true);
	}

	@Override
	protected String getBackingTextureName() {
		return "shock_blue.png";
	}
}
