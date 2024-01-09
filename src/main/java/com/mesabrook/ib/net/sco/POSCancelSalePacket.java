package com.mesabrook.ib.net.sco;

import java.math.BigDecimal;

import com.mesabrook.ib.blocks.BlockRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister;
import com.mesabrook.ib.blocks.te.TileEntityRegister.RegisterStatuses;
import com.mesabrook.ib.items.commerce.ItemMoney;
import com.mesabrook.ib.items.commerce.ItemRegisterFluidWrapper;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class POSCancelSalePacket implements IMessage {

	public BlockPos pos;
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
	}

	public static class Handler implements IMessageHandler<POSCancelSalePacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSCancelSalePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSCancelSalePacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			TileEntity te = world.getTileEntity(message.pos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			IItemHandler iHandler = register.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if (!(iHandler instanceof TileEntityRegister.RegisterItemHandler))
			{
				return;
			}
			
			TileEntityRegister.RegisterItemHandler handler = (TileEntityRegister.RegisterItemHandler)iHandler;
			IBlockState state = world.getBlockState(message.pos);
			BlockPos spawnPos = message.pos.offset(state.getValue(BlockRegister.FACING));
			for(int i = 0; i < handler.getSlots(); i++)
			{
				ItemStack stack = handler.getStackInSlot(i);
				if (!stack.isEmpty() && !stack.hasCapability(ItemRegisterFluidWrapper.CapabilityRegisterFluidWrapper.REGISTER_FLUID_WRAPPER_CAPABILITY, null))
				{
					InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), stack);
				}
			}
			handler.dumpInventory();
			if (register.getTenderedAmount().compareTo(new BigDecimal(0)) > 0)
			{
				for(ItemStack moneyStack : ItemMoney.getMoneyStackForAmount(register.getTenderedAmount()))
				{
					InventoryHelper.spawnItemStack(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), moneyStack);
				}
				
				register.setTenderedAmount(new BigDecimal(0));
			}
			register.setRegisterStatus(RegisterStatuses.Online);
			register.markDirty();
			world.notifyBlockUpdate(message.pos, world.getBlockState(message.pos), world.getBlockState(message.pos), 3);
			
		}
		
	}
}
