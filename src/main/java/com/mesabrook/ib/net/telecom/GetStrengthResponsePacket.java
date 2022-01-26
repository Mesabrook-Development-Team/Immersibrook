package com.mesabrook.ib.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class GetStrengthResponsePacket implements IMessage {

	double reception;
	@Override
	public void fromBytes(ByteBuf buf) {
		reception = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(reception);
	}

	public static class Handler implements IMessageHandler<GetStrengthResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(GetStrengthResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(GetStrengthResponsePacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onReceptionReceived(message.reception);
		}
	}
}
