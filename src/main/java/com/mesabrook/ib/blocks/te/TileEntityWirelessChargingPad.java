package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.util.ISimpleInventory;
import com.mesabrook.ib.util.config.ModConfig;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityWirelessChargingPad extends TileEntitySyncClient implements ISimpleInventory, ITickable
{
    private ItemStack phoneItem = ItemStack.EMPTY;
    private int rotation = 0;
    private int energy = 0;
    private final ChargingPadEnergy capEnergy;
    
    public TileEntityWirelessChargingPad()
    {
    	capEnergy = new ChargingPadEnergy();
    }

    public void setPhone(ItemStack phoneIn)
    {
        this.phoneItem = phoneIn;
        if (this.phoneItem == null)
        {
        	this.phoneItem = ItemStack.EMPTY;
        }
        markDirty();
    }

    public ItemStack getPhoneItem()
    {
        return phoneItem;
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
        markDirty();
    }

    public int getRotation()
    {
        return rotation;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.setPhone(ItemStack.EMPTY);
        if(tagCompound.hasKey("Items", Constants.NBT.TAG_LIST))
        {
            NBTTagList tagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < tagList.tagCount(); ++i)
            {
                NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
                ItemStack stack = new ItemStack(itemTag);
                this.setPhone(stack);
            }
        }
        else if(tagCompound.hasKey("Item", Constants.NBT.TAG_COMPOUND))
        {
            this.setPhone(new ItemStack(tagCompound.getCompoundTag("Item")));
        }
        this.rotation = tagCompound.getInteger("Rotation");
        this.energy = tagCompound.getInteger("Energy");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        if(this.phoneItem != null)
        {
            tagCompound.setTag("Item", this.phoneItem.writeToNBT(new NBTTagCompound()));
        }
        tagCompound.setInteger("Rotation", this.rotation);
        tagCompound.setInteger("Energy", this.energy);
        return tagCompound;
    }

    @Override
    public int getSize()
    {
        return 1;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return getPhoneItem();
    }

    @Override
    public void clear()
    {
        phoneItem = null;
    }
    
    public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
		markDirty();
		
		if (world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return facing == EnumFacing.DOWN && capability == CapabilityEnergy.ENERGY;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (facing == EnumFacing.DOWN && capability == CapabilityEnergy.ENERGY)
		{
			return CapabilityEnergy.ENERGY.cast(capEnergy);
		}
		
		return null;
	}
	
	public EnumChargingPadState getChargePadState()
	{
		if (getEnergy() <= 0)
		{
			return EnumChargingPadState.NoPower;
		}
		
		if (getPhoneItem().isEmpty())
		{
			return EnumChargingPadState.Standby;
		}
		
		ItemPhone.NBTData data = ItemPhone.NBTData.getFromItemStack(getPhoneItem());
		return data.getBatteryLevel() >= ModConfig.smartphoneMaxBattery ? EnumChargingPadState.ChargeComplete : EnumChargingPadState.Charging;
	}
	
	@Override
	public void update() {
		if (world.isRemote || getPhoneItem().isEmpty() || !getPhoneItem().hasCapability(CapabilityEnergy.ENERGY, null) || getEnergy() <= 0)
		{
			return;
		}
		
		IEnergyStorage phoneEnergyStorage = getPhoneItem().getCapability(CapabilityEnergy.ENERGY, null);
		if (phoneEnergyStorage.getEnergyStored() >= phoneEnergyStorage.getMaxEnergyStored())
		{
			return;
		}
		
		int energyToSend = Math.min(20, getEnergy());
		setEnergy(getEnergy() - phoneEnergyStorage.receiveEnergy(energyToSend, false));
	}
	
	public class ChargingPadEnergy implements IEnergyStorage
    {

		@Override
		public int receiveEnergy(int maxReceive, boolean simulate) {
			int max = Math.min(maxReceive, 20);
			int toReceive = max + getEnergyStored() > 50 ? 50 - getEnergyStored() : max;
			
			if (!simulate && toReceive > 0)
			{
				setEnergy(getEnergyStored() + toReceive);
			}
			
			return toReceive;
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate) {
			return 0;
		}

		@Override
		public int getEnergyStored() {
			return getEnergy();
		}

		@Override
		public int getMaxEnergyStored() {
			return 50;
		}

		@Override
		public boolean canExtract() {
			return false;
		}

		@Override
		public boolean canReceive() {
			return true;
		}
    	
    }

	public enum EnumChargingPadState implements IStringSerializable
	{
		NoPower("nopower"),
		Standby("standby"),
		Charging("charging"),
		ChargeComplete("chargecomplete");
		
		private final String name;		
		private EnumChargingPadState(String name) {
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}
}
