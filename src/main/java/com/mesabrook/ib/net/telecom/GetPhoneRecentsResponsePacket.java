package com.mesabrook.ib.net.telecom;

import java.util.ArrayList;
import java.util.List;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;
import com.mesabrook.ib.util.saveData.PhoneLogData;

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

public class GetPhoneRecentsResponsePacket implements IMessage {
	public String phoneNumber;
	public List<PhoneLogData.LogData> logDatum;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		phoneNumber = ByteBufUtils.readUTF8String(buf);
		
		logDatum = new ArrayList<>();
		NBTTagCompound listHolder = ByteBufUtils.readTag(buf);
		NBTTagList list = listHolder.getTagList("list", NBT.TAG_COMPOUND);
		for(NBTBase base : list)
		{
			NBTTagCompound tag = (NBTTagCompound)base;
			PhoneLogData.LogData logData = new PhoneLogData.LogData();
			logData.deserializeNBT(tag);
			logDatum.add(logData);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, phoneNumber);
		
		NBTTagCompound listHolder = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for(PhoneLogData.LogData logData : logDatum)
		{
			list.appendTag(logData.serializeNBT());
		}
		listHolder.setTag("list", list);
		ByteBufUtils.writeTag(buf, listHolder);
	}

	public static class Handler implements IMessageHandler<GetPhoneRecentsResponsePacket, IMessage>
	{
		@Override
		public IMessage onMessage(GetPhoneRecentsResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(GetPhoneRecentsResponsePacket message, MessageContext ctx)
		{
			ClientSideHandlers.TelecomClientHandlers.onRecentsRetrieved(message.phoneNumber, message.logDatum);
		}
	}
}
