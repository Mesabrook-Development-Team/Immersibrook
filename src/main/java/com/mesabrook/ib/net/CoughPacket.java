package com.mesabrook.ib.net;

import com.mesabrook.ib.util.config.*;
import com.mesabrook.ib.util.handlers.*;
import io.netty.buffer.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

public class CoughPacket implements IMessage
{

    @Override
    public void fromBytes(ByteBuf buf)
    {}

    @Override
    public void toBytes(ByteBuf buf)
    {}

    public static class Handler implements IMessageHandler<CoughPacket, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(CoughPacket message, MessageContext ctx)
        {
            ClientSoundPacket soundPacket = new ClientSoundPacket();
            soundPacket.pos = Minecraft.getMinecraft().player.getPosition();

            if(ModConfig.coughTone.equals("masculine"))
            {
                soundPacket.soundName = "cough_m";
            }
            else
            {
                soundPacket.soundName = "cough_f";
            }
            PacketHandler.INSTANCE.sendToServer(soundPacket);
            return null;
        }
    }
}
