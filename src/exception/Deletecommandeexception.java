package exception;

/**
 * Lancée lors d'un échec de suppression d'une commande en base de données.
 */
public class Deletecommandeexception extends Exception {

    public Deletecommandeexception(String message) {
        super(message);
    }
}