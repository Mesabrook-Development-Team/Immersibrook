package rz.mesabrook.wbtc.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import rz.mesabrook.wbtc.blocks.container.ContainerStampBook;
import rz.mesabrook.wbtc.util.Reference;

public class GuiStampBook extends GuiContainer {

	ContainerStampBook stampBookContainer;
	public GuiStampBook(ContainerStampBook container)
	{
		super(container);
		stampBookContainer = container;
		
		xSize = 220;
		ySize = 235;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		// Inventory Text
		this.fontRenderer.drawString("Extra Stamps", 131, 8, 10813440, false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/stamp_book.png"));
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		renderHoveredToolTip(mouseX, mouseY);
	}
}
