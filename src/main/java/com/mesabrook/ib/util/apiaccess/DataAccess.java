package com.mesabrook.ib.util.apiaccess;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mesabrook.ib.Main;
import com.mesabrook.ib.util.config.ModConfig;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public abstract class DataAccess {
	
	public enum API
	{
		OAuth("oauth"),
		System("system"),
		Company("company");
		
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
	private final Class<?>[] resultTypes;
	private boolean requestSuccessful;
	private boolean requireAuthToken = true;
	private HashMap<String, String> headerOverrides = new HashMap<>();
	private HashMap<Class<?>, Object> resultsByType = new HashMap<>();
	protected Object sendableObject = null;
	private int responseCode = 0;
	public DataAccess(API api, String resource)
	{
		this(api, resource, new Class<?>[0]);
	}
	
	public DataAccess(API api, String resource, Class<?>... resultTypes)
	{
		this.api = api;
		this.resource = resource;
		this.resultTypes = resultTypes;
	}
	
	protected abstract String getMethod();
	
	public URL getBaseURL() throws MalformedURLException
	{
		if (api == API.OAuth)
		{
			return new URL(ModConfig.mesasuiteBaseOAuthUrl + "/" + resource);
		}
		else
		{
			return new URL(ModConfig.mesasuiteBaseAPIUrl + "/" + api.getUrlName() + "/" + resource);
		}
	}
	
	public void execute()
	{
		execute(false);
	}
	
	private void execute(boolean isRetryAfterUnauthorized)
	{
		HttpURLConnection connection = null;
		try
		{
			connection = getBaseConnection();
			for(Map.Entry<String, String> headerValue : headerOverrides.entrySet())
			{
				connection.setRequestProperty(headerValue.getKey(), headerValue.getValue());
			}
			
			connection.setDoInput(true);
			if (sendableObject != null)
			{
				connection.setDoOutput(true);
				writeOutput(connection, sendableObject);
			}
			
			responseCode = connection.getResponseCode();
			switch(responseCode)
			{
				case HttpURLConnection.HTTP_OK:
				case HttpURLConnection.HTTP_INTERNAL_ERROR:
				case HttpURLConnection.HTTP_BAD_REQUEST:
					readInput(connection);
					if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR && !resultsByType.containsKey(GenericErrorResponse.class))
					{
						GenericErrorResponse error = new GenericErrorResponse();
						error.message = "An internal server error occurred";
						resultsByType.put(GenericErrorResponse.class, error);
						setRequestSuccessful(false);
						return;
					}
					
					if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST && !resultsByType.containsKey(GenericErrorResponse.class))
					{
						GenericErrorResponse error = new GenericErrorResponse();
						error.message = "An error occurred with your request";
						resultsByType.put(GenericErrorResponse.class, error);
						setRequestSuccessful(false);
						return;
					}
					setRequestSuccessful(true);
					break;
				case HttpURLConnection.HTTP_NOT_FOUND:
					GenericErrorResponse error = new GenericErrorResponse();
					error.message = "The requested resource was not found";
					resultsByType.put(GenericErrorResponse.class, error);
					setRequestSuccessful(false);
					break;
				case HttpURLConnection.HTTP_UNAUTHORIZED:
					if (!isRetryAfterUnauthorized)
					{
						connection.disconnect();
						doTokenRefresh();
						execute(true);
						return;
					}
					else
					{
						GenericErrorResponse unauthError = new GenericErrorResponse();
						unauthError.message = "You must sign in to view this content";
						resultsByType.put(GenericErrorResponse.class, unauthError);
						setRequestSuccessful(false);
					}
					break;
				case HttpURLConnection.HTTP_FORBIDDEN:
					GenericErrorResponse forbiddenError = new GenericErrorResponse();
					forbiddenError.message = "You do not have sufficient permissions to view the content";
					resultsByType.put(GenericErrorResponse.class, forbiddenError);
					setRequestSuccessful(false);
					break;
			}
		}
		catch(Exception ex)
		{
			setRequestSuccessful(false);
		}
		finally
		{
			if (connection != null)
			{
				connection.disconnect();
			}
		}
	}
	
	protected void writeOutput(HttpURLConnection connection, Object sendableObject) throws IOException
	{
		String data;
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		if (connection.getRequestProperty("Content-Type").equalsIgnoreCase("application/json"))
		{
			Gson gson = new Gson();
			data = gson.toJson(sendableObject);
		}	
		else
		{
			data = sendableObject.toString();
		}
		writer.write(data);
		writer.close();
	}
	
	protected void readInput(HttpURLConnection connection) throws IOException
	{
		InputStream inputStream = connection.getErrorStream();
		if (inputStream == null)
		{
			inputStream = connection.getInputStream();
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSS").create();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String data = "";
		String line;
		while((line = reader.readLine()) != null)
		{
			data += line;
		}
		
		for(Class<?> clazz : resultTypes)
		{
			try
			{
				Object aResult = gson.fromJson(data, clazz);
				resultsByType.put(clazz, aResult);
			}
			catch(Exception ex)
			{
				
			}
		}
		
		GenericErrorResponse errorResponse = gson.fromJson(reader, GenericErrorResponse.class);
		resultsByType.put(GenericErrorResponse.class, errorResponse);
		
		setRequestSuccessful(true);
	}
	
	public <ResponseType> ResponseType getResult(Class<ResponseType> resultClazz)
	{
		try
		{
			return resultClazz.cast(resultsByType.get(resultClazz));
		}
		catch(Exception ex) { return null; }
	}
	
	public HashMap<String, String> getHeaderOverrides() { return headerOverrides; }
	
	protected void setRequestSuccessful(boolean requestSuccessful)
	{
		this.requestSuccessful = requestSuccessful;
	}
	
	public boolean getRequestSuccessful()
	{
		return requestSuccessful;
	}
	
	public boolean getRequireAuthToken()
	{
		return requireAuthToken;
	}
	
	public void setRequireAuthToken(boolean requireAuthToken)
	{
		this.requireAuthToken = requireAuthToken;
	}
	
	public int getResponseCode()
	{
		return responseCode;
	}
	
	// === AUTH SETUP STUFF ===
	private void doTokenRefresh()
	{
		RefreshTokenRequest request = new RefreshTokenRequest();
		request.grant_type = "refresh_token";
		request.refresh_token = refreshToken.toString();
		
		PostData post = new PostData(API.OAuth, "Token", request, RefreshTokenSuccessResponse.class);
		post.setRequireAuthToken(false);
		post.execute();
		RefreshTokenSuccessResponse response = post.getResult(RefreshTokenSuccessResponse.class);
		if (response == null || response.access_token == null || response.access_token.isEmpty())
		{
			authToken = null;
			refreshToken = null;
			expiration = null;
			setAuthenticationStatus(AuthenticationStatus.LoggedOut);
			return;
		}
		
		authToken = UUID.fromString(response.access_token);
		refreshToken = UUID.fromString(response.refresh_token);
		expiration = LocalDateTime.now().plusSeconds(response.expires_in);
		setAuthenticationStatus(AuthenticationStatus.LoggedIn); // Call this because it's a quick way to set the world data
	}
	
	protected HttpURLConnection getBaseConnection()
	{
		if (authenticationStatus == AuthenticationStatus.LoggedOut && getRequireAuthToken())
		{
			return null;
		}
		
		if (getRequireAuthToken() && expiration.compareTo(LocalDateTime.now()) < 0)
		{
			doTokenRefresh();
			
			if (authenticationStatus == AuthenticationStatus.LoggedOut)
			{
				return null;
			}
		}
		
		HttpURLConnection connection;
		try
		{
			URL url = getBaseURL();
			connection = (HttpURLConnection)url.openConnection();
		}
		catch (Exception ex)
		{
			setRequestSuccessful(false);
			return null;
		}
		
		connection.setRequestProperty("Content-Type", "application/json");
		if (getRequireAuthToken())
		{
			connection.setRequestProperty("Authorization", "Bearer " + authToken.toString());
		}
		
		return connection;
	}
	
	public enum AuthenticationStatus
	{
		LoggedIn,
		LoggedOut
	}
	
	private static AuthenticationStatus authenticationStatus = AuthenticationStatus.LoggedOut;
	private static UUID authToken;
	private static UUID refreshToken;
	private static LocalDateTime expiration;
	
	public static AuthenticationStatus getAuthenticationStatus()
	{
		return authenticationStatus;
	}
	
	private static void setAuthenticationStatus(AuthenticationStatus status)
	{
		authenticationStatus = status;
		
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
		AuthWorldData data = (AuthWorldData)world.loadData(AuthWorldData.class, "ib_authdata");
		if (data == null)
		{
			data = new AuthWorldData("ib_authdata");
			world.setData("ib_authdata", data);
		}
		data.authToken = authToken;
		data.refreshToken = refreshToken;
		data.expiration = expiration;
		data.markDirty();
	}
	
	public static void init(World world)
	{
		DataRequestQueue.INSTANCE.start();
		
		AuthWorldData data = (AuthWorldData)world.loadData(AuthWorldData.class, "ib_authdata");
		if (data == null)
		{
			data = new AuthWorldData("ib_authdata");
			world.setData("ib_authdata", data);
		}
		
		if (ModConfig.mesasuiteBaseAPIUrl.isEmpty() || ModConfig.mesasuiteBaseOAuthUrl.isEmpty() || ModConfig.mesasuiteClientID.isEmpty() || data.authToken == null)
		{
			setAuthenticationStatus(AuthenticationStatus.LoggedOut);
			return;
		}
		
		authToken = data.authToken;
		refreshToken = data.refreshToken;
		expiration = data.expiration;
		setAuthenticationStatus(AuthenticationStatus.LoggedIn);
		
		GetData get = new GetData(API.System, "Program/GetCurrentPrograms");
		get.execute();
		
		if (!get.getRequestSuccessful())
		{
			authToken = null;
			refreshToken = null;
			expiration = null;
			setAuthenticationStatus(AuthenticationStatus.LoggedOut);
		}
	}
	
	public static void shutdown(World world)
	{
		DataRequestQueue.INSTANCE.stop();
	}
	
	public static class AuthWorldData extends WorldSavedData
	{

		private UUID authToken;
		private UUID refreshToken;
		private LocalDateTime expiration;
		
		public AuthWorldData(String dataName) {
			super(dataName);
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

	private static DataRequestTask loginRequestTask;
	public static void login(UUID playerID)
	{		
		PostData post = new PostData(API.OAuth, "Authorize", "client_id=" + ModConfig.mesasuiteClientID + "&response_type=device_code", DeviceCodeAuthorizeResponse.class);
		post.setRequireAuthToken(false);
		post.getHeaderOverrides().put("Content-Type", "application/x-www-form-urlencoded");
		loginRequestTask = new DataRequestTask(post);
		loginRequestTask.getData().put("playerID", playerID);
		currentLoginPhase = LoginPhases.Authorize;
		DataRequestQueue.INSTANCE.addTask(loginRequestTask);
	}
	
	private static DataRequestTask logoutTask;
	public static void logout(UUID playerID)
	{
		PostData post = new PostData(API.OAuth, "Revoke", "reason=Logout");
		post.getHeaderOverrides().put("Content-Type", "application/x-www-form-urlencoded");
		logoutTask = new DataRequestTask(post);
		logoutTask.getData().put("playerID", playerID);
		currentLoginPhase = LoginPhases.LogOut;
		DataRequestQueue.INSTANCE.addTask(logoutTask);
	}
	
	private static class RefreshTokenRequest
	{
		public String grant_type;
		public String refresh_token;
	}
	
	private static class RefreshTokenSuccessResponse
	{
		public String access_token;
		public String token_type;
		public int expires_in;
		public String refresh_token;
	}
	
	private static class OAuthErrorResponse
	{
		public String error;
		public String error_description;
	}
	
	public static class GenericErrorResponse
	{
		public String message;
	}
	
	private static class DeviceCodeAuthorizeResponse
	{
		public String verification_uri;
		public String user_code;
		public String device_code;
		public int interval;
	}
	
	private static class DeviceCodeTokenResponse
	{
		public String access_token;
		public String token_type;
		public int expires_in;
		public String refresh_token;
	}
	
	private enum LoginPhases
	{
		None,
		Authorize,
		TokenCheck,
		LogOut;
	}
	private static LoginPhases currentLoginPhase = LoginPhases.None;
	private static LocalDateTime lastTokenAttempt = null;
	private static int tokenTimeInterval = 0;
	private static DataRequestTask tokenRequestTask = null;
	
	@EventBusSubscriber
	public static class LoginTickHandler
	{
		@SubscribeEvent
		public static void serverTick(ServerTickEvent e)
		{
			if (currentLoginPhase == LoginPhases.Authorize)
			{
				if (loginRequestTask == null || loginRequestTask.getStatus() != DataRequestTaskStatus.Complete)
				{
					return;
				}
				
				UUID playerID = (UUID)loginRequestTask.getData().get("playerID");
				DeviceCodeAuthorizeResponse response = loginRequestTask.getTask().getResult(DeviceCodeAuthorizeResponse.class);
				
				TextComponentString messageToLoginer = new TextComponentString(response == null ? "Something went wrong!" : "Log in to " + response.verification_uri + " and enter code " + response.user_code);
				EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
				player.sendMessage(messageToLoginer);

				loginRequestTask = null;
				if (response != null)
				{
					lastTokenAttempt = LocalDateTime.now();
					tokenTimeInterval = response.interval;
					currentLoginPhase = LoginPhases.TokenCheck;
					
					PostData post = new PostData(API.OAuth, "Token", "grant_type=device_code&client_id=" + ModConfig.mesasuiteClientID.toString() + "&code=" + response.device_code, DeviceCodeTokenResponse.class, OAuthErrorResponse.class);
					post.setRequireAuthToken(false);
					post.getHeaderOverrides().put("Content-Type", "application/x-www-form-urlencoded");
					tokenRequestTask = new DataRequestTask(post);
					tokenRequestTask.getData().put("playerID", playerID);
					tokenRequestTask.getData().put("deviceCode", response.device_code);
					DataRequestQueue.INSTANCE.addTask(tokenRequestTask);
				}
				else
				{
					currentLoginPhase = LoginPhases.None;
				}
			}
			else if (currentLoginPhase == LoginPhases.TokenCheck)
			{
				if (lastTokenAttempt.plusSeconds(tokenTimeInterval).compareTo(LocalDateTime.now()) > 0 || (tokenRequestTask != null && tokenRequestTask.getStatus() != DataRequestTaskStatus.Complete))
				{
					return;
				}
				
				if (tokenRequestTask == null)
				{
					currentLoginPhase = LoginPhases.None;
					return;
				}
				
				UUID playerID = (UUID)tokenRequestTask.getData().get("playerID");
				String deviceCode = (String)tokenRequestTask.getData().get("deviceCode");
				EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
				
				Runnable resendRequest = () ->
				{
					PostData post = new PostData(API.OAuth, "Token", "grant_type=device_code&client_id=" + ModConfig.mesasuiteClientID.toString() + "&code=" + deviceCode, DeviceCodeTokenResponse.class, OAuthErrorResponse.class);
					post.setRequireAuthToken(false);
					post.getHeaderOverrides().put("Content-Type", "application/x-www-form-urlencoded");
					tokenRequestTask = new DataRequestTask(post);
					tokenRequestTask.getData().put("playerID", playerID);
					tokenRequestTask.getData().put("deviceCode", deviceCode);
					DataRequestQueue.INSTANCE.addTask(tokenRequestTask);
				};
				
				OAuthErrorResponse errorResponse = tokenRequestTask.getTask().getResult(OAuthErrorResponse.class);
				if (errorResponse != null && errorResponse.error != null)
				{
					switch(errorResponse.error)
					{
						case "slow_down":
							Main.logger.warn("Server indicated code check is occurring too often");
						case "authorization_pending":
							lastTokenAttempt = LocalDateTime.now().plusSeconds(tokenTimeInterval); // Try again later
							resendRequest.run();
							return;
						default:
							TextComponentString errorToLoginer = new TextComponentString(TextFormatting.RED + errorResponse.error_description);
							player.sendMessage(errorToLoginer);
							tokenRequestTask = null;
							lastTokenAttempt = null;
							tokenTimeInterval = 0;
							currentLoginPhase = LoginPhases.None;
							return;
					}
				}
				
				DeviceCodeTokenResponse successResponse = tokenRequestTask.getTask().getResult(DeviceCodeTokenResponse.class);
				if (successResponse != null && successResponse.access_token != null)
				{
					authToken = UUID.fromString(successResponse.access_token);
					refreshToken = UUID.fromString(successResponse.refresh_token);
					expiration = LocalDateTime.now().plusMinutes(successResponse.expires_in);
					tokenRequestTask = null;
					lastTokenAttempt = null;
					tokenTimeInterval = 0;
					setAuthenticationStatus(AuthenticationStatus.LoggedIn);
					currentLoginPhase = LoginPhases.None;
					
					TextComponentString errorToLoginer = new TextComponentString(TextFormatting.GREEN + "Login successful!");
					player.sendMessage(errorToLoginer);
					return;
				}
				
				// What now? This shouldn't be a spot we get into, so I guess just log it and try again
				Main.logger.warn("While checking whether or not the code was entered, a valid response was not provided");
				lastTokenAttempt = LocalDateTime.now().plusSeconds(tokenTimeInterval);
				resendRequest.run();
			}
			else if (currentLoginPhase == LoginPhases.LogOut)
			{
				if (logoutTask == null)
				{
					Main.logger.warn("A request to logout was created, but the task was null");
					currentLoginPhase = LoginPhases.None;
					return;
				}
				
				if (logoutTask.getStatus() != DataRequestTaskStatus.Complete)
				{
					return;
				}
				
				UUID playerID = (UUID)logoutTask.getData().get("playerID");
				EntityPlayerMP player = null;
				if (playerID != null)
				{
					player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerID);
				}
				
				if (logoutTask.getTask().getRequestSuccessful() && player != null)
				{
					authToken = null;
					refreshToken = null;
					expiration = null;
					setAuthenticationStatus(AuthenticationStatus.LoggedOut);
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Logout successful"));
				}
				else if (!logoutTask.getTask().getRequestSuccessful())
				{
					if (player != null)
					{
						player.sendMessage(new TextComponentString(TextFormatting.RED + "Something went wrong!"));
					}
					else
					{
						Main.logger.warn("An error occurred while attempting to sign out!");
					}
				}
				logoutTask = null;
				currentLoginPhase = LoginPhases.None;
			}
		}
	}
}
