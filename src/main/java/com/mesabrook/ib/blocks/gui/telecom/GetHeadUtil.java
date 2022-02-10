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
			// https://crafatar.com/avatars/717bb4e7c70142a6b06fbbe17e0518ae?default=MHF_Steve&overlay
			url = new URL("https://crafatar.com/avatars/" + response.id +"?default=MHF_Steve&overlay");
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			
			InputStream in = conn.getInputStream();
			byte[] data = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			
			ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
			BufferedImage image = ImageIO.read(byteStream);
			
			imagesByUsername.put(username, image);
//			
//			if (image == null)
//			{
//				return;
//			}
//			
//			DynamicTexture textureForUsername = new DynamicTexture(image);
//			ResourceLocation location = new ResourceLocation(Reference.MODID, "telecom_addressbook_" + username.toLowerCase());
//			Minecraft.getMinecraft().getTextureManager().loadTexture(location, textureForUsername);
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
