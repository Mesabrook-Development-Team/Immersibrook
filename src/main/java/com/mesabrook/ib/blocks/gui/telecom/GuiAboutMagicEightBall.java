package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiAboutMagicEightBall extends GuiPhoneBase
{
	MinedroidButton back;

	public GuiAboutMagicEightBall(ItemStack phoneStack, EnumHand hand) 
	{
		super(phoneStack, hand);
	}
	
	@Override
	protected String getInnerTextureFileName() 
	{
		return phoneStackData.getIconTheme() + "/app_screen_purple.png";
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 50;
		back = new MinedroidButton(1, INNER_X + 61, lowerControlsY + 15, 40, "Back", 0x000000);
		buttonList.add(back);
	}
	
	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks)
	{
		super.doDraw(mouseX, mouseY, partialticks);
		fontRenderer.drawSplitString("The Magic 8 Ball is the ultimate arbitor of yes/no questions. \nSimply give it a yes/no question and learn its fate.", INNER_X + 14, INNER_Y + 65, INNER_TEX_WIDTH - 12, 0xFFFFFF);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
		if(button == back)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiMagicEightBall(phoneStack, hand));
		}
	}
}
