package com.mesabrook.ib.apimodels.mesasys;

import java.util.Date;

import javax.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class BlockAudit {
	@Nullable
	public Long BlockAuditID;
	public Date AuditTime;
	public int PositionX;
	public int PositionY;
	public int PositionZ;
	public String BlockName;
	public String PlayerName;
	
	public enum AuditTypes
	{
		@SerializedName("1")
		Place,
		@SerializedName("2")
		Break,
		@SerializedName("3")
		Use
	}
	
	public AuditTypes AuditType;
}
