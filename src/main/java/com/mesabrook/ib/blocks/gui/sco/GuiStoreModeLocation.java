package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.net.sco.StoreModeGuiLocationSelectedPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiStoreModeLocation extends GuiScreen {
	
	private int xCenter = 0;
	private int yCenter = 0;
	private int modalTop = 0;
	private int modalLeft = 0;
	private final HashMap<Long, ArrayList<LocationEmployee>> locationsByCompany;
	private final ArrayList<LocationEmployee> selectedLocations;
	private final String companyName;

	private final int buttonsPerPage = 10;
	private GuiButtonExt nextPage;
	private GuiButtonExt prevPage;
	private GuiButtonExt toCompanyList;
	private final int companyPage;
	private int page;
	
	public GuiStoreModeLocation(String companyName, int companyPage, HashMap<Long, ArrayList<LocationEmployee>> locationsByCompany, ArrayList<LocationEmployee> selectedLocations)
	{
		this.companyName = companyName;
		this.companyPage = companyPage;
		this.locationsByCompany = locationsByCompany;
		this.selectedLocations = selectedLocations;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		xCenter = width / 2;
		yCenter = height / 2;
		modalTop = yCenter - 128;
		modalLeft = xCenter - 128;
		
		nextPage = new GuiButtonExt(0, modalLeft + 256 - 4 - 20, modalTop + 4, 20, fontRenderer.FONT_HEIGHT + 4, ">");
		prevPage = new GuiButtonExt(0, modalLeft + 256 - 4 - 20 - 4 - 20, modalTop + 4, 20, fontRenderer.FONT_HEIGHT + 4, "<");
		toCompanyList = new GuiButtonExt(0, modalLeft + 256 - 4 - 20 - 4 - 20 - 4 - 35, modalTop + 4, 35, fontRenderer.FONT_HEIGHT + 4, "Back");
		
		setupButtons();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/sco/gui_128_blank.png"));
		drawModalRectWithCustomSizedTexture(modalLeft, modalTop, 0, 0, 256, 256, 256, 256);
		fontRenderer.drawString(companyName, modalLeft + 4, modalTop + 4, 0x555555);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void setupButtons()
	{
		buttonList.clear();
		buttonList.add(nextPage);
		buttonList.add(prevPage);
		buttonList.add(toCompanyList);
		
		for(int i = buttonsPerPage * page; i < selectedLocations.size(); i++)
		{
			if (i >= buttonsPerPage * (page + 1))
			{
				break;
			}
			
			LocationEmployee locEmp = selectedLocations.get(i);
			GuiButtonExt button = new GuiButtonExt((int)locEmp.LocationID, modalLeft + 4, modalTop + fontRenderer.FONT_HEIGHT + 15 + 22 * (i - buttonsPerPage * page), 248, 20, locEmp.Location.Name);
			buttonList.add(button);
		}
		
		nextPage.enabled = (page * buttonsPerPage) + buttonsPerPage < selectedLocations.size();
		prevPage.enabled = page > 0;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == nextPage)
		{
			page++;
			setupButtons();
		}
		
		if (button == prevPage)
		{
			page--;
			setupButtons();
		}
		
		if (button == toCompanyList)
		{
			// go to company page
			mc.displayGuiScreen(new GuiStoreMode(locationsByCompany, companyPage));
			return;
		}
		
		if (button.id > 0)
		{
			StoreModeGuiLocationSelectedPacket selected = new StoreModeGuiLocationSelectedPacket();
			selected.locationID = button.id;
			PacketHandler.INSTANCE.sendToServer(selected);
			
			mc.displayGuiScreen(null);
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
