package com.mesabrook.ib.net.atm;

import java.math.BigDecimal;

import com.mesabrook.ib.util.apiaccess.PostData;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.handlers.ServerTickHandler;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WithdrawATMPacket implements IMessage {

	public BlockPos dispensePos;
	public long accountID;
	public BigDecimal amount;
	public long companyIDOwner;
	@Override
	public void fromBytes(ByteBuf buf) {
		dispensePos = BlockPos.fromLong(buf.readLong());
		accountID = buf.readLong();
		amount = new BigDecimal(ByteBufUtils.readUTF8String(buf));
		companyIDOwner = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(dispensePos.toLong());
		buf.writeLong(accountID);
		ByteBufUtils.writeUTF8String(buf, amount.toPlainString());
		buf.writeLong(companyIDOwner);
	}

	public static class Handler implements IMessageHandler<WithdrawATMPacket, IMessage>
	{

		@Override
		public IMessage onMessage(WithdrawATMPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(WithdrawATMPacket message, MessageContext ctx) {
			WithdrawParameter parameter = new WithdrawParameter();
			parameter.AccountID = message.accountID;
			parameter.Amount = message.amount;
			parameter.CompanyIDOwner = message.companyIDOwner;
			PostData post = new PostData(API.Company, "AccountIBAccess/Withdraw", parameter, new Class<?>[0]);
			post.getHeaderOverrides().put("PlayerName", ctx.getServerHandler().player.getName());
			
			DataRequestTask task = new DataRequestTask(post);
			task.getData().put("playerID", ctx.getServerHandler().player.getUniqueID());
			task.getData().put("dispensePos", message.dispensePos);
			task.getData().put("amount", message.amount);
			ServerTickHandler.atmWithdrawRequests.add(task);
			DataRequestQueue.INSTANCE.addTask(task);
		}
		
		private static class WithdrawParameter
		{
			public long AccountID;
			public BigDecimal Amount;
			public long CompanyIDOwner;
		}
	}
}
