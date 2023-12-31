package com.mesabrook.ib.events;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.BlockSeat;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.DamageSourceAprilFools;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class SeatEvent
{
    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if(player.getRidingEntity() != null)
        {
            return;
        }

        if(player.isSneaking())
        {
            return;
        }

        World worldIn = event.getWorld();
        BlockPos pos = event.getPos();

        IBlockState state = worldIn.getBlockState(pos);
        ItemStack mainStack = player.getHeldItemMainhand();
        ItemStack offStack = player.getHeldItemOffhand();
        if(!mainStack.isEmpty() || !offStack.isEmpty())
        {
            return;
        }

        if(state.getBlock() instanceof BlockSeat && state.getBlock() != ModBlocks.URINAL && !player.isSneaking())
        {
            if(state.getBlock() == ModBlocks.THRONE_FC)
            {
                SeatEntity seat = new SeatEntity(worldIn, pos);
                worldIn.spawnEntity(seat);
                player.startRiding(seat);
            }
            else if(state.getBlock() == ModBlocks.THRONE)
            {
                SeatEntity seat = new SeatEntity(worldIn, pos);
                worldIn.spawnEntity(seat);
                player.startRiding(seat);
            }
            else
            {
                SeatEntity seat = new SeatEntity(worldIn, pos);
                worldIn.spawnEntity(seat);
                player.startRiding(seat);
            }
        }
    }

    public static class SeatEntity extends Entity
    {
        public SeatEntity(World worldIn, BlockPos pos)
        {
            this(worldIn);
            EnumFacing facing = worldIn.getBlockState(pos).getValue(BlockHorizontal.FACING);

            if(worldIn.getBlockState(pos).getBlock() == ModBlocks.THRONE_FC || worldIn.getBlockState(pos).getBlock() == ModBlocks.THRONE || worldIn.getBlockState(pos).getBlock() == ModBlocks.THRONE_GOV)
            {
                switch(facing)
                {
                    case NORTH:
                        setPosition(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.65D);
                        break;
                    case SOUTH:
                        setPosition(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.35D);
                        break;
                    case EAST:
                        setPosition(pos.getX() + 0.35D, pos.getY() + 0.4D, pos.getZ() + 0.5D);
                        break;
                    case WEST:
                        setPosition(pos.getX() + 0.6D, pos.getY() + 0.4D, pos.getZ() + 0.5D);
                        break;
                }
            }

            if(worldIn.getBlockState(pos).getBlock() == ModBlocks.PRISON_TOILET  || worldIn.getBlockState(pos).getBlock() == ModBlocks.HOME_TOILET)
            {
                switch(facing)
                {
                    case NORTH:
                        setPosition(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.3D);
                        break;
                    case SOUTH:
                        setPosition(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.7D);
                        break;
                    case EAST:
                        setPosition(pos.getX() + 0.7D, pos.getY() + 0.4D, pos.getZ() + 0.5D);
                        break;
                    case WEST:
                        setPosition(pos.getX() + 0.2D, pos.getY() + 0.4D, pos.getZ() + 0.5D);
                        break;
                }
            }

            if(worldIn.getBlockState(pos).getBlock() == ModBlocks.WALL_TOILET)
            {
                switch(facing)
                {
                    case NORTH:
                        setPosition(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D);
                        break;
                    case SOUTH:
                        setPosition(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D);
                        break;
                    case EAST:
                        setPosition(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D);
                        break;
                    case WEST:
                        setPosition(pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D);
                        break;
                }
            }
        }

        public SeatEntity(World worldIn)
        {
            super(worldIn);
            setSize(0.0F, 0.0F);
        }

        @Override
        public void onUpdate()
        {
            super.onUpdate();
            BlockPos pos = getPosition();
            World world = getEntityWorld();
            EntityPlayer player = world.getClosestPlayer(posX, posY, posZ, 5, false);

            // Seat Event for Immersibrook's Toilet blocks.
            if(!(getEntityWorld().getBlockState(pos).getBlock() instanceof BlockSeat))
            {
                setDead();
                return;
            }

            // Play random farts
            if(world.rand.nextDouble() < 0.009)
            {
                if(!world.isRemote && world.getBlockState(pos).getBlock() != ModBlocks.THRONE_FC && world.getBlockState(pos).getBlock() != ModBlocks.THRONE && world.getBlockState(pos).getBlock() != ModBlocks.THRONE_GOV)
                {
                    SoundRandomizer.FartRandomizer(world.rand);
                    ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                    packet.pos = player.getPosition();
                    packet.modID = "wbtc";

                    if(SoundRandomizer.fartResult == "fart_2")
                    {
                        packet.soundName = "fart_1";
                    }
                    else
                    {
                        packet.soundName = SoundRandomizer.fartResult;
                    }

                    packet.rapidSounds = false;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
                }
            }

            List<Entity> passengers = getPassengers();
            if(passengers.isEmpty())
            {
                setDead();
            }
            for(Entity entity : passengers)
            {
                if(entity.isSneaking())
                {
                    setDead();

                    if(world.getBlockState(pos).getBlock() == ModBlocks.THRONE_FC)
                    {
                        if(!player.getUniqueID().equals(Reference.CSX_UUID))
                        {
                            DamageSourceAprilFools dodo = new DamageSourceAprilFools("dodo");
                            player.setGameType(GameType.SURVIVAL);
                            player.attackEntityFrom(dodo, Integer.MAX_VALUE);

                            if(!world.isRemote)
                            {
                                player.sendMessage(new TextComponentString(TextFormatting.RED + "Only the First Consul can sit in this seat."));
                                Main.logger.warn("[NATIONAL SECURITY ALERT] An unauthorized player (" + player.getName() + ") just tried to sit in the First Consul's chair!");
                            }
                        }
                    }
                    else if(world.getBlockState(pos).getBlock() == ModBlocks.THRONE)
                    {

                    }

                    if(!world.isRemote)
                    {
                        if(world.getBlockState(pos).getBlock() != ModBlocks.THRONE_FC && world.getBlockState(pos).getBlock() != ModBlocks.THRONE && world.getBlockState(pos).getBlock() != ModBlocks.THRONE_GOV)
                        {
                            ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                            packet.pos = player.getPosition();
                            packet.modID = "wbtc";

                            if(world.getBlockState(pos).getBlock() == ModBlocks.PRISON_TOILET)
                            {
                                packet.soundName = "toilet_1";
                            }
                            else if(world.getBlockState(pos).getBlock() == ModBlocks.WALL_TOILET)
                            {
                                packet.soundName = "toilet_2";
                            }
                            else if(world.getBlockState(pos).getBlock() == ModBlocks.URINAL)
                            {
                                packet.soundName = "urinal";
                            }
                            else
                            {
                                packet.soundName = "toilet_3";
                            }

                            packet.rapidSounds = true;
                            PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
                        }
                    }
                }
            }
        }

        @Override
        protected void entityInit()
        {

        }

        @Override
        protected void readEntityFromNBT(NBTTagCompound compound)
        {

        }

        @Override
        protected void writeEntityToNBT(NBTTagCompound compound)
        {

        }
    }
}
