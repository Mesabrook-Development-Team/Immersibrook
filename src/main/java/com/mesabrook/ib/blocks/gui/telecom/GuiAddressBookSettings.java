package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.telecom.SetSkinFetcherPacket;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.io.IOException;

public class GuiAddressBookSettings extends GuiPhoneBase
{
    GuiCheckBox mcheads;
    GuiCheckBox crafatar;
    MinedroidButton reset;
    MinedroidButton confirm;
    LabelButton back;

    public GuiAddressBookSettings(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);
    }

    @Override
    protected String getInnerTextureFileName()
    {
        return phoneStackData.getIconTheme() + "/app_screen_contacts.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();

        back = new LabelButton(0, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);

        mcheads = new GuiCheckBox(1, INNER_X + 10, INNER_Y + 52, "MC-Heads.net", phoneStackData.getSkinFetchingEngine() == EnumSkinFetchingEngines.MCHeads);
        crafatar = new GuiCheckBox(2, INNER_X + 10, INNER_Y + 69, "Crafatar", phoneStackData.getSkinFetchingEngine() == EnumSkinFetchingEngines.Crafatar);

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;

        reset = new MinedroidButton(3, INNER_X + 45, lowerControlsY - 10, 32, new TextComponentTranslation("im.musicapp.buttonreset").getFormattedText(), 0x139111);
        confirm = new MinedroidButton(4, INNER_X + 85, lowerControlsY - 10, 32, new TextComponentTranslation("im.settings.apply").getFormattedText(), 0x139111);

        buttonList.addAll(ImmutableList.of(back, reset, mcheads, crafatar, back, confirm));
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);
        fontRenderer.drawString("Address Book Settings", INNER_X + 15, INNER_Y + 20, 0xFFFFFF);
        fontRenderer.drawString("Player Head Renderer", INNER_X + 10, INNER_Y + 40, 0x0b520a);

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiAddressBook(phoneStack, hand));
        }

        if(button == crafatar)
        {
            mcheads.setIsChecked(false);

            if(!crafatar.isChecked())
            {
                crafatar.setIsChecked(true);
            }
        }
        if(button == mcheads)
        {
            crafatar.setIsChecked(false);
            if(!mcheads.isChecked())
            {
                mcheads.setIsChecked(true);
            }
        }

        if(button == confirm)
        {
            SetSkinFetcherPacket packet = new SetSkinFetcherPacket();
            packet.guiClassName = GuiAddressBookSettings.class.getName();
            packet.nextGuiClassName = GuiAddressBook.class.getName();
            packet.hand = hand.ordinal();

            if(mcheads.isChecked())
            {
                packet.engine = EnumSkinFetchingEngines.MCHeads;
            }
            else if(crafatar.isChecked())
            {
                packet.engine = EnumSkinFetchingEngines.Crafatar;
            }
            else
            {
                packet.engine = EnumSkinFetchingEngines.MCHeads;
            }

            PacketHandler.INSTANCE.sendToServer(packet);
        }
    }
}
