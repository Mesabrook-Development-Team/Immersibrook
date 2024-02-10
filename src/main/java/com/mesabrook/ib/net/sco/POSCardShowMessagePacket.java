package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSCardShowMessagePacket implements IMessage {

	public String message = "";
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, message);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		message = ByteBufUtils.readUTF8String(buf);
	}
	
	public static class Handler implements IMessageHandler<POSCardShowMessagePacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSCardShowMessagePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message));
			return null;
		}

		private void handle(POSCardShowMessagePacket message) {
			ClientSideHandlers.SelfCheckOutHandlers.displayCardReaderMessage(message.message);
		}
	}
}
