package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.MinedroidCrashLogUploader;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GuiPhoneCrashed extends GuiPhoneBase
{
    private String errorTitle;
    private String errorStackTrace;
    private IndependentTimer timer;

    MinedroidButton resetButton;
    MinedroidButton uploadToPastebinButton;

    ImageButton crashIcon;

    public GuiPhoneCrashed(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        timer = new IndependentTimer();
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
        return "system/bsod.png";
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
        timer.update();

        if(timer.getElapsedTime() > 3000)
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
            Minecraft.getMinecraft().displayGuiScreen(new GuiMSACBootScreen(phoneStack, hand));
        }

        if(button == uploadToPastebinButton)
        {
            try
            {
                String stackToUpload = errorStackTrace;
                String pastebinURL = MinedroidCrashLogUploader.uploadText(stackToUpload);
                uploadToPastebinButton.enabled = false;

                GuiConfirmOpenLink guiConfirmOpenLink = new GuiConfirmOpenLink(this, pastebinURL, 1, true)
                {
                    @Override
                    protected void actionPerformed(GuiButton button) throws IOException
                    {
                        if (button.id == 0)
                        { // Yes button
                            // Handle Yes button click
                            try {
                                ModUtils.openWebLink(new URI(pastebinURL));
                                Minecraft.getMinecraft().displayGuiScreen(new GuiMSACBootScreen(phoneStack, hand));
                            } catch (URISyntaxException e) {
                                Minecraft.getMinecraft().displayGuiScreen(new GuiMSACBootScreen(phoneStack, hand));
                                e.printStackTrace();
                            }
                            Minecraft.getMinecraft().displayGuiScreen(null);
                        }
                        else if (button.id == 1)
                        { // No button
                            // Handle No button click
                            Minecraft.getMinecraft().displayGuiScreen(new GuiMSACBootScreen(phoneStack, hand));
                        }
                        else if (button.id == 2)
                        { // Copy to Clipboard button
                            // Handle Copy to Clipboard button click
                            StringSelection stringSelection = new StringSelection(pastebinURL);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(stringSelection, null);
                            Minecraft.getMinecraft().displayGuiScreen(new GuiMSACBootScreen(phoneStack, hand));
                            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Link copied to clipboard."));
                        }
                    }
                };
                Minecraft.getMinecraft().displayGuiScreen(guiConfirmOpenLink);
            }
            catch(Exception ex)
            {
                Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.crash.error").getFormattedText(), 0xFFFFFF));
                Main.logger.error(ex);
            }
        }
    }

    @Override
    public void onGuiClosed()
    {
        timer.reset();
        timer.stop();
    }
}
