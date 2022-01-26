package com.mesabrook.ib.telecom.handlers;

import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import com.mesabrook.ib.telecom.CallManager;

@EventBusSubscriber
public class ServerStopHandler {

	@Subscribe
	public static void onServerStop(FMLServerStoppingEvent e)
	{
		CallManager.instance().onServerStop();
	}
}
