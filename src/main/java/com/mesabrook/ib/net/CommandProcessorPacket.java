package com.mesabrook.ib.net;

import com.mesabrook.ib.util.*;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.net.*;
public class CommandProcessorPacket implements IMessage, IMessageHandler<CommandProcessorPacket, IMessage>
{
    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(CommandProcessorPacket message, MessageContext ctx)
    {
        try
        {
            ClientSideHandlers.processURIWebRequest(new URI(Reference.CHANGELOG));
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }
}
