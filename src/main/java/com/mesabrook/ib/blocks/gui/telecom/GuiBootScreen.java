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
            currentTexture = "app_screen_no_bar.png";
        }
        if(fadeAnimationTimer == 50)
        {
            currentTexture = "app_screen_boot_0.png";
        }
        if(fadeAnimationTimer == 100)
        {
            currentTexture = "app_screen_boot_1.png";
        }
        if(fadeAnimationTimer == 150)
        {
            currentTexture = "app_screen_boot_2.png";
        }
        if(fadeAnimationTimer == 200)
        {
            currentTexture = "app_screen_boot_3.png";
        }
        if(fadeAnimationTimer == 250)
        {
            currentTexture = "app_screen_boot_4.png";
        }
        if(fadeAnimationTimer == 300)
        {
            currentTexture = "app_screen_boot_5.png";
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

        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "minedroid_firstboot";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
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

        if(phoneStackData.getNeedToDoOOBE())
        {
            GuiLockScreen lock = new GuiLockScreen(Minecraft.getMinecraft().player.getHeldItem(hand), hand);
            Minecraft.getMinecraft().displayGuiScreen(lock);
        }
        else
        {
            GuiPhoneSetupStart lock = new GuiPhoneSetupStart(Minecraft.getMinecraft().player.getHeldItem(hand), hand);
            Minecraft.getMinecraft().displayGuiScreen(lock);
        }
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
