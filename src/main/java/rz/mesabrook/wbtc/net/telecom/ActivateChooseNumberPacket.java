package rz.mesabrook.wbtc.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class ActivateChooseNumberPacket implements IMessage {

	public int[] numberChoices;
	public boolean isResend;
	@Override
	public void fromBytes(ByteBuf buf) {
		numberChoices = new int[5];
		for(int i = 0; i < 5; i++)
		{
			numberChoices[i] = buf.readInt();
		}
		
		isResend = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		for(int i = 0; i < 5; i++)
		{
			buf.writeInt(numberChoices[i]);
		}
		
		buf.writeBoolean(isResend);
	}

	public static class Handler implements IMessageHandler<ActivateChooseNumberPacket, IMessage>
	{

		@Override
		public IMessage onMessage(ActivateChooseNumberPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(ActivateChooseNumberPacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onChooseNumber(message.numberChoices, message.isResend);
		}
	}
}
