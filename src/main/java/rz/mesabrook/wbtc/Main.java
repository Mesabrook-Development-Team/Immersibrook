package rz.mesabrook.wbtc;

import java.io.File;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rz.mesabrook.wbtc.proxy.CommonProxy;
import rz.mesabrook.wbtc.tab.TabImmersibrook;
import rz.mesabrook.wbtc.tab.WBTCHouseholdTab;
import rz.mesabrook.wbtc.tab.WBTCTab;
import rz.mesabrook.wbtc.tab.WBTCTabCeiling;
import rz.mesabrook.wbtc.tab.WBTCTrophyTab;
import rz.mesabrook.wbtc.util.Reference;
import rz.mesabrook.wbtc.util.handlers.CreativeGuiDrawHandler;
import rz.mesabrook.wbtc.util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, dependencies = "required-after:harvestcraft")
public class Main 
{
	
	@Instance
	public static Main instance;
    
    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
    public static CommonProxy proxy;
    
    public static Logger logger;
    public static boolean IE_LOADED = false;
    public static boolean JABCM_LOADED = false;
    public static boolean FURENIKUS_CITIES = false;
    
    // Config
 	public static File config;
    
    // Creative Tab
    public static final CreativeTabs IMMERSIBROOK_MAIN = new TabImmersibrook("tab_immersibrook");
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        RegistryHandler.preInitRegistries(event);
        
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        RegistryHandler.initRegistries();
        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	RegistryHandler.postInitRegistries(event);
    }
    
	@EventHandler
	public static void serverInit(FMLServerStartingEvent event)
	{
		RegistryHandler.serverRegistries(event);
	}
}
