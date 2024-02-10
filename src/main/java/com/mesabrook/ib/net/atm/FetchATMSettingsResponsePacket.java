package com.mesabrook.ib.net.atm;

import java.math.BigDecimal;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FetchATMSettingsResponsePacket implements IMessage {

	public String error = "";
	public BigDecimal cardChargeAmount = new BigDecimal(0);
	public String cardChargeAccount = "";
	@Override
	public void fromBytes(ByteBuf buf) {
		error = ByteBufUtils.readUTF8String(buf);
		cardChargeAmount = new BigDecimal(ByteBufUtils.readUTF8String(buf));
		cardChargeAccount = ByteBufUtils.readUTF8String(buf);
	}
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, error);
		ByteBufUtils.writeUTF8String(buf, cardChargeAmount.toPlainString());
		ByteBufUtils.writeUTF8String(buf, cardChargeAccount);
	}
	
	public static class Handler implements IMessageHandler<FetchATMSettingsResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(FetchATMSettingsResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(FetchATMSettingsResponsePacket message, MessageContext ctx) {
			ClientSideHandlers.ATMHandlers.onATMFetchSettingsResponse(message.error, message.cardChargeAmount, message.cardChargeAccount);
		}
		
	}
}
