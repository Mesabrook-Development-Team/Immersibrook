package com.mesabrook.ib.blocks.gui.telecom;

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

public class GuiPhoneNameSetup extends GuiPhoneBase
{
    private String currentPhoneName;
    MinedroidButton next;
    MinedroidButton back;
    GuiTextField phoneNameTxtField;

    public GuiPhoneNameSetup(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
        currentPhoneName = phoneStack.getDisplayName();
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

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        back = new MinedroidButton(0, INNER_X + 45, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.back").getFormattedText(), 0xFFFFFF);
        next = new MinedroidButton(1, INNER_X + 85, lowerControlsY - 3, 35, new TextComponentTranslation("im.settings.next").getFormattedText(), 0xFFFFFF);

        phoneNameTxtField = new GuiTextField(100, fontRenderer, INNER_X + 10, INNER_Y + 70, INNER_TEX_WIDTH - 20, 10);
        phoneNameTxtField.setMaxStringLength(35);

        if(phoneStack.hasDisplayName())
        {
            phoneNameTxtField.setText(phoneStack.getDisplayName());
        }
        else
        {
            EntityPlayer player = Minecraft.getMinecraft().player;
            phoneNameTxtField.setText(player.getName() + "'s Minedroid");
        }

        buttonList.add(back);
        buttonList.add(next);
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        GlStateManager.color(1, 1, 1);
        drawCenteredString(fontRenderer, new TextComponentTranslation("im.settings.personalization.phonenamesetup").getFormattedText(), INNER_X + 80, INNER_Y + 20, 0xFFFFFF);
        fontRenderer.drawString(new TextComponentTranslation("im.settings.personalization.setphonename").getFormattedText(), INNER_X + 10, INNER_Y + 50, 0xFFFFFF);

        fontRenderer.drawSplitString(new TextComponentTranslation("im.settings.personalization.nameinfo1").getFormattedText(), INNER_X + 10, INNER_Y + 100, INNER_TEX_WIDTH - 12, 0xFFFFFF);
        phoneNameTxtField.drawTextBox();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        phoneNameTxtField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        phoneNameTxtField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPhoneSetupThemeStep(phoneStack, hand));
        }

        if(button == next)
        {
            if(!phoneNameTxtField.getText().isEmpty())
            {
                CustomizationPacket packet = new CustomizationPacket();
                packet.hand = hand.ordinal();
                packet.newName = phoneNameTxtField.getText();
                packet.guiClassName = GuiPhoneNameSetup.class.getName();
                packet.nextGuiClassName = GuiPhoneSetupStepSecurity.class.getName();
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
            }
            else
            {
                // TODO: Invoke a Toast Here.
            }
        }
    }
}
