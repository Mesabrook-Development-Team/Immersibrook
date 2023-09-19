package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.net.sco.POSChangeStatusClientToServerPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiPOSPaymentCash extends GuiPOSPaymentBase {

	private GuiImageLabelButton back;

	public GuiPOSPaymentCash(TileEntityRegister register) {
		super(register);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		fontRenderer.drawString("Use Bill and Coin Accepters", innerLeft + 25, innerTop + 110, 0);
		fontRenderer.drawString("to complete transaction, or", innerLeft + 26, innerTop + 121, 0);
		fontRenderer.drawString("press Back to pay another way", innerLeft + 15, innerTop + 132, 0);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		back = new GuiImageLabelButton(0, innerLeft + innerWidth - 76, innerTop + 45, 66, 20, "   Back", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 16, 16, 16, 16, ImageOrientation.Left);
		buttonList.add(back);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == back)
		{
			POSChangeStatusClientToServerPacket statusChange = new POSChangeStatusClientToServerPacket();
			statusChange.pos = register.getPos();
			statusChange.status = RegisterStatuses.PaymentSelect;
			PacketHandler.INSTANCE.sendToServer(statusChange);
			
			mc.displayGuiScreen(new GuiPOSPaymentSelect(register));
		}
	}

}
