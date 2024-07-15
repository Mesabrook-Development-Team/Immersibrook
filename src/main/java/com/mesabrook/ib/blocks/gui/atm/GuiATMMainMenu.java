package com.mesabrook.ib.blocks.gui.atm;

import java.io.IOException;

import com.mesabrook.ib.apimodels.account.Account;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityATM;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextFormatting;

public class GuiATMMainMenu extends GuiATMBase {

	Account account;
	
	GuiImageLabelButton back;
	GuiImageLabelButton withdraw;
	GuiImageLabelButton deposit;
	GuiImageLabelButton purchaseCard;
	
	public GuiATMMainMenu(TileEntityATM atm, Account account)
	{
		super(atm);
		this.account = account;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		back = new GuiImageLabelButton(0, left + 3, top + 30, 30, 15, "Back", null, 0, 0, 0, 0, ImageOrientation.Left);
		withdraw = new GuiImageLabelButton(0, left + 3, top + 66, texWidth - 6, 20, "Withdraw", null, 0, 0, 0, 0, ImageOrientation.Left)
					.setEnabledColor(0x00AA00);
		deposit = new GuiImageLabelButton(0, left + 3, top + 92, texWidth - 6, 20, "Deposit", null, 0, 0, 0, 0, ImageOrientation.Left)
				.setEnabledColor(0x00AA00);
		purchaseCard = new GuiImageLabelButton(0, left + 3, top + 116, texWidth - 6, 20, "Purchase New Card", null, 0, 0, 0, 0, ImageOrientation.Left)
				.setEnabledColor(0x00AA00);
		
		buttonList.add(back);
		buttonList.add(withdraw);
		buttonList.add(deposit);
		buttonList.add(purchaseCard);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		drawCenteredString(fontRenderer, TextFormatting.BOLD + "Main Menu", midWidth, top + 30, 0xFFFFFF);
		int accountNameColor = account.CompanyID == 0 ? 0x00FF00 : 0xFF0000;
		fontRenderer.drawString(account.Description, midWidth - (fontRenderer.getStringWidth(account.Description) / 2), top + 30 + fontRenderer.FONT_HEIGHT, accountNameColor);
		
		fontRenderer.drawString(TextFormatting.BOLD + "Current Balance: " + TextFormatting.RESET + account.Balance.toPlainString(), left + 3, top + 51, 0xFFFFFF);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		playButtonSound();
		if (button == back)
		{
			mc.displayGuiScreen(new GuiATMHome(atm));
		}
		
		if (button == withdraw)
		{
			mc.displayGuiScreen(new GuiATMWithdraw(atm, account));
		}
		
		if (button == deposit)
		{
			mc.displayGuiScreen(new GuiATMDeposit(atm, account));
		}
		
		if (button == purchaseCard)
		{
			mc.displayGuiScreen(new GuiATMNewCard(atm, account));
		}
	}
}
