/**
 * Klasa DisplayForecast wyswietlajaca prognoze pogody.
 *
 * @author IP
 * @version 1.0
 */package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class DisplayIcon implements Observer {

    private Label lblDescription;

    public DisplayIcon(Label lblDescription) {
        this.lblDescription = lblDescription;
    }

    @Override
    public void update(WeatherInfo weatherInfo) {
        String icon = weatherInfo.getIcon();
        System.out.println("ikonka gora:" + icon);
        String description = weatherInfo.getDescription();

        String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

        Platform.runLater(() -> {
            lblDescription.setText(description);
            lblDescription.setStyle("-fx-graphic: url('" + iconUrl + "'); "
            );
        });
    }
}
