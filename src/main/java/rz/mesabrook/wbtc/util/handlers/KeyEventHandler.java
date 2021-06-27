package rz.mesabrook.wbtc.util.handlers;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import rz.mesabrook.wbtc.net.VestTogglePacket;
import rz.mesabrook.wbtc.proxy.ClientProxy;

@EventBusSubscriber(Side.CLIENT)
public class KeyEventHandler {
	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent e)
	{
		if (ClientProxy.vestToggleKey.isPressed())
		{
			VestTogglePacket packet = new VestTogglePacket();
			PacketHandler.INSTANCE.sendToServer(packet);
		}
	}
}
