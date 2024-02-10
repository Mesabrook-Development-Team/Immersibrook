package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.blocks.gui.PINTextField;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.net.telecom.SecurityStrategySelectedPacket;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;
import java.util.UUID;

public class GuiPhoneSetupStepSecurity extends GuiPhoneBase
{
    GuiCheckBox pin;
    GuiCheckBox playerID;
    MinedroidButton back;
    MinedroidButton next;
    PINTextField pinValue;
    GuiTextField uuidValue;

    public GuiPhoneSetupStepSecurity(ItemStack phoneStack, EnumHand hand)
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
        boolean usePin = phoneStackData.getSecurityStrategy() == ItemPhone.NBTData.SecurityStrategies.PIN;
        boolean useUUID = phoneStackData.getSecurityStrategy() == ItemPhone.NBTData.SecurityStrategies.UUID;

        pin = new GuiCheckBox(0, INNER_X + 10, INNER_Y + 52, new TextComponentTranslation("im.settings.pin").getFormattedText(), usePin);
        playerID = new GuiCheckBox(1, INNER_X + 10, INNER_Y + 69, new TextComponentTranslation("im.settings.uuid").getFormattedText(), useUUID);

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        back = new MinedroidButton(2, INNER_X + 45, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.back").getFormattedText(), 0xFFFFFF);
        next = new MinedroidButton(3, INNER_X + 85, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.next").getFormattedText(), 0xFFFFFF);

        pinValue = new PINTextField(7, fontRenderer, pin.x + pin.width + 4, pin.y-4, INNER_X + INNER_TEX_WIDTH - (pin.x + pin.width) - 7, 20);
        pinValue.setMaskedText(String.valueOf(phoneStackData.getPin()));
        pinValue.setVisible(false);
        pinValue.setMaxStringLength(8);
        uuidValue = new GuiTextField(8, fontRenderer, playerID.x + playerID.width + 4, playerID.y-4, INNER_X + INNER_TEX_WIDTH - (playerID.x + playerID.width) - 7, 20);
        uuidValue.setVisible(false);
        uuidValue.setMaxStringLength(36);
        if (phoneStackData.getUuid() != null)
        {
            uuidValue.setText(phoneStackData.getUuid().toString());
        }

        buttonList.addAll(ImmutableList.of(pin, playerID, next, back));
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        drawCenteredString(fontRenderer, new TextComponentTranslation("im.settings.securitytitle1").getFormattedText(), INNER_X + 80, INNER_Y + 20, 0xFFFFFF);
        fontRenderer.drawString(new TextComponentTranslation("im.settings.strategy").getFormattedText(), INNER_X + 3, INNER_Y + 36, 0xb5b5b5);

        pinValue.drawTextBox();
        uuidValue.drawTextBox();

        GlStateManager.color(1, 1, 1);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if (button == pin)
        {
            GuiCheckBox checkBox = (GuiCheckBox)button;
            playerID.setIsChecked(false);
            uuidValue.setVisible(false);
            pinValue.setVisible(checkBox.isChecked());
            pinValue.setMaskedText("");
        }
        if (button == playerID)
        {
            GuiCheckBox checkBox = (GuiCheckBox)button;
            pin.setIsChecked(false);
            pinValue.setVisible(false);
            pinValue.setText("");
            uuidValue.setVisible(checkBox.isChecked());

            if (uuidValue.getText().isEmpty())
            {
                uuidValue.setText(Minecraft.getMinecraft().player.getUniqueID().toString());
            }
        }

        if (button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupStepPersonalization(phoneStack, hand));
        }
        if(button == next)
        {
            if(!pin.isChecked() && !playerID.isChecked())
            {
                Minecraft.getMinecraft().displayGuiScreen(new GuiSetupWarning(phoneStack, hand));
            }
            else
            {
                // New Packet
                SecurityStrategySelectedPacket packet = new SecurityStrategySelectedPacket();
                packet.hand = hand.ordinal();
                packet.guiScreenClassForRefresh = GuiPhoneSetupStepSecurity.class.getName();
                packet.nextGuiScreenClassAfterRefresh = GuiPhoneSetupComplete.class.getName();

                if(pin.isChecked())
                {
                    if (pinValue.getMaskedText().isEmpty())
                    {
                        Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.settings.pinerror").getFormattedText(), 0xFF0000));
                        return;
                    }
                    packet.pin = Integer.parseInt(pinValue.getMaskedText());
                }
                if(playerID.isChecked())
                {
                    if (uuidValue.getText().isEmpty())
                    {
                        Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast("Player UUID Required!", 0xFF0000));
                        Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.settings.uuiderror").getFormattedText(), 0xFF0000));
                        return;
                    }

                    UUID parsedPlayerID;
                    try
                    {
                        parsedPlayerID = UUID.fromString(uuidValue.getText());
                    }
                    catch(IllegalArgumentException ex)
                    {
                        Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(2, 300, 2, new TextComponentTranslation("im.settings.tryagain").getFormattedText(), 0xFF0000));
                        return;
                    }
                    packet.playerID = parsedPlayerID;
                }

                PacketHandler.INSTANCE.sendToServer(packet);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        pinValue.mouseClicked(mouseX, mouseY, mouseButton);
        uuidValue.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        pinValue.textboxKeyTyped(typedChar, keyCode);
        uuidValue.textboxKeyTyped(typedChar, keyCode);
    }
}
