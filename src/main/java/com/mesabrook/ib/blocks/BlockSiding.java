package com.mesabrook.ib.blocks;

import com.mesabrook.ib.init.ModBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockSiding extends ImmersiblockRotational {

	public static PropertyBool BACK = PropertyBool.create("back");
	public static PropertyBool LEFT = PropertyBool.create("left");
	public static PropertyBool RIGHT = PropertyBool.create("right");
	
	public BlockSiding(String name, Material materialIn, SoundType soundTypeIn, String harvestTool, int harvestLevel,
			float hardnessIn, float resistanceIn, AxisAlignedBB unrotatedAABB) {
		super(name, materialIn, soundTypeIn, harvestTool, harvestLevel, hardnessIn, resistanceIn, unrotatedAABB);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, BACK, LEFT, RIGHT);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		
		boolean back = worldIn.getBlockState(pos.offset(facing)).isFullBlock();
		boolean left = worldIn.getBlockState(pos.offset(facing.rotateY())).isFullBlock();
		boolean right = worldIn.getBlockState(pos.offset(facing.rotateYCCW())).isFullBlock();
		
		return state.withProperty(BACK, back)
					.withProperty(LEFT, left)
					.withProperty(RIGHT, right);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(GuiScreen.isShiftKeyDown())
		{
			if(this == ModBlocks.SIDING_WHITE || this == ModBlocks.SIDING_ORANGE || this == ModBlocks.SIDING_MAGENTA || this == ModBlocks.SIDING_LBLUE || this == ModBlocks.SIDING_YELLOW || this == ModBlocks.SIDING_LIME || this == ModBlocks.SIDING_PINK || this == ModBlocks.SIDING_GRAY || this == ModBlocks.SIDING_SILVER || this == ModBlocks.SIDING_CYAN || this == ModBlocks.SIDING_PURPLE || this == ModBlocks.SIDING_BROWN || this == ModBlocks.SIDING_BLUE || this == ModBlocks.SIDING_RED || this == ModBlocks.SIDING_GREEN || this == ModBlocks.SIDING_BLACK)
			{
				tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.siding").getFormattedText());
			}
			if(this == ModBlocks.SIDING_OAK || this == ModBlocks.SIDING_SPRUCE || this == ModBlocks.SIDING_BIRCH || this == ModBlocks.SIDING_JUNGLE || this == ModBlocks.SIDING_ACACIA || this == ModBlocks.SIDING_DARK_OAK)
			{
				tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.siding.wood").getFormattedText());
			}
		}
		else
		{
			tooltip.add(TextFormatting.WHITE + new TextComponentTranslation("im.tooltip.hidden").getFormattedText());
		}
	}
}
