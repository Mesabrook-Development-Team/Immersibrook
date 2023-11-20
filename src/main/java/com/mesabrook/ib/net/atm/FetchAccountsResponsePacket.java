package com.mesabrook.ib.net.atm;

import java.util.ArrayList;

import com.mesabrook.ib.apimodels.account.Account;
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

public class FetchAccountsResponsePacket implements IMessage {

	public ArrayList<Account> accounts = new ArrayList<>();
	public String error = "";
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		error = tag.getString("error");
		
		NBTTagList accountList = tag.getTagList("accounts", NBT.TAG_COMPOUND);
		for(NBTBase accountTag : accountList)
		{
			NBTTagCompound accountCompound = (NBTTagCompound)accountTag;
			Account account = new Account();
			account.deserializeNBT(accountCompound);
			accounts.add(account);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("error", error);
		
		NBTTagList list = new NBTTagList();
		for(Account account : accounts)
		{
			list.appendTag(account.serializeNBT());
		}
		tag.setTag("accounts", list);
		
		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler implements IMessageHandler<FetchAccountsResponsePacket, IMessage>
	{

		@Override
		public IMessage onMessage(FetchAccountsResponsePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handler(message, ctx));
			return null;
		}

		private void handler(FetchAccountsResponsePacket message, MessageContext ctx) {
			ClientSideHandlers.ATMHandlers.onATMFetchAccountsResponse(message.error, message.accounts);
		}
		
	}
}
