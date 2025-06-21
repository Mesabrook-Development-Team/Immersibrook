package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.ArrayUtils;

import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.net.sco.StoreModeGuiOpenedPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiNewEmployeeModeScreen extends GuiPhoneBase
{
	boolean firstTick = true;
	private Object drawLock = new Object();
	
	private boolean isLoading = true;
	private final int buttonsPerPage = 10;
	HashMap<Long, ArrayList<LocationEmployee>> LocationsByCompany = new HashMap<>();
	
	private int companyPage = 0;
	private boolean preLoaded = false;
	private MinedroidButton logoutDuringFetch;
	private MinedroidButton nextPage;
	private MinedroidButton prevPage;
	
	public GuiNewEmployeeModeScreen(ItemStack phoneStack, EnumHand hand, HashMap<Long, ArrayList<LocationEmployee>> locationsByCompany, int companyPage) 
	{
		super(phoneStack, hand);
		this.LocationsByCompany = locationsByCompany;
		this.companyPage = companyPage;
		preLoaded = true;
	}
	
	@Override
	protected String getInnerTextureFileName() 
	{
		return phoneStackData.getIconTheme() + "/app_screen.png";
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 50;
		
		if (!preLoaded)
		{
			isLoading = true;
			companyPage = 0;
		}
		
		logoutDuringFetch = new MinedroidButton(0, INNER_X + 21, lowerControlsY - 20, 120, "Go Off Duty", 0xFFFFFF);
		prevPage = new MinedroidButton(1, INNER_X + 3, lowerControlsY - 10, 35, "<<", 0xFFFFFF);
        nextPage = new MinedroidButton(2, INNER_X + 124, lowerControlsY - 10, 35, ">>", 0xFFFFFF);
        
        if (mc.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null).getLocationID() != 0)
		{
			buttonList.add(logoutDuringFetch);
		}
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks)
	{		
		if(firstTick)
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
			super.drawScreen(mouseX, mouseY, partialticks);
		}
	}
	
	public void setLocationEmployees(LocationEmployee[] locationEmployees)
	{
		synchronized (drawLock) 
		{
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
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
	}
}
