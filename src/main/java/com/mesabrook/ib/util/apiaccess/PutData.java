package com.mesabrook.ib.util.apiaccess;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class PutData extends DataAccess {

	private Object objectToPut;
	public PutData(API api, String resource, Object objectToPut) {
		super(api, resource);
		this.objectToPut = objectToPut;
	}

	public void executeNoResult()
	{
		try
		{			
			Gson gson = new Gson();
			String data = gson.toJson(objectToPut);
			
			URL url = getBaseURL();
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(data);
			writer.flush();
			writer.close();
			
			int statusCode = connection.getResponseCode();
			setRequestSuccessful(statusCode == 200);
			
		}
		catch(Exception ex)
		{
			setRequestSuccessful(false);
		}
	}
}
