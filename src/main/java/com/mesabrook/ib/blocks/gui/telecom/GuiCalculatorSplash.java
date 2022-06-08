package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;

public class GuiCalculatorSplash extends GuiPhoneBase
{
    ImageButton logo;
    int progress = 0;

    public GuiCalculatorSplash(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "app_screen_no_bar.png";
    }

    @Override
    public void initGui()
    {
    	super.initGui();
        logo = new ImageButton(0, INNER_X + (INNER_TEX_WIDTH / 2) - 16, INNER_Y + 50, 32, 32, "icn_calc.png", 32, 32);
        buttonList.add(logo);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        drawCenteredString(fontRenderer, "BETA", INNER_X + 80, INNER_Y + 150, 0xFFFFFF);

        progress++;
        if(progress > 135)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiCalculator(phoneStack, hand));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
