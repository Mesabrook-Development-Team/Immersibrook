package rz.mesabrook.wbtc.util.handlers;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import rz.mesabrook.wbtc.net.EngravePacket;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.Reference;

public class PacketHandler 
{
	public static SimpleNetworkWrapper INSTANCE = null;
	private static int id = 0;
	
	public static void registerMessages()
	{
		INSTANCE = new SimpleNetworkWrapper(Reference.NETWORK_CHANNEL_NAME);
		
		INSTANCE.registerMessage(EngravePacket.Handler.class, EngravePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PlaySoundPacket.Handler.class, PlaySoundPacket.class, nextID(), Side.CLIENT);
	}
	
	private static int nextID()
	{
		return ++id;
	}
	
}
