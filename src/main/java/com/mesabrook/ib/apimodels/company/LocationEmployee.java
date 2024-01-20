package com.mesabrook.ib.apimodels.company;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class LocationEmployee implements INBTSerializable<NBTTagCompound> {
	public long LocationEmployeeID;
	public long LocationID;
	public Location Location;
	public boolean ManageRegisters;
	public boolean ManagePrices;
	public boolean ManageInventory;
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		if (Location != null)
		{
			if (Location.Company != null)
			{
				compound.setLong("CompanyID", Location.Company.CompanyID);
				compound.setString("CompanyName", Location.Company.Name == null ? "" : Location.Company.Name);
			}
			
			compound.setLong("LocationID", Location.LocationID);
			compound.setString("LocationName", Location.Name == null ? "" : Location.Name);
		}
		
		compound.setLong("LocationEmployeeID", LocationEmployeeID);
		compound.setBoolean("ManageRegisters", ManageRegisters);
		compound.setBoolean("ManagePrices", ManagePrices);
		compound.setBoolean("ManageInventory", ManageInventory);
		
		return compound;
	}
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		Location = new Location();
		Location.Company = new Company();
		
		Location.Company.CompanyID = nbt.getLong("CompanyID");
		Location.Company.Name = nbt.getString("CompanyName");
		Location.LocationID = nbt.getLong("LocationID");
		Location.CompanyID = Location.Company.CompanyID;
		Location.Name = nbt.getString("LocationName");
		LocationID = Location.LocationID;
		LocationEmployeeID = nbt.getLong("LocationEmployeeID");
		ManageRegisters = nbt.getBoolean("ManageRegisters");
		ManagePrices = nbt.getBoolean("ManagePrices");
		ManageInventory = nbt.getBoolean("ManageInventory");
	}
}
