package exception;

/**
 * Lancée lors d'un échec d'insertion d'une commande en base de données.
 */
public class AddOrderException extends Exception {

    public AddOrderException(String message) {
        super(message);
    }
}