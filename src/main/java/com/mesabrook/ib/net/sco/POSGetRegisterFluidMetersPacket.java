package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityFluidMeter;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.TrackedFluidData;
import com.mesabrook.ib.util.handlers.PacketHandler;

import io.netty.buffer.ByteBuf;
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

public class POSGetRegisterFluidMetersPacket implements IMessage {

	public BlockPos registerPos;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(registerPos.toLong());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		registerPos = BlockPos.fromLong(buf.readLong());
	}
	
	public static class Handler implements IMessageHandler<POSGetRegisterFluidMetersPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSGetRegisterFluidMetersPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSGetRegisterFluidMetersPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			TileEntity te = world.getTileEntity(message.registerPos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			NBTTagList list = new NBTTagList();
			for(TrackedFluidData fluidData : register.getTrackedFluidData())
			{
				te = world.getTileEntity(fluidData.getFluidMeterPos());
				if (!(te instanceof TileEntityFluidMeter))
				{
					continue;
				}
				
				TileEntityFluidMeter meter = (TileEntityFluidMeter)te;
				list.appendTag(meter.getUpdateTag());
			}
			
			NBTTagCompound encapsulatingTag = new NBTTagCompound();
			encapsulatingTag.setTag("list", list);
			POSGetRegisterFluidMetersResponsePacket response = new POSGetRegisterFluidMetersResponsePacket();
			response.encapsulatingTag = encapsulatingTag;
			PacketHandler.INSTANCE.sendTo(response, player);
		}
	}
}
