/**
 * Klasa DisplayChart sluzaca do wyswietlania danych na wykresie.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DisplayChart implements Observer {

    /**
     * Reprezentuje wykres liniowy temperatury.
     */
    private LineChart<String, Number> chartTemp;
    /**
     * Reprezentuje wykres liniowy cisnienia.
     */
    private LineChart<String, Number> chartPressure;
    /**
     * Reprezentuje wykres liniowy wilgotnosci.
     */
    private LineChart<String, Number> chartHumidity;

    /**
     * Reprezentuje liste tablicowa przechowujaca wartosci serii danych z wartosciami temperatury.
     */
    private XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    /**
     * Reprezentuje liste tablicowa przechowujaca wartosci serii danych z wartosciami cisnienia.
     */
    private XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    /**
     * Reprezentuje liste tablicowa przechowujaca wartosci serii danych z wartosciami wilgotnosci.
     */
    private XYChart.Series<String, Number> series3 = new XYChart.Series<>();


    /**
     * Tworzy obiekt DisplayChart o zadanych parametrach.
     *
     * @param chartTemp
     * @param chartPressure
     * @param chartHumidity
     */
    public DisplayChart(LineChart<String, Number> chartTemp, LineChart<String, Number> chartPressure, LineChart<String, Number> chartHumidity) {
        this.chartTemp = chartTemp;
        this.chartPressure = chartPressure;
        this.chartHumidity = chartHumidity;
    }

    /**
     * Usuwa z wykresow wczesniejsze serie, dodaje do list przechowujacych serie nowe dane, po czym dodaje do wykresow nowe serie z akutalnymi danymi.
     *
     * @param weatherInfo
     */
    @Override
    public void update(WeatherInfo weatherInfo) {
        double temperature = weatherInfo.getTemperature();
        double humidity = weatherInfo.getHumidity();
        double pressure = weatherInfo.getPressure();
        LocalTime localTime = weatherInfo.getLocalTime();

        Platform.runLater(() -> {

            chartTemp.getData().removeAll(chartTemp.getData());
            chartPressure.getData().removeAll(chartPressure.getData());
            chartHumidity.getData().removeAll(chartHumidity.getData());

            series1.getData().add(new XYChart.Data<String, Number>(localTime.format(DateTimeFormatter.ofPattern("HH:mm")), temperature));
            chartTemp.getData().addAll(series1);

            series2.getData().add(new XYChart.Data<String, Number>(localTime.format(DateTimeFormatter.ofPattern("HH:mm")), pressure));
            chartPressure.getData().addAll(series2);

            series3.getData().add(new XYChart.Data<String, Number>(localTime.format(DateTimeFormatter.ofPattern("HH:mm")), humidity));
            chartHumidity.getData().addAll(series3);
        });
    }
}
