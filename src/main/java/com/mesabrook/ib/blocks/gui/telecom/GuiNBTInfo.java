package com.mesabrook.ib.blocks.gui.telecom;

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

public class GuiNBTInfo extends GuiPhoneBase
{
    String[] phonedata = new String[] {"Loading NBT data..."};
    private int maxLinesShown = 0;
    private int boxWidth = 0;
    private int boxHeight = 0;
    private int currentPage = 1;
    private int totalPages = 1;
    private String currentLabel;
    private String currentText;
    MinedroidButton ok;
    MinedroidButton nextPage;
    MinedroidButton backPage;
    MinedroidButton saveToFile;

    public GuiNBTInfo(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        currentLabel = currentLabel != null ? currentLabel : "";
        currentText = currentText != null ? currentText : "";
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/app_screen_no_bar.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();
        boxWidth = width - 20;
        maxLinesShown = (INNER_TEX_HEIGHT - 120) / fontRenderer.FONT_HEIGHT;
        boxHeight = 22 + fontRenderer.FONT_HEIGHT * maxLinesShown;

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 50;
        backPage = new MinedroidButton(1, INNER_X + 3, lowerControlsY - 10, 35, "<<", 0xFFFFFF);
        nextPage = new MinedroidButton(2, INNER_X + 124, lowerControlsY - 10, 35, ">>", 0xFFFFFF);
        ok = new MinedroidButton(3, INNER_X + 25, lowerControlsY + 15, 40, "Back", 0xFFFFFF);
        saveToFile = new MinedroidButton(4, INNER_X + 75, lowerControlsY + 15, 69, "Save to Text", 0xFFFFFF);

        buttonList.add(backPage);
        buttonList.add(nextPage);
        buttonList.add(ok);
        buttonList.add(saveToFile);
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        drawCenteredString(fontRenderer, new TextComponentString(TextFormatting.BOLD + "Phone NBT Data").getFormattedText(), INNER_X + 80, INNER_Y + 23, 0xFFFFFF);
        currentText = phoneStack.getTagCompound().toString();
        drawRect(INNER_X + 3, INNER_Y + 40, INNER_X + 159, INNER_Y + 150, 0xFFAAAAAA);
        drawRect(INNER_X + 5, INNER_Y + 42, INNER_X + 157, INNER_Y + 148, 0xFF000000);
        for(int i = (currentPage - 1) * maxLinesShown; i < maxLinesShown * currentPage; i++)
        {
            if (i >= phonedata.length)
            {
                break;
            }

            String line = phonedata[i];
            fontRenderer.drawString(line, INNER_X + 5, INNER_Y + 42 + fontRenderer.FONT_HEIGHT * (i - (currentPage - 1) * maxLinesShown), 0xFFFFFF);
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
        this.phonedata = lines.toArray(new String[0]);

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 50;
        String pageCountLabel = String.format("Page %s/%s", currentPage, totalPages);
        drawCenteredString(fontRenderer, new TextComponentString(TextFormatting.BOLD + pageCountLabel).getFormattedText(), width / 2, lowerControlsY - 7, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if (button == nextPage && currentPage != totalPages)
        {
            currentPage ++;
        }

        if (button == backPage && currentPage > 1)
        {
            currentPage--;
        }

        if(button == ok)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiDebugMenu(phoneStack, hand));
        }

        if(button == saveToFile)
        {
            try
            {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");
                String formattedDateTime = currentDateTime.format(formatter);

                File minecraftDir = Minecraft.getMinecraft().mcDataDir;
                File saveFile = new File(minecraftDir, "/minedroid_nbt_dumps" + "/minedroid_dump_" + phoneStack.getDisplayName() + "_" + getCurrentPhoneNumber() + "_" + formattedDateTime + ".txt");
                saveFile.getParentFile().mkdirs();

                BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
                writer.write(currentText);
                writer.close();
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Dump data saved to " + saveFile.getPath()));
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
