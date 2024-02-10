package com.mesabrook.ib.blocks.commerce;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.ImmersiblockRotational;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTimeClock extends ImmersiblockRotational
{
	public BlockTimeClock()
	{
		super("timeclock", Material.IRON, SoundType.METAL, "pickaxe", 1, 1.5F, 3.0F, ModUtils.getPixelatedAABB(16 - 3, 16 - 5, 16 - 14.5, 16 - 12.5, 16 - 11, 16 - 16));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ServerSoundBroadcastPacket.playIBSound(worldIn, "pos_scanner_beep", pos);
		playerIn.openGui(Main.instance, Reference.GUI_SCO_STOREMODE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
