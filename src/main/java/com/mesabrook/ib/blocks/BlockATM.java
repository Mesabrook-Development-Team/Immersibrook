package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockATM extends ImmersiblockRotational {

	public BlockATM() {
		super("atm", Material.IRON, SoundType.METAL, "pickaxe", 1, 10F, 10F, ModUtils.DEFAULT_AABB);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(Main.instance, Reference.GUI_ATM, worldIn, pos.getX(), pos.getY(), pos.getZ());
		
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityATM();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (!worldIn.isRemote && placer.hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
		{
			IEmployeeCapability capability = placer.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			if (capability.getLocationID() == 0)
			{
				placer.sendMessage(new TextComponentString(TextFormatting.RED + "You must be on duty to place an ATM"));
				worldIn.setBlockToAir(pos);
			}
			else
			{
				TileEntityATM atm = (TileEntityATM)worldIn.getTileEntity(pos);
				atm.setCompanyIDOwner(capability.getLocationEmployee().Location.CompanyID);
				worldIn.notifyBlockUpdate(pos, state, state, 3);
			}
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@EventBusSubscriber
	public static class BlockEventHandler
	{
		@SubscribeEvent
		public static void onBlockBreak(BlockEvent.BreakEvent e)
		{
			if (e.getState().getBlock() != ModBlocks.ATM || !e.getPlayer().hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
			{
				return;
			}
			
			IEmployeeCapability emp = e.getPlayer().getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			World world = e.getWorld();
			TileEntityATM atmTE = (TileEntityATM)world.getTileEntity(e.getPos());
			boolean isCanceled = emp.getLocationID() == 0 || emp.getLocationEmployee().Location.CompanyID != atmTE.getCompanyIDOwner();
			if (isCanceled)
			{
				e.getPlayer().sendMessage(new TextComponentString(TextFormatting.RED + "You must be on duty as the owning company to break this ATM"));
			}
			e.setCanceled(isCanceled);
		}
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		IEmployeeCapability emp = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		TileEntityATM atmTE = (TileEntityATM)world.getTileEntity(pos);
		boolean isCanceled = emp.getLocationID() == 0 || emp.getLocationEmployee().Location.CompanyID != atmTE.getCompanyIDOwner();
		if (isCanceled && !world.isRemote)
		{
			player.sendMessage(new TextComponentString(TextFormatting.RED + "You must be on duty as the owning company to break this ATM"));
		}
		
		return isCanceled ? false : super.removedByPlayer(state, world, pos, player, willHarvest);
	}
}
