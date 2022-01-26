package com.mesabrook.ib.util;

import net.minecraft.util.text.TextComponentTranslation;

import java.util.Random;

public class TooltipRandomizer 
{
	
	public static String result;
	
	public static void ChosenTooltip()
	{
		Random chooser = new Random();
		int lines;
		lines = chooser.nextInt(20);
		
		switch(lines)
		{
		case 1:
			result = new TextComponentTranslation("im.tooltip.foodcube.1").getFormattedText();
			break;
		case 2:
			result = new TextComponentTranslation("im.tooltip.foodcube.2").getFormattedText();
			break;
		case 3:
			result = new TextComponentTranslation("im.tooltip.foodcube.3").getFormattedText();
			break;
		case 4:
			result = new TextComponentTranslation("im.tooltip.foodcube.4").getFormattedText();
			break;
		case 5:
			result = new TextComponentTranslation("im.tooltip.foodcube.5").getFormattedText();
			break;
		case 6:
			result = new TextComponentTranslation("im.tooltip.foodcube.6").getFormattedText();
			break;
		case 7:
			result = new TextComponentTranslation("im.tooltip.foodcube.7").getFormattedText();
			break;
		case 8:
			result = new TextComponentTranslation("im.tooltip.foodcube.8").getFormattedText();
			break;
		case 9:
			result = new TextComponentTranslation("im.tooltip.foodcube.9").getFormattedText();
			break;
		case 10:
			result = new TextComponentTranslation("im.tooltip.foodcube.10").getFormattedText();
			break;
		case 11:
			result = new TextComponentTranslation("im.tooltip.foodcube.11").getFormattedText();
			break;
		case 12:
			result = new TextComponentTranslation("im.tooltip.foodcube.12").getFormattedText();
			break;
		case 13:
			result = new TextComponentTranslation("im.tooltip.foodcube.13").getFormattedText();
			break;
		case 14:
			result = new TextComponentTranslation("im.tooltip.foodcube.14").getFormattedText();
			break;
		case 15:
			result = new TextComponentTranslation("im.tooltip.foodcube.15").getFormattedText();
			break;
		case 16:
			result = new TextComponentTranslation("im.tooltip.foodcube.16").getFormattedText();
			break;
		case 17:
			result = new TextComponentTranslation("im.tooltip.foodcube.17").getFormattedText();
			break;
		case 18:
			result = new TextComponentTranslation("im.tooltip.foodcube.18").getFormattedText();
			break;
		case 19:
			result = new TextComponentTranslation("im.tooltip.foodcube.19").getFormattedText();
			break;
		case 20:
			result = new TextComponentTranslation("im.tooltip.foodcube.20").getFormattedText();
			break;
		}
	}
}
