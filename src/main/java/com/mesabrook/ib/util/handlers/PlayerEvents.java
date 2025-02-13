package com.mesabrook.ib.util.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.apimodels.company.EmployeeToDoItem;
import com.mesabrook.ib.apimodels.mesasys.BlockAudit.AuditTypes;
import com.mesabrook.ib.blocks.sco.BlockShelf;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity;
import com.mesabrook.ib.blocks.te.ShelvingTileEntity.ProductSpot;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.IEmployeeCapability;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.ISecuredItem;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModEnchants;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.PotionInit;
import com.mesabrook.ib.items.ItemSponge;
import com.mesabrook.ib.items.ItemTechRetailBox;
import com.mesabrook.ib.items.armor.ItemRespirator;
import com.mesabrook.ib.items.misc.ItemIBFood;
import com.mesabrook.ib.items.misc.ItemPhone;
import com.mesabrook.ib.items.tools.ItemBanHammer;
import com.mesabrook.ib.items.tools.ItemGavel;
import com.mesabrook.ib.items.tools.ItemIceChisel;
import com.mesabrook.ib.net.ClientSoundPacket;
import com.mesabrook.ib.net.FetchCSNotificationPacket;
import com.mesabrook.ib.net.OpenTOSPacket;
import com.mesabrook.ib.net.ServerSoundBroadcastPacket;
import com.mesabrook.ib.telecom.CallManager;
import com.mesabrook.ib.util.ItemRandomizer;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.UniversalDeathSource;
import com.mesabrook.ib.util.apiaccess.BlockAuditQueue;
import com.mesabrook.ib.util.apiaccess.DataAccess;
import com.mesabrook.ib.util.apiaccess.DataAccess.API;
import com.mesabrook.ib.util.apiaccess.DataAccess.AuthenticationStatus;
import com.mesabrook.ib.util.apiaccess.DataRequestQueue;
import com.mesabrook.ib.util.apiaccess.DataRequestTask;
import com.mesabrook.ib.util.apiaccess.GetData;
import com.mesabrook.ib.util.apiaccess.PutData;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.saveData.SpecialDropTrackingData;
import com.mesabrook.ib.util.saveData.TOSData;
import com.mojang.authlib.GameProfile;
import com.pam.harvestcraft.blocks.blocks.BlockPamCake;
import com.pam.harvestcraft.item.items.ItemPamFood;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PlayerEvents 
{
	public static final DamageSource MESO = new UniversalDeathSource("meso", "im.death.asbestos");
	public Random random;
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
		boolean holidayItemsInInventoryOnJoin = w.getGameRules().getBoolean("holidayItemsInInventoryOnJoin");

		if(ModConfig.showWelcome)
		{
			SpecialDropTrackingData dropData = SpecialDropTrackingData.getOrCreate(w);

			if(player instanceof EntityPlayer)
			{
				Triggers.trigger(Triggers.WELCOME, player);
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

			// New web-fetched MOTD. Invert to test in singleplayer.
			if(player.getServer().isSinglePlayer())
			{
				player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "[" + Reference.MODNAME + "]" + TextFormatting.WHITE + " " + Reference.VERSION + " loaded successfully"));
			}
			else
			{
				try
				{
					String url = Reference.MOTD_URL;
					HttpClient httpClient = HttpClients.createDefault();
					HttpGet request = new HttpGet(url);
					HttpResponse response = httpClient.execute(request);

					if (response.getStatusLine().getStatusCode() == 200)
					{
						BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
						StringBuilder content = new StringBuilder();
						String line;
						
						String urlRegex = "(https?://\\S+)";
				        Pattern pattern = Pattern.compile(urlRegex);

				        TextComponentString parent = new TextComponentString("");
						while ((line = reader.readLine()) != null)
						{
							line = line.replace("%p", player.getDisplayNameString());
							content.append(line).append("\n");

					        Matcher matcher = pattern.matcher(line);
					        
					        TextComponentString lineParent = new TextComponentString("");
					        int lastEnd = 0;
					        while (matcher.find()) {
					            int start = matcher.start();
					            int end = matcher.end();
//					            regularTextBuilder.append(input, lastEnd, start);
//					            urlsBuilder.append(input, start, end);
					            TextComponentString lastText = new TextComponentString(line.substring(lastEnd, start));
					            TextComponentString lineURL = new TextComponentString(line.substring(start, end));
					            lineURL.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, lineURL.getUnformattedText()));
					            lineParent.appendSibling(lastText);
					            lineParent.appendSibling(lineURL);
					            lastEnd = end;
					        }
					        lineParent.appendSibling(new TextComponentString(line.substring(lastEnd) + "\n"));
					        parent.appendSibling(lineParent);
						}

						player.sendMessage(parent);
						reader.close();
					}
				}
				catch(Exception ex)
				{
					player.sendMessage(new TextComponentString(TextFormatting.RED + "[IB] Unable to fetch MOTD from remote source. That isn't supposed to happen! :("));
					player.sendMessage(new TextComponentString(TextFormatting.GOLD + "[" + Reference.MODNAME + " " + Reference.VERSION + "]"  + TextFormatting.LIGHT_PURPLE + " " + "Loaded Successfully"));
					ex.printStackTrace();
				}
			}
		}
		
		if (!player.getServer().isSinglePlayer())
		{			
			ResetInactivityParam param = new ResetInactivityParam();
			param.username = player.getDisplayNameString();
			PutData put = new PutData(API.System, "Inactivity/ResetInactivity", param);
			put.setRequireAuthToken(false);
			put.execute();
			
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
			
			createCSNotificationsDataRequest(player, FetchCSNotificationPacket.FetchTypes.InitialLogin);
		}
		
		if (DataAccess.getAuthenticationStatus() == AuthenticationStatus.LoggedOut && player.canUseCommand(2, ""))
		{
			player.sendMessage(new TextComponentString("" + TextFormatting.BOLD + TextFormatting.YELLOW + "WARNING! The server is currently NOT signed into MesaSuite!"));
		}
	}
	
	public static void createCSNotificationsDataRequest(EntityPlayer player, FetchCSNotificationPacket.FetchTypes fetchType)
	{
		GetData get = new GetData(API.Company, "EmployeeIBAccess/GetToDoItemsForUser", EmployeeToDoItem[].class);
		get.addQueryString("username", player.getName());
		
		DataRequestTask requestTask = new DataRequestTask(get);
		requestTask.getData().put("playerID", player.getUniqueID());
		requestTask.getData().put("fetchType", fetchType);
		ServerTickHandler.companyToDoQueryTasks.add(requestTask);
		DataRequestQueue.INSTANCE.addTask(requestTask);
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
		boolean funnyDeathSound = w.getGameRules().getBoolean("funnyDeathSound");
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			if(!w.isRemote)
			{
				GameProfile profile = ((EntityPlayerMP) e).getGameProfile();

				if(funnyDeathSound)
				{
					ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
					packet.pos = e.getPosition();
					packet.soundName = "death";
					PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 100));
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

		if(stack.isEmpty())
		{
			player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, 0.38F, 1.0F);
		}
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
		BlockPos pos = evt.getPos();
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		float pitchFloat;

		// Ice Chisel
		if(stack.getItem() instanceof ItemIceChisel)
		{
			if(!player.isCreative())
			{
				if(block instanceof BlockIce)
				{
					stack.damageItem(5, player);
				}
				else if(block instanceof BlockPackedIce)
				{
					stack.damageItem(10, player);
				}
			}

			if(block instanceof BlockIce)
			{
				player.addItemStackToInventory(new ItemStack(Items.SNOWBALL,1));
				if(!world.isRemote)
				{
					ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
					packet.pos = player.getPosition();
					packet.soundName = "chisel";
					packet.rapidSounds = true;
					PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 15));
				}
			}
			else if(block instanceof BlockPackedIce)
			{
				player.addItemStackToInventory(new ItemStack(Items.SNOWBALL,5));
				if(!world.isRemote)
				{
					ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
					packet.pos = player.getPosition();
					packet.soundName = "chisel";
					packet.rapidSounds = true;
					PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 15));
				}
			}
		}

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
	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock evt) {
		World world = evt.getWorld();
		EntityPlayer player = evt.getEntityPlayer();
		BlockPos pos = evt.getPos();
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		// Cake Particles
		if (block instanceof BlockCake && player.canEat(false) || block instanceof BlockPamCake && player.canEat(false)) {
			ItemStack stack = block.getPickBlock(state, null, world, pos, player);
			Random rand = Main.rand;

			for (int i = 0; i < 5; ++i) {
				Vec3d vec3d = new Vec3d(((double) rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
				vec3d = vec3d.rotatePitch(-player.rotationPitch * 0.017453292F);
				vec3d = vec3d.rotateYaw(-player.rotationYaw * 0.017453292F);
				double d0 = (double) (-rand.nextFloat()) * 0.6D - 0.3D;
				Vec3d vec3d1 = new Vec3d(((double) rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
				vec3d1 = vec3d1.rotatePitch(-player.rotationPitch * 0.017453292F);
				vec3d1 = vec3d1.rotateYaw(-player.rotationYaw * 0.017453292F);
				vec3d1 = vec3d1.add(player.getLookVec());
				player.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05D, vec3d.z, Item.getIdFromItem(stack.getItem()), stack.getMetadata());
				player.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, pos.getX(), pos.getY(), pos.getZ(), vec3d.x, vec3d.y + 0.05D, vec3d.z, Item.getIdFromItem(stack.getItem()), stack.getMetadata());
			}
			world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS, 0.5F + 0.5F * (float) rand.nextInt(2), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
		}

		// Make others hear your fat logs.
		if (block.getUnlocalizedName().contains("toilet") && Loader.isModLoaded("cfm") && ModConfig.letOthersHearYourLogs) {
			if (world.isRemote && player.isSneaking()) {
				ClientSoundPacket packet = new ClientSoundPacket();
				packet.pos = player.getPosition();
				packet.modID = "cfm";
				packet.soundName = "flush";
				packet.useDelay = true;
				packet.range = 10;
				PacketHandler.INSTANCE.sendToServer(packet);
			}
		}

		if(block.getBlockState().getBlock() == ModBlocks.PRISON_TOILET)
		{
			if(player == null || world.isRemote)
			{
				return;
			}

			if(player.isSneaking())
			{
				ItemStack heldItem = player.getHeldItemMainhand();

				if(!heldItem.isEmpty() && !(player instanceof FakePlayer) && !(heldItem.getItem() instanceof ItemPhone) && !(heldItem.getItem() instanceof ItemTechRetailBox))
				{
					heldItem.shrink(heldItem.getCount());
					player.sendMessage(new TextComponentString(TextFormatting.RED + "I hope this doesn't clog the pipes..."));
				}
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
	
	@SubscribeEvent
	public void onPlayerCloneEvent(net.minecraftforge.event.entity.player.PlayerEvent.Clone e)
	{
		if (e.getEntityPlayer() == null || e.getOriginal() == null || !e.isWasDeath())
		{
			return;
		}
		
		IEmployeeCapability oldEmployeeCapability = e.getOriginal().getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		IEmployeeCapability newEmployeeCapability = e.getEntityPlayer().getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
		newEmployeeCapability.setLocationEmployee(oldEmployeeCapability.getLocationEmployee());
	}
	
	@SubscribeEvent
	public void onPlayerUpdateEvent(LivingUpdateEvent e)
	{
		if (e.getEntityLiving().world.isRemote)
		{
			return;
		}

		if (e.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)e.getEntityLiving();
			ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			ItemStack asbestos = player.getHeldItem(player.getActiveHand());
			
			if(asbestos.getItem() == ModItems.ASBESTOS && !(helmet.getItem() instanceof ItemRespirator)) 
			{
				if(player.world.getGameRules().getBoolean("asbestosRealism"))
				{
					player.addPotionEffect(new PotionEffect(PotionInit.ASBESTOS_EFFECT, 100000, 0, true, false));
				}
			}
			else
			{
				if(player.world.rand.nextInt(420) == 10)
				{
					helmet.damageItem(1, player);
				}
			}

			if(player.getActivePotionEffect(PotionInit.ASBESTOS_EFFECT) != null)
			{
				if(player.world.rand.nextInt(10000) < 1)
				{
					player.attackEntityFrom(MESO, player.getHealth());
				}
			}
			
			if (EnchantmentHelper.getEnchantmentLevel(ModEnchants.AUTO_FEED, helmet) > 0)
			{
				autoFeedPlayer(player);
			}

			IEmployeeCapability cap = player.getCapability(CapabilityEmployee.EMPLOYEE_CAPABILITY, null);
			for(ItemStack stack : player.inventory.mainInventory.stream().filter(is -> is.hasCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null)).collect(Collectors.toSet()))
			{
				ISecuredItem securedItem = stack.getCapability(CapabilitySecuredItem.SECURED_ITEM_CAPABILITY, null);
				if (securedItem.getHomeLocation().getY() == -1 || cap.getLocationID() == securedItem.getLocationIDOwner())
				{
					continue;
				}
				
				if (securedItem.getHomeLocation().getDistance((int)player.posX, (int)player.posY, (int)player.posZ) > securedItem.getResetDistance())
				{
					IBlockState state = player.world.getBlockState(securedItem.getHomeLocation());
					if (!(state.getBlock() instanceof BlockShelf))
					{
						continue;
					}
					
					ShelvingTileEntity shelf = (ShelvingTileEntity)player.world.getTileEntity(securedItem.getHomeLocation());
					if (shelf == null)
					{
						continue;
					}
					
					Optional<ProductSpot> spot = Arrays.stream(shelf.getProductSpots()).filter(ps -> ps.getPlacementID() == securedItem.getHomeSpot()).findFirst();
					if (spot == null || !spot.isPresent())
					{
						continue;
					}
					
					ItemStack[] stackArray = spot.get().getItems();
					if (!stackArray[stackArray.length - 1].isEmpty())
					{
						continue;
					}
					
					int indexToFill = -1;
					for(int i = 0; i < stackArray.length; i++)
					{
						if (stackArray[i].isEmpty())
						{
							indexToFill = i;
							break;
						}
					}
					stackArray[indexToFill] = stack.copy();
					stack.shrink(stack.getCount());
					shelf.markDirty();
					player.world.notifyBlockUpdate(shelf.getPos(), state, state, 3);
				}
			}
		}
	}

	private void autoFeedPlayer(EntityPlayer player)
	{
		FoodStats foodStats = player.getFoodStats();
		int foodLevel = foodStats.getFoodLevel();
		int autoFeedThreshold = 20;

		if (foodLevel < autoFeedThreshold)
		{
			ItemStack foodToConsume = findFoodInInventory(player);

			if (!foodToConsume.isEmpty())
			{
				foodStats.addStats((ItemFood) foodToConsume.getItem(), foodToConsume);

				ServerSoundBroadcastPacket packet = new ServerSoundBroadcastPacket();
				packet.pos = player.getPosition();
				packet.modID = "minecraft";
				packet.soundName = "entity.player.burp";
				PacketHandler.INSTANCE.sendToAllAround(packet, new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));

				ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
				helmet.damageItem(1, player);
			}
		}
	}

	private ItemStack findFoodInInventory(EntityPlayer player)
	{
		for (ItemStack stack : player.inventory.mainInventory)
		{
			if (stack.getItem() instanceof ItemFood || stack.getItem() instanceof ItemIBFood || stack.getItem() instanceof ItemPamFood)
			{
				ItemStack foodToConsume = stack.copy();
				stack.shrink(1);
				return foodToConsume;
			}
		}
		return ItemStack.EMPTY;
	}
}
