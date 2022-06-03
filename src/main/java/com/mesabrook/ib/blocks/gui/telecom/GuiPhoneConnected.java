package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.items.misc.ItemPhone.NBTData.Contact;
import com.mesabrook.ib.net.telecom.DisconnectCallPacket;
import com.mesabrook.ib.net.telecom.MergeCallPacket;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GuiPhoneConnected extends GuiPhoneBase {

	private String toNumber;
	private boolean isConferenceSubCall = false; 
	private boolean mergeable = false;
	private Contact contact;
	public GuiPhoneConnected(ItemStack phoneStack, EnumHand hand, String toNumber, boolean isConferenceSubCall, boolean mergeable) {
		super(phoneStack, hand);
		this.toNumber = toNumber;
		this.isConferenceSubCall = isConferenceSubCall;
		this.mergeable = mergeable;
		contact = phoneStackData.getContactByPhoneNumber(toNumber);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}
	
	public boolean isConferenceSubCall() {
		return isConferenceSubCall;
	}

	public boolean isMergeable() {
		return mergeable;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		int buttonY = contact != null ?
				INNER_Y + 160 :
				INNER_Y + 150;
		
		ImageButton endCall = new ImageButton(0, INNER_X + (INNER_TEX_WIDTH / 2) - 16, buttonY, 32, 32, "numcallend.png", 32, 32);
		buttonList.add(endCall);
		
		ImageButton addCall = new ImageButton(1, INNER_X + INNER_TEX_WIDTH / 2 - 16 - 32 - 4, buttonY, 32, 32, "numaddcall.png", 32, 32);
		addCall.visible = !isConferenceSubCall();
		buttonList.add(addCall);
		
		ImageButton mergeCall = new ImageButton(2, INNER_X+ INNER_TEX_WIDTH / 2 + 16 + 4, buttonY, 32, 32, "btn_mergecall.png", 32, 32);
		mergeCall.visible = isMergeable();
		buttonList.add(mergeCall);
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		LocalDateTime dateTime = TelecomClientHandlers.callStartsByPhone.get(getCurrentPhoneNumber());
		if (dateTime == null)
		{
			dateTime = LocalDateTime.now();
			TelecomClientHandlers.callStartsByPhone.put(getCurrentPhoneNumber(), dateTime);
		}
		long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
		long callStartTime = dateTime.toEpochSecond(ZoneOffset.UTC);
		long elapsedSeconds = currentTime - callStartTime;
		long hoursElapsed = elapsedSeconds / 3600;
		elapsedSeconds -= hoursElapsed * 3600;
		long minutesElapsed = elapsedSeconds / 60;
		elapsedSeconds -= minutesElapsed * 60;
		
		String callTime = "";
		if (hoursElapsed > 0)
		{
			callTime += Long.toString(hoursElapsed) + ":";
		}
		
		callTime += String.format("%02d:%02d", minutesElapsed, elapsedSeconds);
		
		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		drawCenteredString(fontRenderer, callTime, scale(INNER_X + (INNER_TEX_WIDTH / 2), uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);
		
		if (contact != null)
		{
			String contactName = truncateIfExceeds(fontRenderer, contact.getUsername(), INNER_TEX_WIDTH, uBigFont);
			GlStateManager.scale(uBigFont, uBigFont, uBigFont);
			drawCenteredString(fontRenderer, contactName, scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 40, dBigFont), 0xFFFFFF);
			GlStateManager.scale(dBigFont, dBigFont, dBigFont);
			
			drawCenteredString(fontRenderer, getFormattedPhoneNumber(toNumber), INNER_X + (INNER_TEX_WIDTH / 2), INNER_Y + 40 + scale(fontRenderer.FONT_HEIGHT, uBigFont) + 6, 0xFFFFFF);
			
			ResourceLocation faceLocation = GetHeadUtil.getHeadResourceLocation(contact.getUsername());
			Minecraft.getMinecraft().getTextureManager().bindTexture(faceLocation);
			
			drawScaledCustomSizeModalRect(INNER_X + (INNER_TEX_WIDTH / 2) - 30, INNER_Y + (INNER_TEX_HEIGHT / 2) - INNER_TEX_Y_OFFSET - 22, 0, 0, 160, 160, 60, 60, 160, 160);
		}
		else
		{
			GlStateManager.scale(uBigFont, uBigFont, uBigFont);
			drawCenteredString(fontRenderer, getFormattedPhoneNumber(toNumber), scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 50, dBigFont), 0xFFFFFF);
			GlStateManager.scale(dBigFont, dBigFont, dBigFont);
			
			if (!toNumber.equalsIgnoreCase("Conference"))
			{
				ResourceLocation faceLocation = GetHeadUtil.getHeadResourceLocation("101girl_crafter671"); // A username that will never work, courtesy of AM9327
				Minecraft.getMinecraft().getTextureManager().bindTexture(faceLocation);
				
				drawScaledCustomSizeModalRect(INNER_X + (INNER_TEX_WIDTH / 2) - 30, INNER_Y + (INNER_TEX_HEIGHT / 2) - INNER_TEX_Y_OFFSET - 27, 0, 0, 160, 160, 60, 60, 160, 160);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 0)
		{
			DisconnectCallPacket disconnect = new DisconnectCallPacket();
			disconnect.fromNumber = getCurrentPhoneNumber();
			PacketHandler.INSTANCE.sendToServer(disconnect);
		}
		else if (button.id == 1)
		{
			GuiPhoneCall callScreen = new GuiPhoneCall(phoneStack, hand);
			Minecraft.getMinecraft().displayGuiScreen(callScreen);
		}
		else if (button.id == 2)
		{
			MergeCallPacket merge = new MergeCallPacket();
			merge.forNumber = getCurrentPhoneNumber();
			PacketHandler.INSTANCE.sendToServer(merge);
		}
	}
}
