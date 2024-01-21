package com.mesabrook.ib.blocks.gui.atm;

import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public abstract class GuiATMBase extends GuiScreen {

	protected final TileEntityATM atm;
	protected final int texWidth = 300;
	protected final int texHeight = 200;
	protected int left = 0;
	protected int right = 0;
	protected int top = 0;
	protected int bottom = 0;
	protected int midWidth = 0;
	protected int midHeight = 0;
	
	public GuiATMBase(TileEntityATM atm)
	{
		this.atm = atm;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		left = (width / 2) - (texWidth / 2);
		right = left + (width / 2);
		top = (height / 2) - (texHeight / 2);
		bottom = top + (texHeight / 2);
		midWidth = width / 2;
		midHeight = height / 2;
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/atm/atm.png"));
		drawScaledCustomSizeModalRect(left, top, 0, 0, texWidth, texHeight, texWidth, texHeight, texWidth, texHeight);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
