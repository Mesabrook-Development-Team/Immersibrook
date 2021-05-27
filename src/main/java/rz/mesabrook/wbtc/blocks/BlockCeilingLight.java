package rz.mesabrook.wbtc.blocks;

import net.minecraft.block.Block;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.EnumSimpleRotation;
import rz.mesabrook.wbtc.util.IHasModel;

public class BlockCeilingLight extends Block implements IHasModel
{
    public static final PropertyEnum<EnumSimpleRotation> ROTATION = PropertyEnum.create("rotation", EnumSimpleRotation.class);

    public BlockCeilingLight(String name)
    {
        super(Material.GLASS);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, EnumSimpleRotation.NORTH));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        //First try and place it against whatever we're placing it against
        if (facing == EnumFacing.NORTH) { return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.NORTH); }
        if (facing == EnumFacing.EAST)  { return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.EAST);  }
        if (facing == EnumFacing.SOUTH) { return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.SOUTH); }
        if (facing == EnumFacing.WEST)  { return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.WEST);  }
        //If that fails, just place it opposite us
        if (placer.getHorizontalFacing() == EnumFacing.NORTH) { return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.SOUTH); }
        if (placer.getHorizontalFacing() == EnumFacing.EAST)  { return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.WEST);  }
        if (placer.getHorizontalFacing() == EnumFacing.SOUTH) { return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.NORTH); }
        if (placer.getHorizontalFacing() == EnumFacing.WEST)  { return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.EAST);  }

        return this.getDefaultState();
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
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ROTATION});
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta == 0) { this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.NORTH); }
        if (meta == 1) { this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.EAST ); }
        if (meta == 2) { this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.SOUTH); }
        if (meta == 3) { this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.WEST ); }
        return this.getDefaultState().withProperty(ROTATION, EnumSimpleRotation.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumSimpleRotation)state.getValue(ROTATION)).getMetadata();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }
}