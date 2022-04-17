package com.mesabrook.ib.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Scanner;

/**
** This class provides extra information
**/

public class ModUtils 
{
	public static String truncator(String input, String overflow, int maxChars)
	{
		if(input.length() > maxChars)
		{
			if(overflow == null)
			{
				overflow = "...";
			}
			return input.substring(0, maxChars - overflow.length()) + overflow;
		}
		return input;
	}

	public static final AxisAlignedBB getPixelatedAABB(double x0, double y0, double z0, double x1, double y1, double z1)
	{
		return new AxisAlignedBB(x0/16.0, y0/16.0, z0/16.0, x1/16.0, y1/16.0, z1/16.0);
	}
	
	  public static final AxisAlignedBB getRotatedAABB(AxisAlignedBB bb, EnumFacing new_facing, boolean horizontal_rotation)
	  {
	    if(!horizontal_rotation)
	    {
	      switch(new_facing.getIndex())
	      {
	        case 0: return new AxisAlignedBB(1-bb.maxX, 1-bb.maxZ, 1-bb.maxY, 1-bb.minX, 1-bb.minZ, 1-bb.minY); // D
	        case 1: return new AxisAlignedBB(1-bb.maxX,   bb.minZ,   bb.minY, 1-bb.minX,   bb.maxZ,   bb.maxY); // U
	        case 2: return new AxisAlignedBB(1-bb.maxX,   bb.minY, 1-bb.maxZ, 1-bb.minX,   bb.maxY, 1-bb.minZ); // N
	        case 3: return new AxisAlignedBB(  bb.minX,   bb.minY,   bb.minZ,   bb.maxX,   bb.maxY,   bb.maxZ); // S --> bb
	        case 4: return new AxisAlignedBB(1-bb.maxZ,   bb.minY,   bb.minX, 1-bb.minZ,   bb.maxY,   bb.maxX); // W
	        case 5: return new AxisAlignedBB(  bb.minZ,   bb.minY, 1-bb.maxX,   bb.maxZ,   bb.maxY, 1-bb.minX); // E
	      }
	    }
	    else
	    {
	      switch(new_facing.getIndex())
	      {
	        case 0: return new AxisAlignedBB(  bb.minX, bb.minY,   bb.minZ,   bb.maxX, bb.maxY,   bb.maxZ); // D --> bb
	        case 1: return new AxisAlignedBB(  bb.minX, bb.minY,   bb.minZ,   bb.maxX, bb.maxY,   bb.maxZ); // U --> bb
	        case 2: return new AxisAlignedBB(  bb.minX, bb.minY,   bb.minZ,   bb.maxX, bb.maxY,   bb.maxZ); // N --> bb
	        case 3: return new AxisAlignedBB(1-bb.maxX, bb.minY, 1-bb.maxZ, 1-bb.minX, bb.maxY, 1-bb.minZ); // S
	        case 4: return new AxisAlignedBB(  bb.minZ, bb.minY, 1-bb.maxX,   bb.maxZ, bb.maxY, 1-bb.minX); // W
	        case 5: return new AxisAlignedBB(1-bb.maxZ, bb.minY,   bb.minX, 1-bb.minZ, bb.maxY,   bb.maxX); // E
	      }
	    }
	    return bb;
	  }
}
