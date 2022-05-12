package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.SoundPlayerAppInfoPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;

public class GuiPhoneSetupStart extends GuiPhoneBase
{
    MinedroidButton beginSetup;
    ImageButton mux;

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
        beginSetup = new MinedroidButton(1, INNER_X + 100, INNER_Y + 180, 50, "Begin >", 0xFFFFFF);
        mux = new ImageButton(2, INNER_X + 15, INNER_Y + 27, 25, 25, "icn_mux.png", 32, 32);
        buttonList.add(beginSetup);
        buttonList.add(mux);

        SoundPlayerAppInfoPacket soundPacket = new SoundPlayerAppInfoPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "minedroid_startup";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        int stringWidth = fontRenderer.getStringWidth("Hi!");

        GlStateManager.scale(uBigFont, uBigFont, uBigFont);
        fontRenderer.drawString("Hi!", scale(INNER_X + 29, dBigFont) - stringWidth / 2, scale(INNER_Y + 60, dBigFont), 0xFFFFFF, true);
        GlStateManager.scale(dBigFont, dBigFont, dBigFont);

        fontRenderer.drawString("Welcome to Minedroid!", INNER_X + 15, INNER_Y + 105, 0xFFFFFF);
        fontRenderer.drawString("Click Begin to start setup.", INNER_X + 15, INNER_Y + 125, 0xFFFFFF);
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
