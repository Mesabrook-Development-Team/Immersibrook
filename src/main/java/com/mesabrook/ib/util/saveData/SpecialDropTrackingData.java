package com.mesabrook.ib.util.saveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import com.mesabrook.ib.util.Reference;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

public class SpecialDropTrackingData extends WorldSavedData {

	public SpecialDropTrackingData(String name) {
		super(name);
	}

	HashMap<Integer, ArrayList<UUID>> birthdayCakesReceivedByYear = new HashMap<>();
	HashMap<Integer, ArrayList<UUID>> christmasPresentsReceivedByYear = new HashMap<>();
	HashMap<Integer, ArrayList<UUID>> halloweenPresentsReceivedByYear = new HashMap<>();
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		birthdayCakesReceivedByYear = new HashMap<>();
		readIntoMap(nbt, birthdayCakesReceivedByYear, "cakes");
		
		christmasPresentsReceivedByYear = new HashMap<>();
		readIntoMap(nbt, birthdayCakesReceivedByYear, "christmas");
		
		halloweenPresentsReceivedByYear = new HashMap<>();
		readIntoMap(nbt, halloweenPresentsReceivedByYear, "halloween");
	}
	
	private void readIntoMap(NBTTagCompound nbt, HashMap<Integer, ArrayList<UUID>> map, String keyPrefix)
	{
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		if (nbt.hasKey(keyPrefix + currentYear))
		{
			map.put(currentYear, new ArrayList<>());
			NBTTagList list = nbt.getTagList(keyPrefix + currentYear, NBT.TAG_COMPOUND);
			for(NBTBase item : list)
			{
				NBTTagCompound compound = (NBTTagCompound)item;
				UUID playerID = compound.getUniqueId("player");
				map.get(currentYear).add(playerID);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		writeFromMap(compound, birthdayCakesReceivedByYear, "cakes");
		writeFromMap(compound, christmasPresentsReceivedByYear, "christmas");
		writeFromMap(compound, halloweenPresentsReceivedByYear, "halloween");
		return compound;
	}
	
	private void writeFromMap(NBTTagCompound compound, HashMap<Integer, ArrayList<UUID>> map, String keyPrefix)
	{
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		if (map.containsKey(currentYear))
		{
			NBTTagList newList = new NBTTagList();
			for(UUID playerID : map.get(currentYear))
			{
				NBTTagCompound childTag = new NBTTagCompound();
				childTag.setUniqueId("player", playerID);
				newList.appendTag(childTag);
			}
			
			compound.setTag(keyPrefix + currentYear, newList);
		}
	}

	public static SpecialDropTrackingData getOrCreate(World world)
	{
		SpecialDropTrackingData data = (SpecialDropTrackingData)world.loadData(SpecialDropTrackingData.class, Reference.SPECIAL_DROP_TRACKING_DATA_NAME);
		if (data == null)
		{
			data = new SpecialDropTrackingData(Reference.SPECIAL_DROP_TRACKING_DATA_NAME);
			world.setData(Reference.SPECIAL_DROP_TRACKING_DATA_NAME, data);
		}
		
		return data;
	}
	
	public boolean canGiveCake(UUID forPlayer)
	{
		return checkOrSetForYear(forPlayer, birthdayCakesReceivedByYear);
	}
	
	public boolean canGiveChristmasPresent(UUID forPlayer)
	{
		return checkOrSetForYear(forPlayer, christmasPresentsReceivedByYear);
	}
	
	public boolean canGiveHalloweenPresent(UUID forPlayer)
	{
		return checkOrSetForYear(forPlayer, halloweenPresentsReceivedByYear);
	}
	
	private boolean checkOrSetForYear(UUID forPlayer, HashMap<Integer, ArrayList<UUID>> map)
	{
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (!map.containsKey(currentYear))
		{
			map.put(currentYear, new ArrayList<>());
		}
		
		if (!map.get(currentYear).contains(forPlayer))
		{
			map.get(currentYear).add(forPlayer);
			markDirty();
			return true;
		}
		
		return false;
	}
}
