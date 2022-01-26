package com.mesabrook.ib.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class PhoneTossedPacket implements IMessage {

	public String tossedPhoneNumber;
	@Override
	public void fromBytes(ByteBuf buf) {
		tossedPhoneNumber = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, tossedPhoneNumber);
	}

	public static class Handler implements IMessageHandler<PhoneTossedPacket, IMessage>
	{

		@Override
		public IMessage onMessage(PhoneTossedPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PhoneTossedPacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onPhoneToss(message.tossedPhoneNumber);
		}
	}
}
