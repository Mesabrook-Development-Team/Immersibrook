package com.mesabrook.ib.util.handlers;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableCollection;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.blocks.BlockRegister;
import com.mesabrook.ib.blocks.ImmersiblockRotationalManyBB;
import com.mesabrook.ib.blocks.gui.GuiAboutImmersibrook;
import com.mesabrook.ib.blocks.gui.sco.GuiPOSIdentifierSetup;
import com.mesabrook.ib.blocks.gui.sco.GuiPOSWaitingForNetwork;
import com.mesabrook.ib.blocks.gui.sco.GuiStoreMode;
import com.mesabrook.ib.blocks.gui.telecom.GuiCallEnd;
import com.mesabrook.ib.blocks.gui.telecom.GuiHome;
import com.mesabrook.ib.blocks.gui.telecom.GuiIncomingCall;
import com.mesabrook.ib.blocks.gui.telecom.GuiLockScreen;
import com.mesabrook.ib.blocks.gui.telecom.GuiMobileAlert;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneActivate;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneActivate.ActivationScreens;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneBase;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneCall;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneCalling;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneConnected;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneRecents;
import com.mesabrook.ib.blocks.gui.telecom.SignalStrengths;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.init.SoundInit;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.net.telecom.PhoneQueryResponsePacket;
import com.mesabrook.ib.net.telecom.PhoneQueryResponsePacket.ResponseTypes;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager.WirelessEmergencyAlert;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.saveData.PhoneLogData;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@SideOnly(Side.CLIENT)
@EventBusSubscriber
public class ClientSideHandlers
{
	private static HashMap<Long, PositionedSoundRecord> soundsByBlockPos = new HashMap<>();

	public static void openAboutGUI()
	{
		GuiAboutImmersibrook gui = new GuiAboutImmersibrook();
		Minecraft.getMinecraft().displayGuiScreen(gui);
	}

	public static void playSoundHandler(ServerSoundBroadcastPacket message, MessageContext ctx)
	{
		if(!message.rapidSounds)
		{
			SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();

			// Clear our cache of playing sounds
			// Since we do this each time the packet is received, this
			// shouldn't take a whole lot of extra processing time
			HashSet<Long> keysToRemove = new HashSet<>();
			for(Entry<Long, PositionedSoundRecord> kvp : soundsByBlockPos.entrySet())
			{
				if (!soundHandler.isSoundPlaying(kvp.getValue()))
				{
					keysToRemove.add(kvp.getKey());
				}
			}

			for(long key : keysToRemove)
			{
				soundsByBlockPos.remove(key);
			}

			PositionedSoundRecord existingSoundAtPosition = soundsByBlockPos.get(message.pos.toLong());
			if (existingSoundAtPosition != null)
			{
				return;
			}

			EntityPlayer player = Minecraft.getMinecraft().player;
			WorldClient world = Minecraft.getMinecraft().world;

			ResourceLocation soundLocation = new ResourceLocation(message.modID, message.soundName);
			IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
			SoundEvent sound = soundRegistry.getValue(soundLocation);

			if (sound == null)
			{
				Main.logger.warn(String.format("Tried to play sound %s but it does not exist!", message.modID + ":" + message.soundName));
				return;
			}

			PositionedSoundRecord record = new PositionedSoundRecord(sound, SoundCategory.BLOCKS, message.volume, message.pitch, message.pos);

			soundsByBlockPos.put(message.pos.toLong(), record);

			Minecraft.getMinecraft().getSoundHandler().playSound(record);
		}
		else
		{
			SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
			ResourceLocation soundLocation = new ResourceLocation(message.modID, message.soundName);
			IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
			SoundEvent sound = soundRegistry.getValue(soundLocation);

			if (sound == null)
			{
				Main.logger.warn(String.format("Tried to play sound %s but it does not exist!", message.modID + ":" + message.soundName));
				return;
			}

			PositionedSoundRecord record = new PositionedSoundRecord(sound, SoundCategory.BLOCKS, message.volume, message.pitch, message.pos);
			Minecraft.getMinecraft().getSoundHandler().playSound(record);
		}
	}

	// This little piece of shit is what actually loads the super duper poggy woggy creative menu for Immersibrook.
	public static void loadCreativeGUI()
	{
		MinecraftForge.EVENT_BUS.register(new CreativeGuiDrawHandler());
	}

