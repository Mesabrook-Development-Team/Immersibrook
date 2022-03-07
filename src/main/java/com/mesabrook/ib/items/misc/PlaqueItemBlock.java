package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.BlockPlaque;
import com.mesabrook.ib.blocks.te.TileEntityPlaque;
import com.mesabrook.ib.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlaqueItemBlock extends ItemBlock {

	public PlaqueItemBlock(BlockPlaque block) {
		super(block);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {		
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote || stack == null || stack.getItem() != this)
		{
			return super.onItemRightClick(worldIn, playerIn, handIn);			
		}
		
		NBTTagCompound data = stack.getTagCompound();
		if (data == null || !data.hasKey("awardedTo"))
		{
			playerIn.openGui(Main.instance, Reference.GUI_PLAQUE, worldIn, handIn.ordinal(), 0, 0);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ, IBlockState newState) {
		if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState))
		{
			if (!world.isRemote)
			{
				TileEntity tileEntity = world.getTileEntity(pos);
				NBTTagCompound stackTag = stack.getTagCompound();
				if (stackTag != null && (stackTag.hasKey("awardedTo") || stackTag.hasKey("awardedFor")) && tileEntity instanceof TileEntityPlaque)
				{
					TileEntityPlaque plaqueTE = (TileEntityPlaque)tileEntity;
					plaqueTE.setAwardedTo(stackTag.getString("awardedTo"));
					plaqueTE.setAwardedFor(stackTag.getString("awardedFor"));
					
					world.notifyBlockUpdate(pos, newState, newState, 4);
				}
			}
			return true;
		}
		
		return false;
	}
}
