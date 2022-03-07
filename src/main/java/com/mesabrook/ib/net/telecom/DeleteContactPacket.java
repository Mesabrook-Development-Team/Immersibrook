package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.util.handlers.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class DeleteContactPacket implements IMessage {

	public EnumHand hand;
	public String guiClassName;
	public UUID contactToDelete;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		hand = EnumHand.values()[buf.readInt()];
		guiClassName = ByteBufUtils.readUTF8String(buf);
		contactToDelete = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) { 
		buf.writeInt(hand.ordinal());
		ByteBufUtils.writeUTF8String(buf, guiClassName);
		buf.writeLong(contactToDelete.getMostSignificantBits());
		buf.writeLong(contactToDelete.getLeastSignificantBits());
	}

	public static class Handler implements IMessageHandler<DeleteContactPacket, IMessage>
	{
		@Override
		public IMessage onMessage(DeleteContactPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(DeleteContactPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			
			ItemStack stack = player.getHeldItem(message.hand);
			ItemPhone.NBTData stackData = ItemPhone.NBTData.getFromItemStack(stack);
			
			if (stackData == null)
			{
				return;
			}
			
			stackData.removeContactByIdentifier(message.contactToDelete);
			stack.setTagCompound(stackData.serializeNBT());
			
			RefreshStackPacket refresh = new RefreshStackPacket();
			refresh.hand = message.hand;
			refresh.newStack = stack;
			refresh.guiClassName = message.guiClassName;
			PacketHandler.INSTANCE.sendTo(refresh, player);
		}
	}
}
