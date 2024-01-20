package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiPOSOffline extends GuiPOSBase {
	private GuiImageLabelButton adminMode;
	public GuiPOSOffline(TileEntityRegister register) {
		super(register);
	}

	@Override
	protected String getBackingTextureName() {
		return "sco_red.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		IEmployeeCapability emp = mc.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		
		adminMode = new GuiImageLabelButton(0, innerLeft + innerWidth / 2 - 40, innerTop + innerHeight / 2 + 4, 80, 20, "    Admin Mode", new ResourceLocation(Reference.MODID, "textures/gui/cog.png"), 16, 16, 16, 16, GuiImageLabelButton.ImageOrientation.Left)
						.setEnabledColor(0);
		adminMode.visible = emp.getLocationID() == register.getLocationIDOwner();
		buttonList.add(adminMode);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		float scale = 3F;
		GlStateManager.scale(scale, scale, 1);
		
		fontRenderer.drawString(TextFormatting.ITALIC + "Offline", (int)((innerLeft + innerWidth / 2) * (1/scale)) - (fontRenderer.getStringWidth(TextFormatting.ITALIC + "Offline") / 2), (int)((innerTop + innerHeight / 2) * (1/scale)) - fontRenderer.FONT_HEIGHT, 0);
		
		GlStateManager.scale(1/scale, 1/scale, 1);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == adminMode)
		{
			mc.displayGuiScreen(new GuiPOSAdmin(register));
		}
	}
}
