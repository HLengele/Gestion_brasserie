package exception;

/**
 * Lancée lors d'un échec d'insertion d'une commande en base de données.
 */
public class Addcommandeexception extends Exception {

    public Addcommandeexception(String message) {
        super(message);
    }
}