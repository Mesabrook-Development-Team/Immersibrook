package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData.Contact;
import com.mesabrook.ib.net.telecom.DeleteContactPacket;
import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.net.telecom.PhoneQueryResponsePacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.xml.soap.Text;

public class GuiAddressBook extends GuiPhoneBase {

	GuiTextField filter;
	MinedroidButton firstPage;
	MinedroidButton prevPage;
	MinedroidButton nextPage;
	MinedroidButton lastPage;
	MinedroidButton addContact;
	AddressBookItem[] items = new AddressBookItem[4];
	
	private int page = 1;
	private int maxPage = 1;
	
	private final String filterPlaceholder = "" + TextFormatting.GRAY + TextFormatting.ITALIC + new TextComponentTranslation("im.contacts.filter").getFormattedText();
	
	public GuiAddressBook(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen_contacts.png";
	}

	@Override
	public void initGui() {
		super.initGui();

		int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
		firstPage = new MinedroidButton(1, INNER_X + 3, lowerControlsY, 10, "<<", 0);
		prevPage = new MinedroidButton(2, firstPage.x + firstPage.width + 3, lowerControlsY, 10, "<", 0);
		lastPage = new MinedroidButton(4, INNER_X + INNER_TEX_WIDTH - 10 - 3, lowerControlsY, 10, ">>", 0);
		nextPage = new MinedroidButton(3, lastPage.x - 3 - 10, lowerControlsY, 10, ">", 0);
		addContact = new MinedroidButton(4, INNER_X + INNER_TEX_WIDTH - 25 - 3, INNER_Y + 17, 25, new TextComponentTranslation("im.contacts.buttonadd").getFormattedText(), 0x00FF00);
		filter = new GuiTextField(100, fontRenderer, prevPage.x + prevPage.width + 3, lowerControlsY + 2, nextPage.x - (prevPage.x + prevPage.width) - 6, 10);
		filter.setText(filterPlaceholder);
		
		loadAddressBookItems();
		
		buttonList.addAll(ImmutableList.<GuiButton>builder()
				.add(firstPage)
				.add(prevPage)
				.add(nextPage)
				.add(lastPage)
				.add(addContact)
				.build());
	}

