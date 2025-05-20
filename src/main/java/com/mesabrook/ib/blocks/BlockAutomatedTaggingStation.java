package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.TileEntityAutomatedTaggingStation;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAutomatedTaggingStation extends ImmersiblockRotational {

	public static final PropertyEnum<TileEntityAutomatedTaggingStation.LightStates> LIGHT_STATE = PropertyEnum.create("light_state", TileEntityAutomatedTaggingStation.LightStates.class);
	public BlockAutomatedTaggingStation() {
		super("automated_tagging_station", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.25F, 3.5F, ModUtils.DEFAULT_AABB);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityAutomatedTaggingStation();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityAutomatedTaggingStation te = (TileEntityAutomatedTaggingStation)worldIn.getTileEntity(pos);
		
		IEmployeeCapability employeeCap = playerIn.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		if (te != null && !playerIn.isSneaking())
		{
			if (employeeCap.getLocationID() == te.getLocationIDOwner() && employeeCap.getLocationEmployee().ManageInventory)
			{
				playerIn.openGui(Main.instance, Reference.GUI_AUTOMATED_TAGGING_STATION, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			else if (!worldIn.isRemote)
			{
				playerIn.sendMessage(new TextComponentString("You must be on duty and have permission to manage inventory."));
			}
			
			return true;
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if (worldIn.isRemote)
		{
			return;
		}
		
		boolean isPlayer = false;
		if (placer instanceof EntityPlayer)
		{
			IEmployeeCapability employeeCap = placer.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			if (employeeCap.getLocationID() != 0 && employeeCap.getLocationEmployee().ManageInventory)
			{
				TileEntityAutomatedTaggingStation te = (TileEntityAutomatedTaggingStation)worldIn.getTileEntity(pos);
				te.setLocationOwner(employeeCap.getLocationEmployee().Location);
				
				return;
			}
			
			isPlayer = true;
		}
		
		if (isPlayer)
		{
			placer.sendMessage(new TextComponentString("You must be on duty and have permission to manage inventory."));
		}
		
		worldIn.setBlockToAir(pos);
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (!player.hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
		{
			if (!world.isRemote)
			{
				player.sendMessage(new TextComponentString("You must be on duty and have permission to manage inventory."));
			}
			return false;
		}
		
		IEmployeeCapability emp = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityAutomatedTaggingStation)
		{
			TileEntityAutomatedTaggingStation taggingStation = (TileEntityAutomatedTaggingStation)te;
			if (!emp.manageInventory() || taggingStation.getLocationIDOwner() != emp.getLocationID())
			{
				if (!world.isRemote)
    			{
					player.sendMessage(new TextComponentString("You must be on duty and have permission to manage inventory."));
    			}
				return false;
			}
			
			if (!world.isRemote)
			{
				taggingStation.dumpInventory();
			}
			return super.removedByPlayer(state, world, pos, player, willHarvest);
		}
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, LIGHT_STATE);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityAutomatedTaggingStation te = (TileEntityAutomatedTaggingStation)worldIn.getTileEntity(pos);
		if (te == null)
		{
			return state.withProperty(LIGHT_STATE, TileEntityAutomatedTaggingStation.LightStates.Dark);
		}
		
		return state.withProperty(LIGHT_STATE, te.getLightState());
	}
}
