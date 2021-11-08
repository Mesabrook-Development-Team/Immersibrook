package rz.mesabrook.wbtc.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
import rz.mesabrook.wbtc.Main;
import rz.mesabrook.wbtc.advancements.Triggers;
import rz.mesabrook.wbtc.cmds.CommandImmersibrook;
import rz.mesabrook.wbtc.entity.EntityMesabrookM;
import rz.mesabrook.wbtc.entity.EntityWineBottle;
import rz.mesabrook.wbtc.init.ModBlocks;
import rz.mesabrook.wbtc.init.ModItems;
import rz.mesabrook.wbtc.rendering.RenderMesabrookIcon;
import rz.mesabrook.wbtc.rendering.RenderWineBottle;
import rz.mesabrook.wbtc.util.IHasModel;
import rz.mesabrook.wbtc.util.ItemRandomizer;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.TooltipRandomizer;
import rz.mesabrook.wbtc.util.recipe.SmeltingRecipes;

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
		Main.logger.info("");
		Main.logger.info("o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o");
		Main.logger.info("");
		Main.logger.info(Reference.MODNAME);
		Main.logger.info("Version " + Reference.VERSION);
		Main.logger.info("PRE-RELEASE BUILD FOR ANNIVERSARY PLAYTEST");
		Main.logger.info(Reference.UPDATE_NAME);
		Main.logger.info("");
		Main.logger.info("Developed By: RavenholmZombie and CSX8600");
		Main.logger.info("");
		Main.logger.info("Pre-Initialization");
		Main.logger.info("");
		Main.logger.info("o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o=o");
		Main.logger.info("");

        Main.THERCMOD = Loader.isModLoaded("thercmod");
		if(Main.THERCMOD)
		{
			Main.logger.info("[" + Reference.MODNAME + "] The RC Mod Detected.");
		}
		
		PacketHandler.registerMessages();
		Triggers.init();
	}
	
	public static void initRegistries()
	{
		Main.logger.info("[" + Reference.MODNAME + "] Initialization");
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());

		ResourceLocation nameLoc = new ResourceLocation(Reference.MODID + ":mesarang");
		ResourceLocation nameLoc2 = new ResourceLocation(Reference.MODID + ":wine_bottle");
		EntityRegistry.registerModEntity(nameLoc, EntityMesabrookM.class, nameLoc.toString(), 1, Main.instance, 64, 1, true);
		EntityRegistry.registerModEntity(nameLoc2, EntityWineBottle.class, nameLoc2.toString(), 2, Main.instance, 64, 1, true);
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event)
	{
		Main.logger.info("[" + Reference.MODNAME + "] Post Initialization");
    	
		TooltipRandomizer.ChosenTooltip();
		ItemRandomizer.RandomizeItem();
		ItemRandomizer.RandomizePresent();
    	SmeltingRecipes.registerSmeltingRecipes();
    	
    	Main.logger.info("[Immersibrook] Version " + Reference.VERSION + " loaded.");
	}
	
	public static void serverRegistries(FMLServerStartingEvent event)
	{
		Main.logger.info("[" + Reference.MODNAME + "] Registering immersibrook command");
		event.registerServerCommand(new CommandImmersibrook());

		GameRules rules = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0).getGameRules();
		if(!rules.hasRule("manholeAlert"))
		{
			rules.addGameRule("manholeAlert","true", GameRules.ValueType.BOOLEAN_VALUE);
		}
	}
}
