package rz.mesabrook.wbtc.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.util.handlers.PacketHandler;
import rz.mesabrook.wbtc.util.saveData.AntennaData;

public class GetReceptionStrengthPacket implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<GetReceptionStrengthPacket, IMessage>
	{

		@Override
		public IMessage onMessage(GetReceptionStrengthPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(GetReceptionStrengthPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			AntennaData antennaData = AntennaData.getOrCreate(world);
			double reception = antennaData.getBestReception(player.getPosition());
			
			GetStrengthResponsePacket response = new GetStrengthResponsePacket();
			response.reception = reception;
			PacketHandler.INSTANCE.sendTo(response, player);
		}		
	}
}
