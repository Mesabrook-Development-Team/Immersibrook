package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.capability.debitcard.CapabilityDebitCard;
import com.mesabrook.ib.capability.debitcard.CapabilityDebitCardItemProvider;
import com.mesabrook.ib.capability.employee.CapabilityEmployee;
import com.mesabrook.ib.capability.employee.CapabilityEmployeePlayerProvider;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItem;
import com.mesabrook.ib.capability.secureditem.CapabilitySecuredItemProvider;
import com.mesabrook.ib.capability.wallet.ItemWalletCapabilityProvider;
import com.mesabrook.ib.cdm.apps.CompanyStudioLiteApp;
import com.mesabrook.ib.cdm.apps.GovernmentPortalLiteApp;
import com.mesabrook.ib.cdm.apps.MesaMailApp;
import com.mesabrook.ib.cdm.apps.TestApp;
import com.mesabrook.ib.cmds.CommandImmersibrook;
import com.mesabrook.ib.cmds.CommandMeme;
import com.mesabrook.ib.cmds.CommandSkull;
import com.mesabrook.ib.cmds.CommandTalk;
import com.mesabrook.ib.entity.EntityMesabrookM;
import com.mesabrook.ib.entity.EntityWineBottle;
import com.mesabrook.ib.events.SeatEvent;
import com.mesabrook.ib.init.CDMApps;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.init.XNetAPI;
import com.mesabrook.ib.items.commerce.ItemDebitCard;
import com.mesabrook.ib.items.commerce.ItemRegisterFluidWrapper;
import com.mesabrook.ib.items.commerce.ItemSecurityBox;
import com.mesabrook.ib.items.commerce.ItemShoppingBasket;
import com.mesabrook.ib.items.commerce.ItemWallet;
import com.mesabrook.ib.rendering.RenderMesabrookIcon;
import com.mesabrook.ib.rendering.RenderWineBottle;
import com.mesabrook.ib.telecom.DynmapAPIListener;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ItemRandomizer;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.SoundRandomizer;
import com.mesabrook.ib.util.TooltipRandomizer;
import com.mesabrook.ib.util.apiaccess.DataAccess;
import com.mesabrook.ib.util.recipe.RecipesHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@EventBusSubscriber
public class RegistryHandler 
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
		OreDictRegistry.init();
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		FuckingTileEntityHandler.registerTileEntities();
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		OBJLoader.INSTANCE.addDomain(Reference.MODID);
		for(Item item : ModItems.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : ModBlocks.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}

		RenderingRegistry.registerEntityRenderingHandler(EntityMesabrookM.class, RenderMesabrookIcon::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityWineBottle.class, RenderWineBottle::new);
	}
	
	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		//MottoRandomizer.RandomMotto();
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");
		Main.logger.info(Reference.MODNAME);
		Main.logger.info("Version " + Reference.VERSION);
		Main.logger.info("");
		Main.logger.info(Reference.UPDATE_NAME);
		Main.logger.info("");
		Main.logger.info("Developed By: RavenholmZombie and CSX8600");
		Main.logger.info("/ib about for more info.");
		Main.logger.info("");
		Main.logger.info("Pre-Initialization");
		Main.logger.info("");
		Main.logger.info(Reference.MOTTO);
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");

        Main.THERCMOD = Loader.isModLoaded("thercmod");
		if(Main.THERCMOD)
		{
			Main.logger.info("[" + Reference.MODNAME + "] The RC Mod Detected.");
		}
		
		Main.DYNMAP = Loader.isModLoaded("dynmap");
		if(Main.DYNMAP)
		{
			Main.logger.info("[" + Reference.MODNAME + "] Dynmap Detected.");
		}
		
		Main.XNET = Loader.isModLoaded("xnet");
		if (Main.XNET)
		{
			Main.logger.info("[" + Reference.MODNAME + "] XNet Detected.");
		}
		
		PacketHandler.registerMessages();
		Triggers.init();
		CapabilityEmployee.init();
		CapabilitySecuredItem.init();
		CapabilityDebitCard.init();
		ItemRegisterFluidWrapper.CapabilityRegisterFluidWrapper.init();
	}
	
	public static void initRegistries()
	{
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");
		Main.logger.info(Reference.MODNAME);
		Main.logger.info("Version " + Reference.VERSION);
		Main.logger.info("");
		Main.logger.info(Reference.UPDATE_NAME);
		Main.logger.info("");
		Main.logger.info("Developed By: RavenholmZombie and CSX8600");
		Main.logger.info("/ib about for more info.");
		Main.logger.info("");
		Main.logger.info("Initialization");
		Main.logger.info("");
		Main.logger.info(Reference.MOTTO);
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		MinecraftForge.EVENT_BUS.register(new SeatEvent());

		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());

		ResourceLocation nameLoc = new ResourceLocation(Reference.MODID + ":mesarang");
		ResourceLocation nameLoc2 = new ResourceLocation(Reference.MODID + ":wine_bottle");
		ResourceLocation nameLoc3 = new ResourceLocation(Reference.MODID + ":ibseat");

		EntityRegistry.registerModEntity(nameLoc, EntityMesabrookM.class, nameLoc.toString(), 1, Main.instance, 64, 1, true);
		EntityRegistry.registerModEntity(nameLoc2, EntityWineBottle.class, nameLoc2.toString(), 2, Main.instance, 64, 1, true);
		EntityRegistry.registerModEntity(nameLoc3, SeatEvent.SeatEntity.class, nameLoc2.toString(), 3, Main.instance, 80, 1, false);


		if (Main.DYNMAP)
		{
			DynmapAPIListener.register();
		}
		
		if (Main.XNET)
		{
			XNetAPI.registerConnectables();
		}
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event)
	{
		//MottoRandomizer.RandomMotto();
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");
		Main.logger.info(Reference.MODNAME);
		Main.logger.info("Version " + Reference.VERSION);
		Main.logger.info("");
		Main.logger.info(Reference.UPDATE_NAME);
		Main.logger.info("");
		Main.logger.info("Developed By: RavenholmZombie and CSX8600");
		Main.logger.info("/ib about for more info.");
		Main.logger.info("");
		Main.logger.info("Post Initialization");
		Main.logger.info("");
		Main.logger.info(Reference.MOTTO);
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");
    	
		TooltipRandomizer.ChosenTooltip();
		ItemRandomizer.RandomizeItem();
		ItemRandomizer.RandomizePresent();
		SoundRandomizer.FartRandomizer(Main.rand);

    	RecipesHandler.registerSmeltingRecipes();
		RecipesHandler.registerMachineRecipes();

//		CDMApps.registerIBApp(new ResourceLocation(Reference.MODID, "ta"), TestApp.class);
//		CDMApps.registerIBApp(new ResourceLocation(Reference.MODID, "cstudio_lite"), CompanyStudioLiteApp.class);
//		CDMApps.registerIBApp(new ResourceLocation(Reference.MODID, "gportal"), GovernmentPortalLiteApp.class);
//		CDMApps.registerIBApp(new ResourceLocation(Reference.MODID, "mesamail"), MesaMailApp.class);

    	Main.logger.info("[Immersibrook] Version " + Reference.VERSION + " loaded.");
	}
	
	public static void serverRegistries(FMLServerStartingEvent event)
	{
		//MottoRandomizer.RandomMotto();
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");
		Main.logger.info(Reference.MODNAME);
		Main.logger.info("Version " + Reference.VERSION);
		Main.logger.info("");
		Main.logger.info(Reference.UPDATE_NAME);
		Main.logger.info("");
		Main.logger.info("Developed By: RavenholmZombie and CSX8600");
		Main.logger.info("/ib about for more info.");
		Main.logger.info("");
		Main.logger.info("Server Initialization");
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");

		event.registerServerCommand(new CommandImmersibrook());
		event.registerServerCommand(new CommandTalk());
		event.registerServerCommand(new CommandMeme());
		event.registerServerCommand(new CommandSkull());

		// Gamerules
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
		GameRules rules = world.getGameRules();

		if(!rules.hasRule("manholeAlert"))
		{
			rules.addGameRule("manholeAlert","true", GameRules.ValueType.BOOLEAN_VALUE);
		}
		if(!rules.hasRule("specialDrops"))
		{
			rules.addGameRule("specialDrops", "true", GameRules.ValueType.BOOLEAN_VALUE);
		}
		if(!rules.hasRule("holidayItemsInInventoryOnJoin"))
		{
			rules.addGameRule("holidayItemsInInventoryOnJoin", "true", GameRules.ValueType.BOOLEAN_VALUE);
		}
		if(!rules.hasRule("forbidCannibalism"))
		{
			rules.addGameRule("forbidCannibalism", "true", GameRules.ValueType.BOOLEAN_VALUE);
		}
		if(!rules.hasRule("toxicEnderPouch"))
		{
			rules.addGameRule("toxicEnderPouch", "false", GameRules.ValueType.BOOLEAN_VALUE);
		}
		if(!rules.hasRule("funnyDeathSound"))
		{
			rules.addGameRule("funnyDeathSound", "true", GameRules.ValueType.BOOLEAN_VALUE);
		}
		
		// MesaSuite Data Access
		DataAccess.init(world);
	}
	
	@SubscribeEvent
	public static void attachEmployeeCapability(AttachCapabilitiesEvent<Entity> e)
	{
		if (!(e.getObject() instanceof EntityPlayer))
		{
			return;
		}
		
		e.addCapability(new ResourceLocation(Reference.MODID, "cap_employee"), new CapabilityEmployeePlayerProvider((EntityPlayer)e.getObject()));
	}
	
	@SubscribeEvent
	public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> e)
	{
		if (e.getObject().getItem() instanceof ItemSecurityBox)
		{
			e.addCapability(new ResourceLocation(Reference.MODID, "cap_secureditem"), new CapabilitySecuredItemProvider());
		}
		
		if (e.getObject().getItem() instanceof ItemDebitCard)
		{
			e.addCapability(new ResourceLocation(Reference.MODID, "cap_debitcard"), new CapabilityDebitCardItemProvider());
		}
		
		if (e.getObject().getItem() instanceof ItemWallet)
		{
			e.addCapability(new ResourceLocation(Reference.MODID, "cap_walletinv"), new ItemWalletCapabilityProvider());
		}
		
		if (e.getObject().getItem() instanceof ItemRegisterFluidWrapper)
		{
			e.addCapability(new ResourceLocation(Reference.MODID, "cap_registerfluidwrapper"), new ItemRegisterFluidWrapper.RegisterFluidWrapperCapabilityProvider());
		}
		
		if (e.getObject().getItem() instanceof ItemShoppingBasket)
		{
			e.addCapability(new ResourceLocation(Reference.MODID, "cap_inventory"), new ICapabilitySerializable<NBTBase>() {
				private ItemStackHandler handler;
				
				{
					handler = new ItemStackHandler(27);
				}
				
				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
				}
				
				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
					{
						return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(handler);
					}
					return null;
				}
				
				@Override
				public void deserializeNBT(NBTBase nbt) {
					CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(handler, null, nbt);
				}
				
				@Override
				public NBTBase serializeNBT() {
					return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(handler, null);
				}
			});
		}
	}
}
