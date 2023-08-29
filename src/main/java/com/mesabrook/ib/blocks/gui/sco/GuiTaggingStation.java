package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.blocks.container.ContainerTaggingStation;
import com.mesabrook.ib.net.sco.TaggingStationDistanceChangedPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiTaggingStation extends GuiContainer {

	private final ContainerTaggingStation tagStationContainer;
	private final String distanceLabel = "Max Distance: ";
	private int distanceLabelLength;
	GuiTextField distance;
	
	public GuiTaggingStation(InventoryPlayer playerInventory, BlockPos pos) {
		super(new ContainerTaggingStation(playerInventory, pos));
		tagStationContainer = (ContainerTaggingStation)this.inventorySlots;
		
		this.xSize = 175;
		this.ySize = 140;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		Slot slot = tagStationContainer.getSlotFromInventory(tagStationContainer.craftResult, 0);
		distance = new GuiTextField(0, fontRenderer, guiLeft + slot.xPos - 4 - 26, guiTop + slot.yPos + 26, 50, fontRenderer.FONT_HEIGHT + 3);
		distance.setText(Double.toString(tagStationContainer.getResetDistance()));
		distanceLabelLength = fontRenderer.getStringWidth(distanceLabel);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/sco/security_tagging_station.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		fontRenderer.drawString(distanceLabel, distance.x - distanceLabelLength, distance.y + 2, 0x777777);
		distance.drawTextBox();
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		distance.mouseClicked(mouseX, mouseY, mouseButton); // handle first so that the result is set properly
		if (!distance.isFocused())
		{
			try
			{
				Double.parseDouble(distance.getText());
			}
			catch(Exception ex)
			{
				distance.setText("");
			}
		}
		notifyResetDistance();
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	private void notifyResetDistance()
	{
		double distanceVal = 0;
		try
		{
			distanceVal = Double.parseDouble(distance.getText());
		}
		catch(Exception ex) {}
		
		TaggingStationDistanceChangedPacket notifyServer = new TaggingStationDistanceChangedPacket();
		notifyServer.distance = distanceVal;
		PacketHandler.INSTANCE.sendToServer(notifyServer);
		
		tagStationContainer.setResetDistance(distanceVal);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		distance.textboxKeyTyped(typedChar, keyCode);
		notifyResetDistance();
	}
}
