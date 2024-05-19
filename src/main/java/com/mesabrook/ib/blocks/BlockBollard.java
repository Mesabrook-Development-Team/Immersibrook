package com.mesabrook.ib.blocks;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.ModSounds;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class BlockBollard extends Block implements IHasModel
{
	private static final AxisAlignedBB DEFAULT_BB = ModUtils.getPixelatedAABB(5, -2, 5, 11, 0.1, 11);
	private static final AxisAlignedBB EXTENDED_BB = ModUtils.getPixelatedAABB(5, 0, 5, 11, 20, 11);
	private static final AxisAlignedBB EXTENDED_COL_BB = ModUtils.getPixelatedAABB(5, 0, 5, 11, 24, 11);
	
	public BlockBollard(String name)
	{
		super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setMaxStackSize(64));
	}
	
    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
    	return new ItemStack(ModBlocks.BOLLARD);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    	if(worldIn.isBlockPowered(pos))
    	{
    		worldIn.setBlockState(pos, ModBlocks.BOLLARD_EXTENDED.getDefaultState());
    	}
    	else
    	{
    		worldIn.setBlockState(pos, ModBlocks.BOLLARD.getDefaultState());
    	}
    }
    
    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
    	if(world instanceof World)
    	{
    		World worldIn = (World) world;
        	if(worldIn.isBlockPowered(pos))
        	{
        		worldIn.setBlockState(pos, ModBlocks.BOLLARD_EXTENDED.getDefaultState());
        	}
        	else
        	{
        		worldIn.setBlockState(pos, ModBlocks.BOLLARD.getDefaultState());
        	}
    	}
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	if(source instanceof World && ((World)source).isBlockPowered(pos))
    	{
    		return EXTENDED_BB;
    	}
    	return DEFAULT_BB;
    }
    
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
    	if (worldIn instanceof World && ((World) worldIn).isBlockPowered(pos))
    	{
    		return EXTENDED_COL_BB;
    	}
    	return DEFAULT_BB;
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
}
