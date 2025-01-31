package com.mesabrook.ib.net;

import java.util.ArrayList;

import com.mesabrook.ib.apimodels.company.EmployeeToDoItem;
import com.mesabrook.ib.net.FetchCSNotificationPacket.FetchTypes;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FetchCSNotificationResponsePacket implements IMessage {

	public ArrayList<EmployeeToDoItem> employeeToDoItems = new ArrayList<>();
	public FetchCSNotificationPacket.FetchTypes fetchType;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound enclosure = ByteBufUtils.readTag(buf);
		NBTTagList list = enclosure.getTagList("list", NBT.TAG_COMPOUND);
		for(NBTBase nbt : list)
		{
			EmployeeToDoItem newItem = new EmployeeToDoItem();
			newItem.deserializeNBT((NBTTagCompound)nbt);
			employeeToDoItems.add(newItem);
		}
		fetchType = FetchTypes.valueOf(enclosure.getString("fetchType"));
		employeeToDoItems.sort(new EmployeeToDoItem.Comparator());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagList tagList = new NBTTagList();
		for(EmployeeToDoItem item : employeeToDoItems)
		{
			tagList.appendTag(item.serializeNBT());
		}
		
		NBTTagCompound enclosure = new NBTTagCompound();
		enclosure.setTag("list", tagList);
		enclosure.setString("fetchType", fetchType.toString());
		
		ByteBufUtils.writeTag(buf, enclosure);
	}

	public static class Handler implements IMessageHandler<FetchCSNotificationResponsePacket, IMessage>
	{
		@Override
		public IMessage onMessage(FetchCSNotificationResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message));
			return null;
		}
		
		private void handle(FetchCSNotificationResponsePacket message)
		{
			ClientSideHandlers.onCSNotificationResponse(message);
		}
	}
}
