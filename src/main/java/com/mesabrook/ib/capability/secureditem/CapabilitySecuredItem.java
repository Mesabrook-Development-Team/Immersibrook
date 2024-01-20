package com.mesabrook.ib.capability.secureditem;

import com.mesabrook.ib.apimodels.company.Company;
import com.mesabrook.ib.apimodels.company.Location;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilitySecuredItem {

	@CapabilityInject(ISecuredItem.class)
	public static Capability<ISecuredItem> SECURED_ITEM_CAPABILITY;

	public static void init()
	{
		CapabilityManager.INSTANCE.register(ISecuredItem.class, new Storage(), ISecuredItem.Impl::new);
	}
	
	public static class Storage implements IStorage<ISecuredItem>
	{
		@Override
		public NBTBase writeNBT(Capability<ISecuredItem> capability, ISecuredItem instance, EnumFacing side) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setLong("homeLocation", instance.getHomeLocation().toLong());
			tag.setLong("locationIDOwner", instance.getLocationIDOwner());
			tag.setString("locationNameOwner", instance.getLocationNameOwner());
			tag.setTag("innerStack", instance.getInnerStack().serializeNBT());
			tag.setInteger("homeSpot", instance.getHomeSpot());
			tag.setDouble("resetDistance", instance.getResetDistance());
			return tag;
		}

		@Override
		public void readNBT(Capability<ISecuredItem> capability, ISecuredItem instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound compound = (NBTTagCompound)nbt;
			instance.setHomeLocation(BlockPos.fromLong(compound.getLong("homeLocation")));
			Location newLocation = new Location();
			newLocation.LocationID = compound.getLong("locationIDOwner");
			newLocation.Company = new Company();
			newLocation.Company.Name = compound.getString("locationNameOwner");
			instance.setLocation(newLocation);
			instance.setInnerStack(new ItemStack(compound.getCompoundTag("innerStack")));
			instance.setHomeSpot(compound.getInteger("homeSpot"));
			instance.setResetDistance(compound.getDouble("resetDistance"));
		}
		
	}
}
