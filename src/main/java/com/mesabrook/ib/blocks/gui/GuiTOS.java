package com.mesabrook.ib.blocks.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.mesabrook.ib.net.ClosedTOSPacket;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiTOS extends GuiScreen {
	String[] text = new String[] { "Fetching Terms of Service..." };
	
	private final String header = "You must accept Mesabrook Terms Of Service before continuing";
	private int headerWidth = 0;
	private int maxLinesShown = 0;
	private int boxWidth = 0;
	private int boxHeight = 0;
	private int currentPage = 1;
	private int totalPages = 1;
	private boolean acceptClicked = false;
	GuiButtonExt prev;
	GuiButtonExt next;
	GuiButtonExt accept;
	GuiButtonExt decline;
	
	@Override
	public void initGui() {
		super.initGui();
		boxWidth = width - 20;
		headerWidth = fontRenderer.getStringWidth(header);
		maxLinesShown = (height - 80) / fontRenderer.FONT_HEIGHT;
		boxHeight = 22 + fontRenderer.FONT_HEIGHT * maxLinesShown;
		
		prev = new GuiButtonExt(0, 18, height - 60, 50, 20, "Prev");
		next = new GuiButtonExt(0, width - 68, height - 60, 50, 20, "Next");
		accept = new GuiButtonExt(0, width / 2 + 20, height - 30, (width - 18) - (width / 2 + 20), 20, "Accept");
		decline = new GuiButtonExt(0, 18, height - 30, (width / 2 - 20) - 18, 20, "Decline");
		
		buttonList.add(prev);
		buttonList.add(next);
		buttonList.add(accept);
		buttonList.add(decline);
		
		new Thread(() -> fetchTOS(), "TOS Fetcher").start();
	}
	
	private void fetchTOS()
	{
		try {
			this.text = new String[] { "Fetching Terms of Service..." };
			currentPage = 1;
			totalPages = 1;
			
			URL tosURL = new URL(ModConfig.mesasuiteBaseAPIUrl + "/system/TermsofService/Get/Mesabrook");
			HttpURLConnection connection = (HttpURLConnection)tosURL.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			
			int statusCode = connection.getResponseCode();
			if (statusCode == 200)
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				String tos = "";
				while((line = reader.readLine()) != null)
				{
					tos += line;
				}
				
				tos = tos.replace("\\r", "").replace("\\\"", "\"");
				if (tos.startsWith("\""))
				{
					tos = tos.substring(1);
				}
				
				if (tos.endsWith("\""))
				{
					tos = tos.substring(0, tos.length() - 1);
				}
				
				String[] untruncatedLines = tos.split("\\\\n");
				ArrayList<String> lines = new ArrayList<>();
				for(String untruncatedLine : untruncatedLines)
				{
					for(String truncatedLine : fontRenderer.listFormattedStringToWidth(untruncatedLine, boxWidth - 20))
					{
						lines.add(truncatedLine);
					}
				}
				
				totalPages = lines.size() / maxLinesShown + 1;
				this.text = lines.toArray(new String[0]);
			}
		} catch (Exception e) {
			List<String> lines = fontRenderer.listFormattedStringToWidth("An error occurred fetching Terms Of Service: " + e.getLocalizedMessage() + ". Please visit https://www.mesabrook.com/tos.html to view our Terms Of Service.", boxWidth);
			totalPages = 1;
			this.text = lines.toArray(new String[0]);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawBackground(1);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		fontRenderer.drawString(header, width / 2 - headerWidth / 2, 8, 0xFFFFFF);
		
		drawRect(18, 18, boxWidth + 2, boxHeight, 0xFFAAAAAA);
		drawRect(20, 20, boxWidth, boxHeight - 2, 0xFF000000);
		for(int i = (currentPage - 1) * maxLinesShown; i < maxLinesShown * currentPage; i++)
		{
			if (i >= text.length)
			{
				break;
			}
			
			String line = text[i];
			fontRenderer.drawString(line, 20, 20 + fontRenderer.FONT_HEIGHT * (i - (currentPage - 1) * maxLinesShown), 0xFFFFFF);
		}
		
		String pageCountLabel = String.format("Page %s/%s", currentPage, totalPages);
		drawCenteredString(fontRenderer, pageCountLabel, width / 2, height - 55, 0xFFFFFF);
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		
		ClosedTOSPacket closedPacket = new ClosedTOSPacket();
		closedPacket.accepted = acceptClicked;
		PacketHandler.INSTANCE.sendToServer(closedPacket);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == next && currentPage != totalPages)
		{
			currentPage ++;
		}
		
		if (button == prev && currentPage > 1)
		{
			currentPage--;
		}
		
		if (button == accept)
		{
			acceptClicked = true;
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		
		if (button == decline)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}
}
