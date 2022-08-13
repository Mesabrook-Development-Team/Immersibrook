package com.mesabrook.ib.util.saveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.util.PhoneLogState;
import com.mesabrook.ib.util.Reference;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PhoneLogData extends WorldSavedData {
	
	HashMap<Integer, ArrayList<LogData>> logDatumByPhoneNumber = new HashMap<>();
	HashMap<UUID, LogData> logDatumByID = new HashMap<>();
	public PhoneLogData(String dataName)
	{
		super(dataName);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		logDatumByPhoneNumber.clear();
		logDatumByID.clear();
		
		NBTTagList tagList = nbt.getTagList("logs", NBT.TAG_COMPOUND);
		for(NBTBase base : tagList)
		{
			NBTTagCompound tag = (NBTTagCompound)base;
			int forNumber = tag.getInteger("forNumber");
			if (!logDatumByPhoneNumber.containsKey(forNumber))
			{
				logDatumByPhoneNumber.put(forNumber, new ArrayList<>(50));
			}
			
			NBTTagList logDatum = tag.getTagList("logDatum", NBT.TAG_COMPOUND);
			for(NBTBase logDataBase : logDatum)
			{
				NBTTagCompound logDataTag = (NBTTagCompound)logDataBase;
				
				LogData logData = new LogData();
				logData.deserializeNBT(logDataTag);
				logDatumByPhoneNumber.get(forNumber).add(logData);
				logDatumByID.put(logData.getLogID(), logData);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList logs = new NBTTagList();
		for(Entry<Integer, ArrayList<LogData>> logDatumByPhoneNumberEntry : logDatumByPhoneNumber.entrySet())
		{
			NBTTagCompound entry = new NBTTagCompound();
			entry.setInteger("forNumber", logDatumByPhoneNumberEntry.getKey());
			
			NBTTagList logDatum = new NBTTagList();
			for(LogData logData : logDatumByPhoneNumberEntry.getValue())
			{
				NBTTagCompound logTag = logData.serializeNBT();
				logDatum.appendTag(logTag);
			}
			entry.setTag("logDatum", logDatum);
			logs.appendTag(entry);
		}
		
		compound.setTag("logs", logs);
		return compound;
	}

	public ImmutableList<LogData> getLogsByPhone(int phoneNumber)
	{
		if (!logDatumByPhoneNumber.containsKey(phoneNumber))
		{
			return ImmutableList.of();
		}
		
		return ImmutableList.copyOf(logDatumByPhoneNumber.get(phoneNumber));
	}
	
	public LogData getLogByID(UUID id)
	{
		return logDatumByID.get(id);
	}
	
	public LogData addLog(int phoneNumber, LogData log)
	{
		log.setMarkDirtyCallback(() -> markDirty());
		if (!logDatumByPhoneNumber.containsKey(phoneNumber))
		{
			logDatumByPhoneNumber.put(phoneNumber, new ArrayList<>());
		}
		
		while(logDatumByPhoneNumber.get(phoneNumber).size() >= 50)
		{
			LogData removedData = logDatumByPhoneNumber.get(phoneNumber).remove(0);
			logDatumByID.values().remove(removedData);
		}
		
		logDatumByPhoneNumber.get(phoneNumber).add(log);
		logDatumByID.put(log.getLogID(), log);
		
		markDirty();
		
		return log;
	}
	
	public LogData addLog(int phoneNumber, int otherNumber, long callTime, long callLength, PhoneLogState phoneLogState)
	{
		return addLog(phoneNumber, new LogData(otherNumber, callTime, callLength, phoneLogState));
	}
	
	public LogData addLog(int phoneNumber, int otherNumber, Calendar callTime, long callLength, PhoneLogState phoneLogState)
	{
		return addLog(phoneNumber, new LogData(otherNumber, callTime, callLength, phoneLogState));
	}
		
	public static class LogData implements INBTSerializable<NBTTagCompound>
	{

		private UUID logID;
		private int[] otherNumbers;
		private long callTime;
		private long callLength;
		private PhoneLogState phoneLogState;
		private Runnable markDirtyCallback = null;
		
		public LogData() { }
		
		public LogData(int otherNumber, long callTime, long callLength, PhoneLogState phoneLogState)
		{
			setOtherNumbers(new int[] { otherNumber });
			setCallTime(callTime);
			setCallLength(callLength);
			setPhoneLogState(phoneLogState);
		}
		
		public LogData(int otherNumber, Calendar callTime, long callLength, PhoneLogState phoneLogState)
		{
			this(otherNumber, callTime.getTimeInMillis(), callLength, phoneLogState);
		}
		
		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setUniqueId("logID", logID);
			tag.setIntArray("otherNumbers", getOtherNumbers());
			tag.setLong("callTime", getCallTime());
			tag.setLong("callLength", getCallLength());
			tag.setInteger("state", getPhoneLogState().ordinal());
			return tag;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			setLogID(nbt.getUniqueId("logID"));
			if (nbt.hasKey("otherNumber"))
			{
				setOtherNumbers(new int[] { nbt.getInteger("otherNumber") });
			}
			else if (nbt.hasKey("otherNumbers"))
			{
				setOtherNumbers(nbt.getIntArray("otherNumbers"));
			}
			setCallTime(nbt.getLong("callTime"));
			setCallLength(nbt.getLong("callLength"));
			setPhoneLogState(PhoneLogState.values()[nbt.getInteger("state")]);
		}

		public UUID getLogID() {
			if (logID == null)
			{
				setLogID(UUID.randomUUID());
			}
			
			return logID;
		}

		public void setLogID(UUID logID) {
			this.logID = logID;
			markDirty();
		}

		public int[] getOtherNumbers() {
			return otherNumbers;
		}

		public void setOtherNumbers(int[] otherNumbers) {
			this.otherNumbers = otherNumbers;
			markDirty();
		}

		public long getCallTime() {
			return callTime;
		}

		public void setCallTime(long callTime) {
			this.callTime = callTime;
			markDirty();
		}
		
		public Calendar getCallTimeCalendar()
		{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(getCallTime());
			return cal;
		}
		
		public void setCallTimeCalendar(Calendar calendar)
		{
			setCallTime(calendar.getTimeInMillis());
		}

		public long getCallLength() {
			return callLength;
		}

		public void setCallLength(long callLength) {
			this.callLength = callLength;
			markDirty();
		}

		public PhoneLogState getPhoneLogState() {
			return phoneLogState;
		}

		public void setPhoneLogState(PhoneLogState phoneLogState) {
			this.phoneLogState = phoneLogState;
			markDirty();
		}
	
		public void setMarkDirtyCallback(Runnable callback)
		{
			markDirtyCallback = callback;
		}
		
		private void markDirty()
		{
			if (markDirtyCallback != null)
			{
				markDirtyCallback.run();
			}
		}
	}

	public static PhoneLogData getOrCreate(World world)
	{
		PhoneLogData data = (PhoneLogData)world.loadData(PhoneLogData.class, Reference.PHONE_LOG_DATA_NAME);
		if (data == null)
		{
			data = new PhoneLogData(Reference.PHONE_LOG_DATA_NAME);
			world.setData(Reference.PHONE_LOG_DATA_NAME, data);
		}
		
		return data;
	}

	public void removeLogByID(UUID id)
	{
		LogData data = logDatumByID.get(id);
		if (data == null)
		{
			return;
		}
		
		for(Entry<Integer, ArrayList<LogData>> entry : logDatumByPhoneNumber.entrySet())
		{
			entry.getValue().remove(data);
		}
	}
}
