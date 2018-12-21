/**
 * Klasa WeatherDataBase przechowujaca dane pogodowe.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class WeatherDataBase implements Observer {

    /**
     * Reprezentuje liste przechowujaca dane pogodowe jako obiekty klasy WeatherInfo.
     */
    private ArrayList<WeatherInfo> weatherInfoList = new ArrayList<>();

    /**
     * Tworzy obiekt WeatherDataBase.
     */
    public WeatherDataBase() {
    }

    /**
     * Pobiera liste liste przechowujaca obiekty klasy WeatherInfo.
     *
     * @return weatherInfoList
     */
    public ArrayList<WeatherInfo> getWeatherInfoList() {
        return weatherInfoList;
    }

    /**
     * Ustawia liste liste przechowujaca obiekty klasy WeatherInfo.
     */
    public void setWeatherInfoList(ArrayList<WeatherInfo> weatherInfoList) {
        this.weatherInfoList = weatherInfoList;
    }

    /**
     * Dodaje do listy przechowujacej obiekty klasy WeatherInfo nowy obiekt o aktualnych parametrach.
     *
     * @param weatherInfo
     */
    @Override
    public void update(WeatherInfo weatherInfo) {
        double temperature = weatherInfo.getTemperature();
        double humidity = weatherInfo.getHumidity();
        double pressure = weatherInfo.getPressure();
        LocalTime localTime = weatherInfo.getLocalTime();
        String description = weatherInfo.getDescription();
        String city = weatherInfo.getCity();
        String icon = weatherInfo.getIcon();

        double minTempForecast = weatherInfo.getMinTempForecast();
        double maxTempForecast = weatherInfo.getMinTempForecast();
        double pressureForecast = weatherInfo.getPressureForecast();
        double humidityForecast = weatherInfo.getHumidityForecast();
        String descriptionForecast = weatherInfo.getDescriptionForecast();
        String iconForecast = weatherInfo.getIconForecast();
        String dateForecast = weatherInfo.getDateForecast();

        weatherInfoList.add(new WeatherInfo(temperature, humidity, pressure, description, city, icon, localTime, minTempForecast, maxTempForecast, humidityForecast, pressureForecast, descriptionForecast, iconForecast, dateForecast));
    }

    /**
     * Zapisuje zebrane dane pogodowe do podanego pliku w formacie JSON i wyswietla komunikat o stanie zapisu w polu tekstowym. W przypadku niepowodzenia obsługuje wyjatek IOException.
     *
     * @param fileName
     * @param txtWarning
     */
    public void saveToFile(String fileName, TextArea txtWarning) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(fileName + ".json");

        try (FileWriter fileWriter = new FileWriter(file)) {
            gson.toJson(weatherInfoList, fileWriter);
            Platform.runLater(() -> txtWarning.appendText("\nZAPISANO"));
        } catch (IOException e) {
            System.out.println("IO error");
            Platform.runLater(() -> txtWarning.appendText("\nBŁAD ZAPISU"));
        }
    }

    /**
     * Wczytuje zebrane dane pogodowe z pliku w formacie JSON i wpisuje je do listy z danymi. W przypadku niepowodzenia obsługuje blad IOException i wyswielta odpowiedni koumunikat w polu tekstowym.
     *
     * @param filePath
     * @param txtWarning
     */
    public void readFromFile(String filePath, TextArea txtWarning) {
        if (!filePath.contains(".json")) {
            filePath = filePath + ".json";
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        WeatherInfo[] testDatabase = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            testDatabase = gson.fromJson(bufferedReader, WeatherInfo[].class);
        } catch (IOException e) {
            e.printStackTrace();
            Platform.runLater(() -> txtWarning.appendText("\nNIE ZNALEZIONO PLIKU"));

        }
        weatherInfoList = new ArrayList<>(Arrays.asList(testDatabase));
    }
}
