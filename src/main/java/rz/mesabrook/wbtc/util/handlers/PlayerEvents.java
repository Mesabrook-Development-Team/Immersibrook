package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.config.ModConfig;

import java.util.Random;

public class PlayerEvents 
{
	private final String PREFIX = "-> ";

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent e)
	{
		EntityPlayer player = e.player;

		if(player instanceof EntityPlayer)
		{
			Triggers.trigger(Triggers.WELCOME, player);
		}

		if(ModConfig.showWelcome)
		{
			TextComponentString user = new TextComponentString(TextFormatting.GOLD + player.getDisplayNameString());

			TextComponentTranslation prefix = new TextComponentTranslation("im.welcome");
			prefix.getStyle().setColor(TextFormatting.GOLD);
			prefix.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("im.welcome.disable")));
			player.sendMessage(new TextComponentString(prefix.getFormattedText() + user.getFormattedText() + "!"));

			TextComponentTranslation mesaTitle = new TextComponentTranslation("im.website.title");
			TextComponentTranslation wikiTitle = new TextComponentTranslation("im.wiki.title");
			TextComponentTranslation mapTitle = new TextComponentTranslation("im.map.title");

			mesaTitle.getStyle().setColor(TextFormatting.GREEN);
			wikiTitle.getStyle().setColor(TextFormatting.YELLOW);

			TextComponentString mesaURL = new TextComponentString(PREFIX + TextFormatting.RESET + "https://mesabrook.com");
			TextComponentString dynmap = new TextComponentString(PREFIX + TextFormatting.RESET + "http://map.mesabrook.com");
			TextComponentString wikiURL = new TextComponentString(PREFIX + TextFormatting.RESET +"https://bit.ly/2S2G5Wt");

			mesaURL.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("im.website.hover")));
			wikiURL.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("im.website.hover")));
			dynmap.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("im.website.hover")));

			mesaURL.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mesabrook.com"));
			wikiURL.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://bit.ly/2S2G5Wt"));
			dynmap.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://www.map.mesabrook.com/"));

			player.sendMessage(new TextComponentString(""));
			player.sendMessage(new TextComponentString(mesaTitle.getFormattedText()));
			player.sendMessage(mesaURL);
			player.sendMessage(dynmap);
			player.sendMessage(new TextComponentString(""));
			player.sendMessage(new TextComponentString(wikiTitle.getFormattedText()));
			player.sendMessage(wikiURL);
		}
	}
}
