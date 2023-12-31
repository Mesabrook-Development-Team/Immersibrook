package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockSeat extends Block implements IHasModel
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected final ArrayList<AxisAlignedBB> AABBs;

    public BlockSeat(String name, AxisAlignedBB unrotatedAABB, float hardnessIn)
    {
        super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setHarvestLevel("pickaxe", 3);
        setResistance(4F);
        setHardness(hardnessIn);
        setSoundType(SoundType.METAL);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)blockState.getValue(FACING)).getIndex() & 0x7);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(playerIn.isSneaking())
        {
            if(!worldIn.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = playerIn.getPosition();
                packet.modID = "wbtc";
                packet.rapidSounds = true;
                playerIn.swingArm(hand);

                if(state.getBlock() == ModBlocks.PRISON_TOILET)
                {
                    packet.soundName = "toilet_1";
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }
                else if(state.getBlock() == ModBlocks.WALL_TOILET)
                {
                    packet.soundName = "toilet_2";
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }
                else if(state.getBlock() == ModBlocks.HOME_TOILET)
                {
                    packet.soundName = "toilet_3";
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }
                else if(state.getBlock() == ModBlocks.URINAL)
                {
                    packet.soundName = "urinal";
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
                }
                else if(state.getBlock() == ModBlocks.THRONE_FC)
                {
                    packet.soundName = "";
                }
                else if(state.getBlock() == ModBlocks.THRONE)
                {
                    packet.soundName = "";
                }
                else if(state.getBlock() == ModBlocks.THRONE_GOV)
                {
                    packet.soundName = "";
                }
            }
            return true;
        }

        if(state.getBlock() == ModBlocks.PRISON_TOILET)
        {
            ItemStack waterBottle = new ItemStack(Items.POTIONITEM, 1, 0);
            ItemStack heldItem = playerIn.getHeldItem(hand);

            if(!(heldItem.getItem() == Items.GLASS_BOTTLE))
            {
                return false;
            }

            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("Potion", "minecraft:water");
            waterBottle.setTagCompound(nbt);

            if(!playerIn.isCreative())
            {
                heldItem.shrink(1);
            }

            playerIn.addItemStackToInventory(waterBottle);
            playerIn.swingArm(hand);

            ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
            packet.pos = pos;
            packet.modID = "cfm";
            packet.soundName = "tap";
            packet.rapidSounds = true;
            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(playerIn.dimension, playerIn.posX, playerIn.posY, playerIn.posZ, 25));
            return true;
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(state.getBlock() == ModBlocks.THRONE_FC)
        {
            ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
            packet.pos = pos;
            packet.modID = "minecraft";
            packet.soundName = "ui.toast.challenge_complete";
            packet.rapidSounds = false;
            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(placer.dimension, placer.posX, placer.posY, placer.posZ, 25));
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }
}
