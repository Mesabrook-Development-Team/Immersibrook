package com.mesabrook.ib.blocks.gui.atm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.mesabrook.ib.apimodels.account.Account;
import com.mesabrook.ib.blocks.BlockATM;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.gui.PINTextField;
import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.net.atm.CreateNewDebitCardATMPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;

public class GuiATMNewCard extends GuiATMBase {
	private Account account;
	
	GuiImageLabelButton back; 
	PINTextField pin;
	GuiImageLabelButton create;
	
	private String error = "";
	
	public GuiATMNewCard(TileEntityATM atm, Account account) {
		super(atm);
		this.account = account;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		back = new GuiImageLabelButton(0, left + 3, top + 30, 30, 15, "Back", null, 0, 0, 0, 0, ImageOrientation.Left);
		pin = new PINTextField(0, fontRenderer, midWidth + 2, midHeight - 7, 40, 15);
		pin.setFocused(true);
		create = new GuiImageLabelButton(0, left + 20, pin.y + pin.height + 4, texWidth - 40, 15, "Print New Card", null, 0, 0, 0, 0, ImageOrientation.Left)
				.setEnabledColor(0x00AA00);
		
		buttonList.add(back);
		buttonList.add(create);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		pin.drawTextBox();
		
		drawCenteredString(fontRenderer, TextFormatting.BOLD + "New Debit Card", midWidth, top + 30, 0xFFFFFF);
		int accountNameColor = account.CompanyID == 0 ? 0x00FF00 : 0xFF0000;
		fontRenderer.drawString(account.Description, midWidth - (fontRenderer.getStringWidth(account.Description) / 2), top + 30 + fontRenderer.FONT_HEIGHT, accountNameColor);
		drawCenteredString(fontRenderer, TextFormatting.BOLD + "New Card Fee: " + TextFormatting.RESET + (atm.getCardChargeAmount().compareTo(new BigDecimal(0)) <= 0 ? "Free" : atm.getCardChargeAmount().toPlainString()), midWidth, pin.y - fontRenderer.FONT_HEIGHT - 4, 0xFFFFFF);
		fontRenderer.drawString("Card PIN:", midWidth - 2 - fontRenderer.getStringWidth("Card Pin:"), pin.y + 3, 0xFFFFFF);
		
		if (!error.isEmpty())
		{
			List<String> errorLines = fontRenderer.listFormattedStringToWidth(error, texWidth - 40);
			for(int i = 0; i < errorLines.size(); i++)
			{
				String errorLine = errorLines.get(i);
				fontRenderer.drawString(errorLine, midWidth - (fontRenderer.getStringWidth(errorLine) / 2), create.y + create.height + 4 + fontRenderer.FONT_HEIGHT * i, 0xFF0000);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == back)
		{
			mc.displayGuiScreen(new GuiATMMainMenu(atm, account));
		}
		
		if (button == create)
		{
			if (pin.getText().length() != 4)
			{
				pin.setTextColor(0xFF0000);
				return;
			}
			
			try
			{
				Short.parseShort(pin.getMaskedText());
			}
			catch(NumberFormatException ex)
			{
				pin.setTextColor(0xFF0000);
				return;
			}
			
			IBlockState blockState = atm.getWorld().getBlockState(atm.getPos());
			EnumFacing facing = blockState.getValue(BlockATM.FACING);
			
			pin.setEnabled(false);
			create.enabled = false;
			
			CreateNewDebitCardATMPacket createPacket = new CreateNewDebitCardATMPacket();
			createPacket.accountID = account.AccountID;
			createPacket.pin = pin.getMaskedText();
			createPacket.spawnPos = atm.getPos().offset(facing);
			createPacket.atmPos = atm.getPos();
			PacketHandler.INSTANCE.sendToServer(createPacket);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		pin.textboxKeyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		pin.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public void onResponse(String error)
	{
		if (error.isEmpty())
		{
			mc.displayGuiScreen(null);
			return;
		}
		
		this.error = error;
		
		create.enabled = true;
		pin.setEnabled(true);
	}
}
