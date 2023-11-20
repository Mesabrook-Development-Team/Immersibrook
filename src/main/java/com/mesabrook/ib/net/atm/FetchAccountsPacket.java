package com.mesabrook.ib.net.atm;

import java.util.ArrayList;
import java.util.UUID;

import com.mesabrook.ib.apimodels.account.Account;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.GetData;
import com.mesabrook.ib.util.handlers.ServerTickHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FetchAccountsPacket implements IMessage {

	public UUID playerID;
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		playerID = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeLong(playerID.getMostSignificantBits());
		buf.writeLong(playerID.getLeastSignificantBits());
	}

	public static class Handler implements IMessageHandler<FetchAccountsPacket, IMessage>
	{

		@Override
		public IMessage onMessage(FetchAccountsPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(FetchAccountsPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			
			GetData get = new GetData(API.Company, "AccountIBAccess/GetAccountsForUser", Account[].class);
			get.getHeaderOverrides().put("PlayerName", player.getName());
			
			DataRequestTask task = new DataRequestTask(get);
			task.getData().put("playerID", message.playerID);
			ServerTickHandler.fetchATMAccountsRequests.add(task);
			DataRequestQueue.INSTANCE.addTask(task);
		}
		
	}
}
