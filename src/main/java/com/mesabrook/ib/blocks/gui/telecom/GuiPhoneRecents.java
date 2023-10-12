package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData.Contact;
import com.mesabrook.ib.net.telecom.GetPhoneRecentsPacket;
import com.mesabrook.ib.util.PhoneLogState;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mesabrook.ib.util.saveData.PhoneLogData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiPhoneRecents extends GuiPhoneBase {

	LabelButton keypad;
	LabelButton recents;
	MinedroidButton firstPage;
	MinedroidButton prevPage;
	MinedroidButton nextPage;
	MinedroidButton lastPage;
	
	List<PhoneLogData.LogData> logDatum;
	ArrayList<LogItem> logItems = new ArrayList<>(4);
	ArrayList<ImageButton> callButtons = new ArrayList<>();
	boolean isInitializing = true;
	int page = 1;
	
	public GuiPhoneRecents(ItemStack phoneStack, EnumHand hand, int page) {
		super(phoneStack, hand);
		this.page = page;
	}
	
	public GuiPhoneRecents(ItemStack phoneStack, EnumHand hand)
	{
		this(phoneStack, hand, 1);
	}

	@Override
	protected String getInnerTextureFileName() {
		return phoneStackData.getIconTheme() + "/app_screen.png";
	}

	@Override
	public void initGui() {
		super.initGui();
		
		keypad = new LabelButton(0, 0, INNER_Y + INNER_TEX_HEIGHT - 34, "Keypad", 0xFFFFFF);
		keypad.x = INNER_X + INNER_TEX_WIDTH / 2 - keypad.width - 20;
		buttonList.add(keypad);
		
		recents = new LabelButton(1, 0, INNER_Y + INNER_TEX_HEIGHT - 34, "Recents", 0xFFFFFF);
		recents.x = INNER_X + INNER_TEX_WIDTH / 2 + 20;
		buttonList.add(recents);	
		
		firstPage = new MinedroidButton(2, INNER_X + 4, INNER_Y + INNER_TEX_HEIGHT - 50, 10, "<<", 0xFFFFFF);
		firstPage.enabled = false;
		buttonList.add(firstPage);
		prevPage = new MinedroidButton(3, INNER_X + 18, INNER_Y + INNER_TEX_HEIGHT - 50, 10, "<", 0xFFFFFF);
		prevPage.enabled = false;
		buttonList.add(prevPage);
		lastPage = new MinedroidButton(4, INNER_X + INNER_TEX_WIDTH - 12, INNER_Y + INNER_TEX_HEIGHT - 50, 10, ">>", 0xFFFFFF);
		lastPage.enabled = false;
		buttonList.add(lastPage);
		nextPage = new MinedroidButton(5, INNER_X + INNER_TEX_WIDTH - 26, INNER_Y + INNER_TEX_HEIGHT - 50, 10, ">", 0xFFFFFF);
		nextPage.enabled = false;
		buttonList.add(nextPage);
		
		// Add call buttons
		callButtons.clear();
		for(int i = 0; i < 4; i++)
		{
			ImageButton callButton = new ImageButton(10 + i, INNER_X + INNER_TEX_WIDTH - 18, INNER_Y + 35 + (LogItem.HEIGHT * i) + (LogItem.HEIGHT / 2) - 4, 16, 16, "numcall.png", 16, 16);
			callButton.visible = false;
			buttonList.add(callButton);
			callButtons.add(callButton);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 0)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneCall(phoneStack, hand));
		}
		
		if (button.id == firstPage.id)
		{
			page = 1;
			populateLogItems();
		}
		
		if (button.id == prevPage.id)
		{
			page -= 1;
			populateLogItems();
		}
		
		if (button.id == nextPage.id)
		{
			page++;
			populateLogItems();
		}
		
		if (button.id == lastPage.id)
		{
			page = (logDatum.size() / 4) + 1;
			populateLogItems();
		}
		
		if (button.id >= 10 && button.id <= 13)
		{
			int logIndex = button.id - 10;
			
			if (logItems.size() >= logIndex + 1)
			{
				LogItem item = logItems.get(logIndex);
				
				if (item.logData.getOtherNumbers().length == 1)
				{
					GuiPhoneCall callScreen = new GuiPhoneCall(phoneStack, hand, Integer.toString(item.logData.getOtherNumbers()[0]));
					Minecraft.getMinecraft().displayGuiScreen(callScreen);
				}
			}
		}
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		fontRenderer.drawString("Recents", scale(INNER_X + 4, uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);
		
		for(LogItem logItem : logItems)
		{
			logItem.draw();
		}
	}
	
	public void setLogDatum(List<PhoneLogData.LogData> logDatum)
	{
		this.logDatum = logDatum;
		
		populateLogItems();
		setIsInitializing(false);
	}
	
	private void populateLogItems()
	{
		logItems.clear();
		int counter = 0;
		for(PhoneLogData.LogData logData : logDatum.stream().sorted((o1, o2) -> Long.compare(o1.getCallTime(), o2.getCallTime()) * -1).skip(4 * (page - 1)).limit(4).collect(Collectors.toList()))
		{
			logItems.add(new LogItem(fontRenderer, logData, INNER_X, INNER_Y + 35 + (LogItem.HEIGHT * counter), INNER_TEX_WIDTH));
			callButtons.get(counter).visible = logData.getOtherNumbers().length == 1;
			counter++;
		}
		
		for(int i = counter; i < 4; i++)
		{
			callButtons.get(i).visible = false;
		}
		
		firstPage.enabled = page > 1;
		prevPage.enabled = page > 1;
		nextPage.enabled = logDatum.size() > page * 4;
		lastPage.enabled = logDatum.size() > page * 4;
	}
	
	private void setIsInitializing(boolean isInitializing)
	{
		this.isInitializing = isInitializing;
		firstPage.visible = !isInitializing;
		prevPage.visible = !isInitializing;
		nextPage.visible = !isInitializing;
		lastPage.visible = !isInitializing;
		
		// Set visibility of future buttons
	}
	
	@Override
	protected void firstDrawingTick(int mouseX, int mouseY, float partialTicks) {
		super.firstDrawingTick(mouseX, mouseY, partialTicks);
		
		GetPhoneRecentsPacket getData = new GetPhoneRecentsPacket();
		getData.phoneNumber = getCurrentPhoneNumber();
		PacketHandler.INSTANCE.sendToServer(getData);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		for(LogItem logItem : logItems)
		{
			logItem.mouseClicked(mouseX, mouseY);
		}
	}
	
	private class LogItem
	{
		FontRenderer fontRenderer;
		PhoneLogData.LogData logData;
		private int x;
		private int y;
		private int width;
		public static final int HEIGHT = 34;
		
		public LogItem(FontRenderer fontRenderer, PhoneLogData.LogData logData)
		{
			this.fontRenderer = fontRenderer;
			this.logData = logData;
		}
		
		public LogItem(FontRenderer fontRenderer, PhoneLogData.LogData logData, int x, int y, int width)
		{
			this(fontRenderer, logData);
			this.x = x;
			this.y = y;
			this.width = width;
		}
		
		public void draw()
		{
			// Head
			ResourceLocation headRL = new ResourceLocation(Reference.MODID, "textures/gui/telecom/loading.png");
			Contact contact = null;
			if (logData.getOtherNumbers().length == 1)
			{
				contact = phoneStackData.getContactByPhoneNumber(Integer.toString(logData.getOtherNumbers()[0]));
				if (contact != null)
				{
					headRL = GetHeadUtil.getHeadResourceLocation(contact.getUsername());
				}
			}

			Minecraft.getMinecraft().getTextureManager().bindTexture(headRL);
			GlStateManager.color(1, 1, 1);
			drawScaledCustomSizeModalRect(x, y + 2, 0, 0, 160, 160, 30, 30, 160, 160);
			
			// Name/Number
			String contactName = TextFormatting.BOLD + "";
			
			if (logData.getOtherNumbers().length == 1)
			{
				contactName += contact != null ? contact.getUsername() : getFormattedPhoneNumber(Integer.toString(logData.getOtherNumbers()[0]));
			}
			else
			{
				contactName += "Conference";
			}
			
			contactName = truncateIfExceeds(fontRenderer, contactName, width - 32, 1);
			fontRenderer.drawString(contactName, x + 32, y + 2, 0xFFFFFF);
			
			// Status
			fontRenderer.drawString(logData.getPhoneLogState().toString(), x + 32, y + 2 + fontRenderer.FONT_HEIGHT, logData.getPhoneLogState().getColor());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yy HH:mm");
			String dateString = dateFormat.format(logData.getCallTimeCalendar().getTime());
			fontRenderer.drawString(dateString, x + 32, y + 2 + fontRenderer.FONT_HEIGHT * 2, 0xFFFFFF);
			
			if (logData.getPhoneLogState() == PhoneLogState.Answered || logData.getPhoneLogState() == PhoneLogState.Outgoing)
			{
				String timeSpentString = "";
				long hours = logData.getCallLength() / 3600;
				long minutes = logData.getCallLength() / 60 - hours * 60;
				long seconds = logData.getCallLength() - minutes * 60 - hours * 3600;
				
				if (hours > 0)
				{
					timeSpentString += "" + hours + ":";
				}
				
				timeSpentString += String.format("%02d:%02d", minutes, seconds);
				int stringWidth = fontRenderer.getStringWidth(timeSpentString);
				fontRenderer.drawString(timeSpentString, x + width - 2 - stringWidth, y + 2, 0x666666);
			}
			
			// Page Number
			String pageString = "Page " + page + " of " + ((logDatum.size() / 4) + 1);
			int width = fontRenderer.getStringWidth(pageString);
			fontRenderer.drawString(pageString, INNER_X + (INNER_TEX_WIDTH / 2) - (width / 2), INNER_Y + INNER_TEX_HEIGHT - 50, 0xFFFFFF);
		}
		
		public void mouseClicked(int mouseX, int mouseY)
		{
			if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + HEIGHT && logData.getOtherNumbers().length > 1)
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneConferenceDetails(phoneStack, hand, page, logData));
			}
		}
	}
}
