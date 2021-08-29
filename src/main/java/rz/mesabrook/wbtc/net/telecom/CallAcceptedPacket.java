package rz.mesabrook.wbtc.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class CallAcceptedPacket implements IMessage {
	public String fromNumber;
	public String toNumber;
	public boolean isConferenceSubCall;
	@Override
	public void fromBytes(ByteBuf buf) {
		fromNumber = ByteBufUtils.readUTF8String(buf);
		toNumber = ByteBufUtils.readUTF8String(buf);
		isConferenceSubCall = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, fromNumber);
		ByteBufUtils.writeUTF8String(buf, toNumber);
		buf.writeBoolean(isConferenceSubCall);
	}

	public static class Handler implements IMessageHandler<CallAcceptedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(CallAcceptedPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(CallAcceptedPacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onCallConnected(message.fromNumber, message.toNumber, message.isConferenceSubCall);
		}
	}
}
