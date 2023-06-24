package com.mesabrook.ib.blocks.gui.telecom;

import com.google.common.collect.ImmutableList;
import com.mesabrook.ib.net.telecom.CustomizationPacket;
import com.mesabrook.ib.net.telecom.PhoneRingtonesPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.IOException;

public class GuiSettingsRingtones extends GuiPhoneBase
{
    LabelButton back;
    LabelButton ringPrev;
    LabelButton ringNext;
    LabelButton msgPrev;
    LabelButton msgNext;

    ImageButton ringtone;
    ImageButton msgSound;

    MinedroidButton apply;
    MinedroidButton reset;

    private int currentRingtone;
    private int currentNotifSound;

    private PositionedSoundRecord ringtonePreview = null;
    private PositionedSoundRecord chatPreview = null;

    public GuiSettingsRingtones(ItemStack phoneStack, EnumHand hand)
    {
        super(phoneStack, hand);

        currentRingtone = phoneStackData.getRingTone();
        currentNotifSound = phoneStackData.getChatTone();
    }

    @Override
    protected String getInnerTextureFileName() {
        return "system/app_screen.png";
    }

    @Override
    public void initGui()
    {
        super.initGui();
        back = new LabelButton(0, INNER_X + 3, INNER_Y + 20, "<", 0xFFFFFF);

        ringtone = new ImageButton(1, INNER_X + 0, INNER_Y + 40, 32, 32, "icn_call.png", 64, 64);
        msgSound = new ImageButton(2, INNER_X + 0, INNER_Y + 80, 32, 32, "icn_msg.png", 64, 64);

        int lowerControlsY = INNER_Y + INNER_TEX_HEIGHT - INNER_TEX_Y_OFFSET - 32;
        reset = new MinedroidButton(10, INNER_X + 45, lowerControlsY - 10, 32, new TextComponentTranslation("im.musicapp.buttonreset").getFormattedText(), 0xFFFFFF);
        apply = new MinedroidButton(11, INNER_X + 85, lowerControlsY - 10, 32, new TextComponentTranslation("im.settings.apply").getFormattedText(), 0xFFFFFF);

        ringPrev = new LabelButton(12, INNER_X + 125, INNER_Y + 54, "<", 0xFFFFFF);
        ringNext = new LabelButton(13, INNER_X + 150, INNER_Y + 54, ">", 0xFFFFFF);

        msgPrev = new LabelButton(14, INNER_X + 125, INNER_Y + 93, "<", 0xFFFFFF);
        msgNext = new LabelButton(15, INNER_X + 150, INNER_Y + 93, ">", 0xFFFFFF);

        buttonList.addAll(ImmutableList.<GuiButton>builder()
                .add(back)
                .add(ringtone)
                .add(msgSound)
                .add(ringNext)
                .add(ringPrev)
                .add(msgNext)
                .add(msgPrev)
                .add(apply)
                .add(reset)
                .build());
    }