	public static class TelecomClientHandlers
	{
		public static void onNoReception()
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneActivate)
			{
				GuiPhoneActivate activate = (GuiPhoneActivate)Minecraft.getMinecraft().currentScreen;
				activate.setMessage("Failed to activate: out of range");
				activate.setActivationScreen(ActivationScreens.Message);
			}
		}

		public static void onChooseNumber(int[] options, boolean isResend)
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneActivate)
			{
				GuiPhoneActivate activate = (GuiPhoneActivate)Minecraft.getMinecraft().currentScreen;
				activate.setSelectablePhoneNumbers(options, isResend);
				activate.setMessage("");
				activate.setActivationScreen(ActivationScreens.ChooseNumber);
			}
		}

		public static void onActivationComplete(EnumHand hand)
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneActivate)
			{
				GuiPhoneActivate activateScreen = (GuiPhoneActivate)Minecraft.getMinecraft().currentScreen;
				activateScreen.goToMainScreen();
			}
		}

		public static void onReceptionReceived(double reception)
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneBase)
			{
				GuiPhoneBase phone = (GuiPhoneBase)Minecraft.getMinecraft().currentScreen;
				phone.setSignalStrength(SignalStrengths.getForReceptionAmount(reception));
			}
		}

		private static HashMap<String, ISound> incomingCallSoundsByPhone = new HashMap<>();
		private static Object incomingSoundLock = new Object();
		private static void playIncomingCallSound(String phoneNumber)
		{
			EntityPlayerSP player = Minecraft.getMinecraft().player;
			int ringTone = -1;
			for(int i = 0; i < player.inventory.getSizeInventory(); i++)
			{
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (!(stack.getItem() instanceof ItemPhone))
				{
					continue;
				}

				NBTTagCompound tag = stack.getTagCompound();
				ItemPhone.NBTData stackData = new ItemPhone.NBTData();
				stackData.deserializeNBT(tag);

				String stackPhoneNumber = stackData.getPhoneNumberString();
				if (!phoneNumber.equalsIgnoreCase(stackPhoneNumber))
				{
					continue;
				}

				ringTone = stackData.getRingTone();
				break;
			}

			if (ringTone == -1)
			{
				return;
			}

			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
			synchronized(incomingSoundLock)
			{
				ISound incomingCallSound = incomingCallSoundsByPhone.get(phoneNumber);
				if (incomingCallSound != null && handler.isSoundPlaying(incomingCallSound))
				{
					return;
				}

				ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, "ring_" + ringTone);
				IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
				SoundEvent sound = soundRegistry.getValue(soundLocation);

				incomingCallSound = new PositionedSoundRecord(sound, SoundCategory.MASTER, ModConfig.ringtoneVolume, 1F, player.getPosition());
				incomingCallSoundsByPhone.put(phoneNumber, incomingCallSound);
				handler.playSound(incomingCallSound);

				if(Minecraft.getMinecraft().gameSettings.showSubtitles || ModConfig.showCallMsgInChat)
				{
					TextComponentString pn = new TextComponentString(GuiPhoneBase.getFormattedPhoneNumber(phoneNumber));
					pn.getStyle().setBold(true);
					pn.getStyle().setItalic(true);
					pn.getStyle().setColor(TextFormatting.GRAY);

					player.sendStatusMessage(new TextComponentString(new TextComponentTranslation("im.access.call").getFormattedText()), true);
				}
			}
		}

		public static void onIncomingCall(String fromNumber, String toNumber)
		{
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayer player = mc.player;

			if(player.world.provider.getDimension() == 0)
			{
				playIncomingCallSound(toNumber);

				if (mc.currentScreen instanceof GuiPhoneBase)
				{
					GuiPhoneBase screen = (GuiPhoneBase)mc.currentScreen;
					if (!screen.getCurrentPhoneNumber().equals(toNumber))
					{
						return;
					}

					GuiIncomingCall incoming = new GuiIncomingCall(screen.getPhoneStack(), screen.getHand(), fromNumber);
					mc.displayGuiScreen(incoming);
				}
			}
		}

		private static HashMap<String, ISound> outgoingCallsByPhone = new HashMap<>();
		private static Object outgoingSoundLock = new Object();
		private static void playOutgoingCallSound(String phoneNumber)
		{
			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
			synchronized(outgoingSoundLock)
			{
				ISound outgoingCall = outgoingCallsByPhone.get(phoneNumber);
				if (outgoingCall != null && handler.isSoundPlaying(outgoingCall))
				{
					return;
				}

				outgoingCall = new PositionedSoundRecord(SoundInit.OUTGOING_CALL.getSoundName(), SoundCategory.MASTER, 0.25F, 1F, true, 0, AttenuationType.NONE, 0, 0, 0);
				outgoingCallsByPhone.put(phoneNumber, outgoingCall);
				handler.playSound(outgoingCall);
			}
		}

		public static void onOutgoingCallConnected(String fromNumber)
		{
			playOutgoingCallSound(fromNumber);
		}

		public static void onOutgoingCallNoSuchNumber(String fromNumber, String toNumber)
		{
//			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
//			ISound sit = PositionedSoundRecord.getMasterRecord(SoundInit.SIT_2, 1F);
//			handler.playSound(sit);

			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneCalling)
			{
				GuiPhoneCalling calling = (GuiPhoneCalling)Minecraft.getMinecraft().currentScreen;
				if (!calling.getCurrentPhoneNumber().equals(fromNumber))
				{
					return;
				}

				SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
				ISound sit = PositionedSoundRecord.getMasterRecord(SoundInit.SIT_2, 1F);
				handler.playSound(sit);

				GuiCallEnd end = new GuiCallEnd(calling.getPhoneStack(), calling.getHand(), toNumber);
				end.setMessage("No such number!");
				Minecraft.getMinecraft().displayGuiScreen(end);
			}
		}

		public static void onCallNoReception(String fromNumber, String toNumber)
		{
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneCalling)
			{
				GuiPhoneCalling calling = (GuiPhoneCalling)Minecraft.getMinecraft().currentScreen;
				if (!calling.getCurrentPhoneNumber().equals(fromNumber))
				{
					return;
				}

				GuiCallEnd end = new GuiCallEnd(calling.getPhoneStack(), calling.getHand(), toNumber);
				end.setMessage("No reception!");
				Minecraft.getMinecraft().displayGuiScreen(end);
			}
		}

		public static void onCallBusy(String fromNumber, String toNumber)
		{
			SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
			ISound busy = PositionedSoundRecord.getMasterRecord(SoundInit.BUSY, 1F);
			handler.playSound(busy);

			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneCalling)
			{
				GuiPhoneCalling calling = (GuiPhoneCalling)Minecraft.getMinecraft().currentScreen;
				if (!calling.getCurrentPhoneNumber().equals(fromNumber))
				{
					return;
				}

				GuiCallEnd end = new GuiCallEnd(calling.getPhoneStack(), calling.getHand(), toNumber);
				end.setMessage("Recipient Unavailable");
				Minecraft.getMinecraft().displayGuiScreen(end);
			}
		}

		public static HashMap<Integer, Consumer<PhoneQueryResponsePacket>> phoneQueryResponseHandlers = new HashMap<>();
		public static void onPhoneQueryResponsePacket(PhoneQueryResponsePacket packet)
		{
			if (packet.responseType == ResponseTypes.callConnecting)
			{
				playOutgoingCallSound(packet.forNumber);
			}
			else if (packet.responseType == ResponseTypes.callIncoming)
			{
				playIncomingCallSound(packet.forNumber);
			}

			Minecraft mc = Minecraft.getMinecraft();
			if (!(mc.currentScreen instanceof GuiPhoneBase))
			{
				return;
			}

			GuiPhoneBase currentGui = (GuiPhoneBase)mc.currentScreen;
			String currentPhone = currentGui.getCurrentPhoneNumber();
			if (!currentPhone.equals(packet.forNumber))
			{
				return;
			}

			if (packet.responseType == ResponseTypes.idle)
			{
				mc.displayGuiScreen(new GuiLockScreen(currentGui.getPhoneStack(), currentGui.getHand()));
			}
			else if (packet.responseType == ResponseTypes.callConnecting)
			{
				mc.displayGuiScreen(new GuiPhoneCalling(currentGui.getPhoneStack(), currentGui.getHand(), packet.otherNumber));
			}
			else if (packet.responseType == ResponseTypes.callIncoming)
			{
				mc.displayGuiScreen(new GuiIncomingCall(currentGui.getPhoneStack(), currentGui.getHand(), packet.otherNumber));
			}
			else if (packet.responseType == ResponseTypes.callConnected)
			{
				mc.displayGuiScreen(new GuiPhoneConnected(currentGui.getPhoneStack(), currentGui.getHand(), packet.otherNumber, packet.isConferenceSubCall, packet.isMergeable));
			}
		}

		public static int getNextHandlerID()
		{
			if (TelecomClientHandlers.phoneQueryResponseHandlers.isEmpty())
			{
				return 1;
			}

			Optional<Integer> maxID = TelecomClientHandlers.phoneQueryResponseHandlers.keySet().stream().max(Integer::compare);
			int nextID = 1;
			if (maxID.isPresent())
			{
				nextID = maxID.get() + 1;
			}

			return nextID;
		}

		public static void onCallDisconnected(String forNumber, String toNumber)
		{
			Minecraft mc = Minecraft.getMinecraft();
			World world = mc.player.world;

			ISound incomingCallSound = incomingCallSoundsByPhone.get(forNumber);
			if (incomingCallSound != null && mc.getSoundHandler().isSoundPlaying(incomingCallSound))
			{
				mc.getSoundHandler().stopSound(incomingCallSound);
				incomingCallSoundsByPhone.remove(forNumber);
			}

			ISound outgoingCall = outgoingCallsByPhone.get(forNumber);
			if (outgoingCall != null && mc.getSoundHandler().isSoundPlaying(outgoingCall))
			{
				mc.getSoundHandler().stopSound(outgoingCall);
				outgoingCallsByPhone.remove(forNumber);
			}

			PositionedSoundRecord startSound = PositionedSoundRecord.getMasterRecord(SoundInit.ENDCALL, 1F);
			mc.getSoundHandler().playSound(startSound);

			if (mc.currentScreen instanceof GuiIncomingCall)
			{
				GuiIncomingCall incomingScreen = (GuiIncomingCall)mc.currentScreen;
				if (incomingScreen.getCurrentPhoneNumber().equals(forNumber))
				{
					if (GuiPhoneBase.isPhoneUnlocked)
					{
						mc.displayGuiScreen(new GuiHome(incomingScreen.getPhoneStack(), incomingScreen.getHand()));
					}
					else
					{
						mc.displayGuiScreen(new GuiLockScreen(incomingScreen.getPhoneStack(), incomingScreen.getHand()));
					}
				}
			}
			else if (mc.currentScreen instanceof GuiPhoneCalling || mc.currentScreen instanceof GuiPhoneConnected)
			{
				GuiPhoneBase callingScreen = (GuiPhoneBase)mc.currentScreen;
				if (!callingScreen.getCurrentPhoneNumber().equals(forNumber))
				{
					return;
				}

				mc.displayGuiScreen(new GuiCallEnd(callingScreen.getPhoneStack(), callingScreen.getHand(), toNumber));
			}

			if(world.provider.getDimension() == 0)
			{
				mc.player.sendMessage(new TextComponentString("Phone call with " + GuiPhoneBase.getFormattedPhoneNumber(toNumber) + " has been disconnected"));
			}
			else
			{
				mc.player.sendMessage(new TextComponentString("Call Failed - Outside Service Dimension"));
			}
		}

		public static HashMap<String, LocalDateTime> callStartsByPhone = new HashMap<>();
		public static void onCallConnected(String forNumber, String toNumber, boolean isConferenceSubCall, boolean isMergeable)
		{
			Minecraft mc = Minecraft.getMinecraft();

			callStartsByPhone.put(forNumber, LocalDateTime.now());

			ISound incomingCallSound = incomingCallSoundsByPhone.get(forNumber);
			if (incomingCallSound != null && mc.getSoundHandler().isSoundPlaying(incomingCallSound))
			{
				mc.getSoundHandler().stopSound(incomingCallSound);
				incomingCallSoundsByPhone.remove(forNumber);
			}

			ISound outgoingCall = outgoingCallsByPhone.get(forNumber);
			if (outgoingCall != null && mc.getSoundHandler().isSoundPlaying(outgoingCall))
			{
				mc.getSoundHandler().stopSound(outgoingCall);
				outgoingCallsByPhone.remove(forNumber);
			}

			mc.player.sendMessage(new TextComponentString("You are now connected to " + GuiPhoneBase.getFormattedPhoneNumber(toNumber)));

			if (mc.currentScreen instanceof GuiPhoneCalling || mc.currentScreen instanceof GuiIncomingCall)
			{
				GuiPhoneBase phoneBase = (GuiPhoneBase)mc.currentScreen;
				if (!phoneBase.getCurrentPhoneNumber().equals(forNumber))
				{
					return;
				}

				GuiPhoneConnected connected = new GuiPhoneConnected(phoneBase.getPhoneStack(), phoneBase.getHand(), toNumber, isConferenceSubCall, isMergeable);
				mc.displayGuiScreen(connected);
			}
		}

		public static void onCallRejected(String forNumber, String toNumber)
		{
			Minecraft mc = Minecraft.getMinecraft();

			ISound incomingCallSound = incomingCallSoundsByPhone.get(toNumber);
			if (incomingCallSound != null && mc.getSoundHandler().isSoundPlaying(incomingCallSound))
			{
				mc.getSoundHandler().stopSound(incomingCallSound);
				incomingCallSoundsByPhone.remove(toNumber);
			}

			ISound outgoingCall = outgoingCallsByPhone.get(forNumber);
			if (outgoingCall != null && mc.getSoundHandler().isSoundPlaying(outgoingCall))
			{
				mc.getSoundHandler().stopSound(outgoingCall);
				outgoingCallsByPhone.remove(forNumber);
			}

			boolean displayRejectedMessage = true;
			if (mc.currentScreen instanceof GuiIncomingCall)
			{
				GuiIncomingCall incomingScreen = (GuiIncomingCall)mc.currentScreen;
				if (incomingScreen.getCurrentPhoneNumber().equals(toNumber))
				{
					if (GuiPhoneBase.isPhoneUnlocked)
					{
						mc.displayGuiScreen(new GuiHome(incomingScreen.getPhoneStack(), incomingScreen.getHand()));
					}
					else
					{
						mc.displayGuiScreen(new GuiLockScreen(incomingScreen.getPhoneStack(), incomingScreen.getHand()));
					}
					displayRejectedMessage = false;
				}
			}
			else if (mc.currentScreen instanceof GuiPhoneCalling || mc.currentScreen instanceof GuiPhoneConnected)
			{
				PositionedSoundRecord startSound = PositionedSoundRecord.getMasterRecord(SoundInit.ENDCALL, 1F);
				mc.getSoundHandler().playSound(startSound);

				GuiPhoneBase callingScreen = (GuiPhoneBase)mc.currentScreen;
				if (callingScreen.getCurrentPhoneNumber().equals(forNumber))
				{
					mc.displayGuiScreen(new GuiCallEnd(callingScreen.getPhoneStack(), callingScreen.getHand(), toNumber));
					displayRejectedMessage = false;
				}
			}

			if (displayRejectedMessage)
			{
				mc.player.sendMessage(new TextComponentString("Phone call attempt with " + GuiPhoneBase.getFormattedPhoneNumber(toNumber) + " was rejected"));
			}
		}

		public static void onPhoneToss(String number)
		{
			Minecraft mc = Minecraft.getMinecraft();

			ISound sound = incomingCallSoundsByPhone.get(number);
			if (sound != null)
			{
				mc.getSoundHandler().stopSound(sound);
			}

			sound = outgoingCallsByPhone.get(number);
			if (sound != null)
			{
				mc.getSoundHandler().stopSound(sound);
			}

			callStartsByPhone.remove(number);
		}

		public static void onPhoneQueryResponseForPhoneApp(PhoneQueryResponsePacket packet)
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (!(mc.currentScreen instanceof GuiPhoneBase))
			{
				return;
			}

			GuiPhoneBase currentScreen = (GuiPhoneBase)mc.currentScreen;
			if (!currentScreen.getCurrentPhoneNumber().equals(packet.forNumber))
			{
				return;
			}

			GuiPhoneBase newScreen = null;
			if (packet.responseType == ResponseTypes.idle)
			{
				if (GuiPhoneBase.isPhoneUnlocked)
				{
					newScreen = new GuiPhoneCall(currentScreen.getPhoneStack(), currentScreen.getHand());
				}
				else
				{
					newScreen = new GuiLockScreen(currentScreen.getPhoneStack(), currentScreen.getHand());
				}
			}
			else if (packet.responseType == ResponseTypes.callConnecting)
			{
				newScreen = new GuiPhoneCalling(currentScreen.getPhoneStack(), currentScreen.getHand(), packet.otherNumber);
			}
			else if (packet.responseType == ResponseTypes.callConnected)
			{
				newScreen = new GuiPhoneConnected(currentScreen.getPhoneStack(), currentScreen.getHand(), packet.otherNumber, packet.isConferenceSubCall, packet.isMergeable);
			}
			else if (packet.responseType == ResponseTypes.callIncoming)
			{
				newScreen = new GuiIncomingCall(currentScreen.getPhoneStack(), currentScreen.getHand(), packet.otherNumber);
			}

			if (newScreen != null)
			{
				mc.displayGuiScreen(newScreen);
			}
		}
		
		public static void refreshStack(String currentGuiClassName, ItemStack newStack, EnumHand hand)
		{
			refreshStack(currentGuiClassName, currentGuiClassName, newStack, hand);
		}

		public static void refreshStack(String currentGuiClassName, String nextGuiClassName, ItemStack newStack, EnumHand hand)
		{
			if (Minecraft.getMinecraft().currentScreen == null || !Minecraft.getMinecraft().currentScreen.getClass().getName().equals(currentGuiClassName))
			{
				return;
			}

			GuiScreen gui;
			try
			{
				Class<?> guiClass = Class.forName(nextGuiClassName);
				gui = (GuiScreen)guiClass.getConstructor(ItemStack.class, EnumHand.class).newInstance(newStack, hand);
			}
			catch(Exception ex) { return; }

			Minecraft.getMinecraft().displayGuiScreen(gui);
		}

		public static void onRecentsRetrieved(String forNumber, List<PhoneLogData.LogData> logDatum)
		{
			GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
			if (currentScreen == null || !(currentScreen instanceof GuiPhoneRecents))
			{
				return;
			}

			GuiPhoneRecents recents = (GuiPhoneRecents)currentScreen;
			if (!recents.getCurrentPhoneNumber().equalsIgnoreCase(forNumber))
			{
				return;
			}

			recents.setLogDatum(logDatum);
		}
	
		public static void onWirelessEmergencyAlert(String forNumber, WirelessEmergencyAlert alert)
		{
			GuiMobileAlert.labelsByNumber.put(Integer.parseInt(forNumber), alert.getName());
			GuiMobileAlert.textByNumber.put(Integer.parseInt(forNumber), alert.getDescription());
			
			if (Minecraft.getMinecraft().currentScreen instanceof GuiPhoneBase)
			{
				GuiPhoneBase currentScreen = (GuiPhoneBase)Minecraft.getMinecraft().currentScreen;
				if (!currentScreen.getCurrentPhoneNumber().equals(forNumber))
				{
					return;
				}
				
				Minecraft.getMinecraft().displayGuiScreen(new GuiMobileAlert(currentScreen.getPhoneStack(), currentScreen.getHand()));
			}
		}
	}

	public static void processURIWebRequest(URI uri)
	{
		ModUtils.openWebLink(uri);
	}

	public static class SelfCheckOutHandlers
	{
		public static void onInitializeRegisterResponse(boolean wasSuccessful, String error, BlockPos pos)
		{
			if (!(Minecraft.getMinecraft().currentScreen instanceof GuiPOSIdentifierSetup))
			{
				return;
			}
			
			GuiPOSIdentifierSetup gui = (GuiPOSIdentifierSetup)Minecraft.getMinecraft().currentScreen;
			if (!wasSuccessful)
			{
				gui.displayError(error);
			}
			else
			{
				TileEntityRegister register = (TileEntityRegister)Minecraft.getMinecraft().world.getTileEntity(pos);
				GuiPOSWaitingForNetwork waitingForNetwork = new GuiPOSWaitingForNetwork(register);
				Minecraft.getMinecraft().displayGuiScreen(waitingForNetwork);
			}
		}
	
		public static void onStoreModeGUIResponse(LocationEmployee[] employees)
		{
			GuiScreen screen = Minecraft.getMinecraft().currentScreen;
			if (screen == null || !(screen instanceof GuiStoreMode))
			{
				return;
			}
			
			((GuiStoreMode)screen).setLocationEmployees(employees);
		}
		
		public static void openStoreModeGUI()
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiStoreMode());
		}
		
		public static void updateEmployeeCapaiblity(LocationEmployee locationEmployee)
		{
			IEmployeeCapability capability = Minecraft.getMinecraft().player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			capability.setLocationEmployee(locationEmployee);
		}
	}

	@SubscribeEvent
	public static void drawSelectionBoxEvent(DrawBlockHighlightEvent e)
	{
		RayTraceResult rtr = e.getTarget();
		if (rtr.typeOfHit != Type.BLOCK)
		{
			return;
		}
		
		World world = e.getPlayer().world;
		IBlockState blockState = world.getBlockState(rtr.getBlockPos());		
		if (blockState.getBlock() instanceof ImmersiblockRotationalManyBB)
		{
			e.setCanceled(true);
			drawManyBoundingBoxBlockHighlight(e.getPlayer(), e.getPartialTicks(), rtr.getBlockPos(), blockState);
		}
	}
	
	private static void drawRegisterBlockHighlight(EntityPlayer player, float partialTicks, BlockPos pos)
	{
		final double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		final double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		final double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
		final Vec3d start = player.getPositionEyes(partialTicks);
		final Vec3d eyes = player.getLook(partialTicks);
		final float reach = Minecraft.getMinecraft().playerController.getBlockReachDistance();
		final Vec3d end = start.addVector(eyes.x * reach, eyes.y * reach, eyes.z * reach);
		
		AxisAlignedBB boxToDraw = null;
		double leastDistance = reach + 1;
		
		// Check monitor
		RayTraceResult result = BlockRegister.monitorBoundingBox.offset(pos).calculateIntercept(start, end);
		if (result != null)
		{
			leastDistance = result.hitVec.distanceTo(start);
			boxToDraw = BlockRegister.monitorBoundingBox;
		}
		
		// Check card reader
		result = BlockRegister.cardReaderBoundingBox.offset(pos).calculateIntercept(start, end);
		if (result != null)
		{
			double distance = result.hitVec.distanceTo(start);
			if (distance < leastDistance)
			{
				boxToDraw = BlockRegister.cardReaderBoundingBox;
			}
		}
		
		if (boxToDraw != null)
		{			
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
	        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	        GlStateManager.glLineWidth(2.0F);
	        GlStateManager.disableTexture2D();
	        GlStateManager.depthMask(false);
	        
			RenderGlobal.drawSelectionBoundingBox(boxToDraw.offset(pos).offset(-d3, -d4, -d5), 0.0F, 0.0F, 0.0F, 1F);
			
			GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
		}
	}
	
	private static void drawManyBoundingBoxBlockHighlight(EntityPlayer player, float partialTicks, BlockPos pos, IBlockState blockState)
	{
		EnumFacing facing = blockState.getValue(ImmersiblockRotationalManyBB.FACING);
		ImmutableCollection<AxisAlignedBB> boundingBoxesForFacing = ((ImmersiblockRotationalManyBB)blockState.getBlock()).SUB_BOUNDING_BOXES.get(facing);
		
		final double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		final double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		final double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
		final Vec3d start = player.getPositionEyes(partialTicks);
		final Vec3d eyes = player.getLook(partialTicks);
		final float reach = Minecraft.getMinecraft().playerController.getBlockReachDistance();
		final Vec3d end = start.addVector(eyes.x * reach, eyes.y * reach, eyes.z * reach);
		
		AxisAlignedBB boxToDraw = null;
		double leastDistance = reach + 1;
		
		for(AxisAlignedBB box : boundingBoxesForFacing)
		{
			RayTraceResult result = box.offset(pos).calculateIntercept(start, end);
			if (result != null)
			{
				double distance = result.hitVec.distanceTo(start);
				if (distance < leastDistance)
				{
					boxToDraw = box;
					leastDistance = distance;
				}
			}
		}
		
		if (boxToDraw != null)
		{			
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
	        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	        GlStateManager.glLineWidth(2.0F);
	        GlStateManager.disableTexture2D();
	        GlStateManager.depthMask(false);
	        
			RenderGlobal.drawSelectionBoundingBox(boxToDraw.offset(pos).offset(-d3, -d4, -d5), 0.0F, 0.0F, 0.0F, 1F);
			
			GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
		}
	}
}
