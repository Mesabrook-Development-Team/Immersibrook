package com.mesabrook.ib.blocks.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlaque extends TileEntity {
	private String plaqueId = "";
	private String awardedTo = "";
	private String awardedFor = "";
	
	public TileEntityPlaque()
	{
		super();
	}
	
	public TileEntityPlaque(String plaqueId)
	{
		super();
		this.plaqueId = plaqueId;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		plaqueId = compound.getString("plaqueId");
		awardedTo = compound.getString("awardedTo");
		awardedFor = compound.getString("awardedFor");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setString("plaqueId", plaqueId);
		compound.setString("awardedTo", awardedTo);
		compound.setString("awardedFor", awardedFor);
		return super.writeToNBT(compound);
	}

	public String getAwardedTo() {
		return awardedTo;
	}

	public void setAwardedTo(String awardedTo) {
		this.awardedTo = awardedTo;
		markDirty();
	}
	
	public String getAwardedFor()
	{
		return awardedFor;
	}
	
	public void setAwardedFor(String awardedFor)
	{
		this.awardedFor = awardedFor;
		markDirty();
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		tag.setString("awardedTo", getAwardedTo());
		tag.setString("awardedFor", getAwardedFor());
		return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		
		this.awardedTo = tag.getString("awardedTo");
		this.awardedFor = tag.getString("awardedFor");
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("awardedTo", getAwardedTo());
		tag.setString("awardedFor", getAwardedFor());
		return new SPacketUpdateTileEntity(getPos(), 0, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.awardedTo = pkt.getNbtCompound().getString("awardedTo");
		this.awardedFor = pkt.getNbtCompound().getString("awardedFor");
		super.onDataPacket(net, pkt);
	}
}
