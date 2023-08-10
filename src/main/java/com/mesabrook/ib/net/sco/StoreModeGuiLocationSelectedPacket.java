package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.apimodels.company.LocationEmployee;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StoreModeGuiLocationSelectedPacket implements IMessage {

	public long locationID;
	@Override
	public void fromBytes(ByteBuf buf) {
		locationID = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(locationID);
	}
	
	public static class Handler implements IMessageHandler<StoreModeGuiLocationSelectedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(StoreModeGuiLocationSelectedPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(StoreModeGuiLocationSelectedPacket message, MessageContext ctx) {
			LocationEmployee locEmp = new LocationEmployee();
			locEmp.LocationID = message.locationID;
			
			EntityPlayerMP player = ctx.getServerHandler().player;
			IEmployeeCapability employeeCap = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			employeeCap.setLocationEmployee(locEmp.LocationID == 0 ? null : locEmp);
		}	
	}
}
