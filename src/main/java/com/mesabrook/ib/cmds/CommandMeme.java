package com.mesabrook.ib.cmds;

import com.google.common.collect.*;
import com.mesabrook.ib.*;
import com.mesabrook.ib.net.*;
import com.mesabrook.ib.util.*;
import com.mesabrook.ib.util.config.*;
import com.mesabrook.ib.util.handlers.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.network.*;

import java.util.*;

public class CommandMeme extends CommandBase
{
    private final List<String> aliases = Lists.newArrayList("meme");

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length < 1)
        {
            throw new WrongUsageException("im.cmd.meme.error", new Object[0]);
        }
        else
        {
            if("fart".equals(args[0]))
            {
                EntityPlayerMP player = (EntityPlayerMP) sender;
                SoundRandomizer.FartRandomizer(Main.rand);
                ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
                packet.pos = player.getPosition();
                packet.soundName = SoundRandomizer.fartResult;
                packet.rapidSounds = true;
                PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 15));

                player.sendMessage(new TextComponentTranslation("im.cmd.meme.fart"));
            }
            if("cough".equals(args[0]))
            {
                EntityPlayerMP player = (EntityPlayerMP) sender;
                CoughPacket packet = new CoughPacket();
                PacketHandler.INSTANCE.sendTo(packet, player);
                player.sendMessage(new TextComponentTranslation("im.cmd.meme.cough"));
            }
        }
    }

    @Override
    public String getName()
    {
        return "meme";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "im.cmd.meme.usage";
    }

    @Override
    public List<String> getAliases()
    {
        return aliases;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"fart", "cough"}) : Collections.emptyList();
    }
}
