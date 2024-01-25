package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.tools.ItemManholeHook;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImmersiblockRotational extends Block implements IHasModel
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected final ArrayList<AxisAlignedBB> AABBs;

    public ImmersiblockRotational(String name, Material materialIn, SoundType soundTypeIn, String harvestTool, int harvestLevel, float hardnessIn, float resistanceIn, AxisAlignedBB unrotatedAABB)
    {
        super(materialIn);
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setHarvestLevel(harvestTool, harvestLevel);
        setSoundType(soundTypeIn);
        setHardness(hardnessIn);
        setResistance(resistanceIn);
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
        addItemBlock();
    }
    
    protected void addItemBlock()
    {
    	ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return false;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        if(blockState.getBlock() == ModBlocks.SCO_BAGGING)
        {
            return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
        }
        if(blockState.getBlock() == ModBlocks.IN_STREET_CROSSWALK_SIGN)
        {
            return null;
        }
        return FULL_BLOCK_AABB;
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
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            if(this == ModBlocks.MARKER_POLE_BLUE || this == ModBlocks.MARKER_POLE_ORANGE || this == ModBlocks.MARKER_POLE_RED || this == ModBlocks.MARKER_POLE_WOOD_BLUE || this == ModBlocks.MARKER_POLE_WOOD_RED || this == ModBlocks.MARKER_POLE_WOOD_ORANGE)
            {
                tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.markerpole").getFormattedText());
            }

            if(this == ModBlocks.RELAY_MANHOLE)
            {
                tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.relaycover").getFormattedText());
            }

            if(this == ModBlocks.LVN_MANHOLE)
            {
                tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.manhole.lvn").getFormattedText());
            }

            if(this == ModBlocks.UTIL_MANHOLE)
            {
                tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.manhole.util").getFormattedText());
            }

            if(this == ModBlocks.BLANK_MANHOLE)
            {
                tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.manhole.generic").getFormattedText());
            }

            if(this == ModBlocks.IN_STREET_CROSSWALK_SIGN)
            {
                tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.crosswalk").getFormattedText());
            }
            if(this == ModBlocks.ATS)
            {
                tooltip.add(TextFormatting.YELLOW + "(Not Yet Implemented) A machine that automatically places products into Security Boxes for sale.");
            }
        }
        else
        {
            tooltip.add(TextFormatting.WHITE + new TextComponentTranslation("im.tooltip.hidden").getFormattedText());
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = playerIn.getHeldItem(hand);
        if(stack.getItem() instanceof ItemManholeHook)
        {
            if(state.getBlock() == ModBlocks.BLANK_MANHOLE)
            {
                playerIn.addItemStackToInventory(new ItemStack(ModBlocks.BLANK_MANHOLE, 1));
                worldIn.setBlockToAir(pos);

                if(!worldIn.isRemote)
                {
                    ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                    packet.pos = playerIn.getPosition();
                    packet.soundName = "manhole_break";
                    packet.volume = 0.5F;
                    packet.pitch = 1.0F;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }

                if(!playerIn.isCreative())
                {
                    stack.damageItem(1, playerIn);
                }
                return true;
            }
            else if(state.getBlock() == ModBlocks.LVN_MANHOLE)
            {
                playerIn.addItemStackToInventory(new ItemStack(ModBlocks.LVN_MANHOLE, 1));
                worldIn.setBlockToAir(pos);

                if(!worldIn.isRemote)
                {
                    ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                    packet.pos = playerIn.getPosition();
                    packet.soundName = "manhole_break";
                    packet.volume = 0.5F;
                    packet.pitch = 1.0F;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }

                if(!playerIn.isCreative())
                {
                    stack.damageItem(1, playerIn);
                }
                return true;
            }
            else if(state.getBlock() == ModBlocks.RELAY_MANHOLE)
            {
                playerIn.addItemStackToInventory(new ItemStack(ModBlocks.RELAY_MANHOLE, 1));
                worldIn.setBlockToAir(pos);

                if(!worldIn.isRemote)
                {
                    ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                    packet.pos = playerIn.getPosition();
                    packet.soundName = "manhole_break";
                    packet.volume = 0.5F;
                    packet.pitch = 1.0F;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }

                if(!playerIn.isCreative())
                {
                    stack.damageItem(1, playerIn);
                }
                return true;
            }
            else if(state.getBlock() == ModBlocks.UTIL_MANHOLE)
            {
                playerIn.addItemStackToInventory(new ItemStack(ModBlocks.UTIL_MANHOLE, 1));
                worldIn.setBlockToAir(pos);

                if(!worldIn.isRemote)
                {
                    ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                    packet.pos = playerIn.getPosition();
                    packet.soundName = "manhole_break";
                    packet.volume = 0.5F;
                    packet.pitch = 1.0F;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }

                if(!playerIn.isCreative())
                {
                    stack.damageItem(1, playerIn);
                }
                return true;
            }
        }
        return false;
    }
}
