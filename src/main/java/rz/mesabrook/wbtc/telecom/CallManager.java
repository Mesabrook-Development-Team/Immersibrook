package rz.mesabrook.wbtc.telecom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.PrimitiveIterator.OfInt;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import rz.mesabrook.wbtc.items.misc.ItemPhone;
import rz.mesabrook.wbtc.net.telecom.CallAcceptedPacket;
import rz.mesabrook.wbtc.net.telecom.CallRejectedPacket;
import rz.mesabrook.wbtc.net.telecom.DisconnectedCallNotificationPacket;
import rz.mesabrook.wbtc.net.telecom.IncomingCallPacket;
import rz.mesabrook.wbtc.net.telecom.OutgoingCallResponsePacket;
import rz.mesabrook.wbtc.net.telecom.OutgoingCallResponsePacket.States;
import rz.mesabrook.wbtc.net.telecom.PhoneQueryResponsePacket;
import rz.mesabrook.wbtc.net.telecom.PhoneQueryResponsePacket.ResponseTypes;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.config.ModConfig;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;
import rz.mesabrook.wbtc.util.saveData.AntennaData;
import rz.mesabrook.wbtc.util.saveData.PhoneNumberData;

public class CallManager {
	
	private static CallManager instance = null;
	public static CallManager instance()
	{
		if (instance == null)
		{
			instance = new CallManager();
		}
		
		return instance;
	}
	
	private HashMap<UUID, Call> callsByID = new HashMap<>();
	private HashMap<String, Call> callsByPhone = new HashMap<>();
	private ArrayList<UUID> callDequeue = new ArrayList<>();
	private Object callMapLock = new Object();
	
	private CallManager()
	{
		
	}
	
	public void tick()
	{
		synchronized (callMapLock) {
			for(Call call : callsByID.values())
			{
				call.tick();
			}
			
			for(UUID dequeueCall : callDequeue)
			{
				Call call = callsByID.get(dequeueCall);
				
				if (call != null)
				{
					if (!call.getSkipDisconnectNotification())
					{
						Tuple<EntityPlayerMP, ItemStack> originOwner = call.getOriginOwner();
						if (originOwner != null)
						{
							DisconnectedCallNotificationPacket notify = new DisconnectedCallNotificationPacket();
							notify.forNumber = call.getOriginPhone();
							notify.toNumber = call.getDestPhones().size() > 1 ? rz.mesabrook.wbtc.util.Reference.PHONE_CONFERENCE_NAME : call.getDestPhones().get(0);
							PacketHandler.INSTANCE.sendTo(notify, originOwner.getFirst());
						}
						
						ArrayList<Tuple<EntityPlayerMP, ItemStack>> destOwners = call.getDestOwners();
						for(Tuple<EntityPlayerMP, ItemStack> destOwner : destOwners)
						{
							ItemPhone.NBTData nbtData = new ItemPhone.NBTData();
							nbtData.deserializeNBT(destOwner.getSecond().getTagCompound());
							
							DisconnectedCallNotificationPacket notify = new DisconnectedCallNotificationPacket();
							notify.forNumber = nbtData.getPhoneNumberString();
							notify.toNumber = call.getDestPhones().size() > 1 ? rz.mesabrook.wbtc.util.Reference.PHONE_CONFERENCE_NAME : call.getOriginPhone();
							PacketHandler.INSTANCE.sendTo(notify, destOwner.getFirst());
						}
					}
					
					callsByID.remove(dequeueCall);
					while (callsByPhone.values().remove(call)) { }
				}
			}
		}
		
		callDequeue.clear();
	}
	
	public void dequeueCall(UUID id)
	{
		callDequeue.add(id);
	}
	
	public void enqueueCall(Call call)
	{
		callsByID.put(call.getID(), call);
		callsByPhone.put(call.getOriginPhone(), call);
		
		for(String destPhone : call.getDestPhones())
		{
			callsByPhone.put(destPhone, call);
		}
	}
	
	public Call getCall(String phoneNumber)
	{
		return callsByPhone.get(phoneNumber);
	}
	
	public class Call
	{
		private UUID id;
		private CallPhases callPhase;
		private String originPhone;
		private LinkedHashSet<String> destPhones = new LinkedHashSet<>();
		private int connectingTicks;
		private boolean skipDisconnectNotification;
		private Call parentCall = null;
		ArrayList<Call> conferenceSubCalls = new ArrayList<>();
		
