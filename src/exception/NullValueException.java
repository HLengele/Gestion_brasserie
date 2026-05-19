package exception;

/**
 * Lancée lorsqu'une valeur obligatoire est nulle ou vide.
 * Utilisée dans les setters de toutes les classes modèle.
 */
public class NullValueException extends Exception {

    private String fieldName;

    public NullValueException(String message) {
        super(message);
    }

    public NullValueException(String fieldName, String message) {
        super(message);
        setFieldName(fieldName);
    }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
}