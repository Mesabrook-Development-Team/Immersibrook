package com.mesabrook.ib.blocks.gui.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

import net.minecraft.client.renderer.GlStateManager;

public abstract class GuiPOSMainBase extends GuiPOSBase {

	public GuiPOSMainBase(TileEntityRegister register) {
		super(register);
	}

	@Override
	protected String getBackingTextureName() {
		return "sco_gui.png";
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		fontRenderer.drawString("Self-Checkout System", innerLeft + 46, innerTop + 10, 0xFFFFFF);
		
		if (register.getName() != null)
		{
			GlStateManager.scale(0.5, 0.5, 1);
			int stringWidth = fontRenderer.getStringWidth(register.getName());
			fontRenderer.drawString(register.getName(), (innerLeft + innerWidth - 7) * 2 - stringWidth, (innerTop + 17) * 2 - fontRenderer.FONT_HEIGHT, 0);
			GlStateManager.scale(2, 2, 1);
		}
	}
}
