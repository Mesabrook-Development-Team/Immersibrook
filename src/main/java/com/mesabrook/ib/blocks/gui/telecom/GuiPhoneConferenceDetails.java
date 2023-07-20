package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;
import java.util.ArrayList;

import com.mesabrook.ib.items.misc.ItemPhone.NBTData.Contact;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.saveData.PhoneLogData.LogData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiPhoneConferenceDetails extends GuiPhoneBase {

	private int page;
	private LogData logData;
	
	LabelButton back;
	ArrayList<LogItem> logItems = new ArrayList<>(7);
	ArrayList<ImageButton> callButtons = new ArrayList<>();
	boolean exceedsShownLimit = false;
	
	public GuiPhoneConferenceDetails(ItemStack phoneStack, EnumHand hand, int page, LogData data) {
		super(phoneStack, hand);
		this.page = page;
		this.logData = data;
	}

	@Override
	protected String getInnerTextureFileName() {
		return phoneStackData.getIconTheme() + "/app_screen.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		back = new LabelButton(0, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);
		buttonList.add(back);
		
		logItems.clear();
		for(int i = 0; i < 7; i++)
		{
			if (logData.getOtherNumbers().length <= i)
			{
				break;
			}
			
			LogItem item = new LogItem(INNER_X, INNER_Y + 35 + (LogItem.HEIGHT * i), INNER_TEX_WIDTH, fontRenderer, logData.getOtherNumbers()[i]);
			logItems.add(item);
			
			ImageButton callButton = new ImageButton(10 + i, INNER_X + INNER_TEX_WIDTH - 18, INNER_Y + 35 + (LogItem.HEIGHT * i) + (LogItem.HEIGHT / 2) - 8, 16, 16, "numcall.png", 16, 16);
			callButtons.add(callButton);
			buttonList.add(callButton);
		}
		
		exceedsShownLimit = logData.getOtherNumbers().length > 7;
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		// TODO Auto-generated method stub
		super.doDraw(mouseX, mouseY, partialticks);
		
		GlStateManager.scale(dLittleFont, dLittleFont, 1);
		fontRenderer.drawString("Conference Details", scale(INNER_X + 15, uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);
		GlStateManager.scale(uLittleFont, uLittleFont, 1);
		
		for(LogItem item : logItems)
		{
			item.draw();
		}
		
		if (exceedsShownLimit)
		{
			String andMore = TextFormatting.ITALIC + "...and more...";
			int fontWidth = scale(fontRenderer.getStringWidth(andMore), dLittleFont);
			GlStateManager.scale(dLittleFont, dLittleFont, 1);
			fontRenderer.drawString(andMore, scale(INNER_X + (INNER_TEX_WIDTH / 2) - (fontWidth / 2), uLittleFont), scale(INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 25, uLittleFont), 0xFFFFFF);
			GlStateManager.scale(uLittleFont, uLittleFont, 1);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == back)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneRecents(phoneStack, hand, page));
		}
		
		if (button.id >= 10 && button.id <= 17)
		{
			int logItemID = button.id - 10;
			LogItem item = logItems.get(logItemID);
			GuiPhoneCall call = new GuiPhoneCall(phoneStack, hand, item.strPhoneNumber);
			Minecraft.getMinecraft().displayGuiScreen(call);
		}
	}

	private class LogItem
	{
		private int x;
		private int y;
		private int width;
		public static final int HEIGHT = 23;
		private FontRenderer fontRenderer;
		private String strPhoneNumber;
		
		public LogItem(int x, int y, int width, FontRenderer fontRenderer, int phoneNumber)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.fontRenderer = fontRenderer;
			strPhoneNumber = String.valueOf(phoneNumber);
		}
		
		public void draw()
		{
			// Head
			ResourceLocation headRL = new ResourceLocation(Reference.MODID, "textures/gui/telecom/loading.png");
			Contact contact = null;
			contact = phoneStackData.getContactByPhoneNumber(strPhoneNumber);
			if (contact != null)
			{
				headRL = GetHeadUtil.getHeadResourceLocation(contact.getUsername());
			}
		
			Minecraft.getMinecraft().getTextureManager().bindTexture(headRL);
			GlStateManager.color(1, 1, 1);
			drawScaledCustomSizeModalRect(x, y + 2, 0, 0, 160, 160, 19, 19, 160, 160);
			
			// Name/Number
			String contactName = TextFormatting.BOLD + "";
			
			contactName += contact != null ? contact.getUsername() : getFormattedPhoneNumber(strPhoneNumber);
			
			contactName = truncateIfExceeds(fontRenderer, contactName, width - 32, 1);
			fontRenderer.drawString(contactName, x + 32, y + 7, 0xFFFFFF);
		}
	}
}
