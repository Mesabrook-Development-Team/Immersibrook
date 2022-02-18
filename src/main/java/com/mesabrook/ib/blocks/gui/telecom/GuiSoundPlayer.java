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
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;

public class GuiSoundPlayer extends GuiPhoneBase
{
    private String currentlyPlaying = I18n.format("ib.musicapp.statusdefault");
    MinedroidButton playSound;
    MinedroidButton reset;
    MinedroidButton example;
    GuiTextField modIDText;
    GuiTextField soundIDText;
    GuiTextField volumeText;
    GuiTextField pitchText;

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

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        reset = new MinedroidButton(1, INNER_X + 50, lowerControlsY - 20, 32, "Reset", 0xFFFFFF);
        playSound = new MinedroidButton(2, INNER_X + 85, lowerControlsY - 20, 30, "Play", 0xFFFFFF);
        example = new MinedroidButton(3, reset.x, lowerControlsY - 35, 65, "Example", 0xFFFFFF);

        modIDText = new GuiTextField(10, fontRenderer, INNER_X + 60, INNER_Y + 50, 85, 10);
        soundIDText = new GuiTextField(11, fontRenderer, INNER_X + 60, INNER_Y + 70, 85, 10);

        volumeText = new GuiTextField(12, fontRenderer, INNER_X + 60, INNER_Y + 90, 25, 10);
        pitchText = new GuiTextField(13, fontRenderer, INNER_X + 120, INNER_Y + 90, 25, 10);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(reset)
                .add(playSound)
                .add(example)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString("Minedroid Sound Player", INNER_X + 3, INNER_Y + 20, 0xFFFFFF);

        fontRenderer.drawString("Mod ID:", INNER_X + 15, INNER_Y + 50, 0x4444FF);
        fontRenderer.drawString("Sound ID:", INNER_X + 3, INNER_Y + 70, 0x4444FF);
        fontRenderer.drawString("  Volume:", INNER_X + 3, INNER_Y + 90, 0x4444FF);
        fontRenderer.drawString("  Pitch:", INNER_X + 82, INNER_Y + 90, 0x4444FF);

        drawCenteredString(fontRenderer, TextFormatting.BOLD + "Now Playing:", INNER_X + 80, INNER_Y + 115, 3395327);
        drawCenteredString(fontRenderer, TextFormatting.ITALIC + currentlyPlaying, INNER_X + 80, INNER_Y + 129, 3395327);

        modIDText.drawTextBox();
        soundIDText.drawTextBox();
        volumeText.drawTextBox();
        pitchText.drawTextBox();

        GlStateManager.color(1, 1, 1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        modIDText.mouseClicked(mouseX, mouseY, mouseButton);
        soundIDText.mouseClicked(mouseX, mouseY, mouseButton);
        volumeText.mouseClicked(mouseX, mouseY, mouseButton);
        pitchText.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        modIDText.textboxKeyTyped(typedChar, keyCode);
        soundIDText.textboxKeyTyped(typedChar, keyCode);
        volumeText.textboxKeyTyped(typedChar, keyCode);
        pitchText.textboxKeyTyped(typedChar, keyCode);
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

                try
                {
                    packet.volume = Float.parseFloat(volumeText.getText());
                    packet.pitch = Float.parseFloat(pitchText.getText());
                }
                catch(Exception ex)
                {
                    packet.volume = 1.0F;
                    packet.pitch = 1.0F;
                    volumeText.setText("1.0");
                    pitchText.setText("1.0");
                }

                PacketHandler.INSTANCE.sendToServer(packet);

                currentlyPlaying = ModUtils.truncator(packet.modID + ":" + packet.soundName, "...", INNER_TEX_WIDTH / 6);
            }
        }

        if(button == reset)
        {
            modIDText.setText("");
            soundIDText.setText("");
            volumeText.setText("");
            pitchText.setText("");
        }

        if(button == example)
        {
            modIDText.setText("minecraft");
            soundIDText.setText("record.chirp");
            volumeText.setText("1.0");
            pitchText.setText("1.0");
        }
    }
}
