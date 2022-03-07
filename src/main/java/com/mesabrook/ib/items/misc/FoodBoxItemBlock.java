package com.mesabrook.ib.items.misc;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.FoodBox;
import com.mesabrook.ib.blocks.te.TileEntityFoodBox;
import com.mesabrook.ib.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FoodBoxItemBlock extends ItemBlock
{
    public FoodBoxItemBlock(FoodBox block)
    {
        super(block);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote || stack == null || stack.getItem() != this)
        {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        NBTTagCompound data = stack.getTagCompound();
        if(data == null || !data.hasKey("boxID"))
        {
            playerIn.openGui(Main.instance, Reference.GUI_FOODBOX, worldIn, handIn.ordinal(), 0, 0);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if(super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState))
        {
            if(!world.isRemote)
            {
                TileEntity tileEntity = world.getTileEntity(pos);
                NBTTagCompound stackTag = stack.getTagCompound();
                if(stackTag != null && (stackTag.hasKey("boxID") || stackTag.hasKey("company")) && tileEntity instanceof TileEntityFoodBox)
                {
                    TileEntityFoodBox foodTE = (TileEntityFoodBox)tileEntity;
                    foodTE.setBoxID(stackTag.getString("boxID"));
                    foodTE.setCompany(stackTag.getString("company"));
                    world.notifyBlockUpdate(pos, newState, newState, 4);
                }
            }
            return true;
        }
        return false;
    }
}
