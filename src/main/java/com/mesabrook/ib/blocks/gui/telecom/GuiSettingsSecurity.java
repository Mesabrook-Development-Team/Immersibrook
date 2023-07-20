package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.util.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;

public class GuiSettingsSecurity extends GuiPhoneBase
{
    LabelButton back;
    LabelButton lockScreen;
    LabelButton factoryReset;

    ImageButton lockScreenIcon;
    ImageButton factoryResetIcon;

    public GuiSettingsSecurity(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return phoneStackData.getIconTheme() + "/app_screen.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();

        back = new LabelButton(0, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);
        lockScreen = new LabelButton(1, INNER_X + 31, INNER_Y + 53, new TextComponentTranslation("im.settings.securitytitle1").getFormattedText(), 0xFFFFFF);
        factoryReset = new LabelButton(2, INNER_X + 31, INNER_Y + 83, new TextComponentTranslation("im.settings.factoryreset").getFormattedText(), 0xFF0000);

        lockScreenIcon = new ImageButton(5, INNER_X + 0, INNER_Y + 40, 28, 28, "btn_lock.png", 32, 32);
        factoryResetIcon = new ImageButton(6, INNER_X + 0, INNER_Y + 70, 28, 28, "btn_factory_reset.png", 32, 32);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(back)
                .add(lockScreen)
                .add(factoryReset)
                .add(lockScreenIcon)
                .add(factoryResetIcon)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString(new TextComponentTranslation("im.settings.securitytitle").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettings(phoneStack, hand));
        }

        if(button == lockScreen || button == lockScreenIcon)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsLockScreen(phoneStack, hand));
        }

        if(button == factoryReset || button == factoryResetIcon)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiFactoryResetConfirmation(phoneStack, hand));
        }
    }
}
