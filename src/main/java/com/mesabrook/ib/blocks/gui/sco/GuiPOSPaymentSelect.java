package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.net.sco.POSCancelSalePacket;
import com.mesabrook.ib.net.sco.POSChangeStatusClientToServerPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class GuiPOSPaymentSelect extends GuiPOSPaymentBase {

	String total = "";
	String tax = "";
	String due = "Calculating...";
	BigDecimal[] pricesBySlot;
	
	private GuiImageLabelButton payByCard;
	private GuiImageLabelButton payByCash;
	private GuiImageLabelButton cancel;
	private GuiImageLabelButton back;
	
	public GuiPOSPaymentSelect(TileEntityRegister register) {
		super(register);
		IItemHandler handler = register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		pricesBySlot = new BigDecimal[handler.getSlots()];
	}

	@Override
	public void initGui() {
		super.initGui();
		
		payByCard = new GuiImageLabelButton(0, innerLeft + innerWidth - 76, innerTop + 45, 66, 20, "Pay by Card     ", new ResourceLocation(Reference.MODID, "textures/cards/card_personal_blue.png"), 16, 16, 16, 16, ImageOrientation.Right).setEnabledColor(0x00C800).setTextScale(0.75F);
		payByCash = new GuiImageLabelButton(0, innerLeft + innerWidth - 76, innerTop + 66, 66, 20, "Pay by Cash     ", new ResourceLocation(Reference.MODID, "textures/items/one_dollar.png"), 16, 16, 16, 16, ImageOrientation.Right).setEnabledColor(0x00C800).setTextScale(0.75F);
		cancel = new GuiImageLabelButton(0, innerLeft + innerWidth - 76, innerTop + 87, 66, 20, "   Cancel", new ResourceLocation(Reference.MODID, "textures/gui/sco/cancel_order.png"), 16, 16, 16, 16, ImageOrientation.Left).setEnabledColor(0xFF0000);
		back = new GuiImageLabelButton(0, innerLeft + innerWidth - 76, innerTop + 108, 66, 20, "   Back", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_left.png"), 16, 16, 16, 16, ImageOrientation.Left);
		
		buttonList.addAll(new ImmutableList.Builder<GuiButton>()
				.add(payByCard)
				.add(payByCash)
				.add(cancel)
				.add(back)
				.build());
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		if (register.getTenderFailureMessage().isEmpty())
		{
			return;
		}
		
		List<String> errorList = fontRenderer.listFormattedStringToWidth(register.getTenderFailureMessage(), 160);
		ArrayList<String> stringList = new ArrayList<>();
		for(int i = 0; i < 3; i++)
		{
			if (errorList.size() <= i)
			{
				break;
			}
			
			stringList.add(errorList.get(i));
		}
		
		for(int i = 0; i < stringList.size(); i++)
		{
			String errorLine = stringList.get(i);
			fontRenderer.drawString(errorLine, innerLeft + 13, innerTop + 103 + fontRenderer.FONT_HEIGHT * i, 0xFF0000);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == payByCard)
		{
			POSChangeStatusClientToServerPacket statusChange = new POSChangeStatusClientToServerPacket();
			statusChange.pos = register.getPos();
			statusChange.status = RegisterStatuses.PaymentCard;
			PacketHandler.INSTANCE.sendToServer(statusChange);
			
			mc.displayGuiScreen(new GuiPOSPaymentCard(register));
		}
		else if (button == payByCash)
		{
			POSChangeStatusClientToServerPacket statusChange = new POSChangeStatusClientToServerPacket();
			statusChange.pos = register.getPos();
			statusChange.status = RegisterStatuses.PaymentCash;
			PacketHandler.INSTANCE.sendToServer(statusChange);
			
			mc.displayGuiScreen(new GuiPOSPaymentCash(register));
		}
		else if (button == cancel)
		{
			POSCancelSalePacket cancelSale = new POSCancelSalePacket();
			cancelSale.pos = register.getPos();
			PacketHandler.INSTANCE.sendToServer(cancelSale);
			
			mc.displayGuiScreen(new GuiPOSMainWelcome(register));
		}
		else if (button == back)
		{
			POSChangeStatusClientToServerPacket statusChange = new POSChangeStatusClientToServerPacket();
			statusChange.pos = register.getPos();
			statusChange.status = RegisterStatuses.InSession;
			PacketHandler.INSTANCE.sendToServer(statusChange);
			
			mc.displayGuiScreen(new GuiPOSInSession(register));
		}
	}
	
	@Override
	protected void updatePrices() {
		super.updatePrices();
	}
}
