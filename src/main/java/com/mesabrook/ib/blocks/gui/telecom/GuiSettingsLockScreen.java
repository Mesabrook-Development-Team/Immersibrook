package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData.SecurityStrategies;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.net.telecom.ToggleUnlockSliderPacket;
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
	GuiCheckBox useButtonInsteadOfSlider;
	MinedroidButton reset;
	MinedroidButton apply;
	MinedroidButton factoryReset;
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
		return phoneStackData.getIconTheme() + "/app_screen.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();

		boolean usePin = phoneStackData.getSecurityStrategy() == SecurityStrategies.PIN;
		boolean useUUID = phoneStackData.getSecurityStrategy() == SecurityStrategies.UUID;
		boolean useButton = phoneStackData.getUseButtonInsteadOfSlider();
		
		pin = new GuiCheckBox(0, INNER_X + 10, INNER_Y + 52, new TextComponentTranslation("im.settings.pin").getFormattedText(), usePin);
		playerID = new GuiCheckBox(1, INNER_X + 10, INNER_Y + 69, new TextComponentTranslation("im.settings.uuid").getFormattedText(), useUUID);
		useButtonInsteadOfSlider = new GuiCheckBox(70, INNER_X + 10, INNER_Y + 120, "Use Unlock Button?", useButton);


		int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
		reset = new MinedroidButton(2, INNER_X + 45, lowerControlsY - 10, 32, new TextComponentTranslation("im.musicapp.buttonreset").getFormattedText(), 0xFFFFFF);
		apply = new MinedroidButton(3, INNER_X + 85, lowerControlsY - 10, 32, new TextComponentTranslation("im.settings.apply").getFormattedText(), 0xFFFFFF);
		factoryReset = new MinedroidButton(69, INNER_X + 10, INNER_Y + 90, 85, new TextComponentTranslation("im.settings.factoryreset").getFormattedText(), 0xFF0000);

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
		
		buttonList.addAll(ImmutableList.of(pin, playerID, reset, apply, back, changePIN, changeUUID, useButtonInsteadOfSlider));
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		fontRenderer.drawString(new TextComponentTranslation("im.settings.securitytitle1").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
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

		if(button == factoryReset)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiFactoryResetConfirmation(phoneStack, hand));
		}
		
		if (button == apply)
		{
			CustomizationPacket packet = new CustomizationPacket();
			packet.hand = hand.ordinal();
			packet.newName = phoneStack.getDisplayName();
			packet.guiClassName = GuiSettingsLockScreen.class.getName();
			packet.iconTheme = phoneStackData.getIconTheme();
			packet.lockBackground = phoneStackData.getLockBackground();
			packet.homeBackground = phoneStackData.getHomeBackground();
			packet.lockTone = phoneStackData.getChatTone();
			packet.ringtone = phoneStackData.getRingTone();
			packet.setShowIRLTime = phoneStackData.getShowIRLTime();
			packet.useMilitaryTime = phoneStackData.getShowingMilitaryIRLTime();
			packet.toggleDebugMode = phoneStackData.getIsDebugModeEnabled();
			packet.resetName = false;
			
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

			ToggleUnlockSliderPacket toggleUnlockSliderPacket = new ToggleUnlockSliderPacket();
			toggleUnlockSliderPacket.hand = hand.ordinal();
			toggleUnlockSliderPacket.guiClassName = GuiSettingsLockScreen.class.getName();
			toggleUnlockSliderPacket.nextGuiClassName = GuiSettingsLockScreen.class.getName();
			toggleUnlockSliderPacket.useButtonInsteadOfSlider = useButtonInsteadOfSlider.isChecked();
			
			PacketHandler.INSTANCE.sendToServer(packet);
			PacketHandler.INSTANCE.sendToServer(toggleUnlockSliderPacket);

			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.settings.saved").getFormattedText(), 0xFFFFFF));

			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsLockScreen(phoneStack, hand));
		}
		
		if (button == reset)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsLockScreen(phoneStack, hand));
		}
		
		if (button == back)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsSecurity(phoneStack, hand));
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
