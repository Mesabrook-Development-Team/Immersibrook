package com.mesabrook.ib.blocks.gui.telecom;

import java.io.IOException;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.config.GuiCheckBox;

public class GuiPhoneSetupThemeStep extends GuiPhoneBase
{
    GuiCheckBox plex;
    GuiCheckBox aero_bubbles;
    GuiCheckBox luna;

    MinedroidButton back;
    MinedroidButton apply;

    String currentTheme;
    static String updatedTheme;

    public GuiPhoneSetupThemeStep(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        currentTheme = "";
    }

    @Override
    protected String getInnerTextureFileName() {
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
        currentTheme = phoneStackData.getIconTheme();
        updatedTheme = phoneStackData.getIconTheme();

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        back = new MinedroidButton(0, INNER_X + 45, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.back").getFormattedText(), 0xFFFFFF);
        apply = new MinedroidButton(1, INNER_X + 85, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.next").getFormattedText(), 0xFFFFFF);

        plex = new GuiCheckBox(3, INNER_X + 10, INNER_Y + 52, new TextComponentTranslation("im.settings.personalization.icontheme.plex").getFormattedText(), currentTheme.contains("plex"));
        aero_bubbles = new GuiCheckBox(4, INNER_X + 10, INNER_Y + 69, new TextComponentTranslation("im.settings.personalization.icontheme.aero").getFormattedText(), currentTheme.contains("aero_bubble"));
        luna = new GuiCheckBox(5, INNER_X + 10, INNER_Y + 86, new TextComponentTranslation("im.settings.personalization.icontheme.luna").getFormattedText(), currentTheme.contains("luna"));

        // Icon Showcase
        ImageButton button1 = new ImageButton(6, INNER_X + 8, INNER_Y + 130, 32, 32, phoneStackData.getIconTheme() + "/icn_phone.png", 32, 32);
        ImageButton button2 = new ImageButton(7, INNER_X + 45, INNER_Y + 130, 32, 32, phoneStackData.getIconTheme() + "/icn_mail.png", 32, 32);
        ImageButton button3 = new ImageButton(8, INNER_X + 83, INNER_Y + 130, 32, 32, phoneStackData.getIconTheme() + "/icn_settings.png", 32, 32);
        ImageButton button4 = new ImageButton(9, INNER_X + 120, INNER_Y + 130, 32, 32, phoneStackData.getIconTheme() + "/icn_musicplayer.png", 32, 32);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(back)
                .add(apply)
                .add(plex)
                .add(aero_bubbles)
                .add(luna)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);

        drawCenteredString(fontRenderer, new TextComponentTranslation("im.settings.personalization.iconthemesetup").getFormattedText(), INNER_X + 80, INNER_Y + 20, 0xFFFFFF);
        fontRenderer.drawSplitString(new TextComponentTranslation("im.settings.personalization.themesetupblurb").getFormattedText(), INNER_X + 10, INNER_Y + 120, INNER_TEX_WIDTH - 12, 0xFFFFFF);

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

            CustomizationPacket packet = new CustomizationPacket();
            packet.hand = hand.ordinal();
            packet.newName = phoneStack.getDisplayName();
            packet.guiClassName = GuiIconSet.class.getName();
            packet.iconTheme = updatedTheme;
            packet.lockBackground = phoneStackData.getLockBackground();
            packet.homeBackground = phoneStackData.getHomeBackground();
            packet.lockBackground = phoneStackData.getLockBackground();
            packet.homeBackground = phoneStackData.getHomeBackground();
            packet.lockTone = phoneStackData.getChatTone();
            packet.ringtone = phoneStackData.getRingTone();
            packet.setShowIRLTime = phoneStackData.getShowIRLTime();
            packet.useMilitaryTime = phoneStackData.getShowingMilitaryIRLTime();
            packet.toggleDebugMode = phoneStackData.getIsDebugModeEnabled();
            packet.resetName = false;
            packet.pin = phoneStackData.getPin();
            packet.playerID = phoneStackData.getUuid();

            PacketHandler.INSTANCE.sendToServer(packet);

            Minecraft.getMinecraft().displayGuiScreen(new GuiChangingTheme(phoneStack, hand));
            if(updatedTheme != phoneStackData.getIconTheme())
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiChangingTheme(phoneStack, hand));
            }
            else
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneNameSetup(phoneStack, hand));
            }
        }

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStepPersonalization(phoneStack, hand));
        }
    }
}
