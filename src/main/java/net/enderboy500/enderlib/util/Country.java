package net.enderboy500.enderlib.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import net.minecraft.client.Minecraft;

public class Country {
    public static String fetchCountry() throws IOException {
            URL url = new URL("http://ip-api.com/line/?fields=country");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String country = bufferedReader.readLine();
            bufferedReader.close();
            return country;
    }
    public static boolean fetchCountryAndCheck(String isOf) throws IOException {
        URL url = new URL("http://ip-api.com/line/?fields=country");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String country = bufferedReader.readLine();
        bufferedReader.close();
        return country.equals(isOf);
    }
}