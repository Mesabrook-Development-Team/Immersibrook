package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.blocks.gui.telecom.ImageButton;
import com.mesabrook.ib.util.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.net.URI;
import java.net.URISyntaxException;

public class GuiAboutImmersibrook extends GuiScreen
{
    private GuiButton closeButton;
    private ImageButton githubButton;
    private int backgroundWidth  = 250;
    private int backgroundHeight  = 260;

    @Override
    public void initGui()
    {
        // Close Button
        this.buttonList.add(this.closeButton = new GuiButton(0, (this.width - this.backgroundWidth + 365) / 2, (this.height + this.backgroundHeight - 155) / 2, this.backgroundWidth - 190, 20, "Close"));

        // GitHub Button
        //this.buttonList.add(this.githubButton = new GuiButton(1, (this.width - this.backgroundWidth + 27) / 2, (this.height + this.backgroundHeight - 155) / 2, this.backgroundWidth - 185, 20, "GitHub"));
        githubButton = new ImageButton(1, (this.width - this.backgroundWidth + 30) / 2, (this.height + this.backgroundHeight - 153) / 2, 16, 16, "icn_github.png", 16, 16);
        this.buttonList.add(githubButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2 - this.closeButton.height;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/about.png"));
        this.drawTexturedModalRect(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // Text
        drawCenteredString(fontRenderer, "Version " + Reference.VERSION, (this.width + 0) / 2, (this.height + this.backgroundHeight - 410) / 2, 111111);
        drawCenteredString(fontRenderer, Reference.UPDATE_NAME, (this.width + 0) / 2, (this.height + this.backgroundHeight - 330) / 2, 16777215);

        drawCenteredString(fontRenderer, "Developers:", (this.width + 0) / 2, (this.height + this.backgroundHeight - 280) / 2, 16777215);
        drawCenteredString(fontRenderer, "RavenholmZombie", (this.width + 0) / 2, (this.height + this.backgroundHeight - 250) / 2, 16777215);
        drawCenteredString(fontRenderer, "CSX8600", (this.width + 0) / 2, (this.height + this.backgroundHeight - 220) / 2, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
        {
            this.mc.player.closeScreen();
        }

        if(button.id == 1)
        {
            try
            {
                ModUtils.openWebLink(new URI("https://github.com/RavenholmZombie/Immersibrook/wiki"));
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
