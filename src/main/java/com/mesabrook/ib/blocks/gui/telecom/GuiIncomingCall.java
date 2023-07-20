package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.items.misc.ItemPhone.NBTData.Contact;
import com.mesabrook.ib.net.telecom.AcceptCallPacket;
import com.mesabrook.ib.net.telecom.RejectCallPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiIncomingCall extends GuiPhoneBase {

	private String incomingNumber;
	private Contact contact;
	
	public GuiIncomingCall(ItemStack phoneStack, EnumHand hand, String incomingNumber) {
		super(phoneStack, hand);
		this.incomingNumber = incomingNumber;
		contact = phoneStackData.getContactByPhoneNumber(incomingNumber);
	}

	@Override
	protected String getInnerTextureFileName() {
		return phoneStackData.getIconTheme() + "/app_screen.png";
	}

	@Override
	public void initGui() {
		super.initGui();
		
		int buttonY = contact != null ?
						INNER_Y + 160 :
						INNER_Y + 150;
		
		ImageButton acceptCall = new ImageButton(0, INNER_X + (INNER_TEX_WIDTH / 2) - 48, buttonY, 32, 32, "numcall.png", 32, 32);
		buttonList.add(acceptCall);
		
		ImageButton rejectCall = new ImageButton(1, INNER_X + (INNER_TEX_WIDTH / 2) + 16, buttonY, 32, 32, "numcallend.png", 32, 32);
		buttonList.add(rejectCall);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		drawCenteredString(fontRenderer, "Incoming Call", scale(INNER_X + (INNER_TEX_WIDTH / 2), uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);		
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);
		
		if (contact != null)
		{
			String contactName = truncateIfExceeds(fontRenderer, contact.getUsername(), INNER_TEX_WIDTH, uBigFont);
			GlStateManager.scale(uBigFont, uBigFont, uBigFont);
			drawCenteredString(fontRenderer, contactName, scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 40, dBigFont), 0xFFFFFF);
			GlStateManager.scale(dBigFont, dBigFont, dBigFont);
			
			drawCenteredString(fontRenderer, getFormattedPhoneNumber(incomingNumber), INNER_X + (INNER_TEX_WIDTH / 2), INNER_Y + 40 + scale(fontRenderer.FONT_HEIGHT, uBigFont) + 6, 0xFFFFFF);
			
			ResourceLocation faceLocation = GetHeadUtil.getHeadResourceLocation(contact.getUsername());
			Minecraft.getMinecraft().getTextureManager().bindTexture(faceLocation);
			
			drawScaledCustomSizeModalRect(INNER_X + (INNER_TEX_WIDTH / 2) - 30, INNER_Y + (INNER_TEX_HEIGHT / 2) - INNER_TEX_Y_OFFSET - 22, 0, 0, 160, 160, 60, 60, 160, 160);
		}
		else
		{
			GlStateManager.scale(uBigFont, uBigFont, uBigFont);
			drawCenteredString(fontRenderer, getFormattedPhoneNumber(incomingNumber), scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 50, dBigFont), 0xFFFFFF);
			GlStateManager.scale(dBigFont, dBigFont, dBigFont);
			
			ResourceLocation faceLocation = GetHeadUtil.getHeadResourceLocation("101girl_crafter671"); // A username that will never work, courtesy of AM9327
			Minecraft.getMinecraft().getTextureManager().bindTexture(faceLocation);
			
			drawScaledCustomSizeModalRect(INNER_X + (INNER_TEX_WIDTH / 2) - 30, INNER_Y + (INNER_TEX_HEIGHT / 2) - INNER_TEX_Y_OFFSET - 27, 0, 0, 160, 160, 60, 60, 160, 160);
		}
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
