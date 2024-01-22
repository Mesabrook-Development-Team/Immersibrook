package com.mesabrook.ib.blocks.te;

import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.init.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityShoppingBasketHolder extends TileEntity {
	private static final int MAX_BASKETS = 8;
	private Stack<ItemStack> baskets = new Stack<>();
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList tagList = compound.getTagList("baskets", NBT.TAG_COMPOUND);
		tagListToBaskets(tagList);
	}
	
	private void tagListToBaskets(NBTTagList tagList)
	{
		baskets.clear();
		baskets.setSize(tagList.tagCount());
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTBase nbt = tagList.get(i);
			if (nbt instanceof NBTTagEnd)
			{
				baskets.setElementAt(ItemStack.EMPTY, i);
			}
			else if (nbt instanceof NBTTagCompound)
			{
				NBTTagCompound tag = (NBTTagCompound)nbt;
				ItemStack stack = new ItemStack(tag);
				baskets.setElementAt(stack, i);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {		
		compound.setTag("baskets", basketsToTagList());
		return super.writeToNBT(compound);
	}
	
	private NBTTagList basketsToTagList()
	{
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < baskets.size(); i++)
		{
			tagList.appendTag(baskets.get(i).serializeNBT());
		}
		
		return tagList;
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound =  super.getUpdateTag();
		compound.setTag("baskets", basketsToTagList());
		return compound;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		
		tagListToBaskets(tag.getTagList("baskets", NBT.TAG_COMPOUND));
		
		if (world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	public ImmutableList<ItemStack> getBaskets()
	{
		return ImmutableList.copyOf(baskets);
	}
	
	public boolean pushBasket(ItemStack basketStack)
	{
		if (baskets.size() >= MAX_BASKETS || basketStack.getItem() != ModItems.SHOPPING_BASKET || !basketStack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
		{
			return false;
		}
		
		IItemHandler itemHandler = basketStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			if (!itemHandler.getStackInSlot(i).isEmpty())
			{
				return false;
			}
		}
		
		baskets.push(basketStack);
		markDirty();
		if (world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
		return true;
	}
	
	public ItemStack popBasket()
	{
		if (baskets.size() <= 0)
		{
			return null;
		}
		
		ItemStack stack = baskets.pop();
		markDirty();
		if (world != null)
		{
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		}
		
		return stack;
	}
}
