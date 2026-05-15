package exception;

/**
 * Lancée lors d'un échec de mise à jour d'une réservation en base de données.
 */
public class Updatereservationexception extends Exception {

    public Updatereservationexception(String message) {
        super(message);
    }
}