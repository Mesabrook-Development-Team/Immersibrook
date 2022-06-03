package com.mesabrook.ib.net;

import com.mesabrook.ib.init.ModEnchants;
import com.mesabrook.ib.items.tools.ItemBanHammer;
import com.mesabrook.ib.util.SoundRandomizer;
import io.netty.buffer.ByteBuf;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
            SoundRandomizer.HammerRandomizer();
            message.soundID = SoundRandomizer.hammerResult;
            if (tag == null)
            {
                tag = new NBTTagCompound();
                stack.setTagCompound(tag);
            }

            tag.setString("sndID", message.soundID);

            TextComponentTranslation hammerShift = new TextComponentTranslation("im.hammer.shift");
            hammerShift.getStyle().setBold(true);
			hammerShift.getStyle().setColor(TextFormatting.GOLD);
            player.sendStatusMessage(new TextComponentString(hammerShift.getFormattedText() + " " + TextFormatting.ITALIC + TextFormatting.YELLOW + stack.getTagCompound().getString("sndID")), true);
        }
    }
}
