package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiRecoverSession extends GuiPhoneBase
{
    IndependentTimer timer;
    public GuiRecoverSession(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/recovery.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();
        timer = new IndependentTimer();
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }

    @Override
    protected boolean renderTopBar() { return false; }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        timer.update();

        fontRenderer.drawString("Minedroid Recovery Subsystem", INNER_X + 1, INNER_Y + 1, 0xFFFFFF);
        fontRenderer.drawString("Ver. " + Reference.MINEDROID_VERSION, INNER_X + 1, INNER_Y + 10, 0xFFFFFF);

        if(timer.getElapsedTime() >= 300)
        {
            fontRenderer.drawString("Scanning memory...", INNER_X + 1, INNER_Y + 25, 0xFFFFFF);
        }
        if(timer.getElapsedTime() >= 450)
        {
            fontRenderer.drawString("Checking for faults...", INNER_X + 1, INNER_Y + 35, 0xFFFFFF);
        }
        if(timer.getElapsedTime() >= 600)
        {
            fontRenderer.drawString("Locating active sessions...", INNER_X + 1, INNER_Y + 45, 0xFFFFFF);
        }
        if(timer.getElapsedTime() >= 850)
        {
            fontRenderer.drawString("Active session found...", INNER_X + 1, INNER_Y + 55, 0xFFFFFF);
        }
        if(timer.getElapsedTime() >= 1000)
        {
            fontRenderer.drawString("Attempting to recover...", INNER_X + 1, INNER_Y + 65, 0xFFFFFF);
        }

        if(timer.getElapsedTime() >= 3000)
        {
            if(mc.world.rand.nextFloat() > 0.5)
            {
                if(timer.getElapsedTime() > 2300)
                {
                    timer.stop();
                    recover();
                }
            }
            else
            {
                if(timer.getElapsedTime() > 2300)
                {
                    timer.stop();
                    fail();
                }
            }
        }
    }

    private void recover()
    {
        GuiPhoneBase.isPhoneUnlocked = false;
        Minecraft.getMinecraft().displayGuiScreen(new GuiLockScreen(phoneStack, hand));
    }

    private void fail()
    {
        GuiPhoneBase.isPhoneUnlocked = false;
        Minecraft.getMinecraft().displayGuiScreen(new GuiMSACBootScreen(phoneStack, hand));
    }
}
