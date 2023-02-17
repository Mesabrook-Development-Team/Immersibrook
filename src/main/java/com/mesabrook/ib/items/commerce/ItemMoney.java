package com.mesabrook.ib.items.commerce;

import com.mesabrook.ib.*;
import com.mesabrook.ib.init.*;
import com.mesabrook.ib.util.*;
import net.minecraft.item.*;

public class ItemMoney extends Item implements IHasModel
{
    private float denomination;
    public ItemMoney(String name, float value)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(64);
        denomination = value;

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
