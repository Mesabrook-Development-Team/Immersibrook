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
import net.minecraft.nbt.NBTTagCompound;
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
        setMaxStackSize(32);

        ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		if (!stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
		{
			return;
		}
		
		ISecuredItem item = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
		if (!item.getInnerStack().isEmpty())
		{
			tooltip.add(TextFormatting.YELLOW + "Item: " + TextFormatting.RESET + item.getInnerStack().getDisplayName() + " x" + item.getInnerStack().getCount());
		}
		if (item.getLocationNameOwner() != null && !item.getLocationNameOwner().isEmpty())
		{
			tooltip.add(TextFormatting.YELLOW + "Store: " + TextFormatting.RESET + item.getLocationNameOwner());
		}
		if (item.getHomeLocation().getY() != -1)
		{
			tooltip.add(TextFormatting.YELLOW + "Shelf: " + TextFormatting.RESET + String.format("X=%s,Y=%s,Z=%S", item.getHomeLocation().getX(), item.getHomeLocation().getY(), item.getHomeLocation().getZ()));
		}
		if (item.getHomeSpot() >= 0)
		{
			tooltip.add(TextFormatting.YELLOW + "Spot: " + TextFormatting.RESET + item.getHomeSpot());
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
				
				if (this.world.isRemote || !itemstack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) || getItem().isEmpty())
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
		newEntity.lifespan = 120;
		newEntity.motionX = location.motionX;
		newEntity.motionY = location.motionY;
		newEntity.motionZ = location.motionZ;
		return newEntity;
	}
    
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
    	NBTTagCompound tag = super.getNBTShareTag(stack);
    	if (tag == null)
    	{
    		tag = new NBTTagCompound();
    	}
    	
    	if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
    	{
    		tag.setTag("securedData", CapabilitySecuredItem.SECURED_ITEM_CAPABILITY.writeNBT(stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null), null));
    	}
    	
    	return tag;
    }
    
    @Override
    public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
    	super.readNBTShareTag(stack, nbt);
    	
    	if (nbt != null && stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null) && nbt.hasKey("securedData"))
    	{
    		ISecuredItem debitCard = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
    		CapabilitySecuredItem.SECURED_ITEM_CAPABILITY.readNBT(debitCard, null, nbt.getTag("securedData"));
    	}
    }
}
