package com.mesabrook.ib.util.recipe;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.util.config.ModConfig;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class OneTimeChargerRecipe extends ShapelessOreRecipe {

	public OneTimeChargerRecipe(@Nullable final ResourceLocation group, final NonNullList<Ingredient> input, final ItemStack result) {
		super(group, input, result);
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack phoneStack = null;
		for(int i = 0; i < inv.getSizeInventory(); i++)
		{
			if (inv.getStackInSlot(i).getItem() instanceof ItemPhone)
			{
				phoneStack = inv.getStackInSlot(i);
			}
		}
		
		if (phoneStack == null)
		{
			return ItemStack.EMPTY;
		}
		
		phoneStack = phoneStack.copy();
		ItemPhone.NBTData data = ItemPhone.NBTData.getFromItemStack(phoneStack);
		data.setBatteryLevel(ModConfig.smartphoneMaxBattery);
		
		phoneStack.getTagCompound().merge(data.serializeNBT());
		
		return phoneStack;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		boolean hasPhone = false;
		boolean hasOneTimeCharger = false;
		
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.getItem() instanceof ItemPhone)
			{
				if (hasPhone)
				{
					hasPhone = false;
					break;
				}
				
				hasPhone = true;
			}
			
			if (stack.getItem() == ModItems.OT_CHARGER)
			{
				if (hasOneTimeCharger)
				{
					hasOneTimeCharger = false;
					break;
				}
				
				hasOneTimeCharger = true;
			}
		}
		
		return hasPhone && hasOneTimeCharger;
	}

	public static class Factory implements IRecipeFactory
	{
		@Override
		public IRecipe parse(JsonContext context, JsonObject json) {
			final String group = JsonUtils.getString(json, "group", "");
			final NonNullList<Ingredient> ingredients = parseShapeless(context, json);
			final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

			return new OneTimeChargerRecipe(group.isEmpty() ? null : new ResourceLocation(group), ingredients, result);
		}
		
		private static NonNullList<Ingredient> parseShapeless(final JsonContext context, final JsonObject json) {
			final NonNullList<Ingredient> ingredients = NonNullList.create();
			for (final JsonElement element : JsonUtils.getJsonArray(json, "ingredients"))
				ingredients.add(CraftingHelper.getIngredient(element, context));

			if (ingredients.isEmpty())
				throw new JsonParseException("No ingredients for shapeless recipe");

			return ingredients;
		}
	}
}
