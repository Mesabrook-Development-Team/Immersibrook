package com.mesabrook.ib.net.telecom;

import java.util.Calendar;

import com.mesabrook.ib.net.telecom.OutgoingCallResponsePacket.States;
import com.mesabrook.ib.telecom.CallManager;
import com.mesabrook.ib.util.PhoneLogState;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mesabrook.ib.util.saveData.AntennaData;
import com.mesabrook.ib.util.saveData.PhoneLogData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class InitiateCallPacket implements IMessage {

	public String fromNumber;
	public String toNumber;
	@Override
	public void fromBytes(ByteBuf buf) {
		fromNumber = ByteBufUtils.readUTF8String(buf);
		toNumber = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, fromNumber);
		ByteBufUtils.writeUTF8String(buf, toNumber);
	}

	public static class Handler implements IMessageHandler<InitiateCallPacket, IMessage>
	{
		@Override
		public IMessage onMessage(InitiateCallPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(InitiateCallPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			// Just in case we need this...
			PhoneLogData data = PhoneLogData.getOrCreate(world);
			
			AntennaData antenna = AntennaData.getOrCreate(player.world);
			if (antenna.getBestReception(player.getPosition()) <= 0.0)
			{
				OutgoingCallResponsePacket response = new OutgoingCallResponsePacket();
				response.fromNumber = message.fromNumber;
				response.toNumber = message.toNumber;
				response.state = States.noReception;
				PacketHandler.INSTANCE.sendTo(response, player);
				
				data.addLog(Integer.parseInt(message.fromNumber), Integer.parseInt(message.toNumber), Calendar.getInstance(), 0, PhoneLogState.Failed);
				
				return;
			}
			
			CallManager manager = CallManager.instance();
			
			CallManager.Call destinationExistingCall = manager.getCall(message.toNumber);
			if (destinationExistingCall != null)
			{
				OutgoingCallResponsePacket response = new OutgoingCallResponsePacket();
				response.fromNumber = message.fromNumber;
				response.toNumber = message.toNumber;
				response.state = States.destinationBusy;
				PacketHandler.INSTANCE.sendTo(response, player);
				
				data.addLog(Integer.parseInt(message.fromNumber), Integer.parseInt(message.toNumber), Calendar.getInstance(), 0, PhoneLogState.Failed);
				
				return;
			}
			
			CallManager.Call currentCall = manager.getCall(message.fromNumber);
			CallManager.Call newCall = manager.new Call(message.fromNumber, message.toNumber);
			
			if (currentCall == null)
			{
				manager.enqueueCall(newCall);
			}
			else
			{
				currentCall.addConferenceSubCall(newCall);
			}
		}
	}
}
