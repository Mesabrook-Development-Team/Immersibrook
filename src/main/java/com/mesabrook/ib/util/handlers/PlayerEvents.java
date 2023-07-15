package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.init.ModEnchants;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.items.*;
import com.mesabrook.ib.items.misc.*;
import com.mesabrook.ib.items.tools.ItemBanHammer;
import com.mesabrook.ib.items.tools.ItemGavel;
import com.mesabrook.ib.net.*;
import com.mesabrook.ib.net.telecom.*;
import com.mesabrook.ib.telecom.*;
import com.mesabrook.ib.util.ItemRandomizer;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.TooltipRandomizer;
import com.mesabrook.ib.util.apiaccess.PutData;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.saveData.SpecialDropTrackingData;
import com.mesabrook.ib.util.saveData.TOSData;
import com.mojang.authlib.GameProfile;
import com.pam.harvestcraft.blocks.blocks.BlockPamCake;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.time.LocalDate;
import java.util.Random;

public class PlayerEvents 
{
	private final String PREFIX = "-> ";

	public PlayerEvents()
	{
		Main.logger.info("[" + Reference.MODNAME + "] Registering PlayerEvents class...");
	}

	/*
		MOTD
	 */
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent e)
	{		
		EntityPlayer player = e.player;
		World w = e.player.world;
		TextComponentString birthdayMessage = new TextComponentString("Happy Birthday, ");
		birthdayMessage.getStyle().setColor(TextFormatting.GREEN);
		birthdayMessage.getStyle().setBold(true);

		TooltipRandomizer.ChosenTooltip();
		boolean holidayItemsInInventoryOnJoin = w.getGameRules().getBoolean("holidayItemsInInventoryOnJoin");

		if(ModConfig.showWelcome)
		{
			SpecialDropTrackingData dropData = SpecialDropTrackingData.getOrCreate(w);
			
			TextComponentString user = new TextComponentString(TextFormatting.AQUA + player.getDisplayNameString());

			if(player instanceof EntityPlayer)
			{
				Triggers.trigger(Triggers.WELCOME, player);
				if(LocalDate.now().getMonthValue() == 4 && LocalDate.now().getDayOfMonth() == 1)
				{
					SoundRandomizer.RandomizeSound();
					ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
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
					ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
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
				prefix.getStyle().setColor(TextFormatting.BLUE);
				prefix.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("im.welcome.disable")));
				player.sendMessage(new TextComponentString(prefix.getFormattedText() + " " + user.getFormattedText() + "!"));

				if(LocalDate.now().getMonthValue() == Reference.RZ_MONTH && LocalDate.now().getDayOfMonth() == Reference.RZ_DAY)
				{
					birthdayMessage.getStyle().setColor(TextFormatting.GOLD);
					player.sendStatusMessage(new TextComponentString(birthdayMessage.getFormattedText() + TextFormatting.DARK_GREEN + "RavenholmZombie"), true);

					if(player.getGameProfile().getId().equals(Reference.RZ_UUID) && dropData.canGiveCake(Reference.RZ_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.TLZ_MONTH && LocalDate.now().getDayOfMonth() == Reference.TLZ_DAY)
				{
					birthdayMessage.getStyle().setColor(TextFormatting.DARK_PURPLE);
					player.sendStatusMessage(new TextComponentString(birthdayMessage.getFormattedText() + TextFormatting.LIGHT_PURPLE + "timelady_zoe"), true);

					if(player.getGameProfile().getId().equals(Reference.ZOE_UUID) && dropData.canGiveCake(Reference.ZOE_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.CSX_MONTH && LocalDate.now().getDayOfMonth() == Reference.CSX_DAY)
				{
					birthdayMessage.getStyle().setColor(TextFormatting.BLUE);
					player.sendStatusMessage(new TextComponentString(birthdayMessage.getFormattedText() + TextFormatting.GREEN + "CSX8600"), true);

					if(player.getGameProfile().getId().equals(Reference.CSX_UUID) && dropData.canGiveCake(Reference.CSX_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.MD_MONTH && LocalDate.now().getDayOfMonth() == Reference.MD_DAY)
				{
					birthdayMessage.getStyle().setColor(TextFormatting.BLUE);
					player.sendStatusMessage(new TextComponentString(birthdayMessage.getFormattedText() + TextFormatting.RED + "MineDouble"), true);

					if(player.getGameProfile().getId().equals(Reference.MD_UUID) && dropData.canGiveCake(Reference.MD_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.SVV_MONTH && LocalDate.now().getDayOfMonth() == Reference.SVV_DAY)
				{
					birthdayMessage.getStyle().setColor(TextFormatting.GREEN);
					player.sendStatusMessage(new TextComponentString(birthdayMessage.getFormattedText() + TextFormatting.YELLOW + "StarVicVader"), true);

					if(player.getGameProfile().getId().equals(Reference.SVV_UUID) && dropData.canGiveCake(Reference.SVV_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.RZ_MONTH && LocalDate.now().getDayOfMonth() == Reference.SLOOSE_DAY)
				{
					birthdayMessage.getStyle().setColor(TextFormatting.GREEN);
					player.sendStatusMessage(new TextComponentString(birthdayMessage.getFormattedText() + TextFormatting.YELLOW + "sloosecannon"), true);

					if(player.getGameProfile().getId().equals(Reference.SLOOSE_UUID) && dropData.canGiveCake(Reference.SLOOSE_UUID))
					{
						player.addItemStackToInventory(new ItemStack(Items.CAKE, 1));
					}
				}
				if(LocalDate.now().getMonthValue() == Reference.BAG_MONTH && LocalDate.now().getDayOfMonth() == Reference.BAG_DAY)
				{
					birthdayMessage.getStyle().setColor(TextFormatting.GREEN);
					player.sendStatusMessage(new TextComponentString(birthdayMessage.getFormattedText() + TextFormatting.DARK_GRAY + "Bagheera!"), true);
					player.sendStatusMessage(new TextComponentString(TextFormatting.WHITE + "RavenholmZombie's cat!"), true);
				}
				if(LocalDate.now().getMonthValue() == Reference.BB_MONTH && LocalDate.now().getDayOfMonth() == Reference.BB_DAY)
				{
					birthdayMessage.getStyle().setColor(TextFormatting.GREEN);
					player.sendStatusMessage(new TextComponentString(birthdayMessage.getFormattedText() + TextFormatting.DARK_GRAY + "Boo and Bubbles!"), true);
					player.sendStatusMessage(new TextComponentString(TextFormatting.WHITE + "RavenholmZombie's cats!"), true);
					player.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + "Happy Halloween!"), true);

					if(holidayItemsInInventoryOnJoin && dropData.canGiveHalloweenPresent(player.getUniqueID()))
					{
						ItemRandomizer.HalloweenItemRandomizer();
						player.addItemStackToInventory(ItemRandomizer.halloweenItem);
					}
					if(!w.isRemote)
					{
						ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
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
						ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
						packet.pos = player.getPosition();
						packet.soundName = "jingles";
						PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
					}
				}

				TextComponentTranslation mesaTitle = new TextComponentTranslation("im.website.title");
				TextComponentTranslation wikiTitle = new TextComponentTranslation("im.wiki.title");
				TextComponentTranslation mapTitle = new TextComponentTranslation("im.map.title");

				mesaTitle.getStyle().setColor(TextFormatting.AQUA);
				wikiTitle.getStyle().setColor(TextFormatting.AQUA);

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
		
		if (!player.getServer().isSinglePlayer())
		{			
			ResetInactivityParam param = new ResetInactivityParam();
			param.username = player.getDisplayNameString();
			PutData put = new PutData(API.System, "Inactivity/ResetInactivity", param);
			put.executeNoResult();
		}
		
		if (!player.world.isRemote)
		{
			TOSData tos = (TOSData)player.world.loadData(TOSData.class, Reference.TOS_DATA_NAME);
			if (tos == null)
			{
				tos = new TOSData(Reference.TOS_DATA_NAME);
				player.world.setData(Reference.TOS_DATA_NAME, tos);
			}
			
			if (!tos.containsPlayer(player.getUniqueID()))
			{
				PacketHandler.INSTANCE.sendTo(new OpenTOSPacket(), FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(player.getUniqueID()));
			}
		}
	}
	
	private class ResetInactivityParam
	{
		public String username = "";
		public String reason = "Logged into the Mesabrook server"; 
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
					ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
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

		player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, 0.38F, 1.0F);
	}

	/*
		Left Click Block Events
	 */
	@SubscribeEvent
	public void LeftClickBlock(PlayerInteractEvent.LeftClickBlock evt)
	{
		EntityPlayer player = evt.getEntityPlayer();
		EnumHand hand = evt.getHand();
		World world = evt.getWorld();
		ItemStack stack = player.getHeldItem(hand);
		float pitchFloat;

		// Gavel Bang Sound
		if(stack.getItem() instanceof ItemGavel)
		{
			if(!world.isRemote)
			{
				ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
				packet.pos = player.getPosition();
				packet.soundName = "gavel";
				packet.rapidSounds = true;
				PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 15));
			}
			if(!player.isCreative())
			{
				stack.damageItem(1, player);
			}
		}

		// Sponge sound
		if(stack.getItem() instanceof ItemSponge)
		{
			if(!world.isRemote)
			{
				if(!world.isRemote)
				{
					ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
					packet.pos = player.getPosition();
					packet.soundName = "sponge_equip";
					packet.rapidSounds = true;
					PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 15));
				}
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
					if(EnchantmentHelper.getEnchantmentLevel(ModEnchants.RANDOM, stack) > 0)
					{
						Random rand = new Random();
						float randPitch = 0.5F + rand.nextFloat();

						if(randPitch > 1.25F) {randPitch = 1.25F;}
						else if(randPitch < 0.25F) {randPitch = 0.75F;}

						pitchFloat = randPitch;
					}
					else
					{
						pitchFloat = 1.0F;
					}

					if(tag.hasKey("sndID"))
					{
						ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
						packet.pos = player.getPosition();
						packet.soundName = tag.getString("sndID");
						packet.pitch = pitchFloat;
						PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
					}
				}
			}
		}
	}

	/*
		Block Right Click Events.
	 */
	@SubscribeEvent
	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock evt)
	{
		World world = evt.getWorld();
		EntityPlayer player = evt.getEntityPlayer();
		BlockPos pos = evt.getPos();
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		// Cake Particles
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

		// Make others hear your fat logs.
		if(block.getUnlocalizedName().contains("toilet") && Loader.isModLoaded("cfm") && ModConfig.letOthersHearYourLogs)
		{
			if(world.isRemote && player.isSneaking())
			{
				ClientSoundPacket packet = new ClientSoundPacket();
				packet.pos = player.getPosition();
				packet.modID = "cfm";
				packet.soundName = "flush";
				packet.useDelay = true;
				packet.range = 10;
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}
	}

	@SubscribeEvent
	public void PlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		EntityPlayer player = event.player;
		for (int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			ItemStack phoneStack = player.inventory.getStackInSlot(i);
			if (!(phoneStack.getItem() instanceof ItemPhone))
			{
				continue;
			}

			NBTTagCompound tag = phoneStack.getTagCompound();
			ItemPhone.NBTData stackData = new ItemPhone.NBTData();
			stackData.deserializeNBT(tag);

			String phoneNumber = stackData.getPhoneNumberString();
			if(phoneNumber == null)
			{
				return;
			}

			try
			{
				CallManager manager = CallManager.instance();
				CallManager.Call callToDisconnect = manager.getCall(phoneNumber);

				if(callToDisconnect == null)
				{
					return;
				}

				callToDisconnect.disconnectDest(phoneNumber);
			}
			catch(Exception ex)
			{
				player.sendMessage(new TextComponentString(ex.getMessage()));
			}
		}
	}
}
