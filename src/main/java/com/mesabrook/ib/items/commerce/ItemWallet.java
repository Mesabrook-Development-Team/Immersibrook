package com.mesabrook.ib.items.commerce;

import java.util.ArrayList;
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
import net.minecraft.util.EnumHand;
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
    	ArrayList<ItemStack> moneyStacks = new ArrayList<>();
    	for(int i = 0; i < handler.getSlots(); i++)
    	{
    		moneyStacks.add(handler.getStackInSlot(i));
    	}
    	
    	tooltip.add(TextFormatting.DARK_GREEN + "Total MBD$" + TextFormatting.RESET + ItemMoney.getAmountFromMoneyStacks(moneyStacks).toPlainString());
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
    	if (nbt != null)
    	{
	    	if (!nbt.hasKey("inventory") || !stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
	    	{
	    		return;
	    	}
	    	
	    	IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
	    	CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(handler, null, nbt.getTag("inventory"));
    	}
    }
}
