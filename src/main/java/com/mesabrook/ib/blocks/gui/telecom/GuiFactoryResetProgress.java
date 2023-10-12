package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.net.telecom.FactoryResetPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;

public class GuiFactoryResetProgress extends GuiPhoneBase
{
    ImageButton icon;
    private String currentStep;
    private int step;
    private boolean resetComplete;
    public GuiFactoryResetProgress(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        currentStep = "Preparing for reset...";
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/app_splash_red.png";
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
        icon = new ImageButton(1, INNER_X + 72, INNER_Y + 22, 16, 16, "icn_mux.png", 32, 32);
        buttonList.add(icon);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        step++;
        drawCenteredString(fontRenderer, TextFormatting.BOLD + "Factory Reset Utility", INNER_X + 80, INNER_Y + 45, 0xFFFFFF);

        drawCenteredString(fontRenderer, currentStep, INNER_X + 80, INNER_Y + 80, 0xFFFFFF);

        if(step >= 50)
        {
            currentStep = "Clearing User Data...";
        }
        if(step >= 125)
        {
            currentStep = "Apply MSAC Defaults...";
        }
        if(step >= 145)
        {
            currentStep = "Finishing Up...";
        }
        if(step >= 160)
        {
            currentStep = "Reset Complete.";
        }
        if(step > 230)
        {
            resetComplete = true;
            step = 235;
            rebootPhone();
        }
    }

    private void rebootPhone()
    {
        if(resetComplete)
        {
            FactoryResetPacket reset = new FactoryResetPacket();
            reset.hand = hand.ordinal();
            reset.phoneActivateGuiClassName = GuiFactoryResetConfirmation.class.getName();
            PacketHandler.INSTANCE.sendToServer(reset);

            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneActivate(phoneStack, hand));
        }
    }
}
