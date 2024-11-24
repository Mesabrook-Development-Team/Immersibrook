package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.te.TileEntityRegister;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextFormatting;

public class GuiPOSCardAskCashback extends GuiPOSCardBase {

	GuiImageLabelButton yes;
	GuiImageLabelButton no;
	
	public GuiPOSCardAskCashback(TileEntityRegister register, CardReaderInfo readerInfo) {
		super(register, readerInfo);
	}

	@Override
	public void initGui() {
		super.initGui();
		
		yes = new GuiImageLabelButton(0, midWidth - 32, midHeight - 35, 30, 20, "Yes", null, 0, 0, 0, 0, null).setEnabledColor(0x30c918);
		no = new GuiImageLabelButton(0, midWidth + 2, midHeight - 35, 30, 20, "No", null, 0, 0, 0, 0, null).setEnabledColor(0x30c918);
		
		buttonList.add(yes);
		buttonList.add(no);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		drawCenteredStringNoShadow(TextFormatting.BOLD + "Do you want cash back?", midWidth, midHeight - 2 - fontRenderer.FONT_HEIGHT, 0);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		readerInfo.authorizedAmount = register.getDueAmount().setScale(2, RoundingMode.HALF_UP);
		playButtonSound(); 
		if (button == no)
		{			
			readerInfo.cashBack = new BigDecimal("0.00");
			
			mc.displayGuiScreen(new GuiPOSCardAskTotal(register, readerInfo));
		}
		
		if (button == yes)
		{
			mc.displayGuiScreen(new GuiPOSCardCashbackAmount(register, readerInfo));
		}
	}
}
