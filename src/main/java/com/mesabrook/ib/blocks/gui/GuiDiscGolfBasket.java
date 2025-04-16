package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.blocks.container.ContainerDiscGolfBasket;
import com.mesabrook.ib.blocks.te.TileEntityDiscGolfBasket;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiDiscGolfBasket extends GuiContainer {
	
	public GuiDiscGolfBasket(InventoryPlayer player, TileEntityDiscGolfBasket basket)
	{
		super(new ContainerDiscGolfBasket(player, basket));
		
		this.xSize = 176;
		this.ySize = 132;
	}

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString("Basket", 7, 7, 0x0f0f0f, false);
    }

	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        drawDefaultBackground();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/disc_golf_basket_inv.png"));
        drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

}
