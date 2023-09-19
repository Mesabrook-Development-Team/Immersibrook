package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData.SecurityStrategies;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;

public class GuiIconSet extends GuiPhoneBase
{
    GuiCheckBox plex;
    GuiCheckBox aero_bubbles;
    GuiCheckBox luna;

    MinedroidButton reset;
    MinedroidButton apply;
    LabelButton back;

    String currentTheme;
    String updatedTheme;

    public GuiIconSet(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName() {
        return "system/app_screen.png";
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

        if(phoneStackData.getIsDebugModeEnabled())
        {
            fontRenderer.drawString(new TextComponentString(TextFormatting.ITALIC + phoneStackData.getIconTheme()).getFormattedText(), INNER_X + 85, INNER_Y + 115, 0xFFFFFF);
        }
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
            currentTheme = updatedTheme;

            CustomizationPacket packet = new CustomizationPacket();
            packet.hand = hand.ordinal();
            packet.newName = phoneStack.getDisplayName();
            packet.guiClassName = GuiIconSet.class.getName();
            packet.iconTheme = updatedTheme;
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
            Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.settings.saved").getFormattedText(), 0xFFFFFF));
        }

        if(button == reset)
        {
            CustomizationPacket packet = new CustomizationPacket();
            packet.hand = hand.ordinal();
            packet.newName = phoneStack.getDisplayName();
            packet.guiClassName = GuiIconSet.class.getName();
            packet.iconTheme = "plex";
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
            Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.settings.reset").getFormattedText(), 0xFFFFFF));
        }

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsPersonalization(phoneStack, hand));
        }
    }
}
