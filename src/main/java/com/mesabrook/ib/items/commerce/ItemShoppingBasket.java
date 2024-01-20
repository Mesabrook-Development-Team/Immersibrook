package com.mesabrook.ib.items.commerce;

import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

public class ItemShoppingBasket extends Item implements IHasModel
{
    public ItemShoppingBasket()
    {
        setUnlocalizedName("shopping_basket");
        setRegistryName("shopping_basket");
        setMaxStackSize(1);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setHasSubtypes(true);

        ModItems.ITEMS.add(this);
    }
    
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    	super.getSubItems(tab, items);
    	
    	if (isInCreativeTab(tab))
    	{
    		for(int i = 0; i < EnumDyeColor.values().length; i++)
			{
    			items.add(new ItemStack(this, 1, i));
			}
    	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(new TextComponentString(TextFormatting.GREEN + "Your ideal shopping companion!").getFormattedText());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack basketStack = playerIn.getHeldItem(handIn);
    	if (!worldIn.isRemote)
    	{
    		playerIn.openGui(Main.instance, Reference.GUI_SHOPPING_BASKET, worldIn, handIn.ordinal(), 0, 0);
    	}
        
    	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, basketStack);
    }

    @Override
    public void registerModels()
    {
        for(EnumDyeColor dyeColor : EnumDyeColor.values())
        {
        	Main.proxy.registerItemRenderer(this, dyeColor.getMetadata(), "color=" + dyeColor.getUnlocalizedName());
        }
    }
    
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
    	NBTTagCompound tag = super.getNBTShareTag(stack);
    	
    	if (tag == null)
    	{
    		tag = new NBTTagCompound();
    	}
    	
    	if (stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
    	{
    		tag.setTag("wbtc_inv", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), null));
    	}
    	
    	return tag;
    }
    
    @Override
    public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
    	super.readNBTShareTag(stack, nbt);
    	
    	if (nbt.hasKey("wbtc_inv"))
    	{
    		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), null, nbt.getTag("wbtc_inv"));
    	}
    }
}
