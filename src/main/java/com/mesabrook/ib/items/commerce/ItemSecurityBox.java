package com.mesabrook.ib.items.commerce;

import java.util.List;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemSecurityBox extends Item implements IHasModel {
	
	public ItemSecurityBox()
	{
		super();
		setUnlocalizedName("security_box");
        setRegistryName("security_box");
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setMaxStackSize(1);

        ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		ISecuredItem item = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
		if (!item.getInnerStack().isEmpty())
		{
			tooltip.add(TextFormatting.YELLOW + "Item: " + TextFormatting.RESET + item.getInnerStack().getDisplayName() + " x" + item.getInnerStack().getCount());
		}
	}
}
