package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.blocks.container.ContainerAutomatedTaggingStation;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.blocks.te.TileEntityAutomatedTaggingStation;
import com.mesabrook.ib.blocks.te.TileEntityAutomatedTaggingStation.LightStates;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;

public class GuiAutomatedTaggingStation extends GuiContainer {

	private final ContainerAutomatedTaggingStation taggingStationContainer;
	GuiTextField tagStackSizeField;
	GuiTextField resetDistanceField;
	ImageButton activeButton;
	
	public GuiAutomatedTaggingStation(ContainerAutomatedTaggingStation inventorySlotsIn) {
		super(inventorySlotsIn);
		this.taggingStationContainer = inventorySlotsIn;
		
		this.xSize = 225;
		this.ySize = 178;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		tagStackSizeField = new GuiTextField(0, fontRenderer, guiLeft + 87, guiTop + 69, 25, 11);
		tagStackSizeField.setValidator(str -> validateAmount(str, false));
		tagStackSizeField.setMaxStringLength(2);
		tagStackSizeField.setText(Integer.toString(taggingStationContainer.taggingStation.getTagStackSize()));
		tagStackSizeField.setFocused(true);
		
		resetDistanceField = new GuiTextField(0, fontRenderer, guiLeft + 87, guiTop + 82, 35, 11);
		resetDistanceField.setValidator(str -> validateAmount(str, true));
		resetDistanceField.setMaxStringLength(5);
		resetDistanceField.setText(Double.toString(taggingStationContainer.taggingStation.getResetDistance()));
		
		final String imageName = taggingStationContainer.taggingStation.isActive() ? "toggle_on.png" : "toggle_off.png";
		activeButton = new ImageButton(0, guiLeft + 185, guiTop + 69, 32, 16, new ResourceLocation(Reference.MODID, "textures/gui/" + imageName), 32, 16, 32, 16);
		buttonList.add(activeButton);
	}
	
