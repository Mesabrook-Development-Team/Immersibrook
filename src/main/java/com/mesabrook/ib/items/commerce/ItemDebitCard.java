package com.mesabrook.ib.items.commerce;

import java.util.HashMap;
import java.util.List;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.capability.debitcard.CapabilityDebitCard;
import com.mesabrook.ib.capability.debitcard.IDebitCard;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemDebitCard extends Item implements IHasModel
{
	public static HashMap<EnumDebitCardType, ItemDebitCard> debitCardsByType = new HashMap<>();
	
    public ItemDebitCard(String name, EnumDebitCardType debitCardType)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
        debitCardsByType.put(debitCardType, this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
    
    public enum EnumDebitCardType
    {
    	Business,
    	Government,
    	Personal;
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	super.addInformation(stack, worldIn, tooltip, flagIn);
    	
    	if (stack.hasCapability(CapabilityDebitCard.DEBIT_CARD_CAPABILITY, null))
    	{
    		IDebitCard debitCardInfo = stack.getCapability(CapabilityDebitCard.DEBIT_CARD_CAPABILITY, null);
    		if (!debitCardInfo.getCardNumber().isEmpty())
    		{
    			if (!GuiScreen.isShiftKeyDown())
    			{
    				tooltip.add(TextFormatting.WHITE + "Hold [Shift] to see card number");
    			}
    			else
    			{
    				String cardNumberDisplay = debitCardInfo.getCardNumber();
    				cardNumberDisplay = cardNumberDisplay.substring(0, 12) + " " + cardNumberDisplay.substring(12);
    				cardNumberDisplay = cardNumberDisplay.substring(0, 8) + " " + cardNumberDisplay.substring(8);
    				cardNumberDisplay = cardNumberDisplay.substring(0, 4) + " " + cardNumberDisplay.substring(4);
    				
    				tooltip.add(TextFormatting.WHITE + "Card Number: " + TextFormatting.RESET + cardNumberDisplay);
    			}
    		}
    	}
    }
}
