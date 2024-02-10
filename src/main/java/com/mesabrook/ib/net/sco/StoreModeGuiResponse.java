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

public class StoreModeGuiResponse implements IMessage {

	public LocationEmployee[] locationEmployees;

	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readInt();
		locationEmployees = new LocationEmployee[len];
		for(int i = 0; i < len; i++)
		{
			LocationEmployee locationEmployee = new LocationEmployee();
			NBTTagCompound tag = ByteBufUtils.readTag(buf);
			locationEmployee.deserializeNBT(tag);
			locationEmployees[i] = locationEmployee;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (locationEmployees == null)
		{
			buf.writeInt(0);
			return;
		}
		
		buf.writeInt(locationEmployees.length);
		for(LocationEmployee locationEmployee : locationEmployees)
		{
			ByteBufUtils.writeTag(buf, locationEmployee.serializeNBT());
		}
	}
	
	public static class Handler implements IMessageHandler<StoreModeGuiResponse, IMessage>
	{
		@Override
		public IMessage onMessage(StoreModeGuiResponse message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(StoreModeGuiResponse message, MessageContext ctx) {
			ClientSideHandlers.SelfCheckOutHandlers.onStoreModeGUIResponse(message.locationEmployees);
		}
		
	}
}
