package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.GetData;
import com.mesabrook.ib.util.handlers.ServerTickHandler;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StoreModeGuiOpenedPacket implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<StoreModeGuiOpenedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(StoreModeGuiOpenedPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(StoreModeGuiOpenedPacket message, MessageContext ctx) {
			String user = ctx.getServerHandler().player.getName();
			
			GetData locationRetrieval = new GetData(API.Company, "LocationEmployeeIBAccess/GetLocationClearanceForPlayer/" + user, LocationEmployee[].class);
			DataRequestTask task = new DataRequestTask(locationRetrieval);
			ServerTickHandler.storeModeRequestsByUser.put(ctx.getServerHandler().player.getUniqueID(), task);
			DataRequestQueue.INSTANCE.addTask(task);
		}
		
	}
}
