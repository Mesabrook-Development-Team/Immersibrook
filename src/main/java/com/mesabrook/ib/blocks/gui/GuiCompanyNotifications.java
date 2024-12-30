package com.mesabrook.ib.blocks.gui;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.apimodels.company.EmployeeToDoItem;
import com.mesabrook.ib.net.FetchCSNotificationPacket;
import com.mesabrook.ib.net.FetchCSNotificationPacket.FetchTypes;
import com.mesabrook.ib.util.GuiUtil;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiCheckBox;

public class GuiCompanyNotifications extends GuiScreen {

	private final int guiWidth = 176;
	private final int guiHeight = 153;
	
	private int guiLeft;
	private int guiTop;
	
	public GuiCheckBox urgent;
	public GuiCheckBox important;
	public GuiCheckBox information;
	public GuiButtonExt prevButton;
	public GuiButtonExt nextButton;
	
	public ArrayList<EmployeeToDoItem> employeeToDoItems = new ArrayList<>();
	private ArrayList<ToDoItem> displayedToDoItems = new ArrayList<>();
	private int page;
	
	public GuiCompanyNotifications()
	{
		
	}
	
	public void setToDoItems(ArrayList<EmployeeToDoItem> toDoItems)
	{
		this.employeeToDoItems = toDoItems;
		page = 0;
		displayToDoItems();
		
		isFetched = true;
	}
	
	private void displayToDoItems()
	{
		displayedToDoItems.clear();
		List<EmployeeToDoItem> filteredItems = employeeToDoItems.stream().filter(e -> (urgent.isChecked() && e.getTextFormat() == TextFormatting.RED) ||
																							(important.isChecked() && e.getTextFormat() == TextFormatting.YELLOW) ||
																							(information.isChecked() && e.getTextFormat() == TextFormatting.BLUE)).collect(Collectors.toList());
		
		if (page * 12 > filteredItems.size())
		{
			page = filteredItems.size() / 12;
		}
		for(int i = 0 + 12 * page; i < Math.min(12 * (page + 1), filteredItems.size()); i++)
		{
			ToDoItem newToDoItem = new ToDoItem(fontRenderer, filteredItems.get(i), guiLeft + 8, guiTop + 18 + fontRenderer.FONT_HEIGHT * (i - 12 * page), 160);
			displayedToDoItems.add(newToDoItem);
		}
		prevButton.enabled = page > 0;
		nextButton.enabled = filteredItems.size() > 12 * (page + 1);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		guiLeft = width / 2 - guiWidth / 2;
		guiTop = height / 2 - guiHeight / 2;
		
		nextButton = new GuiButtonExt(0, guiLeft + guiWidth - 27, guiTop + 5, 20, 10, ">");
		nextButton.enabled = false;
		prevButton = new GuiButtonExt(0, nextButton.x - 23, nextButton.y, 20, 10, "<");
		prevButton.enabled = false;
		
		urgent = new GuiCheckBox(0, guiLeft + 7, guiTop + 130, "\u00A74Urgent", true);
		important = new GuiCheckBox(0, urgent.x + urgent.width + 3, urgent.y, "\u00A7eImportant", true);
		information = new GuiCheckBox(0, important.x + important.width + 3, important.y, "\u00A79Info", true);
		
		buttonList.add(urgent);
		buttonList.add(important);
		buttonList.add(information);
		buttonList.add(nextButton);
		buttonList.add(prevButton);
		
		if (isFetched)
		{
			displayToDoItems();
		}
	}
	
	boolean isFirstDraw = true;
	boolean isFetched = false;
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (isFirstDraw)
		{
			isFirstDraw = false;
			FetchCSNotificationPacket fetchPacket = new FetchCSNotificationPacket();
			fetchPacket.fetchType = FetchTypes.NotificationsGUI;
			PacketHandler.INSTANCE.sendToServer(fetchPacket);
		}
		
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/notifications.png"));
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, guiWidth, guiHeight, guiWidth, guiHeight);
		super.drawScreen(mouseX, mouseY, partialTicks);
		fontRenderer.drawString("Notifications", guiLeft + 7, guiTop + 6, 0x808080);
		
		int fontWidth;
		if (!isFetched)
		{
			fontWidth = fontRenderer.getStringWidth("Fetching...");
			fontRenderer.drawString("Fetching...", (width / 2) - (fontWidth / 2), (height / 2) - (fontRenderer.FONT_HEIGHT / 2), 0xc6c6c6);
		}
		else if (isFetched && displayedToDoItems.size() == 0)
		{
			fontWidth = fontRenderer.getStringWidth("You're all caught up!");
			fontRenderer.drawString("You're all caught up!", (width / 2) - (fontWidth / 2), (height / 2) - (fontRenderer.FONT_HEIGHT / 2), 0x40FF40);
		}
		else
		{
			EmployeeToDoItem hoveredToDoItem = null;
			for(ToDoItem item : displayedToDoItems)
			{
				item.draw(mouseX, mouseY);
				
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
			}
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		for(ToDoItem item : displayedToDoItems)
		{
			item.mouseClick(mouseX, mouseY);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
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
		else if (button == urgent || button == information || button == important)
		{
			displayToDoItems();
		}
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
		
		public void mouseClick(int mouseX, int mouseY)
		{
			if (GuiUtil.isPointWithinBounds(mouseX, mouseY, x, y, width, height))
			{
				try
		        {
		            Class<?> oclass = Class.forName("java.awt.Desktop");
		            Object object = oclass.getMethod("getDesktop").invoke((Object)null);
		            oclass.getMethod("browse", URI.class).invoke(object, URI.create(toDoItem.MesaSuiteURI));
		            
		            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		        }
		        catch (Throwable throwable1)
		        {
		            Throwable throwable = throwable1.getCause();
		            Main.logger.error("Couldn't open link: {}", (Object)(throwable == null ? "<UNKNOWN>" : throwable.getMessage()));
		        }
			}
		}
	}
}
