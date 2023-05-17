package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;

public class GuiPhoneSetupStart extends GuiPhoneBase
{
    private GuiLockScreen.UnlockSlider complete;
    ImageButton mux;
    
    public GuiPhoneSetupStart(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "app_screen_setup.png";
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
        mux = new ImageButton(2, INNER_X + 15, INNER_Y + 27, 25, 25, "icn_mux.png", 32, 32);
        complete = new GuiLockScreen.UnlockSlider(INNER_X + INNER_TEX_WIDTH / 2 - 60, INNER_Y + INNER_TEX_HEIGHT - 75);
        buttonList.add(mux);

        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "minedroid_startup";
        PacketHandler.INSTANCE.sendToServer(soundPacket);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        int stringWidth = fontRenderer.getStringWidth("Hi!");

        GlStateManager.scale(uBigFont, uBigFont, uBigFont);
        fontRenderer.drawString("Hi!", scale(INNER_X + 29, dBigFont) - stringWidth / 2, scale(INNER_Y + 60, dBigFont), 0xFFFFFF, true);
        GlStateManager.scale(dBigFont, dBigFont, dBigFont);

        fontRenderer.drawString("Welcome to Minedroid!", INNER_X + 15, INNER_Y + 105, 0xFFFFFF);

        complete.draw(mouseX, mouseY, partialticks);
        String swipeText = "Swipe to begin Setup!";
        int fontWidth = fontRenderer.getStringWidth(swipeText);

        fontRenderer.drawString(swipeText, INNER_X + INNER_TEX_WIDTH / 2 - fontWidth / 2, INNER_Y + INNER_TEX_HEIGHT - 60, 0xFFFFFF, true);

        if(complete.isSliderComplete())
        {
            nextStep();
        }
    }

    private void nextStep()
    {
        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "ding_2";
        PacketHandler.INSTANCE.sendToServer(soundPacket);

        Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneNameSetup(phoneStack, hand));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        complete.mouseClicked(mouseX, mouseY);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        complete.mouseReleased();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
    }
}
