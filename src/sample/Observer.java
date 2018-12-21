/**
 * Interfejs Observer okreslajacy zachowanie obserwatora podmiotu.
 *
 * @author IP
 * @version 1.0
 */
package sample;

import java.time.LocalTime;

public interface Observer {

    /**
     * Wykonuje dzialania po otrzymaniu powiadomienia o zmianie wartosci parametrow.
     * @param weatherInfo
     */
    void update(WeatherInfo weatherInfo);
}
