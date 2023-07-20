package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.net.telecom.PhoneWallpaperPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;

public class GuiPhoneSetupStepPersonalization extends GuiPhoneBase
{
    LabelButton lockPrev;
    LabelButton lockNext;
    LabelButton homePrev;
    LabelButton homeNext;

    MinedroidButton next;
    MinedroidButton back;

    private int currentLock;
    private int currentHome;

    public GuiPhoneSetupStepPersonalization(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        try
        {
            currentLock = phoneStackData.getLockBackground();
            currentHome = phoneStackData.getHomeBackground();
        }
        catch(Exception ex)
        {
            currentLock = 1;
            currentHome = 1;
        }
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
        lockPrev = new LabelButton(1, INNER_X + 30, INNER_Y + 145, "<", 0xFFFFFF);
        lockNext = new LabelButton(2, INNER_X + 50, INNER_Y + 145, ">", 0xFFFFFF);

        homePrev = new LabelButton(3, INNER_X + 107, INNER_Y + 145, "<", 0xFFFFFF);
        homeNext = new LabelButton(4, INNER_X + 127, INNER_Y + 145, ">", 0xFFFFFF);

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        back = new MinedroidButton(5, INNER_X + 45, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.back").getFormattedText(), 0xFFFFFF);
        next = new MinedroidButton(6, INNER_X + 85, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.next").getFormattedText(), 0xFFFFFF);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(lockPrev)
                .add(lockNext)
                .add(homePrev)
                .add(homeNext)
                .add(next)
                .add(back)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        drawCenteredString(fontRenderer, new TextComponentTranslation("im.settings.personalization.wallpapersoobe").getFormattedText(), INNER_X + 80, INNER_Y + 20, 0xFFFFFF);

        fontRenderer.drawString(new TextComponentTranslation("im.settings.lockscreen").getFormattedText(), INNER_X + 10, INNER_Y + 45, 0xFFFFFF);
        int fontWidth = fontRenderer.getStringWidth(String.valueOf(currentLock));
        fontRenderer.drawString(String.valueOf(currentLock), INNER_X + 43 - (fontWidth / 2), INNER_Y + 145, 0xFFFFFF);

        fontRenderer.drawString(new TextComponentTranslation("im.settings.homescreen").getFormattedText(), INNER_X + 87, INNER_Y + 45, 0xFFFFFF);
        int fontWidth2 = fontRenderer.getStringWidth(String.valueOf(currentHome));
        fontRenderer.drawString(String.valueOf(currentHome), INNER_X + 120 - (fontWidth2 / 2), INNER_Y + 145, 0xFFFFFF);

        GlStateManager.color(1, 1, 1);

        // Lock Screen Preview
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/telecom/wallpapers/gui_phone_bg_" + currentLock + ".png"));
        drawScaledCustomSizeModalRect(INNER_X + 7, INNER_Y + 60, 0, 0, 323, 414, 69, 80, 324, 415);

        // Home Screen Preview
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/telecom/wallpapers/gui_phone_bg_" + currentHome + ".png"));
        drawScaledCustomSizeModalRect(INNER_X + 85, INNER_Y + 60, 0, 0, 323, 414, 69, 80, 324, 415);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if (button == lockPrev)
        {
            currentLock--;

            if (currentLock < 1)
            {
                currentLock = Reference.MAX_PHONE_BACKGROUNDS;
            }
        }

        if (button == lockNext)
        {
            currentLock++;

            if (currentLock > Reference.MAX_PHONE_BACKGROUNDS)
            {
                currentLock = 1;
            }
        }

        if (button == homeNext)
        {
            currentHome++;

            if (currentHome > Reference.MAX_PHONE_BACKGROUNDS)
            {
                currentHome = 1;
            }
        }

        if (button == homePrev)
        {
            currentHome--;

            if (currentHome < 1)
            {
                currentHome = Reference.MAX_PHONE_BACKGROUNDS;
            }
        }

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStart(phoneStack, hand));
        }

        if(button == next)
        {
            CustomizationPacket packet = new CustomizationPacket();
            packet.hand = hand.ordinal();
            packet.newName = phoneStack.getDisplayName();
            packet.guiClassName = GuiPhoneSetupStepPersonalization.class.getName();
            packet.nextGuiClassName = GuiPhoneSetupThemeStep.class.getName();

            if(phoneStackData.getIconTheme() == null)
            {
                packet.iconTheme = "plex";
            }
            else
            {
                packet.iconTheme = phoneStackData.getIconTheme();
            }

            packet.lockBackground = currentLock;
            packet.homeBackground = currentHome;
            packet.lockTone = phoneStackData.getChatTone();
            packet.ringtone = phoneStackData.getRingTone();
            packet.setShowIRLTime = phoneStackData.getShowIRLTime();
            packet.useMilitaryTime = phoneStackData.getShowingMilitaryIRLTime();
            packet.toggleDebugMode = phoneStackData.getIsDebugModeEnabled();
            packet.resetName = false;
            packet.pin = phoneStackData.getPin();
            packet.playerID = phoneStackData.getUuid();

            PacketHandler.INSTANCE.sendToServer(packet);
        }
    }
}
