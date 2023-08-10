package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class WorldRenderHandler {

	@SubscribeEvent
	public static void worldRender(RenderGameOverlayEvent.Text e)
	{
		IEmployeeCapability cap = Minecraft.getMinecraft().player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		if (cap.getLocationID() == 0)
		{
			return;
		}
		
		String text = "On Duty: " + cap.getLocationEmployee().Location.Company.Name + " (" + cap.getLocationEmployee().Location.Name + ")";
		
		Minecraft.getMinecraft().fontRenderer.drawString(text, 0, 0, 0xFFFFFF);
	}
}
