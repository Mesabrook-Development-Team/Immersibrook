package rz.mesabrook.wbtc.util.handlers;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import rz.mesabrook.wbtc.net.EngravePacket;
import rz.mesabrook.wbtc.net.FoodBoxPacket;
import rz.mesabrook.wbtc.net.NVTogglePacket;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.net.SoundRandomizerPacket;
import rz.mesabrook.wbtc.net.VestTogglePacket;
import rz.mesabrook.wbtc.net.telecom.AcceptCallPacket;
import rz.mesabrook.wbtc.net.telecom.ActivateChooseNumberPacket;
import rz.mesabrook.wbtc.net.telecom.ActivateNoReceptionPacket;
import rz.mesabrook.wbtc.net.telecom.ActivateNumberChosenPacket;
import rz.mesabrook.wbtc.net.telecom.ActivatePhonePacket;
import rz.mesabrook.wbtc.net.telecom.ActivationCompletePacket;
import rz.mesabrook.wbtc.net.telecom.CallAcceptedPacket;
import rz.mesabrook.wbtc.net.telecom.CallRejectedPacket;
import rz.mesabrook.wbtc.net.telecom.DisconnectCallPacket;
import rz.mesabrook.wbtc.net.telecom.DisconnectedCallNotificationPacket;
import rz.mesabrook.wbtc.net.telecom.GetReceptionStrengthPacket;
import rz.mesabrook.wbtc.net.telecom.GetStrengthResponsePacket;
import rz.mesabrook.wbtc.net.telecom.IncomingCallPacket;
import rz.mesabrook.wbtc.net.telecom.InitiateCallPacket;
import rz.mesabrook.wbtc.net.telecom.OutgoingCallResponsePacket;
import rz.mesabrook.wbtc.net.telecom.PhoneQueryPacket;
import rz.mesabrook.wbtc.net.telecom.PhoneQueryResponsePacket;
import rz.mesabrook.wbtc.net.telecom.PhoneTossedPacket;
import rz.mesabrook.wbtc.net.telecom.RejectCallPacket;
import rz.mesabrook.wbtc.util.Reference;

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
	}
	
	private static int nextID()
	{
		return ++id;
	}
	
}
