package rz.mesabrook.wbtc.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import rz.mesabrook.wbtc.blocks.container.ContainerStampBook;
import rz.mesabrook.wbtc.util.Reference;

public class GuiStampBook extends GuiContainer {

	ContainerStampBook stampBookContainer;
	public GuiStampBook(ContainerStampBook container)
	{
		super(container);
		stampBookContainer = container;
		
		xSize = 517;
		ySize = 512;
	}	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/stamp_book.png"));
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
	}

}
