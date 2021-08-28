package rz.mesabrook.wbtc.util;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import rz.mesabrook.wbtc.init.SoundInit;

import java.util.Random;

public class SoundRandomizer 
{
	public static String result;
	public static String hammerResult;
	public static String popResult;
	public static String owoResult;
	public static SoundEvent catResult;
	public static SoundEvent popResultSP;
	public static String hammerRightClick;

	public static void RandomizeSound()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(5);
		
		switch(snds)
		{
		case 0:
			result = "no";
			break;
		case 1:
			result = "safety";
			break;
		case 2:
			result = "yoink";
			break;
		case 3:
			result = "fish";
			break;
		case 4:
			result = "spree";
			break;
		}
	}

	public static void HammerRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(9);

		switch(snds)
		{
			case 0:
				hammerResult = "beaned";
				break;
			case 1:
				hammerResult = "bong";
				break;
			case 2:
				hammerResult = "bonk";
				break;
			case 3:
				hammerResult = "owie";
				break;
			case 4:
				hammerResult = "dove";
				break;
			case 5:
				hammerResult = "cpw";
				break;
			case 6:
				hammerResult = "sploot";
				break;
			case 7:
				hammerResult = "squidward";
				break;
			case 8:
				hammerResult = "reverb";
				break;
		}
	}

	public static void PopRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(2);

		switch(snds)
		{
			case 0:
				popResult = "pop";
				break;
			case 1:
				popResult = "pop_single";
				break;
		}
	}

	public static void SPPopRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(2);

		switch(snds)
		{
			case 0:
				popResultSP = SoundInit.POP;
				break;
			case 1:
				popResultSP = SoundInit.POP_SINGLE;
				break;
		}
	}

	public static void CatCubeRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(3);

		switch(snds)
		{
			case 0:
				catResult = SoundEvents.ENTITY_CAT_AMBIENT;
				break;
			case 1:
				catResult = SoundEvents.ENTITY_CAT_PURR;
				break;
			case 2:
				catResult = SoundEvents.ENTITY_CAT_PURREOW;
		}
	}

	public static void OWOTrophyRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(3);

		switch(snds)
		{
			case 0:
				owoResult = "owo";
				break;
			case 1:
				owoResult = "new_owo";
				break;
			case 2:
				owoResult = "new_owo_short";
				break;
		}
	}

	public static void HammerRightClickRandomizer()
	{
		Random chooser = new Random();
		int snds;
		snds = chooser.nextInt(5);

		switch(snds)
		{
			case 0:
				hammerRightClick = "spree";
				break;
			case 1:
				hammerRightClick = "spree";
				break;
			case 2:
				hammerRightClick = "spree";
				break;
			case 3:
				hammerRightClick = "baka";
				break;
			case 4:
				hammerRightClick = "mallet";
				break;
		}
	}
}
