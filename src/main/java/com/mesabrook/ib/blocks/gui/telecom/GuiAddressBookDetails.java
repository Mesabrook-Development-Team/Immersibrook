package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
import com.mesabrook.ib.net.telecom.DeleteContactPacket;
import com.mesabrook.ib.net.telecom.SaveContactPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiAddressBookDetails extends GuiPhoneBase {

	GuiTextField username;
	GuiTextField phoneNumber;
	GuiTextField address;
	LabelButton back;
	MinedroidButton save;
	MinedroidButton reset;
	ImageButton delete;
	
	boolean deleteClicked = false;
	
	NBTData.Contact contact = null;
	
	public GuiAddressBookDetails(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}
	
	public GuiAddressBookDetails(ItemStack phoneStack, EnumHand hand, NBTData.Contact contact)
	{
		this(phoneStack, hand);
		this.contact = contact;
	}

	@Override
	protected String getInnerTextureFileName() {
		return phoneStackData.getIconTheme() + "/app_screen_contacts.png";
	}

	@Override
	public void initGui() {
		super.initGui();
		
		username = new GuiTextField(101, fontRenderer, INNER_X + 3, INNER_Y + 46, INNER_TEX_WIDTH - 6, 20);
		phoneNumber = new GuiTextField(102, fontRenderer, INNER_X + 3, INNER_Y + 80, INNER_TEX_WIDTH - 6, 20);
		address = new GuiTextField(103, fontRenderer, INNER_X + 3, INNER_Y + 114, INNER_TEX_WIDTH - 6, 20);
		username.setMaxStringLength(Integer.MAX_VALUE);
		phoneNumber.setMaxStringLength(Integer.MAX_VALUE);
		address.setMaxStringLength(Integer.MAX_VALUE);

		back = new LabelButton(4, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);

		int lowerY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
		reset = new MinedroidButton(5, INNER_X + 45, lowerY - 10, 32, new TextComponentTranslation("im.musicapp.buttonreset").getFormattedText(), 0x006800);
		save = new MinedroidButton(6, INNER_X + 85, lowerY - 10, 32, new TextComponentTranslation("im.contacts.save").getFormattedText(), 0x006800);
		
		delete = new ImageButton(3, INNER_X + INNER_TEX_WIDTH - 16 - 3, INNER_Y + 16, 16, 16, "btn_delete.png", 32, 32, 32, 32);
		delete.visible = contact != null;
		
		buttonList.add(back);
		buttonList.add(save);
		buttonList.add(reset);
		buttonList.add(delete);
		
		if (contact != null)
		{
			username.setText(contact.getUsername());
			phoneNumber.setText(getFormattedPhoneNumber(contact.getPhoneNumber()));
			address.setText(contact.getAddress());
		}
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		String titleText = contact == null ? new TextComponentTranslation("im.contacts.new").getFormattedText() : new TextComponentTranslation("im.contacts.edit").getFormattedText();
		fontRenderer.drawString(titleText, INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
		
		fontRenderer.drawString(new TextComponentTranslation("im.contacts.displayname").getFormattedText(), INNER_X + 3, INNER_Y + 35, 0x000000);
		fontRenderer.drawString(new TextComponentTranslation("im.contacts.phone").getFormattedText(), INNER_X + 3, INNER_Y + 69, 0x000000);
		fontRenderer.drawString(new TextComponentTranslation("im.contacts.address").getFormattedText(), INNER_X + 3, INNER_Y + 103, 0x000000);
		
		username.drawTextBox();
		phoneNumber.drawTextBox();
		address.drawTextBox();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		username.mouseClicked(mouseX, mouseY, mouseButton);
		phoneNumber.mouseClicked(mouseX, mouseY, mouseButton);
		address.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		username.textboxKeyTyped(typedChar, keyCode);
		phoneNumber.textboxKeyTyped(typedChar, keyCode);
		address.textboxKeyTyped(typedChar, keyCode);
		
		if (keyCode == Keyboard.KEY_TAB)
		{
			if (username.isFocused())
			{
				username.setFocused(false);
				phoneNumber.setFocused(true);
			}
			else if (phoneNumber.isFocused())
			{
				phoneNumber.setFocused(false);
				address.setFocused(true);
			}
			else if (address.isFocused())
			{
				address.setFocused(false);
				username.setFocused(true);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == back)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBook(phoneStack, hand));
		}
		
		if (button == reset)
		{
			if (contact != null)
			{
				username.setText(contact.getUsername());
				phoneNumber.setText(getFormattedPhoneNumber(contact.getPhoneNumber()));
				address.setText(contact.getAddress());
			}
			else	
			{
				username.setText("");
				phoneNumber.setText("");
				address.setText("");
			}
		}
		
		if (button == save)
		{
			if (username.getText().isEmpty())
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.contacts.usernametoast").getFormattedText(), 0xFF0000));
				return;
			}
			
			String enteredPhoneNumber = phoneNumber.getText().replace("-", "");
			if (enteredPhoneNumber.isEmpty())
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.contacts.phonetoast").getFormattedText(), 0xFF0000));
				return;
			}
			
			boolean isAllNumbers = true;
			try
			{
				Integer.parseInt(enteredPhoneNumber);
			}
			catch (Exception ex)
			{
				isAllNumbers = false;
			}
			
			if (enteredPhoneNumber.length() != 7 || !isAllNumbers)
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.contacts.invalidtoast").getFormattedText(), 0xFF0000));
				return;
			}
			
			NBTData.Contact newContact = new NBTData.Contact();
			newContact.setIdentifier(contact != null ? contact.getIdentifier() : null);
			newContact.setUsername(username.getText());
			newContact.setPhoneNumber(enteredPhoneNumber);
			newContact.setAddress(address.getText());
			
			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.contacts.saved").getFormattedText()));
			Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBook(phoneStack, hand));
			
			SaveContactPacket saveContact = new SaveContactPacket();
			saveContact.hand = hand;
			saveContact.contact = newContact;
			saveContact.guiClassName = GuiAddressBook.class.getName();
			PacketHandler.INSTANCE.sendToServer(saveContact);
		}
		
		if (button == delete)
		{
			if (!deleteClicked)
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.contacts.deletetoast").getFormattedText(), 0xFF0000));
				deleteClicked = true;
				return;
			}
			
			Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBook(phoneStack, hand));
			
			DeleteContactPacket deletePacket = new DeleteContactPacket();
			deletePacket.hand = hand;
			deletePacket.guiClassName = GuiAddressBook.class.getName();
			deletePacket.contactToDelete = contact.getIdentifier();
			PacketHandler.INSTANCE.sendToServer(deletePacket);
		}
	}
}
