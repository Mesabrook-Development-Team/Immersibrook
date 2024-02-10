package com.mesabrook.ib.capability.employee;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@EventBusSubscriber
public class CapabilityEmployee {
	@CapabilityInject(IEmployeeCapability.class)
	public static Capability<IEmployeeCapability> EMPLOYEE_CAPABILITY = null;
	
	public static void init()
	{
		CapabilityManager.INSTANCE.register(IEmployeeCapability.class, new Storage(), () -> new IEmployeeCapability.Impl(null));
	}
	
	public static class Storage implements IStorage<IEmployeeCapability>
	{

		@Override
		public NBTBase writeNBT(Capability<IEmployeeCapability> capability, IEmployeeCapability instance,
				EnumFacing side) {
			if (capability != EMPLOYEE_CAPABILITY)
			{
				return null;
			}
			
			return instance.getLocationEmployee().serializeNBT();
		}

		@Override
		public void readNBT(Capability<IEmployeeCapability> capability, IEmployeeCapability instance, EnumFacing side,
				NBTBase nbt) {
			if (capability != EMPLOYEE_CAPABILITY)
			{
				return;
			}
			
			if (instance.getLocationEmployee() != null)
			{
				instance.getLocationEmployee().deserializeNBT((NBTTagCompound)nbt);
			}
		}
		
	}
	
	@SubscribeEvent
	public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent e)
	{
		IEmployeeCapability cap = e.player.getCapability(EMPLOYEE_CAPABILITY, null);
		cap.serverToClientSync();
	}
	
	@SubscribeEvent
	public static void playerChangedDimensions(PlayerEvent.PlayerChangedDimensionEvent e)
	{
		IEmployeeCapability cap = e.player.getCapability(EMPLOYEE_CAPABILITY, null);
		cap.serverToClientSync();
	}
}
