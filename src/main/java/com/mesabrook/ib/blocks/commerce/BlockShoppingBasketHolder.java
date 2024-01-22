package com.mesabrook.ib.blocks.commerce;

import com.mesabrook.ib.blocks.ImmersiblockRotational;
import com.mesabrook.ib.blocks.te.TileEntityShoppingBasketHolder;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.ModUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockShoppingBasketHolder extends ImmersiblockRotational {
	
	public BlockShoppingBasketHolder(String name)
	{
		super(name, Material.IRON, SoundType.METAL, "pickaxe", 1, 1.25F, 2F, ModUtils.DOUBLE_AABB);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityShoppingBasketHolder();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if (heldItem.getItem() == ModItems.SHOPPING_BASKET)
		{
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityShoppingBasketHolder)
			{
				TileEntityShoppingBasketHolder holder = (TileEntityShoppingBasketHolder)te;
				if (holder.pushBasket(heldItem.copy()))
				{
					heldItem.shrink(1);
					
					return true;
				}
			}
		}
		else if (heldItem.isEmpty())
		{
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityShoppingBasketHolder)
			{
				TileEntityShoppingBasketHolder holder = (TileEntityShoppingBasketHolder)te;
				ItemStack basket = holder.popBasket();
				if (basket != null)
				{
					playerIn.setHeldItem(hand, basket);
					
					return true;
				}
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
}
