package com.mesabrook.ib.telecom.handlers;

import com.google.common.eventbus.Subscribe;
import com.mesabrook.ib.telecom.CallManager;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@EventBusSubscriber
public class ServerStopHandler {

	@Subscribe
	public static void onServerStop(FMLServerStoppingEvent e)
	{
		CallManager.instance().onServerStop();
	}
}
