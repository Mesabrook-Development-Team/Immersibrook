package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.tools.ItemBanHammer;
import com.mesabrook.ib.items.tools.ItemManholeHook;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.Reference;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockManhole extends Block implements IHasModel
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected final ArrayList<AxisAlignedBB> AABBs;
    public BlockManhole(String name, AxisAlignedBB unrotatedAABB)
    {
        super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.METAL);
        setHardness(2.5F);
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
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        if(this == ModBlocks.MANHOLE_CLOSED || this == ModBlocks.MANHOLE_OPEN)
        {
            return new ItemStack(ModBlocks.MANHOLE_CLOSED);
        }
        if(this == ModBlocks.UTIL_MANHOLE_OPEN || this == ModBlocks.UTIL_MANHOLE_CLOSED)
        {
            return new ItemStack(ModBlocks.UTIL_MANHOLE_CLOSED);
        }
        if(this == ModBlocks.BLANK_MANHOLE_CLOSED || this == ModBlocks.BLANK_MANHOLE_OPEN)
        {
            return new ItemStack(ModBlocks.BLANK_MANHOLE_CLOSED);
        }
        if(this == ModBlocks.LVN_MANHOLE_CLOSED || this == ModBlocks.LVN_MANHOLE_OPEN)
        {
            return new ItemStack(ModBlocks.LVN_MANHOLE_CLOSED);
        }
        if(this == ModBlocks.MANHOLE_LADDER)
        {
            return new ItemStack(ModBlocks.MANHOLE_LADDER);
        }
        return new ItemStack(Items.FISH, 1, 1);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = playerIn.getHeldItem(hand);
        facing = state.getValue(FACING);
        if(stack.getItem() instanceof ItemManholeHook && state.getBlock() != ModBlocks.MANHOLE_LADDER || stack.getItem() instanceof ItemBanHammer && state.getBlock() != ModBlocks.MANHOLE_LADDER)
        {
            this.activate(worldIn, pos, playerIn, facing);
            if(!playerIn.isCreative()) {stack.damageItem(1, playerIn);}
            return true;
        }
        return false;
    }

    private void activate(World world, BlockPos pos, EntityPlayer player, EnumFacing direction)
    {
        boolean sendAlertsToConsole = world.getGameRules().getBoolean("manholeAlert");
        if(this == ModBlocks.MANHOLE_CLOSED)
        {
            world.setBlockState(pos, ModBlocks.MANHOLE_OPEN.getDefaultState().withProperty(FACING, direction));
            if(sendAlertsToConsole)
            {
                Main.logger.warn("[" + Reference.MODNAME + " Alert] Manhole in dimension " + player.dimension + " at [X: " + pos.getX() + "], [Y: " + pos.getY() + "], [Z: " + pos.getZ() + "] has been opened by " + player.getName());
            }
            if(!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "manhole_open";
                packet.volume = 0.5F;
                packet.pitch = 1.0F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }
        else if(this == ModBlocks.MANHOLE_OPEN)
        {
            world.setBlockState(pos, ModBlocks.MANHOLE_CLOSED.getDefaultState().withProperty(FACING, direction));
            if(sendAlertsToConsole)
            {
                Main.logger.warn("[" + Reference.MODNAME + " Alert] Manhole in dimension " + player.dimension + " at [X: " + pos.getX() + "], [Y: " + pos.getY() + "], [Z: " + pos.getZ() + "] has been opened by " + player.getName());
            }
            if(!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "manhole_close";
                packet.volume = 0.5F;
                packet.pitch = 1.0F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }

        if(this == ModBlocks.UTIL_MANHOLE_CLOSED)
        {
            world.setBlockState(pos, ModBlocks.UTIL_MANHOLE_OPEN.getDefaultState().withProperty(FACING, direction));
            if(sendAlertsToConsole)
            {
                Main.logger.warn("[" + Reference.MODNAME + " Alert] Manhole in dimension " + player.dimension + " at [X: " + pos.getX() + "], [Y: " + pos.getY() + "], [Z: " + pos.getZ() + "] has been opened by " + player.getName());
            }

            if(!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "manhole_open";
                packet.volume = 0.5F;
                packet.pitch = 1.0F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }
        else if(this == ModBlocks.UTIL_MANHOLE_OPEN)
        {
            world.setBlockState(pos, ModBlocks.UTIL_MANHOLE_CLOSED.getDefaultState().withProperty(FACING, direction));
            if(sendAlertsToConsole)
            {
                Main.logger.warn("[" + Reference.MODNAME + " Alert] Manhole in dimension " + player.dimension + " at [X: " + pos.getX() + "], [Y: " + pos.getY() + "], [Z: " + pos.getZ() + "] has been opened by " + player.getName());
            }

            if(!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "manhole_close";
                packet.volume = 0.5F;
                packet.pitch = 1.0F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }

        if(this == ModBlocks.BLANK_MANHOLE_CLOSED)
        {
            world.setBlockState(pos, ModBlocks.BLANK_MANHOLE_OPEN.getDefaultState().withProperty(FACING, direction));
            if(sendAlertsToConsole)
            {
                Main.logger.warn("[" + Reference.MODNAME + " Alert] Manhole in dimension " + player.dimension + " at [X: " + pos.getX() + "], [Y: " + pos.getY() + "], [Z: " + pos.getZ() + "] has been opened by " + player.getName());
            }
            if(!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "manhole_open";
                packet.volume = 0.5F;
                packet.pitch = 1.0F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }
        else if(this == ModBlocks.BLANK_MANHOLE_OPEN)
        {
            world.setBlockState(pos, ModBlocks.BLANK_MANHOLE_CLOSED.getDefaultState().withProperty(FACING, direction));
            if(sendAlertsToConsole)
            {
                Main.logger.warn("[" + Reference.MODNAME + " Alert] Manhole in dimension " + player.dimension + " at [X: " + pos.getX() + "], [Y: " + pos.getY() + "], [Z: " + pos.getZ() + "] has been opened by " + player.getName());
            }
            if(!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "manhole_close";
                packet.volume = 0.5F;
                packet.pitch = 1.0F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }

        if(this == ModBlocks.LVN_MANHOLE_CLOSED)
        {
            world.setBlockState(pos, ModBlocks.LVN_MANHOLE_OPEN.getDefaultState().withProperty(FACING, direction));
            if(sendAlertsToConsole)
            {
                Main.logger.warn("[" + Reference.MODNAME + " Alert] Manhole in dimension " + player.dimension + " at [X: " + pos.getX() + "], [Y: " + pos.getY() + "], [Z: " + pos.getZ() + "] has been opened by " + player.getName());
            }
            if(!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "manhole_open";
                packet.volume = 0.5F;
                packet.pitch = 1.0F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }
        else if(this == ModBlocks.LVN_MANHOLE_OPEN)
        {
            world.setBlockState(pos, ModBlocks.LVN_MANHOLE_CLOSED.getDefaultState().withProperty(FACING, direction));
            if(sendAlertsToConsole)
            {
                Main.logger.warn("[" + Reference.MODNAME + " Alert] Manhole in dimension " + player.dimension + " at [X: " + pos.getX() + "], [Y: " + pos.getY() + "], [Z: " + pos.getZ() + "] has been opened by " + player.getName());
            }
            if(!world.isRemote)
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = "manhole_close";
                packet.volume = 0.5F;
                packet.pitch = 1.0F;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
            }
        }
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        if(this == ModBlocks.MANHOLE_OPEN)
        {
            drops.add(new ItemStack(ModBlocks.MANHOLE_CLOSED, 1));
        }
        if(this == ModBlocks.UTIL_MANHOLE_OPEN)
        {
            drops.add(new ItemStack(ModBlocks.UTIL_MANHOLE_CLOSED, 1));
        }
        if(this == ModBlocks.BLANK_MANHOLE_OPEN)
        {
            drops.add(new ItemStack(ModBlocks.BLANK_MANHOLE_CLOSED, 1));
        }
        if(this == ModBlocks.LVN_MANHOLE_OPEN)
        {
            drops.add(new ItemStack(ModBlocks.LVN_MANHOLE_CLOSED, 1));
        }
        if(this == ModBlocks.MANHOLE_LADDER)
        {
            drops.add(new ItemStack(ModBlocks.MANHOLE_LADDER, 1));
        }
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
    {
        if(this == ModBlocks.MANHOLE_OPEN || this == ModBlocks.LVN_MANHOLE_OPEN || this == ModBlocks.BLANK_MANHOLE_OPEN || this == ModBlocks.UTIL_MANHOLE_OPEN || this == ModBlocks.MANHOLE_LADDER)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
