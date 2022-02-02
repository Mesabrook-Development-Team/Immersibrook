package com.mesabrook.ib.blocks.gui.telecom;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class MinedroidButton extends GuiButton {
	private int color;
	private int disabledColor;
	private boolean disabled;
	public MinedroidButton(int buttonID, int x, int y, int width, String text, int color)
	{
		this(buttonID, x, y, width, text, color, 0x777777);
	}
	
	public MinedroidButton(int buttonID, int x, int y, int width, String text, int color, int disabledColor)
	{
		super(buttonID, x, y, width, 20, text);
		this.color = color;
		this.disabledColor = disabledColor;
		
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		
		this.height = font.FONT_HEIGHT + 4;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (!visible)
		{
			return;
		}
		
		FontRenderer font = mc.fontRenderer;
		int renderColor = disabled ? disabledColor : color;
		drawCenteredString(font, displayString, x + (width / 2), y + 3, renderColor);
		
		drawBox(renderColor);
	}
	
	private void drawBox(int color)
	{		
		GlStateManager.disableTexture2D();
		
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder builder = tess.getBuffer();
		builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		
		builder.pos(x + width, y, zLevel).endVertex();
		builder.pos(x, y, zLevel).endVertex();
		
		builder.pos(x, y, zLevel).endVertex();
		builder.pos(x, y + height, zLevel).endVertex();
		
		builder.pos(x, y + height, zLevel).endVertex();
		builder.pos(x + width, y + height, zLevel).endVertex();
		
		builder.pos(x + width, y + height, zLevel).endVertex();
		builder.pos(x + width, y, zLevel).endVertex();
		
		tess.draw();
		
		GlStateManager.enableTexture2D();
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
