package rz.mesabrook.wbtc.blocks.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import rz.mesabrook.wbtc.util.Reference;

public class GuiAboutImmersibrook extends GuiScreen
{
    private GuiButton closeButton;
    private GuiButton githubButton;
    private int backgroundWidth  = 509;
    private int backgroundHeight  = 484;

    @Override
    public void initGui()
    {
        // Close Button
        this.buttonList.add(this.closeButton = new GuiButton(0, (this.width - this.backgroundWidth + 100) / 2, (this.height + this.backgroundHeight - 60) / 2, this.backgroundWidth - 100, 20, "Close"));

        // GitHub Button
        this.buttonList.add(this.githubButton = new GuiButton(1, (this.width - this.backgroundWidth + 100) / 2, (this.height + this.backgroundHeight - 60) / 2, this.backgroundWidth - 100, 20, "Close"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2 - this.closeButton.height;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/about.png"));
        this.drawTexturedModalRect(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
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

        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }
}
