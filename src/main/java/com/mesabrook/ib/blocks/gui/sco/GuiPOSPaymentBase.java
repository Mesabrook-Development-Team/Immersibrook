package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.net.sco.POSCancelSalePacket;
import com.mesabrook.ib.net.sco.POSChangeStatusClientToServerPacket;
import com.mesabrook.ib.net.sco.POSFetchPricePacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class GuiPOSPaymentBase extends GuiPOSMainBase {

	String total = "";
	String tax = "";
	String due = "Calculating...";
	
	public GuiPOSPaymentBase(TileEntityRegister register) {
		super(register);
	}
	
	@Override
	protected String getBackingTextureName() {
		return "sco_pay.png";
	}
	
	public boolean isPositionForRegister(BlockPos pos)
	{
		return register.getPos().toLong() == pos.toLong();
	}

	@Override
	public void initGui() {
		super.initGui();
		
		updatePrices();
	}
	
	protected void updatePrices()
	{
		BigDecimal runningTotal = register.getCurrentTotal();		
		this.total = runningTotal.setScale(2, RoundingMode.HALF_UP).toPlainString();
		this.tax = runningTotal.multiply(register.getCurrentTaxRate().divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP).toPlainString();
		this.due = runningTotal.add(new BigDecimal(this.tax)).subtract(register.getTenderedAmount()).setScale(2, RoundingMode.HALF_UP).toPlainString();
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		drawRect(innerLeft + 11, innerTop + 36, innerLeft + 176, innerTop + 46, 0xFFC8C8C8);
		drawRect(innerLeft + 11, innerTop + 57, innerLeft + 176, innerTop + 68, 0xFFC8C8C8);
		drawRect(innerLeft + 11, innerTop + 80, innerLeft + 176, innerTop + 90, 0xFFC8C8C8);
		
		fontRenderer.drawString("Total:", innerLeft + 14, innerTop + 37, 0);
		fontRenderer.drawString("Tax:", innerLeft + 14, innerTop + 48, 0);
		fontRenderer.drawString("Tendered:", innerLeft + 14, innerTop + 59, 0);
		fontRenderer.drawString("Due:", innerLeft + 14, innerTop + 81, 0);
		
		int stringWidth = fontRenderer.getStringWidth(total);
		fontRenderer.drawString(total, innerLeft + 176 - stringWidth, innerTop + 37, 0);
		stringWidth = fontRenderer.getStringWidth(tax);
		fontRenderer.drawString(tax, innerLeft + 176 - stringWidth, innerTop + 48, 0);
		String tendered = register.getTenderedAmount().setScale(2, RoundingMode.HALF_UP).toPlainString();
		stringWidth = fontRenderer.getStringWidth(tendered);
		fontRenderer.drawString(tendered, innerLeft + 176 - stringWidth, innerTop + 59, 0);
		stringWidth = fontRenderer.getStringWidth(due);
		fontRenderer.drawString(due, innerLeft + 176 - stringWidth, innerTop + 81, 0);
	}
}