		public Call(String originPhone, String destPhone)
		{
			id = UUID.randomUUID();
			callPhase = CallPhases.Connecting;
			this.originPhone = originPhone;
			this.destPhones.add(destPhone);
		}
		
		public UUID getID()
		{
			return id;
		}
		
		public CallPhases getCallPhase()
		{
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

		public void addConferenceSubCall(Call call)
		{
			conferenceSubCalls.add(call);
			call.parentCall = this;
			callsByID.put(call.getID(), call);
			callsByPhone.put(new ArrayList<String>(call.destPhones).get(0), call);
		}
		
		public boolean doesConferenceSubCallsContainOrigin(String originNumber)
		{
			return conferenceSubCalls.stream().anyMatch(c -> c.getOriginPhone().equals(originNumber));
		}
		
		public void tick()
		{
			if (getCallPhase() == CallPhases.Connecting)
			{
				if (connectingTicks == 0) // Brand new call
				{
					Tuple<EntityPlayerMP, ItemStack> originPhone = getOriginOwner();
					
					// Check to see if the destination number is valid
					World world = DimensionManager.getWorld(0);
					PhoneNumberData phoneNumbers = PhoneNumberData.getOrCreate(world);
					int destNumber = Integer.parseInt(getDestPhones().get(0));
					
					OutgoingCallResponsePacket outgoingPacket = new OutgoingCallResponsePacket();
					outgoingPacket.fromNumber = getOriginPhone();
					outgoingPacket.toNumber = getDestPhones().get(0);
					if (!phoneNumbers.doesNumberExist(destNumber) && originPhone != null) // Notify user phone does not exist, only if user is holding phone
					{
						outgoingPacket.state = States.noSuchNumber;
						PacketHandler.INSTANCE.sendTo(outgoingPacket, originPhone.getFirst());
						
						skipDisconnectNotification = true;
						
						if (parentCall != null)
						{
							parentCall.conferenceSubCalls.remove(this);
						}
						dequeueCall(getID());
						return;
					}
					else if (originPhone != null) // Notify call is going through, only if user is holding phone
					{	
						outgoingPacket.state = States.success;
						PacketHandler.INSTANCE.sendTo(outgoingPacket, originPhone.getFirst());
					}
					
					// Find destination phone in player's inventory and tell player to ring
					Tuple<EntityPlayerMP, ItemStack> destPhone = getDestOwners().get(0);
					if (destPhone != null)
					{
						IncomingCallPacket incoming = new IncomingCallPacket();
						incoming.fromNumber = getOriginPhone();
						incoming.toNumber = getDestPhones().get(0);
						PacketHandler.INSTANCE.sendTo(incoming, destPhone.getFirst());
					}
				}
				
				connectingTicks++;
				
				if (connectingTicks > ModConfig.phoneRingTicks)
				{
					if (parentCall != null)
					{
						parentCall.conferenceSubCalls.remove(this);
					}
					dequeueCall(getID());
					return;
				}
			}
		}
		
		public Tuple<EntityPlayerMP, ItemStack> getOriginOwner()
		{
			for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
			{
				AntennaData antennaData = AntennaData.getOrCreate(player.world);
				if (antennaData.getBestReception(player.getPosition()) <= 0.0)
				{
					return null;
				}
				
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
					
					int phoneNumber = stackData.getPhoneNumber();
					
					if (phoneNumber == 0)
					{
						continue;
					}
					
					if (getOriginPhone().equals(Integer.toString(phoneNumber)))
					{
						return new Tuple<>(player, stack);
					}
				}
			}
			
			return null;
		}
		
		public ArrayList<Tuple<EntityPlayerMP, ItemStack>> getDestOwners()
		{
			ArrayList<Tuple<EntityPlayerMP, ItemStack>> results = new ArrayList<>(); 
			for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
			{
				AntennaData antennaData = AntennaData.getOrCreate(player.world);
				if (antennaData.getBestReception(player.getPosition()) <= 0.0)
				{
					return null;
				}
				
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
					
					int phoneNumber = stackData.getPhoneNumber();
					
					if (phoneNumber == 0)
					{
						continue;
					}
					
					if (getDestPhones().stream().anyMatch(p -> p.equals(Integer.toString(phoneNumber))))
					{
						results.add(new Tuple<>(player, stack));
					}
				}
			}
			
