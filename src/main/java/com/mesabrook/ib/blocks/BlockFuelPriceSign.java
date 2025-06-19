package com.mesabrook.ib.blocks;

import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
 * TODO for Adam: Implement this pl0x. Maybe also look into using blockstates for the fuel types that'll be displayed on the sign block itself,
 * those being gasoline and diesel. Model files are in wbtc/models/blocks.
 */

@SuppressWarnings("unused")
public class BlockFuelPriceSign extends ImmersiblockRotational
{
	public BlockFuelPriceSign(String name, AxisAlignedBB hitbox)
	{
		super(name, Material.IRON, SoundType.METAL, "pickaxe", 1, 10F, 10F, hitbox);
	}
	
//	@Override
//	public boolean hasTileEntity(IBlockState state) 
//  {
//		return true;
//	}
//	
//	@Override
//	public TileEntity createTileEntity(World world, IBlockState state) 
//	{
//		return new TileEntityATM();
//	}
//	
//	@Override
//	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
//	{
//		if (!worldIn.isRemote && placer.hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
//		{
//			IEmployeeCapability capability = placer.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
//			if (capability.getLocationID() == 0)
//			{
//				placer.sendMessage(new TextComponentString(TextFormatting.RED + "You must be on duty to place this block."));
//				worldIn.setBlockToAir(pos);
//			}
//			else
//			{
//
//			}
//		}
//		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
//	}
//	
//	@EventBusSubscriber
//	public static class BlockEventHandler
//	{
//		@SubscribeEvent
//		public static void onBlockBreak(BlockEvent.BreakEvent e)
//		{
//
//		}
//	}
//	
//	@Override
//	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) 
//	{
//
//		return isCanceled ? false : super.removedByPlayer(state, world, pos, player, willHarvest);
//	}
}
