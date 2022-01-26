package com.mesabrook.ib.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;

public class ActivateNoReceptionPacket implements IMessage {
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	
	public static class Handler implements IMessageHandler<ActivateNoReceptionPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ActivateNoReceptionPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(ActivateNoReceptionPacket message, MessageContext ctx)
		{
			ClientSideHandlers.TelecomClientHandlers.onNoReception();
		}
	}
}
