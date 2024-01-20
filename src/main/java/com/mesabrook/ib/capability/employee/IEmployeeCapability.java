package com.mesabrook.ib.capability.employee;

import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.net.sco.EmployeeCapServerToClientPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public interface IEmployeeCapability {
	void setPlayer(EntityPlayer player);
	LocationEmployee getLocationEmployee();
	void setLocationEmployee(LocationEmployee locationEmployee);
	long getLocationID();
	boolean manageRegisters();
	boolean manageInventory();
	boolean managePrices();
	void serverToClientSync();
	
	public static class Impl implements IEmployeeCapability
	{
		private EntityPlayer player;
		private LocationEmployee locationEmployee;
		public Impl(EntityPlayer player)
		{
			this.player = player;
			locationEmployee = new LocationEmployee();
			locationEmployee.LocationID = 0;
		}
		
		@Override
		public LocationEmployee getLocationEmployee()
		{
			return locationEmployee;
		}
		
		@Override
		public void setPlayer(EntityPlayer player)
		{
			this.player = player;
		}

		@Override
		public void setLocationEmployee(LocationEmployee locationEmployee) {
			this.locationEmployee = locationEmployee;
			if (this.locationEmployee == null)
			{
				this.locationEmployee = new LocationEmployee();
				this.locationEmployee.LocationID = 0;
			}
		}

		@Override
		public long getLocationID() {			
			return locationEmployee.LocationID;
		}

		@Override
		public boolean manageRegisters() {
			return locationEmployee == null ? false : locationEmployee.ManageRegisters;
		}

		@Override
		public boolean manageInventory() {
			return locationEmployee == null ? false : locationEmployee.ManageInventory;
		}

		@Override
		public boolean managePrices() {
			return locationEmployee == null ? false : locationEmployee.ManagePrices;
		}
		
		@Override
		public void serverToClientSync()
		{
			EmployeeCapServerToClientPacket syncPacket = new EmployeeCapServerToClientPacket();
			syncPacket.locationEmployee = locationEmployee;
			PacketHandler.INSTANCE.sendTo(syncPacket, (EntityPlayerMP)player);
		}
	}
}
