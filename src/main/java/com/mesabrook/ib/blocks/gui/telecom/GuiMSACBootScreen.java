package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;


public class GuiMSACBootScreen extends GuiPhoneBase
{
    private int timerToNextScreen = 0;
    private int fadeAnimationTimer = 0;
    private int bootStepTracker = 0;
    private String currentTexture;
    private String bootStep = "";
    private String banner;

    public GuiMSACBootScreen(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        fadeAnimationTimer++;
    }

    @Override
    protected String getInnerTextureFileName()
    {
        String[] textures = {
                "system/app_screen_no_bar.png",
                "system/boot_screen_msac_0.png",
                "system/boot_screen_msac_1.png",
                "system/boot_screen_msac_2.png",
                "system/boot_screen_msac_3.png",
                "system/boot_screen_msac_4.png",
                "system/boot_screen_msac_5.png",
                "system/boot_screen_msac_6.png",
                "system/boot_screen_msac_1.png"
        };

        int[] animationTimers = {0, 50, 100, 150, 200, 250, 300, 350, 400};

        int index = -1;

        for (int i = 0; i < animationTimers.length; i++) {
            if (fadeAnimationTimer <= animationTimers[i]) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            index = 2; // Default index if fadeAnimationTimer is greater than 400
            fadeAnimationTimer = 100; // Reset timer to 100
        }

        if (index == 2) {
            banner = "Starting Minedroid";
        }

        currentTexture = textures[index];
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
        timerToNextScreen++;
        fadeAnimationTimer++;

        drawCenteredString(fontRenderer, banner, INNER_X + 80, INNER_Y + 180, 0xFFFFFF);

        if(timerToNextScreen >= 1500)
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
        timerToNextScreen = 0;
        fadeAnimationTimer = 0;
    }
}
