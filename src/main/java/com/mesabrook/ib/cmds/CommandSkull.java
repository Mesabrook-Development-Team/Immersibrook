package com.mesabrook.ib.cmds;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandSkull  extends CommandBase
{
	private final List<String> aliases = Lists.newArrayList("skull", "head", "givehead");
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
        if (args.length != 1) 
        {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: " + getUsage(sender)));
            return;
        }
        
        String playerName = args[0];
        
        EntityPlayerMP playerMP = getCommandSenderAsPlayer(sender);
        if (playerMP != null)
        {
        	ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
        	skull.setTagCompound(new NBTTagCompound());
        	skull.getTagCompound().setString("SkullOwner", playerName);
        	
        	if (!playerMP.inventory.addItemStackToInventory(skull)) 
        	{
                playerMP.dropItem(skull, false);
            }
        	
        	sender.sendMessage(new TextComponentString("Skull of player " + playerName + " given to " + playerMP.getName()));
        }
        else
        {
        	sender.sendMessage(new TextComponentString(TextFormatting.RED + "Only a player can invoke this command."));
        	return;
        }
	}

	@Override
	public String getName() 
	{
		return "skull";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "/skull <player name> | Example: /skull Dinnerbone";
	}
	
	@Override
	public List<String> getAliases() 
	{
		return aliases;
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 1;
    }
}
