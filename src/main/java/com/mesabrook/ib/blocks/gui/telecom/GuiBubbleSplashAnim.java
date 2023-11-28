package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiBubbleSplashAnim extends GuiPhoneBase
{
    private IndependentTimer timer;
    private String currentFrame = "";

    public GuiBubbleSplashAnim(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        timer = new IndependentTimer();
    }

    @Override
    protected String getInnerTextureFileName()
    {
        int elapsedTime = (int) timer.getElapsedTime();
        int frameNumber = (elapsedTime - 50) / 10;

        frameNumber = Math.max(0, Math.min(frameNumber, 39));

        currentFrame = "system/bubble/frame_" + frameNumber + ".png";
        return currentFrame;
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

        if(timer.getElapsedTime() >= 460)
        {
            finishBoot();
        }
    }

    private void finishBoot()
    {
        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "oobe_startup";
        PacketHandler.INSTANCE.sendToServer(soundPacket);

        Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStart(phoneStack, hand));
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        timer.reset();
        timer.stop();
    }
}
