package exception;

/**
 * Lancée lors d'un échec de lecture en base de données.
 * Exception générique couvrant tous les SELECT.
 */
public class Readexception extends Exception {

    public Readexception(String message) {
        super(message);
    }
}