package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiChangingTheme extends GuiPhoneBase
{
    private String progress;
    private int timer = 0;
    private int animTimer = 0;

    public GuiChangingTheme(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        progress = "[X---------]";
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/app_screen_no_bar.png";
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
        timer++;
        animTimer++;

        String bar = "[";
        int animFrame = animTimer / 10;
        animFrame = animFrame % 18;
        for(int i = 0; i < 10; i++)
        {
            if (i >= animFrame - 4 && i <= animFrame + 3)
            {
                bar += "X";
            }
            else
            {
                bar += "-";
            }
        }
        bar += "]";

        drawCenteredString(fontRenderer, "Applying Theme", INNER_X + 80, INNER_Y + 50, 0xFFFFFF);
        drawCenteredString(fontRenderer, bar, INNER_X + 80, INNER_Y + 180, 0xFFFFFF);

        if(timer >= 1200)
        {
            changeSystemTheme();
        }
    }

    private void changeSystemTheme()
    {
        CustomizationPacket packet = new CustomizationPacket();
        packet.hand = hand.ordinal();
        packet.newName = phoneStack.getDisplayName();
        packet.guiClassName = GuiIconSet.class.getName();

        if(phoneStackData.getNeedToDoOOBE())
        {
            packet.iconTheme = GuiPhoneSetupThemeStep.getUpdatedTheme();
        }
        else
        {
            packet.iconTheme = GuiIconSet.getUpdatedTheme();
        }

        packet.lockBackground = phoneStackData.getLockBackground();
        packet.homeBackground = phoneStackData.getHomeBackground();
        packet.lockBackground = phoneStackData.getLockBackground();
        packet.homeBackground = phoneStackData.getHomeBackground();
        packet.lockTone = phoneStackData.getChatTone();
        packet.ringtone = phoneStackData.getRingTone();
        packet.setShowIRLTime = phoneStackData.getShowIRLTime();
        packet.useMilitaryTime = phoneStackData.getShowingMilitaryIRLTime();
        packet.toggleDebugMode = phoneStackData.getIsDebugModeEnabled();
        packet.resetName = false;
        packet.pin = phoneStackData.getPin();
        packet.playerID = phoneStackData.getUuid();

        PacketHandler.INSTANCE.sendToServer(packet);

        if(phoneStackData.getNeedToDoOOBE())
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneNameSetup(phoneStack, hand));
        }
        else
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiIconSet(phoneStack, hand));
        }
    }
}
