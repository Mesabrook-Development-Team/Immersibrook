package com.mesabrook.ib.cmds;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import com.mesabrook.ib.net.AboutGUIPacket;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
//				TextComponentString modName;
//				modName = new TextComponentString(Reference.MODNAME);
//				modName.getStyle().setColor(TextFormatting.AQUA);
//				modName.getStyle().setBold(true);
//				sender.sendMessage(modName);
//				sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Version " + Reference.VERSION));
//				sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Build Date: " + TextFormatting.BOLD + Reference.BUILD_DATE));
//				sender.sendMessage(new TextComponentString(""));
//				sender.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Developed by RavenholmZombie and CSX8600 for use on the Mesabrook Minecraft server."));
//				sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "https://mesabrook.com"));

				EntityPlayerMP player = (EntityPlayerMP) sender;
				AboutGUIPacket packet = new AboutGUIPacket();
				PacketHandler.INSTANCE.sendTo(packet, player);

//				if((sender instanceof EntityPlayerMP))
//				{
//					AboutGUIPacket packet = new AboutGUIPacket();
//					PacketHandler.INSTANCE.sendToServer(packet);
//				}

			}
			else if("changelog".equals(args[0]))
			{
				sender.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "A link to the changelog has been copied to your Clipboard."));
				StringSelection stringSelection = new StringSelection(Reference.CHANGELOG);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
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
			else if("help".equals(args[0]))
			{
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Valid Sub-Commands:"));
				sender.sendMessage(new TextComponentString(""));
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "about - Shows Immersibrook's version info."));
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "changelog - Shows a link to Immersibrook's changelog."));
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "proxchat on/off - Turn Proximity Chat on or off."));
				sender.sendMessage(new TextComponentString(""));
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "Example Usage: /ib proxchat off"));
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
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"about", "changelog", "help", "proxchat"}) : Collections.emptyList();
	}
}
