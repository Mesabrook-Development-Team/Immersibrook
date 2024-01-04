package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSRemoveFluidMeterPacket implements IMessage {

	public BlockPos registerPos;
	public BlockPos fluidMeterPos;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(registerPos.toLong());
		buf.writeLong(fluidMeterPos.toLong());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		registerPos = BlockPos.fromLong(buf.readLong());
		fluidMeterPos = BlockPos.fromLong(buf.readLong());
	}
	
	public static class Handler implements IMessageHandler<POSRemoveFluidMeterPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSRemoveFluidMeterPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSRemoveFluidMeterPacket message, MessageContext ctx) 
		{
			World world = ctx.getServerHandler().player.world;
			TileEntity te = world.getTileEntity(message.registerPos);
			
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			register.removeTrackedFluidData(message.fluidMeterPos);
		}
	}
}
