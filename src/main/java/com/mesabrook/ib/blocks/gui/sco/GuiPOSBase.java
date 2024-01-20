package com.mesabrook.ib.blocks.gui.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public abstract class GuiPOSBase extends GuiScreen {

	protected final TileEntityRegister register;
	protected int innerLeft;
	protected int innerTop;
	protected final int innerHeight = 204;
	protected final int innerWidth = 256;
	public GuiPOSBase(TileEntityRegister register)
	{
		this.register = register;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		innerLeft = width / 2 - 128;
		innerTop = height / 2 - 102;
	}
	
	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
		bindTexture(getBackingTextureName());
//		drawTexturedModalRect(innerLeft, innerTop, 0, 0, 256, 204);
		drawModalRectWithCustomSizedTexture(innerLeft, innerTop, 0, 0, 256, 204, 256, 204);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		doDraw(mouseX, mouseY, partialTicks);
	}
	
	protected abstract String getBackingTextureName();
	
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {}

	protected void bindTexture(String textureName)
	{
		ResourceLocation location = new ResourceLocation(Reference.MODID, "textures/gui/sco/" + textureName);
		Minecraft.getMinecraft().getTextureManager().bindTexture(location);
	}
}
