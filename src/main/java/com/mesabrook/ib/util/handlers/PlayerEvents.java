package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.tools.ItemBanHammer;
import com.mesabrook.ib.items.tools.ItemGavel;
import com.mesabrook.ib.net.PlaySoundPacket;
import com.mesabrook.ib.util.ItemRandomizer;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.TooltipRandomizer;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.saveData.SpecialDropTrackingData;
import com.mojang.authlib.GameProfile;
import com.pam.harvestcraft.blocks.blocks.BlockPamCake;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.time.LocalDate;
import java.util.Random;

public class PlayerEvents 
{
	private final String PREFIX = "-> ";

	/*
		MOTD
	 */
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent e)
	{
		EntityPlayer player = e.player;
		World w = e.player.world;

		TooltipRandomizer.ChosenTooltip();
		boolean holidayItemsInInventoryOnJoin = w.getGameRules().getBoolean("holidayItemsInInventoryOnJoin");

		if(ModConfig.showWelcome)
		{
			SpecialDropTrackingData dropData = SpecialDropTrackingData.getOrCreate(w);
			
			TextComponentString user = new TextComponentString(TextFormatting.GOLD + player.getDisplayNameString());

			if(player instanceof EntityPlayer)
			{
				Triggers.trigger(Triggers.WELCOME, player);
				if(LocalDate.now().getMonthValue() == 4 && LocalDate.now().getDayOfMonth() == 1)
				{
					SoundRandomizer.RandomizeSound();
					PlaySoundPacket packet = new PlaySoundPacket();
					packet.pos = player.getPosition();
					packet.soundName = "kekw";
					PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
				}

				if(LocalDate.now().getMonthValue() == 10)
				{
					w.playSound(player, player.getPosition(), SoundEvents.ENTITY_WITCH_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
					w.addWeatherEffect(new EntityLightningBolt(player.world, player.posX, player.posY, player.posZ, true));
				}
			}

			if(LocalDate.now().getMonthValue() == 12)
			{
				if(LocalDate.now().getDayOfMonth() <= 25 && LocalDate.now().getDayOfMonth() >= 13)
				{
					SoundRandomizer.RandomizeSound();
					ItemRandomizer.RandomizePresent();
					PlaySoundPacket packet = new PlaySoundPacket();
					packet.pos = player.getPosition();
					packet.soundName = "jingle";
					PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));

					
					if(holidayItemsInInventoryOnJoin && dropData.canGiveChristmasPresent(player.getUniqueID()))
					{
						player.addItemStackToInventory(ItemRandomizer.presentItem);
					}
				}
			}

			if(player.getServer().isSinglePlayer())
			{
				player.sendMessage(new TextComponentString(TextFormatting.GOLD + "[" + Reference.MODNAME + " " + Reference.VERSION + "]"  + TextFormatting.LIGHT_PURPLE + " " + "Loaded Successfully. Welcome " + user.getFormattedText()));
			}
			else
			{
				TextComponentTranslation prefix = new TextComponentTranslation("im.welcome");
				prefix.getStyle().setColor(TextFormatting.GREEN);
				prefix.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("im.welcome.disable")));
				player.sendMessage(new TextComponentString(prefix.getFormattedText() + " " + user.getFormattedText() + "!"));

				if(LocalDate.now().getMonthValue() == Reference.RZ_MONTH && LocalDate.now().getDayOfMonth() == Reference.RZ_DAY)
				{
					player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + "Happy Birthday, RavenholmZombie!"));
					if(player.getGameProfile().getId().equals(Reference.RZ_UUID) && dropData.canGiveCake(Reference.RZ_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.TD_MONTH && LocalDate.now().getDayOfMonth() == Reference.TD_DAY)
				{
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Happy Birthday, TrainDevil!"));
					if(player.getGameProfile().getId().equals(Reference.TD_UUID) && dropData.canGiveCake(Reference.TD_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.TLZ_MONTH && LocalDate.now().getDayOfMonth() == Reference.TLZ_DAY)
				{
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + "Happy Birthday, timelady_zoe!"));
					if(player.getGameProfile().getId().equals(Reference.ZOE_UUID) && dropData.canGiveCake(Reference.ZOE_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.CSX_MONTH && LocalDate.now().getDayOfMonth() == Reference.CSX_DAY)
				{
					player.sendMessage(new TextComponentString(TextFormatting.AQUA + "Happy Birthday, CSX8600!"));
					if(player.getGameProfile().getId().equals(Reference.CSX_UUID) && dropData.canGiveCake(Reference.CSX_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.MD_MONTH && LocalDate.now().getDayOfMonth() == Reference.MD_DAY)
				{
					player.sendMessage(new TextComponentString(TextFormatting.RED + "Happy Birthday, MineDouble!"));
					if(player.getGameProfile().getId().equals(Reference.MD_UUID) && dropData.canGiveCake(Reference.MD_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.SVV_MONTH && LocalDate.now().getDayOfMonth() == Reference.SVV_DAY)
				{
					player.sendMessage(new TextComponentString(TextFormatting.GOLD + "Happy Birthday, StarVicVader!"));
					if(player.getGameProfile().getId().equals(Reference.SVV_UUID) && dropData.canGiveCake(Reference.SVV_UUID))
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
					if(holidayItemsInInventoryOnJoin && dropData.canGiveHalloweenPresent(player.getUniqueID()))
					{
						player.addItemStackToInventory(new ItemStack(ModItems.LOLIPOP_RED, 1));
					}
					if(!w.isRemote)
					{
						PlaySoundPacket packet = new PlaySoundPacket();
						packet.pos = player.getPosition();
						packet.modID = "minecraft";
						packet.soundName = "record.11";
						PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
					}
				}
				if(LocalDate.now().getMonthValue() == 12 && LocalDate.now().getDayOfMonth() == 24 || LocalDate.now().getMonthValue() == 12 && LocalDate.now().getDayOfMonth() == 25)
				{
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Happy Holidays!"));
					if(!w.isRemote)
					{
						PlaySoundPacket packet = new PlaySoundPacket();
						packet.pos = player.getPosition();
						packet.soundName = "jingles";
						PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
					}
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
	}

	/*
		Player Death Events for Server Staff Members.
	 */
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event)
	{
		Entity e = event.getEntity();
		World w = event.getEntity().world;
		boolean specialDrops = w.getGameRules().getBoolean("specialDrops");
		boolean forbidCannibalism = w.getGameRules().getBoolean("forbidCannibalism");
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			if(!w.isRemote)
			{
				GameProfile profile = ((EntityPlayerMP) e).getGameProfile();

				if(ModConfig.oofDeathSound)
				{
					PlaySoundPacket packet = new PlaySoundPacket();
					packet.pos = e.getPosition();
					packet.soundName = "death";
					PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(e.dimension, e.posX, e.posY, e.posZ, Integer.MAX_VALUE));
				}

				if(!forbidCannibalism)
				{
					w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.PLAYER_MEAT, 5)));
				}

				if(specialDrops)
				{
					if(profile != null && Reference.RZ_UUID.equals(profile.getId()))
					{
						w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.RAVEN_BAR, 1)));
					}
					if(profile != null && Reference.CSX_UUID.equals(profile.getId()))
					{
						w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.IRW_VEST, 1)));
					}
					if(profile != null && Reference.TD_UUID.equals(profile.getId()))
					{
						w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.SERPENT_BAR, 1)));
					}
					if(profile != null && Reference.ZOE_UUID.equals(profile.getId()))
					{
						w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(Items.EMERALD, 1)));
					}
					if(profile != null && Reference.MD_UUID.equals(profile.getId()))
					{
						w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.FIRE_HELMET_WHITE, 1)));
					}
					if(profile != null && Reference.SVV_UUID.equals(profile.getId()))
					{
						w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(Items.FIRE_CHARGE, 1)));
					}
					if(profile != null && Reference.LW_UUID.equals(profile.getId()))
					{
						w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(Items.STICK, 1)));
					}
					if(profile != null && Reference.SLOOSE_UUID.equals(profile.getId()))
					{
						if(Loader.isModLoaded("opencomputers"))
						{
							w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("opencomputers", "keyboard")))));
						}
						else
						{
							w.spawnEntity(new EntityItem(w, e.posX, e.posY, e.posZ, new ItemStack(ModItems.ALMOND_WATER, 1)));
						}
					}
				}
			}
		}
	}

	/*
    	Player Left Click Events.
 	*/
	@SubscribeEvent
	public void LeftClickEmpty(PlayerInteractEvent.LeftClickEmpty evt)
	{
		EntityPlayer player = evt.getEntityPlayer();
		EnumHand hand = evt.getHand();
		World w = evt.getWorld();
		ItemStack stack = player.getHeldItem(hand);

		if(stack.getItem() instanceof ItemGavel || stack.getItem() instanceof ItemBanHammer)
		{
			player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
		}
	}
	@SubscribeEvent
	public void LeftClickBlock(PlayerInteractEvent.LeftClickBlock evt)
	{
		EntityPlayer player = evt.getEntityPlayer();
		EnumHand hand = evt.getHand();
		World world = evt.getWorld();
		ItemStack stack = player.getHeldItem(hand);

		// Gavel Bang Sound
		if(stack.getItem() instanceof ItemGavel)
		{
			if(!world.isRemote)
			{
				PlaySoundPacket packet = new PlaySoundPacket();
				packet.pos = player.getPosition();
				packet.soundName = "gavel";
				PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 15));
			}
			if(!player.isCreative())
			{
				stack.damageItem(1, player);
			}
		}

		// Ban Hammer Sound
		if(stack.getItem() instanceof ItemBanHammer)
		{
			NBTTagCompound tag = stack.getTagCompound();
			if(!world.isRemote)
			{
				if(tag != null)
				{
					if(tag.hasKey("sndID"))
					{
						PlaySoundPacket packet = new PlaySoundPacket();
						packet.pos = player.getPosition();
						packet.soundName = tag.getString("sndID");
						PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
					}
				}
			}
		}
	}

	/*
		This block of code is responsible for making cakes produce particles and eating sounds when right-clicked.
	 */
	@SubscribeEvent
	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock evt)
	{
		World world = evt.getWorld();
		EntityPlayer player = evt.getEntityPlayer();
		BlockPos pos = evt.getPos();
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (block instanceof BlockCake && player.canEat(false) || block instanceof BlockPamCake && player.canEat(false))
		{
			ItemStack stack = block.getPickBlock(state, null, world, pos, player);
			Random rand = Main.rand;

			for (int i = 0; i < 5; ++i)
			{
				Vec3d vec3d = new Vec3d(((double)rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
				vec3d = vec3d.rotatePitch(-player.rotationPitch * 0.017453292F);
				vec3d = vec3d.rotateYaw(-player.rotationYaw * 0.017453292F);
				double d0 = (double)(-rand.nextFloat()) * 0.6D - 0.3D;
				Vec3d vec3d1 = new Vec3d(((double)rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
				vec3d1 = vec3d1.rotatePitch(-player.rotationPitch * 0.017453292F);
				vec3d1 = vec3d1.rotateYaw(-player.rotationYaw * 0.017453292F);
				vec3d1 = vec3d1.add(player.getLookVec());
				player.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05D, vec3d.z, Item.getIdFromItem(stack.getItem()), stack.getMetadata());
				player.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX(), pos.getY(), pos.getZ(), vec3d.x, vec3d.y + 0.05D, vec3d.z, Item.getIdFromItem(stack.getItem()), stack.getMetadata());
			}
			world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS, 0.5F + 0.5F * (float)rand.nextInt(2), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
		}
	}
}
