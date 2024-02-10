package com.mesabrook.ib.blocks.gui.telecom;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class GetHeadUtil {
	private static HashMap<String, BufferedImage> imagesByUsername = new HashMap<>();
	private static HashSet<String> threadsRunningForUsername = new HashSet<>();
	private static final ResourceLocation loadingIcon = new ResourceLocation(Reference.MODID, "textures/gui/telecom/loading.png");
	
	public static ResourceLocation getHeadResourceLocation(String username, EnumSkinFetchingEngines skinFetchingEngine)
	{
		username = username.toLowerCase();
		ResourceLocation expectedResourceLocation = new ResourceLocation(Reference.MODID, "telecom_addressbook_" + skinFetchingEngine.getName() + "_" + username.toLowerCase());
		final String imagesByUsernameKey = username + "_" + skinFetchingEngine.getName();
		if (imagesByUsername.containsKey(imagesByUsernameKey))
		{
			DynamicTexture textureForUsername = new DynamicTexture(imagesByUsername.get(imagesByUsernameKey));
			Minecraft.getMinecraft().getTextureManager().loadTexture(expectedResourceLocation, textureForUsername);
			
			return expectedResourceLocation;
		}
		
		ITextureObject textureObject = Minecraft.getMinecraft().getTextureManager().getTexture(expectedResourceLocation);
		if (textureObject == null)
		{
			if (!threadsRunningForUsername.contains(username))
			{
				String localUsername = username;
				new Thread(() -> run(localUsername, skinFetchingEngine)).start();
			}
			
			return loadingIcon;
		}
		else if (textureObject == TextureUtil.MISSING_TEXTURE)
		{
			return loadingIcon;
		}
		
		return expectedResourceLocation;
	}
	
	public static void run(String username, EnumSkinFetchingEngines skinFetchingEngine)
	{
		threadsRunningForUsername.add(username);
		URL url;
		try {
			url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			
			StringBuilder builder = new StringBuilder();
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())))
			{
				for(String line; (line = reader.readLine()) != null;)
				{
					builder.append(line);
				}
			}
			
			MojangAPIResponse response = new Gson().fromJson(builder.toString(), MojangAPIResponse.class);
			String fetcherURL;
			switch(skinFetchingEngine)
			{
				case Crafatar:
					fetcherURL = "https://crafatar.com/renders/head/" + response.id + "?overlay";
					break;
				default:
					fetcherURL = "https://mc-heads.net/head/" + response.id;
					break;
			}

			url = new URL(fetcherURL);

			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			
			InputStream in = conn.getInputStream();
			byte[] data = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			
			ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
			BufferedImage image = ImageIO.read(byteStream);
			
			imagesByUsername.put(username + "_" + skinFetchingEngine.getName(), image);
		} catch (Exception e) {
			try {
				IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(loadingIcon);
				BufferedImage image = ImageIO.read(resource.getInputStream());
				imagesByUsername.put(username, image);
			} catch (IOException e1) {
				Main.logger.catching(e1);
			}
			return;
		}
		finally
		{
			threadsRunningForUsername.remove(username);
		}
	}
	
	private static class MojangAPIResponse
	{
		public String id;
	}
}
