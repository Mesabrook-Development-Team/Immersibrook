package com.mesabrook.ib.util;

import java.util.UUID;

public class Reference
{
	public static final String MODID = "wbtc";
	public static final String MODNAME = "Immersibrook";
	public static final String UPDATE_NAME = "The Technology Update";
	public static final String MOTTO = MottoRandomizer.MOTTO;
	public static final String VERSION = "1.0.0.7";
	public static final String MINEDROID_VERSION = "0.0.8";
	public static final String CHANGELOG = "https://github.com/RavenholmZombie/Immersibrook/releases/tag/" + VERSION;
	public static final String UPDATE_URL = "https://raw.githubusercontent.com/RavenholmZombie/RavenholmZombie/main/update.json";
	
	public static final String CLIENT = "com.mesabrook.ib.proxy.ClientProxy";
	public static final String SERVER = "com.mesabrook.ib.proxy.CommonProxy";
	
	public static final int GUI_TRASHBIN = 1;
	public static final int GUI_PLAQUE = 2;
	public static final int GUI_FOODBOX = 3;
	public static final int GUI_PHONE_ACTIVATE = 4;
	public static final int GUI_PHONE = 5;
	public static final int GUI_STAMP_BOOK = 6;
	public static final int GUI_ABOUT = 7;
	public static final int GUI_WALLSIGN = 8;
	
	public static final String NETWORK_CHANNEL_NAME = "wbtc";
	
	public static final String ANTENNA_DATA_NAME = "antennaData";
	public static final String PHONE_NUMBER_DATA_NAME = "phoneNumberData";
	public static final String LOUDNESS_DATA_NAME = "loudnessData";
	public static final String SPECIAL_DROP_TRACKING_DATA_NAME = "ib.specialdrops";
	public static final String PHONE_LOG_DATA_NAME = "ib.phonelog";

	public static final UUID RZ_UUID = UUID.fromString("c2907bdd-9aba-4c20-b83b-ddb41c004e78");
	public static final UUID CSX_UUID = UUID.fromString("717bb4e7-c701-42a6-b06f-bbe17e0518ae");
	public static final UUID TD_UUID = UUID.fromString("743cb50c-59b8-40c1-9145-140c510f6c5c");
	public static final UUID ZOE_UUID = UUID.fromString("6d6aa650-3c92-4492-b0fd-11a2ebb47388");
	public static final UUID MD_UUID = UUID.fromString("ac72ea4d-2843-4e98-a741-7864b350456e");
	public static final UUID SVV_UUID = UUID.fromString("d3df3541-eb57-4ec8-a775-e1493b35a682");
	public static final UUID LW_UUID = UUID.fromString("2f2bc3a6-89e1-481c-a789-b9454d81cb9b");
	public static final UUID SLOOSE_UUID = UUID.fromString("e768c520-83a5-49fc-aa4e-f553cccfc593");

	public static final String PHONE_NUMBER_NBTKEY = "phonenumber";
	public static final String PHONE_CONFERENCE_NAME = "Conference";
	public static final String SECURITY_STRATEGY_NBTKEY = "securitystrategy";
	public static final String SECURITY_PIN_NBTKEY = "pin";
	public static final String SECURITY_UUID_NBTKEY = "securityUUID";
	public static final String HOME_BACKGROUND = "homeBackground";
	public static final String LOCK_BACKGROUND = "lockBackground";
	public static final String CHAT_TONE = "chatTone";
	public static final String RING_TONE = "ringTone";
	public static final String CONTACTS_NBTKEY = "contacts";
	public static final String SHOW_IRL_TIME = "showirltime";
	public static final String SHOW_MILITARY_TIME = "show24hr";

	public static final int MAX_PHONE_BACKGROUNDS = 33; // This is an INCLUSIVE number
	public static final int MAX_CHAT_NOTIFICATIONS = 9; // This is an INCLUSIVE number
	public static final int MAX_RINGTONES = 14; // This is an INCLUSIVE number

	// Birthdays (Day and Month only - NO YEARS)
	public static final int RZ_MONTH = 9;
	public static final int CSX_MONTH = 6;
	public static final int TD_MONTH = 2;
	public static final int TLZ_MONTH = 5;
	public static final int MD_MONTH = 5;
	public static final int SVV_MONTH = 12;
	public static final int BAG_MONTH = 7;
	public static final int BB_MONTH = 10;

	public static final int RZ_DAY = 2;
	public static final int CSX_DAY = 22;
	public static final int TD_DAY = 12;
	public static final int TLZ_DAY = 22;
	public static final int MD_DAY = 18;
	public static final int SVV_DAY = 7;
	public static final int SLOOSE_DAY = 23;
	public static final int BAG_DAY = 4;
	public static final int BB_DAY = 31;
}
