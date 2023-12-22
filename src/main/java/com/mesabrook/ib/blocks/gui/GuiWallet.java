package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.blocks.container.ContainerWallet;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class GuiWallet extends GuiContainer {

	public GuiWallet(InventoryPlayer playerInventory, ItemStack wallet, EnumHand hand) {		
		super(new ContainerWallet(playerInventory, wallet, hand));
		
		xSize = 345;
		ySize = 290;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/wallet_inv.png"));
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
	}
}
