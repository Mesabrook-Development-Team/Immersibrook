package com.mesabrook.ib.items.commerce;

import com.mesabrook.ib.*;
import com.mesabrook.ib.init.*;
import com.mesabrook.ib.util.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWallet extends Item implements IHasModel
{
    public ItemWallet(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
    		EnumFacing facing, float hitX, float hitY, float hitZ) {
    	player.openGui(Main.instance, Reference.GUI_WALLET, worldIn, hand.ordinal(), 0, 0);
    	return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
