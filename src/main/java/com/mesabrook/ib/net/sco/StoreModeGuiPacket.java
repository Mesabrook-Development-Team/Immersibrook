package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StoreModeGuiPacket implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	public static class Handler implements IMessageHandler<StoreModeGuiPacket, IMessage>
	{
		@Override
		public IMessage onMessage(StoreModeGuiPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(StoreModeGuiPacket message, MessageContext ctx) {
			ClientSideHandlers.SelfCheckOutHandlers.openStoreModeGUI();
		}
		
	}
}
