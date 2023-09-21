package com.mesabrook.ib.items.commerce;

import com.mesabrook.ib.*;
import com.mesabrook.ib.init.*;
import com.mesabrook.ib.util.*;
import net.minecraft.item.*;

public class ItemMoney extends Item implements IHasModel
{
    private int denomination;
    private MoneyType moneyType;
    public ItemMoney(String name, int value, MoneyType moneyType)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(64);
        denomination = value;
        this.moneyType = moneyType;

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
    
    public MoneyType getMoneyType()
    {
    	return moneyType;
    }
    
    public int getValue()
    {
    	return denomination;
    }
    
    public enum MoneyType
    {
    	Coin,
    	Bill;
    }
}
