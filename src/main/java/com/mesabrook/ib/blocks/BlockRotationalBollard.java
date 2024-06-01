package com.mesabrook.ib.blocks;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRotationalBollard extends Block implements IHasModel
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static AxisAlignedBB EXTENDED_COL_BB;
    protected final ArrayList<AxisAlignedBB> AABBs;
    
    public BlockRotationalBollard(String name, AxisAlignedBB unrotatedAABB, AxisAlignedBB colliderAABB, float lightLevel)
    {
    	super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.METAL);
        setHardness(2.5F);
        setResistance(3.0F);
        setLightLevel(lightLevel);
        this.EXTENDED_COL_BB = colliderAABB;
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
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
    	if(state.getBlock() == ModBlocks.WALL_UP) return new ItemStack(ModBlocks.WALL_DOWN);
    	if(state.getBlock() == ModBlocks.WALL_DOWN) return new ItemStack(ModBlocks.WALL_DOWN);
    	
    	return ItemStack.EMPTY;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }
    
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
    	if (worldIn instanceof World && ((World) worldIn).isBlockPowered(pos))
    	{
    		return ModUtils.getRotatedAABB(EXTENDED_COL_BB, state.getBlock().getBedDirection(state, worldIn, pos), false);
    	}
    	return state.getBoundingBox(worldIn, pos);
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
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
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
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	if(worldIn.isBlockPowered(pos))
    	{
    		extend(worldIn, pos, state.getBlock().getBedDirection(state, worldIn, pos), state);
    	}
    	else
    	{
    		retract(worldIn, pos, state.getBlock().getBedDirection(state, worldIn, pos), state);
    	}
    }
    
    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
    	if(world instanceof World)
    	{
    		World worldIn = (World) world;
    		IBlockState state = worldIn.getBlockState(pos);
    		if(worldIn.isBlockPowered(pos))
    		{
    			extend(worldIn, pos, state.getBlock().getBedDirection(state, worldIn, pos), state);
    		}
    		else
    		{
    			retract(worldIn, pos, state.getBlock().getBedDirection(state, worldIn, pos), state);
    		}
    	}
    }
    
    private void extend(World world, BlockPos pos, EnumFacing direction, IBlockState state)
    {
    	if(state.getBlock() == ModBlocks.WALL_DOWN)
    	{
    		world.setBlockState(pos, ModBlocks.WALL_UP.getDefaultState().withProperty(FACING, direction));
    	}
    }
    
    private void retract(World world, BlockPos pos, EnumFacing direction, IBlockState state)
    {
    	if(state.getBlock() == ModBlocks.WALL_UP)
    	{
    		world.setBlockState(pos, ModBlocks.WALL_DOWN.getDefaultState().withProperty(FACING, direction));
    	}
    }
}
