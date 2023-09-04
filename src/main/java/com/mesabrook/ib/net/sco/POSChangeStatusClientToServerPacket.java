package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSChangeStatusClientToServerPacket implements IMessage {

	public BlockPos pos;
	public RegisterStatuses status;

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		status = RegisterStatuses.values()[buf.readInt()];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(status.ordinal());
	}
	
	public static class Handler implements IMessageHandler<POSChangeStatusClientToServerPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSChangeStatusClientToServerPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSChangeStatusClientToServerPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			TileEntity te = world.getTileEntity(message.pos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			register.setRegisterStatus(message.status);
		}
		
	}
}
