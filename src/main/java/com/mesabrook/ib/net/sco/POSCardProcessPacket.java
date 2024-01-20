package com.mesabrook.ib.net.sco;

import java.math.BigDecimal;

import com.mesabrook.ib.blocks.te.TileEntityRegister;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class POSCardProcessPacket implements IMessage {

	public BlockPos registerPos;
	public String enteredPin;
	public BigDecimal authorizedAmount;
	public BigDecimal cashBack;
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(registerPos.toLong());
		ByteBufUtils.writeUTF8String(buf, enteredPin);
		ByteBufUtils.writeUTF8String(buf, authorizedAmount.toPlainString());
		ByteBufUtils.writeUTF8String(buf, cashBack.toPlainString());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		registerPos = BlockPos.fromLong(buf.readLong());
		enteredPin = ByteBufUtils.readUTF8String(buf);
		authorizedAmount = new BigDecimal(ByteBufUtils.readUTF8String(buf));
		cashBack = new BigDecimal(ByteBufUtils.readUTF8String(buf));
	}
	
	public static class Handler implements IMessageHandler<POSCardProcessPacket, IMessage>
	{
		@Override
		public IMessage onMessage(POSCardProcessPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(POSCardProcessPacket message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.world;
			
			TileEntity te = world.getTileEntity(message.registerPos);
			if (!(te instanceof TileEntityRegister))
			{
				return;
			}
			
			TileEntityRegister register = (TileEntityRegister)te;
			register.performDebitCardProcessing(message.enteredPin, message.authorizedAmount, message.cashBack, player.getUniqueID());
		}
	}
}
