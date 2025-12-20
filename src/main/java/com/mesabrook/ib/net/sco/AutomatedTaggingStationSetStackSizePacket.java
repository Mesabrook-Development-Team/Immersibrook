package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityAutomatedTaggingStation;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AutomatedTaggingStationSetStackSizePacket implements IMessage {

	public BlockPos pos;
	public int stackSize;
	public double resetDistance;
	public boolean active;
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		stackSize = buf.readInt();
		resetDistance = buf.readDouble();
		active = buf.readBoolean();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(stackSize);
		buf.writeDouble(resetDistance);
		buf.writeBoolean(active);
	}
	
	public static class Handler implements IMessageHandler<AutomatedTaggingStationSetStackSizePacket, IMessage> {
		
		@Override
		public IMessage onMessage(AutomatedTaggingStationSetStackSizePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(AutomatedTaggingStationSetStackSizePacket message, MessageContext ctx) {
			World world = ctx.getServerHandler().player.world;
			TileEntity te = world.getTileEntity(message.pos);
			if (!(te instanceof TileEntityAutomatedTaggingStation))
			{
				return;
			}
			
			TileEntityAutomatedTaggingStation automatedTaggingStation = (TileEntityAutomatedTaggingStation)te;
			automatedTaggingStation.setTagStackSize(message.stackSize);
			automatedTaggingStation.setResetDistance(message.resetDistance);
			automatedTaggingStation.setActive(message.active);
		}
	}
}
