package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.container.ContainerTaggingStation;
import com.mesabrook.ib.blocks.container.ContainerTaggingStationUntag;
import com.mesabrook.ib.net.sco.TaggingStationChangeTabsPacket;
import com.mesabrook.ib.net.sco.TaggingStationDistanceChangedPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiTaggingStationUntag extends GuiContainer {
	
	private final ResourceLocation tagAdd = new ResourceLocation(Reference.MODID, "textures/gui/sco/security_tagging_station_add.png");
	private final ResourceLocation tagRemove = new ResourceLocation(Reference.MODID, "textures/gui/sco/security_tagging_station_remove.png");
	private final ResourceLocation guiTexture = new ResourceLocation(Reference.MODID, "textures/gui/sco/security_tagging_station_untag.png");
	private final ResourceLocation creative_inventory_tabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
	private final BlockPos taggingPos;
	
	public GuiTaggingStationUntag(InventoryPlayer playerInventory, BlockPos pos) {
		super(new ContainerTaggingStationUntag(playerInventory, pos));
		taggingPos = pos;
		
		this.xSize = 175;
		this.ySize = 168;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiTexture);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop + 28, 0, 0, this.xSize, this.ySize - 28);
		
		this.mc.getTextureManager().bindTexture(creative_inventory_tabs);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 27, 28);
		drawTexturedModalRect(guiLeft + 29, guiTop, 28, 32, 27, 32);
		
		this.mc.getTextureManager().bindTexture(tagAdd);
		drawScaledCustomSizeModalRect(guiLeft + 6, guiTop + 6, 0, 0, 249, 315, 16, 20, 249, 315);
		
		this.mc.getTextureManager().bindTexture(tagRemove);
		drawScaledCustomSizeModalRect(guiLeft + 35, guiTop + 6, 0, 0, 249, 315, 16, 20, 249, 315);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		if (mouseY >= guiTop && mouseY < guiTop + 28)
		{
			if (mouseX >= guiLeft && mouseX <= guiLeft + 27)
			{
				drawHoveringText("Tagging Mode", mouseX - guiLeft, mouseY - guiTop);
			}
			
			if (mouseX >= guiLeft + 29 && mouseX <= guiLeft + 56)
			{
				drawHoveringText("Untagging Mode", mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		renderHoveredToolTip(mouseX - guiLeft, mouseY - guiTop);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseY >= guiTop && mouseY < guiTop + 28 && mouseX >= guiLeft && mouseX <= guiLeft + 27)
		{
			TaggingStationChangeTabsPacket changeTabs = new TaggingStationChangeTabsPacket();
			changeTabs.taggingPos = taggingPos;
			changeTabs.toUntag = false;
			PacketHandler.INSTANCE.sendToServer(changeTabs);
			mc.player.openGui(Main.instance, Reference.GUI_TAGGING_STATION, mc.player.world, taggingPos.getX(), taggingPos.getX(), taggingPos.getX());
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
