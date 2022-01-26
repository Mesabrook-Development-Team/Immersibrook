package com.mesabrook.ib.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class OutgoingCallResponsePacket implements IMessage {

	public String fromNumber;
	public String toNumber;
	public States state;
	@Override
	public void fromBytes(ByteBuf buf) {
		fromNumber = ByteBufUtils.readUTF8String(buf);
		toNumber = ByteBufUtils.readUTF8String(buf);
		state = States.values()[buf.readInt()];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, fromNumber);
		ByteBufUtils.writeUTF8String(buf, toNumber);
		buf.writeInt(state.ordinal());
	}
	
	public static class Handler implements IMessageHandler<OutgoingCallResponsePacket, IMessage>
	{
		@Override
		public IMessage onMessage(OutgoingCallResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(OutgoingCallResponsePacket message, MessageContext ctx)
		{
			if (message.state == States.success)
			{
				TelecomClientHandlers.onOutgoingCallConnected(message.fromNumber);
			}
			else if (message.state == States.noSuchNumber)
			{
				TelecomClientHandlers.onOutgoingCallNoSuchNumber(message.fromNumber, message.toNumber);
			}
			else if (message.state == States.noReception)
			{
				
			}
		}
	}

	public enum States
	{
		success,
		noSuchNumber,
		noReception
	}
}
