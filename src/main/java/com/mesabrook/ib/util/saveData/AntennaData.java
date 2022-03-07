package com.mesabrook.ib.util.saveData;

import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map.Entry;

public class AntennaData extends WorldSavedData {

	private HashMap<Long, Integer> antennaHeightsByBlockPos = new HashMap<>();
	
	public AntennaData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int i = 0;
		while(nbt.hasKey("height_key" + i))
		{
			long key = nbt.getLong("height_key" + i);
			int height = nbt.getInteger("height_value" + i);
			
			antennaHeightsByBlockPos.put(key, height);
			i++;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		int i = 0;
		for(Entry<Long, Integer> entry : antennaHeightsByBlockPos.entrySet())
		{
			compound.setLong("height_key" + i, entry.getKey());
			compound.setInteger("height_value" + i, entry.getValue());
			i++;
		}
		
		return compound;
	}
	
	public void setHeight(BlockPos pos, int height)
	{
		antennaHeightsByBlockPos.put(pos.toLong(), height);
		markDirty();
	}
	
	public void removeHeight(BlockPos pos)
	{
		antennaHeightsByBlockPos.remove(pos.toLong());
		markDirty();
	}
	
	public double getBestReception(BlockPos fromPos)
	{
		double candidate = 0.0;
		for(Entry<Long, Integer> heightEntry : antennaHeightsByBlockPos.entrySet())
		{
			BlockPos antennaPos = BlockPos.fromLong(heightEntry.getKey());
			
			double distanceFromAntenna = antennaPos.getDistance(fromPos.getX(), fromPos.getY(), fromPos.getZ());
			
			double maxCoverage = (double)ModConfig.cellAntennaMaxReception * ((double)heightEntry.getValue() / ModConfig.cellAntennaOptimalHeight);
			double spottyCoverage = (double)ModConfig.cellAntennaSpottyReception * ((double)heightEntry.getValue() / ModConfig.cellAntennaOptimalHeight) + maxCoverage;
			
			if (distanceFromAntenna <= maxCoverage)
			{
				return 1.0;
			}
			else if (distanceFromAntenna <= spottyCoverage)
			{
				double distortion = 1.0 - ((distanceFromAntenna - maxCoverage) / (spottyCoverage - maxCoverage));
				if (distortion > candidate)
				{
					candidate = distortion;
				}
			}
		}
		
		return candidate;
	}
	
	public static AntennaData getOrCreate(World world)
	{
		AntennaData data = (AntennaData)world.loadData(AntennaData.class, Reference.ANTENNA_DATA_NAME);
		if (data == null)
		{
			data = new AntennaData(Reference.ANTENNA_DATA_NAME);
			world.setData(Reference.ANTENNA_DATA_NAME, data);
		}
		
		return data;
	}

}
