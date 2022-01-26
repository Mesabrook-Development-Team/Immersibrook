package com.mesabrook.ib.blocks.gui.telecom;

public enum SignalStrengths {
	unknown("icn_bar_scan.png"),
	none("icn_bar_no_coverage.png"),
	bar1("icn_bar_1.png"),
	bar2("icn_bar_2.png"),
	bar3("icn_bar_3.png"),
	bar4("icn_bar_4.png"),
	bar5("icn_bar_5.png");
	
	private String textureName;
	SignalStrengths(String textureName)
	{
		this.textureName = textureName;
	}
	
	public static SignalStrengths getForReceptionAmount(double reception)
	{
		if (reception <= 0)
		{
			return none;
		}
		
		if (reception > 0 && reception < 0.25)
		{
			return bar1;
		}
		else if (reception >= 0.25 && reception < 0.5)
		{
			return bar2;
		}
		else if (reception >= 0.5 && reception < 0.75)
		{
			return bar3;
		}
		else if (reception >= 0.75 && reception < 1)
		{
			return bar4;
		}
		else
		{
			return bar5;
		}
	}
	
	public String getTextureName()
	{
		return textureName;
	}
}
