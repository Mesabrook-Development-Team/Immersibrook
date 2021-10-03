package rz.mesabrook.wbtc.telecom.handlers;

import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import rz.mesabrook.wbtc.telecom.CallManager;

@EventBusSubscriber
public class ServerStopHandler {

	@Subscribe
	public static void onServerStop(FMLServerStoppingEvent e)
	{
		CallManager.instance().onServerStop();
	}
}
