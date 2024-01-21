package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.blocks.container.ContainerWallet;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

public class GuiWallet extends GuiContainer {

	public GuiWallet(InventoryPlayer playerInventory, ItemStack wallet, EnumHand hand) {		
		super(new ContainerWallet(playerInventory, wallet, hand));
		
		xSize = 176;
		ySize = 153;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/wallet_inv.png"));
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
		
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/slot_card.png"));
		drawScaledCustomSizeModalRect(guiLeft + 8, guiTop + 18, 1, 1, 31, 31, 16, 16, 32, 32);
		drawScaledCustomSizeModalRect(guiLeft + 28, guiTop + 18, 1, 1, 31, 31, 16, 16, 32, 32);
		drawScaledCustomSizeModalRect(guiLeft + 46, guiTop + 18, 1, 1, 31, 31, 16, 16, 32, 32);
		
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/slot_money.png"));
		for(int i = 0; i < 9; i++)
		{
			drawScaledCustomSizeModalRect(guiLeft + 8 + 18 * i, guiTop + 39, 1, 1, 31, 31, 16, 16, 32, 32);
		}
		
		fontRenderer.drawString("Wallet", guiLeft + 6, guiTop + 6, 0x404040);
		
		fontRenderer.drawString("Inventory", guiLeft + 6, guiTop + 59, 0x404040);
		drawDefaultBackground();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		renderHoveredToolTip(mouseX - guiLeft, mouseY - guiTop);
	}
}
