package com.desive.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/*
 Created by Jack DeSive on 10/14/2017 at 2:36 PM
*/
public class Http {

    public static String request(String target, Map<String, String> parameters, Map<String, String> headers, String body, String method) throws IOException {

        URL targetUrl = new URL(target + createParameterString(parameters));
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();

        if(headers != null && !headers.isEmpty()){
            headers.forEach((k, v) -> connection.setRequestProperty(k, v));
        }

        connection.setRequestMethod(method.toUpperCase());
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        if(body != null && body != "") {
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(body);
            outputStream.flush();
            outputStream.close();
        }

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        inputStream.close();

        connection.disconnect();
        return response.toString();
    }

    private static String createParameterString(Map<String, String> parameters){
        if(parameters == null || parameters.isEmpty())
            return "";

        StringBuilder paramString = new StringBuilder("?");
        parameters.forEach((k, v) -> {
            paramString.append(k);
            paramString.append("=");
            paramString.append(v);
            paramString.append("&");
        });
        paramString.deleteCharAt(paramString.length()-1);
        return paramString.toString();
    }

}
