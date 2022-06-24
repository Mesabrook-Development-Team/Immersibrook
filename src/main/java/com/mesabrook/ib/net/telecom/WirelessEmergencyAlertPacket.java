package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager.WirelessEmergencyAlert;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WirelessEmergencyAlertPacket implements IMessage {

	public String phoneNumber;
	public WirelessEmergencyAlert alert;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		phoneNumber = ByteBufUtils.readUTF8String(buf);
		alert = new WirelessEmergencyAlert();
		NBTTagCompound alertTag = ByteBufUtils.readTag(buf);
		alert.deserializeNBT(alertTag);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, phoneNumber);
		ByteBufUtils.writeTag(buf, alert.serializeNBT());
	}
	
	public static class Handler implements IMessageHandler<WirelessEmergencyAlertPacket, IMessage>
	{
		@Override
		public IMessage onMessage(WirelessEmergencyAlertPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(WirelessEmergencyAlertPacket message, MessageContext ctx)
		{
			ClientSideHandlers.TelecomClientHandlers.onWirelessEmergencyAlert(message.phoneNumber, message.alert);
		}
	}
}
