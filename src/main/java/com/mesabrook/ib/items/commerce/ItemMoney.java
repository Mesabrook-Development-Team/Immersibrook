package com.mesabrook.ib.items.commerce;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMoney extends Item implements IHasModel, Comparable<ItemMoney>
{
	public static TreeSet<ItemMoney> SORTED_MONEY = new TreeSet<>();
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
        SORTED_MONEY.add(this);
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

	@Override
	public int compareTo(ItemMoney o) {
		return Integer.compare(denomination, o.denomination) * -1;
	}
	
	public static List<ItemStack> getMoneyStackForAmount(BigDecimal amount)
	{
		ArrayList<ItemStack> items = new ArrayList<>();
		ItemMoney currentMoney = SORTED_MONEY.first();
		while(amount.compareTo(new BigDecimal(0)) > 0)
		{
			BigDecimal value = new BigDecimal(currentMoney.getValue()).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
			if (amount.compareTo(value) >= 0)
			{
				BigDecimal quantity = amount.divide(value).setScale(0, RoundingMode.DOWN);
				ItemStack moneyStack = new ItemStack(currentMoney, quantity.intValue());
				items.add(moneyStack);
				
				amount = amount.subtract(value.multiply(quantity)).setScale(2);
			}
			
			currentMoney = SORTED_MONEY.higher(currentMoney);
			if (currentMoney == null)
			{
				break;
			}
		}
		
		return items;
	}
	
	public static BigDecimal getAmountFromMoneyStacks(ArrayList<ItemStack> moneyStacks)
	{
		BigDecimal totalValue = new BigDecimal(0);
    	for(ItemStack moneyStack : moneyStacks)
    	{
    		if (!(moneyStack.getItem() instanceof ItemMoney))
    		{
    			continue;
    		}
    		
    		if (!(moneyStack.getItem() instanceof ItemMoney))
    		{
    			continue;
    		}
    		
    		ItemMoney moneyItem = (ItemMoney)moneyStack.getItem();
    		totalValue = totalValue.add(new BigDecimal(moneyItem.getValue()).divide(new BigDecimal(100)).multiply(new BigDecimal(moneyStack.getCount())));
    	}
    	
    	totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
    	
    	return totalValue;
	}
}
