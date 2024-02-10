package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.BlockRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSCardEjectPacket implements IMessage {

	public BlockPos atmPos;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(atmPos.toLong());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		atmPos = BlockPos.fromLong(buf.readLong());
	}
	
	public static class Handler implements IMessageHandler<POSCardEjectPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSCardEjectPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSCardEjectPacket message, MessageContext ctx) {
			World world = ctx.getServerHandler().player.world;
			TileEntity te = world.getTileEntity(message.atmPos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			if (register.getInsertedCardStack() == null)
			{
				return;
			}
			
			IBlockState registerState = world.getBlockState(register.getPos());
			EnumFacing facing = registerState.getValue(BlockRegister.FACING);
			BlockPos spawnPos = register.getPos().offset(facing);
			
			InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), register.getInsertedCardStack());
			register.clearInsertedCardStack();
			register.setRegisterStatus(RegisterStatuses.PaymentCard);
		}
	}
}
