package com.mesabrook.ib.net;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.handlers.ClientSideHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerSoundBroadcastPacket implements IMessage
{
	public BlockPos pos;
	public String modID = Reference.MODID;
	public String soundName;
	public float volume = 1F;
	public float pitch = 1F;
	public boolean rapidSounds = false;

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		pos = BlockPos.fromLong(buf.readLong());
		modID = ByteBufUtils.readUTF8String(buf);
		soundName = ByteBufUtils.readUTF8String(buf);
		volume = buf.readFloat();
		pitch = buf.readFloat();
		rapidSounds = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeUTF8String(buf, modID);
		ByteBufUtils.writeUTF8String(buf, soundName);
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
		buf.writeBoolean(rapidSounds);
	}
	
	public static void playIBSound(World world, String sound, BlockPos pos)
	{
		playIBSound(world, sound, pos, false);
	}
	
	public static void playIBSound(World world, String sound, BlockPos pos, boolean rapidSounds)
	{
		ServerSoundBroadcastPacket billAcceptorSound = new ServerSoundBroadcastPacket();
		billAcceptorSound.soundName = sound;
		billAcceptorSound.pos = pos;
		billAcceptorSound.rapidSounds = rapidSounds;
		PacketHandler.INSTANCE.sendToAllAround(billAcceptorSound, new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 25));
	}
	
	public static class Handler implements IMessageHandler<ServerSoundBroadcastPacket, IMessage>
	{
		@Override
		public IMessage onMessage(ServerSoundBroadcastPacket message, MessageContext ctx)
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(ServerSoundBroadcastPacket message, MessageContext ctx)
		{
			try
			{
				ClientSideHandlers.playSoundHandler(message, ctx);
			}
			catch(NullPointerException ex)
			{
				Main.logger.error("[" + Reference.MODNAME + "] An error occurred in " + Handler.class.getName());
				Main.logger.error(ex);
				Main.logger.error("[" + Reference.MODNAME + "] Please report this error to us.");
			}
		}
	}
}
