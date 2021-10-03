package rz.mesabrook.wbtc.util.saveData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.config.ModConfig;

import java.util.HashSet;
import java.util.Random;

public class PhoneNumberData extends WorldSavedData {
	
	private HashSet<Integer> usedPhoneNumbers = new HashSet<Integer>();
	public PhoneNumberData(String name)
	{
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int i = 0;
		usedPhoneNumbers = new HashSet<>();
		while(nbt.hasKey("phone" + i))
		{
			usedPhoneNumbers.add(nbt.getInteger("phone" + i));
			i++;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		int i = 0;
		for(int number : usedPhoneNumbers)
		{
			compound.setInteger("phone" + i, number);
			i++;
		}
		
		return compound;
	}

	public static PhoneNumberData getOrCreate(World world)
	{
		WorldSavedData data = world.loadData(PhoneNumberData.class, Reference.PHONE_NUMBER_DATA_NAME);
		
		if (data == null)
		{
			data = new PhoneNumberData(Reference.PHONE_NUMBER_DATA_NAME);
			world.setData(Reference.PHONE_NUMBER_DATA_NAME, data);
		}
		
		return (PhoneNumberData)data;
	}
	
	public int getNewPhoneNumber()
	{
		Random rand = new Random();
		
		int newNumber = 0;
		do
		{
			newNumber = rand.ints(ModConfig.minimumPhoneNumber, ModConfig.maximumPhoneNumber).findFirst().getAsInt();
		}
		while(usedPhoneNumbers.contains(newNumber));
		
		return newNumber;
	}
	
	public boolean consumeNumber(int number)
	{
		if (usedPhoneNumbers.add(number))
		{
			markDirty();
			return true;
		}
		
		return false;
	}

	public boolean doesNumberExist(int number)
	{
		return usedPhoneNumbers.contains(number);
	}
}
