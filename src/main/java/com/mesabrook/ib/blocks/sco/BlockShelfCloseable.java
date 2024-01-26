package com.mesabrook.ib.blocks.sco;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockShelfCloseable extends BlockShelf {

	public static final PropertyBool CLOSED = PropertyBool.create("closed");
	private final AxisAlignedBB doorBox;
	
	private final ImmutableListMultimap<EnumFacing, AxisAlignedBB> SUB_BOUNDING_BOXES_NO_DOOR;
	private final ImmutableMap<AxisAlignedBB, AxisAlignedBB> ROTATED_BOX_TO_ORIGINAL_NO_DOOR;
	private final ImmutableMap<EnumFacing, ImmutableMap<AxisAlignedBB, AxisAlignedBB>> ORIGINAL_BOX_TO_ROTATED_BY_FACING_NO_DOOR;
	
	public BlockShelfCloseable(String name, ProductPlacement[] productPlacements,
			AxisAlignedBB[] nonShelvingBoundingBoxes, AxisAlignedBB doorBB) {
		super(name, productPlacements, ArrayUtils.addAll(nonShelvingBoundingBoxes, doorBB));
		this.doorBox = doorBB;
		
		ImmutableListMultimap.Builder<EnumFacing, AxisAlignedBB> subBoundingBoxesNoDoorBuilder = new ImmutableListMultimap.Builder<>();
		for(EnumFacing facing : SUB_BOUNDING_BOXES.keySet())
		{
			ImmutableList<AxisAlignedBB> list = SUB_BOUNDING_BOXES.get(facing);
			list.forEach(aabb -> { if (ROTATED_BOX_TO_ORIGINAL.get(aabb) != doorBox) { subBoundingBoxesNoDoorBuilder.put(facing, aabb); } }); 
		}
		SUB_BOUNDING_BOXES_NO_DOOR = subBoundingBoxesNoDoorBuilder.build();
		
		ImmutableMap.Builder<AxisAlignedBB, AxisAlignedBB> rotatedBoxToOriginalNoDoorBuilder = new ImmutableMap.Builder<>();
		ROTATED_BOX_TO_ORIGINAL.forEach((key, val) -> { if(val != doorBox) { rotatedBoxToOriginalNoDoorBuilder.put(key, val); } });
		ROTATED_BOX_TO_ORIGINAL_NO_DOOR = rotatedBoxToOriginalNoDoorBuilder.build();
		
		ImmutableMap.Builder<EnumFacing, ImmutableMap<AxisAlignedBB, AxisAlignedBB>> originalBoxToRotatedByFacingNoDoorBuilder = new ImmutableMap.Builder<>();
		for(EnumFacing facing : ORIGINAL_BOX_TO_ROTATED_BY_FACING.keySet())
		{
			ImmutableMap<AxisAlignedBB, AxisAlignedBB> originalToRotatedMap = ORIGINAL_BOX_TO_ROTATED_BY_FACING.get(facing);
			ImmutableMap.Builder<AxisAlignedBB, AxisAlignedBB> originalToRotatedBuilder = new ImmutableMap.Builder<>();
			originalToRotatedMap.forEach((key, val) -> { if (key != doorBox) { originalToRotatedBuilder.put(key, val); } });
			originalBoxToRotatedByFacingNoDoorBuilder.put(facing, originalToRotatedBuilder.build());
		}
		ORIGINAL_BOX_TO_ROTATED_BY_FACING_NO_DOOR = originalBoxToRotatedByFacingNoDoorBuilder.build();
	}
	
	@Override
	public boolean onSubBoundingBoxActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, AxisAlignedBB subBoundingBox) {
		if (state.getBlock() instanceof BlockShelfCloseable)
		{
			boolean isClosed = state.getValue(CLOSED);
			if ((isClosed && subBoundingBox == doorBox) || (!isClosed && playerIn.isSneaking()))
			{
				worldIn.setBlockState(pos, state.withProperty(CLOSED, !isClosed), 3);
				
				if (!worldIn.isRemote)
				{
					ServerSoundBroadcastPacket.playIBSound(worldIn, isClosed ? "fridge_open" : "fridge_close", pos, true);
				}
				
				return true;
			}
		}
		return super.onSubBoundingBoxActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ, subBoundingBox);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		BlockStateContainer container = super.createBlockState();
		BlockStateContainer.Builder builder = new BlockStateContainer.Builder(this);
		container.getProperties().forEach(p -> builder.add(p));
		builder.add(CLOSED);
		return builder.build();
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(CLOSED, true);
	}
	
	@Override
	public AxisAlignedBB getOriginalBoxFromRotated(IBlockState state, AxisAlignedBB rotated) {
		if (state.getBlock() instanceof BlockShelfCloseable && !state.getValue(CLOSED))
		{
			return ROTATED_BOX_TO_ORIGINAL_NO_DOOR.get(rotated);
		}
		
		return super.getOriginalBoxFromRotated(state, rotated);
	}
	
	@Override
	public ImmutableList<AxisAlignedBB> getRotatedSubBoundingBoxByFacing(IBlockState state, EnumFacing facing) {
		if (state.getBlock() instanceof BlockShelfCloseable && !state.getValue(CLOSED))
		{
			return SUB_BOUNDING_BOXES_NO_DOOR.get(facing);
		}
		
		return super.getRotatedSubBoundingBoxByFacing(state, facing);
	}
	
	@Override
	public ImmutableMap<AxisAlignedBB, AxisAlignedBB> getOriginalToRotatedByFacing(IBlockState state,
			EnumFacing facing) {
		if (state.getBlock() instanceof BlockShelfCloseable && !state.getValue(CLOSED))
		{
			return ORIGINAL_BOX_TO_ROTATED_BY_FACING_NO_DOOR.get(facing);
		}
		
		return super.getOriginalToRotatedByFacing(state, facing);
	}
}
