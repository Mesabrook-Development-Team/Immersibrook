package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;


public class GuiMSACBootScreen extends GuiPhoneBase
{
    private int timerToNextScreen = 0;
    private int fadeAnimationTimer = 0;
    private String currentTexture;

    public GuiMSACBootScreen(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        fadeAnimationTimer++;
    }

    @Override
    protected String getInnerTextureFileName()
    {
        if(fadeAnimationTimer == 0)
        {
            currentTexture = "app_screen_no_bar.png";
        }
        if(fadeAnimationTimer == 50)
        {
            currentTexture = "msac/boot_screen_msac_0.png";
        }
        if(fadeAnimationTimer == 100)
        {
            currentTexture = "msac/boot_screen_msac_1.png";
        }
        if(fadeAnimationTimer == 150)
        {
            currentTexture = "msac/boot_screen_msac_2.png";
        }
        if(fadeAnimationTimer == 200)
        {
            currentTexture = "msac/boot_screen_msac_3.png";
        }
        if(fadeAnimationTimer == 250)
        {
            currentTexture = "msac/boot_screen_msac_4.png";
        }
        if(fadeAnimationTimer == 300)
        {
            currentTexture = "msac/boot_screen_msac_5.png";
        }
        if(fadeAnimationTimer == 400)
        {
            currentTexture = "msac/boot_screen_msac_6.png";
        }
        if(fadeAnimationTimer == 500)
        {
            currentTexture = "msac/boot_screen_msac_2.png";
        }

        if(fadeAnimationTimer > 550)
        {
            fadeAnimationTimer = 150;
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
        timerToNextScreen++;
        fadeAnimationTimer++;

        if(timerToNextScreen >= 1500)
        {
            finishBoot();
        }
    }

    private void finishBoot()
    {
        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "minedroid_firstboot";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
        GuiBootScreen boot = new GuiBootScreen(Minecraft.getMinecraft().player.getHeldItem(hand), hand);
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
