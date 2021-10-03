package rz.mesabrook.wbtc.telecom.handlers;

import net.minecraft.block.BlockDirt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.telecom.CallManager;
import rz.mesabrook.wbtc.util.config.ModConfig;

import java.lang.reflect.Field;
import java.util.Arrays;

@EventBusSubscriber
public class ServerChatEventHandler {
	static Field serverControllerField;
	
	@SubscribeEvent
	public static void onServerChatEvent(ServerChatEvent e)
	{
		if (serverControllerField == null)
		{
			try
			{
				boolean isObfuscated = !Arrays.stream(BlockDirt.class.getMethods()).anyMatch(m -> m.getName() == "getStateFromMeta");
				serverControllerField = NetHandlerPlayServer.class.getDeclaredField(isObfuscated ? "field_147367_d" : "serverController");
				serverControllerField.setAccessible(true);
			}
			catch(Exception ex)
			{
				serverControllerField = null;
				Main.logger.error("An error occurred while trying to get serverController field for proximity chat.", ex);
				return;
			}
		}
		
		MinecraftServer server = null;
		
		try
		{
			server = (MinecraftServer)serverControllerField.get(e.getPlayer().connection);
		}
		catch (Exception ex)
		{
			Main.logger.error("An error occurred while trying to get serverController for proximity chat.", ex);
			return;
		}
		
		e.setCanceled(true);
		CallManager.instance().onPlayerChat(e.getPlayer(), e.getComponent());
		
		for(EntityPlayerMP player : server.getPlayerList().getPlayers())
		{
			if (player.getDistance(e.getPlayer()) <= ModConfig.proximityChatDistance)
			{
				player.sendMessage(e.getComponent());
			}
		}
	}
}
