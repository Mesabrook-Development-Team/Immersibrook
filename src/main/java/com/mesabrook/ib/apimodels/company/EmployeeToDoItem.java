package com.mesabrook.ib.apimodels.company;

import com.google.gson.annotations.SerializedName;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.INBTSerializable;

public class EmployeeToDoItem implements INBTSerializable<NBTTagCompound> {
	public enum Types
	{
		@SerializedName("0")
		PayableInvoiceWaiting,
		@SerializedName("1")
		ReceivableInvoiceWaiting,
		@SerializedName("2")
		PayablePastDueInvoice,
		@SerializedName("3")
		ReceivablePastDueInvoice,
		@SerializedName("4")
		PurchaseOrderWaitingApproval,
		@SerializedName("5")
		QuotationRequestWaiting,
		@SerializedName("6")
		RailcarAwaitingAction,
		@SerializedName("7")
		RegisterOffline,
		@SerializedName("8")
		OpenPurchaseOrders,
		@SerializedName("9")
		AutomaticPaymentsAlmostComplete
	}
	
	public enum Severities
	{
		@SerializedName("0")
		Informational,
		@SerializedName("1")
		Important,
		@SerializedName("2")
		Urgent
	}
	
	public Severities Severity;
	public Types Type;
	public String Message;
	public long CompanyID;
	public String CompanyName;
	public long LocationID;
	public String LocationName;
	public long SourceID;
	public String MesaSuiteURI;
	
	public TextFormatting getTextFormat()
	{		
		switch(Severity)
		{
			case Urgent:
				return TextFormatting.RED;
			case Important:
				return TextFormatting.YELLOW;
			case Informational:
				return TextFormatting.BLUE;
			default:
				return TextFormatting.WHITE;
		}
	}
	
	public static class Comparator implements java.util.Comparator<EmployeeToDoItem>
	{
		@Override
		public int compare(EmployeeToDoItem o1, EmployeeToDoItem o2) {
			int signum = getTypePriority(o1).compareTo(getTypePriority(o2));
			
			if (signum == 0)
			{
				signum = o1.CompanyName.compareTo(o2.CompanyName);
			}
			
			if (signum == 0)
			{
				signum = o1.Message.compareTo(o2.Message);
			}
			
			return signum;
		}
	
		private Integer getTypePriority(EmployeeToDoItem item)
		{
			switch(item.Severity)
			{
				case Urgent:
					return 0;
				case Important:
					return 1;
				case Informational:
					return 2;
				default:
					return Integer.MAX_VALUE;
			}
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong("companyID", CompanyID);
		tag.setString("companyName", CompanyName);
		tag.setLong("locationID", LocationID);
		tag.setString("locationName", LocationName);
		tag.setString("mesaSuiteURI", MesaSuiteURI);
		tag.setString("message", Message);
		tag.setLong("sourceID", SourceID);
		tag.setString("type", Type.toString());
		tag.setString("severity", Severity.toString());
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		CompanyID = nbt.getLong("companyID");
		CompanyName = nbt.getString("companyName");
		LocationID = nbt.getLong("locationID");
		LocationName = nbt.getString("locationName");
		MesaSuiteURI = nbt.getString("mesaSuiteURI");
		Message = nbt.getString("message");
		SourceID = nbt.getLong("sourceID");
		Type = Types.valueOf(nbt.getString("type"));
		Severity = Severities.valueOf(nbt.getString("severity"));
	}
}
