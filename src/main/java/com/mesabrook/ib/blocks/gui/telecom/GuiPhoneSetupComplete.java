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

public class GuiPhoneSetupComplete extends GuiPhoneBase
{
    MinedroidButton complete;

    public GuiPhoneSetupComplete(ItemStack phoneStack, EnumHand hand)
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
        complete = new MinedroidButton(1, INNER_X + 41, INNER_Y + 180, 80, "Finish Setup", 0xFFFFFF);
        buttonList.add(complete);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        int stringWidth = fontRenderer.getStringWidth("Congrats!");

        GlStateManager.scale(uBigFont, uBigFont, uBigFont);
        fontRenderer.drawString("Congrats!", scale(INNER_X + INNER_TEX_WIDTH / 2, dBigFont) - stringWidth / 2, scale(INNER_Y + 60, dBigFont), 0xFFFFFF, true);
        GlStateManager.scale(dBigFont, dBigFont, dBigFont);

        drawCenteredString(fontRenderer, TextFormatting.ITALIC + "Your new Minedroid phone", INNER_X + 80, INNER_Y + 110, 0xFFFFFF);
        drawCenteredString(fontRenderer, TextFormatting.ITALIC + "is now ready to use!", INNER_X + 80, INNER_Y + 130, 0xFFFFFF);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == complete)
        {
            GuiLockScreen done = new GuiLockScreen(Minecraft.getMinecraft().player.getHeldItem(hand), hand);
            Minecraft.getMinecraft().displayGuiScreen(done);
        }
    }
}