package rz.mesabrook.wbtc.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

public class Pillar extends Block implements IHasModel
{
	
	public static final AxisAlignedBB CORE = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	public static final PropertyBool IS_TOP = PropertyBool.create("is_top");
	public static final PropertyBool IS_BOTTOM = PropertyBool.create("is_bottom");
	
	public Pillar(String name, int light)
	{
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.STONE);
		setHardness(8.0F);
		setResistance(8.0F);
		setCreativeTab(CreativeTabs.DECORATIONS);
		setHarvestLevel("pickaxe", 0);
		setLightOpacity(15);
		setLightLevel(light);
		
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
		AxisAlignedBB AABB = this.CORE;
		return AABB;
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, IS_TOP, IS_BOTTOM);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		IBlockState above = worldIn.getBlockState(pos.up());
		IBlockState below = worldIn.getBlockState(pos.down());
		
		boolean isTop = /*above.isNormalCube() &&*/ !(above.getBlock() instanceof Pillar);
		boolean isBottom = /*below.isNormalCube() &&*/ !(below.getBlock() instanceof Pillar);
		
		return state.withProperty(IS_TOP, isTop).withProperty(IS_BOTTOM, isBottom);
	}
}
