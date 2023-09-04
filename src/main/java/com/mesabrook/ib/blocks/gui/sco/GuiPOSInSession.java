package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.mesabrook.ib.blocks.gui.GuiImageLabelButton;
import com.mesabrook.ib.blocks.gui.GuiImageLabelButton.ImageOrientation;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.net.sco.POSCancelSalePacket;
import com.mesabrook.ib.net.sco.POSRemoveItemPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class GuiPOSInSession extends GuiPOSMainBase {

	private static final int MAX_SLATS = 10;
	private GuiImageLabelButton cancelOrder;
	private ArrayList<ItemSlat> itemSlats = new ArrayList<>();
	public GuiPOSInSession(TileEntityRegister register) {
		super(register);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {
		super.doDraw(mouseX, mouseY, partialTicks);
		
		fontRenderer.drawString("Your Cart", innerLeft + 116, innerTop + 29, 0);
		
//		GlStateManager.disableTexture2D();
//		Tessellator tess = Tessellator.getInstance();
//		BufferBuilder builder = tess.getBuffer();
//		builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
//		builder.pos(innerLeft + 115, innerTop + 39, zLevel).color(0, 0, 0, 255).endVertex();
//		builder.pos(innerLeft + 115, innerTop + 39 + 111, zLevel).color(0, 0, 0, 255).endVertex();
//		tess.draw();
//		GlStateManager.enableTexture2D();
		
		for(ItemSlat slat : itemSlats)
		{
			slat.draw();
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		
		cancelOrder = new GuiImageLabelButton(0, innerLeft + 13, innerTop + 41, 83, 20, "      Cancel Order", new ResourceLocation(Reference.MODID, "textures/gui/sco/cancel_order.png"), 16, 16, 16, 16, ImageOrientation.Left)
						.setEnabledColor(0x373737)
						.setTextScale(0.75F);
		buttonList.add(cancelOrder);
		
		itemSlats.clear();

		IItemHandler itemHandler = register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for(int i = 0; i < MAX_SLATS; i++)
		{
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack.isEmpty())
			{
				break;
			}
			
			ItemSlat newSlat = new ItemSlat(i, innerLeft + 115, innerTop + 39 + ItemSlat.HEIGHT * i, 133, stack, fontRenderer);
			buttonList.add(newSlat.getRemoveButton());
			itemSlats.add(newSlat);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		TileEntityRegister.RegisterItemHandler itemHandler = (TileEntityRegister.RegisterItemHandler)register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if (button == cancelOrder)
		{
			POSCancelSalePacket cancelSale = new POSCancelSalePacket();
			cancelSale.pos = register.getPos();
			PacketHandler.INSTANCE.sendToServer(cancelSale);
			
			for(int i = 0; i < itemHandler.getSlots(); i++)
			{
				itemHandler.extractItemInternalOnly(i);
			}
			register.setRegisterStatus(RegisterStatuses.Online);
			
			mc.displayGuiScreen(new GuiPOSMainWelcome(register));
			return;
		}
		
		ItemSlat slat = null;
		for(ItemSlat itemSlat : itemSlats)
		{
			if (slat != null)
			{
				itemSlat.setY(itemSlat.y - ItemSlat.HEIGHT);
			}
			
			if (itemSlat.getRemoveButton() == button)
			{
				slat = itemSlat;
			}
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
			}
		}
	}
	
	private static class ItemSlat
	{
		public static final int HEIGHT = 11;
		
		private int slotIndex;
		private ItemStack stack;
		private int x;
		private int y;
		private int width;
		private FontRenderer fontRenderer;
		
		private String stackName;
		private GuiImageLabelButton removeButton;
		
		public ItemSlat(int slotIndex, int x, int y, int width, ItemStack stack, FontRenderer fontRenderer) {
			this.slotIndex = slotIndex;
			this.x = x;
			this.y = y;
			this.width = width;
			this.stack = stack;
			this.fontRenderer = fontRenderer;
			
			stackName = stack.getDisplayName();
			int stackCount = stack.getCount();
			if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				ISecuredItem secItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
				stackName = secItem.getInnerStack().getDisplayName();
				stackCount = secItem.getInnerStack().getCount();
			}
			
			int stackNameMaxWidth = fontRenderer.getStringWidth(stackName);
			if (stackNameMaxWidth > (width * 0.8) * 2)
			{
				stackNameMaxWidth = (int)((width * 0.8) * 2);
			}
			stackName = fontRenderer.trimStringToWidth(stackName, stackNameMaxWidth);
			if (stackCount > 1)
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

		public void draw()
		{
			GlStateManager.scale(0.5, 0.5, 1);
			fontRenderer.drawString(stackName, x * 2 + 1, y * 2 + 1, 0);
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
