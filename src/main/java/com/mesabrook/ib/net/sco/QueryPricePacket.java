package com.mesabrook.ib.net.sco;

import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
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
			
			TileEntity te = world.getTileEntity(message.shelfPos);
			if (!(te instanceof ShelvingTileEntity))
			{
				return;
			}
			
			ShelvingTileEntity shelf = (ShelvingTileEntity)te;
			
			GetData get = new GetData(API.Company, "PriceCheck/GetItem", LocationItem.class);
			get.addQueryString("locationID", Long.toString(shelf.getLocationIDOwner()));
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
