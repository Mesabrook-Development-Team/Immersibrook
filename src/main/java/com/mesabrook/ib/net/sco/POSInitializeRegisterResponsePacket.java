package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSInitializeRegisterResponsePacket implements IMessage {

	public boolean wasSuccessful;
	public BlockPos pos;
	public String error;
	@Override
	public void fromBytes(ByteBuf buf) {
		wasSuccessful = buf.readBoolean();
		pos = BlockPos.fromLong(buf.readLong());
		error = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(wasSuccessful);
		buf.writeLong(pos.toLong());
		if (error == null)
		{
			ByteBufUtils.writeUTF8String(buf, "");
		}
		else
		{
			ByteBufUtils.writeUTF8String(buf, error);
		}
	}

	public static class Handler implements IMessageHandler<POSInitializeRegisterResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(POSInitializeRegisterResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSInitializeRegisterResponsePacket message, MessageContext ctx)
		{
			ClientSideHandlers.SelfCheckOutHandlers.onInitializeRegisterResponse(message.wasSuccessful, message.error, message.pos);
		}
	}
}
