package com.mesabrook.ib.apimodels.company;

import java.math.BigDecimal;

import com.mesabrook.ib.apimodels.mesasys.Item;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class LocationItem implements INBTSerializable<NBTTagCompound>{
	public long LocationItemID;
	public long LocationID;
	public long ItemID;
	public Item Item;
	public short Quantity;
	public BigDecimal BasePrice;
	public PromotionLocationItem CurrentPromotionLocationItem;
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong("LocationItemID", LocationItemID);
		tag.setLong("LocationID", LocationID);
		tag.setLong("ItemID", ItemID);
		if (Item != null)
		{
			tag.setLong("Item.ItemID", Item.ItemID);
			tag.setString("Item.Name", Item.Name);
			tag.setBoolean("Item.IsFluid", Item.IsFluid);
		}
		
		tag.setShort("Quantity", Quantity);
		if (BasePrice == null)
		{
			tag.setString("BasePrice", "");
		}
		else
		{
			tag.setString("BasePrice", BasePrice.toPlainString());
		}
		
		if (CurrentPromotionLocationItem != null)
		{
			tag.setLong("CurrentPromotionLocationItem.LocationItemID", CurrentPromotionLocationItem.PromotionLocationItemID);
			tag.setLong("CurrentPromotionLocationItem.LocationID", CurrentPromotionLocationItem.PromotionID);
			tag.setLong("CurrentPromotionLocationItem.LocationItemID", CurrentPromotionLocationItem.LocationItemID);
			if (CurrentPromotionLocationItem.PromotionPrice == null)
			{
				tag.setString("CurrentPromotionLocationItem.PromotionPrice", "");
			}
			else
			{
				tag.setString("CurrentPromotionLocationItem.PromotionPrice", CurrentPromotionLocationItem.PromotionPrice.toPlainString());
			}
		}
		
		return tag;
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		LocationItemID = nbt.getLong("LocationItemID");
		LocationID = nbt.getLong("LocationID");
		ItemID = nbt.getLong("ItemID");
		Item = new Item();
		Item.ItemID = nbt.getLong("Item.ItemID");
		Item.Name = nbt.getString("Item.Name");
		Item.IsFluid = nbt.getBoolean("Item.IsFluid");
		Quantity = nbt.getShort("Quantity");
		BasePrice = null;
		try
		{
			BasePrice = new BigDecimal(nbt.getString("BasePrice"));
		}
		catch(Exception ex) {}
		
		CurrentPromotionLocationItem = new PromotionLocationItem();
		CurrentPromotionLocationItem.PromotionLocationItemID = nbt.getLong("CurrentPromotionLocationItem.PromotionLocationItemID");
		CurrentPromotionLocationItem.PromotionID = nbt.getLong("CurrentPromotionLocationItem.PromotionID");
		CurrentPromotionLocationItem.LocationItemID = nbt.getLong("CurrentPromotionLocationItem.LocationItemID");
		CurrentPromotionLocationItem.PromotionPrice = null;
		try
		{
			CurrentPromotionLocationItem.PromotionPrice = new BigDecimal(nbt.getString("CurrentPromotionLocationItem.PromotionPrice"));
		}
		catch(Exception ex) {}
	}
}
