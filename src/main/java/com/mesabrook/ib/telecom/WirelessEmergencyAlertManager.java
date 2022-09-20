package com.mesabrook.ib.telecom;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.net.telecom.WirelessEmergencyAlertPacket;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager.WirelessEmergencyAlert.Coordinate;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class WirelessEmergencyAlertManager {
	private static WirelessEmergencyAlertManager instance;
	private Thread netThread = null;
	private boolean _requestStop = false;
	private ConcurrentHashMap<UUID, WirelessEmergencyAlert> alertsByID = new ConcurrentHashMap<>();
	private ConcurrentLinkedQueue<WirelessEmergencyAlert> newAlerts = new ConcurrentLinkedQueue<>();
	private ConcurrentLinkedQueue<WirelessEmergencyAlert> removedAlerts = new ConcurrentLinkedQueue<>();

	public static WirelessEmergencyAlertManager instance() {
		if (instance == null) {
			instance = new WirelessEmergencyAlertManager();
		}

		return instance;
	}

	private WirelessEmergencyAlertManager() {
	}

	public void start() {
		if (netThread != null && netThread.isAlive()) {
			return;
		}

		netThread = new Thread(() -> netThreadLogic(), "WEA Thread");
		_requestStop = false;
		netThread.start();
	}

	public void stop() {
		_requestStop = true;
	}

	private void netThreadLogic() {
		try {
			int attempt = 0;
			while (!_requestStop) {
				try {
					if (ModConfig.enableWEAForWeather) {
						checkWXRadio();
					}
					attempt = 0;
				} catch (Exception ex) {
					attempt++;
					
					if (attempt > 5)
					{
						Main.logger.error("Exceeded 5 attempts to check the weather radio. Use \"/ib wea start\" to restart the listener.", ex);
						return;
					}
					else
					{
						Main.logger.error("Failed to check the wx radio " + attempt + "/5 times.", ex);
					}
				}

				Thread.sleep(500);
			}
		} catch (InterruptedException ex) { }
		finally
		{
			removedAlerts.addAll(alertsByID.values());

			removeAlertsFromDynmap();
			removedAlerts.clear();
			alertsByID.clear();
			newAlerts.clear();
		}
	}

	private void checkWXRadio() throws IOException {
		Socket socket = new Socket(ModConfig.advisoryProductServerIP, ModConfig.advisoryProductServerPort);
		JsonReader reader = new JsonReader(new InputStreamReader(socket.getInputStream()));
		JsonParser parser = new JsonParser();
		JsonElement json = parser.parse(reader);

		socket.close();

		JsonArray array = json.getAsJsonArray();
		HashSet<UUID> identifiedProducts = new HashSet<>();
		
		for (JsonElement item : array) {
			if (!item.isJsonObject()) {
				continue;
			}

			JsonObject productObject = item.getAsJsonObject();
			String productID = productObject.get("ID").getAsString();
			UUID id = UUID.fromString(productID);
			identifiedProducts.add(id);
			boolean isCancelled = productObject.get("IsCancelled").getAsBoolean();
			
			if (alertsByID.containsKey(id)) {
				if (isCancelled)
				{
					removedAlerts.add(alertsByID.get(id));
					alertsByID.remove(id);
				}
				continue;
			}
			
			if (isCancelled)
			{
				continue;
			}

			String name = productObject.get("Name").getAsString();
			String summary = productObject.get("Summary").getAsString();

			WirelessEmergencyAlert newAlert = new WirelessEmergencyAlert();
			newAlert.setId(id);
			newAlert.setName(name);
			newAlert.setDescription(summary);

			JsonArray coordArray = productObject.get("Coordinates").getAsJsonArray();
			for (JsonElement element : coordArray) {
				JsonObject coordinateObject = element.getAsJsonObject();
				int x = (int) coordinateObject.get("X").getAsDouble();
				int z = (int) coordinateObject.get("Z").getAsDouble();

				Coordinate coordinate = new Coordinate(x, z);
				newAlert.addCoord(coordinate);
			}

			alertsByID.put(id, newAlert);
			newAlerts.add(newAlert);
		}
		
		for(UUID unidentifiedID : alertsByID.keySet())
		{
			if (identifiedProducts.contains(unidentifiedID))
			{
				continue;
			}
			
			removedAlerts.add(alertsByID.get(unidentifiedID));
		}
	}

	public void sendAlerts()
	{
		sendAlertsToPhones();
		sendAlertsToDynmap();
		
		newAlerts.clear();
		
		removeAlertsFromDynmap();
		
		removedAlerts.clear();
	}
	
	private void sendAlertsToPhones()
	{
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
		
		for(WirelessEmergencyAlert alert : newAlerts)
		{
			ArrayList<Tuple<ItemStack, EntityPlayer>> phonesToAlert = new ArrayList<Tuple<ItemStack, EntityPlayer>>();
			
			for(EntityPlayer player : world.playerEntities)
			{
				Coordinate playerCoord = new Coordinate((int)player.posX, (int)player.posZ);
				if (!com.mesabrook.ib.util.Math.isCoordinateInPolygon(playerCoord, alert.getCoords()))
				{
					continue;	
				}
				
				for(int i = 0; i < player.inventory.getSizeInventory(); i++)
				{
					ItemStack itemStack = player.inventory.getStackInSlot(i);
					if (!(itemStack.getItem() instanceof ItemPhone))
					{
						continue;
					}
					
					phonesToAlert.add(new Tuple<>(itemStack, player));
				}
			}
			
			for(Tuple<ItemStack, EntityPlayer> phone : phonesToAlert)
			{
				NBTData data = NBTData.getFromItemStack(phone.getFirst());
				
				BlockPos currentPos = phone.getSecond().getPosition();
				ServerSoundBroadcastPacket playSound = new ServerSoundBroadcastPacket();
				playSound.soundName = "alert_tone";
				playSound.pos = currentPos;
				PacketHandler.INSTANCE.sendToAllAround(playSound, new TargetPoint(phone.getSecond().dimension, currentPos.getX(), currentPos.getY(), currentPos.getZ(), 25));
				
				WirelessEmergencyAlertPacket packet = new WirelessEmergencyAlertPacket();
				packet.phoneNumber = data.getPhoneNumberString();
				packet.alert = alert;
				PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)phone.getSecond());
			}
		}
	}

	private void sendAlertsToDynmap()
	{
		if (!Main.DYNMAP)
		{
			return;
		}
		
		for(WirelessEmergencyAlert alert : newAlerts)
		{
			DynmapAPIListener.setupAlertArea(alert);
		}
	}
	
	private void removeAlertsFromDynmap()
	{
		if (!Main.DYNMAP)
		{
			return;
		}
		
		for(WirelessEmergencyAlert alert : removedAlerts)
		{
			DynmapAPIListener.takeDownAlertArea(alert);
		}
	}
	
	public static class WirelessEmergencyAlert implements net.minecraftforge.common.util.INBTSerializable<net.minecraft.nbt.NBTTagCompound> {

		private UUID id;
		private String name;
		private String description;
		private Coordinate[] coordinates = new Coordinate[4];

		public UUID getId() {
			return id;
		}

		public void setId(UUID id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public ImmutableList<Coordinate> getCoords() {
			return ImmutableList.copyOf(coordinates);
		}

		public void addCoord(Coordinate coord) {
			for (int i = 0; i < 4; i++) {
				if (coordinates[i] == null) {
					coordinates[i] = coord;
					break;
				}
			}
		}

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setUniqueId("id", getId());
			tag.setString("name", getName());
			tag.setString("description", getDescription());
			
			for(int i = 0; i < 4; i++)
			{
				Coordinate coord = coordinates[i];
				if (coord == null)
				{
					continue;
				}
				
				tag.setIntArray("coord" + i, new int[] { coord.x, coord.z });
			}
			
			return tag;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			setId(nbt.getUniqueId("id"));
			setName(nbt.getString("name"));
			setDescription(nbt.getString("description"));

			for(int i = 0; i < 4; i++)
			{
				if (!nbt.hasKey("coord" + i))
				{
					continue;
				}
				
				int[] coords = nbt.getIntArray("coord" + i);
				Coordinate coord = new Coordinate(coords[0], coords[1]);
				coordinates[i] = coord;
			}
		}

		@Override
		public int hashCode() {
			return id.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof WirelessEmergencyAlert && ((WirelessEmergencyAlert) obj).id.equals(id);
		}

		public static class Coordinate {
			private int x;
			private int z;

			public Coordinate() {
			}

			public Coordinate(int x, int z) {
				this.x = x;
				this.z = z;
			}

			public int getX() {
				return x;
			}

			public void setX(int x) {
				this.x = x;
			}

			public int getZ() {
				return z;
			}

			public void setZ(int z) {
				this.z = z;
			}

		}
	}
}
