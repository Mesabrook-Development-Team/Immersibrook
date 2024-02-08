package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.items.misc.ItemPhone.NBTData.Contact;
import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class GuiCallEnd extends GuiPhoneBase {

	private String toNumber;
	private String message = "";
	private Contact contact;
	public GuiCallEnd(ItemStack phoneStack, EnumHand hand, String toNumber) {
		super(phoneStack, hand);
		this.toNumber = toNumber;
		contact = phoneStackData.getContactByPhoneNumber(toNumber);
	}

	@Override
	protected String getInnerTextureFileName() {
		return phoneStackData.getIconTheme() + "/app_screen.png";
	}
	
	private int displayedTicks = 0;	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		drawCenteredString(fontRenderer, "Call Ended", scale(INNER_X + (INNER_TEX_WIDTH / 2), uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);		
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);
		
		if (contact != null && getMessage().isEmpty())
		{
			String contactName = truncateIfExceeds(fontRenderer, contact.getUsername(), INNER_TEX_WIDTH, uBigFont);
			GlStateManager.scale(uBigFont, uBigFont, uBigFont);
			drawCenteredString(fontRenderer, contactName, scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 40, dBigFont), 0xFFFFFF);
			GlStateManager.scale(dBigFont, dBigFont, dBigFont);
			
			drawCenteredString(fontRenderer, getFormattedPhoneNumber(toNumber), INNER_X + (INNER_TEX_WIDTH / 2), INNER_Y + 40 + scale(fontRenderer.FONT_HEIGHT, uBigFont) + 6, 0xFFFFFF);
			
			ResourceLocation faceLocation = GetHeadUtil.getHeadResourceLocation(contact.getUsername(), phoneStackData.getSkinFetchingEngine());
			Minecraft.getMinecraft().getTextureManager().bindTexture(faceLocation);
			
			drawScaledCustomSizeModalRect(INNER_X + (INNER_TEX_WIDTH / 2) - 30, INNER_Y + (INNER_TEX_HEIGHT / 2) - INNER_TEX_Y_OFFSET - 22, 0, 0, 160, 160, 60, 60, 160, 160);
		}
		else
		{
			GlStateManager.scale(uBigFont, uBigFont, uBigFont);
			drawCenteredString(fontRenderer, getFormattedPhoneNumber(toNumber), scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 50, dBigFont), 0xFFFFFF);
			GlStateManager.scale(dBigFont, dBigFont, dBigFont);
			
			if (getMessage().isEmpty() && !toNumber.equalsIgnoreCase("Conference"))
			{
				ResourceLocation faceLocation = GetHeadUtil.getHeadResourceLocation("101girl_crafter671", phoneStackData.getSkinFetchingEngine()); // A username that will never work, courtesy of AM9327
				Minecraft.getMinecraft().getTextureManager().bindTexture(faceLocation);
				
				drawScaledCustomSizeModalRect(INNER_X + (INNER_TEX_WIDTH / 2) - 30, INNER_Y + (INNER_TEX_HEIGHT / 2) - INNER_TEX_Y_OFFSET - 27, 0, 0, 160, 160, 60, 60, 160, 160);
			}
		}
		
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
