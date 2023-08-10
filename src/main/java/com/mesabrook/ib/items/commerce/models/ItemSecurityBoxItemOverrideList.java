package com.mesabrook.ib.items.commerce.models;

import java.util.List;

import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemSecurityBoxItemOverrideList extends ItemOverrideList {

	public ItemSecurityBoxItemOverrideList(List<ItemOverride> overridesIn) {
		super(overridesIn);
	}

	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world,
			EntityLivingBase entity) {
		NBTTagCompound compound = stack.getTagCompound();
		IBakedModel itemModel = null;
		if (compound != null && compound.hasKey("innerItem"))
		{
			NBTTagCompound innerItemNBT = compound.getCompoundTag("innerItem");
			ItemStack containedItem = new ItemStack(innerItemNBT);
			itemModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(containedItem);
		}
		
//		IBakedModel itemModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(new ItemStack(ModBlocks.IN_STREET_CROSSWALK_SIGN));
		return new ItemSecurityBoxFinalModel(originalModel, itemModel, true);
	}
}
