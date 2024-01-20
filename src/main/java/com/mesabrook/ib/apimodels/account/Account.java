package com.mesabrook.ib.apimodels.account;

import java.math.BigDecimal;

import com.mesabrook.ib.apimodels.company.Company;
import com.mesabrook.ib.apimodels.gov.Government;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class Account implements INBTSerializable<NBTTagCompound> {

	public long AccountID;
	public String Description;
	public String AccountNumber;
	public BigDecimal Balance;
	public long CompanyID;
	public Company Company;
	public long GovernmentID;
	public Government Government;
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong("AccountID", AccountID);
		tag.setString("Description", Description == null ? "" : Description);
		tag.setString("AccountNumber", AccountNumber);
		tag.setString("Balance", Balance.toPlainString());
		tag.setLong("CompanyID", CompanyID);
		tag.setString("Company.Name", Company.Name == null ? "" : Company.Name);
		tag.setLong("GovernmentID", GovernmentID);
		tag.setString("Government.Name", Government.Name == null ? "" : Government.Name);
		
		return tag;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		AccountID = nbt.getLong("AccountID");
		Description = nbt.getString("Description");
		AccountNumber = nbt.getString("AccountNumber");
		Balance = new BigDecimal(nbt.getString("Balance"));
		CompanyID = nbt.getLong("CompanyID");
		Company = new Company();
		Company.Name = nbt.getString("Company.Name");
		GovernmentID = nbt.getLong("GovernmentID");
		Government = new Government();
		Government.Name = nbt.getString("Government.Name");
	}
}
