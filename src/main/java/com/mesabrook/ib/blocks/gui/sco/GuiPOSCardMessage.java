package com.mesabrook.ib.blocks.gui.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

public class GuiPOSCardMessage extends GuiPOSCardBase {

	private final String message;
	public GuiPOSCardMessage(TileEntityRegister register, CardReaderInfo readerInfo, String message) {
		super(register, readerInfo);
		this.message = message;
	}

	@Override
	public void doDraw(int mouseX, int mouseY, float partialTicks) {		
		drawCenteredStringNoShadow(message, midWidth, midHeight - fontRenderer.FONT_HEIGHT / 2, 0);
	}
}
