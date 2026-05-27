package exception;


 // Lancée lors d'un échec de suppression d'une commande en base de données.

public class DeleteOrderException extends Exception {

    public DeleteOrderException(String message) {
        super(message);
    }
}