package com.mesabrook.ib.items.commerce;

import com.mesabrook.ib.*;
import com.mesabrook.ib.init.*;
import com.mesabrook.ib.util.*;
import net.minecraft.item.*;

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
}
