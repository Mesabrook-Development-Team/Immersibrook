package com.mesabrook.ib.net.atm;

import java.math.BigDecimal;

import com.mesabrook.ib.apimodels.account.DebitCard;
import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.PostData;
import com.mesabrook.ib.util.handlers.ServerTickHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CreateNewDebitCardATMPacket implements IMessage {

	public long accountID;
	public String pin;
	public BlockPos spawnPos;
	public BlockPos atmPos;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(accountID);
		ByteBufUtils.writeUTF8String(buf, pin);
		buf.writeLong(spawnPos.toLong());
		buf.writeLong(atmPos.toLong());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		accountID = buf.readLong();
		pin = ByteBufUtils.readUTF8String(buf);
		spawnPos = BlockPos.fromLong(buf.readLong());
		atmPos = BlockPos.fromLong(buf.readLong());
	}
	
	public static class Handler implements IMessageHandler<CreateNewDebitCardATMPacket, IMessage>
	{
		@Override
		public IMessage onMessage(CreateNewDebitCardATMPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(CreateNewDebitCardATMPacket message, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().player;
			World world = player.world;
			TileEntity te = world.getTileEntity(message.atmPos);
			if (!(te instanceof TileEntityATM))
			{
				return;
			}
			
			TileEntityATM atm = (TileEntityATM)te;
			
			IssueDebitCardParameter parameter = new IssueDebitCardParameter();
			parameter.AccountID = message.accountID;
			parameter.PIN = message.pin;
			parameter.CardFeeAccount = atm.getAccountRevenue();
			parameter.CardFeeAmount = atm.getCardChargeAmount();
			
			PostData post = new PostData(API.Company, "DebitCardIBAccess/IssueDebitCard", parameter, DebitCard.class);
			post.getHeaderOverrides().put("playerName", player.getName());
			
			DataRequestTask task = new DataRequestTask(post);
			task.getData().put("playerID", player.getUniqueID());
			task.getData().put("spawnPos", message.spawnPos);
			ServerTickHandler.newCardRequests.add(task);
			DataRequestQueue.INSTANCE.addTask(task);
		}
		
		public static class IssueDebitCardParameter
		{
			public long AccountID;
			public String PIN;
			public String CardFeeAccount;
			public BigDecimal CardFeeAmount;
		}
	}
}
