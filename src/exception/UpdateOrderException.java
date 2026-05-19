package exception;

/**
 * Lancée lors d'un échec de mise à jour d'une commande en base de données.
 */
public class UpdateOrderException extends Exception {

    public UpdateOrderException(String message) {
        super(message);
    }
}