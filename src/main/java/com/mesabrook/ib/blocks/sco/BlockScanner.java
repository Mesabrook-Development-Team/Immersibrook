package com.mesabrook.ib.blocks.sco;

import com.mesabrook.ib.blocks.ImmersiblockRotationalManyBB;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.util.ModUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockScanner extends ImmersiblockRotationalManyBB {

	private static final AxisAlignedBB SCANNER = ModUtils.getPixelatedAABB(4, 16.5, -4, 12, 16.75, 2);
	private static final AxisAlignedBB BILL_ACCEPTER = ModUtils.getPixelatedAABB(3.75, 11.5, -4.75, 5.75, 13.75, -2.5);
	private static final AxisAlignedBB COIN_SLOT = ModUtils.getPixelatedAABB(1, 10.75, -5, 3, 13.75, -2.5);
	private static final AxisAlignedBB BASE_BOX = ModUtils.getPixelatedAABB(0, 0, -4, 16, 16.5, 13);
	public BlockScanner()
	{
		super("sco_scanner", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, true, SCANNER, BILL_ACCEPTER, COIN_SLOT, BASE_BOX);
	}
	
	@Override
	public boolean onSubBoundingBoxActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, AxisAlignedBB subBoundingBox) {
		if (worldIn.isRemote || worldIn.getBlockState(pos.up()).getBlock() != ModBlocks.SCO_POS)
		{
			return super.onSubBoundingBoxActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ, subBoundingBox);
		}
		
		TileEntity te = worldIn.getTileEntity(pos.up());
		if (!(te instanceof TileEntityRegister))
		{
			return false;
		}
		
		TileEntityRegister register = (TileEntityRegister)te;
		if (register.getRegisterStatus() != RegisterStatuses.Online &&
				register.getRegisterStatus() != RegisterStatuses.InSession)
		{
			return false;
		}
		
		if (subBoundingBox == SCANNER && !playerIn.getHeldItem(hand).isEmpty()) // Boop
		{
			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (heldItem.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, facing) && 
					heldItem.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, facing).getLocationIDOwner() != register.getLocationIDOwner())
			{
				playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "This item cannot be rung up at this register"));
				return false;
			}
			register.insertItemInFirstAvailableSlot(heldItem.copy());
			register.setRegisterStatus(RegisterStatuses.InSession);
			heldItem.shrink(heldItem.getCount());
			worldIn.notifyBlockUpdate(register.getPos(), worldIn.getBlockState(register.getPos()), worldIn.getBlockState(register.getPos()), 3);
			
			
			return true;
		}
		else if (subBoundingBox == BILL_ACCEPTER) // Brrr
		{
			
		}
		else if (subBoundingBox == COIN_SLOT) // Ka-ching
		{
			
		}
		
		return false;
	}
}
