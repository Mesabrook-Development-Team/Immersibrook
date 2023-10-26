package com.mesabrook.ib.init;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;
import com.mrcrayfish.device.api.ApplicationManager;
import com.mrcrayfish.device.api.app.Application;
import net.minecraft.util.ResourceLocation;

import java.util.Locale;

public class CDMApps
{
    public static void registerIBApp(ResourceLocation resourceLocation, Class<? extends Application> appClass)
    {
        try
        {
            ApplicationManager.registerApplication(resourceLocation, appClass);
            Main.logger.info("[" + Reference.MODNAME + " CDM App Loader] Registered app " + appClass.getSimpleName());
        }
        catch (Exception ex)
        {
            Main.logger.error("[" + Reference.MODNAME + " CDM App Loader] ERROR IN APP REGISTRATION FOR  " + appClass.getSimpleName().toUpperCase(Locale.ROOT));
            Main.logger.error(ex);
            Main.logger.error(appClass.getSimpleName() + " will not be availble in-game until this error is rectified.");
        }
    }
}
