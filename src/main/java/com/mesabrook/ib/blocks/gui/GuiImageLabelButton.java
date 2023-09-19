package com.mesabrook.ib.blocks.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiImageLabelButton extends GuiButton {

	private ResourceLocation texLocation;
	private int texWidth;
	private int texHeight;
	private int uvWidth;
	private int uvHeight;
	private ImageOrientation orientation;
	private int textWidth;
	
	private int enabledColor = 0xc6c6c6;
	private int disabledColor = 0x555555;
	private boolean drawBorder = true;
	private float textScale = 1F;
	
	public GuiImageLabelButton(int id, int x, int y, int width, int height, String text,
			ResourceLocation textureLocation, int texWidth, int texHeight, int uvWidth, int uvHeight, ImageOrientation orientation)
	{
		super(id, x, y, width, height, text);
		this.texWidth = texWidth;
		this.texHeight = texHeight;
		this.uvWidth = uvWidth;
		this.uvHeight = uvHeight;
		this.orientation = orientation;
		
		texLocation = textureLocation;
		textWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
	}
	
	public enum ImageOrientation
	{
		Left,
		Right;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (!visible)
		{
			return;
		}
		
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		
		int effectiveColor = enabled ? enabledColor : disabledColor;
		int red = (effectiveColor >> 16) & 0xFF;
		int green = (effectiveColor >> 8) & 0xFF;
		int blue = effectiveColor & 0xFF;
		
		if (drawBorder)
		{
			
			GlStateManager.disableTexture2D();
			
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder builder = tess.getBuffer();
			builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
			builder.pos(x, y, zLevel).color(red, green, blue, 255).endVertex();
			builder.pos(x + width, y, zLevel).color(red, green, blue, 255).endVertex();
			
			builder.pos(x + width, y, zLevel).color(red, green, blue, 255).endVertex();
			builder.pos(x + width, y + height, zLevel).color(red, green, blue, 255).endVertex();
			
			builder.pos(x + width, y + height, zLevel).color(red, green, blue, 255).endVertex();
			builder.pos(x, y + height, zLevel).color(red, green, blue, 255).endVertex();
			
			builder.pos(x, y + height, zLevel).color(red, green, blue, 255).endVertex();
			builder.pos(x, y, zLevel).color(red, green, blue, 255).endVertex();
			tess.draw();
			GlStateManager.enableTexture2D();
		}
		
		GlStateManager.scale(textScale, textScale, 1);
		int textY = (int)(scale(y + height / 2, 1/textScale) - mc.fontRenderer.FONT_HEIGHT / 2) + 2;
		mc.fontRenderer.drawString(displayString, (int)scale(x + (width / 2) - scale(textWidth / 2, textScale), 1/textScale), textY, effectiveColor);
		GlStateManager.scale(1/textScale, 1/textScale, 1);
		
		if (texLocation != null)
		{
			mc.getTextureManager().bindTexture(texLocation);
			
			if (enabled)
			{
				GlStateManager.color(1, 1, 1);
			}
	
			int textureX = x + 1;
			int textureY = y + (height / 2) - (texHeight / 2);
			if (orientation == ImageOrientation.Left)
			{
				drawScaledCustomSizeModalRect(textureX, textureY, 0, 0, uvWidth, uvHeight, texWidth, texHeight, uvWidth, uvHeight);
			}
			else if (orientation == ImageOrientation.Right)
			{
				drawScaledCustomSizeModalRect(textureX + width - texWidth - 2, textureY, 0, 0, uvWidth, uvHeight, texWidth, texHeight, uvWidth, uvHeight);
			}
		}
		
		if (enabled && hovered)
		{
			GlStateManager.enableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.disableTexture2D();
			GlStateManager.color(red, green, blue, 0.2F);
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder builder = tess.getBuffer();
			builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			builder.pos(x, y, zLevel).color(red, green, blue, 51).endVertex();
			builder.pos(x, y + height, zLevel).color(red, green, blue, 51).endVertex();
			builder.pos(x + width, y + height, zLevel).color(red, green, blue, 51).endVertex();
			builder.pos(x + width, y, zLevel).color(red, green, blue, 51).endVertex();
			tess.draw();
			GlStateManager.enableTexture2D();
		}
	}
	
	private float scale(float number, float scale)
	{
		return number * scale;
	}

	public GuiImageLabelButton setEnabledColor(int color)
	{
		this.enabledColor = color;
		return this;
	}
	
	public GuiImageLabelButton setTextScale(float textScale)
	{
		this.textScale = textScale;
		return this;
	}
}
