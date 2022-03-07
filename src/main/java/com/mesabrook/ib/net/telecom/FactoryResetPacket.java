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

public class FactoryResetPacket implements IMessage {

	public int hand;
	public String phoneActivateGuiClassName;

	@Override
	public void fromBytes(ByteBuf buf) {
		hand = buf.readInt();
		phoneActivateGuiClassName = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand);
		ByteBufUtils.writeUTF8String(buf, phoneActivateGuiClassName);
	}
	
	public static class Handler implements IMessageHandler<FactoryResetPacket, IMessage>
	{
		@Override
		public IMessage onMessage(FactoryResetPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(FactoryResetPacket message, MessageContext ctx)
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
			
			NBTData newPhoneData = new NBTData();
			NBTData oldPhoneData = new NBTData();
			oldPhoneData.deserializeNBT(tag);
			newPhoneData.setPhoneNumber(oldPhoneData.getPhoneNumber());
			
			tag = newPhoneData.serializeNBT();			
			phoneStack.setTagCompound(tag);
			
			RefreshStackPacket refresh = new RefreshStackPacket();
			refresh.hand = EnumHand.values()[message.hand];
			refresh.guiClassName = message.phoneActivateGuiClassName;
			refresh.newStack = phoneStack;
			PacketHandler.INSTANCE.sendTo(refresh, player);
		}
	}
}
