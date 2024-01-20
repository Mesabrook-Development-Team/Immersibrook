package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.math.BigDecimal;

import org.lwjgl.input.Keyboard;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.blocks.container.ContainerTaggingStation;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.net.sco.QueryPricePacket;
import com.mesabrook.ib.net.sco.TaggingStationChangeTabsPacket;
import com.mesabrook.ib.net.sco.TaggingStationDistanceChangedPacket;
import com.mesabrook.ib.net.sco.TaggingStationSetPricePacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiTaggingStation extends GuiContainer {
	
	private final ResourceLocation tagAdd = new ResourceLocation(Reference.MODID, "textures/gui/sco/security_tagging_station_add.png");
	private final ResourceLocation tagRemove = new ResourceLocation(Reference.MODID, "textures/gui/sco/security_tagging_station_remove.png");
	private final ResourceLocation guiTexture = new ResourceLocation(Reference.MODID, "textures/gui/sco/security_tagging_station.png");
	private final ResourceLocation creative_inventory_tabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
	private final ContainerTaggingStation tagStationContainer;
	private final BlockPos taggingPos;
	private final String distanceLabel = "Max Distance: ";
	private int distanceLabelLength;
	GuiTextField distance;
	GuiTextField priceSetBox;
	GuiButtonExt priceSetButton;
	GuiButtonExt priceButton;
	
	public GuiTaggingStation(InventoryPlayer playerInventory, BlockPos pos) {
		super(new ContainerTaggingStation(playerInventory, pos));
		tagStationContainer = (ContainerTaggingStation)this.inventorySlots;
		taggingPos = pos;
		
		this.xSize = 175;
		this.ySize = 168;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		distanceLabelLength = fontRenderer.getStringWidth(distanceLabel);
		
		Slot slot = tagStationContainer.getSlotFromInventory(tagStationContainer.craftResult, 0);
		distance = new GuiTextField(0, fontRenderer, guiLeft + distanceLabelLength + 2, guiTop + slot.yPos + 26, 36, fontRenderer.FONT_HEIGHT + 3);
		distance.setText(Double.toString(tagStationContainer.getResetDistance()));
		
		priceButton = new GuiButtonExt(0, distance.x + distance.width + 6, distance.y, 56, distance.height, "Set Price");
		priceButton.enabled = false;
		buttonList.add(priceButton);
		
		priceSetBox = new GuiTextField(0, fontRenderer, priceButton.x, priceButton.y + priceButton.height + 10, priceButton.width, fontRenderer.FONT_HEIGHT + 3);
		priceSetBox.setVisible(false);
		priceSetButton = new GuiButtonExt(0, priceSetBox.x, priceSetBox.y + priceSetBox.height + 4, priceSetBox.width, priceButton.height, "Set");
		priceSetButton.visible = false;
		buttonList.add(priceSetButton);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(guiTexture);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop + 28, 0, 0, this.xSize, this.ySize - 28);
		
		this.mc.getTextureManager().bindTexture(creative_inventory_tabs);
		drawTexturedModalRect(guiLeft, guiTop, 0, 32, 27, 32);
		drawTexturedModalRect(guiLeft + 29, guiTop, 0, 0, 27, 28);
		
		this.mc.getTextureManager().bindTexture(tagAdd);
		drawScaledCustomSizeModalRect(guiLeft + 6, guiTop + 6, 0, 0, 249, 315, 16, 20, 249, 315);
		
		this.mc.getTextureManager().bindTexture(tagRemove);
		drawScaledCustomSizeModalRect(guiLeft + 35, guiTop + 6, 0, 0, 249, 315, 16, 20, 249, 315);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		if (mouseY >= guiTop && mouseY < guiTop + 28)
		{
			if (mouseX >= guiLeft && mouseX <= guiLeft + 27)
			{
				drawHoveringText("Tagging Mode", mouseX - guiLeft, mouseY - guiTop);
			}
			
			if (mouseX >= guiLeft + 29 && mouseX <= guiLeft + 56)
			{
				drawHoveringText("Untagging Mode", mouseX - guiLeft, mouseY - guiTop);
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		fontRenderer.drawString(distanceLabel, guiLeft + 4, distance.y + 2, 0x777777);
		distance.drawTextBox();
		if (priceSetBox.getVisible())
		{
			
			GlStateManager.translate(0, 0, 300);
			drawDefaultBackground();
			GlStateManager.color(1, 1, 1);
			mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/sco/gui_128_blank.png"));
			drawModalRectWithCustomSizedTexture(priceSetBox.x - 6,
												priceSetBox.y - 6,
												0,
												0,
												(priceSetBox.width + 12) / 2, 
												((priceSetButton.y + priceSetButton.height + 6) - (priceSetBox.y - 6)) / 2,
												128, 
												128); // Upper Left
			drawModalRectWithCustomSizedTexture((priceSetBox.x - 6) + ((priceSetBox.width + 12) / 2),
												priceSetBox.y - 6,
												128 - (priceSetBox.width + 12) / 2,
												0,
												(priceSetBox.width + 12) / 2, 
												((priceSetButton.y + priceSetButton.height + 6) - (priceSetBox.y - 6)) / 2,
												128, 
												128); // Upper right
			drawModalRectWithCustomSizedTexture(priceSetBox.x - 6,
												priceSetBox.y - 6 + ((priceSetButton.y + priceSetButton.height + 6) - (priceSetBox.y - 6)) / 2,
												0,
												128 - ((priceSetButton.y + priceSetButton.height + 6) - (priceSetBox.y - 6)) / 2,
												(priceSetBox.width + 12) / 2, 
												((priceSetButton.y + priceSetButton.height + 6) - (priceSetBox.y - 6)) / 2,
												128, 
												128); // Lower left
			drawModalRectWithCustomSizedTexture((priceSetBox.x - 6) + ((priceSetBox.width + 12) / 2),
												priceSetBox.y - 6 + ((priceSetButton.y + priceSetButton.height + 6) - (priceSetBox.y - 6)) / 2,
												128 - (priceSetBox.width + 12) / 2,
												128 - ((priceSetButton.y + priceSetButton.height + 6) - (priceSetBox.y - 6)) / 2,
												(priceSetBox.width + 12) / 2, 
												((priceSetButton.y + priceSetButton.height + 6) - (priceSetBox.y - 6)) / 2,
												128, 
												128); // Lower right
			
			priceSetBox.drawTextBox();
			priceSetButton.drawButton(mc, mouseX, mouseY, partialTicks); // draw it again so it appears on top
			return;
		}
		else
		{
			renderHoveredToolTip(mouseX, mouseY);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		distance.mouseClicked(mouseX, mouseY, mouseButton); // handle first so that the result is set properly
		priceSetBox.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (priceSetBox.getVisible())
		{
			if (priceSetButton.mousePressed(mc, mouseX, mouseY))
			{
                this.selectedButton = priceSetButton;
                priceSetButton.playPressSound(this.mc.getSoundHandler());
                this.actionPerformed(priceSetButton);
			}
			
			return;
		}
		
		if (!distance.isFocused())
		{
			try
			{
				Double.parseDouble(distance.getText());
			}
			catch(Exception ex)
			{
				distance.setText("");
			}
		}
		notifyResetDistance();
		
		if (mouseY >= guiTop && mouseY < guiTop + 28 && mouseX >= guiLeft + 29 && mouseX <= guiLeft + 56)
		{
			TaggingStationChangeTabsPacket changeTabs = new TaggingStationChangeTabsPacket();
			changeTabs.taggingPos = taggingPos;
			changeTabs.toUntag = true;
			PacketHandler.INSTANCE.sendToServer(changeTabs);
			mc.player.openGui(Main.instance, Reference.GUI_TAGGING_STATION_UNTAG, mc.player.world, taggingPos.getX(), taggingPos.getX(), taggingPos.getX());
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	ItemStack previousStack = ItemStack.EMPTY;
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		
		ItemStack stack = tagStationContainer.craftResult.getStackInSlot(0);
		
		if (!stack.isEmpty() && !ItemStack.areItemStacksEqual(stack, previousStack))
		{
			priceButton.displayString = "Fetching...";
			priceButton.enabled = false;
			
			QueryPricePacket query = new QueryPricePacket();
			query.placementID = 0;
			query.shelfPos = taggingPos;
			
			if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				stack = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack();
			}
			
			query.stack = stack;
			PacketHandler.INSTANCE.sendToServer(query);
		}
		else if (stack.isEmpty())
		{
			priceButton.displayString = "Set Price";
			priceButton.enabled = false;
		}
		
		previousStack = tagStationContainer.craftResult.getStackInSlot(0).copy();
	}
	
	private void notifyResetDistance()
	{
		double distanceVal = 0;
		try
		{
			distanceVal = Double.parseDouble(distance.getText());
		}
		catch(Exception ex) {}
		
		TaggingStationDistanceChangedPacket notifyServer = new TaggingStationDistanceChangedPacket();
		notifyServer.distance = distanceVal;
		PacketHandler.INSTANCE.sendToServer(notifyServer);
		
		tagStationContainer.setResetDistance(distanceVal);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (priceSetBox.getVisible())
		{
			if (keyCode == 1)
			{
				priceSetBox.setVisible(false);
				priceSetButton.visible = false;
			}
			else if (keyCode == Keyboard.KEY_NUMPADENTER || keyCode == Keyboard.KEY_RETURN)
			{
				actionPerformed(priceSetButton);
			}
			
			priceSetBox.textboxKeyTyped(typedChar, keyCode);
			return;
		}
		
		super.keyTyped(typedChar, keyCode);
		distance.textboxKeyTyped(typedChar, keyCode);
		notifyResetDistance();
	}

	public void onPriceResult(LocationItem item)
	{
		ItemStack stack = tagStationContainer.craftResult.getStackInSlot(0);
		IEmployeeCapability emp = mc.player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		
		if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
		{
			stack = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack();
		}
		
		if (stack.isEmpty())
		{
			return;
		}
		
		if (item == null || item.Item == null || item.Item.Name == null || item.Item.Name.isEmpty())
		{
			priceButton.displayString = "Set Price";
			priceButton.enabled = emp.managePrices();
			return;
		}
		
		if (stack.getDisplayName().equalsIgnoreCase(item.Item.Name) && stack.getCount() == item.Quantity)
		{
			priceButton.displayString = item.BasePrice.toPlainString();
			priceButton.enabled = false;
		}
		else
		{
			priceButton.displayString = "Set Price";
			priceButton.enabled = emp.managePrices();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button == priceButton)
		{
			priceSetBox.setText("");
			priceSetBox.setVisible(true);
			priceSetButton.visible = true;
			priceSetBox.setFocused(true);
		}
		
		if (button == priceSetButton)
		{
			BigDecimal price;
			try
			{
				price = new BigDecimal(priceSetBox.getText());
			}
			catch(Exception ex)
			{
				return;
			}
			
			ItemStack stack = tagStationContainer.craftResult.getStackInSlot(0);
			if (stack.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null))
			{
				stack = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null).getInnerStack();
			}
			
			TaggingStationSetPricePacket setPrice = new TaggingStationSetPricePacket();
			setPrice.price = price;
			setPrice.stack = stack;
			setPrice.tagStationPos = taggingPos;
			
			priceSetButton.visible = false;
			priceSetBox.setVisible(false);;
			priceButton.displayString = "Fetching...";
			priceButton.enabled = false;
			
			PacketHandler.INSTANCE.sendToServer(setPrice);
		}
	}
}
