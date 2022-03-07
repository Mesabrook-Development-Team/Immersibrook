package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CallRejectedPacket implements IMessage {
	public String fromNumber;
	public String toNumber;
	@Override
	public void fromBytes(ByteBuf buf) {
		fromNumber = ByteBufUtils.readUTF8String(buf);
		toNumber = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, fromNumber);
		ByteBufUtils.writeUTF8String(buf, toNumber);
	}

	public static class Handler implements IMessageHandler<CallRejectedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(CallRejectedPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(CallRejectedPacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onCallRejected(message.fromNumber, message.toNumber);
		}
	}
}
