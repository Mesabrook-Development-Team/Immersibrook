package com.mesabrook.ib.apimodels.company;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class RegisterStatus {
	public Long RegisterStatusID;
	public Date ChangeTime;
	public Statuses Status;
	public String Initiator;
	
	public enum Statuses
	{
		@SerializedName("0")
		Offline,
		@SerializedName("1")
		InternalStorageFull,
		@SerializedName("2")
		Online;
	}
}
