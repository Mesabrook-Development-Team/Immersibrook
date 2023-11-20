package com.mesabrook.ib.net.atm;

import java.math.BigDecimal;

import com.mesabrook.ib.blocks.te.TileEntityATM;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.util.handlers.PacketHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateATMSettingsPacket implements IMessage {

	public BlockPos atmPos;
	public BigDecimal cardChargeAmount;
	public String cardChargeAccount;
	@Override
	public void fromBytes(ByteBuf buf) {
		atmPos = BlockPos.fromLong(buf.readLong());
		cardChargeAmount = new BigDecimal(ByteBufUtils.readUTF8String(buf));
		cardChargeAccount = ByteBufUtils.readUTF8String(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(atmPos.toLong());
		ByteBufUtils.writeUTF8String(buf, cardChargeAmount.toPlainString());
		ByteBufUtils.writeUTF8String(buf, cardChargeAccount);
	}
	
	public static class Handler implements IMessageHandler<UpdateATMSettingsPacket, IMessage>
	{

		@Override
		public IMessage onMessage(UpdateATMSettingsPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(UpdateATMSettingsPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			
			if (!player.hasCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null))
			{
				return;
			}
			
			TileEntityATM atm = (TileEntityATM)player.world.getTileEntity(message.atmPos);
			if (atm == null)
			{
				return;
			}
			
			IEmployeeCapability emp = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			if (emp.getLocationID() == 0 || emp.getLocationEmployee().Location.CompanyID != atm.getCompanyIDOwner())
			{
				return;
			}
			
			atm.setAccountRevenue(message.cardChargeAccount);
			atm.setCardChargeAmount(message.cardChargeAmount);
			player.world.notifyBlockUpdate(message.atmPos, player.world.getBlockState(message.atmPos), player.world.getBlockState(message.atmPos), 3);
			
			UpdateATMSettingsResponsePacket response = new UpdateATMSettingsResponsePacket();
			PacketHandler.INSTANCE.sendTo(response, player);
		}
		
	}
}
