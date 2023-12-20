package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;

import com.mesabrook.ib.apimodels.company.RegisterStatus.Statuses;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.net.sco.POSChangeStatusClientToServerPacket;
import com.mesabrook.ib.net.sco.POSOpenRegisterSecurityBoxInventoryGUIPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiPOSAdmin extends GuiPOSMainBase {

	private GuiImageLabelButton online;
	private GuiImageLabelButton offline;
	private GuiImageLabelButton securityBoxInv;
	private GuiImageLabelButton back;
	
	public GuiPOSAdmin(TileEntityRegister register) {
		super(register);
	}

	@Override
	public void initGui() {
		super.initGui();
		
		online = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 41, 83, 20, "    Bring Online", new ResourceLocation(Reference.MODID, "textures/misc/go.png"), 16, 16, 16, 16, ImageOrientation.Left)
					.setEnabledColor(0x008800);
		offline = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 61, 83, 20, "    Take Offline", new ResourceLocation(Reference.MODID, "textures/misc/cancel.png"), 16, 16, 16, 16, ImageOrientation.Left)
				.setEnabledColor(0x880000);
		securityBoxInv = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 81, 83, 20, "    Sec Box Inv", new ResourceLocation(Reference.MODID, "textures/misc/logo_cube.png"), 16, 16, 16, 16, ImageOrientation.Left)
				.setEnabledColor(0x373737);
		back = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 130, 83, 20, " Back", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 16, 16, 16, 16, ImageOrientation.Left)
				.setEnabledColor(0x373737);
		
		buttonList.add(online);
		buttonList.add(offline);
		buttonList.add(securityBoxInv);
		buttonList.add(back);
	}
	
	private boolean firstTick = true;
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		if (firstTick)
		{
			IEmployeeCapability capability = mc.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			if (capability == null || !capability.manageRegisters())
			{
				POSChangeStatusClientToServerPacket changeStatus = new POSChangeStatusClientToServerPacket();
				changeStatus.pos = register.getPos();
				changeStatus.status = RegisterStatuses.InSession;
				PacketHandler.INSTANCE.sendToServer(changeStatus);
				return;
			}
			
			firstTick = false;
		}
		
		fontRenderer.drawString("Admin Mode", innerLeft + 116, innerTop + 29, 0);
		fontRenderer.drawSplitString("Select an administrative action from the menu on the left", innerLeft + 117, innerTop + innerHeight / 2 - 25, 128, 0x000000);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == back)
		{
			mc.displayGuiScreen(new GuiPOSStarter(register));
			return;
		}
		
		if (button == securityBoxInv)
		{
			POSOpenRegisterSecurityBoxInventoryGUIPacket openSecurityBoxInvPacket = new POSOpenRegisterSecurityBoxInventoryGUIPacket();
			openSecurityBoxInvPacket.pos = register.getPos();
			PacketHandler.INSTANCE.sendToServer(openSecurityBoxInvPacket);
			return;
		}
		
		if (button == online || button == offline)
		{
			RegisterStatuses registerStatus = button == online ? RegisterStatuses.Online : RegisterStatuses.Offline;
			Statuses onlineStatus = button == online ? Statuses.Online : Statuses.Offline;
			
			POSChangeStatusClientToServerPacket changeStatusPacket = new POSChangeStatusClientToServerPacket();
			changeStatusPacket.pos = register.getPos();
			changeStatusPacket.status = registerStatus;
			changeStatusPacket.onlineStatusChange = onlineStatus;
			PacketHandler.INSTANCE.sendToServer(changeStatusPacket);
			
			register.setRegisterStatus(registerStatus);
		}
	}
}
