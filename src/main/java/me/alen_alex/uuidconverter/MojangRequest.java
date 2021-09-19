package me.alen_alex.uuidconverter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MojangRequest {

    private static final String API_URL = "https://api.ashcon.app/mojang/v2/user/";

    public static String sendRequest(String userName) {
        try {
            URL url = new URL(API_URL + userName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            int responseCode = connection.getResponseCode();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setInstanceFollowRedirects(false);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            if(content != null) {
                JsonElement jsonElement = new JsonParser().parse(content.toString());
                if(jsonElement != null) {
                    if(jsonElement.getAsJsonObject().has("uuid"))
                        return String.valueOf(jsonElement.getAsJsonObject().get("uuid"));
                }
            }
        } catch (IOException e) {
            System.out.println("No data has been found for the user "+userName);
            return null;
        }
        return null;
    }
}
