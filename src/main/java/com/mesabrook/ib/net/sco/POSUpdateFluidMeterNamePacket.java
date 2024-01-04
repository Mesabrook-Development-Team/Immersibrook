package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.TrackedFluidData;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSUpdateFluidMeterNamePacket implements IMessage {

	public BlockPos registerPos;
	public BlockPos fluidMeterPos;
	public String newName;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(registerPos.toLong());
		buf.writeLong(fluidMeterPos.toLong());
		ByteBufUtils.writeUTF8String(buf, newName);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		registerPos = BlockPos.fromLong(buf.readLong());
		fluidMeterPos = BlockPos.fromLong(buf.readLong());
		newName = ByteBufUtils.readUTF8String(buf);
	}
	
	public static class Handler implements IMessageHandler<POSUpdateFluidMeterNamePacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSUpdateFluidMeterNamePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSUpdateFluidMeterNamePacket message, MessageContext ctx)
		{
			World world = ctx.getServerHandler().player.world;
			
			TileEntity te = world.getTileEntity(message.registerPos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			for(TrackedFluidData fluidData : register.getTrackedFluidData())
			{
				if (fluidData.getFluidMeterPos().equals(message.fluidMeterPos))
				{
					fluidData.setName(message.newName);
					return;
				}
			}
		}
	}
}
