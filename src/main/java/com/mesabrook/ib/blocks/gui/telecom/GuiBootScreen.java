package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;


public class GuiBootScreen extends GuiPhoneBase
{
    private int timerToNextScreen = 0;
    private int fadeAnimationTimer = 0;
    private String currentTexture;
    private String bootFramesDirectory = "system/newboot/frame_";

    public GuiBootScreen(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        fadeAnimationTimer++;
    }

    @Override
    protected String getInnerTextureFileName()
    {
        if(fadeAnimationTimer == 0)
        {
            currentTexture = "system/app_screen_no_bar.png";
        }

        // Start Frames
        if(fadeAnimationTimer == 50)
        {
            currentTexture = bootFramesDirectory + 0 + ".png";
        }
        if(fadeAnimationTimer == 100)
        {
            currentTexture = bootFramesDirectory + 1 + ".png";
        }
        if(fadeAnimationTimer == 150)
        {
            currentTexture = bootFramesDirectory + 2 + ".png";
        }
        if(fadeAnimationTimer == 200)
        {
            currentTexture = bootFramesDirectory + 3 + ".png";
        }
        if(fadeAnimationTimer == 250)
        {
            currentTexture = bootFramesDirectory + 4 + ".png";
        }
        if(fadeAnimationTimer == 300)
        {
            currentTexture = bootFramesDirectory + 5 + ".png";
        }
        if(fadeAnimationTimer == 350)
        {
            currentTexture = bootFramesDirectory + 6 + ".png";
        }
        if(fadeAnimationTimer == 400)
        {
            currentTexture = bootFramesDirectory + 7 + ".png";
        }
        if(fadeAnimationTimer == 450)
        {
            currentTexture = bootFramesDirectory + 8 + ".png";
        }
        if(fadeAnimationTimer == 500)
        {
            currentTexture = bootFramesDirectory + 9 + ".png";
        }
        if(fadeAnimationTimer == 550)
        {
            currentTexture = bootFramesDirectory + 10 + ".png";
            fadeAnimationTimer = 550;
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

        if(phoneStackData.getBatteryLevel() > 0)
        {
            ClientSoundPacket soundPacket = new ClientSoundPacket();
            soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
            soundPacket.soundName = "minedroid_firstboot";
            PacketHandler.INSTANCE.sendToServer(soundPacket);
        }
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
        soundPacket.soundName = "minedroid_startup";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
        GuiLockScreen lock = new GuiLockScreen(Minecraft.getMinecraft().player.getHeldItem(hand), hand);
        Minecraft.getMinecraft().displayGuiScreen(lock);
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
