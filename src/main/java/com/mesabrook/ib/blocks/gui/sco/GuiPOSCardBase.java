package com.mesabrook.ib.blocks.gui.sco;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.init.ModSounds;
import com.mesabrook.ib.net.sco.POSCardEjectPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public abstract class GuiPOSCardBase extends GuiScreen {
	protected final int texWidth = 351 / 2;
	protected final int texHeight = 410 / 2;
	
	protected int left;
	protected int top;
	protected int right;
	protected int bottom;
	protected int midWidth;
	protected int midHeight;
	
	protected final TileEntityRegister register;
	protected final CardReaderInfo readerInfo;
	
	protected ImageButton cancelButton;
	protected ImageButton okButton;
	
	protected GuiButtonExt keypad;
	
	public GuiPOSCardBase(TileEntityRegister register, CardReaderInfo readerInfo)
	{
		this.register = register;
		this.readerInfo = readerInfo;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		left = width / 2 - texWidth / 2;
		top = height / 2 - texHeight / 2;
		right = left + texWidth;
		bottom = top + texHeight;
		midWidth = width / 2;
		midHeight = height / 2;
		
		int numpadLeft = left + 62;
		int numpadTop = top + 124;
		
		for(int i = 1; i <= 9; i++)
		{
			int row = (i - 1) / 3;
			int column = (i - 1) % 3;
			
			int numpadButtonX = column == 0 ? numpadLeft :
								column == 1 ? numpadLeft + 18 :
								numpadLeft + 36;
			
			ImageButton numpadButton = new ImageButton(i + 100, numpadButtonX, numpadTop + 18 * row, 16, 16, "calcbtn_" + i + ".png", 32, 32);
			buttonList.add(numpadButton);
		}
		
		buttonList.add(new ImageButton(100, numpadLeft + 18, numpadTop + 54, 16, 16, "calcbtn_0.png", 32, 32));
		cancelButton = new ImageButton(110, numpadLeft, numpadTop + 54, 16, 16, new ResourceLocation(Reference.MODID, "textures/misc/cancel.png"), 32, 32, 32, 32);
		buttonList.add(cancelButton);
		okButton = new ImageButton(111, numpadLeft + 36, numpadTop + 54, 16, 16, new ResourceLocation(Reference.MODID, "textures/misc/go.png"), 32, 32, 32, 32);
		buttonList.add(okButton);
	}
	
	public void playButtonSound()
	{
		Minecraft.getMinecraft().player.playSound(ModSounds.ATM_BEEP, 0.5F, 1.5F);
	}
	
	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		if (register.getInsertedCardStack() != null)
		{
			GlStateManager.translate(midWidth + 154, midHeight - 35, -100);
			GlStateManager.rotate(90F, 0, 0, 1);
			GlStateManager.scale(19, 19, 1);
			GlStateManager.disableDepth();
			itemRender.renderItemIntoGUI(register.getInsertedCardStack(), 0, 0);
			GlStateManager.enableDepth();
			GlStateManager.scale(1F/19, 1F/19, 1);
			GlStateManager.rotate(-90F, 0, 0, 1);
			GlStateManager.translate(-(midWidth + 154), -(midHeight - 35), 100);
		}
		
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/card_reader.png"));
		
		drawModalRectWithCustomSizedTexture(left, top, 0, 0, texWidth, texHeight, 512/2, 512/2);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		doDraw(mouseX, mouseY, partialTicks);
		
		if (register.getInsertedCardStack() != null && isHoveringOverEject(mouseX, mouseY))
		{
			drawHoveringText("Eject Card", mouseX, mouseY);
		}
	}
	
	protected void doDraw(int mouseX, int mouseY, float partialTicks) {}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id >= 100 && button.id <= 109)
		{
			numpadButtonPressed(Integer.toString(button.id - 100), false, false);
		}
		else if (button == okButton || button == cancelButton)
		{
			numpadButtonPressed("", button == cancelButton, button == okButton);
		}
	}
	
	protected void numpadButtonPressed(String character, boolean cancelPressed, boolean okPressed) {}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (register.getInsertedCardStack() != null && isHoveringOverEject(mouseX, mouseY))
		{
			ejectCard();
		}
	}

	protected void ejectCard() {
		mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		
		POSCardEjectPacket eject = new POSCardEjectPacket();
		eject.atmPos = register.getPos();
		PacketHandler.INSTANCE.sendToServer(eject);
		
		mc.displayGuiScreen(null);
	}
	
	public void drawCenteredStringNoShadow(String text, int x, int y, int color) {		
		fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text) / 2, y - 40, 0x424242);
	}
	
	protected boolean isHoveringOverEject(int mouseX, int mouseY)
	{
		return mouseX >= left + 41 && mouseX <= right - 35 && mouseY >= bottom && mouseY <= bottom + 151;
	}
	
	public <T extends GuiPOSCardBase> void showNextGui(Class<T> gui, Object... additionalParameters)
	{
		if (additionalParameters == null)
		{
			additionalParameters = new Object[0];
		}
		
		Class<?>[] constructorParameterTypes = new Class<?>[2 + additionalParameters.length];
		constructorParameterTypes[0] = TileEntityRegister.class;
		constructorParameterTypes[1] = CardReaderInfo.class;
		
		for(int i = 0; i < additionalParameters.length; i++)
		{
			constructorParameterTypes[i + 2] = additionalParameters[i].getClass();
		}
		
		try
		{
			Constructor<?> constructor = gui.getConstructor(constructorParameterTypes);
			Object[] constructorValues = new Object[additionalParameters.length + 2];
			constructorValues[0] = register;
			constructorValues[1] = readerInfo;
			for(int i = 0; i < additionalParameters.length; i++)
			{
				constructorValues[i + 2] = additionalParameters[i];
			}
			
			GuiPOSCardBase guiInstance = (GuiPOSCardBase)constructor.newInstance(constructorValues);
			mc.displayGuiScreen(guiInstance);
		}
		catch(Exception ex) {}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public static class CardReaderInfo
	{
		public String pin;
		public BigDecimal cashBack;
		public BigDecimal authorizedAmount;
	}
}
