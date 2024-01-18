package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.blocks.container.ContainerRegisterSecurityBoxInventory;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPOSSecurityBoxInventory extends GuiContainer {

	private TileEntityRegister register;
	private GuiImageLabelButton back;
	
	public GuiPOSSecurityBoxInventory(TileEntityRegister register, InventoryPlayer playerInventory) {
		super(new ContainerRegisterSecurityBoxInventory(playerInventory, register));
		this.register = register;
		xSize = 300;
		ySize = 300;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/sco/sco_sec_box_inv.png"));
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		back = new GuiImageLabelButton(0, guiLeft + 35, guiTop + 41, 60, 20, "   Back", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 16, 16, 16, 16, ImageOrientation.Left)
				.setEnabledColor(0x373737);
		buttonList.add(back);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1F, 1F, 1F);
		fontRenderer.drawString("Self-Checkout System", guiLeft + 68, guiTop + 11, 0xFFFFFF);
		fontRenderer.drawString("Security Boxes", guiLeft + 105, guiTop + 29, 0);
		
		if (register.getName() != null)
		{
			GlStateManager.scale(0.5, 0.5, 1);
			int stringWidth = fontRenderer.getStringWidth(register.getName());
			fontRenderer.drawString(register.getName(), (guiLeft + xSize - 29) * 2 - stringWidth, (guiTop + 17) * 2 - fontRenderer.FONT_HEIGHT, 0);
			GlStateManager.scale(2, 2, 1);
		}
		
		renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == back)
		{
			mc.displayGuiScreen(new GuiPOSAdmin(register));
		}
	}
}
