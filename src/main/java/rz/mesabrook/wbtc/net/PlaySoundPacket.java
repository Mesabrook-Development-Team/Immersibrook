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
	public float volume = 1F;
	public float pitch = 1F;

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		pos = BlockPos.fromLong(buf.readLong());
		soundName = ByteBufUtils.readUTF8String(buf);
		volume = buf.readFloat();
		pitch = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeUTF8String(buf, soundName);
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
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
			ClientSideHandlers.playSoundHandler(message, ctx);
		}
	}
}
