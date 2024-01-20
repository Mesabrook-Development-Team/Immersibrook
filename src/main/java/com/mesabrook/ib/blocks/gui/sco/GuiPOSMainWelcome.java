package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.net.sco.POSChangeStatusClientToServerPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiPOSMainWelcome extends GuiPOSMainBase {

	private static final String MISSING_SCANNER_MESSAGE = "! SCANNER NOT DETECTED !";
	private int missingScannerMessageWidth = 0;
	boolean missingScanner = false;
	boolean flashState = true;
	long nextFlashTime = System.currentTimeMillis() + 1000;
	GuiImageLabelButton startButton;
	
	public GuiPOSMainWelcome(TileEntityRegister register) {
		super(register);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		missingScanner = register.getWorld().getBlockState(register.getPos().down()).getBlock() != ModBlocks.SCO_SCANNER;
		missingScannerMessageWidth = fontRenderer.getStringWidth(MISSING_SCANNER_MESSAGE);
		
		startButton = new GuiImageLabelButton(0, innerLeft + innerWidth - 52, innerTop + innerHeight - 21, 46, 15, "Start   ", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_right.png"), 10, 10, 10, 10, ImageOrientation.Right)
							.setEnabledColor(0x00FF00)
							.setTextScale(0.8F);
		buttonList.add(startButton);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		if (missingScanner)
		{
			drawRect(innerLeft + 6, innerTop + 6, innerLeft + innerWidth - 6, innerTop + fontRenderer.FONT_HEIGHT + 10, 0xFFFFFF00);
			if (flashState)
			{
				fontRenderer.drawString(MISSING_SCANNER_MESSAGE, (innerLeft + innerWidth / 2)  - (missingScannerMessageWidth / 2), innerTop + 8, 0);				
			}
			
			if (nextFlashTime <= System.currentTimeMillis())
			{
				nextFlashTime = System.currentTimeMillis() + 1000;
				flashState = !flashState;
			}
		}
		
		drawRect(innerLeft + 6, innerTop + (innerHeight / 2) - (fontRenderer.FONT_HEIGHT / 2) - 8, innerLeft + innerWidth - 6, innerTop + (innerHeight / 2) + (fontRenderer.FONT_HEIGHT / 2) + 8, 0xFFc6c6c6);
		int fontWidth = fontRenderer.getStringWidth("Please scan first item or press Start");
		fontRenderer.drawString("Please scan first item or press Start", innerLeft + (innerWidth / 2) - (fontWidth / 2), innerTop + (innerHeight / 2) - fontRenderer.FONT_HEIGHT / 2, 0x373737);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button == startButton)
		{
			POSChangeStatusClientToServerPacket changeStatusPacket = new POSChangeStatusClientToServerPacket();
			changeStatusPacket.pos = register.getPos();
			changeStatusPacket.status = RegisterStatuses.InSession;
			PacketHandler.INSTANCE.sendToServer(changeStatusPacket);
			
			mc.displayGuiScreen(new GuiPOSInSession((TileEntityRegister)register.getWorld().getTileEntity(register.getPos())));
		}
	}

}
