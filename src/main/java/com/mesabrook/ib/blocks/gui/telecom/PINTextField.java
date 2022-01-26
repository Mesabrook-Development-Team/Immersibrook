package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class PINTextField extends GuiTextField {

	private String enteredPIN = "";
	public PINTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int width, int height) {
		super(componentId, fontrendererObj, x, y, width, height);
	}

	@Override
	public boolean textboxKeyTyped(char typedChar, int keyCode) {
		if (!isFocused())
		{
			return false;
		}
		
		setText(enteredPIN);
		super.textboxKeyTyped(typedChar, keyCode);
		
		if (!getText().isEmpty())
		{
			try
			{
				Integer.parseInt(getText());
			}
			catch(NumberFormatException nfe)
			{
				generateAndSetMaskedText(enteredPIN);
				return false;
			}
		}
		
		enteredPIN = getText();
		generateAndSetMaskedText(enteredPIN);
		return true;
	}
	
	private void generateAndSetMaskedText(String unmaskedText)
	{
		String masked = "";
		for(int i = 0; i < unmaskedText.length(); i++)
		{
			masked += "*";
		}
		
		setText(masked);
	}
	
	public String getMaskedText()
	{
		return enteredPIN;
	}
	
	public void setMaskedText(String unmaskedText)
	{
		enteredPIN = unmaskedText;
		generateAndSetMaskedText(enteredPIN);
	}
}
