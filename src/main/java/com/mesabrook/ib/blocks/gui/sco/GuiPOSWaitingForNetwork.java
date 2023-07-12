package com.mesabrook.ib.blocks.gui.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;

public class GuiPOSWaitingForNetwork extends GuiPOSBase {

	private int pollCounter = 0;
	private int frameCounter = 0;
	public GuiPOSWaitingForNetwork(TileEntityRegister register) {
		super(register);
	}

	@Override
	public void doDraw(int mouseX, int mouseY, float partialTicks) {		
		drawCenteredString(fontRenderer, TextFormatting.BOLD + "** WAITING FOR NETWORK **", width / 2, innerTop + 4, 0xFFFFFF);
		
		bindTexture("net_frame_" + frameCounter + ".png");
		drawModalRectWithCustomSizedTexture(width / 2 - 64, height / 2 - 64, 0, 0, 128, 128, 128, 128);
		
		if (++pollCounter >= 20)
		{
			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(register.getPos());
			if (!(te instanceof TileEntityRegister))
			{
				Minecraft.getMinecraft().displayGuiScreen(null);
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			if (register.getRegisterStatus() != RegisterStatuses.WaitingForNetwork)
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiPOSStarter(register));
			}
			
			pollCounter = 0;
			
			if (++frameCounter > 4)
			{
				frameCounter = 0;
			}
		}
	}

	@Override
	protected String getBackingTextureName() {
		return "shock_blue.png";
	}
}
