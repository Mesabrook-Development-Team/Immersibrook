package rz.mesabrook.wbtc.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.items.armor.NightVisionGoggles;
import rz.mesabrook.wbtc.items.tools.ItemBanHammer;
import rz.mesabrook.wbtc.util.SoundRandomizer;

public class SoundRandomizerPacket implements IMessage
{
    public String soundID = "";

    @Override
    public void fromBytes(ByteBuf buf)
    {
        soundID = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, soundID);
    }

    public static class Handler implements IMessageHandler<SoundRandomizerPacket, IMessage>
    {
        @Override
        public IMessage onMessage(SoundRandomizerPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SoundRandomizerPacket message, MessageContext ctx)
        {
            EntityPlayerMP player = ctx.getServerHandler().player;
            ItemStack stack = player.inventory.getCurrentItem();
            if (!(stack.getItem() instanceof ItemBanHammer))
            {
                return;
            }

            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null)
            {
                tag = new NBTTagCompound();
                stack.setTagCompound(tag);
            }

            if (!tag.hasKey("sndID"))
            {
                tag.setString("sndID", SoundRandomizer.hammerResult);
                stack.setTagCompound(tag);
            }
            else
            {
                tag.setString("sndID", message.soundID);
                stack.setTagCompound(tag);
            }
        }
    }
}
