package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.blocks.te.TileEntitySoundEmitter;
import com.mesabrook.ib.net.*;
import com.mesabrook.ib.util.handlers.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.config.*;
import org.lwjgl.input.*;

import java.io.*;

public class GuiSoundEmitter extends GuiScreen
{
    GuiTextField modIDTextField;
    GuiTextField soundIDTextField;
    GuiTextField rangeTextField;
    GuiButton submitButton;
    EnumHand hand;
    BlockPos blockPos;

    public GuiSoundEmitter(EnumHand handIn, BlockPos posIn) {this.hand = handIn; this.blockPos = posIn;}

    @Override
    public void initGui()
    {
        super.initGui();

        int horizontalCenter = width / 2;
        int verticalCenter = height / 2;

        modIDTextField = new GuiTextField(1, fontRenderer, horizontalCenter - 90, verticalCenter - 30, 200, 20);
        modIDTextField.setFocused(true);

        soundIDTextField = new GuiTextField(2, fontRenderer, modIDTextField.x, modIDTextField.y + modIDTextField.height + 4, 200, 20);
        rangeTextField = new GuiTextField(3, fontRenderer, soundIDTextField.x, soundIDTextField.y + soundIDTextField.height + 4, 200, 20);

        submitButton = new GuiButtonExt(4, rangeTextField.x, rangeTextField.y + 24, rangeTextField.width, 20, "Submit");
        buttonList.add(submitButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();

        modIDTextField.drawTextBox();
        soundIDTextField.drawTextBox();
        rangeTextField.drawTextBox();
        int stringWidth = fontRenderer.getStringWidth("Mod ID:");
        fontRenderer.drawString("Mod ID:", modIDTextField.x - stringWidth - 4, modIDTextField.y + ((modIDTextField.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);

        stringWidth = fontRenderer.getStringWidth("Sound ID:");
        fontRenderer.drawString("Sound ID:", soundIDTextField.x - stringWidth - 4, soundIDTextField.y + ((soundIDTextField.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);

        stringWidth = fontRenderer.getStringWidth("Sound Radius:");
        fontRenderer.drawString("Sound Radius:", rangeTextField.x - stringWidth - 4, rangeTextField.y + ((rangeTextField.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        modIDTextField.mouseClicked(mouseX, mouseY, mouseButton);
        soundIDTextField.mouseClicked(mouseX, mouseY, mouseButton);
        rangeTextField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        modIDTextField.textboxKeyTyped(typedChar, keyCode);
        soundIDTextField.textboxKeyTyped(typedChar, keyCode);
        rangeTextField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);

        if(keyCode == Keyboard.KEY_TAB)
        {
            if(modIDTextField.isFocused())
            {
                modIDTextField.setFocused(false);
                soundIDTextField.setFocused(true);
            }
            else if(soundIDTextField.isFocused())
            {
                soundIDTextField.setFocused(false);
                rangeTextField.setFocused(true);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == submitButton)
        {
            if(modIDTextField.getText() != null && soundIDTextField.getText() != null && rangeTextField.getText() != null)
            {
                // Packet go brrrrrr
                SoundEmitterBlockPacket packet = new SoundEmitterBlockPacket();
                packet.modID = modIDTextField.getText();
                packet.soundID = soundIDTextField.getText();

                try
                {
                    packet.range = Integer.parseInt(rangeTextField.getText());
                }
                catch(Exception ex)
                {
                    packet.range = 25;
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ex.getMessage()));
                }

                packet.pos = blockPos;
                packet.hand = hand;

                PacketHandler.INSTANCE.sendToServer(packet);

                Minecraft.getMinecraft().displayGuiScreen(null);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
