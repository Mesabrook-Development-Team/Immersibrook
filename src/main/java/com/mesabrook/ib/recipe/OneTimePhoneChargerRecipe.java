package com.mesabrook.ib.recipe;

import com.mesabrook.ib.items.misc.ItemPhone;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import java.util.stream.Collectors;

public class OneTimePhoneChargerRecipe extends ShapedRecipes
{
    public OneTimePhoneChargerRecipe(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result)
    {
        super(group, width, height, ingredients, result);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack phoneStack = getRecipeOutput().copy();
        NBTTagCompound phoneTag = phoneStack.getTagCompound();

        if (phoneTag == null)
        {
            phoneTag = new NBTTagCompound();
            phoneStack.setTagCompound(phoneTag);
        }

        for(int i = 0; i < inv.getWidth() * inv.getHeight(); i++)
        {
            ItemStack slotStack = inv.getStackInSlot(i);
            if (slotStack == null || !(slotStack.getItem() instanceof ItemPhone))
            {
                continue;
            }
            NBTTagCompound sourceTag = slotStack.getTagCompound();
            if (sourceTag == null)
            {
                continue;
            }
            for(String key : sourceTag.getKeySet().stream().filter(key -> key.startsWith("light")).collect(Collectors.toList()))
            {
                phoneTag.setLong(key, sourceTag.getLong(key));
            }

            break;
        }
        return phoneStack;
    }
}
