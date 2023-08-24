package com.mesabrook.ib.blocks;

import java.util.Random;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockRegister extends ImmersiblockRotationalManyBB {	
	public static AxisAlignedBB monitorBoundingBox = new AxisAlignedBB(0.18, 0, 0.165, 0.812, 0.73, 0.68);
	public static AxisAlignedBB cardReaderBoundingBox = new AxisAlignedBB(0.88, 0.33, 0.39, 1.055, 0.55, 0.525);
	
	public BlockRegister() {
		super("sco_pos", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, true, monitorBoundingBox, cardReaderBoundingBox);
		setTickRandomly(true);
	}
	
	@Override
	public boolean onSubBoundingBoxActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, AxisAlignedBB subBoundingBox) {
		if (subBoundingBox == monitorBoundingBox)
		{
			playerIn.openGui(Main.instance, Reference.GUI_SCO_POS, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		else if (subBoundingBox == cardReaderBoundingBox)
		{
			playerIn.sendMessage(new TextComponentString("this is a card reader"));
			return true;
		}
		
		return super.onSubBoundingBoxActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ, subBoundingBox);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityRegister();
	}
	
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		super.randomTick(worldIn, pos, state, random);
		if (worldIn.isRemote)
		{
			return;
		}
		
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null || !(te instanceof TileEntityRegister))
		{
			return;
		}
		
		((TileEntityRegister)te).onRandomTick();
	}
}
