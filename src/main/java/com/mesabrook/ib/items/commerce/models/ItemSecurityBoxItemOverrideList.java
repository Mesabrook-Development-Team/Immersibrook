package com.mesabrook.ib.items.commerce.models;

import java.util.List;

import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSecurityBoxItemOverrideList extends ItemOverrideList {

	public ItemSecurityBoxItemOverrideList(List<ItemOverride> overridesIn) {
		super(overridesIn);
	}

	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world,
			EntityLivingBase entity) {
		ISecuredItem item = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
		IBakedModel itemModel = null;
		if (!item.getInnerStack().isEmpty())
		{
			itemModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(item.getInnerStack());
		}
		
//		IBakedModel itemModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(new ItemStack(ModBlocks.IN_STREET_CROSSWALK_SIGN));
		return new ItemSecurityBoxFinalModel(originalModel, itemModel, true);
	}
}
