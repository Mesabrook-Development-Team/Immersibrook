package com.mesabrook.ib.enchant;

import com.mesabrook.ib.init.ModEnchants;
import com.mesabrook.ib.items.tools.ItemBanHammer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentRandomizer extends Enchantment
{
    public EnchantmentRandomizer()
    {
        super(Rarity.VERY_RARE, ModEnchants.HAMMER, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
        setRegistryName("randomizer");
        setName("randomizer");
    }

    @Override
    public int getMaxLevel() { return 1; }

    @Override
    public boolean isCurse() { return true; }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return stack.getItem() instanceof ItemBanHammer;
    }

    @Override
    public boolean isAllowedOnBooks()
    {
        return true;
    }
}
