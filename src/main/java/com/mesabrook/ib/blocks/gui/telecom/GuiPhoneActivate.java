package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.net.SoundPlayerAppInfoPacket;
import com.mesabrook.ib.net.telecom.ActivateNumberChosenPacket;
import com.mesabrook.ib.net.telecom.ActivatePhonePacket;
import com.mesabrook.ib.util.GuiUtil;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiPhoneActivate extends GuiScreen {
	
	boolean shouldCloseWindow = true;
	boolean needsToContactServer = true;
	ItemStack phoneStack;
	EnumHand hand;
	private String message = "";
	public ActivationScreens activationScreen = ActivationScreens.Message;
	private boolean isResend = false; 
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public GuiPhoneActivate(ItemStack phoneStack, EnumHand hand)
	{
		if (!(phoneStack.getItem() instanceof ItemPhone))
		{
			return;
		}
		this.phoneStack = phoneStack;
		this.hand = hand;		
		shouldCloseWindow = false;
		
		NBTTagCompound tag = phoneStack.getTagCompound();
		if (tag != null && tag.hasKey(Reference.PHONE_NUMBER_NBTKEY))
		{
			needsToContactServer = false;
		}
	}
	
	@Override
	public void initGui() {
		if (shouldCloseWindow)
		{
			Minecraft.getMinecraft().displayGuiScreen(null);
			return;
		}
		
		setMessage("Activating...");
		
		if (!needsToContactServer)
		{
			goToMainScreen();
			return;
		}
		
		// Setup options
		int workingY = (height / 2);
		workingY = workingY - (((fontRenderer.FONT_HEIGHT + 2) * 6) / 2) + fontRenderer.FONT_HEIGHT + 2;
		
		for(int i = 0; i < 5; i++)
		{
			ClickableLabel label = new ClickableLabel(i, width / 2, workingY);
			label.visible = false;
			buttonList.add(label);
			workingY += fontRenderer.FONT_HEIGHT + 2;
		}
		
		ActivatePhonePacket packet = new ActivatePhonePacket();
		packet.hand = hand.ordinal();
		PacketHandler.INSTANCE.sendToServer(packet);
	}
	
	private final int texWidth = 176;
	private final int texHeight = 222;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		//Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", "textures/gui/telecom/phone_bezel.png"));
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("wbtc", String.format("textures/gui/telecom/%s.png", ((ItemPhone)phoneStack.getItem()).getBezelTextureName())));
		drawScaledCustomSizeModalRect((width - texWidth) / 2, (height - texHeight) / 2, 0, 0, 352, 444, texWidth, texHeight, 512, 512);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if (activationScreen == ActivationScreens.Message || activationScreen == ActivationScreens.Activated)
		{
			drawCenteredString(fontRenderer, message, width / 2, height / 2, 0xFFFFFF);
		}
		else if (activationScreen == ActivationScreens.ChooseNumber)
		{
			drawOptions(mouseX, mouseY);
		}
		
		if (activationScreen == ActivationScreens.Activated)
		{
			mainScreenDelay++;
			
			if (mainScreenDelay >= 150)
			{
				GuiLockScreen home = new GuiLockScreen(Minecraft.getMinecraft().player.getHeldItem(hand), hand);
				Minecraft.getMinecraft().displayGuiScreen(home);

				SoundPlayerAppInfoPacket soundPacket = new SoundPlayerAppInfoPacket();
				soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
				soundPacket.soundName = "minedroid_startup";
				PacketHandler.INSTANCE.sendToServer(soundPacket);
			}
		}
	}
	
	public void setSelectablePhoneNumbers(int[] options, boolean isResend)
	{		
		this.isResend = isResend;
		for(int i = 0; i < 5; i++)
		{
			ClickableLabel label = (ClickableLabel)buttonList.get(i);
			label.setNumber(options[i]);
			label.visible = true;
			
			String numberText = Integer.toString(options[i]);
			numberText = numberText.substring(0, 3) + "-" + numberText.substring(3, 7);
			label.setLabel(numberText);
		}
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public void setActivationScreen(ActivationScreens screen)
	{
		this.activationScreen = screen;
		
		if (screen == ActivationScreens.Message)
		{
			for(GuiButton button : buttonList)
			{
				button.visible = false;
			}
		}
	}
	
	private void drawOptions(int mouseX, int mouseY)
	{
		int workingY = (height / 2);
		workingY = workingY - (((fontRenderer.FONT_HEIGHT + 2) * 6) / 2);
		
		GlStateManager.scale(0.75, 0.75, 0.75);
		drawCenteredString(fontRenderer, "Click to choose a phone number", (int)((width / 2) * (1 / 0.75)), (int)(workingY * (1 / 0.75)), 0xFFFFFF);
		GlStateManager.scale(1 / 0.75, 1 / 0.75, 1 / 0.75);
		
		if (isResend)
		{
			workingY += (fontRenderer.FONT_HEIGHT + 2) * 6;
			drawCenteredString(fontRenderer, "Number already in use!", width/2, workingY, 0xFF0000);
		}
	}
	
	private int mainScreenDelay = 0;
	public void goToMainScreen()
	{
		setMessage("Activated!");
		setActivationScreen(ActivationScreens.Activated);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id >= 0 && button.id < 5)
		{
			ClickableLabel label = (ClickableLabel)button;
			
			setActivationScreen(ActivationScreens.Message);
			setMessage("Activating...");
			isResend = false;
			
			ActivateNumberChosenPacket chosen = new ActivateNumberChosenPacket();
			chosen.hand = hand.ordinal();
			chosen.number = label.getNumber();
			PacketHandler.INSTANCE.sendToServer(chosen);
		}
	}
	
	public class ClickableLabel extends GuiButton
	{
		private int effectiveX;
		private final int hoveredAdjustedX;
		public ClickableLabel(int id, int x, int y)
		{
			super(id, x, y, "");
			this.height = fontRenderer.FONT_HEIGHT;
			hoveredAdjustedX = fontRenderer.getStringWidth(">  <") / 2;
		}
		
		private String label;
		private int number;
		
		public String getLabel()
		{
			return label;
		}
		
		public void setLabel(String label)
		{
			this.label = label;
			width = fontRenderer.getStringWidth(label);
			effectiveX = x - (width / 2);
		}
		
		public int getNumber()
		{
			return number;
		}
		
		public void setNumber(int number)
		{
			this.number = number;
		}
		
		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (!this.visible)
			{
				return;
			}
			
			boolean isHovered = GuiUtil.isPointWithinBounds(mouseX, mouseY, effectiveX, y, width, height);
			int startX = isHovered ? effectiveX - hoveredAdjustedX : effectiveX;
			
			fontRenderer.drawString((isHovered ? "> " : "") + label + (isHovered ? " <" : ""), startX, y, 0xFFFFFF);
		}		
		
		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
			return GuiUtil.isPointWithinBounds(mouseX, mouseY, effectiveX, y, width, height);
		}
	}

	public enum ActivationScreens
	{
		Message,
		ChooseNumber,
		Activated
	}
}
