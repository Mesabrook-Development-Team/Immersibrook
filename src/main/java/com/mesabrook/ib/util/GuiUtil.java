package com.mesabrook.ib.util;

public class GuiUtil {
	public static boolean isPointWithinBounds(int pointX, int pointY, int x, int y, int width, int height)
	{
		return pointX >= x && pointX <= x + width && pointY >= y && pointY <= y + height;
	}
}
