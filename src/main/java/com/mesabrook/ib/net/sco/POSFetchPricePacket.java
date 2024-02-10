package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.GetData;
import com.mesabrook.ib.util.handlers.ServerTickHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSFetchPricePacket implements IMessage {

	public long locationId;
	public BlockPos pos;
	public int slotId;
	public ItemStack stack;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		locationId = buf.readLong();
		pos = BlockPos.fromLong(buf.readLong());
		slotId = buf.readInt();
		stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(locationId);
		buf.writeLong(pos.toLong());
		buf.writeInt(slotId);
		ByteBufUtils.writeItemStack(buf, stack);
	}

	public static class Handler implements IMessageHandler<POSFetchPricePacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSFetchPricePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSFetchPricePacket message, MessageContext ctx) {
			GetData get = new GetData(API.Company, "LocationItemIBAccess/Get", LocationItem.class);
			get.addQueryString("locationid", Long.toString(message.locationId));
			get.addQueryString("name", message.stack.getDisplayName());
			get.addQueryString("quantity", Integer.toString(message.stack.getCount()));
			
			DataRequestTask task = new DataRequestTask(get);
			task.getData().put("pos", message.pos);
			task.getData().put("slotId", message.slotId);
			task.getData().put("playerId", ctx.getServerHandler().player.getUniqueID());
			ServerTickHandler.priceLookupTasks.add(task);
			DataRequestQueue.INSTANCE.addTask(task);
		}
		
	}
}
