/**
 * Interfejs Observable okreslajacy zbior zachowan obserwowanego podmiotu.
 *
 * @author IP
 * @version 1.0
 */
package sample;

public interface Observable {

    /**
     * Dodaje obesewatora.
     * @param observer
     */
    void addObserver(Observer observer);
    /**
     * Usuwa obesewatora.
     * @param observer
     */
    void removeObserver(Observer observer);
    /**
     * Powiadamia obesewatorow.
     */
    void updateObservers();

}
