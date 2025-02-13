package com.mesabrook.ib.items.armor;

import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRespirator extends Item implements IHasModel
{
    public ItemRespirator(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);
        setMaxDamage(200);

        ModItems.ITEMS.add(this);
    }
    
    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }
    
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack)
    {
        return EntityEquipmentSlot.HEAD;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
    	if(GuiScreen.isShiftKeyDown())
    	{
    		tooltip.add(TextFormatting.GOLD + new TextComponentString("Wear before handling Asbestos to avoid Mesothelioma.").getFormattedText());
    	}
    	else
    	{
    		tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("im.tooltip.hidden").getFormattedText());
    	}
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
