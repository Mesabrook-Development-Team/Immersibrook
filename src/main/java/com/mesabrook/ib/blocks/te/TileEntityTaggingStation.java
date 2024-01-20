package com.mesabrook.ib.blocks.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTaggingStation extends TileEntity {

	private double distanceDefault;
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
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setDouble("distanceDefault", distanceDefault);
		return super.writeToNBT(compound);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		tag.setDouble("distanceDefault", distanceDefault);
		return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		distanceDefault = tag.getDouble("distanceDefault");
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
}
