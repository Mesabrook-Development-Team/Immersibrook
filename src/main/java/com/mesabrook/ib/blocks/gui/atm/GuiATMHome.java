package com.mesabrook.ib.blocks.gui.atm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import com.mesabrook.ib.apimodels.account.Account;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.net.atm.FetchAccountsPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiATMHome extends GuiATMBase {
	
	private ArrayList<Account> accounts = null;
	private String error = null;
	boolean firstTick = true;
	GuiImageLabelButton settings;
	ImageButton prevPage;
	ImageButton nextPage;
	
	private int loadingFrame = 0;
	private int page = 0;
	
	public GuiATMHome(TileEntityATM atm)
	{
		super(atm);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		settings = new GuiImageLabelButton(0, left + texWidth - 25, top + 30, 20, 20, "", new ResourceLocation(Reference.MODID, "textures/gui/cog.png"), 16, 16, 16, 16, ImageOrientation.Center);
		settings.visible = false;
		
		if (mc.player.hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
		{
			IEmployeeCapability emp = mc.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			if (emp.getLocationID() != 0)
			{
				settings.visible = emp.getLocationEmployee().Location.CompanyID == atm.getCompanyIDOwner();
			}
		}
		
		prevPage = new ImageButton(0, left + 10, top + 36, 16, 16, new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 16, 16, 16, 16);
		prevPage.enabled = page != 0;
		prevPage.visible = accounts != null;
		nextPage = new ImageButton(0, left + 90, top + 36, 16, 16, new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_right.png"), 16, 16, 16, 16);
		nextPage.enabled = false;
		nextPage.visible = accounts != null;
		if (accounts != null)
		{
			nextPage.enabled = accounts.size() / 12 > 0;
		}
		
		addNonAccountButtons();
	}
	
	private void addNonAccountButtons()
	{
		buttonList.add(settings);
		buttonList.add(prevPage);
		buttonList.add(nextPage);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if (firstTick)
		{
			FetchAccountsPacket fetch = new FetchAccountsPacket();
			fetch.playerID = mc.player.getUniqueID();
			PacketHandler.INSTANCE.sendToServer(fetch);
			
			firstTick = false;
		}
		
		if (accounts == null && error == null)
		{
			int frameNumber = ++loadingFrame / 10;
			if (loadingFrame >= 49)
			{
				loadingFrame = 0;
			}
			
			mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/sco/net_frame_" + frameNumber + ".png"));
			drawModalRectWithCustomSizedTexture(midWidth - 64, midHeight - 64, 0, 0, 128, 128, 128, 128);
			
			int stringWidth = fontRenderer.getStringWidth("Fetching Accounts...");
			fontRenderer.drawString("Fetching Accounts...", midWidth - stringWidth / 2, midHeight - fontRenderer.FONT_HEIGHT / 2 + 66, 0xFFFFFF);
			return;
		}
		else if (error != null && !error.isEmpty())
		{
			int stringWidth = fontRenderer.getStringWidth(error);
			fontRenderer.drawString(error, midWidth - stringWidth / 2, midHeight - fontRenderer.FONT_HEIGHT / 2, 0xFF0000);
			return;
		}
		else if (accounts != null && buttonList.stream().allMatch(b -> b == settings || b == prevPage || b == nextPage))
		{
			setupButtons();
		}
		
		fontRenderer.drawString(TextFormatting.BOLD + "Select an Account", left + 10, top + 28, 0xFFFFFF);
		
		if (accounts != null)
		{
			String pageString = String.format("Page %s/%s", page + 1, accounts.size() / 12 + 1);
			fontRenderer.drawString(pageString, left + 60 - fontRenderer.getStringWidth(pageString) / 2, top + 40, 0xFFFFFF);
		}
		
		for(GuiButton button : buttonList)
		{
			if (button.isMouseOver())
			{
				Optional<Account> currentAccount = accounts.stream().filter(a -> a.AccountID == button.id).findFirst();
				if (currentAccount.isPresent())
				{			
					boolean isCompany = currentAccount.get().CompanyID != 0;
					drawHoveringText((isCompany ? TextFormatting.RED : TextFormatting.GREEN) + (!isCompany ? currentAccount.get().Government.Name : currentAccount.get().Company.Name), mouseX, mouseY);
				}
			}
		}
	}
	
	private void setupButtons()
	{
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				int index = j + i * 6 + page * 12;
				if (index >= accounts.size())
				{
					break;
				}
				
				Account account = accounts.get(index);
				GuiImageLabelButton accountButton = new GuiImageLabelButton((int)account.AccountID, left + 10 + 145 * i, top + 55 + j * 24, 135, 20, fontRenderer.trimStringToWidth(account.Description, 130), null, 0, 0, 0, 0, ImageOrientation.Left)
													.setEnabledColor(account.CompanyID != 0 ? 0xFF0000 : 0x00FF00);
				buttonList.add(accountButton);
			}
		}
		
//		for(int i = 0; i < 6; i++)
//		{
//			GuiImageLabelButton button = new GuiImageLabelButton(0, left + 10, top + 55 + i * 24, 135, 20, "Test", null, 0, 0, 0, 0, ImageOrientation.Left);
//			buttonList.add(button);
//		}
//		
//		for(int i = 0; i < 6; i++)
//		{
//			GuiImageLabelButton button = new GuiImageLabelButton(0, left + 155, top + 55 + i * 24, 135, 20, "Test", null, 0, 0, 0, 0, ImageOrientation.Left);
//			buttonList.add(button);
//		}
	}
	
	public void setData(String error, ArrayList<Account> accounts)
	{
		this.error = error;
		this.accounts = accounts;
		
		if (accounts != null)
		{
			page = 0;
			prevPage.enabled = false;
			prevPage.visible = true;
			
			nextPage.enabled = accounts.size() / 12 > 0;
			nextPage.visible = true;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		playButtonSound();
		if (button == settings)
		{
			mc.displayGuiScreen(new GuiATMSettings(atm));
			
			return;
		}
		
		if (button == nextPage)
		{
			if (page < accounts.size() / 12)
			{
				page++;
				
				nextPage.enabled = page < accounts.size() / 12;
				prevPage.enabled = page > 0;
				buttonList.clear();
				addNonAccountButtons();
			}
			
			return;
		}
		
		if (button == prevPage)
		{
			if (page > 0)
			{
				page--;
				
				nextPage.enabled = page < accounts.size() / 12;
				prevPage.enabled = page > 0;
				buttonList.clear();
				addNonAccountButtons();
			}
			
			return;
		}
		
		Optional<Account> account = accounts.stream().filter(a -> a.AccountID == button.id).findFirst();
		if (account.isPresent())
		{
			mc.displayGuiScreen(new GuiATMMainMenu(atm, account.get()));
		}
	}
}
