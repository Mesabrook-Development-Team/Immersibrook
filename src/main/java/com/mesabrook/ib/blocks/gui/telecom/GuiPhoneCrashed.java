package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.*;
import com.mesabrook.ib.util.handlers.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;

import java.io.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class GuiPhoneCrashed extends GuiPhoneBase
{
    private String errorTitle;
    private String errorStackTrace;
    private int delay = 0;

    MinedroidButton resetButton;
    MinedroidButton copyStackToClipboardButton;

    ImageButton crashIcon;

    public GuiPhoneCrashed(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    public String getCrashTitle()
    {
        return errorTitle;
    }

    public String setErrorTitle(String errorTitleIn)
    {
        errorTitle = errorTitleIn;
        return errorTitle;
    }

    public String getErrorStackTrace()
    {
        return errorStackTrace;
    }

    public String setErrorStackTrace(String errorStackTraceIn)
    {
        errorStackTrace = errorStackTraceIn;
        return errorStackTrace;
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "bsod.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();

        resetButton = new MinedroidButton(1, INNER_X + 3, INNER_Y + 180, INNER_TEX_WIDTH - 6, "Restart Phone", 0xFFFFFF);
        copyStackToClipboardButton = new MinedroidButton(2, INNER_X + 3, INNER_Y + 160, INNER_TEX_WIDTH - 6, "Copy Error to Clipboard", 0xFFFFFF);

        resetButton.visible = false;
        copyStackToClipboardButton.visible = false;

        crashIcon = new ImageButton(3, INNER_X + 59, INNER_Y + 8, 32, 32, "crashed.png", 32, 32);

        buttonList.add(resetButton);
        buttonList.add(copyStackToClipboardButton);
        buttonList.add(crashIcon);

        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "phone_crash";
        soundPacket.volume = 0.2F;
        PacketHandler.INSTANCE.sendToServer(soundPacket);
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }

    @Override
    protected boolean renderTopBar() { return false; }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);

        delay++;

        if(delay > 325)
        {
            drawCenteredString(fontRenderer, new TextComponentString(TextFormatting.BOLD + "Minedroid has crashed.").getFormattedText() , INNER_X + 80, INNER_Y + 50, 0xFFFFFF);

            fontRenderer.drawSplitString(new TextComponentString(TextFormatting.ITALIC + getCrashTitle()).getFormattedText(), INNER_X + 20, INNER_Y + 65, 125, 0xFFFFFF);

            resetButton.visible = true;
            copyStackToClipboardButton.visible = true;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == resetButton)
        {
            isPhoneUnlocked = false;
            ClientSoundPacket soundPacket = new ClientSoundPacket();
            soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
            soundPacket.soundName = "minedroid_startup";
            PacketHandler.INSTANCE.sendToServer(soundPacket);
            Minecraft.getMinecraft().displayGuiScreen(new GuiLockScreen(phoneStack, hand));
        }

        if(button == copyStackToClipboardButton)
        {
            StringSelection stringSelection = new StringSelection(getErrorStackTrace());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            clipboard.setContents(stringSelection, null);
            copyStackToClipboardButton.enabled = false;

            Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Error Copied"));
        }
    }
}
