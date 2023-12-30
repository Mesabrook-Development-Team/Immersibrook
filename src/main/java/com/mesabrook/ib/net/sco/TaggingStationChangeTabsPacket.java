package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TaggingStationChangeTabsPacket implements IMessage {

	public BlockPos taggingPos;
	public boolean toUntag;
	@Override
	public void fromBytes(ByteBuf buf) {
		taggingPos = BlockPos.fromLong(buf.readLong());
		toUntag = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(taggingPos.toLong());
		buf.writeBoolean(toUntag);
	}

	public static class Handler implements IMessageHandler<TaggingStationChangeTabsPacket, IMessage>
	{
		@Override
		public IMessage onMessage(TaggingStationChangeTabsPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(TaggingStationChangeTabsPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			
			player.openGui(Main.instance, message.toUntag ? Reference.GUI_TAGGING_STATION_UNTAG : Reference.GUI_TAGGING_STATION, player.world, message.taggingPos.getX(), message.taggingPos.getY(), message.taggingPos.getZ());
		}
	}
}