			return results;
		}
	
		public void accept()
		{
			if (getCallPhase() != CallPhases.Connecting)
			{
				return;
			}
			
			callPhase = CallPhases.Connected;
			
			Tuple<EntityPlayerMP, ItemStack> origin = getOriginOwner();
			if (origin != null)
			{
				CallAcceptedPacket accepted = new CallAcceptedPacket();
				accepted.fromNumber = getOriginPhone();
				accepted.toNumber = getDestPhones().get(0);
				accepted.isConferenceSubCall = parentCall != null;
				PacketHandler.INSTANCE.sendTo(accepted, origin.getFirst());
			}
			
			Tuple<EntityPlayerMP, ItemStack> dest = getDestOwners().get(0);
			if (dest != null)
			{
				CallAcceptedPacket accepted = new CallAcceptedPacket();
				accepted.fromNumber = getDestPhones().get(0);
				accepted.toNumber = getOriginPhone();
				accepted.isConferenceSubCall = false;
				PacketHandler.INSTANCE.sendTo(accepted, dest.getFirst());
			}
		}
		
		public void reject()
		{
			Tuple<EntityPlayerMP, ItemStack> origin = getOriginOwner();
			Tuple<EntityPlayerMP, ItemStack> dest = getDestOwners().get(0);
			
			CallRejectedPacket rejected = new CallRejectedPacket();
			rejected.fromNumber = getOriginPhone();
			rejected.toNumber = getDestPhones().get(0);
			
			if (origin != null)
			{
				PacketHandler.INSTANCE.sendTo(rejected, origin.getFirst());
			}
			
			if (dest != null)
			{
				PacketHandler.INSTANCE.sendTo(rejected, dest.getFirst());
			}
			
			skipDisconnectNotification = true;
			
			if (parentCall != null)
			{
				parentCall.conferenceSubCalls.remove(this);
			}
			
			dequeueCall(getID());
		}
	
		public void onPlayerChat(EntityPlayerMP player, ITextComponent text)
		{
			if (getCallPhase() != CallPhases.Connected)
			{
				return;
			}
			
			for(Call call : conferenceSubCalls)
			{
				Tuple<EntityPlayerMP, ItemStack> subCallOrigin = call.getOriginOwner();
				if (subCallOrigin != null && subCallOrigin.getFirst() == player)
				{
					call.onPlayerChat(player, text);
					return;
				}
			}
			
			AntennaData data = AntennaData.getOrCreate(player.world);
			double senderReception = data.getBestReception(player.getPosition());
			
			Tuple<EntityPlayerMP, ItemStack> origin = getOriginOwner();
			Tuple<EntityPlayerMP, ItemStack> dest = getDestOwners().get(0);
			
			if (origin == null || dest == null)
			{
				return;
			}
			
			if (origin != null && origin.getFirst() != player && dest.getFirst() == player)
			{
				EntityPlayerMP receiver = origin.getFirst();
				double receiverReception = data.getBestReception(receiver.getPosition());
				
				double effectiveReception = receiverReception < senderReception ? receiverReception : senderReception;
				if (effectiveReception <= 0.0)
				{
					return;
				}
				
				ITextComponent textToSend = getScrambledText(text, effectiveReception);
				receiver.sendMessage(textToSend);
			}
			
			if (dest != null && dest.getFirst() != player && origin.getFirst() == player)
			{
				EntityPlayerMP receiver = dest.getFirst();
				double receiverReception = data.getBestReception(receiver.getPosition());
				
				double effectiveReception = receiverReception < senderReception ? receiverReception : senderReception;
				if (effectiveReception <= 0.0)
				{
					return;
				}
				
				ITextComponent textToSend = getScrambledText(text, effectiveReception);
				receiver.sendMessage(textToSend);
			}
		}
		
