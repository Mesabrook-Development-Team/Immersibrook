package com.mesabrook.ib.capability.secureditem;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public interface ISecuredItem {
	BlockPos getHomeLocation();
	void setHomeLocation(BlockPos homeLocation);
	long getLocationIDOwner();
	void setLocationIDOwner(long locationIDOwner);
	ItemStack getInnerStack();
	void setInnerStack(ItemStack stack);
	int getHomeSpot();
	void setHomeSpot(int spot);
	double getResetDistance();
	void setResetDistance(double distance);
	
	public static class Impl implements ISecuredItem
	{
		private BlockPos homeLocation;
		private int homeSpot;
		private long locationIDOwner;
		ItemStack innerStack;
		private double resetDistance;

		@Override
		public BlockPos getHomeLocation() {
			if (homeLocation == null)
			{
				return new BlockPos(0, -1, 0);
			}
			
			return homeLocation;
		}

		@Override
		public void setHomeLocation(BlockPos homeLocation) {
			this.homeLocation = homeLocation;
		}

		@Override
		public long getLocationIDOwner() {
			return locationIDOwner;
		}

		@Override
		public void setLocationIDOwner(long locationIDOwner) {
			this.locationIDOwner = locationIDOwner;
		}

		@Override
		public ItemStack getInnerStack() {
			if (innerStack == null)
			{
				return ItemStack.EMPTY;
			}
			return innerStack;
		}

		@Override
		public void setInnerStack(ItemStack stack) {
			innerStack = stack;
		}

		@Override
		public int getHomeSpot() {
			return homeSpot;
		}

		@Override
		public void setHomeSpot(int spot) {
			homeSpot = spot;
		}

		public double getResetDistance() {
			return resetDistance;
		}

		public void setResetDistance(double resetDistance) {
			this.resetDistance = resetDistance;
		}
		
		
	}
}
