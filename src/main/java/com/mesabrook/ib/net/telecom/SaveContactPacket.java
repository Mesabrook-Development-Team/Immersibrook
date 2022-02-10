package com.mesabrook.ib.net.telecom;

import java.util.UUID;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData.Contact;
import com.mesabrook.ib.util.handlers.PacketHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SaveContactPacket implements IMessage {

	public ItemPhone.NBTData.Contact contact;
	public EnumHand hand;
	public String guiClassName;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		hand = EnumHand.values()[buf.readInt()];
		guiClassName = ByteBufUtils.readUTF8String(buf);
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		contact = new Contact();
		contact.deserializeNBT(compound);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand.ordinal());
		ByteBufUtils.writeUTF8String(buf, guiClassName);
		NBTTagCompound compound = contact.serializeNBT();
		ByteBufUtils.writeTag(buf, compound);
	}

	public static class Handler implements IMessageHandler<SaveContactPacket, IMessage>
	{
		@Override
		public IMessage onMessage(SaveContactPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(SaveContactPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			
			ItemStack stack = player.getHeldItem(message.hand);
			if (!(stack.getItem() instanceof ItemPhone))
			{
				return;
			}
			
			ItemPhone.NBTData phoneStackData = ItemPhone.NBTData.getFromItemStack(stack);
			if (message.contact.getIdentifier() == null || !phoneStackData.getContacts().containsKey(message.contact.getIdentifier()))
			{
				message.contact.setIdentifier(UUID.randomUUID());
				phoneStackData.getContacts().put(message.contact.getIdentifier(), message.contact);
			}
			else
			{
				ItemPhone.NBTData.Contact existingContact = phoneStackData.getContacts().get(message.contact.getIdentifier());
				existingContact.copyFrom(message.contact);
			}
			
			stack.setTagCompound(phoneStackData.serializeNBT());
			
			RefreshStackPacket refresh = new RefreshStackPacket();
			refresh.hand = message.hand;
			refresh.newStack = stack;
			refresh.guiClassName = message.guiClassName;
			PacketHandler.INSTANCE.sendTo(refresh, player);
		}
	}
}
