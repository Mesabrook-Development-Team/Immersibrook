package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.blocks.BlockRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.items.commerce.ItemRegisterFluidWrapper;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;

public class POSRemoveItemPacket implements IMessage {

	public BlockPos pos;
	public int index;
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		index = buf.readInt();
	}
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(index);
	}
	
	public static class Handler implements IMessageHandler<POSRemoveItemPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSRemoveItemPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSRemoveItemPacket message, MessageContext ctx) {
			World world = ctx.getServerHandler().player.world;
			TileEntity te = world.getTileEntity(message.pos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			TileEntityRegister.RegisterItemHandler handler = (TileEntityRegister.RegisterItemHandler)register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			ItemStack stack = handler.extractItemInternalOnly(message.index);
			if (!register.hasItemsForSession())
			{
				register.setRegisterStatus(RegisterStatuses.Online);
			}
			
			IBlockState registerState = world.getBlockState(register.getPos());
			if (!stack.hasCapability(ItemRegisterFluidWrapper.CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null))
			{
				EnumFacing facing = registerState.getValue(BlockRegister.FACING);
				InventoryHelper.spawnItemStack(world, message.pos.offset(facing).getX(), message.pos.offset(facing).getY(), message.pos.offset(facing).getZ(), stack);
			}
			world.notifyBlockUpdate(message.pos, registerState, registerState, 3);
		}
		
	}
}
