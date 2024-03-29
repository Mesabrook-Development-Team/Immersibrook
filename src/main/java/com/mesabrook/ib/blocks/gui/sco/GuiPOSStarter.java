package com.mesabrook.ib.blocks.gui.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;

import net.minecraft.client.Minecraft;

public class GuiPOSStarter extends GuiPOSBase {

	public GuiPOSStarter(TileEntityRegister register) {
		super(register);
	}

	@Override
	public void doDraw(int mouseX, int mouseY, float partialTicks) {
		switch(register.getRegisterStatus())
		{
			case Uninitialized:
			case Initializing:
				Minecraft.getMinecraft().displayGuiScreen(new GuiPOSIdentifierSetup(register));
				break;
			case WaitingForNetwork:
				Minecraft.getMinecraft().displayGuiScreen(new GuiPOSWaitingForNetwork(register));
				break;
			case InternalStorageFull:
				mc.displayGuiScreen(new GuiPOSInventoryFull(register));
				break;
			case Offline:
				mc.displayGuiScreen(new GuiPOSOffline(register));
				break;
			case Online:
				Minecraft.getMinecraft().displayGuiScreen(new GuiPOSMainWelcome(register));
				break;
			case InSession:
				Minecraft.getMinecraft().displayGuiScreen(new GuiPOSInSession(register));
				break;
			case PaymentSelect:
				Minecraft.getMinecraft().displayGuiScreen(new GuiPOSPaymentSelect(register));
				break;
			case PaymentCard:
			case PaymentCardInUse:
				mc.displayGuiScreen(new GuiPOSPaymentCard(register, register.getRegisterStatus() == RegisterStatuses.PaymentCard));
				break;
			case PaymentCash:
				mc.displayGuiScreen(new GuiPOSPaymentCash(register));
				break;
			default:
				drawCenteredString(fontRenderer, "error: unable to determine status", width / 2, height / 2 - (fontRenderer.FONT_HEIGHT / 2), 0xFFFFFF);
		}		
	}

	@Override
	protected String getBackingTextureName() {
		return "shock_blue.png";
	}
}
