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
    ImageButton mux;

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
        complete = new MinedroidButton(1, INNER_X + 110, INNER_Y + 180, 40, "Finish", 0xFFFFFF);
        mux = new ImageButton(2, INNER_X + 15, INNER_Y + 27, 25, 25, "icn_mux.png", 32, 32);
        buttonList.add(complete);
        buttonList.add(mux);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        int stringWidth = fontRenderer.getStringWidth("Congrats!");

        GlStateManager.scale(uBigFont, uBigFont, uBigFont);
        fontRenderer.drawString("Congrats!", scale(INNER_X + 66, dBigFont) - stringWidth / 2, scale(INNER_Y + 60, dBigFont), 0xFFFFFF, true);
        GlStateManager.scale(dBigFont, dBigFont, dBigFont);

        fontRenderer.drawString("Your new phone is ready!", INNER_X + 15, INNER_Y + 105, 0xFFFFFF);
        fontRenderer.drawString("Click Finish to exit setup.", INNER_X + 15, INNER_Y + 125, 0xFFFFFF);
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