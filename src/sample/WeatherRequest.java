/**
 * Klasa WeatherRequest pobierajaca dane pogodowe z serwera OpenWeather.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class WeatherRequest {

    /**
     * Reprezentuje interwal czasowy co jaki zostana pobrane dane.
     */
    private double interval;
    /**
     * Reprezentuje nazwe miasta.
     */
    private String city;
    /**
     * Reprezentuje mape obiektu z akutalnymi danymi pogodowymi.
     */
    public Map weatherMap;

    /**
     * Reprezentuje mape obiektu z prognozowanymi danymi pogodowymi.
     */
    public Map forecastMap;

    /**
     * Tworzy obiekt.
     */
    public WeatherRequest() {
    }

    /**
     * Tworzy obiekt o zadanych parametrach po czym ustawia mape z aktualnymi danymi i mape z prognoza.
     *
     * @param interval
     * @param city
     */
    public WeatherRequest(double interval, String city) {
        this.interval = interval;
        this.city = city;
        setWeatherMap();
        setForecastMap();
    }

    /**
     * Pobiera mape
     *
     * @return weatherMap
     */

    public Map getWeatherMap() {
        return weatherMap;
    }

    /**
     * Ustawia mape z aktualna pogoda.
     */
    public void setWeatherMap() {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&APPID=2af7e2e12429ce3e4a759ae7a80c24f1";
        weatherMap = sendRequest(url);
    }

    /**
     * Pobiera mape z prognoza pogody.
     * @return forecastMap
     */
    public Map getForecastMap() {
        return forecastMap;
    }

    /**
     * Ustawia mape z prognoza pogody.
     */
    public void setForecastMap() {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&APPID=2af7e2e12429ce3e4a759ae7a80c24f1";
        forecastMap = sendRequest(url);
    }

    /**
     * Dla zadanego adresu url pobiera dane z serwera w postaci String, ktory przekształca na format json i na mape. W przypadku wystapienia problemu z polaczeniem obsluguje wyjatek IOException.
     * @param url
     * @return map
     */
    public Map sendRequest(String url) {

        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException ex) {

            System.out.println("Connection failed");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map map = gson.fromJson(response.toString(), Map.class);
        return map;
    }

}
