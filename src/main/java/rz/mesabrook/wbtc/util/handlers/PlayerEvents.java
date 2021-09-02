package rz.mesabrook.wbtc.util.handlers;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.net.PlaySoundPacket;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.config.ModConfig;

import java.sql.Ref;
import java.time.LocalDate;
import java.util.UUID;

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
			prefix.getStyle().setColor(TextFormatting.GREEN);
			prefix.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("im.welcome.disable")));
			player.sendMessage(new TextComponentString(prefix.getFormattedText() + " " + user.getFormattedText() + "!"));
			if(LocalDate.now().getMonthValue() == Reference.RZ_MONTH && LocalDate.now().getDayOfMonth() == Reference.RZ_DAY)
			{
				player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Happy Birthday, RavenholmZombie!"));
				if(player.getGameProfile().getId().equals(Reference.RZ_UUID))
				{
					player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
				}
			}
			if(LocalDate.now().getMonthValue() == Reference.TD_MONTH && LocalDate.now().getDayOfMonth() == Reference.TD_DAY)
			{
				player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Happy Birthday, TrainDevil!"));
				if(player.getGameProfile().getId().equals(Reference.TD_UUID))
				{
					player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
				}
			}
			if(LocalDate.now().getMonthValue() == Reference.TLZ_MONTH && LocalDate.now().getDayOfMonth() == Reference.TLZ_DAY)
			{
				player.sendMessage(new TextComponentString(TextFormatting.BLUE + "Happy Birthday, timelady_zoe!"));
				if(player.getGameProfile().getId().equals(Reference.ZOE_UUID))
				{
					player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
				}
			}
			if(LocalDate.now().getMonthValue() == Reference.CSX_MONTH && LocalDate.now().getDayOfMonth() == Reference.CSX_DAY)
			{
				player.sendMessage(new TextComponentString(TextFormatting.AQUA + "Happy Birthday, CSX8600!"));
				if(player.getGameProfile().getId().equals(Reference.CSX_UUID))
				{
					player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
				}
			}
			if(LocalDate.now().getMonthValue() == Reference.MD_MONTH && LocalDate.now().getDayOfMonth() == Reference.MD_DAY)
			{
				player.sendMessage(new TextComponentString(TextFormatting.RED + "Happy Birthday, MineDouble!"));
				if(player.getGameProfile().getId().equals(Reference.MD_UUID))
				{
					player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
				}
			}
			if(LocalDate.now().getMonthValue() == Reference.SVV_MONTH && LocalDate.now().getDayOfMonth() == Reference.SVV_DAY)
			{
				player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Happy Birthday, StarVicVader!"));
				if(player.getGameProfile().getId().equals(Reference.SVV_UUID))
				{
					player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
				}
			}
			if(LocalDate.now().getMonthValue() == Reference.BAG_MONTH && LocalDate.now().getDayOfMonth() == Reference.BAG_DAY)
			{
				player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Happy Birthday to Bagheera, RavenholmZombie's cat!"));
			}
			if(LocalDate.now().getMonthValue() == Reference.BB_MONTH && LocalDate.now().getDayOfMonth() == Reference.BB_DAY)
			{
				player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Happy Birthday to Boo & Bubbles, RavenholmZombie's cats!"));
				player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Happy Halloween!"));
			}

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

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event)
	{
		Entity e = event.getEntity();
		World w = event.getEntity().world;
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			if(!w.isRemote)
			{
				GameProfile profile = ((EntityPlayerMP) e).getGameProfile();

				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = e.getPosition();
				packet.soundName = "oof";
				PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 25));

				if(profile != null && Reference.RZ_UUID.equals(profile.getId()))
				{
					Main.logger.info("RavenholmZombie just got assassinated or died :(");
					w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.RAVEN_BAR, 3)));
				}

				if(profile != null && Reference.CSX_UUID.equals(profile.getId()))
				{
					Main.logger.info("CSX8600 just got assassinated or died :(");
					w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.IRW_VEST, 1)));
				}

				if(profile != null && Reference.TD_UUID.equals(profile.getId()))
				{
					Main.logger.info("TrainDevil just got assassinated or died :(");
					w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.SERPENT_BAR, 1)));
				}

				if(profile != null && Reference.ZOE_UUID.equals(profile.getId()))
				{
					Main.logger.info("timelady_zoe just got assassinated or died :(");
					w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(Items.EMERALD, 1)));
				}

				if(profile != null && Reference.MD_UUID.equals(profile.getId()))
				{
					Main.logger.info("MineDouble just got assassinated or died :(");
					w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(Items.WATER_BUCKET, 1)));
				}

				if(profile != null && Reference.SVV_UUID.equals(profile.getId()))
				{
					Main.logger.info("StarVicVader just got assassinated or died :(");
					w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(Items.FIRE_CHARGE, 1)));
				}

				if(profile != null && Reference.LW_UUID.equals(profile.getId()))
				{
					Main.logger.info("lilWeece just got assassinated or died :(");
					w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(Items.STICK, 1)));
				}
			}
		}
	}
}
