package com.mesabrook.ib.blocks;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mesabrook.ib.util.ModUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class ImmersiblockRotationalManyBB extends ImmersiblockRotational {

	public final ImmutableMultimap<EnumFacing, AxisAlignedBB> SUB_BOUNDING_BOXES;
	public final ImmutableMap<AxisAlignedBB, AxisAlignedBB> ROTATED_BOX_TO_ORIGINAL;
	public final ImmutableMap<EnumFacing, ImmutableMap<AxisAlignedBB, AxisAlignedBB>> ORIGINAL_BOX_TO_ROTATED_BY_FACING;
	private final boolean flipBoundingBoxes;
	
	public ImmersiblockRotationalManyBB(String name, Material materialIn, SoundType soundTypeIn, String harvestTool,
			int harvestLevel, float hardnessIn, float resistanceIn, AxisAlignedBB... subBoundingBoxes)
	{
		this(name, materialIn, soundTypeIn, harvestTool, harvestLevel, hardnessIn, resistanceIn, false, subBoundingBoxes);
	}
	
	public ImmersiblockRotationalManyBB(String name, Material materialIn, SoundType soundTypeIn, String harvestTool,
			int harvestLevel, float hardnessIn, float resistanceIn, boolean flipBoundingBoxes, AxisAlignedBB... subBoundingBoxes) {
		super(name, materialIn, soundTypeIn, harvestTool, harvestLevel, hardnessIn, resistanceIn, ModUtils.DEFAULT_AABB);
		this.flipBoundingBoxes = flipBoundingBoxes;
		
		ArrayListMultimap<EnumFacing, AxisAlignedBB> boundingBoxesByFacing = ArrayListMultimap.create();
		HashMap<AxisAlignedBB, AxisAlignedBB> rotatedToOriginal = new HashMap<>();
		HashMap<EnumFacing, HashMap<AxisAlignedBB, AxisAlignedBB>> originalBoxToRotatedByFacing = new HashMap<>();
		for(AxisAlignedBB boundingBox : subBoundingBoxes)
		{
			AxisAlignedBB rotatedBB = ModUtils.getRotatedAABB(boundingBox, EnumFacing.DOWN, false);
			boundingBoxesByFacing.put(flipBoundingBoxes ? EnumFacing.UP : EnumFacing.DOWN, rotatedBB);
			rotatedToOriginal.put(rotatedBB, boundingBox);
			addToOriginalBoxToRotatedByFacing(originalBoxToRotatedByFacing, flipBoundingBoxes ? EnumFacing.UP : EnumFacing.DOWN, rotatedBB, boundingBox);
			
            rotatedBB = ModUtils.getRotatedAABB(boundingBox, EnumFacing.UP, false);
			boundingBoxesByFacing.put(flipBoundingBoxes ? EnumFacing.DOWN : EnumFacing.UP, rotatedBB);
			rotatedToOriginal.put(rotatedBB, boundingBox);
			addToOriginalBoxToRotatedByFacing(originalBoxToRotatedByFacing, flipBoundingBoxes ? EnumFacing.DOWN : EnumFacing.UP, rotatedBB, boundingBox);
			
            rotatedBB = ModUtils.getRotatedAABB(boundingBox, EnumFacing.NORTH, false);
			boundingBoxesByFacing.put(flipBoundingBoxes ? EnumFacing.SOUTH : EnumFacing.NORTH, rotatedBB);
			rotatedToOriginal.put(rotatedBB, boundingBox);
			addToOriginalBoxToRotatedByFacing(originalBoxToRotatedByFacing, flipBoundingBoxes ? EnumFacing.SOUTH : EnumFacing.NORTH, rotatedBB, boundingBox);
			
            rotatedBB = ModUtils.getRotatedAABB(boundingBox, EnumFacing.SOUTH, false);
			boundingBoxesByFacing.put(flipBoundingBoxes ? EnumFacing.NORTH : EnumFacing.SOUTH, rotatedBB);
			rotatedToOriginal.put(rotatedBB, boundingBox);
			addToOriginalBoxToRotatedByFacing(originalBoxToRotatedByFacing, flipBoundingBoxes ? EnumFacing.NORTH : EnumFacing.SOUTH, rotatedBB, boundingBox);
			
            rotatedBB = ModUtils.getRotatedAABB(boundingBox, EnumFacing.WEST, false);
			boundingBoxesByFacing.put(flipBoundingBoxes ? EnumFacing.EAST : EnumFacing.WEST, rotatedBB);
			rotatedToOriginal.put(rotatedBB, boundingBox);
			addToOriginalBoxToRotatedByFacing(originalBoxToRotatedByFacing, flipBoundingBoxes ? EnumFacing.EAST : EnumFacing.WEST, rotatedBB, boundingBox);
			
            rotatedBB = ModUtils.getRotatedAABB(boundingBox, EnumFacing.EAST, false);
			boundingBoxesByFacing.put(flipBoundingBoxes ? EnumFacing.WEST : EnumFacing.EAST, rotatedBB);
			rotatedToOriginal.put(rotatedBB, boundingBox);
			addToOriginalBoxToRotatedByFacing(originalBoxToRotatedByFacing, flipBoundingBoxes ? EnumFacing.WEST : EnumFacing.EAST, rotatedBB, boundingBox);
		}
		
		SUB_BOUNDING_BOXES = ImmutableMultimap.copyOf(boundingBoxesByFacing);
		ROTATED_BOX_TO_ORIGINAL = ImmutableMap.copyOf(rotatedToOriginal);
		HashMap<EnumFacing, ImmutableMap<AxisAlignedBB, AxisAlignedBB>> interim = new HashMap<>();
		for(EnumFacing facing : originalBoxToRotatedByFacing.keySet())
		{
			interim.put(facing, ImmutableMap.copyOf(originalBoxToRotatedByFacing.get(facing)));
		}
		ORIGINAL_BOX_TO_ROTATED_BY_FACING = ImmutableMap.copyOf(interim);
	}
	
	private void addToOriginalBoxToRotatedByFacing(HashMap<EnumFacing, HashMap<AxisAlignedBB, AxisAlignedBB>> originalBoxToRotatedByFacing, EnumFacing facing, AxisAlignedBB rotated, AxisAlignedBB original)
	{
		if (!originalBoxToRotatedByFacing.containsKey(facing))
		{
			originalBoxToRotatedByFacing.put(facing, new HashMap<>());
		}
		
		originalBoxToRotatedByFacing.get(facing).put(original, rotated);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		SUB_BOUNDING_BOXES.get(state.getValue(FACING)).forEach(bb -> addCollisionBoxToList(pos, entityBox, collidingBoxes, bb));
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		AxisAlignedBB bb = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
		EnumFacing facing = blockState.getValue(FACING);
		if (flipBoundingBoxes)
		{
			facing = facing.getOpposite();
		}
		for(AxisAlignedBB subBB : SUB_BOUNDING_BOXES.get(facing))
		{
			bb = subBB.union(bb);
		}
		return bb;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		final Vec3d start = playerIn.getPositionEyes(hitZ);
		final Vec3d look = playerIn.getLookVec();
		final float reach = 4F;
		final Vec3d end = start.addVector(look.x * reach, look.y * reach, look.z * reach);
		
		AxisAlignedBB boxHit = null;
		double leastDistance = reach + 1;
		
		for(AxisAlignedBB subBoundingBox : SUB_BOUNDING_BOXES.get(state.getValue(FACING)))
		{
			RayTraceResult result = subBoundingBox.offset(pos).calculateIntercept(start, end);
			if (result != null)
			{
				double distance = result.hitVec.distanceTo(start);
				if (distance < leastDistance)
				{
					boxHit = subBoundingBox;
					leastDistance = distance;
				}
			}
		}
		
		if (boxHit != null)
		{
			return onSubBoundingBoxActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ, ROTATED_BOX_TO_ORIGINAL.get(boxHit));
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	public boolean onSubBoundingBoxActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, AxisAlignedBB subBoundingBox)
	{
		return false;
	}
}
