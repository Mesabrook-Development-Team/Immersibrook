package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSAddNewFluidMetersAcknowledgePacket implements IMessage {

	public NBTTagCompound updateTag;
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, updateTag);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		updateTag = ByteBufUtils.readTag(buf);
	}
	
	public static class Handler implements IMessageHandler<POSAddNewFluidMetersAcknowledgePacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSAddNewFluidMetersAcknowledgePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSAddNewFluidMetersAcknowledgePacket message, MessageContext ctx)
		{
			ClientSideHandlers.SelfCheckOutHandlers.onAddFluidMeterSaveResponse(message.updateTag);
		}
	}
}
