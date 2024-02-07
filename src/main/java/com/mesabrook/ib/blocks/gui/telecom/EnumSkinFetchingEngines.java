package com.mesabrook.ib.blocks.gui.telecom;

import java.util.HashMap;

public enum EnumSkinFetchingEngines {
	MCHeads((byte)0, "mcheads"),
	Crafatar((byte)1, "crafatar");
	
	private final byte engineID;
	private final String name;
	private static HashMap<Byte, EnumSkinFetchingEngines> ENGINES = new HashMap<>();
	
	static
	{
		for(EnumSkinFetchingEngines engine : values())
		{
			ENGINES.put(engine.getEngineID(), engine);
		}
	}
	
	EnumSkinFetchingEngines(byte engineID, String name)
	{
		this.engineID = engineID;
		this.name = name;
	}
	
	public byte getEngineID()
	{
		return this.engineID;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public static EnumSkinFetchingEngines byEngineID(byte engineID)
	{
		return ENGINES.get(engineID);
	}
}
