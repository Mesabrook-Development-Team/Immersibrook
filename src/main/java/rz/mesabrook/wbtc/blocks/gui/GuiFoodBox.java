package rz.mesabrook.wbtc.blocks.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import rz.mesabrook.wbtc.net.FoodBoxPacket;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;

public class GuiFoodBox extends GuiScreen
{
    GuiTextField boxIDBox;
    GuiTextField companyBox;
    GuiButtonExt print;
    private EnumHand hand;
    public GuiFoodBox(EnumHand hand) {this.hand = hand;}

    @Override
    public void initGui()
    {
        int horizontalCenter = width / 2;
        int verticalCenter = height / 2;

        boxIDBox = new GuiTextField(1, fontRenderer, horizontalCenter - 90, verticalCenter - 30, 200, 20);
        boxIDBox.setFocused(true);
        companyBox = new GuiTextField(2, fontRenderer, boxIDBox.x, boxIDBox.y + boxIDBox.height + 4, 200, 20);
        print = new GuiButtonExt(1, companyBox.x, companyBox.y + 24, companyBox.width, 20, "Print");

        buttonList.add(print);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();

        boxIDBox.drawTextBox();
        companyBox.drawTextBox();
        int stringWidth = fontRenderer.getStringWidth("Product Type:");
        fontRenderer.drawString("Product Type:", boxIDBox.x - stringWidth - 4, boxIDBox.y + ((boxIDBox.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);

        stringWidth = fontRenderer.getStringWidth("Company Name:");
        fontRenderer.drawString("Company Name: ", companyBox.x - stringWidth - 4, companyBox.y + ((companyBox.height - fontRenderer.FONT_HEIGHT) / 2), 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, partialTicks); 
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        boxIDBox.mouseClicked(mouseX, mouseY, mouseButton);
        companyBox.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        boxIDBox.textboxKeyTyped(typedChar, keyCode);
        companyBox.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button == print && boxIDBox.getText() != null && !companyBox.getText().equals(""))
        {
            FoodBoxPacket packet = new FoodBoxPacket();
            packet.boxID = boxIDBox.getText();
            packet.company = companyBox.getText();
            packet.hand = hand;
            PacketHandler.INSTANCE.sendToServer(packet);

            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}
