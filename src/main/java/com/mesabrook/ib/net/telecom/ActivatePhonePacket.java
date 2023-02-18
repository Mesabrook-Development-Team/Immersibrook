package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mesabrook.ib.util.saveData.AntennaData;
import com.mesabrook.ib.util.saveData.PhoneNumberData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ActivatePhonePacket implements IMessage {

	public int hand;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		hand = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand);
	}

	public static class Handler implements IMessageHandler<ActivatePhonePacket, IMessage>
	{
		@Override
		public IMessage onMessage(ActivatePhonePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(ActivatePhonePacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			double reception = AntennaData.getOrCreate(player.world).getBestReception(player.getPosition());
			
			if (reception <= 0.0 || player.world.provider.getDimension() != 0)
			{
				ActivateNoReceptionPacket noReception = new ActivateNoReceptionPacket();
				PacketHandler.INSTANCE.sendTo(noReception, player);
				return;
			}
			
			// Retrieve 5 random numbers for user to pick from
			int[] numbers = new int[5];
			PhoneNumberData numberData = PhoneNumberData.getOrCreate(player.world);
			for(int i = 0; i < 5; i++)
			{
				numbers[i] = numberData.getNewPhoneNumber();
			}
			
			ActivateChooseNumberPacket chooseNumber = new ActivateChooseNumberPacket();
			chooseNumber.numberChoices = numbers;
			PacketHandler.INSTANCE.sendTo(chooseNumber, player);

			SetBatteryLevelPacket batPacket = new SetBatteryLevelPacket();
			batPacket.hand = message.hand;
			batPacket.batteryLevel = 9999;
			PacketHandler.INSTANCE.sendToServer(batPacket);
		}
	}
}
