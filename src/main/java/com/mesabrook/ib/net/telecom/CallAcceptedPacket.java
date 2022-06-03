package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CallAcceptedPacket implements IMessage {
	public String fromNumber;
	public String toNumber;
	public boolean isConferenceSubCall;
	public boolean isMergeable;
	@Override
	public void fromBytes(ByteBuf buf) {
		fromNumber = ByteBufUtils.readUTF8String(buf);
		toNumber = ByteBufUtils.readUTF8String(buf);
		isConferenceSubCall = buf.readBoolean();
		isMergeable = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, fromNumber);
		ByteBufUtils.writeUTF8String(buf, toNumber);
		buf.writeBoolean(isConferenceSubCall);
		buf.writeBoolean(isMergeable);
	}

	public static class Handler implements IMessageHandler<CallAcceptedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(CallAcceptedPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(CallAcceptedPacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onCallConnected(message.fromNumber, message.toNumber, message.isConferenceSubCall, message.isMergeable);
		}
	}
}
