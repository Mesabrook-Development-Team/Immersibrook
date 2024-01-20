package com.mesabrook.ib.blocks.gui.commerce;

import com.mesabrook.ib.blocks.container.ContainerShoppingBasket;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class GuiShoppingBasket extends GuiContainer {

	private int shoppingBasketMeta;
	public GuiShoppingBasket(ContainerShoppingBasket inventorySlotsIn, int shoppingBasketMeta) {
		super(inventorySlotsIn);
		this.shoppingBasketMeta = shoppingBasketMeta;
		
		xSize = 176;
		ySize = 186;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		EnumDyeColor color = EnumDyeColor.byMetadata(shoppingBasketMeta);
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/basket/basket_" + color.getUnlocalizedName() + ".png"));
		
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
		
		fontRenderer.drawString("Shopping Basket", guiLeft + 6, guiTop + 6, 0x404040);
		fontRenderer.drawString("Inventory", guiLeft + 6, guiTop + 90, 0x404040);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		renderHoveredToolTip(mouseX - guiLeft, mouseY - guiTop);
	}
}
