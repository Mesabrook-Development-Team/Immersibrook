package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
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

public class PhoneRingtonesPacket implements IMessage
{
    public int hand;
    public int ringTone;
    public int chatTone;
    public String guiClassName;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        hand = buf.readInt();
        ringTone = buf.readInt();
        chatTone = buf.readInt();
        guiClassName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(hand);
        buf.writeInt(ringTone);
        buf.writeInt(chatTone);
        ByteBufUtils.writeUTF8String(buf, guiClassName);
    }

    public static class Handler implements IMessageHandler<PhoneRingtonesPacket, IMessage>
    {
        @Override
        public IMessage onMessage(PhoneRingtonesPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PhoneRingtonesPacket message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            ItemStack phoneStack = player.getHeldItem(EnumHand.values()[message.hand]);
            if (!(phoneStack.getItem() instanceof ItemPhone))
            {
                return;
            }

            NBTTagCompound tag = phoneStack.getTagCompound();
            if (tag == null)
            {
                tag = new NBTTagCompound();
                phoneStack.setTagCompound(tag);
            }

            NBTData phoneData = new NBTData();
            phoneData.deserializeNBT(tag);

            phoneData.setChatTone(message.chatTone);
            phoneData.setRingTone(message.ringTone);

            tag = phoneData.serializeNBT();
            phoneStack.setTagCompound(tag);

            RefreshStackPacket refresh = new RefreshStackPacket();
            refresh.hand = EnumHand.values()[message.hand];
            refresh.guiClassName = message.guiClassName;
            refresh.newStack = phoneStack;
            PacketHandler.INSTANCE.sendTo(refresh, player);
        }
    }
}
