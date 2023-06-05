package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.util.handlers.ClientSideHandlers;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RefreshStackPacket implements IMessage {

	public EnumHand hand;
	public ItemStack newStack;
	public String guiClassName;
	public String nextGuiClassName;
	@Override
	public void fromBytes(ByteBuf buf) {
		hand = EnumHand.values()[buf.readInt()];
		guiClassName = ByteBufUtils.readUTF8String(buf);
		nextGuiClassName = ByteBufUtils.readUTF8String(buf);
		newStack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand.ordinal());
		ByteBufUtils.writeUTF8String(buf, guiClassName);
		if (nextGuiClassName == null)
		{
			ByteBufUtils.writeUTF8String(buf, "");
		}
		else
		{
			ByteBufUtils.writeUTF8String(buf, nextGuiClassName);
		}
		ByteBufUtils.writeItemStack(buf, newStack);
	}

	public static class Handler implements IMessageHandler<RefreshStackPacket, IMessage>
	{

		@Override
		public IMessage onMessage(RefreshStackPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(RefreshStackPacket message, MessageContext ctx)
		{
			String nextGuiClassName = message.nextGuiClassName;
			if (nextGuiClassName == null || nextGuiClassName == "")
			{
				nextGuiClassName = message.guiClassName;
			}
			ClientSideHandlers.TelecomClientHandlers.refreshStack(message.guiClassName, nextGuiClassName, message.newStack, message.hand);
		}
	}
}
