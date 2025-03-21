package com.mesabrook.ib.cmds;

import com.mesabrook.ib.util.handlers.PacketHandler;
import com.mesabrook.ib.net.PacketSendBlocks;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class CommandDumpBlocks extends CommandBase {
    @Override
    public String getName() {
        return "dumpmodblocks";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/dumpmodblocks <modid>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException(TextFormatting.RED + "Invalid usage! Use: " + getUsage(sender));
        }

        if (!(sender instanceof EntityPlayerMP)) {
            throw new CommandException("Only players can use this command!");
        }

        String modid = args[0];
        EntityPlayerMP player = (EntityPlayerMP) sender;

        List<String> blockList = ForgeRegistries.BLOCKS.getValuesCollection().stream()
            .filter(block -> block.getRegistryName() != null && modid.equals(block.getRegistryName().getResourceDomain()))
            .map(block -> block.getRegistryName().toString() + ";" + block.getUnlocalizedName()) // Separate with ;
            .collect(Collectors.toList());

        if (blockList.isEmpty()) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "No blocks found for mod: " + modid));
            return;
        }

        PacketHandler.INSTANCE.sendTo(new PacketSendBlocks(blockList, modid), player);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // Requires admin-level permissions
    }
}
