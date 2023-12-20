package com.mesabrook.ib.util.config;

import com.mesabrook.ib.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
	
	@Comment("Set to false if you want the Power of Thor enchantment's lightning strikes to not start fires.")
	public static boolean thorCausesFires = true;
	
	@Comment("Set the amount of times the First Aid Kit can be used before it breaks.")
	public static int firstAidUses = 12;

	@Comment("Set the distance allowed for ceiling lights to place fake light blocks.")
	public static int ceilingLightDistance = 100;

	@Comment("Set to false to re-enable the vanilla Minecraft recipe for paper.")
	public static boolean makePaperProductionMoreRealistic = false;

	@Comment("Set to false to disable Immersibrook's recipe override for The RC Mod's plastic item.")
	public static boolean overrideRCModPlastic = true;
	
	@Comment("How many ticks should a phone ring for?")
	public static int phoneRingTicks = 1200; // 1 minute
	
	@Comment("How close do players need to be in order to talk to each other?")
	public static int proximityChatDistance = 50;
	
	@Comment("How many blocks around a cell antenna should a cell antenna check for height?  (Example, a value of 3 will result in a scan area of 3x3)")
	public static int cellAntennaHeightScanWidth = 10;
	
	@Comment("What is the optimal cell antenna height?")
	public static int cellAntennaOptimalHeight = 50;
	
	@Comment("How far out in blocks is the best cell reception?")
	public static int cellAntennaMaxReception = 2000;
	
	@Comment("How far from the max reception range will reception completely fail?")
	public static int cellAntennaSpottyReception = 3000;
	
	@Comment("What is the (inclusive) minimum randomly generated phone number?")
	public static int minimumPhoneNumber = 1000000;
	
	@Comment("What is the (exclusive) maximum randomly generated phone number?")
	public static int maximumPhoneNumber = 7999999;
	
	@Comment("What character should be used for scrambling text when outside of maximum reception range?")
	public static String scrambleCharacter = "-";

	@Comment("[Debugging Option] Set to true if you want the fake light block created by Ceiling Lights to be rendered.")
	public static boolean renderFakeLightBlocks = false;

	@Comment("Set to false to disable the funny death sound effect that plays when a player dies.")
	public static boolean oofDeathSound = true;

	@Comment("[Float] Phone Ringtone Loudness (default - 0.25F | max - 1.0F)")
	public static float ringtoneVolume = 0.75F;
	
	@Comment("Is proximity chat enabled?")
	public static boolean proximityChatEnabled = true;
	
	@Comment("How far does a player's voice travel if talking quietly?")
	public static int quietDistance = 2;
	
	@Comment("How far does a player's voice travel if talking normally?")
	public static int normalDistance = 7;
	
	@Comment("How far does a player's voice travel if talking loudly?")
	public static int loudDistance = 29;

	@Comment("[Accessibility] Show Incoming Call Message? (Enabled by default if subtitles are active)")
	public static boolean showCallMsgInChat = true;

	@Comment("[Special Smartphone] Randomly switch bezel colors when held?")
	public static boolean specialPhoneBezel = true;

	@Comment("[Special Smartphone] Bezel color change interval (WARNING: Do not set too low if you are prone to seizures!)")
	public static int colorInterval = 200;

	@Comment("[Minedroid] Enable debug mode?")
	public static boolean enableDebug = false;
	
	@Comment("[WEA] Check WX Radio advisories?")
	public static boolean enableWEAForWeather = false;
	
	@Comment("[WEA] IP Address of the WX Radio Advisory Product Server")
	public static String advisoryProductServerIP = "";
	
	@Comment("[WEA] Port of the WX Radio Advisory Product Server")
	public static int advisoryProductServerPort = 0;
	
	@Comment("[WEA] Marker Set ID for the display of weather polygons on DynMap (if installed)")
	public static String wxMarkerSetID = "wx";
	
	@Comment("[WEA] Automatically start WEA on server start")
	public static boolean autoStartWEA = false;

	@Comment("[Meme Command] Which voice tone do you want to use for the cough? (Options: masculine, feminine)")
	public static String coughTone = "masculine";

	@Comment("[Realism] Broadcast toilet flushing sound? [Requires MrCrayfish's Furniture Mod be installed]")
	public static boolean letOthersHearYourLogs = false;
	
	@Comment("[WEA] Alert color options. Use this format for each item: Alert Name|Line Color|Fill Color. Use RRGGBB notation for colors. A fill color of \"transparent\" will use a transparent inner fill.")
	public static String[] alertColorOptions = new String[]
	{
		"Severe Thunderstorm Watch|FFFF00|transparent",
		"Severe Thunderstorm Warning|FFFF00|FFFF00",
		"Tornado Watch|FF0000|transparent",
		"Tornado Warning|FF0000|FF0000"
	};
	
	@Comment("[MesaSuite] MesaSuite Base API URL")
	public static String mesasuiteBaseAPIUrl = "https://api.mesabrook.com";
	
	@Comment("[MesaSuite] MesaSuite Base OAuth URL")
	public static String mesasuiteBaseOAuthUrl = "https://auth.mesabrook.com";
	
	@Comment("[MesaSuite] MesaSuite Client ID")
	public static String mesasuiteClientID = "";
	
	@Comment("[Minedroid] What is the maximum battery charge for a Smartphone?")
	public static int smartphoneMaxBattery = 1600;

	@Comment("[Minedroid] Which skin fetching service should Minedroid use? [Available: mc-heads, crafatar] [Default: mc-heads]")
	public static String skinFetcherEngine = "mc-heads";
	
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
