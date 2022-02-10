package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
import com.mesabrook.ib.net.telecom.SaveContactPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiAddressBookDetails extends GuiPhoneBase {

	GuiTextField username;
	GuiTextField phoneNumber;
	GuiTextField address;
	LabelButton back;
	LabelButton save;
	LabelButton reset;
	
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
		return "app_screen_contacts.png";
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
		int lowerY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 28;
		reset = new LabelButton(1, 0, lowerY, "Reset", 0x0000FF);
		reset.x = INNER_X + (INNER_TEX_WIDTH / 2) - reset.width - 10;
		
		save = new LabelButton(2, 0, lowerY, "Save", 0x0000FF);
		save.x = INNER_X + (INNER_TEX_WIDTH / 2) + 10;
		
		buttonList.add(back);
		buttonList.add(save);
		buttonList.add(reset);
		
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
		
		String titleText = contact == null ? "New Contact" : "Edit Contact";
		fontRenderer.drawString(titleText, INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
		
		fontRenderer.drawString("Username:", INNER_X + 3, INNER_Y + 35, 0x000000);
		fontRenderer.drawString("Phone Number:", INNER_X + 3, INNER_Y + 69, 0x000000);
		fontRenderer.drawString("Address:", INNER_X + 3, INNER_Y + 103, 0x000000);
		
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
			username.setText(contact.getUsername());
			phoneNumber.setText(getFormattedPhoneNumber(contact.getPhoneNumber()));
			address.setText(contact.getAddress());
		}
		
		if (button == save)
		{
			if (username.getText().isEmpty())
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Username is required", 0xFF0000));
				return;
			}
			
			String enteredPhoneNumber = phoneNumber.getText().replace("-", "");
			if (enteredPhoneNumber.isEmpty())
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Phone Number is required", 0xFF0000));
				return;
			}
			
			if (enteredPhoneNumber.length() != 7)
			{
				Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Phone Number must be valid", 0xFF0000));
				return;
			}
			
			NBTData.Contact newContact = new NBTData.Contact();
			newContact.setIdentifier(contact != null ? contact.getIdentifier() : null);
			newContact.setUsername(username.getText());
			newContact.setPhoneNumber(enteredPhoneNumber);
			newContact.setAddress(address.getText());
			
			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Saved successfully!"));
			Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBook(phoneStack, hand));
			
			SaveContactPacket saveContact = new SaveContactPacket();
			saveContact.hand = hand;
			saveContact.contact = newContact;
			saveContact.guiClassName = GuiAddressBook.class.getName();
			PacketHandler.INSTANCE.sendToServer(saveContact);
		}
	}
}
