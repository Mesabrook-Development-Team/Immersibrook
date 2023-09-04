package com.mesabrook.ib.items.commerce;

import java.util.Arrays;
import java.util.List;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity.ProductSpot;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
	
	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) { // DEBUG
		return super.getEntityLifespan(itemStack, world);
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		ISecuredItem secureCap = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
		return secureCap.getHomeLocation().getY() != -1;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		EntityItem newEntity = new EntityItem(world, location.posX, location.posY, location.posZ, itemstack)
		{
			@Override
			public void setDead() {
				super.setDead();
				
				if (this.world.isRemote || !itemstack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) || getAge() < lifespan)
				{
					return;
				}
				
				ISecuredItem securedItem = itemstack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
				TileEntity te = this.world.getTileEntity(securedItem.getHomeLocation());
				if (te == null || !(te instanceof ShelvingTileEntity))
				{
					return;
				}
				
				ShelvingTileEntity shelf = (ShelvingTileEntity)te;
				ProductSpot[] spots = shelf.getProductSpots();
				if (!Arrays.stream(spots).anyMatch(ps -> ps.getPlacementID() == securedItem.getHomeSpot()))
				{
					return;
				}
				
				ItemStack[] stacks = Arrays.stream(spots).filter(ps -> ps.getPlacementID() == securedItem.getHomeSpot()).findFirst().get().getItems();
				if (!stacks[stacks.length - 1].isEmpty())
				{
					return;
				}
				
				int indexToSet = -1;
				for(int i = 0; i < stacks.length; i++)
				{
					if (stacks[i].isEmpty())
					{
						indexToSet = i;
						break;
					}
				}
				stacks[indexToSet] = itemstack;
  				this.world.notifyBlockUpdate(shelf.getPos(), this.world.getBlockState(shelf.getPos()), this.world.getBlockState(shelf.getPos()), 3);
				shelf.markDirty();
			}
		};
		newEntity.setPickupDelay(40);
		newEntity.motionX = location.motionX;
		newEntity.motionY = location.motionY;
		newEntity.motionZ = location.motionZ;
		return newEntity;
	}
}
