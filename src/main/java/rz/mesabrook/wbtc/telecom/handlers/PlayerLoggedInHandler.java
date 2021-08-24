package rz.mesabrook.wbtc.telecom.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import rz.mesabrook.wbtc.telecom.CallManager;

@EventBusSubscriber
public class PlayerLoggedInHandler {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent e)
	{
		if (e.player.world.isRemote)
		{
			return;
		}
		
		e.player.inventoryContainer.addListener(new ContainerOpenHandler.PhoneContainerListener());
		
		CallManager manager = CallManager.instance();
		
		manager.sendQueryResponseForAllCalls((EntityPlayerMP)e.player);
	}
}
