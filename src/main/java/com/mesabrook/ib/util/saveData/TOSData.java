package com.mesabrook.ib.util.saveData;

import java.util.HashSet;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class TOSData extends WorldSavedData {

	private HashSet<UUID> players = new HashSet<>();
	public TOSData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int i = 0;
		while(nbt.hasKey("player" + i))
		{
			players.add(nbt.getUniqueId("player" + i));
			i++;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		int i = 0;
		for(UUID player : players)
		{
			compound.setUniqueId("player" + i, player);
			i++;
		}
		
		return compound;
	}

	public void addPlayer(UUID playerID)
	{
		players.add(playerID);
		markDirty();
	}
	
	public void clearPlayers()
	{
		players.clear();
		markDirty();
	}
	
	public boolean containsPlayer(UUID playerID)
	{
		return players.contains(playerID);
	}
}
