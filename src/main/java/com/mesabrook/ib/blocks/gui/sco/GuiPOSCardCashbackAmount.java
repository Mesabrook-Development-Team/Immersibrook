package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.input.Keyboard;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextFormatting;

public class GuiPOSCardCashbackAmount extends GuiPOSCardBase {

	public GuiPOSCardCashbackAmount(TileEntityRegister register, CardReaderInfo readerInfo) {
		super(register, readerInfo);
	}

	GuiTextField amount;
	@Override
	public void initGui() {
		super.initGui();
		
		amount = new GuiTextField(0, fontRenderer, midWidth - 50, midHeight + 2, 100, 20);
		amount.setValidator(str -> validateAmount(str));
		amount.setFocused(true);
	}
	
	private boolean validateAmount(String value)
	{
		if (value == null || value.isEmpty())
		{
			return true;
		}
		
		try
		{
			return new BigDecimal(value).compareTo(new BigDecimal(0)) >= 0;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		drawCenteredStringNoShadow(TextFormatting.BOLD + "= Cash Back Entry =", midWidth, top + 10, 0);
		drawCenteredStringNoShadow("Enter cash back amount:", midWidth, midHeight - 2 - fontRenderer.FONT_HEIGHT, 0);
		drawCenteredStringNoShadow(TextFormatting.ITALIC + "Enter '0' to skip", midWidth, amount.y + amount.height + 4, 0x666666);
		
		amount.drawTextBox();
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		amount.textboxKeyTyped(typedChar, keyCode);
		
		if (!amount.getText().isEmpty() && (keyCode == Keyboard.KEY_NUMPADENTER || keyCode == Keyboard.KEY_RETURN))
		{
			BigDecimal enteredAmount;
			try
			{
				enteredAmount = new BigDecimal(amount.getText()).setScale(2, RoundingMode.HALF_UP);
			}
			catch(Exception ex)
			{
				return;
			}
			
			readerInfo.cashBack = enteredAmount;
			readerInfo.authorizedAmount = readerInfo.authorizedAmount.add(enteredAmount);
			mc.displayGuiScreen(new GuiPOSCardAskTotal(register, readerInfo));
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		amount.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
