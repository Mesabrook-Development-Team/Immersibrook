package rz.mesabrook.wbtc.telecom.handlers;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import rz.mesabrook.wbtc.telecom.CallManager;

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
	}
}
