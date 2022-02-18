package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.SoundPlayerAppInfoPacket;
import com.mesabrook.ib.util.ModUtils;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.io.IOException;

public class GuiSoundPlayer extends GuiPhoneBase
{
    private String currentlyPlaying = I18n.format("ib.musicapp.statusdefault");
    LabelButton playSound;
    LabelButton reset;
    LabelButton example;
    GuiTextField modIDText;
    GuiTextField soundIDText;

    public GuiSoundPlayer(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "app_screen_music.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();

        reset = new LabelButton(1, INNER_X + (INNER_TEX_WIDTH / 4), INNER_Y + INNER_TEX_HEIGHT - 33, "Reset", 0xFFFFFF);
        playSound = new LabelButton(2, INNER_X + INNER_TEX_WIDTH - (INNER_TEX_WIDTH / 4), INNER_Y + INNER_TEX_HEIGHT - 33, "Play", 0xFFFFFF);
        playSound.x = playSound.x - playSound.width;
        example = new LabelButton(3, INNER_X + INNER_TEX_WIDTH - 89, INNER_Y + INNER_TEX_HEIGHT - 136, "Load Example", 0xFFFFFF);

        modIDText = new GuiTextField(10, fontRenderer, INNER_X + 60, INNER_Y + 50, 80, 10);
        soundIDText = new GuiTextField(11, fontRenderer, INNER_X + 60, INNER_Y + 70, 80, 10);

        buttonList.addAll(ImmutableList.of(reset, playSound, example));
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString("Minedroid Sound Player", INNER_X + 3, INNER_Y + 20, 0xFFFFFF);

        fontRenderer.drawString("Mod ID:", INNER_X + 15, INNER_Y + 50, 0x4444FF);
        fontRenderer.drawString("Sound ID:", INNER_X + 3, INNER_Y + 70, 0x4444FF);

        drawCenteredString(fontRenderer, "Now Playing:", INNER_X + 80, INNER_Y + 120, 3395327);
        drawCenteredString(fontRenderer, currentlyPlaying, INNER_X + 80, INNER_Y + 140, 3395327);

        modIDText.drawTextBox();
        soundIDText.drawTextBox();

        GlStateManager.color(1, 1, 1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        modIDText.mouseClicked(mouseX, mouseY, mouseButton);
        soundIDText.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        modIDText.textboxKeyTyped(typedChar, keyCode);
        soundIDText.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        EntityPlayer player = Minecraft.getMinecraft().player;

        if(button == playSound)
        {
            if(modIDText.getText().isEmpty() || soundIDText.getText().isEmpty())
            {
                Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "Missing Entry", 0xFFFFFF));
            }
            else
            {
                SoundPlayerAppInfoPacket packet = new SoundPlayerAppInfoPacket();
                packet.pos = player.getPosition();
                packet.modID = modIDText.getText();
                packet.soundName = soundIDText.getText();
                PacketHandler.INSTANCE.sendToServer(packet);

                currentlyPlaying = ModUtils.truncator(packet.modID + ":" + packet.soundName, "...", INNER_TEX_WIDTH / 6);
            }
        }

        if(button == reset)
        {
            modIDText.setText("");
            soundIDText.setText("");
        }

        if(button == example)
        {
            modIDText.setText("minecraft");
            soundIDText.setText("record.chirp");
        }
    }
}