    @Override
    protected void doDraw(int mouseX, int mouseY, float partialticks)
    {
        super.doDraw(mouseX, mouseY, partialticks);

        fontRenderer.drawString(new TextComponentTranslation("im.settings.personalization.sounds").getFormattedText(), INNER_X + 15, INNER_Y + 20, 0xFFFFFF);

        fontRenderer.drawString(new TextComponentTranslation("im.settings.ringtone").getFormattedText(), INNER_X + 30, INNER_Y + 53, 0xFFFFFF);
        int fontWidth = fontRenderer.getStringWidth(String.valueOf(currentRingtone));
        fontRenderer.drawString(String.valueOf(currentRingtone), INNER_X + 140 - (fontWidth / 2), INNER_Y + 54, 0xFFFFFF);

        fontRenderer.drawString(new TextComponentTranslation("im.settings.chat").getFormattedText(), INNER_X + 30, INNER_Y + 93, 0xFFFFFF);
        int fontWidth2 = fontRenderer.getStringWidth(String.valueOf(currentNotifSound));
        fontRenderer.drawString(String.valueOf(currentNotifSound), INNER_X + 140 - (fontWidth2 / 2), INNER_Y + 93, 0xFFFFFF);

        GlStateManager.color(1, 1, 1);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if(button == back)
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSettingsPersonalization(phoneStack, hand));
        }

        if(button == ringPrev)
        {
            currentRingtone--;
            if(currentRingtone < 1)
            {
                currentRingtone = Reference.MAX_RINGTONES;
            }
        }
        if(button == ringNext)
        {
            currentRingtone++;
            if(currentRingtone > Reference.MAX_RINGTONES)
            {
                currentRingtone = 1;
            }
        }

        if(button == msgPrev)
        {
            currentNotifSound--;
            if(currentNotifSound < 1)
            {
                currentNotifSound = Reference.MAX_CHAT_NOTIFICATIONS;
            }
        }
        if(button == msgNext)
        {
            currentNotifSound++;
            if(currentNotifSound > Reference.MAX_CHAT_NOTIFICATIONS)
            {
                currentNotifSound = 1;
            }
        }

        SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        handler.stopSound(ringtonePreview);
        handler.stopSound(chatPreview);

        if(button == ringNext || button == ringPrev)
        {
            ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, "ring_" + Integer.toString(currentRingtone));
            IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
            SoundEvent soundEvent = soundRegistry.getValue(soundLocation);

            ringtonePreview = PositionedSoundRecord.getMasterRecord(soundEvent, 1F);
            handler.playSound(ringtonePreview);
        }

        if(button == msgNext || button == msgPrev)
        {
            ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, "ding_" + Integer.toString(currentNotifSound));
            IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
            SoundEvent soundEvent = soundRegistry.getValue(soundLocation);

            chatPreview = PositionedSoundRecord.getMasterRecord(soundEvent, 1F);
            handler.playSound(chatPreview);
        }

        if(button == ringtone)
        {
            ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, "ring_" + Integer.toString(phoneStackData.getRingTone()));
            IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
            SoundEvent soundEvent = soundRegistry.getValue(soundLocation);

            ringtonePreview = PositionedSoundRecord.getMasterRecord(soundEvent, 1F);
            handler.playSound(ringtonePreview);
        }
        if(button == msgSound)
        {
            ResourceLocation soundLocation = new ResourceLocation(Reference.MODID, "ding_" + Integer.toString(currentNotifSound));
            IForgeRegistry<SoundEvent> soundRegistry = GameRegistry.findRegistry(SoundEvent.class);
            SoundEvent soundEvent = soundRegistry.getValue(soundLocation);

            chatPreview = PositionedSoundRecord.getMasterRecord(soundEvent, 1F);
            handler.playSound(chatPreview);
        }

        if(button == apply)
        {
            CustomizationPacket packet = new CustomizationPacket();
            packet.hand = hand.ordinal();
            packet.newName = phoneStack.getDisplayName();
            packet.guiClassName = GuiSettingsRingtones.class.getName();
            packet.iconTheme = phoneStackData.getIconTheme();
            packet.lockBackground = phoneStackData.getLockBackground();
            packet.homeBackground = phoneStackData.getHomeBackground();
            packet.lockTone = currentNotifSound;
            packet.ringtone = currentRingtone;
            packet.setShowIRLTime = phoneStackData.getShowIRLTime();
            packet.useMilitaryTime = phoneStackData.getShowingMilitaryIRLTime();
            packet.toggleDebugMode = phoneStackData.getIsDebugModeEnabled();
            packet.resetName = false;
            packet.pin = phoneStackData.getPin();
            packet.playerID = phoneStackData.getUuid();

            PacketHandler.INSTANCE.sendToServer(packet);
            Toaster.forPhoneNumber(phoneStackData.getPhoneNumberString()).queueToast(new Toast(new TextComponentTranslation("im.settings.saved").getFormattedText(), 0xFFFFFF));
        }

        if(button == reset)
        {
            currentRingtone = phoneStackData.getRingTone();
            currentNotifSound = phoneStackData.getChatTone();
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();

        SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        handler.stopSound(chatPreview);
        handler.stopSound(ringtonePreview);
    }
}
