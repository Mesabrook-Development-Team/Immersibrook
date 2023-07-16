package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.telecom.FactoryResetPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;

public class GuiDeadPhone extends GuiPhoneBase
{
    private int currentFrame = 0;
    private int timerToGUIKill;

    public GuiDeadPhone(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        if(currentFrame == 0)
        {
            return "system/dead_0.png";
        }
        if(currentFrame == 10)
        {
            return "system/dead_1.png";
        }
        return "system/dead_0.png";
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
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);

        currentFrame++;
        timerToGUIKill++;

        if(currentFrame > 10)
        {
            currentFrame = 0;
        }

        if(timerToGUIKill >= 1000)
        {
            killPhone();
        }
    }

    private void killPhone()
    {
        Minecraft.getMinecraft().displayGuiScreen(null);
    }
}
