package com.mesabrook.ib.util;

public enum PhoneLogState {
	Outgoing(0x008800),
	Answered(0x008800),
	Ignored(0xFFFF00),
	Missed(0xFF0000),
	Failed(0xFF0000);
	
	private int color;
	public int getColor()
	{
		return color;
	}
	
	PhoneLogState(int color)
	{
		this.color = color;
	}
}