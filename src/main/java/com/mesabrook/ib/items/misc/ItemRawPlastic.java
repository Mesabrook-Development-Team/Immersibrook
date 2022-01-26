package com.mesabrook.ib.items.misc;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRawPlastic extends Item implements IHasModel
{
    public ItemRawPlastic(String name, int stack)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(stack);

        ModItems.ITEMS.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if(this.getUnlocalizedName().contains("raw"))
        {
            tooltip.add(TextFormatting.GOLD + "Raw Material");
        }
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0);
    }
}
