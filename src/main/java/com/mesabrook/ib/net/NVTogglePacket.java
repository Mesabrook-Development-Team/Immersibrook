package com.mesabrook.ib.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.mesabrook.ib.items.armor.NightVisionGoggles;

public class NVTogglePacket implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) { }

    @Override
    public void toBytes(ByteBuf buf) { }

    public static class Handler implements IMessageHandler<NVTogglePacket, IMessage>
    {

        @Override
        public IMessage onMessage(NVTogglePacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(ctx));
            return null;
        }

        private void handle(MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;

            ItemStack stack = player.inventory.armorInventory.get(3); // 3 = head
            if (!(stack.getItem() instanceof NightVisionGoggles))
            {
                return;
            }

            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null)
            {
                tag = new NBTTagCompound();
                stack.setTagCompound(tag);
            }

            if (!tag.hasKey("nightvision"))
            {
                tag.setBoolean("nightvision", true);
            }

            tag.setBoolean("nightvision", !tag.getBoolean("nightvision"));
        }
    }
}