		private ITextComponent getScrambledText(ITextComponent text, double reception)
		{
			String formattedText = text.getFormattedText();
			char[] textChars = formattedText.replace(" ", "").toCharArray();
			char[] originalText = formattedText.toCharArray();
			int amountOfCharsToScramble = (int)(textChars.length * (1.0 - reception));
			
			if (amountOfCharsToScramble == textChars.length)
			{
				String returnedText = "";
				for(int i = 0; i < originalText.length; i++)
				{
					char originalChar = originalText[i];
					if (originalChar == ' ')
					{
						returnedText += " ";
					}
					else
					{
						returnedText += ModConfig.scrambleCharacter;
					}
				}
				
				return new TextComponentString(returnedText);
			}
			
			Random rand = new Random();
			HashSet<Integer> indexesToScramble = new HashSet<>(amountOfCharsToScramble);
			IntStream ints = rand.ints(0, originalText.length);
			
			OfInt iterator = ints.iterator();
			while(indexesToScramble.size() < amountOfCharsToScramble)
			{
				int index = iterator.next();
				
				if (originalText[index] == ' ' || originalText[index] == '\u00A7' || (index > 0 && originalText[index - 1] == '\u00A7'))
				{
					continue;
				}
				
				indexesToScramble.add(index);
			}
			
			ints.close();
			
			String newText = "";
			for(int i = 0; i < originalText.length; i++)
			{
				if (indexesToScramble.contains(i))
				{
					newText += ModConfig.scrambleCharacter;
				}
				else
				{
					newText += originalText[i];
				}
			}
			
			TextComponentString retVal = new TextComponentString("[Phone]");
			Style style = new Style();
			style.setColor(TextFormatting.DARK_PURPLE);
			retVal.setStyle(style);
			retVal.appendSibling(new TextComponentString(newText).setStyle(new Style().setColor(TextFormatting.RESET)));
			
			return retVal;
		}

		public void merge(String mergingNumber, int clientHandlerCode)
		{
			Optional<Call> optSubCall = conferenceSubCalls.stream().filter(c -> c.getOriginPhone().equals(mergingNumber)).findFirst();
			if (!optSubCall.isPresent())
			{
				return;
			}
			
			Call subCall = optSubCall.get();
			destPhones.addAll(subCall.getDestPhones());
			conferenceSubCalls.addAll(subCall.conferenceSubCalls);
			conferenceSubCalls.remove(subCall);
			
			for(String destPhone : subCall.getDestPhones())
			{
				callsByPhone.put(destPhone, this);
			}
			
			PhoneQueryResponsePacket responsePacket = new PhoneQueryResponsePacket();
			
			Tuple<EntityPlayerMP, ItemStack> origin = getOriginOwner();
			if (origin != null)
			{
				origin.getFirst().sendMessage(new TextComponentString("Call merged"));
				
				Tuple<ResponseTypes, String> response = phoneQuery(origin.getFirst(), getOriginPhone());
				responsePacket.forNumber = getOriginPhone();
				responsePacket.responseType = response.getFirst();
				responsePacket.otherNumber = response.getSecond();
				responsePacket.clientHandlerCode = clientHandlerCode;
				
				PacketHandler.INSTANCE.sendTo(responsePacket, origin.getFirst());
			}
			
			for(Tuple<EntityPlayerMP, ItemStack> dest : getDestOwners())
			{
				dest.getFirst().sendMessage(new TextComponentString("You are now connected to " + getFormattedPhoneNumber(getOriginPhone())));
				
				ItemPhone.NBTData nbtData = new ItemPhone.NBTData();
				nbtData.deserializeNBT(dest.getSecond().getTagCompound());
				
				Tuple<ResponseTypes, String> response = phoneQuery(dest.getFirst(), nbtData.getPhoneNumberString());
				responsePacket.forNumber = nbtData.getPhoneNumberString();
				responsePacket.responseType = response.getFirst();
				responsePacket.otherNumber = getOriginPhone();
				responsePacket.clientHandlerCode = PhoneQueryResponsePacket.PHONE_APP_CLIENT_HANDLER;
				
				PacketHandler.INSTANCE.sendTo(responsePacket, dest.getFirst());
			}
		}
		
