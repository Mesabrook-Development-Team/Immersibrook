package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;


public class GuiBellIntroAnimation extends GuiPhoneBase
{
    private IndependentTimer timer;
    private String currentTexture;

    public GuiBellIntroAnimation(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        timer = new IndependentTimer();
    }

    @Override
    protected String getInnerTextureFileName()
    {
        if(timer.getElapsedTime() < 100)
        {
            currentTexture = "system/app_screen_no_bar.png";
        }
        if(timer.getElapsedTime() >= 150)
        {
            currentTexture = "system/newboot/frame_0.png";
        }
        if(timer.getElapsedTime() >= 200)
        {
            currentTexture = "system/newboot/frame_1.png";
        }
        if(timer.getElapsedTime() >= 400)
        {
            currentTexture = "system/newboot/frame_2.png";
        }
        if(timer.getElapsedTime() >= 600)
        {
            currentTexture = "system/newboot/frame_3.png";
        }
        if(timer.getElapsedTime() >= 800)
        {
            currentTexture = "system/newboot/frame_4.png";
        }
        if(timer.getElapsedTime() >= 1000)
        {
            currentTexture = "system/newboot/frame_5.png";
        }
        if(timer.getElapsedTime() >= 1200)
        {
            currentTexture = "system/newboot/frame_6.png";
        }
        if(timer.getElapsedTime() >= 1400)
        {
            currentTexture = "system/newboot/frame_7.png";
        }
        if(timer.getElapsedTime() >= 1600)
        {
            currentTexture = "system/newboot/frame_8.png";
        }
        if(timer.getElapsedTime() >= 1800)
        {
            currentTexture = "system/newboot/frame_9.png";
        }
        if(timer.getElapsedTime() >= 2000)
        {
            currentTexture = "system/newboot/frame_10.png";
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
        timer.update();

        if(timer.getElapsedTime() >= 5000)
        {
            finishBoot();
        }
    }

    private void finishBoot()
    {
        if(phoneStackData.getNeedToDoOOBE())
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStart(phoneStack, hand));
        }
        else
        {
            ClientSoundPacket soundPacket = new ClientSoundPacket();
            soundPacket.pos = Minecraft.getMinecraft().player.getPosition();

            if(phoneStackData.getIconTheme().contains("luna"))
            {
                soundPacket.soundName = "xp_startup";
            }
            else
            {
                soundPacket.soundName = "normal_boot";
            }

            PacketHandler.INSTANCE.sendToServer(soundPacket);
            GuiLockScreen lock = new GuiLockScreen(Minecraft.getMinecraft().player.getHeldItem(hand), hand);
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
        timer.reset();
        timer.stop();
    }
}
