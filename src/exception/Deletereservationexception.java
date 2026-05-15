package exception;

/**
 * Lancée lors d'un échec de suppression d'une réservation en base de données.
 */
public class Deletereservationexception extends Exception {

    public Deletereservationexception(String message) {
        super(message);
    }
}