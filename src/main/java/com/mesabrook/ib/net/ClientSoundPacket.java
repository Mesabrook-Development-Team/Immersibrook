package com.mesabrook.ib.net;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.blocks.gui.telecom.GuiPhoneBase;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/*
    Client sided packet that feeds sound data to ServerSoundBroadcastPacket.
 */
public class ClientSoundPacket implements IMessage
{
    public BlockPos pos;
    public String modID = Reference.MODID;
    public String soundName;
    public boolean useDelay = true;
    public float volume = 1.0F;
    public float pitch = 1.0F;
    public int range = 25;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = BlockPos.fromLong(buf.readLong());
        modID = ByteBufUtils.readUTF8String(buf);
        soundName = ByteBufUtils.readUTF8String(buf);
        useDelay = buf.readBoolean();
        volume = buf.readFloat();
        pitch = buf.readFloat();
        range = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeUTF8String(buf, modID);
        ByteBufUtils.writeUTF8String(buf, soundName);
        buf.writeBoolean(useDelay);
        buf.writeFloat(volume);
        buf.writeFloat(pitch);
        buf.writeInt(range);
    }

    public static class Handler implements IMessageHandler<ClientSoundPacket, IMessage>
    {
        @Override
        public IMessage onMessage(ClientSoundPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(ClientSoundPacket message, MessageContext ctx)
        {
            try
            {
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = message.pos;
                packet.modID = message.modID;
                packet.soundName = message.soundName;
                packet.volume = message.volume;
                packet.pitch = message.pitch;
                packet.rapidSounds = message.useDelay;

                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(ctx.getServerHandler().player.dimension, ctx.getServerHandler().player.posX, ctx.getServerHandler().player.posY, ctx.getServerHandler().player.posZ, message.range));
            }
            catch(NullPointerException ex)
            {
                Main.logger.error("[" + Reference.MODNAME + "] An error occurred in " + ClientSoundPacket.class.getName());
                Main.logger.error(ex);
                Main.logger.error("[" + Reference.MODNAME + "] Please report this error to us.");
            }
        }
    }
}