package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.container.ContainerTaggingStation;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TaggingStationDistanceChangedPacket implements IMessage {

	public double distance;

	@Override
	public void fromBytes(ByteBuf buf) {
		distance = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(distance);
	}
	
	public static class Handler implements IMessageHandler<TaggingStationDistanceChangedPacket, IMessage>
	{

		@Override
		public IMessage onMessage(TaggingStationDistanceChangedPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(TaggingStationDistanceChangedPacket message, MessageContext ctx) {
			Container container = ctx.getServerHandler().player.openContainer;
			if (container == null || !(container instanceof ContainerTaggingStation))
			{
				return;
			}
			
			ContainerTaggingStation tagStation = (ContainerTaggingStation)container;
			tagStation.setResetDistance(message.distance);
		}
		
	}
}
