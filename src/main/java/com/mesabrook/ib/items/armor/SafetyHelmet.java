package com.mesabrook.ib.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

public class SafetyHelmet extends Item implements IHasModel
{
    public SafetyHelmet(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(1);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxDamage(600);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack item = player.getHeldItem(hand);
        ItemStack armorSlot = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

        if(armorSlot.isEmpty())
        {
            player.setHealth(player.getHealth() + 25);
            player.setItemStackToSlot(EntityEquipmentSlot.HEAD, item.copy());
            item.setCount(0);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, item);
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if(player instanceof EntityPlayer)
        {
            if(!world.isRemote)
            {
                player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 10, 1, true, false));
            }
        }
        else if(player instanceof EntityPlayer)
        {
            if(!world.isRemote)
            {
                player.removeActivePotionEffect(MobEffects.FIRE_RESISTANCE);
            }
        }
    }
}
