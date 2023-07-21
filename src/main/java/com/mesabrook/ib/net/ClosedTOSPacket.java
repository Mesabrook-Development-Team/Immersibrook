package com.mesabrook.ib.net;

import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.saveData.TOSData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClosedTOSPacket implements IMessage {

	public boolean accepted;
	@Override
	public void fromBytes(ByteBuf buf) {
		accepted = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(accepted);
	}

	public static class Handler implements IMessageHandler<ClosedTOSPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ClosedTOSPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(ClosedTOSPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			if (message.accepted)
			{
				World world = player.world;
				TOSData tos = (TOSData)world.loadData(TOSData.class, Reference.TOS_DATA_NAME);
				if (tos == null)
				{
					tos = new TOSData(Reference.TOS_DATA_NAME);
					world.setData(Reference.TOS_DATA_NAME, tos);
				}
				tos.addPlayer(player.getPersistentID());
			}
			else
			{
				player.connection.disconnect(new TextComponentString("Terms Of Service agreement required to play on Mesabrook"));
			}
		}
	}
}
