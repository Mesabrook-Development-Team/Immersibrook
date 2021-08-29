package rz.mesabrook.wbtc.net.telecom;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import rz.mesabrook.wbtc.telecom.CallManager;

public class MergeCallPacket implements IMessage {

	public String forNumber;
	public int clientHandlerCode;
	@Override
	public void fromBytes(ByteBuf buf) {
		forNumber = ByteBufUtils.readUTF8String(buf);
		clientHandlerCode = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, forNumber);
		buf.writeInt(clientHandlerCode);
	}

	public static class Handler implements IMessageHandler<MergeCallPacket, IMessage>
	{
		@Override
		public IMessage onMessage(MergeCallPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
	
		private void handle(MergeCallPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			
			CallManager manager = CallManager.instance();
			CallManager.Call call = manager.getCall(message.forNumber);
			
			if (call != null)
			{
				call.merge(message.forNumber, message.clientHandlerCode);
			}
		}
	}
}
