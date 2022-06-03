package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mesabrook.ib.util.saveData.PhoneLogData;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.server.FMLServerHandler;

public class GetPhoneRecentsPacket implements IMessage {

	public String phoneNumber;
	@Override
	public void fromBytes(ByteBuf buf) {
		phoneNumber = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, phoneNumber);
	}

	public static class Handler implements IMessageHandler<GetPhoneRecentsPacket, IMessage>
	{
		@Override
		public IMessage onMessage(GetPhoneRecentsPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(GetPhoneRecentsPacket message, MessageContext ctx)
		{
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().worlds[0];
			PhoneLogData logData = PhoneLogData.getOrCreate(world);
			
			int phoneNumber = 0;
			try
			{
				phoneNumber = Integer.parseInt(message.phoneNumber);
			}
			catch(Exception ex) {}
			
			GetPhoneRecentsResponsePacket response = new GetPhoneRecentsResponsePacket();
			response.phoneNumber = message.phoneNumber;
			response.logDatum = logData.getLogsByPhone(phoneNumber);
			PacketHandler.INSTANCE.sendTo(response, ctx.getServerHandler().player);
		}
	}
}
