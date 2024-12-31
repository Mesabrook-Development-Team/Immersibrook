package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.apimodels.company.EmployeeToDoItem;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.net.FetchCSNotificationPacket;
import com.mesabrook.ib.net.FetchCSNotificationPacket.FetchTypes;
import com.mesabrook.ib.util.GuiUtil;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiCheckBox;

public class GuiCompanyStudioNotifications extends GuiPhoneBase {

	private final int recordsPerPage = 16;
	private ArrayList<EmployeeToDoItem> employeeToDoItems = new ArrayList<>();
	private ArrayList<ToDoItem> displayedToDoItems = new ArrayList<>();
	private int page;
	private int totalFilteredPages = 1;
	
	public GuiCheckBox urgent;
	public GuiCheckBox important;
	public GuiCheckBox information;
	public MinedroidButton prevButton;
	public MinedroidButton nextButton;
	public ImageButton refreshButton;
	public ImageButton filterButton;
	
	public GuiCompanyStudioNotifications(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return phoneStackData.getIconTheme() + "/app_screen_purple.png";
	}

	@Override
	public void initGui() {
		super.initGui();
		
		prevButton = new MinedroidButton(0, INNER_X + INNER_TEX_WIDTH / 2 - 40, INNER_Y + INNER_TEX_HEIGHT - 38, 10, "<", 0xFFFFFF);
		prevButton.visible = false;
		prevButton.enabled = false;
		buttonList.add(prevButton);
		
		nextButton = new MinedroidButton(0, INNER_X + INNER_TEX_WIDTH / 2 + 30, prevButton.y, 10, ">", 0xFFFFFF);
		nextButton.visible = false;
		nextButton.enabled = false;
		buttonList.add(nextButton);
		
		filterButton = new ImageButton(0, INNER_X + INNER_TEX_WIDTH - 16, INNER_Y + 16, 16, 16, phoneStackData.getIconTheme() + "/icn_settings.png", 1024, 1024);
		buttonList.add(filterButton);
		
		refreshButton = new ImageButton(0, filterButton.x - 16, filterButton.y, 16, 16, phoneStackData.getIconTheme() + "/icn_eightball.png", 64, 64);
		refreshButton.visible = false;
		buttonList.add(refreshButton);
		
		// Start with informational because it's the longest and we want everything left-aligned
		information = new GuiCheckBox(0, filterButton.x, filterButton.y, TextFormatting.BLUE + "Informational", true);
		information.x = information.x - information.width - 2;
		information.y = information.y + information.height * 2;
		information.visible = false;
		buttonList.add(information);
		
		important = new GuiCheckBox(0, information.x, information.y - information.height, TextFormatting.YELLOW + "Important", true);
		important.visible = false;
		buttonList.add(important);
		
		urgent = new GuiCheckBox(0, information.x, important.y - important.height, TextFormatting.RED + "Urgent", true);
		urgent.visible = false;
		buttonList.add(urgent);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		fontRenderer.drawString("Company Notifications", INNER_X + 2, INNER_Y + 20, 0xFFFFFF);
		drawCenteredString(fontRenderer, "Page " + (page + 1) + "/" + totalFilteredPages, INNER_X + INNER_TEX_WIDTH / 2, prevButton.y + 3, 0xFFFFFF);
		
		int fontWidth;
		if (!isFetched)
		{
			fontWidth = fontRenderer.getStringWidth("Fetching...");
			fontRenderer.drawString("Fetching...", (INNER_X + INNER_TEX_WIDTH / 2) - (fontWidth / 2), (INNER_Y + INNER_TEX_HEIGHT / 2) - (fontRenderer.FONT_HEIGHT / 2), 0xc6c6c6);
		}
		else if (isFetched && displayedToDoItems.size() == 0)
		{
			fontWidth = fontRenderer.getStringWidth("You're all caught up!");
			fontRenderer.drawString("You're all caught up!", (INNER_X + INNER_TEX_WIDTH / 2) - (fontWidth / 2), (INNER_Y + INNER_TEX_HEIGHT / 2) - (fontRenderer.FONT_HEIGHT / 2), 0x40FF40);
		}
		else
		{
			for(ToDoItem item : displayedToDoItems)
			{
				item.draw(mouseX, mouseY);
			}
		}
		
		drawFilterMenu();
	}
	
