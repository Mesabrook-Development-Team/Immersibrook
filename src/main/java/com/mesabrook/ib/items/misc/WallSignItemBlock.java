package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.*;
import com.mesabrook.ib.blocks.*;
import com.mesabrook.ib.blocks.te.*;
import com.mesabrook.ib.net.*;
import com.mesabrook.ib.util.*;
import com.mesabrook.ib.util.handlers.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
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

public class WallSignItemBlock extends ItemBlock
{
    public WallSignItemBlock(BlockWallSign block)
    {
        super(block);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        NBTTagCompound data = stack.getTagCompound();

        if(playerIn.isSneaking())
        {
            if(!worldIn.isRemote)
            {
                if(stack.hasTagCompound())
                {
                    stack.setTagCompound(null);
                    playerIn.sendMessage(new TextComponentString(new TextComponentTranslation("im.wallsign.reset").getFormattedText()));
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
                }
            }
        }
        else
        {
            if(!worldIn.isRemote || stack == null || stack.getItem() != this)
            {
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }

            if(data == null || !data.hasKey("line1"))
            {
                playerIn.openGui(Main.instance, Reference.GUI_WALLSIGN, worldIn, handIn.ordinal(), 0, 0);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
                                float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if(stack.hasTagCompound())
        {
            if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState))
            {
                if(!world.isRemote)
                {
                    TileEntity tileEntity = world.getTileEntity(pos);
                    NBTTagCompound stackTag = stack.getTagCompound();
                    if(stackTag != null && (stackTag.hasKey("line1") || stackTag.hasKey("line2")) && tileEntity instanceof TileEntityWallSign)
                    {
                        TileEntityWallSign signTE = (TileEntityWallSign)tileEntity;
                        signTE.setLineOne(stackTag.getString("line1"));
                        signTE.setLineTwo(stackTag.getString("line2"));

                        world.notifyBlockUpdate(pos, newState, newState, 4);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return stack.hasTagCompound();
    }
}
