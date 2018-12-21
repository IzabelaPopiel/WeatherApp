/**
 * Klasa DisplayTxtArea sluzaca do aktualnego stanu pogody w polu tekstowym.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DisplayTxtArea implements Observer {

    /**
     * Reprezentuje pole tekstowe wyswieltajace aktualny stan pogody.
     */
    private TextArea txtArea;

    /**
     * Tworzy obiekt o podanych parametrach
     * @param txtArea
     */
    public DisplayTxtArea(TextArea txtArea) {
        this.txtArea = txtArea;
    }

    /**
     * Na postawie otrzymanych danych wyswietla informacj o aktualnym stanie pogody w polu tekstowym.
     */
    @Override
    public void update(WeatherInfo weatherInfo) {
        double temperature = weatherInfo.getTemperature();
        double humidity = weatherInfo.getHumidity();
        double pressure = weatherInfo.getPressure();
        LocalTime localTime = weatherInfo.getLocalTime();
        Platform.runLater(() -> txtArea.appendText("\n" + temperature + "\u2103, " + humidity + "%, " + pressure + "hPa " + localTime.format(DateTimeFormatter.ofPattern("HH:mm"))));

    }
}
