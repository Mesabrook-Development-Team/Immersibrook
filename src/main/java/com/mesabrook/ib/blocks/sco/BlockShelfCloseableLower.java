package com.mesabrook.ib.blocks.sco;

import com.mesabrook.ib.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockShelfCloseableLower extends BlockShelfCloseable {

	private final BlockShelfCloseableUpper upperBlock;
	public BlockShelfCloseableLower(String name, ProductPlacement[] productPlacements,
			AxisAlignedBB[] nonShelvingBoundingBoxes, AxisAlignedBB doorBB, BlockShelfCloseableUpper upperBlock) {
		super(name, productPlacements, nonShelvingBoundingBoxes, doorBB);
		this.upperBlock = upperBlock;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() { return BlockRenderLayer.CUTOUT; }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		IBlockState upperState = worldIn.getBlockState(pos.up());
		if (!(upperState.getBlock() instanceof BlockShelfCloseable))
		{
			worldIn.destroyBlock(pos, false);
		}
		else
		{
			boolean myClosed = state.getValue(BlockShelfCloseable.CLOSED);
			boolean otherClosed = upperState.getValue(BlockShelfCloseable.CLOSED);
			
			if (myClosed != otherClosed)
			{
				worldIn.setBlockState(pos, state.withProperty(BlockShelfCloseable.CLOSED, otherClosed), 3);
			}
		}
		
		super.neighborChanged(upperState, worldIn, pos, blockIn, fromPos);
	}
	
	@Override
	protected void addItemBlock()
	{
		Item itemBlock = new ItemBlock(this)
		{
			public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
			{
				if (!world.getBlockState(pos.up()).getBlock().isReplaceable(world, pos.up()))
				{
					return false;
				}
				
				return super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
			}
		}.setRegistryName(this.getRegistryName());
		ModItems.ITEMS.add(itemBlock);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		if (worldIn.getBlockState(pos) == state)
		{
			IBlockState newUpperBlockState = upperBlock.getDefaultState().withProperty(BlockShelf.FACING, state.getValue(BlockShelf.FACING));
			worldIn.setBlockState(pos.up(), newUpperBlockState, 3);
			newUpperBlockState.getBlock().onBlockPlacedBy(worldIn, pos.up(), newUpperBlockState, placer, stack);
		}
	}

}
