package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class QueryPriceResponsePacket implements IMessage {

	public BlockPos pos;
	public int placementID;
	public LocationItem locationItem;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(placementID);
		buf.writeBoolean(locationItem != null);
		
		if (locationItem != null)
		{
			ByteBufUtils.writeTag(buf, locationItem.serializeNBT());
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		placementID = buf.readInt();
		locationItem = null;
		
		if (buf.readBoolean())
		{
			locationItem = new LocationItem();
			locationItem.deserializeNBT(ByteBufUtils.readTag(buf));
		}
	}
	
	public static class Handler implements IMessageHandler<QueryPriceResponsePacket, IMessage>
	{
		@Override
		public IMessage onMessage(QueryPriceResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(QueryPriceResponsePacket message, MessageContext ctx)
		{
			ClientSideHandlers.SelfCheckOutHandlers.onShelfPriceLookupResponse(message.pos, message.placementID, message.locationItem);
		}
	}
}
