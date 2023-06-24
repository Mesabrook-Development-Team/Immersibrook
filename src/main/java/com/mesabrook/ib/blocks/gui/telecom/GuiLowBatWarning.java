package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;


public class GuiLowBatWarning extends GuiPhoneBase
{
    MinedroidButton okButton;

    public GuiLowBatWarning(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/battery_warning.png";
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
        soundPacket.soundName = "battery_low";
        PacketHandler.INSTANCE.sendToServer(soundPacket);

        okButton = new MinedroidButton(1, INNER_X + 60, INNER_Y + 160, INNER_TEX_WIDTH - 120, "OK", 0xFFFFFF);
        buttonList.add(okButton);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString("Low Battery!", INNER_X + 35, INNER_Y + 45, 0xFFFFFF);

        fontRenderer.drawSplitString("Your phone's battery is very low. Please charge it soon.", INNER_X + 35, INNER_Y + 100, INNER_TEX_WIDTH - 55, 0xFFFFFF);

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == okButton)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiDebugMenu(phoneStack, hand));
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }
}
