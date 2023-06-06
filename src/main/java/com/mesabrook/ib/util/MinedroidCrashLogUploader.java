package com.mesabrook.ib.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MinedroidCrashLogUploader
{
    private static final String API_URL = "https://pastebin.com/api/api_post.php";
    private static final String API_DEV_KEY = "yj25Ut7-McV_XrTr-xrS6Gl3koBXxzCy";

    public static String uploadText(String text) throws Exception
    {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String postData = "api_dev_key=" + URLEncoder.encode(API_DEV_KEY, "UTF-8") +
                "&api_option=paste" +
                "&api_paste_code=" + URLEncoder.encode(text, "UTF-8");

        byte[] postDataBytes = postData.getBytes("UTF-8");
        connection.getOutputStream().write(postDataBytes);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        reader.close();

        return response;
    }
}
