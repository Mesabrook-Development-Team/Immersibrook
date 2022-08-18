package com.mesabrook.ib.net;

import com.mesabrook.ib.items.misc.*;
import io.netty.buffer.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import scala.util.control.Exception;

public class WallSignPacket implements IMessage
{
    public String lineOne = "";
    public String lineTwo = "";
    public EnumHand hand;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        lineOne = ByteBufUtils.readUTF8String(buf);
        lineTwo = ByteBufUtils.readUTF8String(buf);
        hand = EnumHand.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, lineOne);
        ByteBufUtils.writeUTF8String(buf, lineTwo);
        buf.writeInt(hand.ordinal());
    }

    public static class Handler implements IMessageHandler<WallSignPacket, IMessage>
    {
        @Override
        public IMessage onMessage(WallSignPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(WallSignPacket message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            ItemStack heldStack = player.getHeldItem(message.hand);
            if (!(heldStack.getItem() instanceof WallSignItemBlock))
            {
                return;
            }

            NBTTagCompound compound = heldStack.getTagCompound();
            if (compound == null)
            {
                compound = new NBTTagCompound();
                heldStack.setTagCompound(compound);
            }

            compound.setString("line1", message.lineOne);
            compound.setString("line2", message.lineTwo);
        }
    }
}
