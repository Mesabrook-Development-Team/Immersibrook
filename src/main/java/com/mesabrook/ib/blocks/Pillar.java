package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class Pillar extends BlockFalling implements IHasModel
{
	public static final AxisAlignedBB CORE = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	public static final PropertyBool IS_TOP = PropertyBool.create("is_top");
	public static final PropertyBool IS_BOTTOM = PropertyBool.create("is_bottom");
	
	public Pillar(String name, Material mat, SoundType sndType, String harvestTool, int harvestLevel)
	{
		super(mat);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(sndType);
		setHardness(3.0F);
		setResistance(8.0F);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		setHarvestLevel(harvestTool, harvestLevel);
		setLightOpacity(15);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean causesSuffocation(IBlockState state)
	{
		return false;
	}
	
	@Override
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1;
	}
		
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		if(state.getBlock() == ModBlocks.CELL_TOWER || state.getBlock() == ModBlocks.CELL_TOWER_RED)
		{
			AxisAlignedBB AABB = ModUtils.DEFAULT_AABB;
			return AABB;
		}
		AxisAlignedBB AABB = this.CORE;
		return AABB;
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, IS_TOP, IS_BOTTOM);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		IBlockState above = worldIn.getBlockState(pos.up());
		IBlockState below = worldIn.getBlockState(pos.down());
		
		boolean isTop = /*above.isNormalCube() &&*/ !(above.getBlock() instanceof Pillar);
		boolean isBottom = /*below.isNormalCube() &&*/ !(below.getBlock() instanceof Pillar);
		
		return state.withProperty(IS_TOP, isTop).withProperty(IS_BOTTOM, isBottom);
	}
}
