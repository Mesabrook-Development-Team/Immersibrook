package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.util.handlers.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class CustomizationPacket implements IMessage
{
    public int hand;
    public String newName;
    public String guiClassName;
    public String iconTheme;
    public int lockBackground;
    public int homeBackground;
    public int lockTone;
    public int ringtone;
    public boolean setShowIRLTime;
    public boolean useMilitaryTime;
    public boolean toggleDebugMode;
    public boolean resetName = false;

    // Security
    public int pin;
    public UUID playerID;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        hand = buf.readInt();
        newName = ByteBufUtils.readUTF8String(buf);
        guiClassName = ByteBufUtils.readUTF8String(buf);
        iconTheme = ByteBufUtils.readUTF8String(buf);
        lockBackground = buf.readInt();
        homeBackground = buf.readInt();
        lockTone = buf.readInt();
        ringtone = buf.readInt();
        setShowIRLTime = buf.readBoolean();
        useMilitaryTime = buf.readBoolean();
        toggleDebugMode = buf.readBoolean();
        resetName = buf.readBoolean();

        // Security
        pin = buf.readInt();
        if (buf.readBoolean())
        {
            playerID = new UUID(buf.readLong(), buf.readLong());
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(hand);
        ByteBufUtils.writeUTF8String(buf, newName);
        ByteBufUtils.writeUTF8String(buf, guiClassName);

        if(iconTheme != null)
        {
            ByteBufUtils.writeUTF8String(buf, iconTheme);
        }
        else
        {
            ByteBufUtils.writeUTF8String(buf, "plex");
        }

        buf.writeInt(lockBackground);
        buf.writeInt(homeBackground);
        buf.writeInt(lockTone);
        buf.writeInt(ringtone);
        buf.writeBoolean(setShowIRLTime);
        buf.writeBoolean(useMilitaryTime);
        buf.writeBoolean(toggleDebugMode);
        buf.writeBoolean(resetName);

        // Security
        buf.writeInt(pin);
        buf.writeBoolean(playerID != null);
        if (playerID != null)
        {
            buf.writeLong(playerID.getMostSignificantBits());
            buf.writeLong(playerID.getLeastSignificantBits());
        }
    }

    public static class Handler implements IMessageHandler<CustomizationPacket, IMessage>
    {
        @Override
        public IMessage onMessage(CustomizationPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(CustomizationPacket message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            ItemStack phoneStack = player.getHeldItem(EnumHand.values()[message.hand]);
            if (!(phoneStack.getItem() instanceof ItemPhone))
            {
                return;
            }

            if(message.newName != null)
            {
                phoneStack.setStackDisplayName(message.newName);
            }

            if(message.resetName)
            {
                phoneStack.clearCustomName();
            }

            NBTTagCompound tag = phoneStack.getTagCompound();
            if (tag == null)
            {
                tag = new NBTTagCompound();
                phoneStack.setTagCompound(tag);
            }

            ItemPhone.NBTData phoneData = new ItemPhone.NBTData();
            phoneData.deserializeNBT(tag);

            phoneData.setHomeBackground(message.homeBackground);
            phoneData.setLockBackground(message.lockBackground);
            phoneData.setChatTone(message.lockTone);
            phoneData.setRingTone(message.ringtone);

            phoneData.setShowIRLTime(message.setShowIRLTime);
            phoneData.setShowingMilitaryIRLTime(message.useMilitaryTime);
            phoneData.setIsDebugModeEnabled(message.toggleDebugMode);
            phoneData.setIconTheme(message.iconTheme);

            // Security
            phoneData.setPin(message.pin);
            phoneData.setUuid(message.playerID);

            if (message.pin != 0)
            {
                phoneData.setSecurityStrategy(ItemPhone.NBTData.SecurityStrategies.PIN);
            }
            else if (message.playerID != null)
            {
                phoneData.setSecurityStrategy(ItemPhone.NBTData.SecurityStrategies.UUID);
            }
            else
            {
                phoneData.setSecurityStrategy(ItemPhone.NBTData.SecurityStrategies.None);
            }

            tag.merge(phoneData.serializeNBT());

            RefreshStackPacket refresh = new RefreshStackPacket();
            refresh.hand = EnumHand.values()[message.hand];
            refresh.guiClassName = message.guiClassName;
            refresh.newStack = phoneStack;
            PacketHandler.INSTANCE.sendTo(refresh, player);
        }
    }
}
