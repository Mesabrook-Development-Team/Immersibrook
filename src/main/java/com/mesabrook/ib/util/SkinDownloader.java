package com.mesabrook.ib.util;

import com.mesabrook.ib.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class SkinDownloader
{
    public static void downloadSkin(UUID playerUUID)
    {
        try
        {
            Main.logger.info("Starting Skin Downloads...");

            URL url = new URL("https://mc-heads.net/skin/" + playerUUID);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();

            File skinsDir = new File(FMLCommonHandler.instance().getMinecraftServerInstance().getDataDirectory(), "mods/wbtc/src/main/resources/assets/wbtc/textures/skins");
            File skinFile = new File(skinsDir, playerUUID + ".png");
            Files.copy(in, skinFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            ResourceLocation skinLocation = new ResourceLocation("your-mod-name", "textures/skins/" + playerUUID + ".png");
        }
        catch(Exception ex)
        {
            Main.logger.error("ERROR! Unable to download skin! " + ex);
        }
    }
}
