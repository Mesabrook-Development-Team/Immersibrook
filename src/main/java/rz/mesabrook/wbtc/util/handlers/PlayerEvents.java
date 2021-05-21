package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import rz.mesabrook.wbtc.util.config.ModConfig;

import java.util.Random;

public class PlayerEvents 
{
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent e)
	{
		EntityPlayer player = e.player;
		
		if(ModConfig.showWelcome)
		{
			TextComponentTranslation prefix = new TextComponentTranslation("im.welcome");
			prefix.getStyle().setColor(TextFormatting.LIGHT_PURPLE);
			player.sendMessage(prefix);
		}
	}
}