		public void disconnectDest(String dest)
		{
			if (!getDestPhones().contains(dest))
			{
				return;
			}
			
			destPhones.remove(dest);
			
			if (destPhones.size() > 0)
			{
				DisconnectedCallNotificationPacket disconnectPacket = new DisconnectedCallNotificationPacket();
				disconnectPacket.forNumber = dest;
				disconnectPacket.toNumber = Reference.PHONE_CONFERENCE_NAME;
				
			}
		}
	}
	
	public void onPlayerChat(EntityPlayerMP player, ITextComponent text)
	{
		for(Call call : callsByID.values())
		{
			call.onPlayerChat(player, text);
		}
	}

	public Tuple<ResponseTypes, String> phoneQuery(EntityPlayerMP player, String forNumber)
	{
		World world = player.world;
		
		AntennaData antennaData = AntennaData.getOrCreate(world);
		if (antennaData.getBestReception(player.getPosition()) <= 0.0)
		{
			// Too far away
			return new Tuple<>(ResponseTypes.idle, "");
		}
		
		CallManager.Call call = getCall(forNumber);
		
		if (call == null) // No calls for number
		{
			return new Tuple<>(ResponseTypes.idle, "");
		}
		else // A call exists for number...
		{			
			return phoneQueryForCall(call, forNumber);
		}
	}
	
	private Tuple<ResponseTypes, String> phoneQueryForCall(Call call, String forNumber)
	{
		if (call.doesConferenceSubCallsContainOrigin(forNumber))
		{
			return phoneQueryForCall(call.conferenceSubCalls.stream().filter(c -> c.getOriginPhone().equals(forNumber)).findFirst().get(), forNumber);
		}
		
		if (call.getDestPhones().stream().anyMatch(p -> p.equals(forNumber))) // It is being called
		{
			if (call.getCallPhase() == CallPhases.Connecting)
			{
				return new Tuple<>(ResponseTypes.callIncoming, call.getDestPhones().size() > 1 ? rz.mesabrook.wbtc.util.Reference.PHONE_CONFERENCE_NAME : call.getOriginPhone());
			}
			else
			{
				return new Tuple<>(ResponseTypes.callConnected, call.getDestPhones().size() > 1 ? rz.mesabrook.wbtc.util.Reference.PHONE_CONFERENCE_NAME : call.getOriginPhone());
			}
		}
		else
		{
			if (call.getCallPhase() == CallPhases.Connecting) // It is trying to call another number
			{
				return new Tuple<>(ResponseTypes.callConnecting, call.getDestPhones().size() > 1 ? rz.mesabrook.wbtc.util.Reference.PHONE_CONFERENCE_NAME : call.getDestPhones().get(0));
			}
			else if (call.getCallPhase() == CallPhases.Connected) // It is already connected
			{
				return new Tuple<>(ResponseTypes.callConnected, call.getDestPhones().size() > 1 ? rz.mesabrook.wbtc.util.Reference.PHONE_CONFERENCE_NAME : call.getDestPhones().get(0));
			}
		}
		
		return new Tuple<>(ResponseTypes.idle, "");
	}

	public void sendQueryResponseForAllCalls(EntityPlayerMP player)
	{
		for(CallManager.Call call : callsByID.values())
		{
			Tuple<EntityPlayerMP, ItemStack> origin = call.getOriginOwner();
			ArrayList<Tuple<EntityPlayerMP, ItemStack>> dests = call.getDestOwners();
			
			if (origin != null && origin.getFirst() == player)
			{
				ItemPhone.NBTData originData = new ItemPhone.NBTData();
				originData.deserializeNBT(origin.getSecond().getTagCompound());
				
				Tuple<ResponseTypes, String> response = phoneQuery(player, originData.getPhoneNumberString());
				
				PhoneQueryResponsePacket responsePacket = new PhoneQueryResponsePacket();
				responsePacket.forNumber = originData.getPhoneNumberString();
				responsePacket.responseType = response.getFirst();
				responsePacket.otherNumber = response.getSecond();
				responsePacket.clientHandlerCode = PhoneQueryResponsePacket.PHONE_START_CLIENT_HANDLER;
				PacketHandler.INSTANCE.sendTo(responsePacket, player);
			}
			
			for (Tuple<EntityPlayerMP, ItemStack> dest : dests)
			{
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

	private static String getFormattedPhoneNumber(String number)
	{

		String formattedNumber = number;
		if (formattedNumber.length() >= 3)
		{
			formattedNumber = formattedNumber.substring(0, 3) + "-" + formattedNumber.substring(3, formattedNumber.length());
		}
		return formattedNumber;
	}
}
