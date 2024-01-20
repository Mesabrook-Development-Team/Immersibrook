package com.mesabrook.ib.blocks.gui.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

public class GuiPOSCardDetermine extends GuiPOSCardBase {

	public GuiPOSCardDetermine(TileEntityRegister register) {
		super(register, new CardReaderInfo());
	}
	
	@Override
	public void doDraw(int mouseX, int mouseY, float partialTicks) {		
		if (register.getInsertedCardStack() == null)
		{
			mc.displayGuiScreen(new GuiPOSCardMessage(register, readerInfo, "= No Card Inserted ="));
		}
		else
		{
			mc.displayGuiScreen(new GuiPOSCardPIN(register, readerInfo));
		}
	}
}
