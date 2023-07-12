package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSInitializeRegisterPacket implements IMessage {

	public String identifier;
	public BlockPos pos;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		identifier = ByteBufUtils.readUTF8String(buf);
		pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, identifier);
		buf.writeLong(pos.toLong());
	}

	public static class Handler implements IMessageHandler<POSInitializeRegisterPacket, IMessage>
	{

		@Override
		public IMessage onMessage(POSInitializeRegisterPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSInitializeRegisterPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			TileEntity te = world.getTileEntity(message.pos);
			if (te == null || !(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			register.initialize(message.identifier, player.getUniqueID(), player.getDisplayNameString());
		}
	}
}
