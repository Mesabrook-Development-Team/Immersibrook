package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.net.*;
import com.mesabrook.ib.net.telecom.*;
import com.mesabrook.ib.util.Reference;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler 
{
	public static SimpleNetworkWrapper INSTANCE = null;
	private static int id = 0;
	
	public static void registerMessages()
	{
		INSTANCE = new SimpleNetworkWrapper(Reference.NETWORK_CHANNEL_NAME);
		
		INSTANCE.registerMessage(EngravePacket.Handler.class, EngravePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(FoodBoxPacket.Handler.class, FoodBoxPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PlaySoundPacket.Handler.class, PlaySoundPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(VestTogglePacket.Handler.class, VestTogglePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(NVTogglePacket.Handler.class, NVTogglePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(SoundRandomizerPacket.Handler.class, SoundRandomizerPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PoliceEffectsTogglePacket.Handler.class, PoliceEffectsTogglePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AboutGUIPacket.class, AboutGUIPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(SoundPlayerAppInfoPacket.Handler.class, SoundPlayerAppInfoPacket.class, nextID(), Side.SERVER);

		// TELECOM
		INSTANCE.registerMessage(ActivatePhonePacket.Handler.class, ActivatePhonePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(ActivateNoReceptionPacket.Handler.class, ActivateNoReceptionPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(ActivateChooseNumberPacket.Handler.class, ActivateChooseNumberPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(ActivateNumberChosenPacket.Handler.class, ActivateNumberChosenPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(ActivationCompletePacket.Handler.class, ActivationCompletePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(GetReceptionStrengthPacket.Handler.class, GetReceptionStrengthPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(GetStrengthResponsePacket.Handler.class, GetStrengthResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(IncomingCallPacket.Handler.class, IncomingCallPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(OutgoingCallResponsePacket.Handler.class, OutgoingCallResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(InitiateCallPacket.Handler.class, InitiateCallPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PhoneQueryPacket.Handler.class, PhoneQueryPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PhoneQueryResponsePacket.Handler.class, PhoneQueryResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(DisconnectCallPacket.Handler.class, DisconnectCallPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(DisconnectedCallNotificationPacket.Handler.class, DisconnectedCallNotificationPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(AcceptCallPacket.Handler.class, AcceptCallPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(CallAcceptedPacket.Handler.class, CallAcceptedPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(RejectCallPacket.Handler.class, RejectCallPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(CallRejectedPacket.Handler.class, CallRejectedPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PhoneTossedPacket.Handler.class, PhoneTossedPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(MergeCallPacket.Handler.class, MergeCallPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(SecurityStrategySelectedPacket.Handler.class, SecurityStrategySelectedPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(RefreshStackPacket.Handler.class, RefreshStackPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PhoneWallpaperPacket.Handler.class, PhoneWallpaperPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PhoneRingtonesPacket.Handler.class, PhoneRingtonesPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(FactoryResetPacket.Handler.class, FactoryResetPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(SaveContactPacket.Handler.class, SaveContactPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(DeleteContactPacket.Handler.class, DeleteContactPacket.class, nextID(), Side.SERVER);		
	}
	
	private static int nextID()
	{
		return ++id;
	}
	
}
