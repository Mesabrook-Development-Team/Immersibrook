package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.blocks.container.ContainerSmartphone;
import com.mesabrook.ib.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiSmartphoneInv extends GuiContainer
{
    ContainerSmartphone containerSmartphone;
    public GuiSmartphoneInv(ContainerSmartphone container)
    {
        super(container);
        containerSmartphone = container;

        xSize = 176;
        ySize = 132;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        // Inventory Text
        this.fontRenderer.drawString("Smartphone (NYI)", 7, 7, 0x0f0f0f, false);
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        drawDefaultBackground();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/smartphone_gui.png"));
        drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
    }
}
