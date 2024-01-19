package com.mesabrook.ib.net.sco;

import java.math.BigDecimal;

import com.mesabrook.ib.apimodels.company.LocationItem;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.PostData;
import com.mesabrook.ib.util.handlers.ServerTickHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TaggingStationSetPricePacket implements IMessage {

	public BlockPos tagStationPos;
	public ItemStack stack;
	public BigDecimal price;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(tagStationPos.toLong());
		ByteBufUtils.writeItemStack(buf, stack);
		ByteBufUtils.writeUTF8String(buf, price.toPlainString());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		tagStationPos = BlockPos.fromLong(buf.readLong());
		stack = ByteBufUtils.readItemStack(buf);
		price = new BigDecimal(ByteBufUtils.readUTF8String(buf));
	}
	
	public static class Handler implements IMessageHandler<TaggingStationSetPricePacket, IMessage>
	{
		@Override
		public IMessage onMessage(TaggingStationSetPricePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(TaggingStationSetPricePacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			IEmployeeCapability emp = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			
			PostParameter param = new PostParameter();
			param.LocationID = emp.getLocationID();
			param.ItemName = message.stack.getDisplayName();
			param.Quantity = (short)message.stack.getCount();
			param.Price = message.price;
			
			PostData post = new PostData(API.Company, "LocationItemIBAccess/Post", param, LocationItem.class);
			
			DataRequestTask task = new DataRequestTask(post);
			task.getData().put("playerID", player.getUniqueID());
			task.getData().put("tagStationPos", message.tagStationPos);
			ServerTickHandler.priceSetTasks.add(task);
			DataRequestQueue.INSTANCE.addTask(task);
		}
		
		public static class PostParameter
		{
		    public long LocationID;
		    public String ItemName;
		    public short Quantity;
		    public BigDecimal Price;
		}
	}
}
