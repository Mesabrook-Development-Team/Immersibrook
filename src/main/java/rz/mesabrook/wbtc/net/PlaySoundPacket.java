package rz.mesabrook.wbtc.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.handlers.ClientSideHandlers;

public class PlaySoundPacket implements IMessage
{
	public BlockPos pos;
	public String soundName;

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeUTF8String(buf, soundName);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		pos = BlockPos.fromLong(buf.readLong());
		ByteBufUtils.readUTF8String(buf);
	}
	
	public static class Handler implements IMessageHandler<PlaySoundPacket, IMessage>
	{
		@Override
		public IMessage onMessage(PlaySoundPacket message, MessageContext ctx) 
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PlaySoundPacket message, MessageContext ctx)
		{
			ClientSideHandlers.handle(message, ctx);
		}
		
	}
}
