package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.SoundPlayerAppInfoPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;

public class GuiPhoneSetupStart extends GuiPhoneBase
{
    MinedroidButton beginSetup;

    public GuiPhoneSetupStart(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "app_screen_setup.png";
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        beginSetup = new MinedroidButton(1, INNER_X + 41, INNER_Y + 180, 80, "Start Setup", 0xFFFFFF);
        buttonList.add(beginSetup);

        SoundPlayerAppInfoPacket soundPacket = new SoundPlayerAppInfoPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "minedroid_startup";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        int stringWidth = fontRenderer.getStringWidth("Welcome!");

        GlStateManager.scale(uBigFont, uBigFont, uBigFont);
        fontRenderer.drawString("Welcome!", scale(INNER_X + INNER_TEX_WIDTH / 2, dBigFont) - stringWidth / 2, scale(INNER_Y + 60, dBigFont), 0xFFFFFF, true);
        GlStateManager.scale(dBigFont, dBigFont, dBigFont);

        drawCenteredString(fontRenderer, TextFormatting.ITALIC + "Your new Minedroid awaits!!", INNER_X + 80, INNER_Y + 110, 0xFFFFFF);
        drawCenteredString(fontRenderer, TextFormatting.ITALIC + "Click Start Setup to begin.", INNER_X + 80, INNER_Y + 130, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == beginSetup)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStepPersonalization(phoneStack, hand));
        }
    }
}
