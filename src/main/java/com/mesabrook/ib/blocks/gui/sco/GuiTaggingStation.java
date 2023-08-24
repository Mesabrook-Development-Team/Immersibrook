package com.mesabrook.ib.blocks.gui.sco;

import com.mesabrook.ib.blocks.container.ContainerTaggingStation;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiTaggingStation extends GuiContainer {

	public GuiTaggingStation(InventoryPlayer playerInventory, BlockPos pos) {
		super(new ContainerTaggingStation(playerInventory, pos));
		
		this.xSize = 175;
		this.ySize = 124;
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
		renderHoveredToolTip(mouseX, mouseY);
	}
}
