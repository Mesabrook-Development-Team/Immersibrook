package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.net.telecom.PhoneNamePacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;

public class GuiSettingsPhoneName extends GuiPhoneBase
{
    private String currentPhoneName;
    LabelButton back;

    MinedroidButton apply;
    MinedroidButton reset;

    GuiTextField nameTxtBox;

    public GuiSettingsPhoneName(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        currentPhoneName = phoneStack.getDisplayName();
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

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        reset = new MinedroidButton(10, INNER_X + 45, lowerControlsY - 10, 32, new TextComponentTranslation("im.musicapp.buttonreset").getFormattedText(), 0xFFFFFF);
        apply = new MinedroidButton(11, INNER_X + 85, lowerControlsY - 10, 32, new TextComponentTranslation("im.settings.apply").getFormattedText(), 0xFFFFFF);

        nameTxtBox = new GuiTextField(100, fontRenderer, INNER_X + 10, INNER_Y + 100, INNER_TEX_WIDTH - 20, 10);
        nameTxtBox.setMaxStringLength(35);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(reset)
                .add(apply)
                .add(back)
                .build());

        nameTxtBox.setText(currentPhoneName);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString(new TextComponentTranslation("im.settings.personalization.phonename").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);

        fontRenderer.drawString(new TextComponentTranslation("im.settings.personalization.setphonename").getFormattedText(), INNER_X + 10, INNER_Y + 85, 0xFFFFFF);

        nameTxtBox.drawTextBox();
        GlStateManager.color(1, 1, 1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        nameTxtBox.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        nameTxtBox.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        EntityPlayer player = Minecraft.getMinecraft().player;

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsPersonalization(phoneStack, hand));
        }

        if(button == apply)
        {
            if(!nameTxtBox.getText().isEmpty())
            {
                CustomizationPacket packet = new CustomizationPacket();
                packet.hand = hand.ordinal();
                packet.newName = nameTxtBox.getText();
                packet.guiClassName = GuiSettingsPhoneName.class.getName();
                packet.iconTheme = phoneStackData.getIconTheme();
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
                nameTxtBox.setText(packet.newName);
            }
            else
            {
                // TODO: Invoke a Toast Here.
            }
        }

        if(button == reset)
        {
            CustomizationPacket packet = new CustomizationPacket();
            packet.hand = hand.ordinal();
            packet.newName = nameTxtBox.getText();
            packet.guiClassName = GuiSettingsPhoneName.class.getName();
            packet.iconTheme = "plex";
            packet.lockBackground = phoneStackData.getLockBackground();
            packet.homeBackground = phoneStackData.getHomeBackground();
            packet.lockTone = phoneStackData.getChatTone();
            packet.ringtone = phoneStackData.getRingTone();
            packet.setShowIRLTime = phoneStackData.getShowIRLTime();
            packet.useMilitaryTime = phoneStackData.getShowingMilitaryIRLTime();
            packet.toggleDebugMode = phoneStackData.getIsDebugModeEnabled();

            PacketHandler.INSTANCE.sendToServer(packet);
            nameTxtBox.setText(phoneStack.getDisplayName());
        }
    }
}
