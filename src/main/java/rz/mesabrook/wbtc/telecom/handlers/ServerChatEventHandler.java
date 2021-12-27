package rz.mesabrook.wbtc.telecom.handlers;

import net.minecraft.block.BlockDirt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.telecom.CallManager;
import rz.mesabrook.wbtc.util.config.ModConfig;
import rz.mesabrook.wbtc.util.saveData.PlayerLoudnessData;
import rz.mesabrook.wbtc.util.saveData.PlayerLoudnessLevel;

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
		
		CallManager.instance().onPlayerChat(e.getPlayer(), e.getComponent());
		
		if (ModConfig.proximityChatEnabled)
		{
			e.setCanceled(true);
			PlayerLoudnessData loudnessData = PlayerLoudnessData.getOrCreate(e.getPlayer().world);
			int distance = 0;
			PlayerLoudnessLevel loudnessLevel = loudnessData.getLevelForPlayer(e.getPlayer().getUniqueID());
			
			if (loudnessLevel == PlayerLoudnessLevel.Globally && !e.getPlayer().canUseCommand(4, "talk"))
			{
				e.getPlayer().sendMessage(new TextComponentTranslation("im.talk.noglobalperm", new Object[0]));
				return;
			}
			
			for(EntityPlayerMP player : server.getPlayerList().getPlayers())
			{
				switch(loudnessLevel)
				{
					case Quietly:
						distance = ModConfig.quietDistance;
						break;
					case Normally:
						distance = ModConfig.normalDistance;
						break;
					case Loudly:
						distance = ModConfig.loudDistance;
						break;
					case Globally:
						distance = -1;
						break;
				}
				
				TextComponentString prefix = new TextComponentString("");
				switch(loudnessLevel)
				{
					case Quietly:
						prefix.setStyle(new Style().setItalic(true).setColor(TextFormatting.GRAY));
						prefix.appendText(("[Quietly]"));
						break;
					case Loudly:
						prefix.setStyle(new Style().setItalic(true).setBold(true).setColor(TextFormatting.RED));
						prefix.appendText(("[LOUDLY]"));
						break;
					case Globally:
						prefix.setStyle(new Style().setItalic(true).setColor(TextFormatting.YELLOW));
						prefix.appendText(("[Global]"));
						break;
				}
				
				if (distance == -1 || player.getDistance(e.getPlayer()) <= distance)
				{
					// Minecraft formatting is stupid - do some fuckery to just prepend the text with a specially formatted message
					
					// Get Original Style
					Style originalStyle = e.getComponent().getStyle().createDeepCopy();
					TextFormatting originalColor = originalStyle.getColor(); // Oh, yeah, if there's no specified color, it's null. :rolling_eyes:
					
					// Append the message (which sets the parent style)
					prefix.appendSibling(e.getComponent());
					
					// Set the style of the chat message back to what it was previously. 
					// Set the color specifically to white if it's null
					e.getComponent().setStyle(originalStyle).getStyle().setColor(originalColor == null ? TextFormatting.WHITE : originalColor);
					
					// Send the message
					player.sendMessage(prefix);
				}
			}
		}
	}
}
