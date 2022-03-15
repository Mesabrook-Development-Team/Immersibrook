package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.telecom.PersonalizationPacket;
import com.mesabrook.ib.util.GuiUtil;
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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.awt.*;
import java.io.IOException;

public class GuiSettingsPersonalization extends GuiPhoneBase
{
    LabelButton back;
    LabelButton wallpapers;
    LabelButton sounds;
    LabelButton phoneName;
    ImageButton wallpaperIcon;
    ImageButton soundsIcon;
    ImageButton phoneNameIcon;

    public GuiSettingsPersonalization(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "app_screen.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();

        back = new LabelButton(0, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);
        wallpapers = new LabelButton(1, INNER_X + 31, INNER_Y + 53, new TextComponentTranslation("im.settings.personalization.wallpapers").getFormattedText(), 0xFFFFFF);
        sounds = new LabelButton(2, INNER_X + 31, INNER_Y + 83, new TextComponentTranslation("im.settings.personalization.sounds").getFormattedText(), 0xFFFFFF);
        phoneName = new LabelButton(3, INNER_X + 31, INNER_Y + 113, new TextComponentTranslation("im.settings.personalization.phonename").getFormattedText(), 0xFFFFFF);

        wallpaperIcon = new ImageButton(4, INNER_X + 0, INNER_Y + 40, 28, 28, "btn_personalize.png", 32, 32);
        soundsIcon = new ImageButton(5, INNER_X + 0, INNER_Y + 70, 28, 28, "icn_sound.png", 32, 32);
        phoneNameIcon = new ImageButton(6, INNER_X + 0, INNER_Y + 100, 28, 28, "icn_name.png", 32, 32);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(back)
                .add(wallpaperIcon)
                .add(soundsIcon)
                .add(phoneNameIcon)
                .add(wallpapers)
                .add(sounds)
                .add(phoneName)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString(new TextComponentTranslation("im.settings.personalization").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettings(phoneStack, hand));
        }

        if(button == wallpaperIcon || button == wallpapers)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsWallpapers(phoneStack, hand));
        }

        if(button == phoneName || button == phoneNameIcon)
        {
            Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "Feature Coming Soon", 0xFFFFFF));
        }
    }
}
