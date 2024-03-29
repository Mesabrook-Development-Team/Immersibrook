package com.mesabrook.ib.blocks.gui.telecom;

import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.init.ModSounds;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.telecom.*;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.IOException;

public class GuiPhoneSetupComplete extends GuiPhoneBase
{
    private GuiLockScreen.UnlockSlider complete;
    ImageButton mux;

    public GuiPhoneSetupComplete(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return phoneStackData.getIconTheme() + "/app_screen_setup.png";
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
        complete = new GuiLockScreen.UnlockSlider(INNER_X + INNER_TEX_WIDTH / 2 - 60, INNER_Y + INNER_TEX_HEIGHT - 75);
        mux = new ImageButton(2, INNER_X + 15, INNER_Y + 27, 25, 25, "icn_mux.png", 32, 32);
        buttonList.add(mux);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        int stringWidth = fontRenderer.getStringWidth("Congrats!");

        GlStateManager.scale(uBigFont, uBigFont, uBigFont);
        fontRenderer.drawString("Congrats!", scale(INNER_X + 66, dBigFont) - stringWidth / 2, scale(INNER_Y + 60, dBigFont), 0xFFFFFF, true);
        GlStateManager.scale(dBigFont, dBigFont, dBigFont);

        fontRenderer.drawString("Your new phone is ready!", INNER_X + 15, INNER_Y + 105, 0xFFFFFF);

        complete.draw(mouseX, mouseY, partialticks);
        String swipeText = "Swipe to finish Setup!";
        int fontWidth = fontRenderer.getStringWidth(swipeText);

        fontRenderer.drawString(swipeText, INNER_X + INNER_TEX_WIDTH / 2 - fontWidth / 2, INNER_Y + INNER_TEX_HEIGHT - 60, 0xFFFFFF, true);

        if(complete.isSliderComplete())
        {
            goHome();
        }
    }

    private void goHome()
    {
        GuiPhoneBase.isPhoneUnlocked = true;

        OOBEStatusPacket packet = new OOBEStatusPacket();
        packet.hand = hand.ordinal();
        packet.guiClassName = GuiPhoneSetupComplete.class.getName();
        packet.nextGuiClassName = GuiHome.class.getName();
        packet.needToDoOOBE = false;

        PacketHandler.INSTANCE.sendToServer(packet);

        ClientSoundPacket soundPacket = new ClientSoundPacket();
        soundPacket.pos = Minecraft.getMinecraft().player.getPosition();
        soundPacket.soundName = "phone_unlock";
        soundPacket.useDelay = true;
        soundPacket.volume = 1.0F;
        PacketHandler.INSTANCE.sendToServer(soundPacket);
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