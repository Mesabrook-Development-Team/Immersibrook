package com.mesabrook.ib.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class PhoneQueryResponsePacket implements IMessage {

	public static final int PHONE_START_CLIENT_HANDLER = -1;
	public static final int PHONE_APP_CLIENT_HANDLER = -2;
	public ResponseTypes responseType;
	public String forNumber;
	public String otherNumber = "";
	public int clientHandlerCode;
	public boolean isConferenceSubCall = false;
	public boolean isMergeable = false;
	@Override
	public void fromBytes(ByteBuf buf) {
		responseType = ResponseTypes.values()[buf.readInt()];
		forNumber = ByteBufUtils.readUTF8String(buf);
		otherNumber = ByteBufUtils.readUTF8String(buf);
		clientHandlerCode = buf.readInt();
		isConferenceSubCall = buf.readBoolean();
		isMergeable = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(responseType.ordinal());
		ByteBufUtils.writeUTF8String(buf, forNumber);
		ByteBufUtils.writeUTF8String(buf, otherNumber);
		buf.writeInt(clientHandlerCode);
		buf.writeBoolean(isConferenceSubCall);
		buf.writeBoolean(isMergeable);
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
			if (message.clientHandlerCode == PHONE_START_CLIENT_HANDLER)
			{
				TelecomClientHandlers.onPhoneQueryResponsePacket(message);
			}
			else if (message.clientHandlerCode == PHONE_APP_CLIENT_HANDLER)
			{
				TelecomClientHandlers.onPhoneQueryResponseForPhoneApp(message);
			}
			else if (TelecomClientHandlers.phoneQueryResponseHandlers.containsKey(message.clientHandlerCode))
			{
				TelecomClientHandlers.phoneQueryResponseHandlers.get(message.clientHandlerCode).accept(message);
				TelecomClientHandlers.phoneQueryResponseHandlers.remove(message.clientHandlerCode);
			}
		}
	}
}
