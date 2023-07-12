package com.mesabrook.ib.blocks.gui.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

public abstract class GuiPOSMainBase extends GuiPOSBase {

	public GuiPOSMainBase(TileEntityRegister register) {
		super(register);
	}

	@Override
	protected final String getBackingTextureName() {
		return "sco_gui.png";
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
	}
}
