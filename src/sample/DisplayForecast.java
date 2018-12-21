/**
 * Klasa DisplayForecast wyswietlajaca prognoze pogody.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class DisplayForecast implements Observer {

    /**
     * Reprezentuje etykiete z prognoza i grafika ilustrujaca stan pogody.
     */
    private Label lblForecastIcon;

    /**
     * Tworzy obiekt o zadanych paramterach
     *
     * @param lblForecastIcon
     */
    public DisplayForecast(Label lblForecastIcon) {
        this.lblForecastIcon = lblForecastIcon;
    }

    /**
     * Wyswietla w etykiecie informacje o prognozie pogody oraz grafike.
     */
    @Override
    public void update(WeatherInfo weatherInfo) {
        double minTemp = weatherInfo.getMinTempForecast();
        System.out.println("min temp" + minTemp);
        double maxTemp = weatherInfo.getMaxTempForecast();
        double pressure = weatherInfo.getPressureForecast();
        double humidity = weatherInfo.getHumidityForecast();
        String description = weatherInfo.getDescriptionForecast();
        String icon = weatherInfo.getIconForecast();
        String date = weatherInfo.getDateForecast();
        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

        Platform.runLater(() -> {
            lblForecastIcon.setStyle("-fx-graphic: url('" + iconUrl + "'); ");

            String s = new String("Prognoza pogody\n" + date + "\nTemp min: " + minTemp + "\u2103\nTemp max: " + maxTemp + "\u2103\nCiśnienie: " + pressure + "hPa\nWilgotność: " + humidity + "%\n" + description);
            lblForecastIcon.setText(s);
        });
    }
}
