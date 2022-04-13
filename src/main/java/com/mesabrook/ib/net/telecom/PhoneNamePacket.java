package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.util.handlers.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PhoneNamePacket implements IMessage
{
    public int hand;
    public String newName;
    public String guiClassName;
    public boolean resetName = false;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        hand = buf.readInt();
        newName = ByteBufUtils.readUTF8String(buf);
        guiClassName = ByteBufUtils.readUTF8String(buf);
        resetName = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(hand);
        ByteBufUtils.writeUTF8String(buf, newName);
        ByteBufUtils.writeUTF8String(buf, guiClassName);
        buf.writeBoolean(resetName);
    }

    public static class Handler implements IMessageHandler<PhoneNamePacket, IMessage>
    {
        @Override
        public IMessage onMessage(PhoneNamePacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PhoneNamePacket message, MessageContext ctx)
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

            RefreshStackPacket refresh = new RefreshStackPacket();
            refresh.hand = EnumHand.values()[message.hand];
            refresh.guiClassName = message.guiClassName;
            refresh.newStack = phoneStack;
            PacketHandler.INSTANCE.sendTo(refresh, player);
        }
    }
}