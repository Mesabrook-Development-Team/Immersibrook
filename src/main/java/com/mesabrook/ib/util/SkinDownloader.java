package com.mesabrook.ib.util;

import com.google.common.net.*;
import com.mesabrook.ib.*;
import com.mojang.authlib.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.server.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SkinDownloader
{
    public static void downloadSkin(String playerName, ResourceLocation resourceLocation)
    {
        try
        {
            // Get the player's UUID
            MinecraftServer minecraftServer = Minecraft.getMinecraft().getIntegratedServer();
            GameProfile profile = minecraftServer.getPlayerProfileCache().getGameProfileForUsername(playerName);
            if (profile == null)
            {
                // The player does not exist or has not logged in yet
                return;
            }

            SkinManager skinManager = Minecraft.getMinecraft().getSkinManager();
            ResourceLocation skinLocation = (ResourceLocation) skinManager.loadSkinFromCache(profile);

            // Download the skin file
            BufferedImage skinImage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(skinLocation).getInputStream());

            // Get the directory in which to save the skin
            File assetsDir = new File("./src/main/resources/assets");
            File resourcePackDir = new File(assetsDir, Reference.MODID);

            // Create the directories if they do not exist
            assetsDir.mkdirs();
            resourcePackDir.mkdirs();

            // Create the resource location file
            File file = new File(resourceLocation.getResourcePath());
            file.getParentFile().mkdirs();

            // Save the skin to the resource pack
            ImageIO.write(skinImage, "png", file);
        }
        catch(Exception ex)
        {
            Main.logger.error("ERROR! Unable to download skin! " + ex);
        }
    }
}
