package com.mesabrook.ib.apimodels.company;

import java.math.BigDecimal;

import com.mesabrook.ib.apimodels.mesasys.Item;

public class LocationItem {
	public long LocationItemID;
	public long LocationID;
	public long ItemID;
	public Item Item;
	public short Quantity;
	public BigDecimal BasePrice;
	public PromotionLocationItem CurrentPromotionLocationItem;
}
