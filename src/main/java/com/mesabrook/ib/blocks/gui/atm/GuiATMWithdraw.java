package com.mesabrook.ib.blocks.gui.atm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.mesabrook.ib.apimodels.account.Account;
import com.mesabrook.ib.blocks.BlockATM;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.net.atm.WithdrawATMPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;

public class GuiATMWithdraw extends GuiATMBase {

	Account account;
	
	GuiImageLabelButton back; 
	GuiTextField amount;
	GuiImageLabelButton withdraw;
	
	String error = "";
	
	public GuiATMWithdraw(TileEntityATM atm, Account account) {
		super(atm);
		this.account = account;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		back = new GuiImageLabelButton(0, left + 3, top + 30, 30, 15, "Back", null, 0, 0, 0, 0, ImageOrientation.Left);
		amount = new GuiTextField(0, fontRenderer, left + 20, midHeight - 7, texWidth - 40, 15);
		amount.setFocused(true);
		withdraw = new GuiImageLabelButton(0, amount.x, amount.y + amount.height + 4, amount.width, 15, "Withdraw", null, 0, 0, 0, 0, ImageOrientation.Left)
				.setEnabledColor(0x00AA00);
		
		buttonList.add(back);
		buttonList.add(withdraw);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		amount.drawTextBox();
		
		drawCenteredString(fontRenderer, TextFormatting.BOLD + "Withdraw", midWidth, top + 30, 0xFFFFFF);
		int accountNameColor = account.CompanyID == 0 ? 0x00FF00 : 0xFF0000;
		fontRenderer.drawString(account.Description, midWidth - (fontRenderer.getStringWidth(account.Description) / 2), top + 30 + fontRenderer.FONT_HEIGHT, accountNameColor);
		drawCenteredString(fontRenderer, TextFormatting.BOLD + "Current Balance: " + TextFormatting.RESET + account.Balance.toPlainString(), midWidth, amount.y - fontRenderer.FONT_HEIGHT - 4, 0xFFFFFF);
		
		if (!error.isEmpty())
		{
			List<String> errorLines = fontRenderer.listFormattedStringToWidth(error, texWidth - 40);
			for(int i = 0; i < errorLines.size(); i++)
			{
				String errorLine = errorLines.get(i);
				fontRenderer.drawString(errorLine, midWidth - (fontRenderer.getStringWidth(errorLine) / 2), withdraw.y + withdraw.height + 4 + fontRenderer.FONT_HEIGHT * i, 0xFF0000);
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
		
		if (button == withdraw)
		{
			error = "";
			
			BigDecimal withdrawAmount;
			try
			{
				withdrawAmount = new BigDecimal(amount.getText());
			}
			catch(NumberFormatException ex)
			{
				amount.setTextColor(0xFF0000);
				return;
			}
			
			if (withdrawAmount.compareTo(new BigDecimal(0)) <= 0 || withdrawAmount.compareTo(account.Balance) > 0)
			{
				amount.setTextColor(0xFF0000);
				return;
			}
			
			amount.setEnabled(false);
			withdraw.enabled = false;
			
			IBlockState state = atm.getWorld().getBlockState(atm.getPos());
			EnumFacing atmFacing = state.getValue(BlockATM.FACING);
			
			WithdrawATMPacket packet = new WithdrawATMPacket();
			packet.dispensePos = atm.getPos().offset(atmFacing);
			packet.accountID = account.AccountID;
			packet.amount = withdrawAmount;
			packet.companyIDOwner = atm.getCompanyIDOwner();
			PacketHandler.INSTANCE.sendToServer(packet);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		if (amount.textboxKeyTyped(typedChar, keyCode))
		{
			amount.setTextColor(14737632);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (amount.mouseClicked(mouseX, mouseY, mouseButton))
		{
			amount.setTextColor(14737632);
		}
	}

	public void onResponse(String error)
	{
		if (error.isEmpty())
		{
			mc.displayGuiScreen(null);
			return;
		}
		
		amount.setEnabled(true);
		withdraw.enabled = true;
		this.error = error;
	}
}
