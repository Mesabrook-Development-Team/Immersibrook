package com.mesabrook.ib.net.sco;

import java.util.ArrayList;

import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.util.handlers.PacketHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSAddNewFluidMetersPacket implements IMessage {

	public BlockPos registerPos;
	public ArrayList<BlockPos> newBlockPos = new ArrayList<>();
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(registerPos.toLong());
		buf.writeInt(newBlockPos.size());
		for(BlockPos blockPos : newBlockPos)
		{
			buf.writeLong(blockPos.toLong());
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		registerPos = BlockPos.fromLong(buf.readLong());
		newBlockPos = new ArrayList<>();
		int max = buf.readInt();
		for(int i = 0; i < max; i++)
		{
			newBlockPos.add(BlockPos.fromLong(buf.readLong()));
		}
	}
	
	public static class Handler implements IMessageHandler<POSAddNewFluidMetersPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSAddNewFluidMetersPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(POSAddNewFluidMetersPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			TileEntity te = world.getTileEntity(message.registerPos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			for(BlockPos newMeter : message.newBlockPos)
			{
				register.addTrackedFluidData(newMeter);
			}
			
			POSAddNewFluidMetersAcknowledgePacket ack = new POSAddNewFluidMetersAcknowledgePacket();
			ack.updateTag = register.getUpdateTag();
			PacketHandler.INSTANCE.sendTo(ack, player);
		}
	}
}
