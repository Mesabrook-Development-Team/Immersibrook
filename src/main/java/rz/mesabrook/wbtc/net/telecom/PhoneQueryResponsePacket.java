package rz.mesabrook.wbtc.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class PhoneQueryResponsePacket implements IMessage {

	public ResponseTypes responseType;
	String forNumber;
	String otherNumber = "";
	@Override
	public void fromBytes(ByteBuf buf) {
		responseType = ResponseTypes.values()[buf.readInt()];
		forNumber = ByteBufUtils.readUTF8String(buf);
		otherNumber = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(responseType.ordinal());
		ByteBufUtils.writeUTF8String(buf, forNumber);
		ByteBufUtils.writeUTF8String(buf, otherNumber);
	}

	public enum ResponseTypes
	{
		idle,
		callConnecting,
		callConnected,
		callIncoming
	}
	
	public static class Handler implements IMessageHandler<PhoneQueryResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(PhoneQueryResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PhoneQueryResponsePacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onPhoneQueryResponsePacket(message.forNumber, message.responseType, message.otherNumber);
		}
	}
}
