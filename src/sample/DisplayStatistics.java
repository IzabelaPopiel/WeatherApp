/**
 * Klasa DisplayStatistics sluzaca do wyswietlania stasystyki pogodowej.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.time.LocalTime;
import java.util.ArrayList;

public class DisplayStatistics implements Observer {

    private String cityName;
    /**
     * Reprezentuje liste przechowujaca wartosci temperatury.
     */
    private ArrayList<Double> temperatureValues;
    /**
     * Reprezentuje liste przechowujaca wartosci cisnienia.
     */
    private ArrayList<Double> pressureValues;
    /**
     * Reprezentuje liste przechowujaca wartosci wilgotnosci.
     */
    private ArrayList<Double> humidityValues;
    /**
     * Reprezentuje pole tekstowe wyswieltajace statystyke.
     */
    private TextArea txtStatistics;

    private Label lblTitle;


    /**
     * Reprezentuje srednia wartosc temperatury.
     */
    private double tempMean;
    /**
     * Reprezentuje srednia wartosc cisnienia.
     */
    private double pressureMean;
    /**
     * Reprezentuje srednia wartosc wilgotnosci.
     */
    private double humidityMean;

    /**
     * Reprezentuje maksymalna wartosc temperatury.
     */
    private double tempMax;
    /**
     * Reprezentuje maksymalna wartosc cisnienia.
     */
    private double pressureMax;
    /**
     * Reprezentuje maksymalna wartosc wilgostnosci.
     */
    private double humidityMax;

    /**
     * Reprezentuje minimalna wartosc temperatury.
     */
    private double tempMin;
    /**
     * Reprezentuje minimalna wartosc cisnienia.
     */
    private double pressureMin;
    /**
     * Reprezentuje minimalna wartosc wilgotnosci.
     */
    private double humidityMin;

    /**
     * Reprezentuje odchylenie standardowe wartosci temperatury.
     */
    private double tempSTD;
    /**
     * Reprezentuje odchylenie standardowe wartosci cisnienia.
     */
    private double pressureSTD;
    /**
     * Reprezentuje odchylenie standardowe wartosci wilgotnosci.
     */
    private double humiditySTD;

    /**
     * Tworzy obiekt DisplayStatistics o parametrze.
     *
     * @param txtStatistics
     */
    public DisplayStatistics(TextArea txtStatistics, Label lblTitle) {
        this.txtStatistics = txtStatistics;
        this.lblTitle = lblTitle;
        temperatureValues = new ArrayList<>();
        pressureValues = new ArrayList<>();
        humidityValues = new ArrayList<>();
    }

    /**
     * Oblicza wartosci statystyczne
     */
    public void calculateStatistics() {
        tempMean = mean(temperatureValues);
        pressureMean = mean(pressureValues);
        humidityMean = mean(humidityValues);

        tempMax = max(temperatureValues);
        pressureMax = max(pressureValues);
        humidityMax = max(humidityValues);

        tempMin = min(temperatureValues);
        pressureMin = min(pressureValues);
        humidityMin = min(humidityValues);

        tempSTD = std(temperatureValues);
        pressureSTD = std(pressureValues);
        humiditySTD = std(humidityValues);
    }

    /**
     * Pobiera wartosc sredniej temperatury.
     * @return tempMean
     */
    public double getTempMean() {
        return tempMean;
    }

    /**
     * Pobiera wartosc sredniego cisnienia.
     * @return pressureMean
     */
    public double getPressureMean() {
        return pressureMean;
    }

    /**
     * Pobiera wartosc sredniej wilgnotnosci.
     * @return humidityMean
     */
    public double getHumidityMean() {
        return humidityMean;
    }

    /**
     * Pobiera wartosc maksymalnej temperatury.
     * @return tempMax
     */
    public double getTempMax() {
        return tempMax;
    }

    /**
     * Pobiera wartosc maksymalnego cisnienia.
     * @return pressureMax
     */
    public double getPressureMax() {
        return pressureMax;
    }

    /**
     * Pobiera wartosc maksymalnej wilgnotnosci.
     * @return humidityMax
     */
    public double getHumidityMax() {
        return humidityMax;
    }

    /**
     * Pobiera wartosc minimalnej temperatury.
     * @return tempMin
     */
    public double getTempMin() {
        return tempMin;
    }

    /**
     * Pobiera wartosc minimalnego cisnienia.
     * @return pressureMin
     */
    public double getPressureMin() {
        return pressureMin;
    }

    /**
     * Pobiera wartosc minimalnej wilgnotnosci.
     * @return humidityMin
     */
    public double getHumidityMin() {
        return humidityMin;
    }

    /**
     * Pobiera wartosc odchylenia standardowego temperatury.
     * @return tempStd
     */
    public double getTempSTD() {
        return tempSTD;
    }

    /**
     * Pobiera wartosc odchylenia standardowego cisnienia.
     * @return pressureStd
     */
    public double getPressureSTD() {
        return pressureSTD;
    }

    /**
     * Pobiera wartosc odchylenia standardowego wilgostnosci.
     * @return humidityStd
     */
    public double getHumiditySTD() {
        return humiditySTD;
    }

    /**
     * Na podstawie otrzymanych parametrow dodaje do list nowe wartosci, po czym oblicza statystyku i wyswietla jej wartosci w polu tekstowym.
     * @param weatherInfo
     */
    @Override
    public void update(WeatherInfo weatherInfo) {
        double temperature = weatherInfo.getTemperature();
        double humidity = weatherInfo.getHumidity();
        double pressure = weatherInfo.getPressure();
        LocalTime localTime = weatherInfo.getLocalTime();
        cityName = weatherInfo.getCity();
        temperatureValues.add(temperature);
        pressureValues.add(pressure);
        humidityValues.add(humidity);

        calculateStatistics();

        Platform.runLater(() -> {
            String string = "Liczba pomiarów: " + temperatureValues.size() + "\n\nśrednia temperatura: " + getTempMean() + "\u2103\ntemperatura minimalna: " + getTempMin() + "\u2103\ntemperatura maksymalna: " + getTempMax() + "\u2103\nodchylenie standardowe tempratury: " + getTempSTD() + "\u2103\n\nśrednia wilgotność: " + getHumidityMean() + "%\nwilgotność minimalna: " + getHumidityMin() + "%\nwilgotność maksymalna: " + getHumidityMax() + "%\nodchylenie standardowe wilgotności: " + getHumiditySTD() + "%\n\nśrednie ciśnienie: " + getPressureMean() + "hPa\nminimalne ciśnienie: " + getPressureMin() + "hPa\nmaksymalne ciśnienie: " + getPressureMax() + "hPa\nodchylenie standardowe ciśnienia: " + getPressureSTD() + "hPa";

            txtStatistics.clear();
            txtStatistics.appendText(string);
            System.out.println(cityName);
            lblTitle.setText("POGODA " + cityName.toUpperCase());
        });
    }

    /**
     * Oblicza srednia z wartosci umieszczonych w liscie.
     * @param arrayList
     * @return mean
     */
    public double mean(ArrayList<Double> arrayList) {

        double sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum += arrayList.get(i);
        }
        return sum / arrayList.size();
    }

    /**
     * Znajduje wartosc maksymalna z wartosci umieszczonych w liscie.
     * @param arrayList
     * @return max
     */
    public double max(ArrayList<Double> arrayList) {
        double max = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) > max) {
                max = arrayList.get(i);
            }
        }
        return max;
    }

    /**
     * Znajduje wartosc minimalna z wartosci umieszczonych w liscie.
     * @param arrayList
     * @return min
     */
    public double min(ArrayList<Double> arrayList) {
        double min = arrayList.get(0);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) > min) {
                min = arrayList.get(i);
            }
        }
        return min;
    }

    /**
     * Oblicza wartosc odchylenia standardowego z wartosci umieszczonych w liscie.
     * @param arrayList
     * @return std
     */
    public double std(ArrayList<Double> arrayList) {
        mean(arrayList);
        double sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum = sum + Math.pow((arrayList.get(i) - mean(arrayList)), 2);
        }
        return sum / arrayList.size();
    }
}
