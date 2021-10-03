package rz.mesabrook.wbtc.blocks.gui.telecom;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import rz.mesabrook.wbtc.net.telecom.AcceptCallPacket;
import rz.mesabrook.wbtc.net.telecom.RejectCallPacket;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

import java.io.IOException;

public class GuiIncomingCall extends GuiPhoneBase {

	private String incomingNumber;
	public GuiIncomingCall(ItemStack phoneStack, EnumHand hand, String incomingNumber) {
		super(phoneStack, hand);
		this.incomingNumber = incomingNumber;
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}

	@Override
	public void initGui() {
		super.initGui();
		
		ImageButton acceptCall = new ImageButton(0, INNER_X + (INNER_TEX_WIDTH / 2) - 48, INNER_Y + 150, 32, 32, "numcall.png", 32, 32);
		buttonList.add(acceptCall);
		
		ImageButton rejectCall = new ImageButton(1, INNER_X + (INNER_TEX_WIDTH / 2) + 16, INNER_Y + 150, 32, 32, "numcallend.png", 32, 32);
		buttonList.add(rejectCall);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		drawCenteredString(fontRenderer, "Incoming Call", scale(INNER_X + (INNER_TEX_WIDTH / 2), uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);		
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);
		
		GlStateManager.scale(uBigFont, uBigFont, uBigFont);
		drawCenteredString(fontRenderer, getFormattedPhoneNumber(incomingNumber), scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 50, dBigFont), 0xFFFFFF);
		GlStateManager.scale(dBigFont, dBigFont, dBigFont);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 1)
		{
			RejectCallPacket reject = new RejectCallPacket();
			reject.fromNumber = incomingNumber;
			PacketHandler.INSTANCE.sendToServer(reject);
		}
		
		if (button.id == 0)
		{
			AcceptCallPacket accept = new AcceptCallPacket();
			accept.fromNumber = getCurrentPhoneNumber();
			PacketHandler.INSTANCE.sendToServer(accept);
		}
	}
}
