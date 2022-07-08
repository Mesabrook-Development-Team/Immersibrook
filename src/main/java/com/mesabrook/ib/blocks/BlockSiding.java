package com.mesabrook.ib.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockSiding extends ImmersiblockRotational {

	public static PropertyBool BACK = PropertyBool.create("back");
	public static PropertyBool LEFT = PropertyBool.create("left");
	public static PropertyBool RIGHT = PropertyBool.create("right");
	
	public BlockSiding(String name, Material materialIn, SoundType soundTypeIn, String harvestTool, int harvestLevel,
			float hardnessIn, float resistanceIn, AxisAlignedBB unrotatedAABB) {
		super(name, materialIn, soundTypeIn, harvestTool, harvestLevel, hardnessIn, resistanceIn, unrotatedAABB);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, BACK, LEFT, RIGHT);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		
		boolean back = worldIn.getBlockState(pos.offset(facing)).isFullBlock();
		boolean left = worldIn.getBlockState(pos.offset(facing.rotateY())).isFullBlock();
		boolean right = worldIn.getBlockState(pos.offset(facing.rotateYCCW())).isFullBlock();
		
		return state.withProperty(BACK, back)
					.withProperty(LEFT, left)
					.withProperty(RIGHT, right);
	}
}
