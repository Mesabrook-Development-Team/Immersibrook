package com.mesabrook.ib.blocks.te;

import java.math.BigDecimal;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityATM extends TileEntity {
	private long companyIDOwner;
	private BigDecimal cardChargeAmount = new BigDecimal(0);
	private String accountRevenue = "";
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		companyIDOwner = compound.getLong("CompanyIDOwner");
		accountRevenue = compound.getString("AccountRevenue");
		if (compound.hasKey("cardChargeAmount"))
		{
			cardChargeAmount = new BigDecimal(compound.getString("cardChargeAmount"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("CompanyIDOwner", companyIDOwner);
		compound.setString("AccountRevenue", accountRevenue);
		compound.setString("cardChargeAmount", cardChargeAmount.toPlainString());
		return super.writeToNBT(compound);
	}

	public long getCompanyIDOwner() {
		return companyIDOwner;
	}

	public void setCompanyIDOwner(long companyIDOwner) {
		this.companyIDOwner = companyIDOwner;
		markDirty();
	}

	public String getAccountRevenue() {
		return accountRevenue;
	}

	public void setAccountRevenue(String accountRevenue) {
		this.accountRevenue = accountRevenue;
		markDirty();
	}
	
	public BigDecimal getCardChargeAmount() {
		return cardChargeAmount;
	}

	public void setCardChargeAmount(BigDecimal cardChargeAmount) {
		this.cardChargeAmount = cardChargeAmount;
		markDirty();
	}

	// Sync stuff
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		tag.setLong("CompanyIDOwner", companyIDOwner);
		tag.setString("cardChargeAmount", cardChargeAmount.toPlainString());
		return tag;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		companyIDOwner = tag.getLong("CompanyIDOwner");
		cardChargeAmount = new BigDecimal(tag.getString("cardChargeAmount"));
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag()); 
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
}