	private boolean isFilterMenuShown;
	private void drawFilterMenu()
	{
		if (!isFilterMenuShown)
		{
			return;
		}
		
		GlStateManager.disableTexture2D();
		GlStateManager.color(1F, 1F, 1F);
		
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder builder = tess.getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		
		builder.pos(filterButton.x, filterButton.y, 0).endVertex();
		builder.pos(urgent.x, filterButton.y, 0).endVertex();
		builder.pos(urgent.x, information.y + information.height, 0).endVertex();
		builder.pos(filterButton.x, information.y + information.height, 0).endVertex();
		
		tess.draw();
		
		GlStateManager.enableTexture2D();
	}
	
	@Override
	protected void postDraw(int mouseX, int mouseY, float partialticks) {
		super.postDraw(mouseX, mouseY, partialticks);
		
		if (isFilterMenuShown)
		{
			return;
		}
		
		if (isFetched && displayedToDoItems.size() > 0)
		{
			EmployeeToDoItem hoveredToDoItem = null;
			for(ToDoItem item : displayedToDoItems)
			{				
				if (GuiUtil.isPointWithinBounds(mouseX, mouseY, item.x, item.y, item.width, item.height))
				{
					hoveredToDoItem = item.toDoItem;
				}
			}
			
			if (hoveredToDoItem != null)
			{
				ArrayList<String> textLines = new ArrayList<>();
				textLines.add(hoveredToDoItem.CompanyName + " (" + hoveredToDoItem.LocationName + ")");
				textLines.add(hoveredToDoItem.getTextFormat() + hoveredToDoItem.Message);
				textLines.add("" + TextFormatting.GRAY + TextFormatting.ITALIC + "Click to open in Company Studio");
				drawHoveringText(textLines, mouseX, mouseY);
				
				GlStateManager.disableLighting();
			}
		}
	}
	
	@Override
	protected void firstDrawingTick(int mouseX, int mouseY, float partialTicks) {
		super.firstDrawingTick(mouseX, mouseY, partialTicks);
		
		isFetched = false;
		employeeToDoItems.clear();
		displayedToDoItems.clear();
		page = 0;
		totalFilteredPages = 1;
		
		FetchCSNotificationPacket fetch = new FetchCSNotificationPacket();
		fetch.fetchType = FetchTypes.MinedroidApp;
		PacketHandler.INSTANCE.sendToServer(fetch);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		if (!isFilterMenuShown)
		{
			for(ToDoItem item : displayedToDoItems)
			{
				if (GuiUtil.isPointWithinBounds(mouseX, mouseY, item.x, item.y, item.width, item.height))
				{
					if (!item.mouseClick())
					{
						//Chris
						//MesaSuite apparently isn't installed on this machine, so prompt them to download it instead of
						//displaying this Toast
						Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("MesaSuite not found", 0xFF0000));
					}
				}
			}
		}
		
		if (isFilterMenuShown && !filterButton.isMouseOver() && !GuiUtil.isPointWithinBounds(mouseX, mouseY, urgent.x, urgent.y, information.width, information.y + information.height - urgent.y))
		{
			toggleFilterMenu();
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == filterButton)
		{
			toggleFilterMenu();
		}
		else if (button == urgent || button == important || button == information)
		{
			displayToDoItems();
		}
		
		if (isFilterMenuShown)
		{
			return;
		}
		
