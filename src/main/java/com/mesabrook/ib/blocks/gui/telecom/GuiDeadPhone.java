package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.telecom.FactoryResetPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;

public class GuiDeadPhone extends GuiPhoneBase
{
    private int currentFrame = 0;
    private String currentFrameImg = "system/dead_0.png";
    private int timerToGUIKill;

    public GuiDeadPhone(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return currentFrameImg;
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }

    @Override
    protected boolean renderTopBar()
    {
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        ClientSoundPacket soundPacket = new ClientSoundPacket();
		soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
		soundPacket.soundName = "phone_battery_low";

		PacketHandler.INSTANCE.sendToServer(soundPacket);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);

        currentFrame++;
        timerToGUIKill++;

        if(currentFrame == 200)
        {
            currentFrameImg = "system/dead_1.png";
        }
        if(currentFrame >= 400)
        {
            currentFrameImg = "system/dead_0.png";
            currentFrame = 0;
        }


        if(timerToGUIKill >= 1000)
        {
            killPhone();
        }
    }

    @Override
    protected boolean closeOnDeadBattery()
    {
        return false;
    }

    private void killPhone()
    {
        Minecraft.getMinecraft().displayGuiScreen(null);
    }
}
