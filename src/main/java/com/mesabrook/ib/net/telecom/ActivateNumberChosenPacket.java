package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mesabrook.ib.util.saveData.PhoneNumberData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ActivateNumberChosenPacket implements IMessage {

	public int hand;
	public int number;
	
	@Override
	public void fromBytes(ByteBuf buf) {
		hand = buf.readInt();
		number = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand);
		buf.writeInt(number);
	}

	public static class Handler implements IMessageHandler<ActivateNumberChosenPacket, IMessage>
	{

		@Override
		public IMessage onMessage(ActivateNumberChosenPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(ActivateNumberChosenPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			PhoneNumberData phoneData = PhoneNumberData.getOrCreate(world);
			if (!phoneData.consumeNumber(message.number))
			{
				sendNewNumbers(phoneData, player);
			}
			else
			{
				ItemStack stack = player.getHeldItem(EnumHand.values()[message.hand]);
				NBTTagCompound nbt = stack.getTagCompound();
				if (nbt == null)
				{
					nbt = new NBTTagCompound();
					stack.setTagCompound(nbt);
				}
				
				nbt.setInteger(Reference.PHONE_NUMBER_NBTKEY, message.number);
				nbt.setInteger(Reference.BATTERY_LEVEL, ModConfig.smartphoneMaxBattery);
				ActivationCompletePacket complete = new ActivationCompletePacket();
				complete.hand = EnumHand.values()[message.hand];
				PacketHandler.INSTANCE.sendTo(complete, player);
			}
		}
		
		private void sendNewNumbers(PhoneNumberData phoneData, EntityPlayerMP player)
		{
			int[] numbers = new int[5];
			for(int i = 0; i < 5; i++)
			{
				numbers[i] = phoneData.getNewPhoneNumber();
			}
			
			ActivateChooseNumberPacket packet = new ActivateChooseNumberPacket();
			packet.numberChoices = numbers;
			packet.isResend = true;
			PacketHandler.INSTANCE.sendTo(packet, player);
		}
	}
}
