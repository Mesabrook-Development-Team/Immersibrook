package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.items.misc.ItemPhone;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetBatteryLevelPacket implements IMessage
{
    public int hand;
    public int batteryLevel;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        hand = buf.readInt();
        batteryLevel = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(hand);
        buf.writeInt(batteryLevel);
    }

    public static class Handler implements IMessageHandler<SetBatteryLevelPacket, IMessage>
    {
        @Override
        public IMessage onMessage(SetBatteryLevelPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SetBatteryLevelPacket message, MessageContext ctx)
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
            phoneData.setBatteryLevel(message.batteryLevel);
            tag.merge(phoneData.serializeNBT());
        }
    }
}
