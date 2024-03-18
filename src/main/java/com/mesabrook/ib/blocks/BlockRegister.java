package com.mesabrook.ib.blocks;

import java.math.BigDecimal;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.SecurityBoxHandler;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.items.commerce.ItemRegisterFluidWrapper.CapabilityRegisterFluidWrapper;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockRegister extends ImmersiblockRotationalManyBB {	
	public static AxisAlignedBB monitorBoundingBox = ModUtils.getPixelatedAABB(3, 0, 5, 13.1, 12, 14);
	public static AxisAlignedBB cardReaderBoundingBox = ModUtils.getPixelatedAABB(13.25, 4, 9, 16, 9, 11.5);
	
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
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityRegister)
		{
			TileEntityRegister register = (TileEntityRegister)te;
			IItemHandler itemHandler = register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			for(int i = 0; i < itemHandler.getSlots(); i++)
			{
				ItemStack stack = itemHandler.getStackInSlot(i);
				if (stack.hasCapability(CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null) || stack.isEmpty())
				{
					continue;
				}
				
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
			}
			
			SecurityBoxHandler secBoxHandler = register.getSecurityBoxInventory();
			for(int i = 0; i < secBoxHandler.getSlots(); i++)
			{
				ItemStack stack = secBoxHandler.getStackInSlot(i);
				if (stack.isEmpty())
				{
					continue;
				}
				
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
			}
			
			BigDecimal tenderedAmount = register.getTenderedAmount();
			if (tenderedAmount != null && tenderedAmount.compareTo(new BigDecimal(0)) > 0)
			{
				for(ItemStack stack : ItemMoney.getMoneyStackForAmount(tenderedAmount))
				{
					InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
				}
			}
			
			if (register.getInsertedCardStack() != null && !register.getInsertedCardStack().isEmpty())
			{
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), register.getInsertedCardStack().copy());
			}
		}
		
		super.breakBlock(worldIn, pos, state);
	}
}
