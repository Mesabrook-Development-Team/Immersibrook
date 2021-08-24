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
			CallManager.instance().phoneQuery(player, message.forNumber);
		}
	}
}
