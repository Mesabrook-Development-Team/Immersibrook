package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;

public class GuiSetupWarning extends GuiPhoneBase
{
    MinedroidButton back;
    MinedroidButton skip;
    ImageButton icon;

    public GuiSetupWarning(ItemStack phoneStack, EnumHand hand)
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
        icon = new ImageButton(1, INNER_X + 72, INNER_Y + 42, 16, 16, "icn_alert_yellow.png", 32, 32);

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        back = new MinedroidButton(0, INNER_X + 45, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.back").getFormattedText(), 0xFFFFFF);
        skip = new MinedroidButton(1, INNER_X + 85, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.next").getFormattedText(), 0xFFFFFF);

        buttonList.addAll(ImmutableList.of(icon, back, skip));
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        drawCenteredString(fontRenderer, TextFormatting.BOLD + "WARNING", INNER_X + 80, INNER_Y + 65, 0xFFFFFF);

        drawCenteredString(fontRenderer, "It is recommended that", INNER_X + 80, INNER_Y + 85, 0xFFFFFF);
        drawCenteredString(fontRenderer, "you secure your phone.", INNER_X + 80, INNER_Y + 95, 0xFFFFFF);
        drawCenteredString(fontRenderer, "Securing your phone", INNER_X + 80, INNER_Y + 115, 0xFFFFFF);
        drawCenteredString(fontRenderer, "will keep others from", INNER_X + 80, INNER_Y + 125, 0xFFFFFF);
        drawCenteredString(fontRenderer, "using it if stolen.", INNER_X + 80, INNER_Y + 135, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStepSecurity(phoneStack, hand));
        }
        if(button == skip)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupComplete(phoneStack, hand));
        }
    }
}
