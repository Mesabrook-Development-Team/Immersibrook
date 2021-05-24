package rz.mesabrook.wbtc.util;

import java.util.Random;

import net.minecraft.util.SoundEvent;
import rz.mesabrook.wbtc.init.SoundInit;

public class SoundRandomizer 
{
	public static String result;
	
	public static void RandomizeSound()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(5);
		
		switch(snds)
		{
		case 1:
			result = "no";
			break;
		case 2:
			result = "safety";
			break;
		case 3:
			result = "cheese_click";
			break;
		case 4:
			result = "fish";
			break;
		}
	}
}
