package rz.mesabrook.wbtc.util;

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
			result = "A Complicatedly Complex Culinary Creation";
			break;
		case 2:
			result = "If you eat this cube whole and record it, we'll send you $100";
			break;
		case 3:
			result = "Don't ask how this is held together...";
			break;
		case 4:
			result = "If you stare into this block long enough, it'll stare back.";
			break;
		case 5:
			result = "Don't eat this block, it'll destroy your teeth.";
			break;
		case 6:
			result = "Made with 95% real meat!";
			break;
		case 7:
			result = "Why does it have a face?";
			break;
		case 8:
			result = "Yeet That Meat! MEATMEATMEATMEATMEATMEATMEATMEAT";
			break;
		case 9:
			result = "Refrigerate After Opening";
			break;
		case 10:
			result = "Exposure to the Food Cube may cause headaches, fever, upset stomach, and uncontrollable gas.";
			break;
		case 11:
			result = "Consult with your physician before starting Food Cube.";
			break;
		case 12:
			result = "They said it couldn't be done, that it was impossible. Yet here we are now.";
			break;
		case 13:
			result = "Try our other cube product: The Companion Cube!";
			break;
		case 14:
			result = "Food Cubes make excellent pets!";
			break;
		case 15:
			result = "Food Cubes are a registered trademark of the RZ Foodstuffs Company. All Rights Reserved.";
			break;
		case 16:
			result = "Safe for Levo and Dextro consumers!";
			break;
		case 17:
			result = "c u b e";
			break;
		case 18:
			result = "This cube took generations of scientists to develop.";
			break;
		case 19:
			result = "Do not drop the Food Cube. Doing so may hurt its feelings :(";
			break;
		case 20:
			result = "Proudly Made in Mesabrook!";
			break;
		}
	}
}
