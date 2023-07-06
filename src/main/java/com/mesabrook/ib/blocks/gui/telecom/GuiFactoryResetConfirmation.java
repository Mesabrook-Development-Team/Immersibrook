package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;

public class GuiFactoryResetConfirmation extends GuiPhoneBase
{
    LabelButton back;
    MinedroidButton proceed;
    GuiCheckBox confirmation;
    ImageButton icon;

    public GuiFactoryResetConfirmation(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return "system/app_splash_red.png";
    }

    @Override
    protected boolean renderControlBar() {
        return false;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        icon = new ImageButton(1, INNER_X + 72, INNER_Y + 22, 16, 16, "icn_alert_yellow.png", 32, 32);

        confirmation = new GuiCheckBox(2, INNER_X + 30, INNER_Y + 155, "I wish to continue", false);

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        proceed = new MinedroidButton(3, INNER_X + 45, lowerControlsY - 3, 75, "Reset Device", 0xFF0000);
        back = new LabelButton(4, INNER_X + 3, INNER_Y + 20, "<", 0xFF0000);

        buttonList.addAll(ImmutableList.of(icon, back, confirmation, proceed));
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        drawCenteredString(fontRenderer, TextFormatting.BOLD + "DANGER", INNER_X + 80, INNER_Y + 45, 0xFFFFFF);
        fontRenderer.drawSplitString("Factory resetting your phone will erase all of your personal data and reset all settings. This cannot be undone. \n\nAre you sure you want to proceed?", INNER_X + 14, INNER_Y + 65, INNER_TEX_WIDTH - 12, 0xFFFFFF);

        proceed.enabled = confirmation.isChecked();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == proceed)
        {
            if(confirmation.isChecked())
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiFactoryResetProgress(phoneStack, hand));
            }
        }

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsSecurity(phoneStack, hand));
        }
    }
}
