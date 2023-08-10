package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.net.sco.StoreModeGuiLocationSelectedPacket;
import com.mesabrook.ib.net.sco.StoreModeGuiOpenedPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiStoreMode extends GuiScreen {
	
	boolean firstTick = true;
	private Object drawLock = new Object();
	
	private int xCenter = 0;
	private int yCenter = 0;
	private int modalTop = 0;
	private int modalLeft = 0;
	private boolean isLoading = true;
	private long companyID = -1;
	private String companyName = "";
	private final int buttonsPerPage = 10;
	HashMap<Long, ArrayList<LocationEmployee>> LocationsByCompany = new HashMap<>();
	private int companyPage = 0;
	private int locationPage = 0;
	private GuiButtonExt nextPage;
	private GuiButtonExt prevPage;
	private GuiButtonExt toCompanyList;
	
	@Override
	public void initGui() {
		super.initGui();
		
		xCenter = width / 2;
		yCenter = height / 2;
		modalTop = yCenter - 128;
		modalLeft = xCenter - 128;
		firstTick = true;
		isLoading = true;
		companyID = -1;
		companyName = "";
		companyPage = 0;
		locationPage = 0;
		
		nextPage = new GuiButtonExt(0, modalLeft + 256 - 4 - 20, modalTop + 4, 20, fontRenderer.FONT_HEIGHT + 4, ">");
		prevPage = new GuiButtonExt(0, modalLeft + 256 - 4 - 20 - 4 - 20, modalTop + 4, 20, fontRenderer.FONT_HEIGHT + 4, "<");
		toCompanyList = new GuiButtonExt(0, modalLeft + 256 - 4 - 20 - 4 - 20 - 4 - 35, modalTop + 4, 35, fontRenderer.FONT_HEIGHT + 4, "Back");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (firstTick)
		{
			StoreModeGuiOpenedPacket openedPacket = new StoreModeGuiOpenedPacket();
			PacketHandler.INSTANCE.sendToServer(openedPacket);
			
			firstTick = false;
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/sco/gui_128_blank.png"));
		drawModalRectWithCustomSizedTexture(modalLeft, modalTop, 0, 0, 256, 256, 256, 256);
		fontRenderer.drawString(companyID == -1 ? "Store Selector" : companyName, modalLeft + 4, modalTop + 4, 0x555555);
		
		if (isLoading)
		{
			String loadingMessage = TextFormatting.ITALIC + "Fetching Stores...";
			drawCenteredString(fontRenderer, loadingMessage, xCenter, yCenter, 0xFFFFFF);
		}
		
		synchronized(drawLock)
		{
			super.drawScreen(mouseX, mouseY, partialTicks);
		}
	}
	
	public void setLocationEmployees(LocationEmployee[] locationEmployees)
	{
		synchronized (drawLock) {
			for(int i = 0; i < locationEmployees.length; i++)
			{
				LocationEmployee locEmp = locationEmployees[i];
				if (!LocationsByCompany.containsKey(locEmp.Location.CompanyID))
				{
					LocationsByCompany.put(locEmp.Location.CompanyID, new ArrayList<>());
				}
				
				LocationsByCompany.get(locEmp.Location.CompanyID).add(locEmp);
			}
			
			setCompanyButtons();			
			isLoading = false;
		}
	}
	
	private void setCompanyButtons()
	{
		buttonList.clear();
		buttonList.add(nextPage);
		buttonList.add(prevPage);
		
		Long[] companyIDs = LocationsByCompany.keySet().toArray(new Long[0]);
		for(int i = buttonsPerPage * companyPage; i < LocationsByCompany.size(); i++)
		{
			if (i >= buttonsPerPage * (companyPage + 1))
			{
				break;
			}
			
			ArrayList<LocationEmployee> locations = LocationsByCompany.get(companyIDs[i]);
			GuiButtonExt button = new GuiButtonExt(companyIDs[i].intValue(), modalLeft + 4, modalTop + fontRenderer.FONT_HEIGHT + 15 + 22 * (i - buttonsPerPage * companyPage), 248, 20, locations.get(0).Location.Company.Name);
			buttonList.add(button);
		}
		
		nextPage.enabled = (companyPage * buttonsPerPage) + buttonsPerPage < companyIDs.length;
		prevPage.enabled = companyPage > 0;
	}
	
	private void setLocationButtons()
	{
		buttonList.clear();
		buttonList.add(prevPage);
		buttonList.add(nextPage);
		buttonList.add(toCompanyList);
		
		List<LocationEmployee> locations = LocationsByCompany.get(companyID);
		if (locations == null)
		{
			return;
		}
		
		locations = locations.stream().filter(l -> l.ManageInventory || l.ManagePrices || l.ManageRegisters).collect(Collectors.toList());
		for(int i = buttonsPerPage * locationPage; i < locations.size(); i++)
		{
			if (i >= buttonsPerPage * (locationPage + 1))
			{
				break;
			}
			
			LocationEmployee locEmp = locations.get(i);
			GuiButtonExt button = new GuiButtonExt((int)locEmp.LocationID, modalLeft + 4, modalTop + fontRenderer.FONT_HEIGHT + 15 + 22 * (i - buttonsPerPage * locationPage), 248, 20, locEmp.Location.Name);
			buttonList.add(button);
		}
		
		nextPage.enabled = (locationPage * buttonsPerPage) + buttonsPerPage < locations.size();
		prevPage.enabled = locationPage > 0;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == nextPage)
		{
			if (companyID == -1)
			{
				companyPage++;
			}
			else
			{
				locationPage++;
			}
			setCompanyButtons();
		}
		else if (button == prevPage)
		{
			if (companyID == -1)
			{
				companyPage--;
			}
			else
			{
				locationPage--;
			}
			setCompanyButtons();
		}
		else if (button == toCompanyList)
		{
			locationPage = 0;
			companyID = -1;
			setCompanyButtons();
		}
		else if (companyID == -1)
		{
			companyID = button.id;
			companyName = button.displayString;
			setLocationButtons();
		}
		else // Selecting a Location
		{
			StoreModeGuiLocationSelectedPacket selected = new StoreModeGuiLocationSelectedPacket();
			selected.locationID = button.id;
			PacketHandler.INSTANCE.sendToServer(selected);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
