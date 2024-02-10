package com.mesabrook.ib.util.apiaccess;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;

import scala.Console;

public class GetData extends DataAccess {

	public GetData(API api, String resource) {
		super(api, resource);
	}
	
	public GetData(API api, String resource, Class<?>... returnTypes)
	{
		super(api, resource, returnTypes);
	}

	@Override
	protected String getMethod() { return "GET"; }
	
	@Override
	public URL getBaseURL() throws MalformedURLException {
		if (queryStringMap.isEmpty())
		{
			return super.getBaseURL();			
		}
		
		String url = super.getBaseURL().toString();
		boolean isFirst = true;
		for(Map.Entry<String, Collection<String>> entry : queryStringMap.asMap().entrySet())
		{
			for(String value : entry.getValue())
			{
				if (isFirst)
				{
					url += "?";
					isFirst = false;
				}
				else
				{
					url += "&";
				}
				
				String keyEncoded;
				String valueEncoded;
				try
				{
					keyEncoded = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
					valueEncoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
				}
				catch (UnsupportedEncodingException ex)
				{
					Console.println("UTF 8 was not supported while encoding query string keys/values");
					continue;
				}
				
				url += keyEncoded + "=" + valueEncoded;
			}
		}
		
		return new URL(url);
	}
	
	private ArrayListMultimap<String, String> queryStringMap = ArrayListMultimap.create();
	public void addQueryString(String key, String value)
	{
		queryStringMap.put(key, value);
	}
	
	public void removeQueryStringKeyValue(String key, String value)
	{
		queryStringMap.remove(key, value);
	}
	
	public void removeQueryStringKey(String key)
	{
		queryStringMap.removeAll(key);
	}
}
