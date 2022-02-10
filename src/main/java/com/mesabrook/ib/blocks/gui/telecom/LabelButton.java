package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class LabelButton extends GuiButton {
	private int color;
	private int disabledColor;
	private boolean disabled;
	public LabelButton(int buttonID, int x, int y, String text, int color)
	{
		this(buttonID, x, y, text, color, 0x777777);
	}
	
	public LabelButton(int buttonID, int x, int y, String text, int color, int disabledColor)
	{
		super(buttonID, x, y, text);
		this.color = color;
		this.disabledColor = disabledColor;
		
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		
		this.height = font.FONT_HEIGHT;
		this.width = font.getStringWidth(text);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (!visible)
		{
			return;
		}
		
		FontRenderer font = mc.fontRenderer;
		int renderColor = disabled ? disabledColor : color;
		font.drawString(displayString, x, y, renderColor);
	}
	
	public boolean isDisabled()
	{
		return disabled;
	}
	
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}
}
