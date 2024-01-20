package com.mesabrook.ib.capability.employee;

import com.mesabrook.ib.apimodels.company.LocationEmployee;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityEmployeePlayerProvider implements ICapabilitySerializable<NBTTagCompound> {

	private IEmployeeCapability capability;	
	
	public CapabilityEmployeePlayerProvider(EntityPlayer player)
	{
		capability = new IEmployeeCapability.Impl(player);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEmployee.EMPLOYEE_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEmployee.EMPLOYEE_CAPABILITY)
		{
			return CapabilityEmployee.EMPLOYEE_CAPABILITY.cast(this.capability);
		}
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		if (capability.getLocationEmployee() != null)
		{
			return capability.getLocationEmployee().serializeNBT();
		}
		
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt != null)
		{
			LocationEmployee locationEmployee = new LocationEmployee();
			locationEmployee.deserializeNBT(nbt);
			capability.setLocationEmployee(locationEmployee);
		}
	}

}
