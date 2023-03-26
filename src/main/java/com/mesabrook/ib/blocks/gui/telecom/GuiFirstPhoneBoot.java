package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;


public class GuiFirstPhoneBoot extends GuiPhoneBase
{
    private int timerToNextScreen = 0;

    public GuiFirstPhoneBoot(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "app_screen_boot.png";
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }

    @Override
    protected boolean renderTopBar() {
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "minedroid_firstboot";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        timerToNextScreen++;

        if(timerToNextScreen >= 1500)
        {
            goToOOBE();
        }
    }

    private void goToOOBE()
    {
        GuiPhoneSetupStart oobe = new GuiPhoneSetupStart(Minecraft.getMinecraft().player.getHeldItem(hand), hand);
        Minecraft.getMinecraft().displayGuiScreen(oobe);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        timerToNextScreen = 0;
    }
}
