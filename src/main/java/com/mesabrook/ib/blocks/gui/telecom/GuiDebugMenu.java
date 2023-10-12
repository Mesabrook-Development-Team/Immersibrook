package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.ImageButton;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.telecom.OOBEStatusPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.*;

import java.io.*;

public class GuiDebugMenu extends GuiPhoneBase
{
    LabelButton back;
    LabelButton weaLabel;
    LabelButton oobeLabel;
    LabelButton crashLabel;
    LabelButton lowBatTest;

    ImageButton weaIcon;
    ImageButton oobeIcon;
    ImageButton crashIcon;
    public GuiDebugMenu(ItemStack phoneStack, EnumHand hand) {
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

        back = new LabelButton(0, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);

        weaLabel = new LabelButton(1, INNER_X + 31, INNER_Y + 53, new TextComponentTranslation("im.settings.debug.wea").getFormattedText(), 0xFFFFFF);
        oobeLabel = new LabelButton(2, INNER_X + 31, INNER_Y + 83, new TextComponentTranslation("im.settings.debug.oobe").getFormattedText(), 0xFFFFFF);
        crashLabel = new LabelButton(3, INNER_X + 31, INNER_Y + 113, new TextComponentString("Crash Device").getFormattedText(), 0xFFFFFF);
        lowBatTest = new LabelButton(4, INNER_X + 31, INNER_Y + 133, new TextComponentString("Low Battery Screen").getFormattedText(), 0xFFFFFF);

        weaIcon = new ImageButton(100, INNER_X + 0, INNER_Y + 40, 28, 28, phoneStackData.getIconTheme() + "/btn_debug.png", 32, 32);
        oobeIcon = new ImageButton(101, INNER_X + 0, INNER_Y + 70, 28, 28, phoneStackData.getIconTheme() + "/btn_debug.png", 32, 32);
        crashIcon = new ImageButton(102, INNER_X + 0, INNER_Y + 100, 28, 28, phoneStackData.getIconTheme() + "/btn_debug.png", 32, 32);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(back)
                .add(weaLabel)
                .add(oobeLabel)
                .add(crashLabel)
                .add(weaIcon)
                .add(oobeIcon)
                .add(crashIcon)
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

            GuiNewEmergencyAlert.labelsByNumber.put(phoneStackData.getPhoneNumber(), "National Periodic Test");
            GuiNewEmergencyAlert.textByNumber.put(phoneStackData.getPhoneNumber(), "THIS IS A TEST OF THE NATIONAL WIRELESS EMERGENCY ALERT SYSTEM, THIS MESSAGE ORIGINATES FROM THE CENTERS OF MESABROOK BELL IN COOPERATION WITH THE DEPARTMENT OF PUBLIC SAFETY. IF THIS WERE AN ACTUAL EMERGENCY SUCH AS A TORNADO WARNING OR CIVIL DANGER WARNING, OFFICIAL INFORMATION OR INSTRUCTIONS WOULD BE BROADCAST TO ALL SMARTPHONES IN THE NATION. THIS IS ONLY A TEST, NO ACTION IS REQUIRED. THIS TEST OF THE NATIONAL WIRELESS EMERGENCY ALERT SYSTEM IS NOW CONCLUDED.");
            Minecraft.getMinecraft().displayGuiScreen(new GuiNewEmergencyAlert(phoneStack, hand));
        }

        if(button == oobeIcon || button == oobeLabel)
        {
            OOBEStatusPacket packet = new OOBEStatusPacket();
            packet.hand = hand.ordinal();
            packet.guiClassName = GuiDebugMenu.class.getName();
            packet.nextGuiClassName = GuiMSACBootScreen.class.getName();
            packet.needToDoOOBE = true;

            PacketHandler.INSTANCE.sendToServer(packet);

        }

        if(button == crashIcon || button == crashLabel)
        {
            try
            {
                throw new Exception("Manually Initiated Crash");
            }
            catch (Exception ex)
            {
                GuiPhoneCrashed crashGui = new GuiPhoneCrashed(phoneStack, hand);

                StringWriter writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter( writer );
                ex.printStackTrace(printWriter);
                printWriter.flush();

                crashGui.setErrorTitle(ex.toString());
                crashGui.setErrorStackTrace(writer.toString());

                Minecraft.getMinecraft().displayGuiScreen(crashGui);
            }
        }

        if(button == lowBatTest)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiLowBatWarning(phoneStack, hand));
        }
    }
}
