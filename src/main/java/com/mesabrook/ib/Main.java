package com.mesabrook.ib;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

import com.mesabrook.ib.proxy.CommonProxy;
import com.mesabrook.ib.tab.TabImmersibrook;
import com.mesabrook.ib.telecom.CallManager;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.apiaccess.DataAccess;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.RegistryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, dependencies = "required-after:harvestcraft;required-after:immersiveengineering;required-after:jabcm", updateJSON = Reference.UPDATE_URL)
public class Main 
{
	
	@Instance
	public static Main instance;
    
    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
    public static CommonProxy proxy;
    
    public static Logger logger;
    public static boolean THERCMOD = false;
    public static boolean DYNMAP = false;
    public static final Random rand = new Random();
    
    // Config
 	public static File config;
    
    // Creative Tab
    public static final CreativeTabs IMMERSIBROOK_MAIN = new TabImmersibrook("tab_immersibrook");

    // New motto system
    private List<String> mottos;

    private List<String> loadMottos()
    {
        List<String> mottos = new ArrayList<>();

        try
        {
            InputStream inputStream = getClass().getResourceAsStream("/assets/wbtc/mottos.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null)
            {
                mottos.add(line);
            }

            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return mottos;
    }

    private String getRandomMotto()
    {
        int index = rand.nextInt(mottos.size());
        return mottos.get(index);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        mottos = loadMottos();
        Reference.MOTTO = getRandomMotto();
        RegistryHandler.preInitRegistries(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Reference.MOTTO = getRandomMotto();
        RegistryHandler.initRegistries();
        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Reference.MOTTO = getRandomMotto();
    	RegistryHandler.postInitRegistries(event);
    }
    
	@EventHandler
	public static void serverInit(FMLServerStartingEvent event)
	{
		RegistryHandler.serverRegistries(event);
	}
	
	@EventHandler
	public static void serverStarted(FMLServerStartedEvent event)
	{
		if (ModConfig.autoStartWEA)
		{
			WirelessEmergencyAlertManager.instance().start();
		}
	}
	
	@EventHandler
	public static void serverStopping(FMLServerStoppingEvent event)
	{
		if (ModConfig.autoStartWEA)
		{
			WirelessEmergencyAlertManager.instance().stop();
		}
		CallManager.instance().onServerStop();
		DataAccess.shutdown(FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0));
	}
}
