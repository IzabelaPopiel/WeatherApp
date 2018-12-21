/**
 * Klasa WeatherInfo przechowujaca infromacje o aktualnych danych pogodowych.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class WeatherInfo implements Observable, Runnable {

    /**
     * Reprezentuje liste przechowujaca obserwatorow.
     */
    private ArrayList<Observer> observers = new ArrayList<>();
    /**
     * Reprezentuje aktualna temperature.
     */
    private double temperature;
    /**
     * Reprezentuje aktualna wilgotnosc.
     */
    private double humidity;
    /**
     * Reprezentuje aktualna cisnienie.
     */
    private double pressure;

    /**
     * Reprezentuje opis stanu pogody.
     */
    private String description;
    /**
     * Reprezentuje nazwe miasta.
     */
    private String city;

    /**
     * Reprezentuje ikonke stanu pogody.
     */
    private String icon;
    /**
     * Reprezentuje aktualny czas.
     */
    private LocalTime localTime;
    /**
     * Reprezentuje prognozowana minimalna temperature.
     */
    private double minTempForecast;
    /**
     * Reprezentuje prognozowana maksymalna temperature.
     */
    private double maxTempForecast;
    /**
     * Reprezentuje prognozowana wilgotnosc.
     */
    private double humidityForecast;
    /**
     * Reprezentuje prognozowane cisnienie.
     */
    private double pressureForecast;

    /**
     * Reprezentuje opis prognozowanej pogody.
     */
    private String descriptionForecast;

    /**
     * Reprezentuje ikonke prognozowanej pogody.
     */
    private String iconForecast;
    /**
     * Reprezentuje date prognozy.
     */
    private String dateForecast;
    /**
     * Reprezentuje mape obiektu w formacie json z aktualnymi danymi pogodowymi.
     */
    private Map m;
    /**
     * Reprezentuje mape obiektu w formacie json z prognozowanymi danymi pogodowymi.
     */
    private Map forecastMap;
    /**
     * Reprezentuje obiekt klasy WeatherRequest sluzacy do komunikacji z serwerem.
     */
    private WeatherRequest weatherRequest;
    /**
     * Reprezentuje interwal czasowy pobierania danych z zserwera.
     */
    private double interval;
    /**
     * Reprezentuje nazwe miasta, dla ktorej zostaje pobrana pogoda.
     */
    private String userCity;
    /**
     * Reprezentuje osobnyn watek sluzacy do komunikacji z serwerem.
     */
    private Thread worker;
    /**
     * Reprezentuje wartosc logiczna czy watek jest uruchomiony.
     */
    protected volatile boolean isRunning;


    /**
     * Tworzy obiekt o zadanych parametrach, po czym na ich podstawie ustawia wartosc map i pomiarow.
     *
     * @param interval
     * @param userCity
     */
    public WeatherInfo(double interval, String userCity) {
        this.interval = interval;
        this.userCity = userCity;
        setM();
        setMeasurements();
    }

    /**
     * Tworzy obiekt o zadanych parametrach.
     *
     * @param temperature
     * @param humidity
     * @param pressure
     * @param description
     * @param city
     * @param icon
     * @param localTime
     * @param minTempForecast
     * @param maxTempForecast
     * @param humidityForecast
     * @param pressureForecast
     * @param descriptionForecast
     * @param iconForecast
     * @param dateForecast
     */
    public WeatherInfo(double temperature, double humidity, double pressure, String description, String city, String icon, LocalTime localTime, double minTempForecast, double maxTempForecast, double humidityForecast, double pressureForecast, String descriptionForecast, String iconForecast, String dateForecast) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.description = description;
        this.city = city;
        this.icon = icon;
        this.localTime = localTime;
        this.minTempForecast = minTempForecast;
        this.maxTempForecast = maxTempForecast;
        this.humidityForecast = humidityForecast;
        this.pressureForecast = pressureForecast;
        this.descriptionForecast = descriptionForecast;
        this.iconForecast = iconForecast;
        this.dateForecast = dateForecast;
    }


    /**
     * Tworzy obiekt.
     */
    public WeatherInfo() {
    }

    /**
     * Pobiera wartosc temperatury
     *
     * @return temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Ustawia wartosc temperatury
     *
     * @param temperature
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Pobiera wartosc wilgotnosci
     *
     * @return humidity
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Ustawaia wartosc wilgotnosci
     *
     * @param humidity
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * Pobiera wartosc cisnienia
     *
     * @return pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * Ustawia wartosc cisnienia
     *
     * @param pressure
     */
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    /**
     * Pobiera czas
     *
     * @return localTime
     */
    public LocalTime getLocalTime() {
        return localTime;
    }

    /**
     * Ustawia czas
     *
     * @param localTime
     */
    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    /**
     * Pobiera interwal czasowy
     *
     * @return interval
     */
    public double getInterval() {
        return interval;
    }

    /**
     * Pobiera nazwe miasta
     *
     * @param interval
     */
    public void setInterval(double interval) {
        this.interval = interval;
    }

    /**
     * Pobiera nazwe miasta
     *
     * @return userCity
     */
    public String getUserCity() {
        return userCity;
    }

    /**
     * Ustawia nazwe miasta podana przez uzytkownika
     *
     * @param userCity
     */
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    /**
     * Pobiera mape z aktualna pogoda
     *
     * @return m
     */
    public Map getM() {
        return m;
    }

    /**
     * Ustawia mape tworzac obiekt WeatherRequest i z niego pobierajac mape.
     */
    public void setM() {
        weatherRequest = new WeatherRequest(interval, userCity);
        m = weatherRequest.getWeatherMap();
        forecastMap = weatherRequest.getForecastMap();
    }

    /**
     * Pobiera nazwe miasta
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Ustawia nazwe miasta
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Pobiera opis pogody
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ustawia opis pogody
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Pobiera ikonke pogody
     *
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Ustawia nazwe ikonki
     *
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Pobiera prognozowana temperature minimalna
     *
     * @return minTempForecast
     */
    public double getMinTempForecast() {
        return minTempForecast;
    }

    /**
     * Pobiera prognozowana temperature maksymalna
     *
     * @return maxTempForecast
     */
    public double getMaxTempForecast() {
        return maxTempForecast;
    }

    /**
     * Pobiera prognozowana wilgotnosc
     *
     * @return humidityForecast
     */
    public double getHumidityForecast() {
        return humidityForecast;
    }

    /**
     * Pobiera prognozowane cisnienie
     *
     * @return pressureForecast
     */
    public double getPressureForecast() {
        return pressureForecast;
    }

    /**
     * Pobiera opis prognozowanej pogody
     *
     * @return descriptionForecast
     */
    public String getDescriptionForecast() {
        return descriptionForecast;
    }

    /**
     * Pobiera ikonke prognozowanej pogody
     *
     * @return iconForecast
     */
    public String getIconForecast() {
        return iconForecast;
    }

    /**
     * Pobiera date prognozowanej pogody
     *
     * @return dateForecast
     */
    public String getDateForecast() {
        return dateForecast;
    }

    /**
     * Ustawia prognozowana wilgotnosc
     *
     * @param humidityForecast
     */
    public void setHumidityForecast(double humidityForecast) {
        this.humidityForecast = humidityForecast;
    }

    /**
     * Ustawia prognozowane cisnienie
     *
     * @param pressureForecast
     */
    public void setPressureForecast(double pressureForecast) {
        this.pressureForecast = pressureForecast;
    }

    /**
     * Ustawia prognozowany stan pogody
     *
     * @param descriptionForecast
     */
    public void setDescriptionForecast(String descriptionForecast) {
        this.descriptionForecast = descriptionForecast;
    }

    /**
     * Ustawia prognozowana ikonke
     *
     * @param iconForecast
     */
    public void setIconForecast(String iconForecast) {
        this.iconForecast = iconForecast;
    }

    /**
     * Ustawia date prognozy
     *
     * @param dateForecast
     */
    public void setDateForecast(String dateForecast) {
        this.dateForecast = dateForecast;
    }

    /**
     * Ustawia prognozowana temperature minimalna
     *
     * @param minTempForecast
     */
    public void setMinTempForecast(double minTempForecast) {
        this.minTempForecast = minTempForecast;
    }

    /**
     * Ustawia prognozowana temperature maksymalna
     *
     * @param maxTempForecast
     */
    public void setMaxTempForecast(double maxTempForecast) {
        this.maxTempForecast = maxTempForecast;
    }

    /**
     * Pobiera mape z prognoza pogody
     *
     * @return forecastMap
     */
    public Map getForecastMap() {
        return forecastMap;
    }


    /**
     * Dodaje obserwatora do lity obserwatorow jesli taki nie znajduje się juz na liscie.
     *
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Usuwa obserwatora z lity obserwatorow jesli taki znajduje się na liscie.
     *
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    /**
     * Powiadamia obserwatorow z listy o aktualnych wartosciach temperatury, wilgotnosci, cisnienia i czasu.
     */
    @Override
    public void updateObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(this);
        }
        System.out.println("update");
    }

    /**
     * Ustawia wartosci pomiarow na postawie danych z mapy, po czym infromuje obserwatorow.
     */
    public void setMeasurements() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map m1 = gson.fromJson(m.get("main").toString(), Map.class);
        Map m2 = ((ArrayList<Map>) m.get("weather")).get(0);

        temperature = Double.parseDouble(m1.get("temp").toString());
        pressure = Double.parseDouble(m1.get("pressure").toString());
        humidity = Double.parseDouble(m1.get("humidity").toString());
        localTime = LocalTime.now();
        description = m2.get("description").toString();
        icon = m2.get("icon").toString();
        city = m.get("name").toString();

        Map m3 = ((ArrayList<Map>) forecastMap.get("list")).get(1);
        Map m6 = gson.fromJson(m3.get("main").toString(), Map.class);
        humidityForecast = Double.parseDouble(m6.get("humidity").toString());
        pressureForecast = Double.parseDouble(m6.get("pressure").toString());
        minTempForecast = Double.parseDouble(m6.get("temp_min").toString());
        maxTempForecast = Double.parseDouble(m6.get("temp_max").toString());
        Map m5 = ((ArrayList<Map>) m3.get("weather")).get(0);
        System.out.println(m5.toString());
        descriptionForecast = m5.get("description").toString();
        iconForecast = m5.get("icon").toString();
        System.out.println(icon);
        dateForecast = m3.get("dt_txt").toString();
        updateObservers();
    }

    /**
     * Tworzy nowy watek i go uruchamia.
     */
    public void start() {
        worker = new Thread(this, " Thread1");
        worker.start();
    }

    /**
     * Zatrzymuje watek
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Wstrzymuje watek
     */
    public void interrupt() {

        isRunning = false;
        worker.interrupt();
    }

    /**
     * Wykonuje sie w osobnym watku. Jesli watek jest uruchomiony ustawia mape i pomiary, po czym usypia watek na podany przez uzytkownika czas. Jesli watej jest wstrzymany to obsluzy wyjatek InterruptedException e.
     */
    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            try {
                setM();
                setMeasurements();
                Thread.sleep((int) (interval * 60000));

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed to complete operation");
            }
        }
    }
}
