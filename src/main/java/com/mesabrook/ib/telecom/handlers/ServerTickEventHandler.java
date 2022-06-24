package com.mesabrook.ib.telecom.handlers;

import com.mesabrook.ib.telecom.CallManager;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

@EventBusSubscriber
public class ServerTickEventHandler {

	@SubscribeEvent
	public static void onTick(ServerTickEvent e)
	{
		if (e.phase != Phase.END)
		{
			return;
		}
		
		CallManager.instance().tick();
		WirelessEmergencyAlertManager.instance().sendAlerts();
	}
}
