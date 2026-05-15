package exception;

/**
 * Lancée lors d'un échec de mise à jour d'une commande en base de données.
 */
public class Updatecommandeexception extends Exception {

    public Updatecommandeexception(String message) {
        super(message);
    }
}