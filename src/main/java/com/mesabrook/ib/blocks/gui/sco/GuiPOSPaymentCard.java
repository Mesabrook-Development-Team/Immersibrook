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

public class GuiPOSPaymentCard extends GuiPOSPaymentBase {

	private GuiImageLabelButton back;
	private boolean displayBackButton;
	
	public GuiPOSPaymentCard(TileEntityRegister register) {
		this(register, true);
	}
	
	public GuiPOSPaymentCard(TileEntityRegister register, boolean displayBackButton)
	{
		super(register);
		this.displayBackButton = displayBackButton;
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		fontRenderer.drawString("Use PIN Pad", innerLeft + 66, innerTop + 115, 0);
		fontRenderer.drawString("to complete transaction", innerLeft + 37, innerTop + 126, 0);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		back = new GuiImageLabelButton(0, innerLeft + innerWidth - 76, innerTop + 45, 66, 20, "   Back", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 16, 16, 16, 16, ImageOrientation.Left);
		back.visible = displayBackButton;
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
