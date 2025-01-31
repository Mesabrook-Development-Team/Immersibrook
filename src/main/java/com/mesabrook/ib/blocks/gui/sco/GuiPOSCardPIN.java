package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.gui.PINTextField;
import com.mesabrook.ib.blocks.te.TileEntityRegister;

import net.minecraft.util.text.TextFormatting;

public class GuiPOSCardPIN extends GuiPOSCardBase {

	PINTextField pin;
	
	public GuiPOSCardPIN(TileEntityRegister register, CardReaderInfo readerInfo) {
		super(register, readerInfo);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		pin = new PINTextField(0, fontRenderer, midWidth - 30, midHeight - 35, 60, 20);
		pin.setMaxStringLength(4);
		pin.setFocused(true);
	}
	
	@Override
	public void doDraw(int mouseX, int mouseY, float partialTicks) {		
		drawCenteredStringNoShadow(TextFormatting.BOLD + "= PIN Required =", midWidth, top + 60, 0);
		
		drawCenteredStringNoShadow("Enter PIN:", midWidth, midHeight - fontRenderer.FONT_HEIGHT - 4, 0);
		pin.drawTextBox();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		pin.setFocused(true);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		if(pin.textboxKeyTyped(typedChar, keyCode))
		{
			playButtonSound();
		}
		
		if (keyCode == Keyboard.KEY_NUMPADENTER || keyCode == Keyboard.KEY_RETURN)
		{
			readerInfo.pin = pin.getMaskedText();
			
			mc.displayGuiScreen(new GuiPOSCardAskCashback(register, readerInfo));
		}
	}
	
	@Override
	protected void numpadButtonPressed(String character, boolean cancelPressed, boolean okPressed) {
		try
		{
			if (character != "")
			{
				keyTyped(character.toCharArray()[0], 0);
				pin.setFocused(true);
			}
			else if (okPressed)
			{
				keyTyped(' ', Keyboard.KEY_RETURN);
			}
			else if (cancelPressed)
			{
				ejectCard();
			}
		}
		catch(IOException ex)
		{
			Main.logger.error("Error occurred handling key typed", ex);
		}
	}
}
