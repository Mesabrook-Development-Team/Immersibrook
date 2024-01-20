package com.mesabrook.ib.net.atm;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CreateNewDebitCardATMResponsePacket implements IMessage {
	public String error = "";
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, error);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		error = ByteBufUtils.readUTF8String(buf);
	}
	
	public static class Handler implements IMessageHandler<CreateNewDebitCardATMResponsePacket, IMessage>
	{
		@Override
		public IMessage onMessage(CreateNewDebitCardATMResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(CreateNewDebitCardATMResponsePacket message, MessageContext ctx) {
			ClientSideHandlers.ATMHandlers.onATMNewCardResponse(message.error);
		}
	}
}
