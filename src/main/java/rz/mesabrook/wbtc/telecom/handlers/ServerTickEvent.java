package rz.mesabrook.wbtc.telecom.handlers;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import rz.mesabrook.wbtc.telecom.CallManager;

@EventBusSubscriber(Side.SERVER)
public class ServerTickEvent {

	@SubscribeEvent
	public static void onTick(TickEvent e)
	{
		if (e.phase != Phase.END)
		{
			return;
		}
		
		CallManager.instance().tick();
	}
}
