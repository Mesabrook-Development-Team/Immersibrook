package com.mesabrook.ib.util.handlers;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.advancements.Triggers;
import com.mesabrook.ib.cmds.CommandImmersibrook;
import com.mesabrook.ib.cmds.CommandTalk;
import com.mesabrook.ib.entity.EntityMesabrookM;
import com.mesabrook.ib.entity.EntityWineBottle;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.rendering.RenderMesabrookIcon;
import com.mesabrook.ib.rendering.RenderWineBottle;
import com.mesabrook.ib.telecom.DynmapAPIListener;
import com.mesabrook.ib.util.*;
import com.mesabrook.ib.util.recipe.RecipesHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
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
		TileEntityHandler.registerTileEntities();
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
		
		PacketHandler.registerMessages();
		Triggers.init();
	}
	
	public static void initRegistries()
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
		Main.logger.info("/ib about for more info.");
		Main.logger.info("");
		Main.logger.info("Initialization");
		Main.logger.info("");
		Main.logger.info(Reference.MOTTO);
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());

		ResourceLocation nameLoc = new ResourceLocation(Reference.MODID + ":mesarang");
		ResourceLocation nameLoc2 = new ResourceLocation(Reference.MODID + ":wine_bottle");
		EntityRegistry.registerModEntity(nameLoc, EntityMesabrookM.class, nameLoc.toString(), 1, Main.instance, 64, 1, true);
		EntityRegistry.registerModEntity(nameLoc2, EntityWineBottle.class, nameLoc2.toString(), 2, Main.instance, 64, 1, true);
		if (Main.DYNMAP)
		{
			DynmapAPIListener.register();
		}
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event)
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
    	RecipesHandler.registerSmeltingRecipes();
    	
    	Main.logger.info("[Immersibrook] Version " + Reference.VERSION + " loaded.");
	}
	
	public static void serverRegistries(FMLServerStartingEvent event)
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
		Main.logger.info("/ib about for more info.");
		Main.logger.info("");
		Main.logger.info("Server Initialization");
		Main.logger.info("");
		Main.logger.info(Reference.MOTTO);
		Main.logger.info("");
		Main.logger.info("=============================================================================");
		Main.logger.info("");

		event.registerServerCommand(new CommandImmersibrook());
		event.registerServerCommand(new CommandTalk());

		// Gamerules
		GameRules rules = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0).getGameRules();

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
	}
}
