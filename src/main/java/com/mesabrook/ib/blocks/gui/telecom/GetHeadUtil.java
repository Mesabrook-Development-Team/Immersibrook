package com.mesabrook.ib.blocks.gui.telecom;

import com.google.gson.Gson;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

public class GetHeadUtil {
	private static HashMap<String, BufferedImage> imagesByUsername = new HashMap<>();
	private static HashSet<String> threadsRunningForUsername = new HashSet<>();
	private static final ResourceLocation loadingIcon = new ResourceLocation(Reference.MODID, "textures/gui/telecom/loading.png");
	
	public static ResourceLocation getHeadResourceLocation(String username)
	{
		username = username.toLowerCase();
		ResourceLocation expectedResourceLocation = new ResourceLocation(Reference.MODID, "telecom_addressbook_" + username.toLowerCase());
		if (imagesByUsername.containsKey(username))
		{
			DynamicTexture textureForUsername = new DynamicTexture(imagesByUsername.get(username));
			Minecraft.getMinecraft().getTextureManager().loadTexture(expectedResourceLocation, textureForUsername);
			
			return expectedResourceLocation;
		}
		
		ITextureObject textureObject = Minecraft.getMinecraft().getTextureManager().getTexture(expectedResourceLocation);
		if (textureObject == null)
		{
			if (!threadsRunningForUsername.contains(username))
			{
				String localUsername = username;
				new Thread(() -> run(localUsername)).start();
			}
			
			return loadingIcon;
		}
		else if (textureObject == TextureUtil.MISSING_TEXTURE)
		{
			return loadingIcon;
		}
		
		return expectedResourceLocation;
	}
	
	public static void run(String username)
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
			String fetcherURL = "";
			if(ModConfig.skinFetcherEngine.contains("mc-heads"))
			{
				fetcherURL = "https://mc-heads.net/head/" + response.id;
			}
			else if(ModConfig.skinFetcherEngine.contains("crafatar"))
			{
				fetcherURL = "https://crafatar.com/renders/head/" + response.id;
			}
			else
			{
				fetcherURL = "https://mc-heads.net/head/" + response.id;
			}

			url = new URL(fetcherURL);

			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			
			InputStream in = conn.getInputStream();
			byte[] data = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			
			ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
			BufferedImage image = ImageIO.read(byteStream);
			
			imagesByUsername.put(username, image);
		} catch (Exception e) {
			try {
				IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(loadingIcon);
				BufferedImage image = ImageIO.read(resource.getInputStream());
				imagesByUsername.put(username, image);
			} catch (IOException e1) {
				e1.printStackTrace();
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
