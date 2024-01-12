package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterItemHandler;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.items.commerce.ItemRegisterFluidWrapper;
import com.mesabrook.ib.net.sco.POSCancelSalePacket;
import com.mesabrook.ib.net.sco.POSChangeStatusClientToServerPacket;
import com.mesabrook.ib.net.sco.POSRemoveItemPacket;
import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.CapabilityItemHandler;

public class GuiPOSInSession extends GuiPOSMainBase {

	private static final int MAX_SLATS = 10;
	private GuiImageLabelButton cancelOrder;
	private GuiImageLabelButton fluidPurchase;
	private GuiImageLabelButton adminMode;
	private ArrayList<ItemSlat> itemSlats = new ArrayList<>();
	private int currentPage = -1;
	private String savingsTotal = "";
	private String subTotal = "";
	private String taxAmount = "";
	private String total = "Calculating...";
	
	private ImageButton nextPage;
	private ImageButton prevPage;
	private GuiImageLabelButton pay;
	public GuiPOSInSession(TileEntityRegister register) {
		super(register);
	}
	
	public boolean isPositionForRegister(BlockPos pos)
	{
		return register.getPos().toLong() == pos.toLong();
	}
	
	private IndependentTimer refreshTimer=  new IndependentTimer();
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		refreshTimer.update();
		if (refreshTimer.getElapsedTime() >= 500)
		{			
			initGui();
			refreshTimer.reset();
		}
		
		fontRenderer.drawString("Your Cart", innerLeft + 116, innerTop + 29, 0);
		
		for(ItemSlat slat : itemSlats)
		{
			slat.draw();
		}
		
		GlStateManager.scale(0.5, 0.5, 1);
		String pageText = "" + (currentPage + 1) + "/" + (((itemSlats.size() - 1) / MAX_SLATS) + 1);
		int textWidth = fontRenderer.getStringWidth(pageText);
		fontRenderer.drawString(pageText, (innerLeft + 121 + ((innerLeft + innerWidth - 14) - (innerLeft + 121)) / 2) * 2 - (textWidth / 2), (innerTop + 153) * 2, 0x74A3E0);
		
		fontRenderer.drawString("Subtotal", (innerLeft + 114) * 2, (innerTop + 162) * 2, 0);
		textWidth = fontRenderer.getStringWidth(subTotal);
		fontRenderer.drawString(subTotal, (int)((innerLeft + innerWidth - 6) * 2 - textWidth), (innerTop + 162) * 2, 0);
		
		fontRenderer.drawString("Tax", (innerLeft + 114) * 2, (innerTop + 168) * 2, 0);
		textWidth = fontRenderer.getStringWidth(taxAmount);
		fontRenderer.drawString(taxAmount, (int)((innerLeft + innerWidth - 6) * 2 - textWidth), (innerTop + 168) * 2, 0);
		
		GlStateManager.scale(2, 2, 1);
		
