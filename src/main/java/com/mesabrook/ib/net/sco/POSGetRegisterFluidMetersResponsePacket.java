package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSGetRegisterFluidMetersResponsePacket implements IMessage {

	public NBTTagCompound encapsulatingTag;
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, encapsulatingTag);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		encapsulatingTag = ByteBufUtils.readTag(buf);
	}
	
	public static class Handler implements IMessageHandler<POSGetRegisterFluidMetersResponsePacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSGetRegisterFluidMetersResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSGetRegisterFluidMetersResponsePacket message, MessageContext ctx) {
			ClientSideHandlers.SelfCheckOutHandlers.onPayFluidsResponse(message.encapsulatingTag.getTagList("list", NBT.TAG_COMPOUND));
		}
	}
}
