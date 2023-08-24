package com.mesabrook.ib.blocks.sco;

import net.minecraft.util.math.AxisAlignedBB;

public class ProductPlacement {
	private int placementID;
	private int spaces;
	private AxisAlignedBB boundingBox;
	
	public ProductPlacement(int placementID, int spaces, AxisAlignedBB boundingBox) {
		this.placementID = placementID;
		this.spaces = spaces;
		this.boundingBox = boundingBox;
	}
	
	public int getPlacementID()
	{
		return placementID;
	}
	
	public void setPlacementID(int placementID)
	{
		this.placementID = placementID;
	}
	
	public int getSpaces() {
		return spaces;
	}
	public void setSpaces(int spaces) {
		this.spaces = spaces;
	}
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	
}
