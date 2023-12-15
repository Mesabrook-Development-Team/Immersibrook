package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ImageButton extends GuiButton {

	private String textureFileName;
	private int texWidth;
	private int texHeight;
	private int uvWidth;
	private int uvHeight;
	private boolean hover;
	public ImageButton(int buttonId, int x, int y, int widthIn, int heightIn, String textureFileName, int texWidth, int texHeight) {
		this(buttonId, x, y, widthIn, heightIn, textureFileName, texWidth, texHeight, texWidth, texHeight);
	}
	
	public ImageButton(int buttonId, int x, int y, int widthIn, int heightIn, String textureFileName, int texWidth, int texHeight, int uvWidth, int uvHeight)
	{
		super(buttonId, x, y, widthIn, heightIn, "");
		this.textureFileName = textureFileName;
		this.texWidth = texWidth;
		this.texHeight = texHeight;
		this.uvWidth = uvWidth;
		this.uvHeight = uvHeight;
		this.hover = false;
	}

	public String getTextureFileName()
	{
		return textureFileName;
	}
	
	public void setTextureFileName(String textureFileName)
	{
		this.textureFileName = textureFileName;
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
	public boolean isHovering()
	{
		return this.hover;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (!this.visible)
		{
			return;
		}
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		GlStateManager.color(1, 1, 1);
		mc.getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/" + textureFileName));
		drawScaledCustomSizeModalRect(x, y, 0, 0, uvWidth, uvHeight, width, height, texWidth, texHeight);
	}
}
