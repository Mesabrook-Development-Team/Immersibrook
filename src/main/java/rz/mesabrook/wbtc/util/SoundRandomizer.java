package rz.mesabrook.wbtc.util;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import rz.mesabrook.wbtc.init.SoundInit;

import java.util.Random;

/**
 * Uses a randomizer to randomly select a sound from Immersibrook's custom sounds and then feeds
 * its ID into the packet-based sound system.
 */
public class SoundRandomizer 
{
	public static String result;
	public static String hammerResult;
	public static String popResult;
	public static SoundEvent catResult;
	public static SoundEvent popResultSP;

	public static void RandomizeSound()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(6);
		
		switch(snds)
		{
		case 1:
			result = "no";
			break;
		case 2:
			result = "safety";
			break;
		case 3:
			result = "yoink";
			break;
		case 4:
			result = "fish";
			break;
		case 5:
			result = "spree";
			break;
		}
	}

	public static void HammerRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(7);

		switch(snds)
		{
			case 1:
				hammerResult = "beaned";
				break;
			case 2:
				hammerResult = "bong";
				break;
			case 3:
				hammerResult = "bonk";
				break;
			case 4:
				hammerResult = "beaned";
				break;
			case 5:
				hammerResult = "owie";
				break;
			case 6:
				hammerResult = "dove";
				break;
		}
	}

	public static void PopRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(3);

		switch(snds)
		{
			case 1:
				popResult = "pop";
				break;
			case 2:
				popResult = "pop_single";
				break;
		}
	}

	public static void SPPopRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(3);

		switch(snds)
		{
			case 1:
				popResultSP = SoundInit.POP;
				break;
			case 2:
				popResultSP = SoundInit.POP_SINGLE;
				break;
		}
	}

	public static void CatCubeRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(4);

		switch(snds)
		{
			case 1:
				catResult = SoundEvents.ENTITY_CAT_AMBIENT;
				break;
			case 2:
				catResult = SoundEvents.ENTITY_CAT_PURR;
				break;
			case 3:
				catResult = SoundEvents.ENTITY_CAT_PURREOW;
		}
	}
}
