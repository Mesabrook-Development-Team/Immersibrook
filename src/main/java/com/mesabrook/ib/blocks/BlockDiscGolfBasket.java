package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityDiscGolfBasket;
import com.mesabrook.ib.items.misc.ItemDiscGolf;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDiscGolfBasket extends Immersiblock {

	public BlockDiscGolfBasket() {
		super("disc_golf_basket", Material.IRON, SoundType.METAL, Main.IMMERSIBROOK_MAIN, 0F);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!(playerIn.getHeldItem(hand).getItem() instanceof ItemDiscGolf)) // Player could be trying to toss a disc in from close range
		{
			playerIn.openGui(Main.instance, Reference.GUI_DISC_GOLF_BASKET, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityDiscGolfBasket();
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
