package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;

public class GuiCallEnd extends GuiPhoneBase {

	private String toNumber;
	private String message = "";
	public GuiCallEnd(ItemStack phoneStack, EnumHand hand, String toNumber) {
		super(phoneStack, hand);
		this.toNumber = toNumber;
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}
	
	private int displayedTicks = 0;	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		drawCenteredString(fontRenderer, "Call Ended", scale(INNER_X + (INNER_TEX_WIDTH / 2), uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);		
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);
		
		GlStateManager.scale(uBigFont, uBigFont, uBigFont);
		drawCenteredString(fontRenderer, getFormattedPhoneNumber(toNumber), scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 50, dBigFont), 0xFFFFFF);
		GlStateManager.scale(dBigFont, dBigFont, dBigFont);
		
		if (getMessage() != null && !getMessage().equals(""))
		{
			drawCenteredString(fontRenderer, getMessage(), INNER_X + (INNER_TEX_WIDTH / 2), INNER_Y + 70, 0xFF0000);
		}
		
		if (displayedTicks++ > 180)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiEmptyPhone(getPhoneStack(), getHand()));
			
			PhoneQueryPacket query = new PhoneQueryPacket();
			query.forNumber = getCurrentPhoneNumber();
			
			int nextID = TelecomClientHandlers.getNextHandlerID();
			TelecomClientHandlers.phoneQueryResponseHandlers.put(nextID, TelecomClientHandlers::onPhoneQueryResponseForPhoneApp);
			query.clientHandlerCode = nextID;
			PacketHandler.INSTANCE.sendToServer(query);
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
