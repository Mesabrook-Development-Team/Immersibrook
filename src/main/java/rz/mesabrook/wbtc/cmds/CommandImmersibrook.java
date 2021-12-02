package rz.mesabrook.wbtc.cmds;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.config.ModConfig;

import java.util.Collections;
import java.util.List;

public class CommandImmersibrook extends CommandBase
{
	private final List<String> aliases = Lists.newArrayList("immersibrook", "ib", "mesabrook", "brook");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(args.length < 1)
		{
			throw new WrongUsageException("im.cmd.error", new Object[0]);
		}
		else
		{
			if("about".equals(args[0]))
			{
				TextComponentString modName;
				modName = new TextComponentString(Reference.MODNAME);
				modName.getStyle().setColor(TextFormatting.AQUA);
				modName.getStyle().setBold(true);
				sender.sendMessage(modName);
				sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Version " + Reference.VERSION));
				sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Build Date: " + TextFormatting.BOLD + Reference.BUILD_DATE));
				sender.sendMessage(new TextComponentString(""));
				sender.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Developed by RavenholmZombie and CSX8600 for use on the Mesabrook Minecraft server."));
				sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "https://mesabrook.com"));
			}
			else if("changelog".equals(args[0]))
			{
				sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "You are using version " + Reference.VERSION));
				sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Click the link below to view the changelog."));

				TextComponentString url;
				url = new TextComponentString(Reference.CHANGELOG);
				url.getStyle().setColor(TextFormatting.AQUA);
				url.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Reference.CHANGELOG));
				sender.sendMessage(url);
			}
			else if("proxchat".equals(args[0]))
			{
				if (!sender.canUseCommand(4, getName()))
				{
					throw new CommandException("command.generic.permission", new Object[0]);
				}
				
				if (args.length < 2 || (!"on".equals(args[1].toLowerCase()) && !"off".equals(args[1].toLowerCase())))
				{
					throw new WrongUsageException("im.cmd.proxchat.usage", new Object[0]);
				}
				
				ModConfig.proximityChatEnabled = "on".equals(args[1].toLowerCase());
				ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
				sender.sendMessage(new TextComponentTranslation("im.cmd.proxchat.set", ModConfig.proximityChatEnabled ? "on" : "off"));
			}
		}
	}
	
	@Override
	public String getName()
	{
		return "immersibrook";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "im.cmd.usage";
	}
	
	@Override
	public List<String> getAliases() 
	{
		return aliases;
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) 
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"about", "changelog"}) : Collections.emptyList();
	}
}
