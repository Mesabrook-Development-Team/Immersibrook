package com.mesabrook.ib.net.atm;

import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.util.handlers.PacketHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FetchATMSettingsPacket implements IMessage {

	public BlockPos atmPos;
	@Override
	public void fromBytes(ByteBuf buf) {
		atmPos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(atmPos.toLong());
	}

	public static class Handler implements IMessageHandler<FetchATMSettingsPacket, IMessage>
	{
		@Override
		public IMessage onMessage(FetchATMSettingsPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(FetchATMSettingsPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			if (!player.hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
			{
				return;
			}
			IEmployeeCapability emp = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			
			World world = player.world;
			
			TileEntityATM atm = (TileEntityATM)world.getTileEntity(message.atmPos);
			if (atm == null)
			{
				return;
			}
			
			FetchATMSettingsResponsePacket response = new FetchATMSettingsResponsePacket();
			if (emp.getLocationID() == 0 || emp.getLocationEmployee().Location.CompanyID != atm.getCompanyIDOwner())
			{
				response.error = "You don't have permission to view this";
				PacketHandler.INSTANCE.sendTo(message, player);
				return;
			}
			
			response.cardChargeAccount = atm.getAccountRevenue();
			response.cardChargeAmount = atm.getCardChargeAmount();
			PacketHandler.INSTANCE.sendTo(response, player);
		}
		
	}
}
