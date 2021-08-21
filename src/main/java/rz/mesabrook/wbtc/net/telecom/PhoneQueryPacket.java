package rz.mesabrook.wbtc.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.net.telecom.PhoneQueryResponsePacket.ResponseTypes;
import rz.mesabrook.wbtc.telecom.CallManager;
import rz.mesabrook.wbtc.telecom.CallPhases;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;
import rz.mesabrook.wbtc.util.saveData.AntennaData;

public class PhoneQueryPacket implements IMessage {

	public String forNumber;
	@Override
	public void fromBytes(ByteBuf buf) {
		forNumber = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, forNumber);
	}

	public static class Handler implements IMessageHandler<PhoneQueryPacket, IMessage>
	{

		@Override
		public IMessage onMessage(PhoneQueryPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PhoneQueryPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			PhoneQueryResponsePacket response = new PhoneQueryResponsePacket();
			response.forNumber = message.forNumber;
			
			AntennaData antennaData = AntennaData.getOrCreate(world);
			if (antennaData.getBestReception(player.getPosition()) <= 0.0)
			{
				// Too far away
				response.responseType = ResponseTypes.idle;
				PacketHandler.INSTANCE.sendTo(response, player);
				return;
			}
			
			CallManager manager = CallManager.instance();
			CallManager.Call call = manager.getCall(message.forNumber);
			
			if (call == null) // No calls for number
			{
				response.responseType = ResponseTypes.idle;
			}
			else // A call exists for number...
			{
				if (call.getDestPhone().equals(message.forNumber)) // ...and it is being called
				{
					if (call.getCallPhase() == CallPhases.Connecting)
					{
						response.responseType = ResponseTypes.callIncoming;
					}
					else
					{
						response.responseType = ResponseTypes.callConnected;
					}
					
					response.otherNumber = call.getOriginPhone();
				}
				else
				{
					if (call.getCallPhase() == CallPhases.Connecting) // ...and it is trying to call another number
					{
						response.responseType = ResponseTypes.callConnecting;
						response.otherNumber = call.getDestPhone();
					}
					else if (call.getCallPhase() == CallPhases.Connected) // ...and it is already connected
					{
						response.responseType = ResponseTypes.callConnected;
						response.otherNumber = call.getDestPhone();
					}
				}
			}
			
			PacketHandler.INSTANCE.sendTo(response, player);
		}
	}
}
