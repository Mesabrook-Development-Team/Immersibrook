package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
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

public class PersonalizationPacket implements IMessage {

	public int hand;
	public int homeBackground;
	public int lockBackground;
	public int chatTone;
	public int ringTone;
	public String guiClassName;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		hand = buf.readInt();
		homeBackground = buf.readInt();
		lockBackground = buf.readInt();
		chatTone = buf.readInt();
		ringTone = buf.readInt();
		guiClassName = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand);
		buf.writeInt(homeBackground);
		buf.writeInt(lockBackground);
		buf.writeInt(chatTone);
		buf.writeInt(ringTone);
		ByteBufUtils.writeUTF8String(buf, guiClassName);
	}

	public static class Handler implements IMessageHandler<PersonalizationPacket, IMessage>
	{
		@Override
		public IMessage onMessage(PersonalizationPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PersonalizationPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			ItemStack phoneStack = player.getHeldItem(EnumHand.values()[message.hand]);
			if (!(phoneStack.getItem() instanceof ItemPhone))
			{
				return;
			}
			
			NBTTagCompound tag = phoneStack.getTagCompound();
			if (tag == null)
			{
				tag = new NBTTagCompound();
				phoneStack.setTagCompound(tag);
			}
			
			NBTData phoneData = new NBTData();
			phoneData.deserializeNBT(tag);
			
			phoneData.setHomeBackground(message.homeBackground);
			phoneData.setLockBackground(message.lockBackground);
			phoneData.setChatTone(message.chatTone);
			phoneData.setRingTone(message.ringTone);
			
			tag = phoneData.serializeNBT();
			phoneStack.setTagCompound(tag);
			
			RefreshStackPacket refresh = new RefreshStackPacket();
			refresh.hand = EnumHand.values()[message.hand];
			refresh.guiClassName = message.guiClassName;
			refresh.newStack = phoneStack;
			PacketHandler.INSTANCE.sendTo(refresh, player);
		}
	}
}
