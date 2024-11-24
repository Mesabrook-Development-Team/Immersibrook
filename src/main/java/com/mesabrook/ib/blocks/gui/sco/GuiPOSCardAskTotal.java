package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.net.sco.POSCardProcessPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextFormatting;

public class GuiPOSCardAskTotal extends GuiPOSCardBase {

	GuiImageLabelButton yes;
	GuiImageLabelButton no;
	
	public GuiPOSCardAskTotal(TileEntityRegister register, CardReaderInfo readerInfo) {
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
		
		drawCenteredStringNoShadow(TextFormatting.BOLD + "Authorize MBD$" + readerInfo.authorizedAmount.toPlainString() + "?", midWidth, midHeight - 2 - fontRenderer.FONT_HEIGHT, 0);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		playButtonSound();
		if (button == yes)
		{
			POSCardProcessPacket process = new POSCardProcessPacket();
			process.registerPos = register.getPos();
			process.enteredPin = readerInfo.pin;
			process.authorizedAmount = readerInfo.authorizedAmount;
			process.cashBack = readerInfo.cashBack;
			PacketHandler.INSTANCE.sendToServer(process);
			
			mc.displayGuiScreen(new GuiPOSCardMessage(register, readerInfo, "Processing..."));
		}
		
		if (button == no)
		{
			ejectCard();
		}
	}
}
