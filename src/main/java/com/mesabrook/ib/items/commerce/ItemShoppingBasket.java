package com.mesabrook.ib.items.commerce;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

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
        ItemStack basketStack = playerIn.getActiveItemStack();
        return new ActionResult<ItemStack>(EnumActionResult.PASS, basketStack);
    }

    @Override
    public void registerModels()
    {
        for(EnumDyeColor dyeColor : EnumDyeColor.values())
        {
        	Main.proxy.registerItemRenderer(this, dyeColor.getMetadata(), "color=" + dyeColor.getUnlocalizedName());
        }
    }
}
