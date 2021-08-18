package rz.mesabrook.wbtc.blocks.gui.telecom;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiPhoneCall extends GuiPhoneBase {

	private String currentlyTypedNumber = "";
	
	public GuiPhoneCall(ItemStack phoneStack, EnumHand hand) {
		super(phoneStack, hand);
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		for(int i = 0; i < 9; i++)
		{
			int x = ((i % 3) - 1) * 25;
			int y = ((i / 3) - 1) * 25;
			
			ImageButton digit = new ImageButton(i + 1, INNER_X + INNER_TEX_WIDTH / 2 + x - 8, INNER_Y + INNER_TEX_HEIGHT / 2 + y + 5, 16, 16, "num" + (i + 1) + ".png", 16, 16);
			buttonList.add(digit);
		}
		
		ImageButton digit0 = new ImageButton(0, INNER_X + INNER_TEX_WIDTH / 2 - 8, INNER_Y + INNER_TEX_HEIGHT / 2 + 55, 16, 16, "num0.png", 16, 16);
		buttonList.add(digit0);
		
		ImageButton call = new ImageButton(11, INNER_X + INNER_TEX_WIDTH / 2 + 17, INNER_Y + INNER_TEX_HEIGHT / 2 + 55, 16, 16, "numcall.png", 16, 16);
		buttonList.add(call);
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		final double dLittleFont = 0.75;
		final double uLittleFont = 1 / 0.75;
		
		final double uBigFont = 2.1;
		final double dBigFont = 1 / 2.1;
		
		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		fontRenderer.drawString("Phone", scale(INNER_X + 4, uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);
		
		GlStateManager.scale(uBigFont, uBigFont, uBigFont);
		String formattedNumber = currentlyTypedNumber;
		if (formattedNumber.length() >= 3)
		{
			formattedNumber = formattedNumber.substring(0, 3) + "-" + formattedNumber.substring(3, formattedNumber.length());
		}
		drawCenteredString(fontRenderer, formattedNumber + "|", scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 60, dBigFont), 0xFFFFFF);
		GlStateManager.scale(dBigFont, dBigFont, dBigFont);
	}
	
	private int scale(int number, double scale)
	{
		return (int)(number * scale);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id >= 0 && button.id <= 9 && currentlyTypedNumber.length() < 7)
		{
			currentlyTypedNumber += Integer.toString(button.id);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		if ((keyCode == Keyboard.KEY_DELETE || keyCode == Keyboard.KEY_BACK) && currentlyTypedNumber.length() > 0)
		{
			currentlyTypedNumber = currentlyTypedNumber.substring(0, currentlyTypedNumber.length() - 1);
		}
		
		else if (Character.isDigit(typedChar))
		{
			int pressedNumber = Integer.parseInt(Character.toString(typedChar));
			
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
}
