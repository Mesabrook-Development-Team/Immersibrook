package rz.mesabrook.wbtc.util.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rz.mesabrook.wbtc.util.Reference;

@SuppressWarnings("WeakerAccess")
@Config(modid = Reference.MODID)
@LangKey(Reference.MODID + ".config.title")
public final class ModConfig 
{
	@Comment("Set to false to disable the welcome message shown at login")
	public static boolean showWelcome = true;
	
	@Comment("Set to false to disable sound effects for Food Cube blocks.")
	public static boolean foodCubeSounds = true;
	
	@Comment("Set to false to disable the random quote shown in tooltips attached to Immersibrook's blocks and items.")
	public static boolean funnyTooltips = true;
	
	@Comment("Set to false if you want to disable the Leather = Saddle smelting recipe.")
	public static boolean smeltingLeatherForASaddle = true;
	
	@Comment("Set to true if you want the Golden Apple Food Cube to give the player random potion effects when right-clicked. EXPERIMENTAL")
	public static boolean goldenAppleCubeGivesPotionEffects = false;
	
	@Comment("Set to false if you want to disable Immersibrook's /tpdim command.")
	public static boolean tpdimCommand = true;
	
	@Comment("Set to false if you want the Power of Thor enchantment's lightning strikes to not start fires.")
	public static boolean thorCausesFires = true;
	
	@Comment("Set the amount of times the First Aid Kit can be used before it breaks.")
	public static int firstAidUses = 12;
	
	@Mod.EventBusSubscriber(modid = Reference.MODID)
	private static class EventHandler
	{
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
		{
			if(event.getModID().equals(Reference.MODID))
			{
				ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
