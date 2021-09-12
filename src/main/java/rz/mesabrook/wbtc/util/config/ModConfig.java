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
	
	@Comment("Set to false if you want to disable Immersibrook's /tpdim command.")
	public static boolean tpdimCommand = true;
	
	@Comment("Set to false if you want the Power of Thor enchantment's lightning strikes to not start fires.")
	public static boolean thorCausesFires = true;

	@Comment("Set to false if you don't want the Cat Block to spawn an Ocelot when broken in Survival Mode.")
	public static boolean catBlockMakesCat = true;
	
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

	@Comment("Should the Night Vision Goggles take damage when active in Survival Mode?")
	public static boolean nightVisionDamage = true;

	@Comment("[Debugging Option] Set to true if you want the fake light block created by Ceiling Lights to be rendered.")
	public static boolean renderFakeLightBlocks = false;
	
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
