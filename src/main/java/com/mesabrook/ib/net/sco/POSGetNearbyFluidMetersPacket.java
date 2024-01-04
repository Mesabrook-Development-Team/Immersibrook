package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityFluidMeter;
import com.mesabrook.ib.util.handlers.PacketHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSGetNearbyFluidMetersPacket implements IMessage {
	public int scanDistance;
	public BlockPos registerPos;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(scanDistance);
		buf.writeLong(registerPos.toLong());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		scanDistance = buf.readInt();
		registerPos = BlockPos.fromLong(buf.readLong());
	}
	
	public static class Handler implements IMessageHandler<POSGetNearbyFluidMetersPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSGetNearbyFluidMetersPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSGetNearbyFluidMetersPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			NBTTagList tagList = new NBTTagList();
			
			for(int y = message.registerPos.getY() + message.scanDistance; y > 0 && y < 256 && y > message.registerPos.getY() - message.scanDistance; y--)
			{
				for(int x = message.registerPos.getX() - message.scanDistance; x < message.registerPos.getX() + message.scanDistance; x++)
				{
					for(int z = message.registerPos.getZ() - message.scanDistance; z < message.registerPos.getZ() + message.scanDistance; z++)
					{
						TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
						if (!(te instanceof TileEntityFluidMeter))
						{
							continue;
						}
						
						TileEntityFluidMeter meter = (TileEntityFluidMeter)te;
						NBTTagCompound meterData = meter.getUpdateTag();
						tagList.appendTag(meterData);
					}
				}
			}
			
			POSGetNearbyFluidMetersResponsePacket response = new POSGetNearbyFluidMetersResponsePacket();
			response.nearbyFluidMeters = tagList;
			PacketHandler.INSTANCE.sendTo(response, player);
		}
	}
}
