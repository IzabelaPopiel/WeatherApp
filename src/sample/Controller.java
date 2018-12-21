/**
 * Klasa Controller kontrolująca działanie aplikacji.
 *
 * @author IP
 * @version 1.0
 */

package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    /**
     * Reprezentuje etykiete z aktualnym opisem pogody.
     */
    @FXML
    private Label lblDescription;

    /**
     * Reprezentuje etykiete z tytulem aplikacji.
     */
    @FXML
    private Label lblTitle;

    /**
     * Reprezentuje pole tekstowe, do ktorego wprowadza sie nazwe miasta.
     */
    @FXML
    private TextField txtCity;

    /**
     * Reprezentuje pole tekstowe, do ktorego wprowadza się czestosc pobierania danych.
     */
    @FXML
    private TextField txtFreq;

    /**
     * Reprezentuje przycisk starujacy pobieranie danych.
     */
    @FXML
    private Button btnStart;

    /**
     * Reprezentuje przycisk wstrzymujacy pobieranie danych.
     */
    @FXML
    private Button btnPause;

    /**
     * Reprezentuje przycisk zatrzymujacy pobieranie danych.
     */
    @FXML
    private Button btnStop;

    /**
     * Reprezentuje przycisk wczytujacy podany plik.
     */
    @FXML
    private Button btnReadFile;

    /**
     * Reprezentuje pole tekstowe wyswietlajace pobrane dane pogodowe oraz ostrzezenia.
     */
    @FXML
    private TextArea txtWarning;

    /**
     * Reprezentuje pole tekstowe wyswietlajace statystyke.
     */
    @FXML
    private TextArea txtStatistics;

    /**
     * Reprezentuje etykiete z grafika ilustrujaca stan pogody.
     */
    @FXML
    private Label lblForecastIcon;

    /**
     * Reprezentuje wykres liniowy zmian temperaury w czasie.
     */
    @FXML
    private LineChart<String, Number> chartTemp;

    /**
     * Reprezentuje os x wykresu temperatury z wartosciami czasu.
     */
    @FXML
    private CategoryAxis xAxisTemp;

    /**
     * Reprezentuje os y wykresu temperatury z wartosciami temperatury.
     */
    @FXML
    private NumberAxis yAxisTemp;

    /**
     * Reprezentuje wykres liniowy zmian cisnienia w czasie.
     */
    @FXML
    private LineChart<String, Number> chartPressure;

    /**
     * Reprezentuje os x wykresu cisnienia z wartosciami czasu.
     */
    @FXML
    private CategoryAxis xAxisPressure;

    /**
     * Reprezentuje os y wykresu temperatury z wartosciami cisnienia.
     */
    @FXML
    private NumberAxis yAxisPressure;

    /**
     * Reprezentuje wykres liniowy zmian wligotnosci w czasie.
     */
    @FXML
    private LineChart<String, Number> chartHumidity;

    /**
     * Reprezentuje os x wykresu wilgotnosci z wartosciami czasu.
     */
    @FXML
    private CategoryAxis xAxisHumidity;

    /**
     * Reprezentuje os y wykresu wilgotnosci z wartosciami wilgotnosci.
     */
    @FXML
    private NumberAxis yAxisHumidity;

    /**
     * Reprezentuje przycisk zapisujacy dane do podany plik.
     */
    @FXML
    private Button btnSave;

    /**
     * Reprezentuje etykiete dotyczaca zapisywania danych do pliku.
     */
    @FXML
    private Label lblSaveFilePath;

    /**
     * Reprezentuje pole tekstowe, do ktorego wprowadza sie sciezke pliku do zapisu danych.
     */
    @FXML
    private TextField txtSaveFilePath;

    /**
     * Reprezentuje etykiete dotyczaca odczytywania danych z pliku.
     */
    @FXML
    private Label lblLoadFilePath;

    /**
     * Reprezentuje pole tekstowe, do ktorego wprowadza sie sciezke pliku do odczytu danych.
     */
    @FXML
    private TextField txtLoadFilePath;

    /**
     * Reprezentuje przycisk czyszczacy wszystkie pola tekstowe i wykresy.
     */
    @FXML
    private Button btnClear;


    /**
     * Reprezentuje obiekt klasy WeatherInfo, ktory bedzie przechowywal aktualne dane pogodowe.
     */
    private WeatherInfo weatherInfo;

    /**
     * Reprezentuje obiekt klasy WeatherDataBase, ktory bedzie przechowywal baze danych pogodowych.
     */
    private WeatherDataBase weatherDataBase;


    /**
     * Tworzy obiekt Controller
     */
    public Controller() {
        System.out.println("Calling constructor");
    }

    /**
     * Nadaje wartosci początkowe obiektowi klasy Controller - czysci pola tekstowe i wyrkesy aplikacji, tworzy nowe obiekty klas WeatherInfo i WeatherDataBase oraz dodaje obserwatorow do obiektu WeatherInfo.
     */
    public void initialize() {

        System.out.println("Calling initialize");
        clear();

        weatherInfo = new WeatherInfo();
        weatherDataBase = new WeatherDataBase();

        weatherInfo.addObserver(new DisplayCurrentWeather());
        weatherInfo.addObserver(new DisplayTxtArea(txtWarning));
        weatherInfo.addObserver(new DisplayChart(chartTemp, chartPressure, chartHumidity));
        weatherInfo.addObserver(new DisplayStatistics(txtStatistics, lblTitle));
        weatherInfo.addObserver(new DisplayForecast(lblForecastIcon));
        weatherInfo.addObserver(new DisplayIcon(lblDescription));
    }

    /**
     * Reaguje po wcisnieciu przycisku start. Pobiera podana przez uzytkownika nazwe miast oraz wartosc czetosci pobierania danych. Zmienia tytul aplikacji dopisujac nazwe miasta, dodaje nowego obserwatora - obiekt klasy WeatherDataBase, w celu zapisywania pobranych danych do bazy pogodowej, ustawia pola obiektu weatherInfo i rozpoczyna nowy watek. W przypadku nieprawidlowej wartosci czestosci wyrzuca wyjatek o niepoprawnym formacie wprowadzonych danych.
     */
    @FXML
    void btnStartPressed(ActionEvent event) {

        try {
            String cityName = txtCity.getText();
            double interval = Double.parseDouble(txtFreq.getText());

            weatherInfo.addObserver(weatherDataBase);

            weatherInfo.setInterval(interval);
            weatherInfo.setUserCity(cityName);
            weatherInfo.start();
            txtWarning.appendText("\nstart");


        } catch (NumberFormatException e) {
            txtWarning.setText("\nNIEPRAWIDŁOWE DANE");
        }
    }

    /**
     * Wstrzymuje watek klasy WeatherInfo i wyswietla odpowiedni komunikat
     */
    @FXML
    void btnPausePressed(ActionEvent event) {
        txtWarning.appendText("\nwstrzymano pobieranie");
        weatherInfo.interrupt();
    }

    /**
     * Zatrzymuje watek klasy WeatherInfo i ustawia wartosci poczatkowe.
     */
    @FXML
    void btnStopPressed(ActionEvent event) {
        txtWarning.appendText("\nzatrzymano pobieranie");
        weatherInfo.stop();
        initialize();
    }

    /**
     * Zapisuje dane pogodowe do pliku o podanej nazwie.
     */
    @FXML
    void btnSavePressed(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save File");
        File file = chooser.showSaveDialog(new Stage());

        System.out.println(file.toString());
        if (file != null) {
            String filePath = file.toString();
            weatherDataBase.saveToFile(filePath, txtWarning);
        }
    }

    /**
     * Wczytuje dane pogodowe z pliku o podanej nazwie. Dla kazdego zapisanego stanu pogody pobiera informacje o temperaturze, cisnieniu, wilgotnosci i czasie, po czym powiadamia obserwatorow o zmianie stanu pogody. Observator weatherData zostaje usuniety z listy obserwatorow, zeby do bazy nie zostaly wczytane wartosci ktore sa odczytywane z pliku.
     */
    @FXML
    void btnReadFilePressed(ActionEvent event) {
        weatherInfo.removeObserver(weatherDataBase);

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());

        System.out.println(file.toString());
        if (file != null) {
            String filePath = file.toString();
            weatherDataBase.readFromFile(filePath, txtWarning);
        }

        for (int i = 0; i < weatherDataBase.getWeatherInfoList().size(); i++) {
            weatherInfo.setTemperature(weatherDataBase.getWeatherInfoList().get(i).getTemperature());
            weatherInfo.setPressure(weatherDataBase.getWeatherInfoList().get(i).getPressure());
            weatherInfo.setHumidity(weatherDataBase.getWeatherInfoList().get(i).getHumidity());
            weatherInfo.setLocalTime(weatherDataBase.getWeatherInfoList().get(i).getLocalTime());
            weatherInfo.setCity(weatherDataBase.getWeatherInfoList().get(i).getCity());
            weatherInfo.setDescription(weatherDataBase.getWeatherInfoList().get(i).getDescription());
            weatherInfo.setIcon(weatherDataBase.getWeatherInfoList().get(i).getIcon());

            weatherInfo.setMinTempForecast(weatherDataBase.getWeatherInfoList().get(i).getMinTempForecast());
            weatherInfo.setMaxTempForecast(weatherDataBase.getWeatherInfoList().get(i).getMaxTempForecast());
            weatherInfo.setPressureForecast(weatherDataBase.getWeatherInfoList().get(i).getPressureForecast());
            weatherInfo.setHumidityForecast(weatherDataBase.getWeatherInfoList().get(i).getHumidityForecast());
            weatherInfo.setDescriptionForecast(weatherDataBase.getWeatherInfoList().get(i).getDescriptionForecast());
            weatherInfo.setIconForecast(weatherDataBase.getWeatherInfoList().get(i).getIconForecast());
            weatherInfo.setDateForecast(weatherDataBase.getWeatherInfoList().get(i).getDateForecast());

            weatherInfo.updateObservers();
        }
    }


    /**
     * Zatrzymuje watek i ustawia domyslne wartosci poczatkowe.
     */
    @FXML
    void btnClearPressed(ActionEvent event) {
        weatherInfo.stop();
        initialize();
    }


    /**
     * Czysci wszystkie wprowadzone przez uzytkownika dane, pola tektowe, wykresy oraz wybrane etykiety.
     */
    void clear() {
        txtWarning.clear();
        lblTitle.setText("POGODA");
        txtStatistics.clear();
        chartTemp.getData().removeAll(chartTemp.getData());
        chartPressure.getData().removeAll(chartPressure.getData());
        chartHumidity.getData().removeAll(chartHumidity.getData());
        txtCity.clear();
        txtFreq.clear();
        lblDescription.setText("");
        lblDescription.setGraphic(null);
        lblForecastIcon.setText("");
        lblForecastIcon.setGraphic(null);
    }
}