	private void loadAddressBookItems() {
		items = new AddressBookItem[4];
		int counter = 0;
		List<Contact> filteredContactList = phoneStackData
				.getContacts()
				.stream()
				.filter(c -> filter.getText().isEmpty() || filter.getText().equalsIgnoreCase(filterPlaceholder) ? true : c.getUsername().toLowerCase().contains(filter.getText().toLowerCase()))
				.collect(Collectors.toList());
		
		for(Contact contact : filteredContactList
									.stream()
									.sorted(Comparator.comparing(ItemPhone.NBTData.Contact::getUsername))
									.skip(4 * (page - 1))
									.limit(4)
									.collect(Collectors.toList()))
		{
			items[counter] = new AddressBookItem(contact, INNER_X, INNER_Y + 35 + (AddressBookItem.HEIGHT * counter), INNER_TEX_WIDTH);
			counter++;
			
			if (counter > 3)
			{
				break;
			}
		}
		
		maxPage = (filteredContactList.size() / 4) + 1;
		
		firstPage.enabled = page > 1;
		prevPage.enabled = page > 1;
		nextPage.enabled = page < maxPage;
		lastPage.enabled = page < maxPage;
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {		
		fontRenderer.drawString(new TextComponentTranslation("im.contacts.apptitle").getFormattedText(), INNER_X + 3, INNER_Y + 20, 0xFFFFFF);
		filter.drawTextBox();
		
		for(int i = 0; i < 4; i++)
		{
			AddressBookItem item = items[i];
			if (item != null)
			{
				item.draw(fontRenderer);
			}
		}
		
		GlStateManager.color(1, 1, 1);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == addContact)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBookDetails(phoneStack, hand));
			return;
		}
		
		for(int i = 0; i < 4; i++)
		{
			AddressBookItem item = items[i];
			if (item != null)
			{
				item.actionPerformed(button);
			}
		}
		
		if (button == prevPage)
		{
			if (page > 1)
			{
				page--;
				loadAddressBookItems();
			}
		}
		
		if (button == nextPage)
		{
			if (page < maxPage)
			{
				page++;
				loadAddressBookItems();
			}
		}
		
		if (button == firstPage)
		{
			page = 1;
			loadAddressBookItems();
		}
		
		if (button == lastPage)
		{
			page = maxPage;
			loadAddressBookItems();
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (filter.mouseClicked(mouseX, mouseY, mouseButton))
		{
			if (filter.getText().equalsIgnoreCase(filterPlaceholder))
			{
				filter.setText("");
			}
		}
		else if (filter.getText().isEmpty())
		{
			filter.setText(filterPlaceholder);
		}
		
		for(int i = 0; i < 4; i++)
		{
			AddressBookItem item = items[i];
			if (item != null)
			{
				item.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		if (filter.textboxKeyTyped(typedChar, keyCode))
		{
			loadAddressBookItems();
		}
	}

	public class AddressBookItem extends Gui
	{
		private NBTData.Contact contact;
		private int x;
		private int y;
		private int width;
		boolean deleteClicked = false;
		
		private ImageButton callButton;
		private ImageButton detailsButton;
		private ImageButton deleteButton;
		
		private boolean isClicked;
		
		public static final int HEIGHT = 34;
		
		public AddressBookItem(NBTData.Contact contact, int x, int y, int width)
		{
			this.contact = contact;
			this.x = x;
			this.y = y;
			this.width = width;
		}

		public NBTData.Contact getContact() {
			return contact;
		}

		public void setContact(NBTData.Contact contact) {
			this.contact = contact;
		}
		
		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public void draw(FontRenderer font)
		{
			GlStateManager.color(1, 1, 1);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(GetHeadUtil.getHeadResourceLocation(getContact().getUsername()));
			drawScaledCustomSizeModalRect(x + 3, y, 0, 0, 160, 160, 32, 32, 160, 160);
			
			int availableSpace = getWidth() - (3 + 32 + 3);
			
			String text = TextFormatting.BOLD + getContact().getUsername();
			text = truncateIfExceeds(font, text, availableSpace, 1);			
			font.drawString(text, x + 3 + 32 + 3, y, 0);
			
			GlStateManager.scale(dLittleFont, dLittleFont, 1);
			font.drawString("Phone: " + GuiPhoneBase.getFormattedPhoneNumber(getContact().getPhoneNumber()), scale(x + 3 + 32 + 3, uLittleFont), scale(y + font.FONT_HEIGHT + 3, uLittleFont), 0);
			
			text = "Address: " + getContact().getAddress();
			text = truncateIfExceeds(font, text, availableSpace, dLittleFont);			
			font.drawString(text, scale(x + 3 + 32 + 3, uLittleFont), scale(y + font.FONT_HEIGHT * 2 + 4, uLittleFont), 0);
			GlStateManager.scale(uLittleFont, uLittleFont, 1);
			
			if (isClicked)
			{
				GlStateManager.color(1, 1, 1);
				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				ResourceLocation resourceLocation = new ResourceLocation(Reference.MODID, "textures/gui/telecom/address_book_click.png");
				Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
				
				drawScaledCustomSizeModalRect(x, y, 0, 0, this.width, this.HEIGHT, this.width, this.HEIGHT, this.width, this.HEIGHT);
				GlStateManager.disableAlpha();
			}
		}
	
		public void mouseClicked(int mouseX, int mouseY, int mouseButton)
		{
			boolean clicked = clickedInside(mouseX, mouseY);
			
			if (clicked && !isClicked)
			{
				callButton = new ImageButton(50, x + (this.width / 2) - 8 - 2 - 16 - 2, this.y + (this.HEIGHT / 2) - 8, 16, 16, "numcall.png", 32, 32, 32, 32);
				detailsButton = new ImageButton(51, x + (this.width / 2) - 8, this.y + (this.HEIGHT / 2) - 8, 16, 16, "btn_details.png", 32, 32, 32, 32);
				deleteButton = new ImageButton(52, x + (this.width / 2) + 8 + 2, this.y + (this.HEIGHT / 2) - 8, 16, 16, "btn_delete.png", 32, 32, 32, 32);
				
				buttonList.add(callButton);
				buttonList.add(detailsButton);
				buttonList.add(deleteButton);
			}
			
			if (!clicked && isClicked)
			{
				buttonList.remove(callButton);
				buttonList.remove(detailsButton);
				buttonList.remove(deleteButton);
				
				deleteClicked = false;
			}
			
			isClicked = clicked;
		}
		
		public void actionPerformed(GuiButton button)
		{
			if (button == callButton)
			{
				int nextID = TelecomClientHandlers.getNextHandlerID();
				TelecomClientHandlers.phoneQueryResponseHandlers.put(nextID, response -> 
				{
					if (response.responseType != PhoneQueryResponsePacket.ResponseTypes.idle)
					{
						Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.contacts.callinprogresstoast").getFormattedText(), 0xFF0000));
					}
					else
					{
						Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneCall(phoneStack, hand, getContact().getPhoneNumber()));
					}
				});
				
				PhoneQueryPacket queryPacket = new PhoneQueryPacket();
				queryPacket.forNumber = phoneStackData.getPhoneNumberString();
				queryPacket.clientHandlerCode = nextID;
				PacketHandler.INSTANCE.sendToServer(queryPacket);
			}
			
			if (button == detailsButton)
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBookDetails(phoneStack, hand, getContact()));
			}			
			
			if (button == deleteButton)
			{
				if (!deleteClicked)
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.contacts.deletetoast").getFormattedText(), 0xFF0000));
					deleteClicked = true;
					return;
				}
				
				DeleteContactPacket deletePacket = new DeleteContactPacket();
				deletePacket.hand = hand;
				deletePacket.guiClassName = GuiAddressBook.class.getName();
				deletePacket.contactToDelete = getContact().getIdentifier();
				PacketHandler.INSTANCE.sendToServer(deletePacket);
			}
			
			deleteClicked = false;
		}
		
		private boolean clickedInside(int mouseX, int mouseY)
		{
			return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.HEIGHT;
		}
	}
}
