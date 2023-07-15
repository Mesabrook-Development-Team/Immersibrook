package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.net.AboutGUIPacket;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.CommandProcessorPacket;
import com.mesabrook.ib.net.CoughPacket;
import com.mesabrook.ib.net.EngravePacket;
import com.mesabrook.ib.net.FoodBoxPacket;
import com.mesabrook.ib.net.NVTogglePacket;
import com.mesabrook.ib.net.OpenTOSPacket;
import com.mesabrook.ib.net.PoliceEffectsTogglePacket;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.net.SoundRandomizerPacket;
import com.mesabrook.ib.net.VestTogglePacket;
import com.mesabrook.ib.net.WallSignPacket;
import com.mesabrook.ib.net.telecom.AcceptCallPacket;
import com.mesabrook.ib.net.telecom.ActivateChooseNumberPacket;
import com.mesabrook.ib.net.telecom.ActivateNoReceptionPacket;
import com.mesabrook.ib.net.telecom.ActivateNumberChosenPacket;
import com.mesabrook.ib.net.telecom.ActivatePhonePacket;
import com.mesabrook.ib.net.telecom.ActivationCompletePacket;
import com.mesabrook.ib.net.telecom.CallAcceptedPacket;
import com.mesabrook.ib.net.telecom.CallRejectedPacket;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.net.telecom.DeleteContactPacket;
import com.mesabrook.ib.net.telecom.DisconnectCallPacket;
import com.mesabrook.ib.net.telecom.DisconnectedCallNotificationPacket;
import com.mesabrook.ib.net.telecom.FactoryResetPacket;
import com.mesabrook.ib.net.telecom.GetPhoneRecentsPacket;
import com.mesabrook.ib.net.telecom.GetPhoneRecentsResponsePacket;
import com.mesabrook.ib.net.telecom.GetReceptionStrengthPacket;
import com.mesabrook.ib.net.telecom.GetStrengthResponsePacket;
import com.mesabrook.ib.net.telecom.IncomingCallPacket;
import com.mesabrook.ib.net.telecom.InitiateCallPacket;
import com.mesabrook.ib.net.telecom.MergeCallPacket;
import com.mesabrook.ib.net.telecom.OutgoingCallResponsePacket;
import com.mesabrook.ib.net.telecom.PhoneNamePacket;
import com.mesabrook.ib.net.telecom.PhoneQueryPacket;
import com.mesabrook.ib.net.telecom.PhoneQueryResponsePacket;
import com.mesabrook.ib.net.telecom.PhoneRingtonesPacket;
import com.mesabrook.ib.net.telecom.PhoneTossedPacket;
import com.mesabrook.ib.net.telecom.PhoneWallpaperPacket;
import com.mesabrook.ib.net.telecom.RefreshStackPacket;
import com.mesabrook.ib.net.telecom.RejectCallPacket;
import com.mesabrook.ib.net.telecom.SaveContactPacket;
import com.mesabrook.ib.net.telecom.SecurityStrategySelectedPacket;
import com.mesabrook.ib.net.telecom.WirelessEmergencyAlertPacket;
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
		INSTANCE.registerMessage(WallSignPacket.Handler.class, WallSignPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(FoodBoxPacket.Handler.class, FoodBoxPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(ServerSoundBroadcastPacket.Handler.class, ServerSoundBroadcastPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(VestTogglePacket.Handler.class, VestTogglePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(NVTogglePacket.Handler.class, NVTogglePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(SoundRandomizerPacket.Handler.class, SoundRandomizerPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PoliceEffectsTogglePacket.Handler.class, PoliceEffectsTogglePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AboutGUIPacket.class, AboutGUIPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(CommandProcessorPacket.class, CommandProcessorPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(ClientSoundPacket.Handler.class, ClientSoundPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(CoughPacket.Handler.class, CoughPacket.class, nextID(), Side.CLIENT);

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
		INSTANCE.registerMessage(PhoneNamePacket.Handler.class, PhoneNamePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(CustomizationPacket.Handler.class, CustomizationPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(FactoryResetPacket.Handler.class, FactoryResetPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(SaveContactPacket.Handler.class, SaveContactPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(DeleteContactPacket.Handler.class, DeleteContactPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(GetPhoneRecentsPacket.Handler.class, GetPhoneRecentsPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(GetPhoneRecentsResponsePacket.Handler.class, GetPhoneRecentsResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(WirelessEmergencyAlertPacket.Handler.class, WirelessEmergencyAlertPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(OpenTOSPacket.Handler.class, OpenTOSPacket.class, nextID(), Side.CLIENT);
	}

	private static int nextID()
	{
		return ++id;
	}

}
