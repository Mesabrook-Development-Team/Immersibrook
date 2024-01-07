package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.IndependentTimer;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GuiPhoneCrashed extends GuiPhoneBase
{
    String[] crashText = new String[] {"Dumping crash report..."};
    private int maxLinesShown = 0;
    private int boxWidth = 0;
    private int boxHeight = 0;
    private int currentPage = 1;
    private int totalPages = 1;
    private String currentLabel;
    private String currentText;

    private String errorTitle;
    private String errorStackTrace;
    private IndependentTimer timer;

    MinedroidButton resetButton;
    MinedroidButton uploadToPastebinButton;
    MinedroidButton copyToClipboardFailback;
    MinedroidButton nextPage;
    MinedroidButton backPage;

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

        boxWidth = width - 20;
        maxLinesShown = (INNER_TEX_HEIGHT - 120) / fontRenderer.FONT_HEIGHT;
        boxHeight = 22 + fontRenderer.FONT_HEIGHT * maxLinesShown;

        resetButton = new MinedroidButton(1, INNER_X + 3, INNER_Y + 180, INNER_TEX_WIDTH - 6, "Recover Session", 0xFFFFFF);
        uploadToPastebinButton = new MinedroidButton(2, INNER_X + 3, INNER_Y + 160, INNER_TEX_WIDTH - 6, "Upload Crash to Pastebin", 0xFFFFFF);
        copyToClipboardFailback = new MinedroidButton(4, INNER_X + 3, INNER_Y + 140, INNER_TEX_WIDTH - 6, "Copy Error to Clipboard", 0xFFFFFF);

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 50;
        backPage = new MinedroidButton(10, INNER_X + 3, lowerControlsY - 10, 35, "<<", 0xFFFFFF);
        nextPage = new MinedroidButton(11, INNER_X + 124, lowerControlsY - 10, 35, ">>", 0xFFFFFF);

        resetButton.visible = false;
        uploadToPastebinButton.visible = false;
        copyToClipboardFailback.visible = false;
        nextPage.visible = false;
        backPage.visible = false;

        crashIcon = new ImageButton(3, INNER_X + 59, INNER_Y + 1, 32, 32, "crashed.png", 32, 32);

        buttonList.add(resetButton);
        buttonList.add(uploadToPastebinButton);
        buttonList.add(crashIcon);
        buttonList.add(copyToClipboardFailback);
        buttonList.add(nextPage);
        buttonList.add(backPage);

        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "phone_crash";
        soundPacket.volume = 1F;
        PacketHandler.INSTANCE.sendToServer(soundPacket);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");
        String formattedDateTime = currentDateTime.format(formatter);

        File minecraftDir = Minecraft.getMinecraft().mcDataDir;
        File saveFile = new File(minecraftDir, "/crash-reports" + "/minedroid_crash_" + getCurrentPhoneNumber() + "_" + formattedDateTime + ".txt");
        saveFile.getParentFile().mkdirs();

        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(getErrorStackTrace());
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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

        if(timer.getElapsedTime() > 4000)
        {
            drawCenteredString(fontRenderer, new TextComponentString(TextFormatting.BOLD + "Minedroid has crashed.").getFormattedText() , INNER_X + 80, INNER_Y + 35, 0xFFFFFF);

            fontRenderer.drawSplitString(new TextComponentString(TextFormatting.ITALIC + getCrashTitle()).getFormattedText(), INNER_X + 20, INNER_Y + 65, 125, 0xFFFFFF);

            resetButton.visible = true;
            uploadToPastebinButton.visible = false;
            nextPage.visible = true;
            backPage.visible = true;

            drawRect(INNER_X + 3, INNER_Y + 50, INNER_X + 159, INNER_Y + 150, 0xFFAAAAAA);
            drawRect(INNER_X + 5, INNER_Y + 52, INNER_X + 157, INNER_Y + 148, 0xFF000000);
            currentText = new TextComponentString("Minedroid has crashed. The crash report has been saved to your /crash-reports directory." + "\n" + "You can view the report below or tap Recover Session. \n" + "========================= \n" + getCrashTitle() + "\n\n" + getErrorStackTrace()).getFormattedText();
            for(int i = (currentPage - 1) * maxLinesShown; i < maxLinesShown * currentPage; i++)
            {
                if (i >= crashText.length)
                {
                    break;
                }

                String line = crashText[i];
                fontRenderer.drawString(line, INNER_X + 5, INNER_Y + 52 + fontRenderer.FONT_HEIGHT * (i - (currentPage - 1) * maxLinesShown), 0xFFFFFF);
            }

            String[] untruncatedLines = currentText.split("\\\\n");
            ArrayList<String> lines = new ArrayList<>();
            for(String untruncatedLine : untruncatedLines)
            {
                for(String truncatedLine : fontRenderer.listFormattedStringToWidth(untruncatedLine, INNER_TEX_WIDTH - 10))
                {
                    lines.add(truncatedLine);
                }
            }

            totalPages = lines.size() / maxLinesShown + 1;
            this.crashText = lines.toArray(new String[0]);

            int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 50;
            String pageCountLabel = String.format("Page %s/%s", currentPage, totalPages);
            drawCenteredString(fontRenderer, new TextComponentString(TextFormatting.BOLD + pageCountLabel).getFormattedText(), width / 2, lowerControlsY - 7, 0xFFFFFF);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == resetButton)
        {
            isPhoneUnlocked = false;
            Minecraft.getMinecraft().displayGuiScreen(new GuiRecoverSession(phoneStack, hand));
        }

        if (button == nextPage && currentPage != totalPages)
        {
            currentPage ++;
        }

        if (button == backPage && currentPage > 1)
        {
            currentPage--;
        }
    }

    @Override
    public void onGuiClosed()
    {
        timer.reset();
        timer.stop();
    }
}
