package com.mesabrook.ib.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRegister extends ImmersiblockRotational {	
	public static AxisAlignedBB monitorBoundingBox = new AxisAlignedBB(0.18, 0, 0.165, 0.812, 0.73, 0.68);
	public static AxisAlignedBB cardReaderBoundingBox = new AxisAlignedBB(0.88, 0.33, 0.39, 1.055, 0.55, 0.525);
	
	public BlockRegister() {
		super("sco_pos", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);
		setTickRandomly(true);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {		
		Vec3d hit = new Vec3d(hitX, hitY, hitZ);
		Vec3d look = playerIn.getLookVec();
		Vec3d end = hit.add(look);
		
		AxisAlignedBB boxHit = null;
		double leastDistance = 2;
		
		// Check monitor
		RayTraceResult result = monitorBoundingBox.calculateIntercept(hit, end);
		if (result != null)
		{
			leastDistance = result.hitVec.distanceTo(hit);
			boxHit = monitorBoundingBox;
		}
		
		// Check card reader
		result = cardReaderBoundingBox.calculateIntercept(hit, end);
		if (result != null)
		{
			double distance = result.hitVec.distanceTo(hit);
			if (distance < leastDistance)
			{
				boxHit = cardReaderBoundingBox;
			}
		}
		
		if (boxHit == monitorBoundingBox)
		{
			playerIn.openGui(Main.instance, Reference.GUI_SCO_POS, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		else if (boxHit == cardReaderBoundingBox)
		{
			playerIn.sendMessage(new TextComponentString("this is a card reader"));
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
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, monitorBoundingBox);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, cardReaderBoundingBox);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return monitorBoundingBox.union(cardReaderBoundingBox);
	}
}
