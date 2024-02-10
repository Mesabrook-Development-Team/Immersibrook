package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.util.handlers.PacketHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSScanFluidPacket implements IMessage {

	public BlockPos registerPos;
	public ItemStack registerFluidWrapper;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(registerPos.toLong());
		ByteBufUtils.writeItemStack(buf, registerFluidWrapper);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		registerPos = BlockPos.fromLong(buf.readLong());
		registerFluidWrapper = ByteBufUtils.readItemStack(buf);
	}
	
	public static class Handler implements IMessageHandler<POSScanFluidPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSScanFluidPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSScanFluidPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			TileEntity te = world.getTileEntity(message.registerPos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			register.insertItemInFirstAvailableSlot(message.registerFluidWrapper);
			
			POSScanFluidResponsePacket response = new POSScanFluidResponsePacket();
			response.updateTag = register.getUpdateTag();
			PacketHandler.INSTANCE.sendTo(response, player);
		}
	}
}
