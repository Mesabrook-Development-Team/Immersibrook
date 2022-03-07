package com.mesabrook.ib.cmds;

import com.mesabrook.ib.util.saveData.PlayerLoudnessData;
import com.mesabrook.ib.util.saveData.PlayerLoudnessLevel;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTalk extends CommandBase {

	@Override
	public String getName() {
		return "talk";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "im.talk.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1)
		{
			throw new WrongUsageException("Valid subcommands: quietly, normally, loudly, globally", new Object[0]);
		}
		
		PlayerLoudnessLevel level = PlayerLoudnessLevel.getForAlias(args[0]);
		if (level == null)
		{
			throw new WrongUsageException("Valid subcommands: quietly, normally, loudly, globally", new Object[0]);
		}
		
		if (level == PlayerLoudnessLevel.Globally && !sender.canUseCommand(4, getName()))
		{
			throw new CommandException("command.generic.permission", new Object[0]);
		}
		
		PlayerLoudnessData data = PlayerLoudnessData.getOrCreate(sender.getEntityWorld());
		data.setLevelForPlayer(((EntityPlayer)sender).getUniqueID(), level);
		sender.sendMessage(new TextComponentTranslation("im.talk.success", level.toString().toLowerCase()));
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
}
