package com.mesabrook.ib.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.mesabrook.ib.items.armor.PoliceHelmet;

public class PoliceEffectsTogglePacket implements IMessage
{
    @Override
    public void fromBytes(ByteBuf buf) { }

    @Override
    public void toBytes(ByteBuf buf) { }

    public static class Handler implements IMessageHandler<PoliceEffectsTogglePacket, IMessage>
    {
        @Override
        public IMessage onMessage(PoliceEffectsTogglePacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(ctx));
            return null;
        }

        private void handle(MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;

            ItemStack stack = player.inventory.armorInventory.get(3); // 3 = head
            if (!(stack.getItem() instanceof PoliceHelmet))
            {
                return;
            }

            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null)
            {
                tag = new NBTTagCompound();
                stack.setTagCompound(tag);
            }

            if (!tag.hasKey("policeeffects"))
            {
                tag.setBoolean("policeeffects", true);
            }

            tag.setBoolean("policeeffects", !tag.getBoolean("policeeffects"));
        }
    }
}