package com.mesabrook.ib.items.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

public class ItemPlasticIngot extends Item implements IHasModel
{
    public ItemPlasticIngot(String name, int stack)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(stack);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        if(playerIn instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.MAKE_PLASTIC_INGOTS, playerIn);
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
