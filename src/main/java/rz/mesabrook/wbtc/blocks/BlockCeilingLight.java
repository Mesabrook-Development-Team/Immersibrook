package rz.mesabrook.wbtc.blocks;

import net.minecraft.item.ItemBlock;
import rz.mesabrook.wbtc.blocks.te.CeilingLightTileEntity;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.Main;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.util.IHasModel;

@EventBusSubscriber
public class BlockCeilingLight extends Block implements ITileEntityProvider, IHasModel
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public BlockCeilingLight(String name)
    {
        super(Material.GLASS);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state)
    {
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return 1;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new CeilingLightTileEntity();
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (worldIn.isRemote)
        {
            return;
        }

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof CeilingLightTileEntity)
        {
            CeilingLightTileEntity sls = (CeilingLightTileEntity)te;
            sls.removeLightSources();
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te instanceof CeilingLightTileEntity)
        {
            CeilingLightTileEntity watchable = (CeilingLightTileEntity)te;
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public float getAmbientOcclusionLightValue(IBlockState state)
    {
        return 1F;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (worldIn.isRemote)
        {
            super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
            return;
        }

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof CeilingLightTileEntity)
        {
            CeilingLightTileEntity sls = (CeilingLightTileEntity)te;
            boolean shouldUpdate = false;
            if (worldIn.isBlockPowered(pos))
            {
                sls.removeLightSources();
            }
            else
            {
                sls.addLightSources();
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e)
    {
        if (e.getWorld().isRemote)
        {
            return;
        }

        BlockPos workingPos = new BlockPos(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ());

        workingPos = workingPos.north(2).west(2);
        IBlockState state = e.getWorld().getBlockState(workingPos);
        if (state.getBlock() == ModBlocks.CEILING_LIGHT_TEST && state.getValue(FACING) != EnumFacing.EAST && state.getValue(FACING) != EnumFacing.SOUTH)
        {
            e.getWorld().setBlockState(e.getPos(), ModBlocks.FAKE_LIGHT_SOURCE.getDefaultState());
            e.setCanceled(true);
        }

        workingPos = workingPos.east(4);
        state = e.getWorld().getBlockState(workingPos);
        if (state.getBlock() == ModBlocks.CEILING_LIGHT_TEST && state.getValue(FACING) != EnumFacing.SOUTH && state.getValue(FACING) != EnumFacing.WEST)
        {
            e.getWorld().setBlockState(e.getPos(), ModBlocks.FAKE_LIGHT_SOURCE.getDefaultState());
            e.setCanceled(true);
        }

        workingPos = workingPos.south(4);
        state = e.getWorld().getBlockState(workingPos);
        if (state.getBlock() == ModBlocks.CEILING_LIGHT_TEST && state.getValue(FACING) != EnumFacing.WEST && state.getValue(FACING) != EnumFacing.NORTH)
        {
            e.getWorld().setBlockState(e.getPos(), ModBlocks.FAKE_LIGHT_SOURCE.getDefaultState());
            e.setCanceled(true);
        }

        workingPos = workingPos.west(4);
        state = e.getWorld().getBlockState(workingPos);
        if (state.getBlock() == ModBlocks.CEILING_LIGHT_TEST && state.getValue(FACING) != EnumFacing.NORTH && state.getValue(FACING) != EnumFacing.EAST)
        {
            e.getWorld().setBlockState(e.getPos(), ModBlocks.FAKE_LIGHT_SOURCE.getDefaultState());
            e.setCanceled(true);
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }
}
