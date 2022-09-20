package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;

public class GuiDebugMenu extends GuiPhoneBase
{
    LabelButton back;
    LabelButton weaLabel;
    LabelButton oobeLabel;

    ImageButton weaIcon;
    ImageButton oobeIcon;
    public GuiDebugMenu(ItemStack phoneStack, EnumHand hand) {
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

        weaLabel = new LabelButton(1, INNER_X + 31, INNER_Y + 53, new TextComponentTranslation("im.settings.debug.wea").getFormattedText(), 0xFFFFFF);
        oobeLabel = new LabelButton(2, INNER_X + 31, INNER_Y + 83, new TextComponentTranslation("im.settings.debug.oobe").getFormattedText(), 0xFFFFFF);

        weaIcon = new ImageButton(100, INNER_X + 0, INNER_Y + 40, 28, 28, "btn_debug.png", 32, 32);
        oobeIcon = new ImageButton(101, INNER_X + 0, INNER_Y + 70, 28, 28, "btn_debug.png", 32, 32);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(back)
                .add(weaLabel)
                .add(oobeLabel)
                .add(weaIcon)
                .add(oobeIcon)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString(new TextComponentTranslation("im.settings.debug").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettings(phoneStack, hand));
        }

        if(button == weaIcon || button == weaLabel)
        {
            ClientSoundPacket packet = new ClientSoundPacket();
            packet.pos = Minecraft.getMinecraft().player.getPosition();
            packet.modID = Reference.MODID;
            packet.soundName = "alert_tone";
            packet.volume = 1.0F;
            packet.pitch = 1.0F;
            packet.useDelay = false;
            PacketHandler.INSTANCE.sendToServer(packet);
            
            GuiMobileAlert.labelsByNumber.put(phoneStackData.getPhoneNumber(), "Test Alert");
        	GuiMobileAlert.textByNumber.put(phoneStackData.getPhoneNumber(), "If this were a real emergency, more information would follow here.");
            Minecraft.getMinecraft().displayGuiScreen(new GuiMobileAlert(phoneStack, hand));
        }

        if(button == oobeIcon || button == oobeLabel)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStart(phoneStack, hand));
        }
    }
}
