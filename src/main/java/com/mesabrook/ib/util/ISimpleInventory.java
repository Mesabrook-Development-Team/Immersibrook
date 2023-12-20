package com.mesabrook.ib.util;

import net.minecraft.item.ItemStack;

public interface ISimpleInventory
{
    int getSize();
    ItemStack getItem(int i);
    void clear();
}
