package com.mesabrook.ib.net;

import com.mesabrook.ib.util.handlers.PlayerEvents;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FetchCSNotificationPacket implements IMessage {

	public enum FetchTypes
	{
		InitialLogin,
		NotificationsGUI,
		MinedroidApp
	}
	
	public FetchTypes fetchType;

	@Override
	public void fromBytes(ByteBuf buf) {
		fetchType = FetchTypes.values()[buf.readInt()];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(fetchType.ordinal());
	}
	
	public static class Handler implements IMessageHandler<FetchCSNotificationPacket, IMessage>
	{
		@Override
		public IMessage onMessage(FetchCSNotificationPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(FetchCSNotificationPacket message, MessageContext ctx) {
			PlayerEvents.createCSNotificationsDataRequest(ctx.getServerHandler().player, message.fetchType);
		}
	}
}
