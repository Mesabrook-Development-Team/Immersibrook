package com.mesabrook.ib.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.net.URI;

/**
** Immersibrook Utilities Class
**/

public class ModUtils 
{
	// Default AABB
	public static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, 1D);
	public static final AxisAlignedBB DOUBLE_AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 2D, 1D);

	public static void dropTileEntityInventoryItems(World world, BlockPos pos, TileEntity te)
	{
		if(te instanceof ISimpleInventory)
		{
			dropInventoryItems(world, pos, (ISimpleInventory) te);
		}
		if(te instanceof IInventory)
		{
			InventoryHelper.dropInventoryItems(world, pos, (IInventory) te);
		}
	}

	public static void dropInventoryItems(World world, BlockPos pos, ISimpleInventory inv)
	{
		for(int i = 0; i < inv.getSize(); i++)
		{
			ItemStack stack = inv.getItem(i);

			if(stack != null && !stack.isEmpty())
			{
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
		}
	}


	/**
	 * A basic handler that allows us to open the user's default browser and navigate to the provided URL.
	 *
	 * @param url URI
	 */
	public static void openWebLink(URI url)
	{
		try
		{
			Class<?> oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
			oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * GUI Text Truncating Utility.
	 *
	 * @param input Input String
	 * @param overflow Overflow Character
	 * @param maxChars Maximum Limit before Truncation Int.
	 * @return
	 */
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

	/**
	 * Pixelated AxisAlignedBB Utility.
	 * Allows you to create an AABB using pixel measurements.
	 *
	 * @param x0
	 * @param y0
	 * @param z0
	 * @param x1
	 * @param y1
	 * @param z1
	 * @return
	 */
	public static final AxisAlignedBB getPixelatedAABB(double x0, double y0, double z0, double x1, double y1, double z1)
	{
		return new AxisAlignedBB(x0/16.0, y0/16.0, z0/16.0, x1/16.0, y1/16.0, z1/16.0);
	}

	/**
	 * Rotated AxisAlignedBB Utility
	 * Allows you to rotate your AABB easily for blocks with rotational variants.
	 *
	 * @param bb
	 * @param new_facing
	 * @param horizontal_rotation
	 * @return
	 */
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
