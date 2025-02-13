package com.mesabrook.ib.blocks.gui.atm;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.mesabrook.ib.apimodels.account.Account;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.net.atm.DepositATMPacket;
import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiATMDeposit extends GuiATMBase {

	private Account account;
	private IndependentTimer timer;
	
	private BigDecimal pocketAmount;
	GuiImageLabelButton back; 
	GuiTextField amount;
	GuiImageLabelButton deposit;
	
	private String error = "";
	
	public GuiATMDeposit(TileEntityATM atm, Account account) {
		super(atm);
		this.account = account;
		timer = new IndependentTimer();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		pocketAmount = new BigDecimal(0);
		for(int i = 0; i < mc.player.inventory.getSizeInventory(); i++)
		{
			ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (!(stack.getItem() instanceof ItemMoney))
			{
				continue;
			}
			
			ItemMoney money = (ItemMoney)stack.getItem();
			pocketAmount = pocketAmount.add(new BigDecimal(money.getValue()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(stack.getCount())));
		}

		back = new GuiImageLabelButton(0, left + 3, top + 30, 30, 15, "Back", null, 0, 0, 0, 0, ImageOrientation.Left);
		amount = new GuiTextField(0, fontRenderer, left + 20, midHeight - 7, texWidth - 40, 15);
		amount.setFocused(true);
		deposit = new GuiImageLabelButton(0, amount.x, amount.y + amount.height + 4, amount.width, 15, "Deposit", null, 0, 0, 0, 0, ImageOrientation.Left)
				.setEnabledColor(0x00AA00);
		
		buttonList.add(back);
		buttonList.add(deposit);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		amount.drawTextBox();
		
		drawCenteredString(fontRenderer, TextFormatting.BOLD + "Deposit", midWidth, top + 30, 0xFFFFFF);
		int accountNameColor = account.CompanyID == 0 ? 0x00FF00 : 0xFF0000;
		fontRenderer.drawString(account.Description, midWidth - (fontRenderer.getStringWidth(account.Description) / 2), top + 30 + fontRenderer.FONT_HEIGHT, accountNameColor);
		drawCenteredString(fontRenderer, TextFormatting.BOLD + "Pocket Amount: " + TextFormatting.RESET + pocketAmount.toPlainString(), midWidth, amount.y - fontRenderer.FONT_HEIGHT - 4, 0xFFFFFF);
		
		if (!error.isEmpty())
		{
			List<String> errorLines = fontRenderer.listFormattedStringToWidth(error, texWidth - 40);
			for(int i = 0; i < errorLines.size(); i++)
			{
				String errorLine = errorLines.get(i);
				fontRenderer.drawString(errorLine, midWidth - (fontRenderer.getStringWidth(errorLine) / 2), deposit.y + deposit.height + 4 + fontRenderer.FONT_HEIGHT * i, 0xFF0000);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		playButtonSound();
		if (button == back)
		{
			mc.displayGuiScreen(new GuiATMMainMenu(atm, account));
		}
		
		if (button == deposit)
		{
			BigDecimal depositAmount;
			try
			{
				depositAmount = new BigDecimal(amount.getText());
			}
			catch(NumberFormatException ex)
			{
				amount.setTextColor(0xFF0000);
				return;
			}
			
			if (depositAmount.compareTo(pocketAmount) > 0)
			{
				amount.setTextColor(0xFF0000);
				return;
			}
			
			deposit.enabled = false;
			amount.setEnabled(false);
			
			DepositATMPacket packet = new DepositATMPacket();
			packet.accountID = account.AccountID;
			packet.amount = depositAmount;
			packet.companyIDOwner = atm.getCompanyIDOwner();
			PacketHandler.INSTANCE.sendToServer(packet);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);

		if(amount.textboxKeyTyped(typedChar, keyCode))
		{
			playButtonSound();
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		amount.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void onResponse(String error)
	{
		if (error.isEmpty())
		{
			mc.displayGuiScreen(null);
			ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
			packet.pos = Minecraft.getMinecraft().player.getPosition();
			packet.modID = "wbtc";
			packet.soundName = "bill_acceptor";
			PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(Minecraft.getMinecraft().player.dimension, Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, 25));
			return;
		}
		
		amount.setEnabled(true);
		deposit.enabled = true;
		this.error = error;
	}
}
