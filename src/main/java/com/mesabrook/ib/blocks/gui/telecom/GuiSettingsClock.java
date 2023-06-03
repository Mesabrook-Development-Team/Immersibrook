package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.*;
import com.mesabrook.ib.net.telecom.*;
import com.mesabrook.ib.util.*;
import com.mesabrook.ib.util.handlers.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.client.config.*;

import java.io.*;

public class GuiSettingsClock extends GuiPhoneBase
{
    LabelButton back;
    GuiCheckBox toggle;
    GuiCheckBox toggleMilitaryTime;
    MinedroidButton apply;
    MinedroidButton reset;

    public GuiSettingsClock(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName() {
        return "app_screen.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();

        back = new LabelButton(0, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);

        toggle = new GuiCheckBox(1, INNER_X + 9,  INNER_Y + 140, "Real Time on Lock Screen", phoneStackData.getShowIRLTime());
        toggleMilitaryTime = new GuiCheckBox(2, INNER_X + 9,  INNER_Y + 153, "Use 24hr Time Format", phoneStackData.getShowIRLTime());

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 25;
        reset = new MinedroidButton(3, INNER_X + 45, lowerControlsY - 10, 32, new TextComponentTranslation("im.musicapp.buttonreset").getFormattedText(), 0xFFFFFF);
        apply = new MinedroidButton(4, INNER_X + 85, lowerControlsY - 10, 32, new TextComponentTranslation("im.settings.apply").getFormattedText(), 0xFFFFFF);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(back)
                .add(toggle)
                .add(toggleMilitaryTime)
                .add(reset)
                .add(apply)
                .build());
        toggle.setIsChecked(phoneStackData.getShowIRLTime());
        toggleMilitaryTime.enabled = toggle.isChecked();
        toggleMilitaryTime.setIsChecked(phoneStackData.getShowingMilitaryIRLTime());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString(new TextComponentTranslation("im.settings.personalization.clock").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/telecom/clock_example.png"));
        drawScaledCustomSizeModalRect(INNER_X + 15, INNER_Y + 40, 0, 0, 516, 414, 132, 80, 512, 512);

        int stringWidth = fontRenderer.getStringWidth(getTime());
        int stringWidth2 = fontRenderer.getStringWidth(getIRLTime());

        GlStateManager.scale(uBigFont, uBigFont, uBigFont);
        fontRenderer.drawString(getTime(), scale(INNER_X + INNER_TEX_WIDTH / 2, dBigFont) - stringWidth / 2, scale(INNER_Y + 69, dBigFont), 0xFFFFFF, true);
        GlStateManager.scale(dBigFont, dBigFont, dBigFont);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        if(toggle.isChecked())
        {
            fontRenderer.drawString(getIRLTime(), INNER_X + INNER_TEX_WIDTH / 2 - stringWidth2 / 2, INNER_Y + INNER_TEX_HEIGHT - 134, 0xFFFFFF, true);
        }

        toggleMilitaryTime.enabled = toggle.isChecked();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsPersonalization(phoneStack, hand));
        }

        if(button == apply)
        {
            CustomizationPacket packet = new CustomizationPacket();
            packet.hand = hand.ordinal();
            packet.newName = phoneStack.getDisplayName();
            packet.guiClassName = GuiSettingsClock.class.getName();
            packet.iconTheme = phoneStackData.getIconTheme();
            packet.lockBackground = phoneStackData.getLockBackground();
            packet.homeBackground = phoneStackData.getHomeBackground();
            packet.lockTone = phoneStackData.getChatTone();
            packet.ringtone = phoneStackData.getRingTone();
            packet.setShowIRLTime = toggle.isChecked();
            packet.useMilitaryTime = toggleMilitaryTime.isChecked();
            packet.toggleDebugMode = phoneStackData.getIsDebugModeEnabled();
            packet.resetName = false;
            packet.pin = phoneStackData.getPin();
            packet.playerID = phoneStackData.getUuid();

            Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.settings.saved").getFormattedText(), 0xFFFFFF));
        }

        if(button == reset)
        {
            toggle.setIsChecked(phoneStackData.getShowIRLTime());
            toggleMilitaryTime.setIsChecked(phoneStackData.getShowingMilitaryIRLTime());
        }
    }
}
