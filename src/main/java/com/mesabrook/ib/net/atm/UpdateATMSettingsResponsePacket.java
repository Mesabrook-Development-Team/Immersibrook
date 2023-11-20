package com.mesabrook.ib.net.atm;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateATMSettingsResponsePacket implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
	}

	public static class Handler implements IMessageHandler<UpdateATMSettingsResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(UpdateATMSettingsResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle());
			return null;
		}

		private void handle() {
			ClientSideHandlers.ATMHandlers.onATMUpdateSettingsResponse();
		}
		
	}
}
