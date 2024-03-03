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
        int elapsedTime = (int) timer.getElapsedTime();
        int frameNumber = (elapsedTime - 50) / 27;

        frameNumber = Math.max(0, Math.min(frameNumber, 113));

        currentTexture = "system/newboot/frame_" + frameNumber + ".png";
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

        if(timer.getElapsedTime() >= 6000)
        {
            finishBoot();
        }
    }

    private void finishBoot()
    {
        if(phoneStackData.getNeedToDoOOBE())
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiBubbleSplashAnim(phoneStack, hand));
        }
        else
        {
            ClientSoundPacket soundPacket = new ClientSoundPacket();
            soundPacket.pos = Minecraft.getMinecraft().player.getPosition();

            if(phoneStackData.getIconTheme().contains("luna"))
            {
                soundPacket.soundName = "xp_startup";
            }
            else if(phoneStackData.getIconTheme().contains("aero"))
            {
                soundPacket.soundName = "aero_startup";
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
