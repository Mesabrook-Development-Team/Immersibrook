package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.blocks.container.ContainerTrashBin;
import com.mesabrook.ib.blocks.te.TileEntityTrashBin;
import com.mesabrook.ib.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTrashBin extends GuiContainer
{
	private static final ResourceLocation GUI_TRASHBIN = new ResourceLocation(Reference.MODID + ":textures/gui/trash_bin.png");
	private final InventoryPlayer playerInventory;
	private final TileEntityTrashBin te;
	
	public GuiTrashBin(InventoryPlayer playerInv, TileEntityTrashBin chestInventory, EntityPlayer player)
	{
		super(new ContainerTrashBin(playerInv, chestInventory, player));
		this.playerInventory = playerInv;
		this.te = chestInventory;
		
		this.xSize = 179;
		this.ySize = 256;
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(this.te.getDisplayName().getUnformattedText(), 8, 6, 000000);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 000000);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TRASHBIN);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}
}
