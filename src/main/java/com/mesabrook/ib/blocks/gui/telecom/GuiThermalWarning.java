package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class GuiThermalWarning extends GuiPhoneBase
{
    int timerToCloseGUI = 0;
    public GuiThermalWarning(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/thermal_warning.png";
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
        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "alert";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString("Thermal Warning", INNER_X + 40, INNER_Y + 75, 0xFFFFFF);
        fontRenderer.drawSplitString("To prevent damage to your phone, it has shut down due to extreme temperatures. Please cool your phone down.", INNER_X + 29, INNER_Y + 100, INNER_TEX_WIDTH - 55, 0xFFFFFF);

        timerToCloseGUI++;

        if(timerToCloseGUI >= 300)
        {
            CloseGUI();
        }
    }

    private void CloseGUI()
    {
        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }
}
