/**
 * Klasa DisplayCurrentWeather sluzaca do wyswietlania aktualnych pobranych danych w konsoli.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DisplayCurrentWeather implements Observer {

    /**
     * Tworzy obiekt DisplayCurrentWeather.
     */
    public DisplayCurrentWeather() {
    }

    /**
     * Wyswietla w konsoli informacje o aktualnym stanie pogody
     */
    @Override
    public void update(WeatherInfo weatherInfo) {
        double temperature = weatherInfo.getTemperature();
        double humidity = weatherInfo.getHumidity();
        double pressure = weatherInfo.getPressure();
        LocalTime localTime = weatherInfo.getLocalTime();
        System.out.println("Current weather: " + temperature + "\u2103, humidity " + humidity + "%, pressure " + pressure + "hPa "  + " " + localTime.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
