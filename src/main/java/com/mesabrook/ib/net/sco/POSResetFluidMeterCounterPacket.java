package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityFluidMeter;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSResetFluidMeterCounterPacket implements IMessage {

	public BlockPos fluidMeterPos;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(fluidMeterPos.toLong());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		fluidMeterPos = BlockPos.fromLong(buf.readLong());
	}
	
	public static class Handler implements IMessageHandler<POSResetFluidMeterCounterPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSResetFluidMeterCounterPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSResetFluidMeterCounterPacket message, MessageContext ctx)
		{
			World world = ctx.getServerHandler().player.world;
			TileEntity te = world.getTileEntity(message.fluidMeterPos);
			if (!(te instanceof TileEntityFluidMeter))
			{
				return;
			}
			
			TileEntityFluidMeter meter = (TileEntityFluidMeter)te;
			meter.setFluidCounter(0);
		}
	}
}
