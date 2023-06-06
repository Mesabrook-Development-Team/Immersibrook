package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.net.*;
import com.mesabrook.ib.util.MinedroidCrashLogUploader;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.handlers.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;

import java.io.*;
import java.net.URI;

public class GuiPhoneCrashed extends GuiPhoneBase
{
    private String errorTitle;
    private String errorStackTrace;
    private int delay = 0;

    MinedroidButton resetButton;
    MinedroidButton uploadToPastebinButton;

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
        uploadToPastebinButton = new MinedroidButton(2, INNER_X + 3, INNER_Y + 160, INNER_TEX_WIDTH - 6, "Upload Crash to Pastebin", 0xFFFFFF);

        resetButton.visible = false;
        uploadToPastebinButton.visible = false;

        crashIcon = new ImageButton(3, INNER_X + 59, INNER_Y + 8, 32, 32, "crashed.png", 32, 32);

        buttonList.add(resetButton);
        buttonList.add(uploadToPastebinButton);
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
            uploadToPastebinButton.visible = true;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == resetButton)
        {
            isPhoneUnlocked = false;
            Minecraft.getMinecraft().displayGuiScreen(new GuiBootScreen(phoneStack, hand));
        }

        if(button == uploadToPastebinButton)
        {
            try
            {
                String stackToUpload = errorStackTrace;
                String PastebinURL = MinedroidCrashLogUploader.uploadText(stackToUpload);

                ModUtils.openWebLink(new URI(PastebinURL));
                uploadToPastebinButton.enabled = false;

                Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.crash.uploaded").getFormattedText(), 0xFFFFFF));
            }
            catch(Exception ex)
            {
                Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.crash.error").getFormattedText(), 0xFFFFFF));
                Main.logger.error(ex);
            }
        }
    }
}
