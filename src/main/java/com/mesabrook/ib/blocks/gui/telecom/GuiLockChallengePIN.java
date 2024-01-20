package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.init.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiLockChallengePIN extends GuiPhoneBase {

	private String enteredPIN = "";
	
	public GuiLockChallengePIN(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int upperLeftX = INNER_X + INNER_TEX_WIDTH / 2 - 28;
		int upperLeftY = INNER_Y + INNER_TEX_HEIGHT / 2 + 8;
		
		for(int i = 1; i <= 9; i++)
		{
			ImageButton digit = new ImageButton(i, upperLeftX + 20 * ((i - 1) % 3), upperLeftY + 20 * ((i - 1) / 3), 16, 16, phoneStackData.getIconTheme() + "/numpad/numpad_" + i + ".png", 16, 16);
			buttonList.add(digit);
		}
		
		ImageButton zero = new ImageButton(0, upperLeftX + 20, upperLeftY + 60, 16, 16, phoneStackData.getIconTheme() + "/numpad/numpad_0.png", 16, 16);
		buttonList.add(zero);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "wallpapers/gui_phone_bg_" + Integer.toString(phoneStackData.getLockBackground()) + ".png";
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		super.doDraw(mouseX, mouseY, partialticks);
		
		int fontWidth = fontRenderer.getStringWidth("PIN Required");
		fontRenderer.drawString("PIN Required", INNER_X + INNER_TEX_WIDTH / 2 - fontWidth / 2, INNER_Y + 30, 0xFFFFFF, true);
		
		String mask = "";
		for(int i = 0; i < enteredPIN.length(); i++)
		{
			mask += "*";
		}
		
		fontWidth = fontRenderer.getStringWidth(mask);
		GlStateManager.scale(uBigFont, uBigFont, uBigFont);
		fontRenderer.drawString(mask, scale(INNER_X + INNER_TEX_WIDTH / 2 - fontWidth, dBigFont), scale(INNER_Y + 72, dBigFont), 0xFFFFFF, true);
		GlStateManager.scale(dBigFont, dBigFont, dBigFont);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.id >= 0 && button.id <= 9)
		{
			try
			{
				Integer.parseInt(enteredPIN + button.id);
			}
			catch(NumberFormatException ex)
			{
				return;
			}
			if(enteredPIN.length() <= 8)
			{
				enteredPIN += String.valueOf(button.id);
			}
			
			if (Integer.parseInt(enteredPIN) == phoneStackData.getPin())
			{
				isPhoneUnlocked = true;
				Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundInit.PHONE_UNLOCK, 1F));
				Minecraft.getMinecraft().displayGuiScreen(new GuiHome(phoneStack, hand));
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		if ((keyCode == Keyboard.KEY_BACK || keyCode == Keyboard.KEY_DELETE) && enteredPIN.length() > 0)
		{
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1F));
			enteredPIN = enteredPIN.substring(0, enteredPIN.length() - 1);
		}
		else if (Character.isDigit(typedChar))
		{
			int pressedNumber = Integer.parseInt(String.valueOf(typedChar));
			for(GuiButton button : buttonList)
			{
				if (button.id == pressedNumber)
				{
					this.mouseClicked(button.x + 1, button.y + 1, 0);
					break;
				}
			}
		}
	}
	
	@Override
	protected boolean renderControlBar() {
		return false;
	}
}
