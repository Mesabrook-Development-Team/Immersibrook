package com.mesabrook.ib.net.telecom;

import java.util.UUID;

import com.mesabrook.ib.items.misc.ItemPhone.NBTData;
import com.mesabrook.ib.items.misc.ItemPhone.NBTData.SecurityStrategies;
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

public class SecurityStrategySelectedPacket implements IMessage {

	public int hand;
	public int pin;
	public UUID playerID;
	public String guiScreenClassForRefresh;

	@Override
	public void fromBytes(ByteBuf buf) {
		hand = buf.readInt();
		pin = buf.readInt();
		guiScreenClassForRefresh = ByteBufUtils.readUTF8String(buf);
		if (buf.readBoolean())
		{
			playerID = new UUID(buf.readLong(), buf.readLong());
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand);
		buf.writeInt(pin);
		ByteBufUtils.writeUTF8String(buf, guiScreenClassForRefresh);
		buf.writeBoolean(playerID != null);
		if (playerID != null)
		{
			buf.writeLong(playerID.getMostSignificantBits());
			buf.writeLong(playerID.getLeastSignificantBits());
		}
	}

	public static class Handler implements IMessageHandler<SecurityStrategySelectedPacket, IMessage>
	{

		@Override
		public IMessage onMessage(SecurityStrategySelectedPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(SecurityStrategySelectedPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			EnumHand hand = EnumHand.values()[message.hand];
			
			ItemStack phoneStack = player.getHeldItem(hand);
			NBTData data = NBTData.getFromItemStack(phoneStack);
			if (data == null)
			{
				return;
			}
			
			NBTTagCompound stackData = phoneStack.getTagCompound();
			
			data.setPin(message.pin);
			data.setUuid(message.playerID);
			
			if (message.pin != 0)
			{
				data.setSecurityStrategy(SecurityStrategies.PIN);
			}
			else if (message.playerID != null)
			{
				data.setSecurityStrategy(SecurityStrategies.UUID);
			}
			else
			{
				data.setSecurityStrategy(SecurityStrategies.None);
			}
			
			stackData.merge(data.serializeNBT());
//
//			RefreshStackPacket refreshPacket = new RefreshStackPacket();
//			refreshPacket.hand = hand;
//			refreshPacket.newStack = phoneStack;
//			refreshPacket.guiClassName = message.guiScreenClassForRefresh;
//			PacketHandler.INSTANCE.sendTo(refreshPacket, player);
		}
	}
}
