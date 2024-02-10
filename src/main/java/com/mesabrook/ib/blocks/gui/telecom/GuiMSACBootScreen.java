package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.util.IndependentTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;


public class GuiMSACBootScreen extends GuiPhoneBase
{
    private IndependentTimer timer;
    private IndependentTimer bootTimer;
    private String currentTexture;
    private String banner;

    public GuiMSACBootScreen(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        timer = new IndependentTimer();
        bootTimer = new IndependentTimer();
    }

    @Override
    protected String getInnerTextureFileName()
    {
        if(timer.getElapsedTime() < 100)
        {
            currentTexture = "system/boot_screen_msac_1.png";
        }
        if(timer.getElapsedTime() >= 150)
        {
            currentTexture = "system/boot_screen_msac_1.png";
        }
        if(timer.getElapsedTime() >= 200)
        {
            currentTexture = "system/boot_screen_msac_1.png";
            banner = "Starting Minedroid";
        }
        if(timer.getElapsedTime() >= 400)
        {
            currentTexture = "system/boot_screen_msac_2.png";
        }
        if(timer.getElapsedTime() >= 600)
        {
            currentTexture = "system/boot_screen_msac_3.png";
        }
        if(timer.getElapsedTime() >= 800)
        {
            currentTexture = "system/boot_screen_msac_4.png";
        }
        if(timer.getElapsedTime() >= 1000)
        {
            currentTexture = "system/boot_screen_msac_5.png";
        }
        if(timer.getElapsedTime() >= 1200)
        {
            currentTexture = "system/boot_screen_msac_6.png";
        }
        if(timer.getElapsedTime() >= 1400)
        {
            timer.reset();
        }
        return currentTexture;
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }

    @Override
    protected boolean renderTopBar() {
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        timer.update();
        bootTimer.update();

        drawCenteredString(fontRenderer, banner, INNER_X + 80, INNER_Y + 180, 0xFFFFFF);

        if(bootTimer.getElapsedTime() >= 3000)
        {
            finishBoot();
        }
    }

    private void finishBoot()
    {
        GuiBellIntroAnimation boot = new GuiBellIntroAnimation(phoneStack, hand);
        Minecraft.getMinecraft().displayGuiScreen(boot);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        timer.reset();
        timer.stop();
        bootTimer.reset();
        bootTimer.stop();
    }
}
