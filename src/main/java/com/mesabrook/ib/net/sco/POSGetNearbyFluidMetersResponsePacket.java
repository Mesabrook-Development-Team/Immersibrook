package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSGetNearbyFluidMetersResponsePacket implements IMessage {

	public NBTTagList nearbyFluidMeters;
	
	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound encapsulatingTag = new NBTTagCompound();
		encapsulatingTag.setTag("list", nearbyFluidMeters);
		ByteBufUtils.writeTag(buf, encapsulatingTag);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound encapsulatingTag = ByteBufUtils.readTag(buf);
		nearbyFluidMeters = encapsulatingTag.getTagList("list", NBT.TAG_COMPOUND);
	}
	
	public static class Handler implements IMessageHandler<POSGetNearbyFluidMetersResponsePacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSGetNearbyFluidMetersResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		public void handle(POSGetNearbyFluidMetersResponsePacket message, MessageContext ctx)
		{
			ClientSideHandlers.SelfCheckOutHandlers.onAddFluidMeterListResponse(message.nearbyFluidMeters);
		}
	}
}
