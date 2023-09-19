package com.mesabrook.ib.net.sco;

import java.math.BigDecimal;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSFetchPriceResponsePacket implements IMessage {

	public BlockPos pos;
	public int slotId;
	public boolean success;
	public BigDecimal price;
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		slotId = buf.readInt();
		success = buf.readBoolean();
		if (success)
		{
			price = new BigDecimal(ByteBufUtils.readUTF8String(buf));
		}
	}
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(slotId);
		buf.writeBoolean(success);
		if (success)
		{
			ByteBufUtils.writeUTF8String(buf, price.toPlainString());
		}
	}
	
	public static class Handler implements IMessageHandler<POSFetchPriceResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(POSFetchPriceResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSFetchPriceResponsePacket message, MessageContext ctx) {
			ClientSideHandlers.SelfCheckOutHandlers.onFetchPriceResponse(message.pos, message.slotId, message.success, message.price);
		}
		
	}
}
