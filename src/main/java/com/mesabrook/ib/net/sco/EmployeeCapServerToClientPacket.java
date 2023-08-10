package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EmployeeCapServerToClientPacket implements IMessage {

	public LocationEmployee locationEmployee;
	@Override
	public void fromBytes(ByteBuf buf) {
		locationEmployee = new LocationEmployee();
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		locationEmployee.deserializeNBT(tag);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, locationEmployee.serializeNBT());
	}

	public static class Handler implements IMessageHandler<EmployeeCapServerToClientPacket, IMessage>
	{

		@Override
		public IMessage onMessage(EmployeeCapServerToClientPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(EmployeeCapServerToClientPacket message, MessageContext ctx) {
			ClientSideHandlers.SelfCheckOutHandlers.updateEmployeeCapaiblity(message.locationEmployee);
		}
		
	}
}
