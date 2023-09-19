package com.mesabrook.ib.init;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundInit
{
	public static final SoundEvent OWO_SFX;
	public static final SoundEvent RZ_TROPHY;
	public static final SoundEvent CSX_TROPHY;
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
	public static final SoundEvent HEARTBEAT;
	public static final SoundEvent SQUID;
	public static final SoundEvent REVERB;
	public static final SoundEvent BURP;
	public static final SoundEvent QUACK;
	public static final SoundEvent QUACKBOOM;
	public static final SoundEvent BAKA;
	public static final SoundEvent MALLET;
	public static final SoundEvent POLICE_HELMET;
	public static final SoundEvent KEKW;
	public static final SoundEvent TWO_YEARS;
	public static final SoundEvent LEOTEU_ON;
	public static final SoundEvent LEOTEU_OFF;
	public static final SoundEvent EXP;
	public static final SoundEvent LOUDBONG;
	public static final SoundEvent BOOK_OPEN;
	public static final SoundEvent BOOK_CLOSE;
	public static final SoundEvent MH_OPEN;
	public static final SoundEvent MH_CLOSE;
	public static final SoundEvent JINGLES;
	public static final SoundEvent RIP;
	public static final SoundEvent SUS1;
	public static final SoundEvent SUS2;
	public static final SoundEvent SPICY;
	public static final SoundEvent OK1;
	public static final SoundEvent OK2;
	public static final SoundEvent DEATH;
	public static final SoundEvent WATERPHONE;
	public static final SoundEvent HEAL;
	public static final SoundEvent GAVEL;
	public static final SoundEvent SPONGE_EQUIP;
	public static final SoundEvent SPONGE_USE;
	public static final SoundEvent FART_1;
	public static final SoundEvent FART_2;
	public static final SoundEvent FART_3;
	public static final SoundEvent FART_4;
	public static final SoundEvent FART_5;
	public static final SoundEvent COUGH_M;
	public static final SoundEvent COUGH_F;
	public static final SoundEvent CHISEL;
	public static final SoundEvent RATION_OPEN;
	public static final SoundEvent TAPE_MEASURE_OPEN;
	public static final SoundEvent TAPE_MEASURE_CLOSE;

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
	public static final SoundEvent COOKING;
	public static final SoundEvent MEMORY;

	// Holiday Records
	public static final SoundEvent SPOOKY;
	public static final SoundEvent RITZ;
	public static final SoundEvent HL3;

	// Plastic SoundType
	public static final SoundEvent PLASTIC_PLACE;
	public static final SoundEvent PLASTIC_BREAK;
	public static final SoundEvent PLASTIC_HIT;
	public static final SoundEvent PLASTIC_STEP;
	public static final SoundEvent PLASTIC_LAND;

	// Manhole SoundType
	public static final SoundEvent MANHOLE_OPEN;
	public static final SoundEvent MANHOLE_CLOSE;
	public static final SoundEvent MANHOLE_PLACE;
	public static final SoundEvent MANHOLE_BREAK;
	public static final SoundEvent MANHOLE_STEP;

	// Warp Metro
	public static final SoundEvent TM_BEEP;

	// Comms
	public static final SoundEvent RADIO_CLOSE;
	public static final SoundEvent PHONE_ACTIVATE;
	public static final SoundEvent DING_1;
	public static final SoundEvent DING_2;
	public static final SoundEvent DING_3;
	public static final SoundEvent DING_4;
	public static final SoundEvent DING_5;
	public static final SoundEvent DING_6;
	public static final SoundEvent DING_7;
	public static final SoundEvent DING_8;
	public static final SoundEvent DING_9;
	public static final SoundEvent OUTGOING_CALL;
	public static final SoundEvent SIT;
	public static final SoundEvent SIT_1;
	public static final SoundEvent SIT_2;
	public static final SoundEvent SIT_3;
	public static final SoundEvent SIT_4;
	public static final SoundEvent SIT_5;
	public static final SoundEvent STARTCALL;
	public static final SoundEvent ENDCALL;
	public static final SoundEvent PHONE_UNLOCK;
	public static final SoundEvent BUSY;
	public static final SoundEvent ALERT_TONE;
	public static final SoundEvent PHONE_OFF;
	public static final SoundEvent MINEDROID_STARTUP;
	public static final SoundEvent PHONE_BATTERY_LOW;
	public static final SoundEvent PHONE_UNBOX;

	// Ringtones
	public static final SoundEvent RING_1;
	public static final SoundEvent RING_2;
	public static final SoundEvent RING_3;
	public static final SoundEvent RING_4;
	public static final SoundEvent RING_5;
	public static final SoundEvent RING_6;
	public static final SoundEvent RING_7;
	public static final SoundEvent RING_8;
	public static final SoundEvent RING_9;
	public static final SoundEvent RING_10;
	public static final SoundEvent RING_11;
	public static final SoundEvent RING_12;
	public static final SoundEvent RING_13;
	public static final SoundEvent RING_14;
	public static final SoundEvent RING_15;

	// DTMF Phone Tones
	public static final SoundEvent DTMF_0;
	public static final SoundEvent DTMF_1;
	public static final SoundEvent DTMF_2;
	public static final SoundEvent DTMF_3;
	public static final SoundEvent DTMF_4;
	public static final SoundEvent DTMF_5;
	public static final SoundEvent DTMF_6;
	public static final SoundEvent DTMF_7;
	public static final SoundEvent DTMF_8;
	public static final SoundEvent DTMF_9;
	public static final SoundEvent PHONE_CRASH;
	public static final SoundEvent PHONE_BOOTUP;
	public static final SoundEvent BATTERY_LOW;

	static
	{
		OWO_SFX = addSoundsToRegistry("owo");
		RZ_TROPHY = addSoundsToRegistry("rz_trophy");
		CSX_TROPHY = addSoundsToRegistry("csx_trophy");
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
		HEARTBEAT = addSoundsToRegistry("hb");
		SQUID = addSoundsToRegistry("squidward");
		REVERB = addSoundsToRegistry("reverb");
		BURP = addSoundsToRegistry("burp");
		QUACK = addSoundsToRegistry("duck");
		QUACKBOOM = addSoundsToRegistry("duckboom");
		BAKA = addSoundsToRegistry("baka");
		MALLET = addSoundsToRegistry("mallet");
		POLICE_HELMET = addSoundsToRegistry("police_helmet");
		KEKW = addSoundsToRegistry("kekw");
		TWO_YEARS = addSoundsToRegistry("two_years");
		LEOTEU_ON = addSoundsToRegistry("leoteu_on");
		LEOTEU_OFF = addSoundsToRegistry("leoteu_off");
		EXP = addSoundsToRegistry("exp");
		LOUDBONG = addSoundsToRegistry("loudbong");
		BOOK_OPEN = addSoundsToRegistry("book_open");
		BOOK_CLOSE = addSoundsToRegistry("book_close");
		MH_OPEN = addSoundsToRegistry("mh_open");
		MH_CLOSE = addSoundsToRegistry("mh_close");
		JINGLES = addSoundsToRegistry("jingles");
		RIP = addSoundsToRegistry("rip");
		SUS1 = addSoundsToRegistry("sus1");
		SUS2 = addSoundsToRegistry("sus2");
		SPICY = addSoundsToRegistry("spicy");
		OK1 = addSoundsToRegistry("ok1");
		OK2 = addSoundsToRegistry("ok2");
		DEATH = addSoundsToRegistry("death");
		WATERPHONE = addSoundsToRegistry("waterphone");
		HEAL = addSoundsToRegistry("heal");
		GAVEL = addSoundsToRegistry("gavel");
		SPONGE_EQUIP = addSoundsToRegistry("sponge_equip");
		SPONGE_USE = addSoundsToRegistry("sponge_use");
		FART_1 = addSoundsToRegistry("fart_1");
		FART_2 = addSoundsToRegistry("fart_2");
		FART_3 = addSoundsToRegistry("fart_3");
		FART_4 = addSoundsToRegistry("fart_4");
		FART_5 = addSoundsToRegistry("fart_5");
		COUGH_M = addSoundsToRegistry("cough_m");
		COUGH_F = addSoundsToRegistry("cough_f");
		CHISEL = addSoundsToRegistry("chisel");
		RATION_OPEN = addSoundsToRegistry("ration_open");
		TAPE_MEASURE_OPEN = addSoundsToRegistry("tape_measure_open");
		TAPE_MEASURE_CLOSE = addSoundsToRegistry("tape_measure_close");

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
		SPOOKY = addSoundsToRegistry("spooky");
		RITZ = addSoundsToRegistry("ritz");
		HL3 = addSoundsToRegistry("hl3");
		COOKING = addSoundsToRegistry("cooking");
		MEMORY = addSoundsToRegistry("memory");

		// Plastic SoundType
		PLASTIC_BREAK = addSoundsToRegistry("plastic_break");
		PLASTIC_HIT = addSoundsToRegistry("plastic_hit");
		PLASTIC_LAND = addSoundsToRegistry("plastic_land");
		PLASTIC_PLACE = addSoundsToRegistry("plastic_place");
		PLASTIC_STEP = addSoundsToRegistry("plastic_step");

		// Manhole SoundType
		MANHOLE_OPEN = addSoundsToRegistry("manhole_open");
		MANHOLE_CLOSE = addSoundsToRegistry("manhole_close");
		MANHOLE_PLACE = addSoundsToRegistry("manhole_place");
		MANHOLE_BREAK = addSoundsToRegistry("manhole_break");
		MANHOLE_STEP = addSoundsToRegistry("manhole_step");

		// Warp Metro
		TM_BEEP = addSoundsToRegistry("tm_beep");

		// Comms
		RADIO_CLOSE = addSoundsToRegistry("radio_close");
		DING_1 = addSoundsToRegistry("ding_1");
		DING_2 = addSoundsToRegistry("ding_2");
		DING_3 = addSoundsToRegistry("ding_3");
		DING_4 = addSoundsToRegistry("ding_4");
		DING_5 = addSoundsToRegistry("ding_5");
		DING_6 = addSoundsToRegistry("ding_6");
		DING_7 = addSoundsToRegistry("ding_7");
		DING_8 = addSoundsToRegistry("ding_8");
		DING_9 = addSoundsToRegistry("ding_9");
		OUTGOING_CALL = addSoundsToRegistry("outgoingcall");
		SIT = addSoundsToRegistry("sit");
		SIT_1 = addSoundsToRegistry("sit_1");
		SIT_2 = addSoundsToRegistry("sit_2");
		SIT_3 = addSoundsToRegistry("sit_3");
		SIT_4 = addSoundsToRegistry("sit_4");
		SIT_5 = addSoundsToRegistry("sit_5");
		STARTCALL = addSoundsToRegistry("startcall");
		ENDCALL = addSoundsToRegistry("endcall");
		PHONE_ACTIVATE = addSoundsToRegistry("welcome");
		PHONE_UNLOCK = addSoundsToRegistry("phone_unlock");
		BUSY = addSoundsToRegistry("busy");
		ALERT_TONE = addSoundsToRegistry("alert_tone");
		PHONE_OFF = addSoundsToRegistry("phone_off");
		MINEDROID_STARTUP = addSoundsToRegistry("minedroid_startup");
		PHONE_CRASH = addSoundsToRegistry("phone_crash");
		PHONE_BATTERY_LOW = addSoundsToRegistry("phone_battery_low");
		PHONE_BOOTUP = addSoundsToRegistry("minedroid_firstboot");
		BATTERY_LOW = addSoundsToRegistry("battery_low");
		PHONE_UNBOX = addSoundsToRegistry("phone_unbox");

		// Ringtones
		RING_1 = addSoundsToRegistry("ring_1");
		RING_2 = addSoundsToRegistry("ring_2");
		RING_3 = addSoundsToRegistry("ring_3");
		RING_4 = addSoundsToRegistry("ring_4");
		RING_5 = addSoundsToRegistry("ring_5");
		RING_6 = addSoundsToRegistry("ring_6");
		RING_7 = addSoundsToRegistry("ring_7");
		RING_8 = addSoundsToRegistry("ring_8");
		RING_9 = addSoundsToRegistry("ring_9");
		RING_10 = addSoundsToRegistry("ring_10");
		RING_11 = addSoundsToRegistry("ring_11");
		RING_12 = addSoundsToRegistry("ring_12");
		RING_13 = addSoundsToRegistry("ring_13");
		RING_14 = addSoundsToRegistry("ring_14");
		RING_15 = addSoundsToRegistry("ring_15");

		// DTMF Phone Tones
		DTMF_0 = addSoundsToRegistry("dtmf_0");
		DTMF_1 = addSoundsToRegistry("dtmf_1");
		DTMF_2 = addSoundsToRegistry("dtmf_2");
		DTMF_3 = addSoundsToRegistry("dtmf_3");
		DTMF_4 = addSoundsToRegistry("dtmf_4");
		DTMF_5 = addSoundsToRegistry("dtmf_5");
		DTMF_6 = addSoundsToRegistry("dtmf_6");
		DTMF_7 = addSoundsToRegistry("dtmf_7");
		DTMF_8 = addSoundsToRegistry("dtmf_8");
		DTMF_9 = addSoundsToRegistry("dtmf_9");
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
				event.getRegistry().registerAll(SoundInit.HEARTBEAT);
				event.getRegistry().registerAll(SoundInit.SPOOKY);
				event.getRegistry().registerAll(SoundInit.RITZ);
				event.getRegistry().registerAll(SoundInit.HL3);
				event.getRegistry().registerAll(SoundInit.SQUID);
				event.getRegistry().registerAll(SoundInit.REVERB);
				event.getRegistry().registerAll(SoundInit.BURP);
				event.getRegistry().registerAll(SoundInit.RADIO_CLOSE);
				event.getRegistry().registerAll(SoundInit.DING_1);
				event.getRegistry().registerAll(SoundInit.DING_2);
				event.getRegistry().registerAll(SoundInit.DING_3);
				event.getRegistry().registerAll(SoundInit.DING_4);
				event.getRegistry().registerAll(SoundInit.DING_5);
				event.getRegistry().registerAll(SoundInit.DING_6);
				event.getRegistry().registerAll(SoundInit.DING_7);
				event.getRegistry().registerAll(SoundInit.OUTGOING_CALL);
				event.getRegistry().registerAll(SoundInit.SIT);
				event.getRegistry().registerAll(SoundInit.STARTCALL);
				event.getRegistry().registerAll(SoundInit.ENDCALL);
				event.getRegistry().registerAll(SoundInit.DTMF_0);
				event.getRegistry().registerAll(SoundInit.DTMF_1);
				event.getRegistry().registerAll(SoundInit.DTMF_2);
				event.getRegistry().registerAll(SoundInit.DTMF_3);
				event.getRegistry().registerAll(SoundInit.DTMF_4);
				event.getRegistry().registerAll(SoundInit.DTMF_5);
				event.getRegistry().registerAll(SoundInit.DTMF_6);
				event.getRegistry().registerAll(SoundInit.DTMF_7);
				event.getRegistry().registerAll(SoundInit.DTMF_8);
				event.getRegistry().registerAll(SoundInit.DTMF_9);
				event.getRegistry().registerAll(SoundInit.QUACK);
				event.getRegistry().registerAll(SoundInit.QUACKBOOM);
				event.getRegistry().registerAll(SoundInit.BAKA);
				event.getRegistry().registerAll(SoundInit.MALLET);
				event.getRegistry().registerAll(SoundInit.COOKING);
				event.getRegistry().registerAll(SoundInit.POLICE_HELMET);
				event.getRegistry().registerAll(SoundInit.KEKW);
				event.getRegistry().registerAll(SoundInit.PHONE_ACTIVATE);
				event.getRegistry().registerAll(SoundInit.TWO_YEARS);
				event.getRegistry().registerAll(SoundInit.LEOTEU_OFF);
				event.getRegistry().registerAll(SoundInit.LEOTEU_ON);
				event.getRegistry().registerAll(SoundInit.EXP);
				event.getRegistry().registerAll(SoundInit.LOUDBONG);
				event.getRegistry().registerAll(SoundInit.MEMORY);
				event.getRegistry().registerAll(SoundInit.BOOK_OPEN);
				event.getRegistry().registerAll(SoundInit.BOOK_CLOSE);
				event.getRegistry().registerAll(SoundInit.JINGLES);
				event.getRegistry().registerAll(SoundInit.RIP);
				event.getRegistry().registerAll(SoundInit.SUS1);
				event.getRegistry().registerAll(SoundInit.SUS2);
				event.getRegistry().registerAll(SoundInit.SPICY);
				event.getRegistry().registerAll(SoundInit.PHONE_UNLOCK);
				event.getRegistry().registerAll(SoundInit.OK1);
				event.getRegistry().registerAll(SoundInit.OK2);
				event.getRegistry().registerAll(SoundInit.DEATH);
				event.getRegistry().register(SoundInit.RING_1);
				event.getRegistry().register(SoundInit.RING_2);
				event.getRegistry().register(SoundInit.RING_3);
				event.getRegistry().register(SoundInit.RING_4);
				event.getRegistry().register(SoundInit.RING_5);
				event.getRegistry().register(SoundInit.RING_6);
				event.getRegistry().register(SoundInit.RING_7);
				event.getRegistry().register(SoundInit.RING_8);
				event.getRegistry().register(SoundInit.RING_9);
				event.getRegistry().register(SoundInit.RING_10);
				event.getRegistry().register(SoundInit.RING_11);
				event.getRegistry().register(SoundInit.DING_8);
				event.getRegistry().register(SoundInit.DING_9);
				event.getRegistry().register(SoundInit.RING_12);
				event.getRegistry().register(SoundInit.RING_13);
				event.getRegistry().register(SoundInit.RING_14);
				event.getRegistry().register(SoundInit.RING_15);
				event.getRegistry().register(SoundInit.MANHOLE_OPEN);
				event.getRegistry().register(SoundInit.MANHOLE_CLOSE);
				event.getRegistry().register(SoundInit.MANHOLE_BREAK);
				event.getRegistry().register(SoundInit.MANHOLE_PLACE);
				event.getRegistry().register(SoundInit.MANHOLE_STEP);
				event.getRegistry().register(SoundInit.BUSY);
				event.getRegistry().register(SoundInit.WATERPHONE);
				event.getRegistry().register(SoundInit.HEAL);
				event.getRegistry().register(SoundInit.GAVEL);
				event.getRegistry().register(SoundInit.ALERT_TONE);
				event.getRegistry().register(SoundInit.PHONE_OFF);
				event.getRegistry().register(SoundInit.MINEDROID_STARTUP);
				event.getRegistry().register(SoundInit.SPONGE_EQUIP);
				event.getRegistry().register(SoundInit.SPONGE_USE);
				event.getRegistry().register(SoundInit.FART_1);
				event.getRegistry().register(SoundInit.FART_2);
				event.getRegistry().register(SoundInit.FART_3);
				event.getRegistry().register(SoundInit.FART_4);
				event.getRegistry().register(SoundInit.FART_5);
				event.getRegistry().register(SoundInit.COUGH_M);
				event.getRegistry().register(SoundInit.COUGH_F);
				event.getRegistry().register(SoundInit.PHONE_CRASH);
				event.getRegistry().register(SoundInit.PHONE_BATTERY_LOW);
				event.getRegistry().register(SoundInit.PHONE_BOOTUP);
				event.getRegistry().register(SoundInit.BATTERY_LOW);
				event.getRegistry().register(SoundInit.CHISEL);
				event.getRegistry().register(SoundInit.SIT_1);
				event.getRegistry().register(SoundInit.SIT_2);
				event.getRegistry().register(SoundInit.SIT_3);
				event.getRegistry().register(SoundInit.SIT_4);
				event.getRegistry().register(SoundInit.SIT_5);
				event.getRegistry().register(SoundInit.PHONE_UNBOX);
				event.getRegistry().register(SoundInit.RATION_OPEN);
				event.getRegistry().register(SoundInit.TAPE_MEASURE_OPEN);
				event.getRegistry().register(SoundInit.TAPE_MEASURE_CLOSE);

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
