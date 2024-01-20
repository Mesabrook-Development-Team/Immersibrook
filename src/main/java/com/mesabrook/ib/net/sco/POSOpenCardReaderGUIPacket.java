package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.util.handlers.ClientSideHandlers.SelfCheckOutHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSOpenCardReaderGUIPacket implements IMessage {

	public BlockPos atmPos;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		atmPos = BlockPos.fromLong(buf.readLong());
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(atmPos.toLong());
	}
	
	public static class Handler implements IMessageHandler<POSOpenCardReaderGUIPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSOpenCardReaderGUIPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSOpenCardReaderGUIPacket message, MessageContext ctx) {
			SelfCheckOutHandlers.onOpenCardReaderPacket(message.atmPos);
		}
		
	}
}
