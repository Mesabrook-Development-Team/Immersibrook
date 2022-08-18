package com.mesabrook.ib.blocks;

import com.mesabrook.ib.*;
import com.mesabrook.ib.blocks.te.*;
import com.mesabrook.ib.init.*;
import com.mesabrook.ib.items.misc.*;
import com.mesabrook.ib.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.*;
import java.util.*;

public class BlockWallSign extends Block implements IHasModel
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected final ArrayList<AxisAlignedBB> AABBs;

    public BlockWallSign(String name, MapColor color, AxisAlignedBB unrotatedAABB)
    {
        super(Material.IRON, color);
        setUnlocalizedName(name);
        setRegistryName(name);
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
        ModItems.ITEMS.add(new WallSignItemBlock(this).setRegistryName(this.getRegistryName()).setMaxStackSize(1));
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
        String line1 = null;
        String line2 = null;
        NBTTagCompound tag = stack.getTagCompound();

        if(GuiScreen.isShiftKeyDown())
        {
            if (tag != null)
            {
                if (tag.hasKey("line1"))
                {
                    line1 = tag.getString("line1");
                    tooltip.add(TextFormatting.AQUA + line1);
                }

                if (tag.hasKey("line2"))
                {
                    line2 = tag.getString("line2");
                    tooltip.add(TextFormatting.AQUA + line2);
                }
            }
            else
            {
                tooltip.add(TextFormatting.AQUA + "A decorative wall sign. Ideal for offices.");
            }
        }
        else
        {
            tooltip.add(TextFormatting.WHITE + new TextComponentTranslation("im.tooltip.hidden").getFormattedText());
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
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityWallSign(getUnlocalizedName());
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
                         int fortune)
    {
        ItemStack signStack = new ItemStack(this);
        signStack.setCount(1);

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityWallSign)
        {
            TileEntityWallSign wallSignTE = (TileEntityWallSign)te;
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("line1", wallSignTE.getLineOne());
            compound.setString("line2", wallSignTE.getLineTwo());
            signStack.setTagCompound(compound);
        }

        drops.add(signStack);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest) return true;
        else return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        ItemStack stack = new ItemStack(this);
        stack.setCount(1);

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityWallSign)
        {
            TileEntityWallSign wallSignTE = (TileEntityWallSign)te;
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("line1", wallSignTE.getLineOne());
            compound.setString("line2", wallSignTE.getLineTwo());
            stack.setTagCompound(compound);
        }
        return stack;
    }
}
