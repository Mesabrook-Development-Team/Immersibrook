package com.mesabrook.ib.cmds;

import com.google.common.collect.Lists;
import com.mesabrook.ib.*;
import com.mesabrook.ib.net.*;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager;
import com.mesabrook.ib.util.*;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.PacketHandler;
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

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.*;
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
				try
				{
					EntityPlayerMP player = (EntityPlayerMP) sender;
//					AboutGUIPacket packet = new AboutGUIPacket();
//					PacketHandler.INSTANCE.sendTo(packet, player);

					player.sendMessage(new TextComponentString("============================================"));
					player.sendMessage(new TextComponentString("Immersibrook"));
					player.sendMessage(new TextComponentString("Mod ID: " + Reference.MODID));
					player.sendMessage(new TextComponentString("Version " + Reference.VERSION));
					player.sendMessage(new TextComponentString(" "));
					player.sendMessage(new TextComponentString("Developed by: RavenholmZombie and CSX8600"));
					player.sendMessage(new TextComponentString("For the Mesabrook modded Minecraft server."));
					player.sendMessage(new TextComponentString(" "));
					player.sendMessage(new TextComponentString("============================================"));
				}
				catch(Exception ex)
				{
					MottoRandomizer.RandomMotto();
					Main.logger.info("");
					Main.logger.info("=============================================================================");
					Main.logger.info("");
					Main.logger.info(Reference.MODNAME);
					Main.logger.info("Version " + Reference.VERSION);
					Main.logger.info("");
					Main.logger.info(Reference.UPDATE_NAME);
					Main.logger.info("");
					Main.logger.info("Developed By: RavenholmZombie and CSX8600");
					Main.logger.info("");
					Main.logger.info(Reference.MOTTO);
					Main.logger.info("");
					Main.logger.info("=============================================================================");
					Main.logger.info("");
				}
			}
			else if("changelog".equals(args[0]))
			{
				try
				{
					EntityPlayerMP player = (EntityPlayerMP) sender;
					CommandProcessorPacket packet = new CommandProcessorPacket();
					PacketHandler.INSTANCE.sendTo(packet, player);
					if(sender instanceof EntityPlayerMP)
					{
						player.sendMessage(new TextComponentString("Opening GitHub release in default browser..."));
					}
				}
				catch(Exception ex)
				{
					Main.logger.info(Reference.CHANGELOG);
				}
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
			else if ("wea".equals(args[0]))
			{
				if (!sender.canUseCommand(4, getName()))
				{
					throw new CommandException("command.generic.permission", new Object[0]);
				}
				
				if (args.length < 2)
				{
					throw new WrongUsageException("im.cmd.wea.usage", new Object[0]);
				}
				
				if ("start".equals(args[1]))
				{
					WirelessEmergencyAlertManager.instance().start();
					sender.sendMessage(new TextComponentString("Start attempted for WEA"));
				}
				else if ("stop".equals(args[1]))
				{
					WirelessEmergencyAlertManager.instance().stop();
					sender.sendMessage(new TextComponentString("Stop requested for WEA"));
				}
				else
				{
					throw new WrongUsageException("im.cmd.wea.usage", new Object[0]);
				}
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
