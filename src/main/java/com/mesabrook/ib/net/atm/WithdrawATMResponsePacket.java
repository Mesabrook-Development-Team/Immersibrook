package com.mesabrook.ib.net.atm;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WithdrawATMResponsePacket implements IMessage {
	
	public String error = "";

	@Override
	public void fromBytes(ByteBuf buf) {
		error = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, error);
	}
	
	public static class Handler implements IMessageHandler<WithdrawATMResponsePacket, IMessage>
	{
		@Override
		public IMessage onMessage(WithdrawATMResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message));
			return null;
		}

		private void handle(WithdrawATMResponsePacket message) {
			ClientSideHandlers.ATMHandlers.onATMWithdrawResponse(message.error);
		}
		
	}
}
