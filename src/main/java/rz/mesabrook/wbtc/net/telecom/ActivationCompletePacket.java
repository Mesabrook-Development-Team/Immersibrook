package rz.mesabrook.wbtc.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.util.handlers.ClientSideHandlers.TelecomClientHandlers;

public class ActivationCompletePacket implements IMessage {

	public EnumHand hand;
	@Override
	public void fromBytes(ByteBuf buf) {
		hand = EnumHand.values()[buf.readInt()];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand.ordinal());
	}

	public static class Handler implements IMessageHandler<ActivationCompletePacket, IMessage>
	{

		@Override
		public IMessage onMessage(ActivationCompletePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(ActivationCompletePacket message, MessageContext ctx)
		{
			TelecomClientHandlers.onActivationComplete(message.hand);
		}
	}
}
