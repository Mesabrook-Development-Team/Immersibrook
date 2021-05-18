package rz.mesabrook.wbtc.cmds;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import rz.mesabrook.wbtc.cmds.util.CustomTeleporter;
import rz.mesabrook.wbtc.util.Reference;

public class CommandImmersibrook extends CommandBase
{
	private final List<String> aliases = Lists.newArrayList(Reference.MODID, "immersibrook", "ib", "mesabrook");

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		sender.sendMessage(new TextComponentString(TextFormatting.AQUA + "Immersibrook " + TextFormatting.YELLOW + Reference.VERSION));
		sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Build Date: " + TextFormatting.BOLD + Reference.BUILD_DATE));
	}
	
	@Override
	public String getName()
	{
		return "immersibrook";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "Immersibrook Base Command";
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
		return Collections.emptyList();
	}
}
