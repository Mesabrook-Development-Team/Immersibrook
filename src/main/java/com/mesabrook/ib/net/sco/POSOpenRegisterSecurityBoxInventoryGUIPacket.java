package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSOpenRegisterSecurityBoxInventoryGUIPacket implements IMessage {

	public BlockPos pos;
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
	}
	
	public static class Handler implements IMessageHandler<POSOpenRegisterSecurityBoxInventoryGUIPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSOpenRegisterSecurityBoxInventoryGUIPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSOpenRegisterSecurityBoxInventoryGUIPacket message, MessageContext ctx) {
			ctx.getServerHandler().player.openGui(Main.instance, Reference.GUI_REGISTER_SECURITY_BOX_INVENTORY, ctx.getServerHandler().player.world, message.pos.getX(), message.pos.getY(), message.pos.getZ());
		}
	}
}
