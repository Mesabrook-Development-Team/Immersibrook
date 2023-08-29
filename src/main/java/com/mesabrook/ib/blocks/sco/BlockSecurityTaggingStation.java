package com.mesabrook.ib.blocks.sco;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.ImmersiblockRotational;
import com.mesabrook.ib.blocks.te.TileEntityTaggingStation;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockSecurityTaggingStation extends ImmersiblockRotational {

	public BlockSecurityTaggingStation() {
		super("security_station", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.DEFAULT_AABB);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {		
		IEmployeeCapability empCap = playerIn.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		if (empCap.getLocationID() == 0)
		{
			if (!worldIn.isRemote)
			{
				playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "You must be in store mode to use the security tagging station"));
			}
		}
		else
		{
			playerIn.openGui(Main.instance, Reference.GUI_TAGGING_STATION, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTaggingStation();
	}
}
