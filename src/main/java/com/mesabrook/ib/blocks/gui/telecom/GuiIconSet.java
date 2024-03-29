package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.ImageButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.config.GuiCheckBox;

public class GuiIconSet extends GuiPhoneBase
{
    GuiCheckBox plex;
    GuiCheckBox aero_bubbles;
    GuiCheckBox luna;

    MinedroidButton reset;
    MinedroidButton apply;
    LabelButton back;

    String currentTheme;
    static String updatedTheme;

    public GuiIconSet(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName() {
        if(phoneStackData.getIconTheme().contains("luna"))
        {
            return "luna/app_background_settings_bar.png";
        }
        else
        {
            return phoneStackData.getIconTheme() + "/app_screen.png";
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();
        currentTheme = phoneStackData.getIconTheme();
        updatedTheme = phoneStackData.getIconTheme();

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        reset = new MinedroidButton(0, INNER_X + 45, lowerControlsY - 10, 32, new TextComponentTranslation("im.musicapp.buttonreset").getFormattedText(), 0xFFFFFF);
        apply = new MinedroidButton(1, INNER_X + 85, lowerControlsY - 10, 32, new TextComponentTranslation("im.settings.apply").getFormattedText(), 0xFFFFFF);
        back = new LabelButton(2, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);

        plex = new GuiCheckBox(3, INNER_X + 10, INNER_Y + 52, new TextComponentTranslation("im.settings.personalization.icontheme.plex").getFormattedText(), currentTheme.contains("plex"));
        aero_bubbles = new GuiCheckBox(4, INNER_X + 10, INNER_Y + 69, new TextComponentTranslation("im.settings.personalization.icontheme.aero").getFormattedText(), currentTheme.contains("aero_bubble"));
        luna = new GuiCheckBox(5, INNER_X + 10, INNER_Y + 86, new TextComponentTranslation("im.settings.personalization.icontheme.luna").getFormattedText(), currentTheme.contains("luna"));



        // Icon Showcase
        ImageButton button1 = new ImageButton(6, INNER_X + 8, INNER_Y + 130, 32, 32, phoneStackData.getIconTheme() + "/icn_phone.png", 32, 32);
        ImageButton button2 = new ImageButton(7, INNER_X + 45, INNER_Y + 130, 32, 32, phoneStackData.getIconTheme() + "/icn_mail.png", 32, 32);
        ImageButton button3 = new ImageButton(8, INNER_X + 83, INNER_Y + 130, 32, 32, phoneStackData.getIconTheme() + "/icn_settings.png", 32, 32);
        ImageButton button4 = new ImageButton(9, INNER_X + 120, INNER_Y + 130, 32, 32, phoneStackData.getIconTheme() + "/icn_musicplayer.png", 32, 32);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(reset)
                .add(apply)
                .add(back)
                .add(plex)
                .add(aero_bubbles)
                .add(luna)
                .add(button1)
                .add(button2)
                .add(button3)
                .add(button4)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);

        fontRenderer.drawString(new TextComponentTranslation("im.settings.personalization.icontheme").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
        fontRenderer.drawString(new TextComponentTranslation("im.settings.personalization.icontheme.current").getFormattedText(), INNER_X + 10, INNER_Y + 115, 0xFFFFFF);
    }

    public static String getUpdatedTheme()
    {
        return updatedTheme;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == plex)
        {
            GuiCheckBox checkBox = (GuiCheckBox)button;
            aero_bubbles.setIsChecked(false);
            luna.setIsChecked(false);
            updatedTheme = "plex";
        }

        if(button == aero_bubbles)
        {
            GuiCheckBox checkBox = (GuiCheckBox)button;
            plex.setIsChecked(false);
            luna.setIsChecked(false);
            updatedTheme = "aero_bubble";
        }

        if(button == luna)
        {
            GuiCheckBox checkBox = (GuiCheckBox)button;
            plex.setIsChecked(false);
            aero_bubbles.setIsChecked(false);
            updatedTheme = "luna";
        }

        if(button == apply)
        {
            if(!plex.isChecked() && !aero_bubbles.isChecked() && !luna.isChecked())
            {
                Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "A theme must be chosen", 0xFF0000));
                return;
            }

            if(updatedTheme != currentTheme)
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiChangingTheme(phoneStack, hand));
            }
            else
            {
                Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, "Theme is already active", 0xFF0000));
            }
        }

        if(button == reset)
        {
            updatedTheme = "plex";
            Minecraft.getMinecraft().displayGuiScreen(new GuiChangingTheme(phoneStack, hand));
        }

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsPersonalization(phoneStack, hand));
        }
    }
}
