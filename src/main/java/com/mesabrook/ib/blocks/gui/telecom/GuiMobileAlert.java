package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.SoundPlayerAppInfoPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;

public class GuiMobileAlert extends GuiPhoneBase
{
    MinedroidButton close;
    ImageButton alertIcon;

    public GuiMobileAlert(ItemStack phoneStack, EnumHand hand) {super(phoneStack, hand);}

    @Override
    protected String getInnerTextureFileName()
    {
        return "app_screen.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();
        SoundPlayerAppInfoPacket packet = new SoundPlayerAppInfoPacket();
        packet.pos = Minecraft.getMinecraft().player.getPosition();
        packet.modID = Reference.MODID;
        packet.soundName = "alert_tone";
        packet.volume = 1.0F;
        packet.pitch = 1.0F;
        packet.useDelay = false;
        PacketHandler.INSTANCE.sendToServer(packet);

        alertIcon = new ImageButton(0, INNER_X + 3, INNER_Y + 18, 13, 13, "icn_alert.png", 32, 32);
        close = new MinedroidButton(4, INNER_X + INNER_TEX_WIDTH - 43, INNER_Y + 17, 39, new TextComponentTranslation("im.alert.close").getFormattedText(), 0xFFFFFF);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(alertIcon)
                .add(close)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString(new TextComponentTranslation("im.alert.name").getFormattedText(), INNER_X + 20, INNER_Y + 20, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == close)
        {
            if(isPhoneUnlocked)
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiHome(phoneStack, hand));
            }
            else
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiLockScreen(phoneStack, hand));
            }
        }
    }
}
