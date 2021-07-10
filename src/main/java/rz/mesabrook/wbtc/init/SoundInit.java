package rz.mesabrook.wbtc.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.util.Reference;

public class SoundInit 
{
	public static final SoundEvent OWO_SFX;
	public static final SoundEvent RZ_TROPHY;
	public static final SoundEvent CSX_TROPHY;
	public static final SoundEvent TD_TROPHY;
	public static final SoundEvent TLZ_TROPHY;
	public static final SoundEvent LW_TROPHY;
	public static final SoundEvent MD_TROPHY;
	public static final SoundEvent SVV_TROPHY;
	public static final SoundEvent TROPHY_BREAK;
	public static final SoundEvent OOF;
	public static final SoundEvent CAN_OPEN;
	public static final SoundEvent CAN_CLOSE;
	public static final SoundEvent CHEESE_PLACE;
	public static final SoundEvent CHEESE_CLICK;
	public static final SoundEvent PIE;
	public static final SoundEvent FISH;
	public static final SoundEvent FISH_FULL;
	public static final SoundEvent NO;
	public static final SoundEvent NV;
	public static final SoundEvent VEST;
	public static final SoundEvent SAFETY;
	public static final SoundEvent YOINK;
	public static final SoundEvent BEANED;
	public static final SoundEvent BONG;
	public static final SoundEvent BONK;
	public static final SoundEvent POP;
	public static final SoundEvent POP_SINGLE;
	public static final SoundEvent SPREE;
	public static final SoundEvent OWIE;
	public static final SoundEvent DOVE;
	public static final SoundEvent CPW;
	public static final SoundEvent SPLOOT;
	public static final SoundEvent OWO2;
	public static final SoundEvent OWO3;
	public static final SoundEvent PUFF;
	public static final SoundEvent WOOSH;
	public static final SoundEvent DOOT;
	public static final SoundEvent SHEP;
	public static final SoundEvent TRUMPET;
	
	// Meme Records integration
	public static final SoundEvent AMALTHEA;
	public static final SoundEvent NYAN;
	public static final SoundEvent USSR1;
	public static final SoundEvent USSR2;
	public static final SoundEvent BOOEY;
	public static final SoundEvent DOLAN;
	public static final SoundEvent MURICA;
	public static final SoundEvent PIGSTEP;
	public static final SoundEvent KRAB_BORG;
	public static final SoundEvent KRAB_BORG_FULL;
	public static final SoundEvent MS_XP;

	// Plastic SoundType
	public static final SoundEvent PLASTIC_PLACE;
	public static final SoundEvent PLASTIC_BREAK;
	public static final SoundEvent PLASTIC_HIT;
	public static final SoundEvent PLASTIC_STEP;
	public static final SoundEvent PLASTIC_LAND;

	// Warp Metro
	public static final SoundEvent TM_BEEP;

	static
	{
		OWO_SFX = addSoundsToRegistry("owo");
		RZ_TROPHY = addSoundsToRegistry("rz_trophy");
		CSX_TROPHY = addSoundsToRegistry("csx_trophy");
		TD_TROPHY = addSoundsToRegistry("td_trophy");
		TLZ_TROPHY = addSoundsToRegistry("tlz_trophy");
		LW_TROPHY = addSoundsToRegistry("lw_trophy");
		MD_TROPHY = addSoundsToRegistry("md_trophy");
		SVV_TROPHY = addSoundsToRegistry("svv_trophy");
		TROPHY_BREAK = addSoundsToRegistry("trophy_break");
		OOF = addSoundsToRegistry("oof");
		CAN_OPEN = addSoundsToRegistry("can_open");
		CAN_CLOSE = addSoundsToRegistry("can_close");
		CHEESE_PLACE = addSoundsToRegistry("cheese_place");
		CHEESE_CLICK = addSoundsToRegistry("cheese_click");
		PIE = addSoundsToRegistry("pie");
		FISH = addSoundsToRegistry("fish");
		FISH_FULL = addSoundsToRegistry("fish_full");
		NO = addSoundsToRegistry("no");
		NV = addSoundsToRegistry("nv");
		VEST = addSoundsToRegistry("vest");
		SAFETY = addSoundsToRegistry("safety");
		YOINK = addSoundsToRegistry("yoink");
		BEANED = addSoundsToRegistry("beaned");
		BONG = addSoundsToRegistry("bong");
		BONK = addSoundsToRegistry("bonk");
		POP = addSoundsToRegistry("pop");
		POP_SINGLE = addSoundsToRegistry("pop_single");
		SPREE = addSoundsToRegistry("spree");
		OWIE = addSoundsToRegistry("owie");
		DOVE = addSoundsToRegistry("dove");
		CPW = addSoundsToRegistry("cpw");
		SPLOOT = addSoundsToRegistry("sploot");
		OWO2 = addSoundsToRegistry("new_owo");
		OWO3 = addSoundsToRegistry("new_owo_short");
		PUFF = addSoundsToRegistry("puff");
		WOOSH = addSoundsToRegistry("woosh");
		DOOT = addSoundsToRegistry("doot");
		SHEP = addSoundsToRegistry("shep");
		TRUMPET = addSoundsToRegistry("trumpet");
		
		// Begin Records
		AMALTHEA = addSoundsToRegistry("amalthea");
		NYAN = addSoundsToRegistry("nyan");
		USSR1 = addSoundsToRegistry("ussr1");
		USSR2 = addSoundsToRegistry("ussr2");
		BOOEY = addSoundsToRegistry("baba_booey");
		DOLAN = addSoundsToRegistry("dolan");
		MURICA = addSoundsToRegistry("murica");
		PIGSTEP = addSoundsToRegistry("pigstep");
		KRAB_BORG = addSoundsToRegistry("krab_borg");
		KRAB_BORG_FULL = addSoundsToRegistry("krab_borg_full");
		MS_XP = addSoundsToRegistry("ms_xp");

		// Plastic SoundType
		PLASTIC_BREAK = addSoundsToRegistry("plastic_break");
		PLASTIC_HIT = addSoundsToRegistry("plastic_hit");
		PLASTIC_LAND = addSoundsToRegistry("plastic_land");
		PLASTIC_PLACE = addSoundsToRegistry("plastic_place");
		PLASTIC_STEP = addSoundsToRegistry("plastic_step");

		// Warp Metro
		TM_BEEP = addSoundsToRegistry("tm_beep");
	}
	
