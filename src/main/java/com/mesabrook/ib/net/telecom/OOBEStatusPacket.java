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

public class OOBEStatusPacket implements IMessage
{
    public int hand;
    public String guiClassName;
    public String nextGuiClassName;
    public boolean needToDoOOBE = false;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        hand = buf.readInt();
        guiClassName = ByteBufUtils.readUTF8String(buf);
        nextGuiClassName = ByteBufUtils.readUTF8String(buf);
        needToDoOOBE = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(hand);
        ByteBufUtils.writeUTF8String(buf, guiClassName);
        ByteBufUtils.writeUTF8String(buf, nextGuiClassName);
        buf.writeBoolean(needToDoOOBE);
    }

    public static class Handler implements IMessageHandler<OOBEStatusPacket, IMessage>
    {
        @Override
        public IMessage onMessage(OOBEStatusPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(OOBEStatusPacket message, MessageContext ctx)
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

            ItemPhone.NBTData phoneData = new ItemPhone.NBTData();
            phoneData.deserializeNBT(tag);
            phoneData.setNeedToDoOOBE(message.needToDoOOBE);
            tag.merge(phoneData.serializeNBT());

            RefreshStackPacket refresh = new RefreshStackPacket();
            refresh.hand = EnumHand.values()[message.hand];
            refresh.guiClassName = message.guiClassName;
            if (message.nextGuiClassName == null || message.nextGuiClassName == "")
            {
                refresh.nextGuiClassName = message.guiClassName;
            }
            else
            {
                refresh.nextGuiClassName = message.nextGuiClassName;
            }
            refresh.newStack = phoneStack;
            PacketHandler.INSTANCE.sendTo(refresh, player);
        }
    }
}
