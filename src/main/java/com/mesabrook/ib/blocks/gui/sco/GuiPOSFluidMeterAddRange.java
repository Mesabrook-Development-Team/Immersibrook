package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

public class GuiPOSFluidMeterAddRange extends GuiPOSMainBase {

	private GuiTextField distance;
	
	private GuiImageLabelButton cancel;
	private GuiImageLabelButton scan;
	
	public GuiPOSFluidMeterAddRange(TileEntityRegister register) {
		super(register);
	}
	
	@Override
	protected String getBackingTextureName() {
		return "sco_general_input.png";
	}

	@Override
	public void initGui() {
		super.initGui();
		
		distance = new GuiTextField(0, fontRenderer, innerLeft + (innerWidth / 2) - 30, innerTop + (innerHeight / 2) - 15, 60, 20);
		distance.setFocused(true);
		
		scan = new GuiImageLabelButton(0, distance.x, distance.y + 24, distance.width, distance.height, "Scan   ", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_right.png"), 16, 16, 16, 16, ImageOrientation.Right)
				.setEnabledColor(0x00AA00);
		cancel = new GuiImageLabelButton(0, scan.x, scan.y + 24, scan.width, scan.height, "    Cancel", new ResourceLocation(Reference.MODID, "textures/gui/sco/red_x.png"), 16, 16, 16, 16, ImageOrientation.Left)
				.setEnabledColor(0xFF0000);
		
		buttonList.add(scan);
		buttonList.add(cancel);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		distance.drawTextBox();
		
		final String prompt = "Enter fluid meter scan radius:";
		fontRenderer.drawString(prompt, innerLeft + (innerWidth / 2) - (fontRenderer.getStringWidth(prompt) / 2), distance.y - fontRenderer.FONT_HEIGHT - 4, 0);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == cancel)
		{
			mc.displayGuiScreen(new GuiPOSFluidMeterList(register));
			return;
		}
		
		if (button == scan)
		{
			int scanDistance;
			try
			{
				scanDistance = Integer.parseInt(distance.getText());
				if (scanDistance <= 0)
				{
					return;
				}
			}
			catch(Exception ex)
			{
				return;
			}
			
			mc.displayGuiScreen(new GuiPOSFluidMeterAddList(register, scanDistance));
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		distance.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		distance.textboxKeyTyped(typedChar, keyCode);
	}
}
