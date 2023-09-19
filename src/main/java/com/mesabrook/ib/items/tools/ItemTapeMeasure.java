package com.mesabrook.ib.items.tools;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
            if(!player.isCreative())
            {
                stack.damageItem(1, player);
            }

            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null)
            {
                tag = new NBTTagCompound();
                stack.setTagCompound(tag);
            }

            BlockPos pointA = BlockPos.ORIGIN;
            BlockPos pointB = BlockPos.ORIGIN;
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
                pointB = player.getPosition();
                player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Second Point Set"));
                tag.setLong("PointB", pointB.toLong());
            }
            else
            {
                pointA = player.getPosition();
                player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "First Point Set. Hold SHIFT and right-click again to set the second point."));
                tag.setLong("PointA", pointA.toLong());
            }

            if (!pointA.equals(BlockPos.ORIGIN) && !pointB.equals(BlockPos.ORIGIN))
            {
                double distance = pointA.distanceSq(pointB);
                int roundedDistance = (int) Math.round(Math.sqrt(distance));
                player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "§lDistance: " + roundedDistance + " blocks."));

                tag.removeTag("PointA");
                tag.removeTag("PointB");
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
