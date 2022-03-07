package com.mesabrook.ib.net;

import com.mesabrook.ib.items.misc.PlaqueItemBlock;
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

public class EngravePacket implements IMessage {
	public String awardedTo = "";
	public String awardedFor = "";
	public EnumHand hand;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		awardedTo = ByteBufUtils.readUTF8String(buf);
		awardedFor = ByteBufUtils.readUTF8String(buf);
		hand = EnumHand.values()[buf.readInt()];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, awardedTo);
		ByteBufUtils.writeUTF8String(buf, awardedFor);
		buf.writeInt(hand.ordinal());
	}
	
	public static class Handler implements IMessageHandler<EngravePacket, IMessage>
	{

		@Override
		public IMessage onMessage(EngravePacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(EngravePacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			ItemStack heldStack = player.getHeldItem(message.hand);
			if (!(heldStack.getItem() instanceof PlaqueItemBlock))
			{
				return;
			}
			
			NBTTagCompound compound = heldStack.getTagCompound();
			if (compound == null)
			{
				compound = new NBTTagCompound();
				heldStack.setTagCompound(compound);
			}
			
			compound.setString("awardedTo", message.awardedTo);
			compound.setString("awardedFor", message.awardedFor);
		}
	}
}
