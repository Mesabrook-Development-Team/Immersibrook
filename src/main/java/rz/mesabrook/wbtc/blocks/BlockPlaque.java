package rz.mesabrook.wbtc.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.blocks.te.TileEntityPlaque;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.items.misc.PlaqueItemBlock;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.ModUtils;

public class BlockPlaque extends Block implements IHasModel
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected final ArrayList<AxisAlignedBB> AABBs;
	
	public BlockPlaque(String name, MapColor color, AxisAlignedBB unrotatedAABB)
	{
		super(Material.WOOD, color);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.WBTC_TAB_TROPHY);
		setHarvestLevel("pickaxe", 1);
		setHardness(1.0F);
		setResistance(3.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
	    AABBs = new ArrayList<AxisAlignedBB>(Arrays.asList(
	    	      ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.DOWN, false),
	    	      ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.UP, false),
	    	      ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.NORTH, false),
	    	      ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.SOUTH, false),
	    	      ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.WEST, false),
	    	      ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.EAST, false),
	    	      unrotatedAABB, unrotatedAABB // Array fill to ensure that the array size covers 4 bit (meta & 0x07).
	    	    ));
		
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new PlaqueItemBlock(this).setRegistryName(this.getRegistryName()).setMaxStackSize(1));
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return side != EnumFacing.DOWN && side != EnumFacing.UP && this.canAttachTo(worldIn, pos, side);
	}
	
	public boolean canAttachTo(World worldIn, BlockPos pos, EnumFacing facing)
	{
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return this.isAcceptableNeighbor(worldIn, pos.offset(facing.getOpposite()), facing) && (block == Blocks.AIR || block == Blocks.VINE || this.isAcceptableNeighbor(worldIn, pos.up(), EnumFacing.UP));
	}
	
	private boolean isAcceptableNeighbor(World worldIn, BlockPos pos, EnumFacing facing)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		return iblockstate.getBlockFaceShape(worldIn, pos, facing) == BlockFaceShape.SOLID;
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
	public boolean isFullCube(IBlockState state)
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
	public BlockRenderLayer getBlockLayer() 
	{
		return BlockRenderLayer.CUTOUT;
	}
	
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }
    
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }
	
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{	
		String awardedTo = null;
		String awardedFor = null;
		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null)
		{
			if (tag.hasKey("awardedTo"))
			{
				awardedTo = tag.getString("awardedTo");
			}
			
			if (tag.hasKey("awardedFor"))
			{
				awardedFor = tag.getString("awardedFor");
			}
		}
		
		if(this.getUnlocalizedName().contains("plaque_test"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "WBTC");
			if (awardedTo != null)
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded To: " + TextFormatting.YELLOW + awardedTo);
			}
			if (awardedFor != null)
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded For: " + TextFormatting.YELLOW + awardedFor);
			}
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD +  "Testing this plaque.");
		}
		else if(this.getUnlocalizedName().contains("plaque_dev"))
		{
			tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded By: " + TextFormatting.BLUE + "Government of Mesabrook");
			if (awardedTo != null)
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded To: " + TextFormatting.YELLOW + awardedTo);
			}
			if (awardedFor != null)
			{
				tooltip.add(TextFormatting.LIGHT_PURPLE + "Awarded For: " + TextFormatting.YELLOW + awardedFor);
			}
			tooltip.add(TextFormatting.LIGHT_PURPLE + "For: " + TextFormatting.GOLD +  "Contributing to the server by developing mods or resource packs.");
		}
	}
    
	@Override
    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state)
    {
        return true;
    }
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityPlaque(getUnlocalizedName());
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		ItemStack plaqueStack = new ItemStack(this);
		plaqueStack.setCount(1);
		
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityPlaque)
		{
			TileEntityPlaque plaqueTileEntity = (TileEntityPlaque)te;
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("awardedTo", plaqueTileEntity.getAwardedTo());
			compound.setString("awardedFor", plaqueTileEntity.getAwardedFor());
			plaqueStack.setTagCompound(compound);
		}
		
		drops.add(plaqueStack);
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (willHarvest) return true;
		else return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
	}
}
