package com.mesabrook.ib.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ImageButton extends GuiButton {

	private ResourceLocation textureRL;
	private int texWidth;
	private int texHeight;
	private int uvWidth;
	private int uvHeight;
	public ImageButton(int buttonId, int x, int y, int widthIn, int heightIn, String textureFileName, int texWidth, int texHeight) {
		this(buttonId, x, y, widthIn, heightIn, textureFileName, texWidth, texHeight, texWidth, texHeight);
	}
	
	public ImageButton(int buttonId, int x, int y, int widthIn, int heightIn, String textureFileName, int texWidth, int texHeight, int uvWidth, int uvHeight)
	{
		this(buttonId, x, y, widthIn, heightIn, new ResourceLocation("wbtc", "textures/gui/telecom/" + textureFileName), texWidth, texHeight, uvWidth, uvHeight);
	}
	
	public ImageButton(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation textureLocation, int texWidth, int texHeight, int uvWidth, int uvHeight)
	{
		super(buttonId, x, y, widthIn, heightIn, "");
		this.textureRL = textureLocation;
		this.texWidth = texWidth;
		this.texHeight = texHeight;
		this.uvWidth = uvWidth;
		this.uvHeight = uvHeight;
	}
	
	public int getTexWidth() {
		return texWidth;
	}

	public void setTexWidth(int texWidth) {
		this.texWidth = texWidth;
	}

	public int getTexHeight() {
		return texHeight;
	}

	public void setTexHeight(int texHeight) {
		this.texHeight = texHeight;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (!this.visible)
		{
			return;
		}
		
		if (enabled)
		{
			GlStateManager.color(1, 1, 1);
		}
		else
		{
			GlStateManager.color(0.5F, 0.5F, 0.5F);
		}
		mc.getTextureManager().bindTexture(textureRL);
		drawScaledCustomSizeModalRect(x, y, 0, 0, uvWidth, uvHeight, width, height, texWidth, texHeight);
	}
}
