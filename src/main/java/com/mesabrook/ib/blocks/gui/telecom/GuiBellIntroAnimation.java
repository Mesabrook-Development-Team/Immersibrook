package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;


public class GuiBellIntroAnimation extends GuiPhoneBase
{
    private int timerToNextScreen = 0;
    private int fadeAnimationTimer = 0;
    private String currentTexture;

    public GuiBellIntroAnimation(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        fadeAnimationTimer++;
    }

    @Override
    protected String getInnerTextureFileName()
    {
        String[] textures = {
                "system/app_screen_no_bar.png",
                "system/newboot/frame_0.png",
                "system/newboot/frame_1.png",
                "system/newboot/frame_2.png",
                "system/newboot/frame_3.png",
                "system/newboot/frame_4.png",
                "system/newboot/frame_5.png",
                "system/newboot/frame_6.png",
                "system/newboot/frame_7.png",
                "system/newboot/frame_8.png",
                "system/newboot/frame_9.png",
                "system/newboot/frame_10.png",
        };

        int[] animationTimers = {0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550};

        int index = -1;

        for (int i = 0; i < animationTimers.length; i++) {
            if (fadeAnimationTimer <= animationTimers[i]) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            index = 11; // Default index if fadeAnimationTimer is greater than 400
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
        if(phoneStackData.getNeedToDoOOBE())
        {
            ClientSoundPacket soundPacket = new ClientSoundPacket();
            soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
            soundPacket.soundName = "minedroid_startup";
            PacketHandler.INSTANCE.sendToServer(soundPacket);
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStart(phoneStack, hand));
        }
        else
        {
            ClientSoundPacket soundPacket = new ClientSoundPacket();
            soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
            soundPacket.soundName = "minedroid_startup";
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
        timerToNextScreen = 0;
        fadeAnimationTimer = 0;
    }
}
