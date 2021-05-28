package rz.mesabrook.wbtc.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.EnumSimpleRotation;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.ModUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockCeilingLight extends Block implements IHasModel
{

    public static final AxisAlignedBB CORE = new AxisAlignedBB(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D);
    public BlockCeilingLight(String name, AxisAlignedBB unrotatedAABB)
    {
        super(Material.GLASS);
        setSoundType(SoundType.GLASS);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setLightLevel(1);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        placeLightBlockBelow(world, pos, placer);
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        destroyLightBlockBelow(worldIn, pos);
        super.breakBlock(worldIn, pos, state);
    }

    @SuppressWarnings("deprecation")
    public void placeLightBlockBelow(World world, BlockPos pos, EntityLivingBase placer)
    {
        BlockPos posOffset = pos.offset(EnumFacing.DOWN);
        Block block = world.getBlockState(posOffset).getBlock();

        int count = 0;

        while (block == Blocks.AIR && count < 15) {
            posOffset = posOffset.offset(EnumFacing.DOWN);
            block = world.getBlockState(posOffset).getBlock();
            count++;
            if (block != Blocks.AIR) {
                Block targetBlock = world.getBlockState(posOffset.offset(EnumFacing.UP)).getBlock();
                if (targetBlock == Blocks.AIR) {
                    world.setBlockState(posOffset.offset(EnumFacing.UP), ModBlocks.FAKE_LIGHT_SOURCE.getStateForPlacement(world, posOffset, EnumFacing.UP, 0, 0, 0, 0, placer), 3);
                }
            }
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public void destroyLightBlockBelow(World world, BlockPos pos)
    {
        BlockPos posOffset = pos.offset(EnumFacing.DOWN);
        Block block = world.getBlockState(posOffset).getBlock();

        int count = 0;

        while (block != ModBlocks.FAKE_LIGHT_SOURCE && count < 15)
        {
            posOffset = posOffset.offset(EnumFacing.DOWN);
            block = world.getBlockState(posOffset).getBlock();
            count++;
            if(block == ModBlocks.FAKE_LIGHT_SOURCE)
            {
                world.setBlockToAir(posOffset);
            }
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state;
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
}