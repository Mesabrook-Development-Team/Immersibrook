package com.mesabrook.ib.items.armor;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRespirator extends Item implements IHasModel
{
    public ItemRespirator(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);
        setMaxDamage(200);

        ModItems.ITEMS.add(this);
    }
    
    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }
    
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
