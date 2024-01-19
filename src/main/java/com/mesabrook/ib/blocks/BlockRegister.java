package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
			if (!worldIn.isRemote)
			{
				TileEntityRegister register = (TileEntityRegister)worldIn.getTileEntity(pos);
				if (register == null)
				{
					return true;
				}
				
				register.onCardReaderUse(playerIn.getHeldItem(hand), (EntityPlayerMP)playerIn);
			}
			
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
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityRegister)
		{
			TileEntityRegister register = (TileEntityRegister)te;
			if (register.getLocationIDOwner() == 0)
			{
				return super.removedByPlayer(state, world, pos, player, willHarvest);
			}
			
			if (!player.hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
			{
				if (!world.isRemote)
    			{
					player.sendMessage(new TextComponentString("You must be on duty and have permission to manage prices."));
    			}
				return false;
			}
			
			IEmployeeCapability emp = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			if (!emp.managePrices() || emp.getLocationID() != register.getLocationIDOwner())
			{
				if (!world.isRemote)
    			{
					player.sendMessage(new TextComponentString("You must be on duty and have permission to manage prices."));
    			}
				return false;
			}
		}
		
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
}
