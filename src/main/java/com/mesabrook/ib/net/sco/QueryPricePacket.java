package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.TileEntityTaggingStation;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.handlers.ServerTickHandler;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.GetData;

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

public class QueryPricePacket implements IMessage {

	public BlockPos shelfPos;
	public int placementID;
	public ItemStack stack;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(shelfPos.toLong());
		buf.writeInt(placementID);
		ByteBufUtils.writeItemStack(buf, stack);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		shelfPos = BlockPos.fromLong(buf.readLong());
		placementID = buf.readInt();
		stack = ByteBufUtils.readItemStack(buf);
	}
	
	public static class Handler implements IMessageHandler<QueryPricePacket, IMessage>
	{
		@Override
		public IMessage onMessage(QueryPricePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(QueryPricePacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			long locationID = 0;
			
			TileEntity te = world.getTileEntity(message.shelfPos);
			if (te instanceof ShelvingTileEntity)
			{
				ShelvingTileEntity shelf = (ShelvingTileEntity)te;
				locationID = shelf.getLocationIDOwner();
			}
			
			if (te instanceof TileEntityTaggingStation)
			{
				IEmployeeCapability emp = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null); // Tagging stations don't have owners - it's based on player
				if (emp != null)
				{
					locationID = emp.getLocationID();
				}
			}
			
			if (locationID == 0)
			{
				return;
			}
			
			GetData get = new GetData(API.Company, "PriceCheck/GetItem", LocationItem.class);
			get.addQueryString("locationID", Long.toString(locationID));
			get.addQueryString("itemName", message.stack.getDisplayName());
			get.addQueryString("quantity", Integer.toString(message.stack.getCount()));
			
			DataRequestTask task = new DataRequestTask(get);
			task.getData().put("shelfPos", message.shelfPos);
			task.getData().put("placementID", message.placementID);
			task.getData().put("playerID", player.getUniqueID());
			ServerTickHandler.shelfPriceLookupTasks.add(task);
			DataRequestQueue.INSTANCE.addTask(task);
		}
	}
}
