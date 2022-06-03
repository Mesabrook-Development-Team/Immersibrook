package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DisconnectedCallNotificationPacket implements IMessage {

	public String forNumber;
	public String toNumber;
	@Override
	public void fromBytes(ByteBuf buf) {
		forNumber = ByteBufUtils.readUTF8String(buf);
		toNumber = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, forNumber);
		ByteBufUtils.writeUTF8String(buf, toNumber);
	}

	public static class Handler implements IMessageHandler<DisconnectedCallNotificationPacket, IMessage>
	{

		@Override
		public IMessage onMessage(DisconnectedCallNotificationPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(DisconnectedCallNotificationPacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onCallDisconnected(message.forNumber, message.toNumber);
		}
	}
}