	private boolean validateAmount(String amount, boolean asDouble)
	{
		if (StringUtils.isNullOrEmpty(amount)) 
		{
			return true;
		}
		
		try
		{
			if (asDouble)
			{
				Double.parseDouble(amount);
			}
			else
			{
				Integer.parseInt(amount);
			}
			return true;
		}
		catch(Exception ex) {}
		
		return false;
	}

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString("Security Boxes", 7, 7, 0x0f0f0f, false);
        this.fontRenderer.drawString("Energy", 182, 5, 0x0f0f0f, false);
        this.fontRenderer.drawString("Items To Tag", 7, 39, 0x0f0f0f, false);
        this.fontRenderer.drawString("Tag Stack Size:", 7, 71, 0x0f0f0f, false);
        this.fontRenderer.drawString("Reset Distance:", 7, 83, 0x0f0f0f, false);
        this.fontRenderer.drawString("Active", 182, 59, 0x0f0f0f, false);
    }

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        drawDefaultBackground();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/automated_tagging_station.png"));
        drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        
		GlStateManager.disableLighting();
		tagStackSizeField.drawTextBox();
		resetDistanceField.drawTextBox();
		GlStateManager.enableLighting();
		
		drawEnergyGauge();
		drawEnergyGaugeLines();
		drawActivityIndicator();
		
		renderHoveredToolTip(mouseX, mouseY);
		renderEnergyGaugeTooltip(mouseX, mouseY);
    }
    
    private void drawEnergyGauge() {
		TileEntityAutomatedTaggingStation taggingStation = (TileEntityAutomatedTaggingStation)mc.world.getTileEntity(taggingStationContainer.taggingStation.getPos());
		if (taggingStation == null)
		{
			return;
		}
		
		int currentEnergy = taggingStation.getEnergyStored();
		double chargePercent = (double)currentEnergy / (double)TileEntityAutomatedTaggingStation.MAX_ENERGY;
		int chargeBarHeight = (int)(chargePercent * 31D);
		
		if (chargeBarHeight > 0)
		{
			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.disableLighting();
			mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/energy.png"));
			drawModalRectWithCustomSizedTexture(guiLeft + 188, guiTop + 18 + (31 - chargeBarHeight), 0, 32 - chargeBarHeight, 26, chargeBarHeight, 32, 32);
			GlStateManager.enableLighting();
		}
	}
    
    private void renderEnergyGaugeTooltip(int mouseX, int mouseY) {
	    if (isPointInRegion(188, 18, 26, 31, mouseX, mouseY))
	    {
	    	TileEntityAutomatedTaggingStation taggingStation = (TileEntityAutomatedTaggingStation)mc.world.getTileEntity(taggingStationContainer.taggingStation.getPos());
			if (taggingStation == null)
			{
				return;
			}
			
	    	drawHoveringText(Arrays.asList(
	    		TextFormatting.YELLOW + "Stored Energy",
	    		taggingStation.getEnergyStored() + "RF / " + TileEntityAutomatedTaggingStation.MAX_ENERGY + "RF"
    			), mouseX, mouseY);
	    }
    }

	private void drawEnergyGaugeLines()
    {
    	GlStateManager.color(0.76F, 0.35F, 0F);
    	GlStateManager.disableTexture2D();
    	
    	Tessellator tess = Tessellator.getInstance();
    	BufferBuilder builder = tess.getBuffer();
    	builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
    	builder.pos(guiLeft + 190, guiTop + 40, 0).endVertex();
    	builder.pos(guiLeft + 211, guiTop + 40, 0).endVertex();
    	
    	builder.pos(guiLeft + 190, guiTop + 32, 0).endVertex();
    	builder.pos(guiLeft + 211, guiTop + 32, 0).endVertex();
    	
    	builder.pos(guiLeft + 190, guiTop + 24, 0).endVertex();
    	builder.pos(guiLeft + 211, guiTop + 24, 0).endVertex();
    	tess.draw();
    	
    	GlStateManager.enableTexture2D();
    	GlStateManager.color(1F, 1F, 1F);
    }
    
	private void drawActivityIndicator() {
		GlStateManager.color(1F, 1F, 1F);
		GlStateManager.disableLighting();
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/ats_activity_indicator.png"));
		drawModalRectWithCustomSizedTexture(guiLeft + 129, guiTop + 71, 0, 0, 40, 20, 40, 20);
		
		if (taggingStationContainer.taggingStation.getLightState() == LightStates.Green)
		{
			GlStateManager.disableTexture2D();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder builder = tess.getBuffer();
			builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			builder.pos(guiLeft + 135, guiTop + 76, 0).color(0F, 0.8F, 0F, 1F).endVertex();
			builder.pos(guiLeft + 135, guiTop + 86, 0).color(0F, 0.8F, 0F, 1F).endVertex();
			builder.pos(guiLeft + 145, guiTop + 86, 0).color(0F, 0.8F, 0F, 1F).endVertex();
			builder.pos(guiLeft + 145, guiTop + 76, 0).color(0F, 0.8F, 0F, 1F).endVertex();
			tess.draw();
			GlStateManager.enableTexture2D();
		}
		else if (taggingStationContainer.taggingStation.getLightState() == LightStates.Red)
		{
			GlStateManager.disableTexture2D();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder builder = tess.getBuffer();
			builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			builder.pos(guiLeft + 153, guiTop + 76, 0).color(1F, 0F, 0F, 1F).endVertex();
			builder.pos(guiLeft + 153, guiTop + 86, 0).color(1F, 0F, 0F, 1F).endVertex();
			builder.pos(guiLeft + 163, guiTop + 86, 0).color(1F, 0F, 0F, 1F).endVertex();
			builder.pos(guiLeft + 163, guiTop + 76, 0).color(1F, 0F, 0F, 1F).endVertex();
			tess.draw();
			GlStateManager.enableTexture2D();
		}
		
		GlStateManager.enableLighting();
	}
	
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	super.keyTyped(typedChar, keyCode);
    	tagStackSizeField.textboxKeyTyped(typedChar, keyCode);
    	resetDistanceField.textboxKeyTyped(typedChar, keyCode);
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    	boolean wasStackSizeFocused = tagStackSizeField.isFocused();
    	boolean wasResetDistanceFocused = resetDistanceField.isFocused();
		tagStackSizeField.mouseClicked(mouseX, mouseY, mouseButton);
		resetDistanceField.mouseClicked(mouseX, mouseY, mouseButton);
		if ((wasStackSizeFocused && !tagStackSizeField.isFocused()) ||
			(wasResetDistanceFocused && !resetDistanceField.isFocused()))
		{
			TileEntityAutomatedTaggingStation taggingStation = taggingStationContainer.taggingStation;
			
			if (!StringUtils.isNullOrEmpty(tagStackSizeField.getText()))
			{
				taggingStation.setTagStackSize(Integer.parseInt(tagStackSizeField.getText()));
			}
			
			if (!StringUtils.isNullOrEmpty(resetDistanceField.getText()))
			{
				taggingStation.setResetDistance(Double.parseDouble(resetDistanceField.getText()));
			}
			taggingStation.syncClientToServer();
		}
    }
    
    @Override
    public void onGuiClosed() {
    	super.onGuiClosed();
    	TileEntityAutomatedTaggingStation taggingStation = taggingStationContainer.taggingStation;
		
		if (!StringUtils.isNullOrEmpty(tagStackSizeField.getText()))
		{
			taggingStation.setTagStackSize(Integer.parseInt(tagStackSizeField.getText()));
		}
		if (!StringUtils.isNullOrEmpty(resetDistanceField.getText()))
		{
			taggingStation.setResetDistance(Double.parseDouble(resetDistanceField.getText()));
		}
		taggingStation.syncClientToServer();
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	super.actionPerformed(button);
    	
    	if (button == activeButton)
    	{
    		TileEntityAutomatedTaggingStation taggingStation = taggingStationContainer.taggingStation;
    		taggingStation.setActive(!taggingStation.isActive());
			taggingStation.syncClientToServer();
			
			String imageName = taggingStation.isActive() ? "toggle_on.png" : "toggle_off.png";
			activeButton.setTextureRL(new ResourceLocation(Reference.MODID, "textures/gui/" + imageName));
    	}
    }

}
