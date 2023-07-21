package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.init.SoundInit;
import com.mesabrook.ib.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;
import java.util.UUID;

public class GuiLockScreen extends GuiPhoneBase {

	private UnlockSlider unlockSlider;
	MinedroidButton unlockButton;
	public GuiLockScreen(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "wallpapers/gui_phone_bg_" + Integer.toString(phoneStackData.getLockBackground()) + ".png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
		unlockSlider = new UnlockSlider(INNER_X + INNER_TEX_WIDTH / 2 - 60, INNER_Y + INNER_TEX_HEIGHT - 75);
		unlockButton = new MinedroidButton(69, INNER_X + 85, lowerControlsY - 20, 30, "Unlock", 0xFFFFFF);

		if(phoneStackData.getUseButtonInsteadOfSlider())
		{
			buttonList.add(unlockButton);
		}
	}
	
	@Override
	protected boolean renderControlBar() {
		return false;
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		int stringWidth = fontRenderer.getStringWidth(getTime());
		int stringWidth2 = fontRenderer.getStringWidth(getIRLTime());
		
		GlStateManager.scale(uBigFont, uBigFont, uBigFont);
		fontRenderer.drawString(getTime(), scale(INNER_X + INNER_TEX_WIDTH / 2, dBigFont) - stringWidth / 2, scale(INNER_Y + 40, dBigFont), 0xFFFFFF, true);
		GlStateManager.scale(dBigFont, dBigFont, dBigFont);

		if(phoneStackData.getShowIRLTime())
		{
			fontRenderer.drawString(getIRLTime(), INNER_X + INNER_TEX_WIDTH / 2 - stringWidth2 / 2, INNER_Y + INNER_TEX_HEIGHT - 155, 0xFFFFFF, true);
		}

		String phoneName = truncateIfExceeds(fontRenderer, phoneStack.getDisplayName(), INNER_TEX_WIDTH, 1.0);
		stringWidth = fontRenderer.getStringWidth(phoneName);
		
		fontRenderer.drawString(phoneName, INNER_X + (INNER_TEX_WIDTH / 2) - (stringWidth / 2), INNER_Y + 20, 0xFFFFFF, true);

		if(!phoneStackData.getUseButtonInsteadOfSlider())
		{
			unlockSlider.draw(mouseX, mouseY, partialticks);
			String swipeText = "Swipe to unlock";
			int fontWidth = fontRenderer.getStringWidth(swipeText);

			fontRenderer.drawString(swipeText, INNER_X + INNER_TEX_WIDTH / 2 - fontWidth / 2, INNER_Y + INNER_TEX_HEIGHT - 60, 0xFFFFFF, true);

			if (unlockSlider.isSliderComplete())
			{
				onScreenUnlocked();
			}
		}
	}
	
	private void onScreenUnlocked()
	{
		switch(phoneStackData.getSecurityStrategy())
		{
			case None:
				goHome();
				break;
			case UUID:
				handleUUIDStrategy();
				break;
			case PIN:
				handlePINStrategy();
				break;
		}
	}
	
	private void goHome()
	{
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundInit.PHONE_UNLOCK, 1F));
		GuiPhoneBase.isPhoneUnlocked = true;
		Minecraft.getMinecraft().displayGuiScreen(new GuiHome(phoneStack, hand));
	}
	
	private void handleUUIDStrategy()
	{
		UUID stratID = phoneStackData.getUuid();
		if (stratID == null)
		{
			goHome();
		}
		
		if (!stratID.equals(Minecraft.getMinecraft().player.getUniqueID()))
		{
			Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "Wrong Player", 0xFF0000));
			unlockSlider.mouseReleased();
			return;
		}
		
		goHome();
	}
	
	private void handlePINStrategy()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiLockChallengePIN(phoneStack, hand));
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		unlockSlider.mouseClicked(mouseX, mouseY);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		unlockSlider.mouseReleased();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
		if(button == unlockButton)
		{
			onScreenUnlocked();
		}
	}
	
	public static class UnlockSlider
	{
		private final int SLIDER_WIDTH = 17;
		private final int SLIDER_HEIGHT = 14;
		
		private int x;
		private int y;
		private int sliderX;
		private int mouseXOffset;
		private boolean isMouseClicked;
		
		public UnlockSlider(int x, int y)
		{
			this.x = x;
			this.sliderX = x;
			this.y = y;
		}
		
		int previousMouseX;
		public void draw(int mouseX, int mouseY, float partialTicks)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/telecom/slider.png"));
			if (isMouseClicked)
			{
				sliderX = previousMouseX + (int)((mouseX - previousMouseX) * partialTicks) - mouseXOffset;
				previousMouseX = mouseX;
				
				if (sliderX > x + 120 - SLIDER_WIDTH)
				{
					sliderX = x + 120 - SLIDER_WIDTH;
				}
				
				if (sliderX < x)
				{
					sliderX = x;
				}
			}
			advanceBarTowardStart();
			drawBar();
			drawButton();
		}
		
		private void advanceBarTowardStart()
		{
			if (isMouseClicked || sliderX == x)
			{
				return;
			}
			
			sliderX-= 5;
			
			if (sliderX < x)
			{
				sliderX = x;
			}
		}
		
		private void drawBar()
		{
			GuiScreen.drawScaledCustomSizeModalRect(x, y + 2, 0, 0, 240, 20, 120, 10, 240, 46);
		}
		
		private void drawButton()
		{
			GuiScreen.drawScaledCustomSizeModalRect(sliderX, y, 0, 20, 36, 25, SLIDER_WIDTH, SLIDER_HEIGHT, 240, 46);
		}
		
		public void mouseClicked(int mouseX, int mouseY)
		{
			isMouseClicked = mouseX >= sliderX && mouseY >= this.y && mouseX < sliderX + SLIDER_WIDTH && mouseY < this.y + SLIDER_HEIGHT;
			
			if (isMouseClicked)
			{
				previousMouseX = mouseX;
				mouseXOffset = mouseX - sliderX;
			}
		}
		
		public void mouseReleased()
		{
			isMouseClicked = false;
			mouseXOffset = 0;
		}
	
		public boolean isSliderComplete() { return sliderX == x + 120 - SLIDER_WIDTH; }
	}
}
