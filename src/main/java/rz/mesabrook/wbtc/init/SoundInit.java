package rz.mesabrook.wbtc.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
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
	}
	
	private static SoundEvent addSoundsToRegistry(String soundId)
	{
		ResourceLocation shotSoundLocation = new ResourceLocation(Reference.MODID, soundId);
		SoundEvent soundEvent = new SoundEvent(shotSoundLocation);
		soundEvent.setRegistryName(shotSoundLocation);
		return soundEvent;
	}
	
	public class SoundRegisterListener
	{
		@SubscribeEvent(priority = EventPriority.HIGH, receiveCanceled = true)
		public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event)
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
				
				MinecraftForge.EVENT_BUS.register(new SoundRegisterListener());
			}
			catch(Exception ex)
			{
				Main.logger.error("[Mesalleaneous SoundLoader] ERROR: " + ex);
			}
		}
	}
}
