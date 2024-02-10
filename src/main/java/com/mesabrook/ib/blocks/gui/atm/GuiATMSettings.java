package com.mesabrook.ib.blocks.gui.atm;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.net.atm.FetchATMSettingsPacket;
import com.mesabrook.ib.net.atm.UpdateATMSettingsPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiATMSettings extends GuiATMBase {

	boolean firstTick = true;
	byte loadingFrame = 0;
	String error;
	BigDecimal cardChargeAmount;
	String cardChargeAccount;
	
	final String cardChargeAmountLabel = "New Card Amt:";
	final String cardChargeAccountLabel = "New Card Acct:";
	GuiTextField cardChargeAmountField;
	GuiTextField cardChargeAccountField;
	GuiImageLabelButton save;
	GuiImageLabelButton cancel;
	
	public GuiATMSettings(TileEntityATM atm)
	{
		super(atm);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		firstTick = true;
		
		cardChargeAccountField = new GuiTextField(0, fontRenderer, left + 2 + fontRenderer.getStringWidth(cardChargeAccountLabel) + 4, top + 60, 10, 15);
		cardChargeAccountField.width = texWidth - cardChargeAccountField.x + left - 4;
		cardChargeAccountField.setVisible(false);
		
		cardChargeAmountField = new GuiTextField(0, fontRenderer, cardChargeAccountField.x, top + 40, 10, 15);
		cardChargeAmountField.width = cardChargeAccountField.width;
		cardChargeAmountField.setVisible(false);
		
		save = new GuiImageLabelButton(0, midWidth + 2, cardChargeAccountField.y + cardChargeAccountField.height + 4, texWidth / 2 - 4, 15, "Save", null, 0, 0, 0, 0, ImageOrientation.Left)
				.setEnabledColor(0x00AA00);
		save.visible = false;
		cancel = new GuiImageLabelButton(0, left + 2, save.y, save.width, 15, "Cancel", null, 0, 0, 0, 0, ImageOrientation.Left)
				.setEnabledColor(0xFF0000);
		cancel.visible = false;
		
		buttonList.add(save);
		buttonList.add(cancel);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		cardChargeAmountField.drawTextBox();
		cardChargeAccountField.drawTextBox();
		
		if (firstTick)
		{
			FetchATMSettingsPacket fetch = new FetchATMSettingsPacket();
			fetch.atmPos = atm.getPos();
			PacketHandler.INSTANCE.sendToServer(fetch);
			
			firstTick = false;
		}
		
		if (error == null)
		{
			int frameNumber = ++loadingFrame / 10;
			if (loadingFrame >= 49)
			{
				loadingFrame = 0;
			}
			
			mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/sco/net_frame_" + frameNumber + ".png"));
			drawModalRectWithCustomSizedTexture(midWidth - 64, midHeight - 64, 0, 0, 128, 128, 128, 128);
			
			int stringWidth = fontRenderer.getStringWidth("Fetching Accounts...");
			fontRenderer.drawString("Fetching Settings...", midWidth - stringWidth / 2, midHeight - fontRenderer.FONT_HEIGHT / 2 + 66, 0xFFFFFF);
			return;
		}
		else if (!error.isEmpty())
		{
			fontRenderer.drawString(error, midWidth - fontRenderer.getStringWidth(error) / 2, midHeight - fontRenderer.FONT_HEIGHT / 2, 0xFF0000);
		}
		else
		{
			drawCenteredString(fontRenderer, TextFormatting.BOLD + "ATM Settings", midWidth, top + 30, 0xFFFFFF);
			fontRenderer.drawString(cardChargeAmountLabel, left + 2, top + 43, 0xFFFFFF);
			fontRenderer.drawString(cardChargeAccountLabel, left + 2, top + 63, 0xFFFFFF);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == cancel)
		{
			mc.displayGuiScreen(new GuiATMHome(atm));
		}
		else if (button == save)
		{
			BigDecimal newCardChargeAmount;
			try
			{
				newCardChargeAmount = new BigDecimal(cardChargeAmountField.getText()).setScale(2, RoundingMode.HALF_UP);
			}
			catch(NumberFormatException ex)
			{
				cardChargeAmountField.setTextColor(0xFF0000);
				return;
			}
			
			if (cardChargeAccountField.getText().isEmpty() || 
					cardChargeAccountField.getText().length() != 16 ||
					!cardChargeAccountField.getText().matches("\\d+"))
			{
				cardChargeAccountField.setTextColor(0xFF0000);
				return;
			}
			
			cancel.enabled = false;
			save.enabled = false;
			
			UpdateATMSettingsPacket update = new UpdateATMSettingsPacket();
			update.atmPos = atm.getPos();
			update.cardChargeAmount = newCardChargeAmount;
			update.cardChargeAccount = cardChargeAccountField.getText();
			PacketHandler.INSTANCE.sendToServer(update);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		if (cardChargeAmountField.textboxKeyTyped(typedChar, keyCode) | cardChargeAccountField.textboxKeyTyped(typedChar, keyCode))
		{
			cardChargeAccountField.setTextColor(14737632);
			cardChargeAmountField.setTextColor(14737632);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (cardChargeAmountField.mouseClicked(mouseX, mouseY, mouseButton) | cardChargeAccountField.mouseClicked(mouseX, mouseY, mouseButton))
		{
			cardChargeAccountField.setTextColor(14737632);
			cardChargeAmountField.setTextColor(14737632);
		}
	}
	
	public void setData(String error, BigDecimal chargeAmount, String chargeAccount)
	{
		this.error = error;
		this.cardChargeAmount = chargeAmount;
		this.cardChargeAccount = chargeAccount;
		
		cardChargeAmountField.setText(chargeAmount.toPlainString());
		cardChargeAmountField.setVisible(true);
		cardChargeAccountField.setText(chargeAccount);
		cardChargeAccountField.setVisible(true);
		
		save.visible = true;
		cancel.visible = true;
	}
	
	public void onSettingsSaved()
	{
		TileEntity te = mc.world.getTileEntity(atm.getPos());
		if (!(te instanceof TileEntityATM))
		{
			return;
		}
		
		mc.displayGuiScreen(new GuiATMHome((TileEntityATM)te));
	}
}
