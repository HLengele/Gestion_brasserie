package exception;

/**
 * Lancée lors d'un échec d'insertion d'une réservation en base de données.
 * La couche DataAccess transforme la SQLException en cette exception.
 */
public class Addreservationexception extends Exception {

    public Addreservationexception(String message) {
        super(message);
    }
}