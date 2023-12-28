package com.mesabrook.ib.items.commerce;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    	playerIn.openGui(Main.instance, Reference.GUI_WALLET, worldIn, handIn.ordinal(), 0, 0);
    	return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	super.addInformation(stack, worldIn, tooltip, flagIn);
    	
    	if (stack == null || !stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
    	{
    		return;
    	}
    	
    	IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    	BigDecimal totalValue = new BigDecimal(0);
    	for(int i = 0; i < handler.getSlots(); i++)
    	{
    		ItemStack moneyStack = handler.getStackInSlot(i);
    		if (!(moneyStack.getItem() instanceof ItemMoney))
    		{
    			continue;
    		}
    		
    		ItemMoney moneyItem = (ItemMoney)moneyStack.getItem();
    		totalValue = totalValue.add(new BigDecimal(moneyItem.getValue()).divide(new BigDecimal(100)).multiply(new BigDecimal(moneyStack.getCount())));
    	}
    	
    	totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
    	
    	tooltip.add(TextFormatting.DARK_GREEN + "Total MBD$" + TextFormatting.RESET + totalValue.toPlainString());
    }
    
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
    	if (!stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
    	{
    		return super.getNBTShareTag(stack);
    	}
    	
    	NBTTagCompound stackTag = super.getNBTShareTag(stack);
    	
    	if (stackTag == null)
    	{
    		stackTag = new NBTTagCompound();
    	}
    	
    	IItemHandler stackHandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    	stackTag.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(stackHandler, null));
    	return stackTag;
    }
    
    @Override
    public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
    	super.readNBTShareTag(stack, nbt);
    	if (!nbt.hasKey("inventory") || !stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
    	{
    		return;
    	}
    	
    	IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    	CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(handler, null, nbt.getTag("inventory"));
    }
}