		double upScale = 1 / 0.75;
		GlStateManager.scale(0.75, 0.75, 1);
		fontRenderer.drawString("Grand Total", (int)((innerLeft + 114) * upScale), (int)((innerTop + 174) * upScale), 0);
		textWidth = fontRenderer.getStringWidth(total);
		fontRenderer.drawString(total, (int)((innerLeft + innerWidth - 6) * upScale - textWidth), (int)((innerTop + 174) * upScale), 0);
		GlStateManager.scale(upScale, upScale, 1);
	}

	@Override
	public void initGui() {
		super.initGui();
		
		cancelOrder = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 41, 83, 20, "      Cancel Order", new ResourceLocation(Reference.MODID, "textures/gui/sco/cancel_order.png"), 16, 16, 16, 16, ImageOrientation.Left)
						.setEnabledColor(0x373737)
						.setTextScale(0.75F);
		fluidPurchase = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 61, 83, 20, "     Pay for Fluids", new ItemStack(Items.BUCKET), ImageOrientation.Left)
						.setEnabledColor(0x373737)
						.setTextScale(0.75F);
		fluidPurchase.visible = register.getTrackedFluidData().size() > 0;
		
		adminMode = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 131, 83, 20, "      Admin Mode", new ResourceLocation(Reference.MODID, "textures/gui/cog.png"), 16, 16, 16, 16, ImageOrientation.Left)
				.setEnabledColor(0x373737)
				.setTextScale(0.75F);
		adminMode.visible = false;
		
		if (mc.player.hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
		{
			IEmployeeCapability emp = mc.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			adminMode.visible = emp != null && emp.manageRegisters();
		}
		
		buttonList.clear();
		buttonList.add(cancelOrder);
		buttonList.add(fluidPurchase);
		buttonList.add(adminMode);
		
		itemSlats.clear();

		TileEntityRegister.RegisterItemHandler itemHandler = (TileEntityRegister.RegisterItemHandler)register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int slatCount = 0;
		for(int i = 0; i < itemHandler.getSlots(); i++)
		{
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack.isEmpty())
			{
				break;
			}
			
			BigDecimal price = itemHandler.getPrice(i);
			
			int pageShownOn = slatCount / MAX_SLATS;
			int yMultiplier = slatCount % MAX_SLATS;
			
			ItemSlat newSlat = new ItemSlat(i, innerLeft + 115, innerTop + 39 + ItemSlat.HEIGHT * yMultiplier, 133, stack, fontRenderer);
			if (price == null)
			{
				newSlat.setPriceNotFound(true);
			}
			else
			{
				newSlat.setPrice(price);
				newSlat.setRegularPrice(itemHandler.getRegularPrice(i));
			}
			newSlat.setVisible(false);
			buttonList.add(newSlat.getRemoveButton());
			itemSlats.add(newSlat);
			slatCount++;
		}
		
		if (currentPage == -1)
		{
			currentPage = (slatCount - 1) / MAX_SLATS;
			if (currentPage < 0)
			{
				currentPage = 0;
			}
		}
			
		for(int i = currentPage * MAX_SLATS; i < (currentPage + 1) * MAX_SLATS; i++)
		{
			if (i >= itemSlats.size())
			{
				break;
			}
			
			itemSlats.get(i).setVisible(true);
		}
		
		nextPage = new ImageButton(0, innerLeft + innerWidth - 14, innerTop + 150, 8, 8, new ResourceLocation(Reference.MODID, "textures/gui/sco/resultset_next.png"), 16, 16, 16, 16);
		prevPage = new ImageButton(0, innerLeft + 113, innerTop + 150, 8, 8, new ResourceLocation(Reference.MODID, "textures/gui/sco/resultset_previous.png"), 16, 16, 16, 16);
		pay = new GuiImageLabelButton(0, innerLeft + innerWidth - 52, innerTop + innerHeight - 21, 46, 15, "Pay  ", new ResourceLocation(Reference.MODID, "textures/gui/sco/arrow_right.png"), 10, 10, 10, 10, ImageOrientation.Right)
				.setEnabledColor(0x00FF00)
				.setTextScale(0.8F);
		
		prevPage.enabled = currentPage != 0;
		nextPage.enabled = currentPage < (slatCount - 1) / MAX_SLATS;
		pay.enabled = false;
		
		buttonList.add(prevPage);
		buttonList.add(nextPage);
		buttonList.add(pay);
		
		updateTotals();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == adminMode)
		{
			mc.displayGuiScreen(new GuiPOSAdmin(register));
			return;
		}
		
		TileEntityRegister.RegisterItemHandler itemHandler = (TileEntityRegister.RegisterItemHandler)register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if (button == cancelOrder)
		{
			POSCancelSalePacket cancelSale = new POSCancelSalePacket();
			cancelSale.pos = register.getPos();
			PacketHandler.INSTANCE.sendToServer(cancelSale);
			
			itemHandler.dumpInventory();
			register.setRegisterStatus(RegisterStatuses.Online);
			
			mc.displayGuiScreen(new GuiPOSMainWelcome(register));
			return;
		}
		
		if (button == fluidPurchase)
		{
			mc.displayGuiScreen(new GuiPOSSelectFluid(register));
			return;
		}
		
		if (button == nextPage && currentPage < itemSlats.size() / MAX_SLATS)
		{
			currentPage++;
		}
		
		if (button == prevPage && currentPage > 0)
		{
			currentPage--;
		}
		
		ItemSlat slat = null;
		for(int i = 0; i < itemSlats.size(); i++)
		{
			ItemSlat itemSlat = itemSlats.get(i);
			int effectiveIndex = i;
			if (slat != null)
			{
				effectiveIndex--;
			}
			
			if (itemSlat.getRemoveButton() == button)
			{
				slat = itemSlat;
			}
			
			int effectivePosition = effectiveIndex % MAX_SLATS;
			itemSlat.setY(innerTop + 39 + ItemSlat.HEIGHT * effectivePosition);
		}
		
		if (slat != null)
		{
			POSRemoveItemPacket removeItemPacket = new POSRemoveItemPacket();
			removeItemPacket.index = slat.slotIndex;
			removeItemPacket.pos = register.getPos();
			PacketHandler.INSTANCE.sendToServer(removeItemPacket);
			
			itemHandler.extractItemInternalOnly(slat.slotIndex);
			itemSlats.remove(slat);
			buttonList.remove(slat.getRemoveButton());
			if (!register.hasItemsForSession())
			{
				mc.displayGuiScreen(new GuiPOSMainWelcome(register));
				return;
			}
			
			if (itemSlats.stream().allMatch(is -> is.price != null && !is.priceNotFound))
			{
				updateTotals();
			}
		}
		
		for(int i = 0; i < itemSlats.size(); i++)
		{
			ItemSlat slatForVisible = itemSlats.get(i);
			slatForVisible.setVisible(i / MAX_SLATS == currentPage);
		}		
		prevPage.enabled = currentPage != 0;
		nextPage.enabled = (itemSlats.size() - 1) / MAX_SLATS < 0 ? false : currentPage < (itemSlats.size() - 1) / MAX_SLATS;
		
		if (button == pay)
		{
			POSChangeStatusClientToServerPacket changeStatus = new POSChangeStatusClientToServerPacket();
			changeStatus.pos = register.getPos();
			changeStatus.status = RegisterStatuses.PaymentSelect;
			PacketHandler.INSTANCE.sendToServer(changeStatus);
			
			mc.displayGuiScreen(new GuiPOSPaymentSelect(register));
		}
	}
	
	private void updateTotals()
	{
		BigDecimal subtotal = new BigDecimal("0.00");
		BigDecimal savingsTotal = new BigDecimal("0.00");
		for(ItemSlat slat : itemSlats)
		{
			if (slat.priceNotFound)
			{
				this.subTotal = "";
				this.taxAmount = "";
				this.total = "Invalid Item";
				pay.enabled = false;
				return;
			}
			subtotal = subtotal.add(slat.price);
			savingsTotal = savingsTotal.add(slat.regularPrice.subtract(slat.price));
		}
		
		this.savingsTotal = savingsTotal.toPlainString();
		this.subTotal = subtotal.toPlainString();
		this.taxAmount = subtotal.multiply(register.getCurrentTaxRate().divide(new BigDecimal("100.00"))).setScale(2, RoundingMode.HALF_UP).toPlainString();
		this.total = subtotal.add(new BigDecimal(this.taxAmount)).toPlainString();
		pay.enabled = itemSlats.size() > 0;
	}
	
	private static class ItemSlat
	{
		public static final int HEIGHT = 11;
		
		private int slotIndex;
		private ItemStack stack;
		private int x;
		private int y;
		private int width;
		private boolean visible = true;
		private FontRenderer fontRenderer;
		private boolean priceNotFound;
		private BigDecimal price;
		private BigDecimal regularPrice;
		
		private String stackName;
		private GuiImageLabelButton removeButton;
		
		public ItemSlat(int slotIndex, int x, int y, int width, ItemStack stack, FontRenderer fontRenderer) {
			this.slotIndex = slotIndex;
			this.x = x;
			this.y = y;
			this.width = width;
			this.stack = stack;
			this.fontRenderer = fontRenderer;
			
			String stackCount;
			stackName = stack.getDisplayName();
			stackCount = Integer.toString(stack.getCount());
			if (stack.hasCapability(ItemRegisterFluidWrapper.CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null))
			{
				ItemRegisterFluidWrapper.IRegisterFluidWrapper wrapper = stack.getCapability(ItemRegisterFluidWrapper.CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null);
				if (wrapper.getFluidStack() == null)
				{
					stackName = "Unknown";
					stackCount = "0mb";
				}
				else
				{
					stackName = wrapper.getFluidStack().getLocalizedName();
					stackCount = wrapper.getFluidStack().amount + "mb";
				}
			}
			else if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				ISecuredItem secItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
				stackName = secItem.getInnerStack().getDisplayName();
				stackCount = Integer.toString(secItem.getInnerStack().getCount());
			}
			
			int stackNameMaxWidth = fontRenderer.getStringWidth(stackName);
			if (stackNameMaxWidth > (width * 0.8) * 2)
			{
				stackNameMaxWidth = (int)((width * 0.8) * 2);
			}
			stackName = fontRenderer.trimStringToWidth(stackName, stackNameMaxWidth);
			if (!stackCount.equalsIgnoreCase(Integer.toString(1)))
			{
				stackName += " (x" + stackCount + ")";
			}
			
			removeButton = new GuiImageLabelButton(0, (int)(x + width - (width * 0.2)), y + HEIGHT / 2, (int)(width * 0.2), HEIGHT / 2, "Remove     ", new ResourceLocation(Reference.MODID, "textures/gui/sco/red_x.png"), 5, 5, 16, 16, ImageOrientation.Right)
							.setTextScale(0.4F)
							.setEnabledColor(0xFF0000);
		}
		
		public ItemStack getStack()
		{
			return stack;
		}
		
		public void setY(int y)
		{
			this.y = y;
			this.removeButton.y = y + HEIGHT / 2;
		}
		
		public void setPrice(BigDecimal price)
		{
			this.price = price;
		}
		
		public BigDecimal getPrice()
		{
			return price;
		}
		
		public BigDecimal getRegularPrice() {
			return regularPrice;
		}

		public void setRegularPrice(BigDecimal regularPrice) {
			this.regularPrice = regularPrice;
		}

		public boolean getPriceNotFound()
		{
			return priceNotFound;
		}
		
		public void setPriceNotFound(boolean priceNotFound)
		{
			this.priceNotFound = priceNotFound;
		}

		public boolean isVisible() {
			return visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
			this.removeButton.visible = visible;
		}

		public void draw()
		{
			if (!visible)
			{
				return;
			}
			GlStateManager.scale(0.5, 0.5, 1);
			fontRenderer.drawString(stackName, x * 2 + 1, y * 2 + 1, 0);
			if (!getPriceNotFound() && price != null && regularPrice != null)
			{
				boolean hasPromotionPrice = price.compareTo(regularPrice) != 0;
				
				String priceText = hasPromotionPrice ? TextFormatting.STRIKETHROUGH + "" : "";
				priceText += regularPrice.toPlainString();
				
				if (hasPromotionPrice)
				{
					priceText += TextFormatting.RESET + " "  + TextFormatting.DARK_GREEN + price.toPlainString();
				}
				
				int stringWidth = fontRenderer.getStringWidth(priceText);
				fontRenderer.drawString(priceText, (x + width - 1) * 2 - stringWidth, y * 2 + 1, 0x888888);
				
				if (hasPromotionPrice)
				{
					priceText = "" + TextFormatting.DARK_GREEN +  "You saved " + regularPrice.subtract(price).toPlainString() + "!";
					fontRenderer.drawString(priceText, x * 2 + 1, y * 2 + 1 + fontRenderer.FONT_HEIGHT, 0);
				}
			}
			else if (getPriceNotFound())
			{
				int invalidWidth = fontRenderer.getStringWidth("Invalid Item! Please Remove");
				fontRenderer.drawString("Invalid Item! Please Remove", (x + width - 1) * 2 - invalidWidth, y * 2 + 1, 0xFF0000);
			}
			GlStateManager.scale(2, 2, 1);
			
			GlStateManager.disableTexture2D();
			Tessellator tess = Tessellator.getInstance();
			BufferBuilder builder = tess.getBuffer();
			builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
			builder.pos(x, y + HEIGHT, 0).color(0, 0, 0, 1F).endVertex();
			builder.pos(x + width, y + HEIGHT, 0).color(0, 0, 0, 1F).endVertex();
			tess.draw();
			GlStateManager.enableTexture2D();
		}
		
		public GuiButton getRemoveButton()
		{
			return removeButton;
		}
	}
}
