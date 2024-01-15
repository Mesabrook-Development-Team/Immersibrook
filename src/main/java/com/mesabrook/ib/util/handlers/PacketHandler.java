package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.net.AboutGUIPacket;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.ClosedTOSPacket;
import com.mesabrook.ib.net.CommandProcessorPacket;
import com.mesabrook.ib.net.CoughPacket;
import com.mesabrook.ib.net.EngravePacket;
import com.mesabrook.ib.net.FoodBoxPacket;
import com.mesabrook.ib.net.NVTogglePacket;
import com.mesabrook.ib.net.OpenTOSPacket;
import com.mesabrook.ib.net.PoliceEffectsTogglePacket;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.net.SoundEmitterBlockPacket;
import com.mesabrook.ib.net.SoundRandomizerPacket;
import com.mesabrook.ib.net.VestTogglePacket;
import com.mesabrook.ib.net.WallSignPacket;
import com.mesabrook.ib.net.atm.CreateNewDebitCardATMPacket;
import com.mesabrook.ib.net.atm.CreateNewDebitCardATMResponsePacket;
import com.mesabrook.ib.net.atm.DepositATMPacket;
import com.mesabrook.ib.net.atm.DepositATMResponsePacket;
import com.mesabrook.ib.net.atm.FetchATMSettingsPacket;
import com.mesabrook.ib.net.atm.FetchATMSettingsResponsePacket;
import com.mesabrook.ib.net.atm.FetchAccountsPacket;
import com.mesabrook.ib.net.atm.FetchAccountsResponsePacket;
import com.mesabrook.ib.net.atm.UpdateATMSettingsPacket;
import com.mesabrook.ib.net.atm.UpdateATMSettingsResponsePacket;
import com.mesabrook.ib.net.atm.WithdrawATMPacket;
import com.mesabrook.ib.net.atm.WithdrawATMResponsePacket;
import com.mesabrook.ib.net.sco.EmployeeCapServerToClientPacket;
import com.mesabrook.ib.net.sco.POSAddNewFluidMetersAcknowledgePacket;
import com.mesabrook.ib.net.sco.POSAddNewFluidMetersPacket;
import com.mesabrook.ib.net.sco.POSCancelSalePacket;
import com.mesabrook.ib.net.sco.POSCardEjectPacket;
import com.mesabrook.ib.net.sco.POSCardProcessPacket;
import com.mesabrook.ib.net.sco.POSCardShowMessagePacket;
import com.mesabrook.ib.net.sco.POSChangeStatusClientToServerPacket;
import com.mesabrook.ib.net.sco.POSFetchPricePacket;
import com.mesabrook.ib.net.sco.POSFetchPriceResponsePacket;
import com.mesabrook.ib.net.sco.POSGetNearbyFluidMetersPacket;
import com.mesabrook.ib.net.sco.POSGetNearbyFluidMetersResponsePacket;
import com.mesabrook.ib.net.sco.POSGetRegisterFluidMetersPacket;
import com.mesabrook.ib.net.sco.POSGetRegisterFluidMetersResponsePacket;
import com.mesabrook.ib.net.sco.POSInitializeRegisterPacket;
import com.mesabrook.ib.net.sco.POSInitializeRegisterResponsePacket;
import com.mesabrook.ib.net.sco.POSOpenCardReaderGUIPacket;
import com.mesabrook.ib.net.sco.POSOpenRegisterSecurityBoxInventoryGUIPacket;
import com.mesabrook.ib.net.sco.POSRemoveFluidMeterPacket;
import com.mesabrook.ib.net.sco.POSRemoveItemPacket;
import com.mesabrook.ib.net.sco.POSResetFluidMeterCounterPacket;
import com.mesabrook.ib.net.sco.POSScanFluidPacket;
import com.mesabrook.ib.net.sco.POSScanFluidResponsePacket;
import com.mesabrook.ib.net.sco.POSUpdateFluidMeterNamePacket;
import com.mesabrook.ib.net.sco.QueryPricePacket;
import com.mesabrook.ib.net.sco.QueryPriceResponsePacket;
import com.mesabrook.ib.net.sco.StoreModeGuiLocationSelectedPacket;
import com.mesabrook.ib.net.sco.StoreModeGuiOpenedPacket;
import com.mesabrook.ib.net.sco.StoreModeGuiPacket;
import com.mesabrook.ib.net.sco.StoreModeGuiResponse;
import com.mesabrook.ib.net.sco.TaggingStationChangeTabsPacket;
import com.mesabrook.ib.net.sco.TaggingStationDistanceChangedPacket;
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
import com.mesabrook.ib.net.telecom.OOBEStatusPacket;
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
import com.mesabrook.ib.net.telecom.SetSkinFetcherPacket;
import com.mesabrook.ib.net.telecom.ToggleUnlockSliderPacket;
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
		INSTANCE.registerMessage(OpenTOSPacket.Handler.class, OpenTOSPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(ClosedTOSPacket.Handler.class, ClosedTOSPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(SoundEmitterBlockPacket.Handler.class, SoundEmitterBlockPacket.class, nextID(), Side.SERVER);

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
		INSTANCE.registerMessage(ToggleUnlockSliderPacket.Handler.class, ToggleUnlockSliderPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(OOBEStatusPacket.Handler.class, OOBEStatusPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(FactoryResetPacket.Handler.class, FactoryResetPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(SaveContactPacket.Handler.class, SaveContactPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(DeleteContactPacket.Handler.class, DeleteContactPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(GetPhoneRecentsPacket.Handler.class, GetPhoneRecentsPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(GetPhoneRecentsResponsePacket.Handler.class, GetPhoneRecentsResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(WirelessEmergencyAlertPacket.Handler.class, WirelessEmergencyAlertPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(SetSkinFetcherPacket.Handler.class, SetSkinFetcherPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(StoreModeGuiPacket.Handler.class, StoreModeGuiPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(StoreModeGuiOpenedPacket.Handler.class, StoreModeGuiOpenedPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(StoreModeGuiResponse.Handler.class, StoreModeGuiResponse.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(StoreModeGuiLocationSelectedPacket.Handler.class, StoreModeGuiLocationSelectedPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(EmployeeCapServerToClientPacket.Handler.class, EmployeeCapServerToClientPacket.class, nextID(), Side.CLIENT);
		
		// SELFCHECKOUT
		INSTANCE.registerMessage(POSInitializeRegisterPacket.Handler.class, POSInitializeRegisterPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSInitializeRegisterResponsePacket.Handler.class, POSInitializeRegisterResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(POSCancelSalePacket.Handler.class, POSCancelSalePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSChangeStatusClientToServerPacket.Handler.class, POSChangeStatusClientToServerPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSRemoveItemPacket.Handler.class, POSRemoveItemPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSFetchPricePacket.Handler.class, POSFetchPricePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSFetchPriceResponsePacket.Handler.class, POSFetchPriceResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(POSOpenCardReaderGUIPacket.Handler.class, POSOpenCardReaderGUIPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(POSCardEjectPacket.Handler.class, POSCardEjectPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSCardShowMessagePacket.Handler.class, POSCardShowMessagePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(POSCardProcessPacket.Handler.class, POSCardProcessPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSOpenRegisterSecurityBoxInventoryGUIPacket.Handler.class, POSOpenRegisterSecurityBoxInventoryGUIPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(TaggingStationDistanceChangedPacket.Handler.class, TaggingStationDistanceChangedPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(TaggingStationChangeTabsPacket.Handler.class, TaggingStationChangeTabsPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSGetNearbyFluidMetersPacket.Handler.class, POSGetNearbyFluidMetersPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSGetNearbyFluidMetersResponsePacket.Handler.class, POSGetNearbyFluidMetersResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(POSAddNewFluidMetersPacket.Handler.class, POSAddNewFluidMetersPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSAddNewFluidMetersAcknowledgePacket.Handler.class, POSAddNewFluidMetersAcknowledgePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(POSUpdateFluidMeterNamePacket.Handler.class, POSUpdateFluidMeterNamePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSRemoveFluidMeterPacket.Handler.class, POSRemoveFluidMeterPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSResetFluidMeterCounterPacket.Handler.class, POSResetFluidMeterCounterPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSGetRegisterFluidMetersPacket.Handler.class, POSGetRegisterFluidMetersPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSGetRegisterFluidMetersResponsePacket.Handler.class, POSGetRegisterFluidMetersResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(POSScanFluidPacket.Handler.class, POSScanFluidPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(POSScanFluidResponsePacket.Handler.class, POSScanFluidResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(QueryPricePacket.Handler.class, QueryPricePacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(QueryPriceResponsePacket.Handler.class, QueryPriceResponsePacket.class, nextID(), Side.CLIENT);
		
		// ATM
		INSTANCE.registerMessage(FetchAccountsPacket.Handler.class, FetchAccountsPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(FetchAccountsResponsePacket.Handler.class, FetchAccountsResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(FetchATMSettingsPacket.Handler.class, FetchATMSettingsPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(FetchATMSettingsResponsePacket.Handler.class, FetchATMSettingsResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(UpdateATMSettingsPacket.Handler.class, UpdateATMSettingsPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(UpdateATMSettingsResponsePacket.Handler.class, UpdateATMSettingsResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(WithdrawATMPacket.Handler.class, WithdrawATMPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(WithdrawATMResponsePacket.Handler.class, WithdrawATMResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(DepositATMPacket.Handler.class, DepositATMPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(DepositATMResponsePacket.Handler.class, DepositATMResponsePacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(CreateNewDebitCardATMPacket.Handler.class, CreateNewDebitCardATMPacket.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(CreateNewDebitCardATMResponsePacket.Handler.class, CreateNewDebitCardATMResponsePacket.class, nextID(), Side.CLIENT);
	}

	private static int nextID()
	{
		return ++id;
	}

}
