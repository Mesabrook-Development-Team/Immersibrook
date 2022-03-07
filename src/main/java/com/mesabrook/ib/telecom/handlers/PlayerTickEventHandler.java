package com.mesabrook.ib.telecom.handlers;

import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneBase;
import com.mesabrook.ib.blocks.gui.telecom.Toaster;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map.Entry;

@EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class PlayerTickEventHandler {
	@SubscribeEvent
	public static void advanceInactiveToasts(PlayerTickEvent e)
	{
		if (e.phase == Phase.END)
		{
			for(Entry<String, Toaster> toasterEntry : Toaster.all())
			{
				if (Minecraft.getMinecraft().currentScreen == null ||
						!(Minecraft.getMinecraft().currentScreen instanceof GuiPhoneBase) ||
						!((GuiPhoneBase)Minecraft.getMinecraft().currentScreen).getCurrentPhoneNumber().equals(toasterEntry.getKey()))
				{
					toasterEntry.getValue().tickNoDraw();
				}
			}
		}
	}
}
