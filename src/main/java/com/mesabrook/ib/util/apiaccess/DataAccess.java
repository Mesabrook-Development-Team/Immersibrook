package com.mesabrook.ib.util.apiaccess;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import com.mesabrook.ib.util.config.ModConfig;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public abstract class DataAccess {
	
	public enum API
	{
		System("system");
		
		private String urlName;
		private API(String urlName)
		{
			this.urlName = urlName;
		}
		
		public String getUrlName()
		{
			return urlName;
		}
	}

	private final API api;
	private final String resource;
	private boolean requestSuccessful;
	public DataAccess(API api, String resource)
	{
		this.api = api;
		this.resource = resource;
	}
	
	public URL getBaseURL() throws MalformedURLException
	{
		return new URL(ModConfig.mesasuiteBaseAPIUrl + "/" + api.getUrlName() + "/" + resource);
	}
	
	protected void setRequestSuccessful(boolean requestSuccessful)
	{
		this.requestSuccessful = requestSuccessful;
	}
	
	public boolean getRequestSuccessful()
	{
		return requestSuccessful;
	}
	
	public enum AuthenticationStatus
	{
		LoggedIn,
		LoggedOut
	}
	
	private static AuthenticationStatus authenticationStatus = AuthenticationStatus.LoggedOut;
	public static AuthenticationStatus getAuthenticationStatus()
	{
		return authenticationStatus;
	}
	
	public static void init(World world)
	{
		// TODO: Check auth status and set authenticationStatus appropriately
	}
	
	public static class AuthWorldData extends WorldSavedData
	{

		private UUID authToken;
		private UUID refreshToken;
		private LocalDateTime expiration;
		
		public AuthWorldData() {
			super("ib_authdata");
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			authToken = nbt.getUniqueId("authToken");
			refreshToken = nbt.getUniqueId("refreshToken");
			expiration = LocalDateTime.ofEpochSecond(nbt.getLong("expiration"), 0, ZoneOffset.UTC);
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound) {
			if (authToken != null)
			{
				compound.setUniqueId("authToken", authToken);
			}
			
			if (refreshToken != null)
			{
				compound.setUniqueId("refreshToken", refreshToken);
			}
			
			if (expiration != null)
			{
				compound.setLong("expiration", expiration.toEpochSecond(ZoneOffset.UTC));
			}
			
			return compound;
		}
		
	}

	
}
