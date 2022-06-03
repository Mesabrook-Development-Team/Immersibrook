package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class MinedroidButton extends GuiButton {
	private int color;
	private int disabledColor;
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
		int renderColor = enabled ? color : disabledColor;
		int fontWidth = font.getStringWidth(displayString);
		
		font.drawString(displayString, x + (width / 2) - (fontWidth / 2), y + (height / 2) - (font.FONT_HEIGHT / 2) + 1, renderColor);
		
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
}
