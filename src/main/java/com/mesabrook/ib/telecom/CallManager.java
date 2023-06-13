package com.mesabrook.ib.telecom;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.PrimitiveIterator.OfInt;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.net.telecom.CallAcceptedPacket;
import com.mesabrook.ib.net.telecom.CallRejectedPacket;
import com.mesabrook.ib.net.telecom.DisconnectedCallNotificationPacket;
import com.mesabrook.ib.net.telecom.IncomingCallPacket;
import com.mesabrook.ib.net.telecom.OutgoingCallResponsePacket;
import com.mesabrook.ib.net.telecom.OutgoingCallResponsePacket.States;
import com.mesabrook.ib.net.telecom.PhoneQueryResponsePacket;
import com.mesabrook.ib.net.telecom.PhoneQueryResponsePacket.ResponseTypes;
import com.mesabrook.ib.util.PhoneLogState;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mesabrook.ib.util.saveData.AntennaData;
import com.mesabrook.ib.util.saveData.PhoneLogData;
import com.mesabrook.ib.util.saveData.PhoneLogData.LogData;
import com.mesabrook.ib.util.saveData.PhoneNumberData;

import net.minecraft.block.BlockDirt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class CallManager {

	private static CallManager instance = null;
	private static Field textComponentStringTextField;
	private static Field textComponentTranslationChildrenField;
	private static Method textComponentTranslationEnsureInitializedMethod;

	public static CallManager instance() {
		if (instance == null) {
			instance = new CallManager();
		}

		return instance;
	}

	private HashMap<UUID, Call> callsByID = new HashMap<>();
	private HashMap<String, Call> callsByPhone = new HashMap<>();
	private ArrayList<UUID> callDequeue = new ArrayList<>();
	private Object callMapLock = new Object();

	private CallManager() {

	}

	public void tick() {
		ArrayList<UUID> postDequeueList = new ArrayList<>();
		synchronized (callMapLock) {
			for (Call call : callsByID.values()) {
				call.tick();
			}

			if (callDequeue.size() > 0) {
				World logDataWorld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
				PhoneLogData phoneLogData = PhoneLogData.getOrCreate(logDataWorld);
				Calendar currentTime = Calendar.getInstance();

				for (UUID dequeueCall : callDequeue) {
					Call call = callsByID.get(dequeueCall);

					if (call != null) {
						if (!call.getSkipDisconnectNotification()) {
							Tuple<EntityPlayerMP, ItemStack> originOwner = call.getOriginOwner();
							if (originOwner != null) {
								DisconnectedCallNotificationPacket notify = new DisconnectedCallNotificationPacket();
								notify.forNumber = call.getOriginPhone();
								notify.toNumber = call.getDestPhones().size() > 1
										? com.mesabrook.ib.util.Reference.PHONE_CONFERENCE_NAME
										: call.getDestPhones().get(0);
								PacketHandler.INSTANCE.sendTo(notify, originOwner.getFirst());

								LogData logData = phoneLogData
										.getLogByID(call.logDataIDsByNumber.get(call.getOriginPhone()));
								logData.setCallLength((currentTime.getTimeInMillis() - logData.getCallTimeCalendar().getTimeInMillis()) / 1000);
							}

							ArrayList<Tuple<EntityPlayerMP, ItemStack>> destOwners = call.getDestOwners();
							for (Tuple<EntityPlayerMP, ItemStack> destOwner : destOwners) {
								ItemPhone.NBTData nbtData = new ItemPhone.NBTData();
								nbtData.deserializeNBT(destOwner.getSecond().getTagCompound());

								DisconnectedCallNotificationPacket notify = new DisconnectedCallNotificationPacket();
								notify.forNumber = nbtData.getPhoneNumberString();
								notify.toNumber = call.getDestPhones().size() > 1
										? com.mesabrook.ib.util.Reference.PHONE_CONFERENCE_NAME
										: call.getOriginPhone();
								PacketHandler.INSTANCE.sendTo(notify, destOwner.getFirst());

								LogData logData = phoneLogData
										.getLogByID(call.logDataIDsByNumber.get(nbtData.getPhoneNumberString()));
								logData.setCallLength((currentTime.getTimeInMillis() - logData.getCallTimeCalendar().getTimeInMillis()) / 1000);
							}

							for (Call subCall : call.conferenceSubCalls) {
								postDequeueList.add(subCall.getID());
							}
						}

						callsByID.remove(dequeueCall);
						while (callsByPhone.values().remove(call)) {
						}
					}
				}
			}
		}

		callDequeue.clear();
		callDequeue.addAll(postDequeueList);
	}

	public void dequeueCall(UUID id) {
		callDequeue.add(id);
	}

	public void enqueueCall(Call call) {
		callsByID.put(call.getID(), call);
		callsByPhone.put(call.getOriginPhone(), call);

		for (String destPhone : call.getDestPhones()) {
			callsByPhone.put(destPhone, call);
		}
	}

	public Call getCall(String phoneNumber) {
		return callsByPhone.get(phoneNumber);
	}

	public class Call {
		private UUID id;
		private CallPhases callPhase;
		private String originPhone;
		private LinkedHashSet<String> destPhones = new LinkedHashSet<>();
		private int connectingTicks;
		private boolean skipDisconnectNotification;
		private Call parentCall = null;
		ArrayList<Call> conferenceSubCalls = new ArrayList<>();
		HashMap<String, UUID> logDataIDsByNumber = new HashMap<>();

		public Call(String originPhone, String destPhone) {
			id = UUID.randomUUID();
			callPhase = CallPhases.Connecting;
			this.originPhone = originPhone;
			this.destPhones.add(destPhone);

			World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
			PhoneLogData phoneLogData = PhoneLogData.getOrCreate(world);

			int intOriginPhone = 0;
			int intDestPhone = 0;
			try {
				intOriginPhone = Integer.parseInt(originPhone);
				intDestPhone = Integer.parseInt(destPhone);
			} catch (Exception ex) {
			}

			if (intOriginPhone != 0 && intDestPhone != 0) {
				LogData originData = phoneLogData.addLog(intOriginPhone, intDestPhone, Calendar.getInstance(), 0,
						PhoneLogState.Outgoing);
				logDataIDsByNumber.put(originPhone, originData.getLogID());

				LogData destData = phoneLogData.addLog(intDestPhone, intOriginPhone, Calendar.getInstance(), 0,
						PhoneLogState.Missed);
				logDataIDsByNumber.put(destPhone, destData.getLogID());
			}
		}

		public UUID getID() {
			return id;
		}

		public CallPhases getCallPhase() {
			return callPhase;
		}

		public String getOriginPhone() {
			return originPhone;
		}

		public ImmutableList<String> getDestPhones() {
			return ImmutableList.copyOf(destPhones);
		}

		public int getConnectingTicks() {
			return connectingTicks;
		}

		public boolean getSkipDisconnectNotification() {
			return skipDisconnectNotification;
		}

		public void addConferenceSubCall(Call call) {
			conferenceSubCalls.add(call);
			call.parentCall = this;
			callsByID.put(call.getID(), call);
			callsByPhone.put(new ArrayList<String>(call.destPhones).get(0), call);
		}

		public boolean doesConferenceSubCallsContainOrigin(String originNumber) {
			return conferenceSubCalls.stream().anyMatch(c -> c.getOriginPhone().equals(originNumber));
		}

		public boolean containsNumber(String number) {
			return (getOriginPhone().equals(number) || getDestPhones().stream().anyMatch(n -> n.equals(number)))
					&& !conferenceSubCalls.stream().anyMatch(c -> c.containsNumber(number));
		}

		public void tick() {
			if (getCallPhase() == CallPhases.Connecting) {
				if (connectingTicks == 0) // Brand new call
				{
					World logDataWorld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
					PhoneLogData phoneLogData = PhoneLogData.getOrCreate(logDataWorld);

					Tuple<EntityPlayerMP, ItemStack> originPhone = getOriginOwner();

					// Check to see if the destination number is valid
					World world = DimensionManager.getWorld(0);
					PhoneNumberData phoneNumbers = PhoneNumberData.getOrCreate(world);
					int destNumber = Integer.parseInt(getDestPhones().get(0));

					OutgoingCallResponsePacket outgoingPacket = new OutgoingCallResponsePacket();
					outgoingPacket.fromNumber = getOriginPhone();
					outgoingPacket.toNumber = getDestPhones().get(0);
					if (!phoneNumbers.doesNumberExist(destNumber)) {
						if (originPhone != null) // Notify user phone does not exist, only if user is holding phone
						{
							outgoingPacket.state = States.noSuchNumber;
							PacketHandler.INSTANCE.sendTo(outgoingPacket, originPhone.getFirst());

							LogData logData = phoneLogData.getLogByID(logDataIDsByNumber.get(getOriginPhone()));
							logData.setPhoneLogState(PhoneLogState.Failed);
						}

						skipDisconnectNotification = true;

						if (parentCall != null) {
							parentCall.conferenceSubCalls.remove(this);
						}
						dequeueCall(getID());
						return;
					}

					if (originPhone != null) // Notify call is going through, only if user is holding phone
					{
						outgoingPacket.state = States.success;
						PacketHandler.INSTANCE.sendTo(outgoingPacket, originPhone.getFirst());
					}

					// Find destination phone in player's inventory and tell player to ring
					ArrayList<Tuple<EntityPlayerMP, ItemStack>> destOwners = getDestOwners();
					Tuple<EntityPlayerMP, ItemStack> destPhone = destOwners.size() > 0 ? destOwners.get(0) : null;
					if (destPhone != null) {
						IncomingCallPacket incoming = new IncomingCallPacket();
						incoming.fromNumber = getOriginPhone();
						incoming.toNumber = getDestPhones().get(0);
						PacketHandler.INSTANCE.sendTo(incoming, destPhone.getFirst());
					}
				}

				connectingTicks++;

				if (connectingTicks > ModConfig.phoneRingTicks) {
					World logDataWorld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
					PhoneLogData phoneLogData = PhoneLogData.getOrCreate(logDataWorld);

					if (parentCall != null) {
						parentCall.conferenceSubCalls.remove(this);
					}

					LogData logData = phoneLogData.getLogByID(logDataIDsByNumber.get(getDestPhones().get(0)));
					logData.setPhoneLogState(PhoneLogState.Missed);

					dequeueCall(getID());
					return;
				}
			}
		}

		public Tuple<EntityPlayerMP, ItemStack> getOriginOwner() {
			for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
					.getPlayers()) {
				AntennaData antennaData = AntennaData.getOrCreate(player.world);
				if (antennaData.getBestReception(player.getPosition()) <= 0.0) {
					return null;
				}

				for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
					ItemStack stack = player.inventory.getStackInSlot(i);
					if (!(stack.getItem() instanceof ItemPhone)) {
						continue;
					}

					NBTTagCompound tag = stack.getTagCompound();
					ItemPhone.NBTData stackData = new ItemPhone.NBTData();
					stackData.deserializeNBT(tag);

					int phoneNumber = stackData.getPhoneNumber();

					if (phoneNumber == 0) {
						continue;
					}

					if (getOriginPhone().equals(Integer.toString(phoneNumber))) {
						return new Tuple<>(player, stack);
					}
				}
			}

			return null;
		}

		public ArrayList<Tuple<EntityPlayerMP, ItemStack>> getDestOwners() {
			ArrayList<Tuple<EntityPlayerMP, ItemStack>> results = new ArrayList<>();
			for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
					.getPlayers()) {
				AntennaData antennaData = AntennaData.getOrCreate(player.world);
				if (antennaData.getBestReception(player.getPosition()) <= 0.0) {
					continue;
				}

				for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
					ItemStack stack = player.inventory.getStackInSlot(i);
					if (!(stack.getItem() instanceof ItemPhone)) {
						continue;
					}

					NBTTagCompound tag = stack.getTagCompound();
					ItemPhone.NBTData stackData = new ItemPhone.NBTData();
					stackData.deserializeNBT(tag);

					int phoneNumber = stackData.getPhoneNumber();

					if (phoneNumber == 0) {
						continue;
					}

					if (getDestPhones().stream().anyMatch(p -> p.equals(Integer.toString(phoneNumber)))) {
						results.add(new Tuple<>(player, stack));
					}
				}
			}

			return results;
		}

		public Tuple<EntityPlayerMP, ItemStack> getDestOwner(String dest) {
			ArrayList<Tuple<EntityPlayerMP, ItemStack>> destOwners = getDestOwners();
			Optional<Tuple<EntityPlayerMP, ItemStack>> phoneStack = destOwners.stream().filter(t -> {
				NBTData data = NBTData.getFromItemStack(t.getSecond());
				if (data == null) {
					return false;
				}

				return data.getPhoneNumberString().equalsIgnoreCase(dest);
			}).findFirst();

			if (phoneStack.isPresent()) {
				return phoneStack.get();
			}

			return null;
		}

		public void accept() {
			if (getCallPhase() != CallPhases.Connecting) {
				return;
			}

			callPhase = CallPhases.Connected;

			Tuple<EntityPlayerMP, ItemStack> origin = getOriginOwner();
			if (origin != null) {
				CallAcceptedPacket accepted = new CallAcceptedPacket();
				accepted.fromNumber = getOriginPhone();
				accepted.toNumber = getDestPhones().get(0);
				accepted.isConferenceSubCall = parentCall != null;
				accepted.isMergeable = parentCall != null;
				PacketHandler.INSTANCE.sendTo(accepted, origin.getFirst());
			}

			ArrayList<Tuple<EntityPlayerMP, ItemStack>> destOwners = getDestOwners();
			Tuple<EntityPlayerMP, ItemStack> dest = destOwners.size() > 0 ? destOwners.get(0) : null;
			if (dest != null) {
				CallAcceptedPacket accepted = new CallAcceptedPacket();
				accepted.fromNumber = getDestPhones().get(0);
				accepted.toNumber = getOriginPhone();
				accepted.isConferenceSubCall = false;
				accepted.isMergeable = false;
				PacketHandler.INSTANCE.sendTo(accepted, dest.getFirst());

				World logDataWorld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
				PhoneLogData phoneLogData = PhoneLogData.getOrCreate(logDataWorld);
				LogData logData = phoneLogData.getLogByID(logDataIDsByNumber.get(getDestPhones().get(0)));
				logData.setPhoneLogState(PhoneLogState.Answered);
			}
		}

		public void reject(String caller) {
			Tuple<EntityPlayerMP, ItemStack> origin = getOriginOwner();

			if (doesConferenceSubCallsContainOrigin(caller)) {
				Call subCall = conferenceSubCalls.stream().filter(c -> c.getOriginPhone().equals(caller)).findFirst()
						.get();
				subCall.reject(caller);
				return;
			}

			ArrayList<Tuple<EntityPlayerMP, ItemStack>> destOwners = getDestOwners();
			Tuple<EntityPlayerMP, ItemStack> dest = destOwners.size() > 0 ? destOwners.get(0) : null;

			CallRejectedPacket rejected = new CallRejectedPacket();
			rejected.fromNumber = getOriginPhone();
			rejected.toNumber = getDestPhones().get(0);

			if (origin != null) {
				PacketHandler.INSTANCE.sendTo(rejected, origin.getFirst());
			}

			if (dest != null) {
				PacketHandler.INSTANCE.sendTo(rejected, dest.getFirst());

				NBTData destData = NBTData.getFromItemStack(dest.getSecond());

				World logDataWorld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
				PhoneLogData phoneLogData = PhoneLogData.getOrCreate(logDataWorld);
				LogData logData = phoneLogData.getLogByID(logDataIDsByNumber.get(destData.getPhoneNumberString()));
				logData.setPhoneLogState(PhoneLogState.Ignored);
			}

			skipDisconnectNotification = true;

			if (parentCall != null) {
				parentCall.conferenceSubCalls.remove(this);
			}

			dequeueCall(getID());
		}

		public void onPlayerChat(EntityPlayerMP player, ITextComponent text) {
			if (getCallPhase() != CallPhases.Connected) {
				return;
			}

			AntennaData antenna = AntennaData.getOrCreate(player.world);
			double playerReception = antenna.getBestReception(player.getPosition());
			if (playerReception <= 0.0) {
				return;
			}

			ArrayList<Tuple<EntityPlayerMP, ItemStack>> phones = getDestOwners();
			phones.add(getOriginOwner());
			for (Tuple<EntityPlayerMP, ItemStack> phone : phones) {
				if (phone == null)
				{
					continue;
				}
				
				ItemPhone.NBTData nbtData = new ItemPhone.NBTData();
				nbtData.deserializeNBT(phone.getSecond().getTagCompound());
				
				if (phone.getFirst() == player) {
					int drainage = 1;
					if (playerReception < 1.0)
					{
						drainage += (int)((1.0 - playerReception) * 10);
					}
					nbtData.setBatteryLevel(nbtData.getBatteryLevel() - drainage);
					if (nbtData.getBatteryLevel() <= 0)
					{
						nbtData.setBatteryLevel(0);
					}
					phone.getSecond().getTagCompound().merge(nbtData.serializeNBT());
					
					continue;
				}

				if (doesConferenceSubCallsContainOrigin(nbtData.getPhoneNumberString())) {
					continue;
				}

				double effectiveReception = playerReception;
				double receiverReception = antenna.getBestReception(phone.getFirst().getPosition());
				int drainage = 1;
				if (receiverReception < 1.0)
				{
					drainage += (int)((1.0 - receiverReception) * 10);
				}
				
				if (receiverReception < effectiveReception) {
					effectiveReception = receiverReception;
				}

				EntityPlayerMP playerToSendTo = phone.getFirst();
				ITextComponent textToSend = getScrambledText(text, effectiveReception);
				playerToSendTo.sendMessage(textToSend);
				nbtData.setBatteryLevel(nbtData.getBatteryLevel() - drainage);
				if (nbtData.getBatteryLevel() <= 0)
				{
					nbtData.setBatteryLevel(0);
					disconnectDest(nbtData.getPhoneNumberString());
				}
				phone.getSecond().getTagCompound().merge(nbtData.serializeNBT());

				// Play chat notification sound
				ServerSoundBroadcastPacket playSound = new ServerSoundBroadcastPacket();
				playSound.pos = playerToSendTo.getPosition();
				playSound.volume = 1;
				playSound.pitch = 1;
				playSound.soundName = "ding_" + nbtData.getChatTone();
				PacketHandler.INSTANCE.sendToAllAround(playSound, new TargetPoint(playerToSendTo.dimension,
						playerToSendTo.posX, playerToSendTo.posY, playerToSendTo.posZ, 25));
			}
		}
		
		private String getTextComponentStringsForScramble(ITextComponent parent, ArrayList<Tuple<TextComponentString, Integer>> textPartIndexes, ArrayList<TextComponentString> textParts)
		{
			String scramblableText = "";
			boolean isFirst = true;
			
			ArrayList<ITextComponent> components = new ArrayList<>();
			fillComponentList(parent, components);
			
			for(ITextComponent component : components)
			{
				char[] componentChars = component.getUnformattedComponentText().toCharArray();
				for(int i = 0; i < componentChars.length; i++)
				{
					char character = componentChars[i];
					if (character == ' ')
					{
						continue;
					}
					
					scramblableText += character;
					textPartIndexes.add(new Tuple<>((TextComponentString)component, i));
				}
				
				if (componentChars.length > 0)
				{
					textParts.add((TextComponentString)component);
				}
			}
			return scramblableText;
		}
		
		private void fillComponentList(ITextComponent parent, ArrayList<ITextComponent> components)
		{
			if (parent instanceof TextComponentTranslation)
			{
				try
				{
					textComponentTranslationEnsureInitializedMethod.invoke(parent, new Object[0]);
					@SuppressWarnings("unchecked")
					List<ITextComponent> translationComponents = (List<ITextComponent>)textComponentTranslationChildrenField.get(parent);
					for(ITextComponent component : translationComponents)
					{
						components.add(component);
						fillComponentList(component, components);
					}
				}
				catch(Exception ex)
				{
					Main.logger.error("Failed to handle TextComponentTranslation", ex);
				}
			}
			
			for(ITextComponent component : parent.getSiblings())
			{
				components.add(component);
				fillComponentList(component, components);
			}
		}
		
		private boolean doReflection()
		{
			boolean isObfuscated = !java.util.Arrays.stream(BlockDirt.class.getMethods()).anyMatch(m -> m.getName() == "getStateFromMeta");
			if (textComponentStringTextField == null)
			{
				try
				{
					textComponentStringTextField = TextComponentString.class.getDeclaredField(isObfuscated ? "field_150267_b" : "text");
					textComponentStringTextField.setAccessible(true);
					
					Field modifiersField = Field.class.getDeclaredField("modifiers");
					modifiersField.setAccessible(true);
					modifiersField.setInt(textComponentStringTextField, textComponentStringTextField.getModifiers() & ~Modifier.FINAL);
				}
				catch(Exception ex)
				{
					textComponentStringTextField = null;
					Main.logger.error("An error occurred while trying to modify text field for chat scrambling.", ex);
					return false;
				}
			}
			
			if (textComponentTranslationChildrenField == null)
			{
				try
				{
					textComponentTranslationChildrenField = TextComponentTranslation.class.getDeclaredField(isObfuscated ? "field_150278_b" : "children");
					textComponentTranslationChildrenField.setAccessible(true);
				}
				catch(Exception ex)
				{
					textComponentTranslationChildrenField = null;
					Main.logger.error("An error occurred while trying to modify children field for chat scrambling.", ex);
					return false;
				}
			}
			
			if (textComponentTranslationEnsureInitializedMethod == null)
			{
				try
				{
					textComponentTranslationEnsureInitializedMethod = TextComponentTranslation.class.getDeclaredMethod(isObfuscated ? "func_150270_g" : "ensureInitialized", new Class<?>[0]);
					textComponentTranslationEnsureInitializedMethod.setAccessible(true);
				}
				catch(Exception ex)
				{
					textComponentTranslationEnsureInitializedMethod = null;
					Main.logger.error("An error occurred while trying to modify ensureInitialized method for chat scrambling.", ex);
					return false;
				}
			}
			
			return true;
		}
		
		private ITextComponent getScrambledText(ITextComponent text, double reception) {
			if (!doReflection())
			{
				return text;
			}
			
			ITextComponent textCopy = text.createCopy();
			
			// Figure out what is eligible to be scrambled
			ArrayList<Tuple<TextComponentString, Integer>> textPartIndexes = new ArrayList<>();
			ArrayList<TextComponentString> textParts = new ArrayList<>();
			String scramblableText = getTextComponentStringsForScramble(textCopy, textPartIndexes, textParts);
			
			int scrambleAmount = (int)(textPartIndexes.size() * (1.0 - reception));
			if (scrambleAmount > 0) // Nothing to scramble
			{			
				// Get random indexes
				Random rand = new Random();
				IntStream ints = rand.ints(0, scramblableText.length());
				OfInt iterator = ints.iterator();
				ArrayList<Integer> indexesToScramble = new ArrayList<>();
				while(indexesToScramble.size() < scrambleAmount)
				{
					indexesToScramble.add(iterator.next());
				}
				
				ints.close();
				
				// Modify respective text components based on the indexes to scramble
				for(int indexToScramble : indexesToScramble)
				{
					Tuple<TextComponentString, Integer> componentLocalIndex = textPartIndexes.get(indexToScramble);
					String currentText = componentLocalIndex.getFirst().getUnformattedComponentText();
					String newText = "";
					char[] textChars = currentText.toCharArray();
					for(int i = 0; i < textChars.length; i++)
					{
						if (i == componentLocalIndex.getSecond())
						{
							newText += ModConfig.scrambleCharacter;
						}
						else
						{
							newText += textChars[i];
						}
					}
					
					// Known issue: Parent styles still have their click event in effect
					componentLocalIndex.getFirst().getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ""));
					componentLocalIndex.getFirst().getStyle().setHoverEvent(null);
					
					try
					{
						textComponentStringTextField.set(componentLocalIndex.getFirst(), newText);
					}
					catch(Exception ex)
					{
						Main.logger.error("Failed to set text for chat during scramble");
					}
				}
			}
			
			// Prepend text and return
			TextComponentString retVal = new TextComponentString("[Call]");
			Style style = new Style();
			style.setColor(TextFormatting.GREEN);
			style.setBold(true);
			retVal.setStyle(style);
			
			Style oldStyle = textCopy.getStyle().createDeepCopy();
			if (oldStyle.getColor() == null)
			{
				oldStyle.setColor(TextFormatting.WHITE);
			}
			retVal.appendSibling(textCopy);
			textCopy.setStyle(oldStyle);
			return retVal;
		}

		public void merge(String mergingNumber) {
			Optional<Call> optSubCall = conferenceSubCalls.stream()
					.filter(c -> c.getOriginPhone().equals(mergingNumber)).findFirst();
			if (!optSubCall.isPresent()) {
				return;
			}

			Call subCall = optSubCall.get();
			destPhones.addAll(subCall.getDestPhones());
			conferenceSubCalls.addAll(subCall.conferenceSubCalls);
			conferenceSubCalls.remove(subCall);
			callsByID.values().remove(subCall);

			while (callsByPhone.values().remove(subCall)) {
			}

			for (String destPhone : subCall.getDestPhones()) {
				callsByPhone.put(destPhone, this);
			}
			
			World logDataWorld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
			PhoneLogData phoneLogData = PhoneLogData.getOrCreate(logDataWorld);
			for (Entry<String, UUID> logDataByID : logDataIDsByNumber.entrySet()) // Add subcall destPhones to our originPhone/destPhone's logs
			{
				LogData logData = phoneLogData.getLogByID(logDataByID.getValue());
				if (logData != null)
				{
					for(String strDestNumber : subCall.getDestPhones())
					{
						int destNumber = Integer.parseInt(strDestNumber);
						int[] otherNumbers = Arrays.copyOf(logData.getOtherNumbers(), logData.getOtherNumbers().length + 1);
						otherNumbers[otherNumbers.length - 1] = destNumber;
						logData.setOtherNumbers(otherNumbers);
					}
				}
			}
			
			for(Entry<String, UUID> logDataByID : subCall.logDataIDsByNumber.entrySet()) // Add all of our numbers to the subCall's destinations
			{
				LogData logData = phoneLogData.getLogByID(logDataByID.getValue());
				if (logData != null)
				{
					for(String strDestNumber : getDestPhones())
					{
						if (strDestNumber.equalsIgnoreCase(subCall.getOriginPhone()))
						{
							continue;
						}
						
						int destNumber = Integer.parseInt(strDestNumber);
						int[] otherNumbers = Arrays.copyOf(logData.getOtherNumbers(), logData.getOtherNumbers().length + 1);
						otherNumbers[otherNumbers.length - 1] = destNumber;
						logData.setOtherNumbers(otherNumbers);
					}
					
					if (!getOriginPhone().equalsIgnoreCase(subCall.getOriginPhone()))
					{
						int originNumber = Integer.parseInt(getOriginPhone());
						int[] otherNumbers = Arrays.copyOf(logData.getOtherNumbers(), logData.getOtherNumbers().length + 1);
						otherNumbers[otherNumbers.length - 1] = originNumber;
						logData.setOtherNumbers(otherNumbers);
					}
				}
				
				if (!logDataByID.getKey().equalsIgnoreCase(getOriginPhone()))
				{
					logDataIDsByNumber.put(logDataByID.getKey(), logDataByID.getValue());
				}
			}
			
			phoneLogData.removeLogByID(subCall.logDataIDsByNumber.get(subCall.getOriginPhone())); // We need to delete this otherwise it'll show up twice in the origin's history)

			PhoneQueryResponsePacket responsePacket = new PhoneQueryResponsePacket();

			Tuple<EntityPlayerMP, ItemStack> origin = getOriginOwner();
			if (origin != null) {
				origin.getFirst().sendMessage(new TextComponentString("Call merged"));

				Tuple<ResponseTypes, String> response = phoneQuery(origin.getFirst(), getOriginPhone());
				responsePacket.forNumber = getOriginPhone();
				responsePacket.responseType = response.getFirst();
				responsePacket.otherNumber = Reference.PHONE_CONFERENCE_NAME;
				responsePacket.clientHandlerCode = PhoneQueryResponsePacket.PHONE_APP_CLIENT_HANDLER;

				PacketHandler.INSTANCE.sendTo(responsePacket, origin.getFirst());
			}

			for (Tuple<EntityPlayerMP, ItemStack> dest : getDestOwners()) {
				dest.getFirst().sendMessage(new TextComponentString(
						"You are now connected to " + getFormattedPhoneNumber(getOriginPhone())));

				ItemPhone.NBTData nbtData = new ItemPhone.NBTData();
				nbtData.deserializeNBT(dest.getSecond().getTagCompound());

				Tuple<ResponseTypes, String> response = phoneQuery(dest.getFirst(), nbtData.getPhoneNumberString());
				responsePacket.forNumber = nbtData.getPhoneNumberString();
				responsePacket.responseType = response.getFirst();
				responsePacket.otherNumber = Reference.PHONE_CONFERENCE_NAME;
				responsePacket.clientHandlerCode = PhoneQueryResponsePacket.PHONE_APP_CLIENT_HANDLER;

				PacketHandler.INSTANCE.sendTo(responsePacket, dest.getFirst());
			}
		}

		public void disconnectDest(String dest) {
			if (getOriginPhone().equals(dest)) {
				dequeueCall(getID());
				return;
			}

			if (!getDestPhones().contains(dest)) {
				return;
			}

			if (destPhones.size() <= 1) {
				dequeueCall(getID());
			} else {
				ArrayList<Tuple<EntityPlayerMP, ItemStack>> phones = getDestOwners();
				phones.add(getOriginOwner());

				for (Tuple<EntityPlayerMP, ItemStack> phone : phones) {
					ItemPhone.NBTData data = new ItemPhone.NBTData();
					data.deserializeNBT(phone.getSecond().getTagCompound());

					if (data.getPhoneNumberString().equals(dest)) {
						DisconnectedCallNotificationPacket notification = new DisconnectedCallNotificationPacket();
						notification.forNumber = dest;
						notification.toNumber = destPhones.size() > 1 ? Reference.PHONE_CONFERENCE_NAME : dest;
						PacketHandler.INSTANCE.sendTo(notification, phone.getFirst());

						World logDataWorld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
						PhoneLogData phoneLogData = PhoneLogData.getOrCreate(logDataWorld);
						LogData logData = phoneLogData.getLogByID(logDataIDsByNumber.get(data.getPhoneNumberString()));
						Calendar currentTime = Calendar.getInstance();
						logData.setCallLength((currentTime.getTimeInMillis() - logData.getCallTimeCalendar().getTimeInMillis()) / 1000);
					} else {
						phone.getFirst().sendMessage(
								new TextComponentString(getFormattedPhoneNumber(dest) + " has left the conference."));
					}
				}

				destPhones.remove(dest);
				callsByPhone.remove(dest);
			}
		}
	}

	public void onPlayerChat(EntityPlayerMP player, ITextComponent text) {
		ArrayList<String> playerNumbers = new ArrayList<>();
		ArrayList<String> deadPhoneNumbers = new ArrayList<>();
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (!(stack.getItem() instanceof ItemPhone)) {
				continue;
			}

			ItemPhone.NBTData data = new ItemPhone.NBTData();
			data.deserializeNBT(stack.getTagCompound());

			String phoneNumber = data.getPhoneNumberString();
			if (phoneNumber == null) {
				continue;
			}

			if (data.getBatteryLevel() > 0)
			{
				playerNumbers.add(phoneNumber);
			}
			else
			{
				deadPhoneNumbers.add(phoneNumber);
			}
		}

		for (Call call : callsByID.values()) {
			String phoneNumber = null;
			String deadPhoneNumber = null;
			for (String playerNumber : playerNumbers) {
				if (call.containsNumber(playerNumber)) {
					phoneNumber = playerNumber;
					break;
				}
			}
			
			for (String playerNumber : deadPhoneNumbers) {
				if (call.containsNumber(playerNumber)) {
					deadPhoneNumber = playerNumber;
					break;
				}
			}

			if (phoneNumber != null) {
				call.onPlayerChat(player, text);
			}
			else if (deadPhoneNumber != null)
			{
				call.disconnectDest(deadPhoneNumber);
			}
		}
	}

	public Tuple<ResponseTypes, String> phoneQuery(EntityPlayerMP player, String forNumber) {
		World world = player.world;

		AntennaData antennaData = AntennaData.getOrCreate(world);
		if (world.provider.getDimension() != 0 || antennaData.getBestReception(player.getPosition()) <= 0.0) {
			// Too far away
			return new Tuple<>(ResponseTypes.idle, "");
		}

		CallManager.Call call = getCall(forNumber);

		if (call == null) // No calls for number
		{
			return new Tuple<>(ResponseTypes.idle, "");
		} else // A call exists for number...
		{
			return phoneQueryForCall(call, forNumber);
		}
	}

	private Tuple<ResponseTypes, String> phoneQueryForCall(Call call, String forNumber) {
		if (call.doesConferenceSubCallsContainOrigin(forNumber)) {
			return phoneQueryForCall(call.conferenceSubCalls.stream().filter(c -> c.getOriginPhone().equals(forNumber))
					.findFirst().get(), forNumber);
		}

		if (call.getDestPhones().stream().anyMatch(p -> p.equals(forNumber))) // It is being called
		{
			if (call.getCallPhase() == CallPhases.Connecting) {
				return new Tuple<>(ResponseTypes.callIncoming,
						call.getDestPhones().size() > 1 ? com.mesabrook.ib.util.Reference.PHONE_CONFERENCE_NAME
								: call.getOriginPhone());
			} else {
				return new Tuple<>(ResponseTypes.callConnected,
						call.getDestPhones().size() > 1 ? com.mesabrook.ib.util.Reference.PHONE_CONFERENCE_NAME
								: call.getOriginPhone());
			}
		} else {
			if (call.getCallPhase() == CallPhases.Connecting) // It is trying to call another number
			{
				return new Tuple<>(ResponseTypes.callConnecting,
						call.getDestPhones().size() > 1 ? com.mesabrook.ib.util.Reference.PHONE_CONFERENCE_NAME
								: call.getDestPhones().get(0));
			} else if (call.getCallPhase() == CallPhases.Connected) // It is already connected
			{
				return new Tuple<>(ResponseTypes.callConnected,
						call.getDestPhones().size() > 1 ? com.mesabrook.ib.util.Reference.PHONE_CONFERENCE_NAME
								: call.getDestPhones().get(0));
			}
		}

		return new Tuple<>(ResponseTypes.idle, "");
	}

	public void sendQueryResponseForAllCalls(EntityPlayerMP player) {
		for (CallManager.Call call : callsByID.values()) {
			Tuple<EntityPlayerMP, ItemStack> origin = call.getOriginOwner();
			ArrayList<Tuple<EntityPlayerMP, ItemStack>> dests = call.getDestOwners();

			if (origin != null && origin.getFirst() == player) {
				ItemPhone.NBTData originData = new ItemPhone.NBTData();
				originData.deserializeNBT(origin.getSecond().getTagCompound());

				Tuple<ResponseTypes, String> response = phoneQuery(player, originData.getPhoneNumberString());

				PhoneQueryResponsePacket responsePacket = new PhoneQueryResponsePacket();
				responsePacket.forNumber = originData.getPhoneNumberString();
				responsePacket.responseType = response.getFirst();
				responsePacket.otherNumber = response.getSecond();
				responsePacket.clientHandlerCode = PhoneQueryResponsePacket.PHONE_START_CLIENT_HANDLER;
				responsePacket.isConferenceSubCall = call.parentCall != null
						&& call.doesConferenceSubCallsContainOrigin(originData.getPhoneNumberString());
				responsePacket.isMergeable = responsePacket.isConferenceSubCall;
				PacketHandler.INSTANCE.sendTo(responsePacket, player);
			}

			for (Tuple<EntityPlayerMP, ItemStack> dest : dests) {
				if (dest.getFirst() != player) {
					continue;
				}

				ItemPhone.NBTData destData = new ItemPhone.NBTData();
				destData.deserializeNBT(dest.getSecond().getTagCompound());

				Tuple<ResponseTypes, String> response = phoneQuery(player, destData.getPhoneNumberString());

				PhoneQueryResponsePacket responsePacket = new PhoneQueryResponsePacket();
				responsePacket.forNumber = destData.getPhoneNumberString();
				responsePacket.responseType = response.getFirst();
				responsePacket.otherNumber = response.getSecond();
				responsePacket.clientHandlerCode = PhoneQueryResponsePacket.PHONE_START_CLIENT_HANDLER;
				PacketHandler.INSTANCE.sendTo(responsePacket, player);
			}
		}
	}

	private static String getFormattedPhoneNumber(String number) {

		String formattedNumber = number;
		if (formattedNumber.length() >= 3) {
			formattedNumber = formattedNumber.substring(0, 3) + "-"
					+ formattedNumber.substring(3, formattedNumber.length());
		}
		return formattedNumber;
	}

	public void onServerStop() {

		callsByID.clear();
		callsByPhone.clear();
		callDequeue.clear();
	}
}
