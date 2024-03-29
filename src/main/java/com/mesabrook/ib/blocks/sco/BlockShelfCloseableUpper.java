package com.mesabrook.ib.blocks.sco;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockShelfCloseableUpper extends BlockShelfCloseable {

	public BlockShelfCloseableUpper(String name, ProductPlacement[] productPlacements,
			AxisAlignedBB[] nonShelvingBoundingBoxes, AxisAlignedBB doorBB) {
		super(name, productPlacements, nonShelvingBoundingBoxes, doorBB);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.CUTOUT; }
	
	@Override
	protected void addItemBlock()
	{
		// Do nothing
	}
	
	@Override
	public void registerModels()
	{
		// Do nothing
	}
	
	@Override
	public void neighborChanged(IBlockState state, net.minecraft.world.World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		IBlockState lowerState = worldIn.getBlockState(pos.down());
		if (!(lowerState.getBlock() instanceof BlockShelfCloseable))
		{
			worldIn.destroyBlock(pos, false);
		}
		else
		{
			boolean myClosed = state.getValue(BlockShelfCloseable.CLOSED);
			boolean otherClosed = lowerState.getValue(BlockShelfCloseable.CLOSED);
			
			if (myClosed != otherClosed)
			{
				worldIn.setBlockState(pos, state.withProperty(BlockShelfCloseable.CLOSED, otherClosed));
			}
		}
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		IBlockState lowerBlock = world.getBlockState(pos.down());
		if (lowerBlock.getBlock() instanceof BlockShelfCloseableLower)
		{
			return new ItemStack(lowerBlock.getBlock());
		}
		
		return super.getPickBlock(state, target, world, pos, player);
	}
}
