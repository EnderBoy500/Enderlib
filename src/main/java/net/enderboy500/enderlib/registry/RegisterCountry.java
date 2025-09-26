package net.enderboy500.enderlib.registry;

import net.minecraft.client.MinecraftClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class RegisterCountry {
    public static void addForbiddenCountry(String forbiddenCountry) {
        try {
            URL url = new URL("http://ip-api.com/line/?fields=country");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String country = bufferedReader.readLine();
            bufferedReader.close();
            if (Objects.equals(country, forbiddenCountry)) {
                MinecraftClient.getInstance().stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addForbiddenCountryWithLogMessage(String forbiddenCountry, String logMessage) {
        try {
            URL url = new URL("http://ip-api.com/line/?fields=country");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String country = bufferedReader.readLine();
            bufferedReader.close();
            if (Objects.equals(country, forbiddenCountry)) {
                System.out.println(logMessage);
                MinecraftClient.getInstance().stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}