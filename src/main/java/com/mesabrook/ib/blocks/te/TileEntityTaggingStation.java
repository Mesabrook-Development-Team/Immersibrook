package com.mesabrook.ib.blocks.te;

import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.init.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityTaggingStation extends TileEntity {

	private double distanceDefault;
	private final ItemStackHandler securityBoxHandler;
	
	public TileEntityTaggingStation()
	{
		securityBoxHandler = new ItemStackHandler(1)
		{
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return stack.getItem() == ModItems.SECURITY_BOX &&
						stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) &&
						stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack().isEmpty();
			}
			
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				markDirty();
			}
		};
	}
	
	public double getDistanceDefault() {
		return distanceDefault;
	}

	public void setDistanceDefault(double distanceDefault) {
		this.distanceDefault = distanceDefault;
		markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		distanceDefault = compound.getDouble("distanceDefault");
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(securityBoxHandler, null, compound.getTag("security_box_inv"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setDouble("distanceDefault", distanceDefault);
		compound.setTag("security_box_inv", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(securityBoxHandler, null));
		return super.writeToNBT(compound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		tag.setDouble("distanceDefault", distanceDefault);
		tag.setTag("security_box_inv", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(securityBoxHandler, null));
		return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		distanceDefault = tag.getDouble("distanceDefault");
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(securityBoxHandler, null, tag.getTag("security_box_inv"));
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	public IItemHandler getTaggingStationInventory()
	{
		return securityBoxHandler;
	}
}
