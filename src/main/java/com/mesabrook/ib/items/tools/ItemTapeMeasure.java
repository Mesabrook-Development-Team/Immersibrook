package com.mesabrook.ib.items.tools;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.blocks.BlockPole;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTapeMeasure extends Item implements IHasModel
{
    private BlockPos pos1 = null;
    private BlockPos pos2 = null;

    public ItemTapeMeasure(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(1);
        setMaxDamage(100);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(!world.isRemote)
        {
            NBTTagCompound tag = stack.getTagCompound();
            RayTraceResult rayTraceResult = rayTrace(world, player);
            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                BlockPos hitPos = rayTraceResult.getBlockPos();
                if (tag == null)
                {
                    tag = new NBTTagCompound();
                    stack.setTagCompound(tag);
                }

                BlockPos pointA = BlockPos.ORIGIN;
                BlockPos pointB = BlockPos.ORIGIN;
                BlockPos polePos = BlockPos.ORIGIN;
                IBlockState poleBlock = world.getBlockState(polePos);
                if (tag.hasKey("PointA"))
                {
                    pointA = BlockPos.fromLong(tag.getLong("PointA"));
                }

                if (tag.hasKey("PointB"))
                {
                    pointB = BlockPos.fromLong(tag.getLong("PointA"));
                }

                if(player.isSneaking())
                {
                    pointA = BlockPos.fromLong(tag.getLong("PointA"));
                    pointB = hitPos;
                    tag.setLong("PointB", pointB.toLong());
                }
                else
                {
                    pointA = hitPos;
                    polePos = pointA.up();

                    ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                    packet.pos = player.getPosition();
                    packet.soundName = "tape_measure_open";
                    packet.rapidSounds = true;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));

                    player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "First Point Set. Hold SHIFT and right-click again to set the second point."));
                    tag.setLong("PointA", pointA.toLong());

                    world.setBlockState(polePos, ModBlocks.POLE.getDefaultState());
                }

                if (!pointA.equals(BlockPos.ORIGIN) && !pointB.equals(BlockPos.ORIGIN))
                {
                    polePos = pointA.up();
                    poleBlock = world.getBlockState(polePos);
                    double distance = pointA.distanceSq(pointB);
                    int roundedDistance = (int) Math.round(Math.sqrt(distance));
                    player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "§lDistance: " + roundedDistance + " Meters"));

                    if (poleBlock.getBlock() == ModBlocks.POLE)
                    {
                        // Destroy the POLE block with break sound and particles
                        world.destroyBlock(polePos, false);
                    }

                    ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                    packet.pos = player.getPosition();
                    packet.soundName = "tape_measure_close";
                    packet.rapidSounds = true;
                    PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));

                    tag.removeTag("PointA");
                    tag.removeTag("PointB");

                    if(!player.isCreative())
                    {
                        stack.damageItem(1, player);
                    }

                    if(player instanceof EntityPlayer)
                    {
                        Triggers.trigger(Triggers.TAPE_MEASURE, player);
                    }
                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.add(TextFormatting.GOLD + "A simple tool that allows you to measure the distance between two set points.");
        }
        else
        {
            tooltip.add(TextFormatting.YELLOW + "Press [SHIFT] for more info.");
        }
    }

    private RayTraceResult rayTrace(World world, EntityPlayer player)
    {
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d look = player.getLookVec();
        double reach = 5.0; // Adjust the reach distance as needed

        Vec3d end = start.addVector(look.x * reach, look.y * reach, look.z * reach);
        return world.rayTraceBlocks(start, end);
    }
}
