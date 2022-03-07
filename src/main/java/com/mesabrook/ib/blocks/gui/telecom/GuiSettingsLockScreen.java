package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData.SecurityStrategies;
import com.mesabrook.ib.net.telecom.SecurityStrategySelectedPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;
import java.util.UUID;

public class GuiSettingsLockScreen extends GuiPhoneBase {

	GuiCheckBox pin;
	GuiCheckBox playerID;
	LabelButton reset;
	LabelButton apply;
	LabelButton back;
	LabelButton changePIN;
	LabelButton changeUUID;
	PINTextField pinValue;
	GuiTextField uuidValue;
	
	public GuiSettingsLockScreen(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		boolean usePin = phoneStackData.getSecurityStrategy() == SecurityStrategies.PIN;
		boolean useUUID = phoneStackData.getSecurityStrategy() == SecurityStrategies.UUID;
		
		pin = new GuiCheckBox(0, INNER_X + 10, INNER_Y + 52, new TextComponentTranslation("im.settings.pin").getFormattedText(), usePin);
		playerID = new GuiCheckBox(1, INNER_X + 10, INNER_Y + 69, new TextComponentTranslation("im.settings.uuid").getFormattedText(), useUUID);
		reset = new LabelButton(2, INNER_X + (INNER_TEX_WIDTH / 4), INNER_Y + INNER_TEX_HEIGHT - 33, new TextComponentTranslation("im.settings.reset").getFormattedText(), 0x0000FF);
		apply = new LabelButton(3, INNER_X + INNER_TEX_WIDTH - (INNER_TEX_WIDTH / 4), INNER_Y + INNER_TEX_HEIGHT - 33, new TextComponentTranslation("im.settings.apply").getFormattedText(), 0x0000FF);
		apply.x = apply.x - apply.width;
		back = new LabelButton(4, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);
		changePIN = new LabelButton(5, 0, pin.y + 2, new TextComponentTranslation("im.settings.changepin").getFormattedText(), 0x0000FF);
		changePIN.x = INNER_X + INNER_TEX_WIDTH - changePIN.width - 5;
		changePIN.visible = usePin;
		changeUUID = new LabelButton(6, 0, playerID.y + 2, new TextComponentTranslation("im.settings.changeuuid").getFormattedText(), 0x0000FF);
		changeUUID.x = INNER_X + INNER_TEX_WIDTH - changeUUID.width - 5;
		changeUUID.visible = useUUID;
		
		pinValue = new PINTextField(7, fontRenderer, pin.x + pin.width + 4, pin.y-4, INNER_X + INNER_TEX_WIDTH - (pin.x + pin.width) - 7, 20);
		pinValue.setMaskedText(String.valueOf(phoneStackData.getPin()));
		pinValue.setVisible(false);
		uuidValue = new GuiTextField(8, fontRenderer, playerID.x + playerID.width + 4, playerID.y-4, INNER_X + INNER_TEX_WIDTH - (playerID.x + playerID.width) - 7, 20);
		uuidValue.setVisible(false);
		uuidValue.setMaxStringLength(36);
		if (phoneStackData.getUuid() != null)
		{
			uuidValue.setText(phoneStackData.getUuid().toString());
		}
		
		buttonList.addAll(ImmutableList.of(pin, playerID, reset, apply, back, changePIN, changeUUID));
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		fontRenderer.drawString(new TextComponentTranslation("im.settings.securitytitle").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
		fontRenderer.drawString(new TextComponentTranslation("im.settings.strategy").getFormattedText(), INNER_X + 3, INNER_Y + 36, 0x4444FF);
		
		pinValue.drawTextBox();
		uuidValue.drawTextBox();
		
		GlStateManager.color(1, 1, 1);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == pin)
		{
			GuiCheckBox checkBox = (GuiCheckBox)button;
			playerID.setIsChecked(false);
			changeUUID.visible = false;
			uuidValue.setVisible(false);
			changePIN.visible = false;
			pinValue.setVisible(checkBox.isChecked());
			pinValue.setMaskedText("");
		}
		
		if (button == playerID)
		{
			GuiCheckBox checkBox = (GuiCheckBox)button;
			pin.setIsChecked(false);
			changePIN.visible = false;
			changeUUID.visible = false;
			pinValue.setVisible(false);
			pinValue.setText("");
			uuidValue.setVisible(checkBox.isChecked());
			
			if (uuidValue.getText().isEmpty())
			{
				uuidValue.setText(Minecraft.getMinecraft().player.getUniqueID().toString());
			}
		}
		
		if (button == changePIN)
		{
			changePIN.visible = false;
			pinValue.setVisible(true);
			pinValue.setMaskedText("");
		}
		
		if (button == changeUUID)
		{
			changeUUID.visible = false;
			uuidValue.setVisible(true);
			
			if (uuidValue.getText().isEmpty())
			{
				uuidValue.setText(Minecraft.getMinecraft().player.getUniqueID().toString());
			}
		}
		
		if (button == apply)
		{
			SecurityStrategySelectedPacket packet = new SecurityStrategySelectedPacket();
			packet.hand = hand.ordinal();
			packet.guiScreenClassForRefresh = GuiSettingsLockScreen.class.getName();
			
			if (pin.isChecked())
			{
				if (pinValue.getMaskedText().isEmpty())
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.settings.pinerror").getFormattedText(), 0xFF0000));
					return;
				}
				
				packet.pin = Integer.parseInt(pinValue.getMaskedText());
			}
			
			if (playerID.isChecked())
			{
				if (uuidValue.getText().isEmpty())
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Player UUID Required!", 0xFF0000));
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.settings.uuiderror").getFormattedText(), 0xFF0000));
					return;
				}
				
				UUID parsedPlayerID;
				try
				{
					parsedPlayerID = UUID.fromString(uuidValue.getText());
				}
				catch(IllegalArgumentException ex)
				{
					Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.settings.tryagain").getFormattedText(), 0xFF0000));
					return;
				}
				
				packet.playerID = parsedPlayerID;
			}
			
			PacketHandler.INSTANCE.sendToServer(packet);

			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.settings.saved").getFormattedText(), 0xFFFFFF));

			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsLockScreen(phoneStack, hand));
		}
		
		if (button == reset)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsLockScreen(phoneStack, hand));
		}
		
		if (button == back)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettings(phoneStack, hand));
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		pinValue.mouseClicked(mouseX, mouseY, mouseButton);
		uuidValue.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		pinValue.textboxKeyTyped(typedChar, keyCode);
		uuidValue.textboxKeyTyped(typedChar, keyCode);
	}
}