		if (button == nextButton)
		{
			page++;
			displayToDoItems();
		}
		else if (button == prevButton)
		{
			page--;
			displayToDoItems();
		}
		else if (button == refreshButton)
		{
			refreshButton.visible = false;
			prevButton.visible = false;
			nextButton.visible = false;
			firstDrawingTick(0, 0, 0);
		}
	}
	
	private void toggleFilterMenu()
	{
		isFilterMenuShown = !isFilterMenuShown;
		urgent.visible = isFilterMenuShown;
		important.visible = isFilterMenuShown;
		information.visible = isFilterMenuShown;
		
		refreshButton.visible = !isFilterMenuShown; // Hacky but whatever - it'd logically be hidden behind the menu anyway
	}
	
	private boolean isFetched;
	public void setToDoItems(ArrayList<EmployeeToDoItem> toDoItems)
	{
		this.employeeToDoItems = toDoItems;
		page = 0;
		displayToDoItems();
		
		if (!isFilterMenuShown)
		{
			refreshButton.visible = true;
		}
		
		isFetched = true;
	}
	
	private void displayToDoItems()
	{
		displayedToDoItems.clear();
		List<EmployeeToDoItem> filteredItems = employeeToDoItems.stream().filter(e -> (urgent.isChecked() && e.getTextFormat() == TextFormatting.RED) ||
												(important.isChecked() && e.getTextFormat() == TextFormatting.YELLOW) ||
												(information.isChecked() && e.getTextFormat() == TextFormatting.BLUE)).collect(Collectors.toList());
		totalFilteredPages = filteredItems.size() / recordsPerPage + 1;
		
		if (page * recordsPerPage > filteredItems.size())
		{
			page = filteredItems.size() / recordsPerPage;
		}
		for(int i = 0 + recordsPerPage * page; i < Math.min(recordsPerPage * (page + 1), filteredItems.size()); i++)
		{
			ToDoItem newToDoItem = new ToDoItem(fontRenderer, filteredItems.get(i), INNER_X + 1, INNER_Y + 35 + fontRenderer.FONT_HEIGHT * (i - recordsPerPage * page), INNER_TEX_WIDTH - 2);
			displayedToDoItems.add(newToDoItem);
		}
		prevButton.visible = true;
		prevButton.enabled = page > 0;
		nextButton.visible = true;
		nextButton.enabled = filteredItems.size() > recordsPerPage * (page + 1);
	}
	
	protected static class ToDoItem
	{
		private final int height;
		private final String line1;
		private final String line2;
		
		private FontRenderer fontRenderer;
		private EmployeeToDoItem toDoItem;
		private int x;
		private int y;
		private int width;
		
		public ToDoItem(FontRenderer fontRenderer, EmployeeToDoItem toDoItem, int x, int y, int width)
		{
			this.fontRenderer = fontRenderer;
			this.toDoItem = toDoItem;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = fontRenderer.FONT_HEIGHT;
			
			String line1 = toDoItem.CompanyName + " (" + toDoItem.LocationName + ")";
			String line2 = toDoItem.Message;
			
			int fontWidth = fontRenderer.getStringWidth(line1);
			if (fontWidth > width * 2)
			{
				line1 = fontRenderer.trimStringToWidth(line1, width * 2);
				line1 = line1.substring(0, line1.length() - 3) + "...";
			}
			
			fontWidth = fontRenderer.getStringWidth(line2);
			if (fontWidth > width * 2)
			{
				line2 = fontRenderer.trimStringToWidth(line2, width * 2);
				line2 = line2.substring(0, line2.length() - 3) + "...";
			}
			
			this.line1 = line1;
			this.line2 = line2;
		}
		
		public void draw(int mouseX, int mouseY)
		{
			GlStateManager.disableTexture2D();
			GlStateManager.color(1F, 1F, 1F, 1F);
			
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder builder = tess.getBuffer();
			builder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
			
			builder.pos(x, y, 0).endVertex();
			builder.pos(x, y + height, 0).endVertex();
			builder.pos(x + width, y + height, 0).endVertex();
			builder.pos(x + width, y, 0).endVertex();
			
			tess.draw();
			
			GlStateManager.enableTexture2D();
			
			GlStateManager.scale(0.5, 0.5, 1);
			fontRenderer.drawString(line1, x * 2, y * 2, 0xFFFFFF);
			fontRenderer.drawString(toDoItem.getTextFormat() + line2, x * 2, y * 2 + fontRenderer.FONT_HEIGHT, 0xFFFFFF);
			GlStateManager.scale(2, 2, 1);
		}
		
		public boolean mouseClick()
		{
			return ModUtils.openMesaSuiteLink(URI.create(toDoItem.MesaSuiteURI));
		}
	}
}
