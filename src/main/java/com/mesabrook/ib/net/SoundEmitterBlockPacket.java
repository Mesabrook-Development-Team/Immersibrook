package com.mesabrook.ib.net;

import com.mesabrook.ib.blocks.te.TileEntitySoundEmitter;
import com.mesabrook.ib.items.misc.*;
import io.netty.buffer.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import scala.util.control.Exception;

public class SoundEmitterBlockPacket implements IMessage
{
    public String modID = "wbtc";
    public String soundID = "owo";
    public int range = 0;
    public BlockPos pos;
    public EnumHand hand;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        modID = ByteBufUtils.readUTF8String(buf);
        soundID = ByteBufUtils.readUTF8String(buf);
        range = buf.readInt();

        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        pos = new BlockPos(x, y, z);

        hand = EnumHand.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, modID);
        ByteBufUtils.writeUTF8String(buf, soundID);
        buf.writeInt(range);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(hand.ordinal());
    }

    public static class Handler implements IMessageHandler<SoundEmitterBlockPacket, IMessage>
    {
        @Override
        public IMessage onMessage(SoundEmitterBlockPacket message, MessageContext ctx)
        {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(SoundEmitterBlockPacket message, MessageContext ctx)
        {
            TileEntitySoundEmitter te = (TileEntitySoundEmitter) ctx.getServerHandler().player.getEntityWorld().getTileEntity(message.pos);

            if(te != null)
            {
                te.setModID(message.modID);
                te.setSoundID(message.soundID);
                te.setRange(message.range);

                ctx.getServerHandler().player.sendMessage(new TextComponentString("Sound Emitter Configured! (ModID: " + message.modID + " SoundID: " + message.soundID + " Range: " + message.range));
            }
        }
    }
}
