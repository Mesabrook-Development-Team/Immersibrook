package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.net.*;
import com.mesabrook.ib.util.handlers.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.client.config.*;

import java.io.*;

public class GuiWallSign extends GuiScreen
{
    GuiTextField lineOneBox;
    GuiTextField lineTwoBox;
    GuiButtonExt submit;
    private EnumHand hand;

    public GuiWallSign(EnumHand handIn) {this.hand = handIn;}

    @Override
    public void initGui()
    {
        super.initGui();
        int horizontalCenter = width / 2;
        int verticalCenter = height / 2;

        lineOneBox = new GuiTextField(1, fontRenderer, horizontalCenter - 90, verticalCenter - 30, 200, 20);
        lineOneBox.setFocused(true);
        lineTwoBox = new GuiTextField(2, fontRenderer, lineOneBox.x, lineOneBox.y + lineOneBox.height + 4, 200, 20);

        submit = new GuiButtonExt(1, lineTwoBox.x, lineTwoBox.y + 24, lineTwoBox.width, 20, "Submit");

        buttonList.add(submit);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        lineOneBox.drawTextBox();
        lineTwoBox.drawTextBox();
        int stringWidth = fontRenderer.getStringWidth("Name:");
        fontRenderer.drawString("Name:", lineOneBox.x - stringWidth - 4, lineOneBox.y + ((lineOneBox.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);

        stringWidth = fontRenderer.getStringWidth("Info:");
        fontRenderer.drawString("Info:", lineTwoBox.x - stringWidth - 4, lineTwoBox.y + ((lineTwoBox.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        lineOneBox.mouseClicked(mouseX, mouseY, mouseButton);
        lineTwoBox.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        lineOneBox.textboxKeyTyped(typedChar, keyCode);
        lineTwoBox.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button == submit && lineOneBox.getText() != null && !lineTwoBox.getText().equals(""))
        {
            WallSignPacket packet = new WallSignPacket();
            packet.lineOne = lineOneBox.getText();
            packet.lineTwo = lineTwoBox.getText();
            packet.hand = hand;
            PacketHandler.INSTANCE.sendToServer(packet);
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}
