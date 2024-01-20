package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.dynmap.jetty.util.ArrayUtil;

import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
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
	private final int buttonsPerPage = 10;
	HashMap<Long, ArrayList<LocationEmployee>> LocationsByCompany = new HashMap<>();
	private int companyPage = 0;
	private boolean preLoaded = false;
	private GuiButtonExt logoutDuringFetch;
	private GuiButtonExt nextPage;
	private GuiButtonExt prevPage;
	
	public GuiStoreMode() {}
	
	public GuiStoreMode(HashMap<Long, ArrayList<LocationEmployee>> locationsByCompany, int companyPage)
	{
		this.LocationsByCompany = locationsByCompany;
		this.companyPage = companyPage;
		preLoaded = true;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		xCenter = width / 2;
		yCenter = height / 2;
		modalTop = yCenter - 128;
		modalLeft = xCenter - 128;
		firstTick = true;
		
		if (!preLoaded)
		{
			isLoading = true;
			companyPage = 0;
		}
		
		logoutDuringFetch = new GuiButtonExt(0, modalLeft + 4, modalTop + fontRenderer.FONT_HEIGHT + 15, 248, 20, "Go Off Duty");
		nextPage = new GuiButtonExt(0, modalLeft + 256 - 4 - 20, modalTop + 4, 20, fontRenderer.FONT_HEIGHT + 4, ">");
		prevPage = new GuiButtonExt(0, modalLeft + 256 - 4 - 20 - 4 - 20, modalTop + 4, 20, fontRenderer.FONT_HEIGHT + 4, "<");
		
		if (mc.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null).getLocationID() != 0)
		{
			buttonList.add(logoutDuringFetch);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (firstTick)
		{
			if (!preLoaded)
			{
				StoreModeGuiOpenedPacket openedPacket = new StoreModeGuiOpenedPacket();
				PacketHandler.INSTANCE.sendToServer(openedPacket);
			}
			else
			{
				isLoading = false;
				setCompanyButtons();
			}
			
			firstTick = false;
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/sco/gui_128_blank.png"));
		drawModalRectWithCustomSizedTexture(modalLeft, modalTop, 0, 0, 256, 256, 256, 256);
		fontRenderer.drawString("Store Selector", modalLeft + 4, modalTop + 4, 0x555555);
		
		if (isLoading)
		{
			String loadingMessage = TextFormatting.ITALIC + "Fetching Stores...";
			drawCenteredString(fontRenderer, loadingMessage, xCenter, yCenter, 0xFFFFFF);
		}
		else if (LocationsByCompany.size() <= 0)
		{
			String noStoresMessage = TextFormatting.ITALIC + "No Stores";
			drawCenteredString(fontRenderer, noStoresMessage, xCenter, yCenter, 0xFFFFFF);
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
				if (!locEmp.ManageInventory && !locEmp.ManagePrices && !locEmp.ManageRegisters)
				{
					continue;
				}
				
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
		int iterationLimitModifier = 0;
		if (mc.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null).getLocationID() != 0)
		{
			companyIDs = ArrayUtils.add(companyIDs, 0, 0L);
			iterationLimitModifier = 1;
		}
		
		for(int i = buttonsPerPage * companyPage; i < LocationsByCompany.size() + iterationLimitModifier; i++)
		{
			if (i >= buttonsPerPage * (companyPage + 1))
			{
				break;
			}
			
			String companyName;
			if (companyIDs[i] == 0L)
			{
				companyName = "Go Off Duty";
			}
			else
			{
				ArrayList<LocationEmployee> locations = LocationsByCompany.get(companyIDs[i]);
				companyName = locations.get(0).Location.Company.Name;
			}
			GuiButtonExt button = new GuiButtonExt(companyIDs[i].intValue(), modalLeft + 4, modalTop + fontRenderer.FONT_HEIGHT + 15 + 22 * (i - buttonsPerPage * companyPage), 248, 20, companyName);
			buttonList.add(button);
		}
		
		nextPage.enabled = (companyPage * buttonsPerPage) + buttonsPerPage < companyIDs.length;
		prevPage.enabled = companyPage > 0;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == logoutDuringFetch)
		{
			StoreModeGuiLocationSelectedPacket selected = new StoreModeGuiLocationSelectedPacket();
			selected.locationID = button.id;
			PacketHandler.INSTANCE.sendToServer(selected);
			
			mc.displayGuiScreen(null);
		}
		else if (button == nextPage)
		{
			companyPage++;
			setCompanyButtons();
		}
		else if (button == prevPage)
		{
			companyPage--;
			setCompanyButtons();
		}
		else
		{
			if (button.id == 0)
			{
				actionPerformed(logoutDuringFetch);
				return;
			}
			
			int companyID = button.id;
			String companyName = button.displayString;
			
			mc.displayGuiScreen(new GuiStoreModeLocation(companyName, companyPage, LocationsByCompany, LocationsByCompany.get((long)companyID)));
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
