package com.mesabrook.ib.util.saveData;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class PlayerLoudnessData extends WorldSavedData {

	private HashMap<UUID, PlayerLoudnessLevel> loudnessByPlayer = new HashMap<>();
	
	public PlayerLoudnessData(String dataName)
	{
		super(dataName);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		for(String key : nbt.getKeySet())
		{
			try
			{
				UUID playerID = UUID.fromString(key);
				String keyValue = nbt.getString(key);
				PlayerLoudnessLevel level = PlayerLoudnessLevel.valueOf(keyValue);
				
				loudnessByPlayer.put(playerID, level);
			}
			catch(Exception ex)
			{
				Main.logger.error("An error occurred parsing UUID from string: " + key, ex);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(Entry<UUID, PlayerLoudnessLevel> entry : loudnessByPlayer.entrySet())
		{
			compound.setString(entry.getKey().toString(), entry.getValue().toString());
		}
		
		return compound;
	}

	public static PlayerLoudnessData getOrCreate(World world)
	{
		PlayerLoudnessData loudData = (PlayerLoudnessData)world.loadData(PlayerLoudnessData.class, Reference.LOUDNESS_DATA_NAME);
		if (loudData == null)
		{
			loudData = new PlayerLoudnessData(Reference.LOUDNESS_DATA_NAME);
			world.setData(Reference.LOUDNESS_DATA_NAME, loudData);
		}
		
		return loudData;
	}
	
	public PlayerLoudnessLevel getLevelForPlayer(UUID playerID)
	{
		if (!loudnessByPlayer.containsKey(playerID))
		{
			setLevelForPlayer(playerID, PlayerLoudnessLevel.Normally);
		}
		
		return loudnessByPlayer.get(playerID);
	}
	
	public void setLevelForPlayer(UUID playerID, PlayerLoudnessLevel level)
	{
		loudnessByPlayer.put(playerID, level);
		markDirty();
	}
}