	private static SoundEvent addSoundsToRegistry(String soundId)
	{
		ResourceLocation shotSoundLocation = new ResourceLocation(Reference.MODID, soundId);
		SoundEvent soundEvent = new SoundEvent(shotSoundLocation);
		soundEvent.setRegistryName(shotSoundLocation);
		return soundEvent;
	}
	
	@EventBusSubscriber
	public static class SoundRegisterListener
	{
		@SubscribeEvent
		public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event)
		{
			try
			{
				event.getRegistry().registerAll(SoundInit.OWO_SFX);
				event.getRegistry().registerAll(SoundInit.RZ_TROPHY);
				event.getRegistry().registerAll(SoundInit.CSX_TROPHY);
				event.getRegistry().registerAll(SoundInit.TD_TROPHY);
				event.getRegistry().registerAll(SoundInit.TLZ_TROPHY);
				event.getRegistry().registerAll(SoundInit.LW_TROPHY);
				event.getRegistry().registerAll(SoundInit.MD_TROPHY);
				event.getRegistry().registerAll(SoundInit.SVV_TROPHY);
				event.getRegistry().registerAll(SoundInit.TROPHY_BREAK);
				event.getRegistry().registerAll(SoundInit.OOF);
				event.getRegistry().registerAll(SoundInit.CAN_OPEN);
				event.getRegistry().registerAll(SoundInit.CAN_CLOSE);
				event.getRegistry().registerAll(SoundInit.CHEESE_CLICK);
				event.getRegistry().registerAll(SoundInit.CHEESE_PLACE);
				event.getRegistry().registerAll(SoundInit.PIE);
				event.getRegistry().registerAll(SoundInit.AMALTHEA);
				event.getRegistry().registerAll(SoundInit.NYAN);
				event.getRegistry().registerAll(SoundInit.USSR1);
				event.getRegistry().registerAll(SoundInit.USSR2);
				event.getRegistry().registerAll(SoundInit.BOOEY);
				event.getRegistry().registerAll(SoundInit.DOLAN);
				event.getRegistry().registerAll(SoundInit.MURICA);
				event.getRegistry().registerAll(SoundInit.PIGSTEP);
				event.getRegistry().registerAll(SoundInit.KRAB_BORG);
				event.getRegistry().registerAll(SoundInit.KRAB_BORG_FULL);
				event.getRegistry().registerAll(SoundInit.FISH);
				event.getRegistry().registerAll(SoundInit.FISH_FULL);
				event.getRegistry().registerAll(SoundInit.NO);
				event.getRegistry().registerAll(SoundInit.NV);
				event.getRegistry().registerAll(SoundInit.VEST);
				event.getRegistry().registerAll(SoundInit.SAFETY);
				event.getRegistry().registerAll(SoundInit.YOINK);
				event.getRegistry().registerAll(SoundInit.BEANED);
				event.getRegistry().registerAll(SoundInit.BONG);
				event.getRegistry().registerAll(SoundInit.BONK);
				event.getRegistry().registerAll(SoundInit.POP);
				event.getRegistry().registerAll(SoundInit.POP_SINGLE);
				event.getRegistry().registerAll(SoundInit.SPREE);
				event.getRegistry().registerAll(SoundInit.OWIE);
				event.getRegistry().registerAll(SoundInit.DOVE);
				event.getRegistry().registerAll(SoundInit.MS_XP);
				event.getRegistry().registerAll(SoundInit.PLASTIC_PLACE);
				event.getRegistry().registerAll(SoundInit.PLASTIC_BREAK);
				event.getRegistry().registerAll(SoundInit.PLASTIC_HIT);
				event.getRegistry().registerAll(SoundInit.PLASTIC_LAND);
				event.getRegistry().registerAll(SoundInit.PLASTIC_STEP);
				event.getRegistry().registerAll(SoundInit.CPW);
				event.getRegistry().registerAll(SoundInit.SPLOOT);
				event.getRegistry().registerAll(SoundInit.OWO2);
				event.getRegistry().registerAll(SoundInit.OWO3);
				event.getRegistry().registerAll(SoundInit.PUFF);
				event.getRegistry().registerAll(SoundInit.WOOSH);
				event.getRegistry().registerAll(SoundInit.DOOT);
				event.getRegistry().registerAll(SoundInit.SHEP);
				event.getRegistry().registerAll(SoundInit.TRUMPET);
				event.getRegistry().registerAll(SoundInit.TM_BEEP);
				
				MinecraftForge.EVENT_BUS.register(new SoundRegisterListener());
				Main.logger.info("[" + Reference.MODNAME + " SoundLoader] Loaded");
			}
			catch(Exception ex)
			{
				Main.logger.error("[" + Reference.MODNAME + " ] Ah fuck, something broke...");
				Main.logger.error("[" + Reference.MODNAME + " ] ERROR IN SOUNDLOADER" + ex);
			}
		}
	}
}
